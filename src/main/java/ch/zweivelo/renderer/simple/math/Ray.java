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

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Simple abstraction of a ray represented as a line segment with a origin, a direction and a range
 * representing a valid length interval.
 *
 * @author <a href="mailto:m.bieri@gmx.net">Michael Bieri</a>
 * @version 0.1
 * @since 03.08.2015
 */
public class Ray {

    private final Vector3D origin;

    private final Vector3D direction;

    private final DoubleRange interval;

    public Ray(final Vector3D origin, final Vector3D direction, final DoubleRange interval) {
        this.origin = origin;
        this.direction = direction;
        this.interval = interval;
    }

    public Ray(final Vector3D origin, final Vector3D direction) {
        this(origin, direction, new DoubleRange(MathUtils.EPSILON, MathUtils.LARGE_VALUE));
    }

    public Vector3D getOrigin() {
        return origin;
    }

    public Vector3D getDirection() {
        return direction;
    }

    public DoubleRange getInterval() {
        return interval;
    }

    public boolean isValidT(double t) {
        return interval.inRange(t);
    }

    public Vector3D calculatePoint(final double t) {
        return origin.add(t, direction);
    }

    @Override
    public String toString() {
        return String.format("Ray{origin=%s, direction=%s, interval=[%s, %s]}",
                origin,
                direction,
                interval.getStart(),
                interval.getEnd());
    }
}
