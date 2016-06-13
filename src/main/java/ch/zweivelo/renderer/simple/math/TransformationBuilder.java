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

package ch.zweivelo.renderer.simple.math;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

/**
 * Builder for affine transformations.
 *
 * @author <a href="mailto:m.bieri@gmx.net">Michael Bieri</a>
 * @version 0.1
 * @since 13.06.2016
 */
public class TransformationBuilder {

    public static Transformation scale(double su, double sv) {
        return scale(su, sv, 1);
    }

    public static Transformation scale(double sx, double sy, double sz) {
        RealMatrix scaleMatrix = new Array2DRowRealMatrix(new double[][]{
            {sx, 0, 0, 0},
            {0, sy, 0, 0},
            {0, 0, sz, 0},
            {0, 0, 0, 1}
        }, false);

        final Array2DRowRealMatrix inverseScaleMatrix = new Array2DRowRealMatrix(new double[][]{
            {1d / sx, 0, 0, 0},
            {0, 1d / sy, 0, 0},
            {0, 0, 1d / sz, 0},
            {0, 0, 0, 1}
        }, false);

        return new MatrixTransformation(scaleMatrix, inverseScaleMatrix);
    }

    public static Transformation rotateX(double angle) {
        final double radAngle = toRadians(angle);
        final Array2DRowRealMatrix rotationMatrix = new Array2DRowRealMatrix(new double[][]{
            {1, 0, 0, 0},
            {0, cos(radAngle), -sin(radAngle), 0},
            {0, sin(radAngle), cos(radAngle), 0},
            {0, 0, 0, 1}
        }, false);

        return new MatrixTransformation(rotationMatrix, rotationMatrix.transpose());
    }

    public static Transformation rotateY(double angle) {
        final double radAngle = toRadians(angle);
        final Array2DRowRealMatrix rotationMatrix = new Array2DRowRealMatrix(new double[][]{
            {cos(radAngle), 0, sin(radAngle), 0},
            {0, 1, 0, 0},
            {-sin(radAngle), 0, cos(radAngle), 0},
            {0, 0, 0, 1}
        }, false);

        return new MatrixTransformation(rotationMatrix, rotationMatrix.transpose());
    }

