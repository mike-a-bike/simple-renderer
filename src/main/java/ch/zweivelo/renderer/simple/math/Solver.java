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

import org.apache.commons.math3.exception.InsufficientDataException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.util.LocalizedFormats;

import java.util.stream.Stream;

import static ch.zweivelo.renderer.simple.math.MathUtils.isZero;
import static org.apache.commons.math3.util.FastMath.PI;
import static org.apache.commons.math3.util.FastMath.acos;
import static org.apache.commons.math3.util.FastMath.cbrt;
import static org.apache.commons.math3.util.FastMath.cos;
import static org.apache.commons.math3.util.FastMath.sqrt;

/**
 * Implementation of different solvers.<br>
 * LINEAR: c[1]x + c[0] = 0<br>
 * QUADRATIC: c[2]x<sup>2</sup> + c[1]x + c[0] = 0<br>
 * CUBIC: c[3]x<sup>3</sup> + c[2]x<sup>2</sup> + c[1]x + c[0] = 0<br>
 * QUADRIC: c[4]x<sup>4</sup> + c[3]x<sup>3</sup> + c[2]x<sup>2</sup> + c[1]x + c[0] = 0
 *
 * @author <a href="mailto:m.bieri@gmx.net">Michael Bieri</a>
 * @version 0.1
 * @since 10.08.2015
 */
public enum Solver {

    LINEAR {
        /**
         * Linear solver for the equation: c[0] + c[1]x = 0
         * @param c Coefficients: c[0], c[1]
         */
        @Override
        public Stream<Double> solve(final double... c) {
            if (c.length != 2) {
                throw new InsufficientDataException();
            }
            if (c[1] == 0d) {
                throw new MathIllegalArgumentException(
                        LocalizedFormats.ZERO_DENOMINATOR_IN_FRACTION,
                        c[0],
                        c[1]);
            }

            return Stream.of(-c[0] / c[1]);
        }
    },

    QUADRATIC {
        /**
         * Quadratic solver for the equation: c[0] + c[1]x + c[2]x<sup>2</sup> = 0
         * @param c Coefficients: c[0], c[1], c[2]
         */
        @Override
        public Stream<Double> solve(final double... c) {
            switch (c.length) {
                case 3:
                    break;
                case 2:
                    return LINEAR.solve(c[0], c[1]);
                default:
                    throw new InsufficientDataException();
            }

            if (isZero(c[2])) {
                return LINEAR.solve(c[0], c[1]);
            }

            /* normal form: x^2 + px + q = 0 */
            double p = c[1] / (2 * c[2]);
            double q = c[0] / c[2];

            double D = p * p - q;

            if (isZero(D)) {
                return Stream.of(-p);
            } else if (D < 0) {
                return Stream.empty();
            }

            double sqrtD = sqrt(D);
            return Stream.of(sqrtD - p, -sqrtD - p).sorted(Double::compare);
        }
    },

