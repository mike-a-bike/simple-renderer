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

package ch.zweivelo.renderer.simple.lights;

import ch.zweivelo.renderer.simple.math.Color;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Simple point light source. This light has no physical size and all the light originates from a infinitesimal
 * small volume. Since there is no size, shadows have a clearly defined border and there is no fading of the shadows.
 *
 * @author <a href="mailto:m.bieri@gmx.net">Michael Bieri</a>
 * @version 0.1
 * @since 13.06.2016
 */

@Getter
@AllArgsConstructor
public class Pointlight implements Light {

    private final Vector3D position;
    private final Color color;
    private final double intensity;

    @Override
    public boolean isDelta() {
        return true;
    }

}
