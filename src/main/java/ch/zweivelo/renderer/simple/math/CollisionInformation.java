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

package ch.zweivelo.renderer.simple.math;

import ch.zweivelo.renderer.simple.shapes.Shape;

import lombok.Value;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Information about a collision between a Ray and a Shape
 *
 * @author <a href="mailto:m.bieri@gmx.net">Michael Bieri</a>
 * @version 0.1
 * @since 03.08.2015
 */

@Value(staticConstructor = "from")
public class CollisionInformation {

    private final double distance;
    private final Shape shape;
    private final Vector3D point;
    private final Color color;

}