    public static Transformation rotateZ(double angle) {
        final double radAngle = toRadians(angle);
        final Array2DRowRealMatrix rotationMatrix = new Array2DRowRealMatrix(new double[][]{
            {cos(radAngle), -sin(radAngle), 0, 0},
            {sin(radAngle), cos(radAngle), 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
        }, false);

        return new MatrixTransformation(rotationMatrix, rotationMatrix.transpose());
    }

    public Transformation rotate(double angle, Vector3D axis) {
        final double radAngle = toRadians(angle);
        final Vector3D normalizedAxis = axis.normalize();
        final double x = normalizedAxis.getX();
        final double y = normalizedAxis.getY();
        final double z = normalizedAxis.getZ();
        final double sin = sin(radAngle);
        final double cos = cos(radAngle);
        final double[][] rotationMatrix = new double[4][4];

        rotationMatrix[0][0] = x * x + (1d - x * x) * cos;
        rotationMatrix[0][1] = x * y * (1d - cos) - z * sin;
        rotationMatrix[0][2] = x * z * (1d - cos) + y * sin;
        rotationMatrix[0][3] = 0;

        rotationMatrix[1][0] = y * x * (1d - cos) + z * sin;
        rotationMatrix[1][1] = y * y + (1d - y * y) * cos;
        rotationMatrix[1][2] = y * z * (1d - cos) - x * sin;
        rotationMatrix[1][3] = 0;

        rotationMatrix[2][0] = z * x * (1d - cos) - y * sin;
        rotationMatrix[2][1] = z * y * (1d - cos) + x * sin;
        rotationMatrix[2][2] = z * z + (1d - z * z) * cos;
        rotationMatrix[2][3] = 0;

        rotationMatrix[3][0] = 0;
        rotationMatrix[3][1] = 0;
        rotationMatrix[3][2] = 0;
        rotationMatrix[3][3] = 1;

        final Array2DRowRealMatrix transformationMatrix = new Array2DRowRealMatrix(rotationMatrix, false);
        return new MatrixTransformation(transformationMatrix, transformationMatrix.transpose());

    }

    public static Transformation translate(double du, double dv) {
        return translate(du, dv, 0d);
    }

    public static Transformation translate(double dx, double dy, double dz) {
        RealMatrix translationMatrix = new Array2DRowRealMatrix(new double[][]{
            {1, 0, 0, dx},
            {0, 1, 0, dy},
            {0, 0, 1, dz},
            {0, 0, 0, 1}
        }, false);

        final Array2DRowRealMatrix inverseTranslationMatrix = new Array2DRowRealMatrix(new double[][]{
            {1, 0, 0, -dx},
            {0, 1, 0, -dy},
            {0, 0, 1, -dz},
            {0, 0, 0, 1}
        }, false);

        return new MatrixTransformation(translationMatrix, inverseTranslationMatrix);
    }

    public static Transformation lookAt(Vector3D eye, Vector3D lookAt, Vector3D up) {
        final Vector3D direction = lookAt.subtract(eye).normalize();
        final Vector3D right = up.crossProduct(direction).normalize();
        final Vector3D newUp = direction.crossProduct(right);

        final Array2DRowRealMatrix cameraToWorldMatrix = new Array2DRowRealMatrix(new double[][]{
            {right.getX(), newUp.getX(), direction.getX(), eye.getX()},
            {right.getY(), newUp.getY(), direction.getY(), eye.getY()},
            {right.getZ(), newUp.getZ(), direction.getZ(), eye.getZ()},
            {0, 0, 0, 1}
        }, false);

        final RealMatrix lookAtMatrix = new LUDecomposition(cameraToWorldMatrix).getSolver().getInverse();

        return new MatrixTransformation(lookAtMatrix, cameraToWorldMatrix);

    }

    private static class MatrixTransformation implements Transformation {

        private final RealMatrix transformationMatrix;
        private final RealMatrix inverseTransformationMatrix;
        private Transformation inverseTransformation;

        MatrixTransformation(RealMatrix transformationMatrix) {
            this(transformationMatrix, new LUDecomposition(transformationMatrix).getSolver().getInverse());
        }

        MatrixTransformation(final RealMatrix transformationMatrix, final RealMatrix inverseTransformationMatrix) {
            this.transformationMatrix = transformationMatrix;
            this.inverseTransformationMatrix = inverseTransformationMatrix;
        }

        @Override
        public Transformation getInverse() {
            calculateInverse();
            return inverseTransformation;
        }

        @Override
        public Transformation andThen(final Transformation append) {
            if (MatrixTransformation.class.isAssignableFrom(append.getClass())) {
                final MatrixTransformation matrixTransformation = MatrixTransformation.class.cast(append);
                return new MatrixTransformation(transformationMatrix.multiply(matrixTransformation.transformationMatrix));
            }
            throw new IllegalArgumentException("Cannot append transformation: " + append.toString());
        }

        @Override
        public Vector2D applyToUVPoint(final Vector2D uvPoint) {
            RealMatrix columnVector = new Array2DRowRealMatrix(new double[]{uvPoint.getX(), uvPoint.getY(), 0d, 1d});
            final RealMatrix transformedVector = transformationMatrix.multiply(columnVector);
            return new Vector2D(transformedVector.getEntry(0, 0), transformedVector.getEntry(1, 0));
        }

        @Override
        public Vector3D applyToPoint(final Vector3D point) {
            RealMatrix homogeneousPoint = new Array2DRowRealMatrix(new double[]{point.getX(), point.getY(), point.getZ(), 1d});
            final RealMatrix transformedPoint = transformationMatrix.multiply(homogeneousPoint);
            final RealMatrix scalarMultiply = transformedPoint.scalarMultiply(1d / transformedPoint.getEntry(3, 0));
            return createVector3DFromColumnMatrix(scalarMultiply);
        }

        @Override
        public Vector3D applyToNormal(final Vector3D normal) {
            final Array2DRowRealMatrix homogeneousNormal = new Array2DRowRealMatrix(new double[]{normal.getX(), normal.getY(), normal.getZ(), 0d});
            final RealMatrix transformedNormal = inverseTransformationMatrix.transpose().multiply(homogeneousNormal);
            return createVector3DFromColumnMatrix(transformedNormal);
        }

        @Override
        public Vector3D applyToVector(final Vector3D vector) {
            RealMatrix columnVector = new Array2DRowRealMatrix(new double[]{vector.getX(), vector.getY(), vector.getZ(), 0d});
            final RealMatrix transformedVector = transformationMatrix.multiply(columnVector);
            return createVector3DFromColumnMatrix(transformedVector);
        }

        private void calculateInverse() {
            if (inverseTransformation == null) {
                inverseTransformation = new MatrixTransformation(inverseTransformationMatrix);
            }
        }

        private Vector3D createVector3DFromColumnMatrix(final RealMatrix columnMatrix) {
            return new Vector3D(
                columnMatrix.getEntry(0, 0),
                columnMatrix.getEntry(1, 0),
                columnMatrix.getEntry(2, 0)
            );
        }

    }

}
