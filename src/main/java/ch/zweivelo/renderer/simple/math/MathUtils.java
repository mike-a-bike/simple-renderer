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

import static java.lang.Double.MAX_VALUE;
import static org.apache.commons.math3.util.FastMath.PI;
import static org.apache.commons.math3.util.FastMath.signum;

/**
 * Collection of useful constants.
 *
 * @author <a href="mailto:m.bieri@gmx.net">Michael Bieri</a>
 * @version 0.1
 * @since 03.08.2015
 */
public abstract class MathUtils {

    public static final double EPSILON = 1e-9d;

    private static final double SMALL_ESPILON = EPSILON / 10d;

    static final double LARGE_VALUE = MAX_VALUE;

    public static final double TWO_PI = PI * 2d;

    public static final double INV_PI = 1d / PI;

    private MathUtils() {
    }

    /**
     * Check if value is zero with a tolerance of {@value #SMALL_ESPILON}
     *
     * @param value The value to check
     * @return true if {@value #SMALL_ESPILON} <= value <= {@value #SMALL_ESPILON}, false otherwise
     */
    static boolean isZero(final double value) {
        return -SMALL_ESPILON <= value && value <= SMALL_ESPILON;
    }

    /**
     * Safe division of two numerical values. If {@code b} is zero, then {@link #LARGE_VALUE} is returned.
     * The signs are maintained. This method never throws a dividing by zero exception. Since this application
     * solves approximated numerical problems, this is preferred over the division by zero error.
     *
     * @param a The numerator
     * @param b The denominator
     * @return a / b if b is not zero, {@link #LARGE_VALUE} otherwise.
     */
    static double div(final double a, final double b) {
        if (a == 0) {
            return 0;
        }
        if (b == 0) {
            return LARGE_VALUE * signum(a);
        }
        if ((a + b) == a) {
            return LARGE_VALUE * signum(a) * signum(b);
        }

        return a / b;
    }
}
