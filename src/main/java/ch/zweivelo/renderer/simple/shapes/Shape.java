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

import ch.zweivelo.renderer.simple.math.CollisionInformation;
import ch.zweivelo.renderer.simple.math.Ray;

import java.util.Optional;

/**
 * Generic interface representing the methods of a shape
 *
 * @author <a href="mailto:m.bieri@gmx.net">Michael Bieri</a>
 * @version 0.1
 * @since 03.08.2015
 */
public interface Shape {

    Optional<Double> calculateIntersectionDistance(Ray ray);

    default Optional<CollisionInformation> intersect(final Ray ray) {
        return calculateIntersectionDistance(ray).map(
                t -> new CollisionInformation(
                        ray.calculatePoint(t)
                )
        );
    }
}
