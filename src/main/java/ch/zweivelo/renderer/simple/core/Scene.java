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

package ch.zweivelo.renderer.simple.core;

import ch.zweivelo.renderer.simple.cameras.Camera;
import ch.zweivelo.renderer.simple.lights.Light;
import ch.zweivelo.renderer.simple.shapes.Shape;

import java.util.Collection;

/**
 * Data structure holding the information about a certain scene setup
 *
 * @author Michael Bieri
 * @since 12.06.16
 */
public class Scene {

    private final Collection<Shape> shapes;

    private final Collection<Light> lights;

    private final Collection<Camera> cameras;

    public Scene(Collection<Shape> shapes, Collection<Light> lights, Collection<Camera> cameras) {
        this.shapes = shapes;
        this.lights = lights;
        this.cameras = cameras;
    }

    public Collection<Shape> getShapes() {
        return shapes;
    }

    public Collection<Light> getLights() {
        return lights;
    }

    public Collection<Camera> getCameras() {
        return cameras;
    }

    @Override
    public String toString() {
        return String.format("Scene: %d shapes, %d lights, %d cameras", shapes.size(), lights.size(), cameras.size());
    }
}
