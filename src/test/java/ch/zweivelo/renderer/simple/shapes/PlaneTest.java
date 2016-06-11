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
import ch.zweivelo.renderer.simple.math.Color;
import ch.zweivelo.renderer.simple.math.Ray;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static ch.zweivelo.renderer.simple.math.MathUtils.EPSILON;
import static org.apache.commons.math3.geometry.euclidean.threed.Vector3D.MINUS_J;
import static org.apache.commons.math3.geometry.euclidean.threed.Vector3D.PLUS_J;
import static org.apache.commons.math3.geometry.euclidean.threed.Vector3D.ZERO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test the calculations of the intersection distances for a plane and a ray.
 *
 * @author <a href="mailto:m.bieri@gmx.net">Michael Bieri</a>
 * @version 0.1
 * @since 03.08.2015
 */
public class PlaneTest {

    private Plane plane;
    private Ray ray;

    @Before
    public void setUp() throws Exception {
        plane = new Plane(ZERO, PLUS_J, Color.BLUE);
        ray = new Ray(PLUS_J, MINUS_J);
    }

    @Test
    public void testCalculateIntersectionDistance() throws Exception {

        Optional<Double> doubleOptional = plane.calculateIntersectionDistance(ray);

        assertNotNull(doubleOptional);
        assertTrue(doubleOptional.isPresent());
        assertEquals(1d, doubleOptional.get(), EPSILON);
    }

    @Test
    public void testIntersect() throws Exception {

        Optional<CollisionInformation> collisionInformationOptional = plane.intersect(ray);

        CollisionInformation collisionInformation = collisionInformationOptional.get();
        assertNotNull(collisionInformation.getShape());
        assertTrue(collisionInformation.getShape() instanceof Plane);
    }
}