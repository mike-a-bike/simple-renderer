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

import lombok.extern.slf4j.Slf4j;
import lombok.val;
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

        printRenderingHeader(configuration, scene);

        // Simplification: use only the first camera
        val camera = scene.getCameras().stream().findFirst().orElseThrow(RuntimeException::new);

        // Initialize pixel stream for rendering (this contains sampled sub-pixel as well)
        val pixelStream = IntStream.range(0, configuration.getHeight())
                .mapToObj(Integer::valueOf)
                .flatMap(y -> IntStream.range(0, configuration.getWidth()).mapToObj(Integer::valueOf).map(x -> new Vector2D(x, y)))
                .flatMap(configuration.getSubPixelSampler()::sample)
                .peek(p -> log.trace("render point: {}", p));

        printRenderingFooter();

    }

    private void printRenderingHeader(ApplicationConfiguration configuration, Scene scene) {
        log.info("-----------------------------------------------------------");
        log.info("RayCaster");
        log.info(" - rendering: {}", scene);
        log.info(" - image dimensions: {}x{}", configuration.getWidth(), configuration.getHeight());
        log.info(" - output: {} ({})", configuration.getImageFileName(), configuration.getFormat());
        log.info(" - sub pixel sampler: {}", configuration.getSubPixelSampler());
    }

    private void printRenderingFooter() {
        log.info("RayCaster: rendering done");
        log.info("-----------------------------------------------------------");
    }

}
