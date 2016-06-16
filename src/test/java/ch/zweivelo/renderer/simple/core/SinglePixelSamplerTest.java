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

package ch.zweivelo.renderer.simple.core;

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
 * Test for the single pixel sampler
 *
 * @author Michael Bieri
 * @since 17.06.16
 */
public class SinglePixelSamplerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private SubPixelSampler sampler = new SinglePixelSampler();

    @Test
    public void originSample() throws Exception {
        final Stream<Vector2D> samplesStream = sampler.sample(Vector2D.ZERO);

        assertThat(samplesStream, is(not(nullValue())));

        final List<Vector2D> samples = samplesStream.collect(Collectors.toList());

        assertThat(samples, is(not(nullValue())));
        assertThat(samples.size(), is(1));
        assertThat(samples.get(0), is(equalTo(new Vector2D(.5d, .5d))));

    }

    @Test
    public void throwNullPointerException() throws Exception {

        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("pixel");

        sampler.sample(null);

    }
}