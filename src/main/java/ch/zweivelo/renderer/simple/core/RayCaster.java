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

import ch.zweivelo.renderer.simple.app.ApplicationConfiguration;
import ch.zweivelo.renderer.simple.cameras.Camera;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.stream.IntStream;

/**
 * Renderer using the ray casting method.
 *
 * @author Michael Bieri
 * @since 12.06.16
 */

@Slf4j
public class RayCaster implements Renderer {

    @Override
    public void render(ApplicationConfiguration configuration, Scene scene) {

        // Simplification: use only the first camera
        final Camera camera = scene.getCameras().stream().findFirst().orElseThrow(RuntimeException::new);

        IntStream.range(0, configuration.getHeight())
                .mapToObj(Integer::valueOf)
                .flatMap(y -> IntStream.range(0, configuration.getWidth()).mapToObj(Integer::valueOf).map(x -> new Vector2D(x, y)))
                .forEach(p -> log.trace("render point: {}", p));

    }

}
