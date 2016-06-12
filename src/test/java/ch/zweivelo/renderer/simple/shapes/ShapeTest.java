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
import ch.zweivelo.renderer.simple.math.Ray;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.when;

/**
 * Test the default implementation of the Shape#intersect method.
 *
 * @author <a href="mailto:m.bieri@gmx.net">Michael Bieri</a>
 * @version 0.1
 * @since 03.08.2015
 */
@RunWith(MockitoJUnitRunner.class)
public class ShapeTest {

    @Mock
    Ray ray;

    @Before
    public void prepareRay() {
        when(ray.calculatePoint(anyDouble())).thenReturn(Vector3D.PLUS_I);
    }

    @Test
    public void testIntersect() throws Exception {
        Shape testShape;
        Optional<CollisionInformation> collisionInformationOptional;

        testShape = ray1 -> Optional.empty();
        collisionInformationOptional = testShape.intersect(ray);
        assertNotNull(collisionInformationOptional);
        assertFalse(collisionInformationOptional.isPresent());

        testShape = ray1 -> Optional.of(1d);
        collisionInformationOptional = testShape.intersect(ray);
        assertNotNull(collisionInformationOptional);
        CollisionInformation collisionInformation = collisionInformationOptional.get();
        assertNotNull(collisionInformation.getShape());
        assertEquals(Vector3D.PLUS_I, collisionInformation.getPoint());
    }

}