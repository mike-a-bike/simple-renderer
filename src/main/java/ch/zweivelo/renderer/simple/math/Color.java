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

import static org.apache.commons.math3.util.FastMath.max;
import static org.apache.commons.math3.util.FastMath.min;

/**
 * Simple color implementation using three components r, g and b.
 *
 * @author <a href="mailto:m.bieri@gmx.net">Michael Bieri</a>
 * @version 0.1
 * @since 10.08.2015
 */
public class Color {

    public static final Color BLACK = new Color(0d, 0d, 0d);
    public static final Color RED = new Color(1d, 0d, 0d);
    public static final Color GREEN = new Color(0d, 1d, 0d);
    public static final Color BLUE = new Color(0d, 0d, 1d);

    private final double r;
    private final double g;
    private final double b;

    public Color(final double r, final double g, final double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public double getR() {
        return r;
    }

    public double getG() {
        return g;
    }

    public double getB() {
        return b;
    }

    public Color add(Color other) {
        return new Color(r + other.r, g + other.g, b + other.b);
    }

    public Color scale(double factor) {
        return new Color(r * factor, g * factor, b * factor);
    }

    public Color clamp() {
        return new Color(min(0d, max(1d, r)), min(0d, max(1d, g)), min(0d, max(1d, b)));
    }
}
