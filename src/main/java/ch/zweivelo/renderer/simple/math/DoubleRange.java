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

import org.apache.commons.math3.util.Pair;

/**
 * Represent a value range.
 *
 * @author <a href="mailto:m.bieri@gmx.net">Michael Bieri</a>
 * @version 0.1
 * @since 03.08.2015
 */
public class DoubleRange {

    private final double start;
    private final double end;

    public DoubleRange(double start, double end) {
        this.start = start;
        this.end = end;
    }

    public boolean inRange(double value) {
        return start <= value && value <= end;
    }

    public double getStart() {
        return start;
    }

    public double getEnd() {
        return end;
    }
}
