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

package ch.zweivelo.renderer.simple.math.sampler;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Test for the single value sampler implementation
 *
 * @author Michael Bieri
 * @since 17.06.16
 */
public class SingleValueSamplerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Sampler sampler = new SingleValueSampler();

    @Test
    public void testUnitSquareSamples() throws Exception {

        final Stream<Vector2D> samplesStream = sampler.unitSquare();

        validateStreamContent(samplesStream, new Vector2D(.5d, .5d));
    }

    @Test
    public void testUnitCircleSamples() throws Exception {

        final Stream<Vector2D> samplesStream = sampler.unitCircle();

        validateStreamContent(samplesStream, Vector2D.ZERO);

    }

    @Test
    public void testUnitHemisphereSamples() throws Exception {

        final Stream<Vector3D> samplesStream = sampler.unitHemisphere();

        validateStreamContent(samplesStream, Vector3D.PLUS_J);

    }

    @Test
    public void testUnitSphereException() throws Exception {

        expectedException.expect(RuntimeException.class);

        sampler.unitSphere();

    }

    private <T> void validateStreamContent(Stream<T> samplesStream, T referenceSample) {
        assertThat(samplesStream, is(not(nullValue())));

        final List<T> samples = samplesStream.collect(Collectors.toList());

        assertThat(samples, is(not(nullValue())));
        assertThat(samples.size(), is(1));
        assertThat(samples.get(0), is(equalTo(referenceSample)));
    }
}