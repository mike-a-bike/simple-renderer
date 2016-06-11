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

package ch.zweivelo.renderer.simple.shapes;

import ch.zweivelo.renderer.simple.math.Color;
import ch.zweivelo.renderer.simple.math.Ray;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.Optional;

/**
 * Representation of a plane. The plane is defined by a origin and normal.
 *
 * @author <a href="mailto:m.bieri@gmx.net">Michael Bieri</a>
 * @version 0.1
 * @since 03.08.2015
 */
public class Plane extends AbstractShape {

    private final Vector3D origin;
    private final Vector3D normal;

    public Plane(final Vector3D origin, final Vector3D normal, Color color) {
        super(color);
        this.origin = origin;
        this.normal = normal.normalize();
    }

    @Override
    public Optional<Double> calculateIntersectionDistance(final Ray ray) {
        double numerator = origin.subtract(ray.getOrigin()).dotProduct(normal);
        double denominator = ray.getDirection().dotProduct(normal);

        // parallel to the plane
        if (denominator == 0d) {
            // outside the plane -> not intersection
            if (numerator != 0d) {
                return Optional.empty();
            }
            // within the plane. take the closest intersection possible
            return Optional.of(ray.getInterval().getStart());
        }

        double distance = numerator / denominator;
        return ray.isValidT(distance) ? Optional.of(distance) : Optional.empty();
    }

    @Override
    public String toString() {
        return String.format("Plane{origin=%s, normal=%s}", origin, normal);
    }
}
