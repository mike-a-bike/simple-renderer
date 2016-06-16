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

package ch.zweivelo.renderer.simple.math.sampler;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.stream.Stream;

/**
 * Simple implementation: Just returns the midpoint of the requested samples.
 *
 * @author Michael Bieri
 * @since 17.06.16
 */
public class SingleValueSampler implements Sampler {

    private static final Vector2D UNIT_SQUARE_MID_POINT = new Vector2D(.5d, .5d);

    @Override
    public Stream<Vector2D> unitSquare() {
        return Stream.of(UNIT_SQUARE_MID_POINT);
    }

    @Override
    public Stream<Vector2D> unitCircle() {
        return Stream.of(Vector2D.ZERO);
    }

    @Override
    public Stream<Vector3D> unitHemisphere() {
        return Stream.of(Vector3D.PLUS_J);
    }

    @Override
    public Stream<Vector3D> unitSphere() {
        throw new RuntimeException("There is no single representative sample for a unit sphere");
    }
}