    CUBIC {
        /**
         * Cubic solver for the equation: c[0] + c[1]x + c[2]x<sup>2</sup> + c[3]x<sup>3</sup>= 0
         * @param c Coefficients: c[0], c[1], c[2], c[3]
         */
        @Override
        public Stream<Double> solve(final double... c) {
            switch (c.length) {
                case 4:
                    break;
                case 3:
                    return QUADRATIC.solve(c[0], c[1], c[2]);
                default:
                    throw new InsufficientDataException();
            }

            if (isZero(c[3])) {
                return QUADRATIC.solve(c[0], c[1], c[2]);
            }

            Stream<Double> solutions;

            /* normal form: x^3 + Ax^2 + Bx + C = 0 */

            double A = c[2] / c[3];
            double B = c[1] / c[3];
            double C = c[0] / c[3];

            /* substitute x = y - A/3 to eliminate the quadratic term: x^3 + px +q = 0 */

            double squareA = A * A;
            double p = 1d / 3d * (-1d / 3d * squareA + B);
            double q = 1d / 2d * (2d / 27d * A * squareA - 1d / 3d * A * B + C);

            /* use Cardano's formula */
            double cubeP = p * p * p;
            double D = q * q + cubeP;

            if (isZero(D)) {

                if (isZero(q)) {
                    /* one triple solution */

                    solutions = Stream.of(0d);

                } else {
                    /* one single and one double solution */

                    double u = cbrt(-q);
                    solutions = Stream.of(2 * u, -u);

                }

            } else if (D < 0) {
                /* three real solutions */

                double phi = 1d / 3d * acos(-q / sqrt(-cubeP));
                double t = 2 * sqrt(-p);

                solutions = Stream.of(
                        t * cos(phi),
                        -t * cos(phi + PI / 3d),
                        -t * cos(phi - PI / 3d)
                );

            } else {
                /* one real solution */

                double sqrtD = sqrt(D);
                double u = cbrt(sqrtD - q);
                double v = -cbrt(sqrtD + q);

                solutions = Stream.of(u + v);
            }

            /* resubstitute */
            double sub = 1d / 3d * A;

            return solutions.map(solution -> solution - sub).sorted(Double::compare);
        }
    },

    QUARTIC {
        /**
         * Quartic solver for the equation: c[0] + c[1]x + c[2]x<sup>2</sup> + c[3]x<sup>3</sup> + c[4]x<sup>4</sup>= 0
         * @param c Coefficients: c[0], c[1], c[2], c[3], c[4]
         */
        @Override
        public Stream<Double> solve(final double... c) {
            switch (c.length) {
                case 5:
                    break;
                case 4:
                    return CUBIC.solve(c[0], c[1], c[2], c[3]);
                default:
                    throw new InsufficientDataException();
            }

            if (isZero(c[4])) {
                return CUBIC.solve(c[0], c[1], c[2], c[3]);
            }

            Stream<Double> solutions;

            /* normal form: x^4 + Ax^3 + Bx^2 + Cx + D = 0 */
            double A = c[3] / c[4];
            double B = c[2] / c[4];
            double C = c[1] / c[4];
            double D = c[0] / c[4];

            /* substitute x = y - A/4 to eliminate cubic term: x^4 + px^2 + qx + r = 0 */
            double squareA = A * A;
            double p = -3d / 8d * squareA + B;
            double q = 1d / 8d * squareA * A - 1d / 2d * A * B + C;
            double r = -3d / 256d * squareA * squareA + 1d / 16d * squareA * B - 1d / 4d * A * C + D;

            if (isZero(r)) {

                /* no absolute term: y(y^3 + py +q) = 0 */
                solutions = CUBIC.solve(q, p, 0, q);
                solutions = Stream.concat(solutions, Stream.of(0d));

            } else {

                /* solve the resolvent cubic ... */
                Stream<Double> cubicSolution = CUBIC.solve(
                        1d / 2d * r * p - 1d / 8d * q * q,
                        -r,
                        -1d / 2d * p,
                        1
                );

                /* ... and take the one real solution ... */
                double z = cubicSolution.findFirst().get();

                /* ... to build two quadric equations */
                double u = z * z - r;
                double v = 2 * z - p;

                if (isZero(u)) {
                    u = 0;
                } else if (u > 0d) {
                    u = sqrt(u);
                } else {
                    /* no real solutions */
                    return Stream.empty();
                }

                solutions = Stream.concat(
                        QUADRATIC.solve(
                                z - u,
                                q < 0 ? -v : v,
                                1
                        ),
                        QUADRATIC.solve(
                                z + u,
                                q < 0 ? v : -v,
                                1
                        )
                );

            }

            /* resubstitute */
            double sub = 1d / 4d * A;

            return solutions.map(solution -> solution - sub).sorted(Double::compare);
        }
    };

    /**
     * @param c Coefficients
     * @return A stream of real solutions
     */
    public abstract Stream<Double> solve(double... c);

}
