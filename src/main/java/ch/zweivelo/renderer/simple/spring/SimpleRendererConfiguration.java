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

package ch.zweivelo.renderer.simple.spring;

import ch.zweivelo.renderer.simple.app.CommandlineParser;
import ch.zweivelo.renderer.simple.app.GlobalStatistics;
import ch.zweivelo.renderer.simple.app.SceneReader;
import ch.zweivelo.renderer.simple.app.YamlSceneReader;
import ch.zweivelo.renderer.simple.core.RayCaster;
import ch.zweivelo.renderer.simple.core.Renderer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring boot configuration for the simple renderer project.
 *
 * @author <a href="mailto:m.bieri@gmx.net">Michael Bieri</a>
 * @version 0.1
 * @since 30.07.2015
 */
@Configuration
public class SimpleRendererConfiguration {

    @Bean
    public CommandlineParser commandlineParser() {
        return new CommandlineParser();
    }

    @Bean
    public GlobalStatistics globalStatistics() {
        return new GlobalStatistics();
    }

    @Bean
    public SceneReader sceneReader() {
        return new YamlSceneReader();
    }

    @Bean
    public Renderer renderer() {
        return new RayCaster();
    }

}
