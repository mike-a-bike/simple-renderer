/*
 * Copyright 2015 Michael Bieri
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

package ch.zweivelo.renderer.simple.cameras;

import ch.zweivelo.renderer.simple.math.Ray;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.stream.Stream;

/**
 * Mathematical model of a perfect pinhole camera.
 *
 * @author <a href="mailto:m.bieri@gmx.net">Michael Bieri</a>
 * @version 0.1
 * @since 10.08.2015
 */
public class PinholeCamera implements Camera {

    private final Vector3D position;

    public PinholeCamera(final Vector3D position, final Vector3D up, final Vector3D lookat) {
        this.position = position;
    }

    @Override
    public Stream<Ray> createRaysFor(final Vector2D uvPoint) {
        Vector2D transformedUV = uvPoint.subtract(new Vector2D(.5d, .5d));
        return Stream.empty();
    }

}
