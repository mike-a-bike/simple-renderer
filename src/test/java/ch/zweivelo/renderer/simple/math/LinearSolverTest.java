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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.stream.DoubleStream;

import static ch.zweivelo.renderer.simple.math.MathUtils.EPSILON;
import static ch.zweivelo.renderer.simple.math.Solver.LINEAR;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test for linear solver
 *
 * @author <a href="mailto:m.bieri@gmx.net">Michael Bieri</a>
 * @version 0.1
 * @since 10.08.2015
 */
public class LinearSolverTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testNotEnoughCoefficients() throws Exception {
        expectedException.expect(InsufficientDataException.class);
        LINEAR.solve(1d);
    }

    @Test
    public void testZeroAsC1() throws Exception {
        expectedException.expect(MathIllegalArgumentException.class);
        LINEAR.solve(1d, 0d);
    }

    @Test
    public void testSolveLinear() throws Exception {
        DoubleStream solutions = LINEAR.solve(2d, 1d);
        assertNotNull(solutions);
        List<Double> solutionsList = solutions.mapToObj(d -> d).collect(toList());
        assertEquals(1l, solutionsList.size());
        assertEquals(solutionsList.get(0), -2d, EPSILON);
    }
}