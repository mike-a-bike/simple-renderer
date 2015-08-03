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

import ch.zweivelo.renderer.simple.math.DoubleRange;
import ch.zweivelo.renderer.simple.math.MathUtils;
import ch.zweivelo.renderer.simple.math.Ray;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Test the calculations of the intersection distances for a plane and a ray.
 *
 * @author <a href="mailto:m.bieri@gmx.net">Michael Bieri</a>
 * @version 0.1
 * @since 03.08.2015
 */
public class PlaneTest {

    @Test
    public void testCalculateIntersectionDistance() throws Exception {
        Plane p = new Plane(Vector3D.ZERO, Vector3D.PLUS_J);
        Ray r = new Ray(Vector3D.PLUS_J, Vector3D.MINUS_J);

        Optional<Double> doubleOptional = p.calculateIntersectionDistance(r);

        assertNotNull(doubleOptional);
        assertTrue(doubleOptional.isPresent());
        assertEquals(1d, doubleOptional.get(), MathUtils.EPSILON);
    }

}