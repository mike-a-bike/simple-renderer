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
import org.junit.Before;
import org.junit.Test;

import static ch.zweivelo.renderer.simple.math.MathUtils.EPSILON;
import static ch.zweivelo.renderer.simple.math.MathUtils.LARGE_VALUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test some basic ray functions.
 *
 * @author <a href="mailto:m.bieri@gmx.net">Michael Bieri</a>
 * @version 0.1
 * @since 03.08.2015
 */
public class RayTest {

    Ray ray;

    @Before
    public void prepareTest() {
        ray = new Ray(Vector3D.ZERO, Vector3D.PLUS_I, DoubleRange.from(EPSILON, LARGE_VALUE));
    }

    @Test
    public void testIsValidT() throws Exception {
        assertTrue(ray.isValidT(EPSILON));
        assertTrue(ray.isValidT(1d));
        assertTrue(ray.isValidT(10d));
        assertTrue(ray.isValidT(100d));
        assertTrue(ray.isValidT(1000d));
        assertTrue(ray.isValidT(LARGE_VALUE));

        assertFalse(ray.isValidT(0d));
        assertFalse(ray.isValidT(-1d));
        assertFalse(ray.isValidT(-100000d));
        assertFalse(ray.isValidT(Double.NaN));
        assertFalse(ray.isValidT(Double.POSITIVE_INFINITY));
        assertFalse(ray.isValidT(Double.NEGATIVE_INFINITY));
    }

    @Test
    public void testCalculatePoint() throws Exception {
        assertEquals(Vector3D.PLUS_I, ray.calculatePoint(1d));
    }
}