/*
 * Copyright 2016 Michael Bieri
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.zweivelo.renderer.simple.app;

import ch.zweivelo.renderer.simple.cameras.Camera;
import ch.zweivelo.renderer.simple.core.Scene;
import ch.zweivelo.renderer.simple.lights.Light;
import ch.zweivelo.renderer.simple.math.Color;
import ch.zweivelo.renderer.simple.shapes.Plane;
import ch.zweivelo.renderer.simple.shapes.Shape;
import ch.zweivelo.renderer.simple.shapes.Sphere;

import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Reader capable of parsing a YAML file and creating a {@link Scene} representation.
 *
 * @author Michael Bieri
 * @see org.yaml.snakeyaml.Yaml
 * @since 12.06.16
 */
@SuppressWarnings("unchecked") // much casting due to the nature of the YAML decoder
public class YamlSceneReader implements SceneReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(YamlSceneReader.class);

    private static final String SHAPES_KEY = "shapes";
    private static final String LIGHTS_KEY = "lights";
    private static final String CAMERAS_KEY = "cameras";

    private static final String SPHERE_KEY = "sphere";
    private static final String PLANE_KEY = "plane";

    @Override
    public Scene getScene(ApplicationConfiguration configuration) {

        final String sceneName = configuration.getSceneName();

        final Optional<Map<String, Object>> sceneMap = readSceneMap(sceneName);

        return sceneMap.map(this::createSceneFromMap).orElseThrow(() -> new SceneReaderException("Error reading YAML scene description."));

    }

    private Optional<Map<String, Object>> readSceneMap(String sceneName) {

        Optional<Map<String, Object>> sceneMap;
        try (InputStream yamlStream = FileUtils.openInputStream(new File(sceneName))) {

            Yaml yaml = new Yaml();

            LOGGER.info("Loading scene: " + sceneName);

            //noinspection unchecked -> untyped object created by the YAML reader
            sceneMap = Optional.of((Map<String, Object>) yaml.load(yamlStream));

            LOGGER.debug("Loaded scene: " + sceneMap);

        } catch (IOException exception) {
            LOGGER.error("Error reading scene file: " + exception.getMessage(), exception);
            sceneMap = Optional.empty();
        }

        return sceneMap;
    }

    private Scene createSceneFromMap(Map<String, Object> sceneMap) {

        Collection<Shape> shapes = null;
        Collection<Light> lights = null;
        Collection<Camera> cameras = null;

        for (Map.Entry<String, Object> entry : sceneMap.entrySet()) {
            switch(entry.getKey()) {
                case SHAPES_KEY:
                    shapes = decodeShapes((List<Map<String, Object>>) entry.getValue());
                    break;

                case LIGHTS_KEY:
                    lights = decodeLights((List<Map<String, Object>>) entry.getValue());
                    break;

                case CAMERAS_KEY:
                    cameras = decodeCameras((List<Map<String, Object>>) entry.getValue());
                    break;

                default:
                    throw new SceneReaderException("Invalid yaml key found: " + entry.getKey());
            }
        }

        if (shapes == null) {
            throw new SceneReaderException("No shapes defined in scene");
        }

        if (lights == null) {
            throw new SceneReaderException("No lights defined in scene");
        }

        if (cameras == null) {
            throw new SceneReaderException("No cameras defined in scene");
        }

        return new Scene(shapes, lights, cameras);
    }

    private Collection<Shape> decodeShapes(List<Map<String, Object>> shapesMap) {
        final List<Shape> shapes = new ArrayList<>();

        for (Map<String, Object> shapeWrapperMap : shapesMap) {
            if (shapeWrapperMap.size() != 1) {
                throw new SceneReaderException("Invalid shape description: " + shapeWrapperMap.toString());
            }
            final Map.Entry<String, Object> shapeMapEntry = shapeWrapperMap.entrySet().iterator().next();

            final String shapeName = shapeMapEntry.getKey();
            final Shape shape;
            switch(shapeName) {
                case SPHERE_KEY:
                    shape = decodeSphere((Map<String, Object>) shapeMapEntry.getValue());
                    break;

                case PLANE_KEY:
                    shape = decodePlane((Map<String, Object>) shapeMapEntry.getValue());
                    break;

                default:
                    throw new SceneReaderException("Unknown shape found: " + shapeName);
            }

            shapes.add(shape);
        }

        return shapes;
    }

    private Collection<Light> decodeLights(List<Map<String, Object>> lightsMap) {
        return Collections.emptyList();
    }

    private Collection<Camera> decodeCameras(List<Map<String, Object>> camerasMap) {
        return Collections.emptyList();
    }

    private Sphere decodeSphere(Map<String, Object> sphereMap) {
        Vector3D center = decodeVector3D((Map<String, Number>) sphereMap.get("center"));
        double radius = ((Number)sphereMap.get("radius")).doubleValue();
        Color color = decodeColor((Map<String, Number>) sphereMap.get("color"));

        return new Sphere(center, radius, color);
    }

    private Plane decodePlane(Map<String, Object> planeMap) {
        Vector3D point = decodeVector3D((Map<String, Number>) planeMap.get("point"));
        Vector3D normal = decodeVector3D((Map<String, Number>) planeMap.get("normal"));
        Color color = decodeColor((Map<String, Number>) planeMap.get("color"));

        return new Plane(point, normal, color);
    }

    private Vector3D decodeVector3D(Map<String, Number> vectorValues) {
        return new Vector3D(vectorValues.get("x").doubleValue(), vectorValues.get("y").doubleValue(), vectorValues.get("z").doubleValue());
    }

    private Color decodeColor(Map<String, Number> colorValues) {
        return new Color(colorValues.get("r").doubleValue(), colorValues.get("g").doubleValue(), colorValues.get("b").doubleValue());
    }
}
