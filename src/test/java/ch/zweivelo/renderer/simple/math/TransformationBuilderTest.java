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
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Test for the affine transformation builder
 *
 * @author <a href="mailto:m.bieri@gmx.net">Michael Bieri</a>
 * @version 0.1
 * @since 13.06.2016
 */
public class TransformationBuilderTest {

    @Test
    public void scaleUVPoint() {

        Vector2D uvVector = new Vector2D(1, 1);
        Transformation scale2D = TransformationBuilder.scale(2, 3);
        final Vector2D transformedVector = scale2D.applyToUVPoint(uvVector);

        assertThat(transformedVector, is(not(nullValue())));
        assertThat(transformedVector.getX(), is(2d));
        assertThat(transformedVector.getY(), is(3d));

    }

    @Test
    public void scale3DVector() {

        Vector3D vector = new Vector3D(1, 1, 1);
        Transformation scale3D = TransformationBuilder.scale(2, 3, 4);
        final Vector3D transformedVector = scale3D.applyToVector(vector);

        assertThat(transformedVector, is(not(nullValue())));
        assertThat(transformedVector.getX(), is(2d));
        assertThat(transformedVector.getY(), is(3d));
        assertThat(transformedVector.getZ(), is(4d));

    }

    @Test
    public void translateUVPoint() {

        Vector2D uvVector = new Vector2D(1, 1);
        Transformation translate2D = TransformationBuilder.translate(1, 2);
        final Vector2D translatedVector = translate2D.applyToUVPoint(uvVector);

        assertThat(translatedVector, is(not(nullValue())));
        assertThat(translatedVector.getX(), is(2d));
        assertThat(translatedVector.getY(), is(3d));

    }

    @Test
    public void translate3DVector() {

        Vector3D vector = new Vector3D(1, 1, 1);
        final Transformation translate = TransformationBuilder.translate(1, 2, 3);
        final Vector3D translatedVector = translate.applyToVector(vector);

        // vectors are NOT translated
        assertThat(translatedVector, is(not(nullValue())));
        assertThat(translatedVector.getX(), is(1d));
        assertThat(translatedVector.getY(), is(1d));
        assertThat(translatedVector.getZ(), is(1d));

    }

    @Test
    public void translate3DPoint() {

        Vector3D point = new Vector3D(1, 1, 1);
        final Transformation translate = TransformationBuilder.translate(1, 2, 3);
        final Vector3D translatedPoint = translate.applyToPoint(point);

        // points ARE translated
        assertThat(translatedPoint, is(not(nullValue())));
        assertThat(translatedPoint.getX(), is(2d));
        assertThat(translatedPoint.getY(), is(3d));
        assertThat(translatedPoint.getZ(), is(4d));

    }

}