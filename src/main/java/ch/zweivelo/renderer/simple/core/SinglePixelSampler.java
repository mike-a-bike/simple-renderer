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

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.stream.Stream;

/**
 * Simple sampler. This creates no additional pixel, but uses the given point as new stream.
 *
 * @author Michael Bieri
 * @since 17.06.16
 */
public class SinglePixelSampler implements SubPixelSampler {

    @Override
    public Stream<Vector2D> sample(Vector2D pixel) {
        return Stream.of(pixel);
    }

    @Override
    public String toString() {
        return "SinglePixelSampler";
    }
}
