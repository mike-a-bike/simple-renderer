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

package ch.zweivelo.renderer.simple;

import ch.zweivelo.renderer.simple.app.ApplicationConfiguration;
import ch.zweivelo.renderer.simple.app.CommandlineParser;
import ch.zweivelo.renderer.simple.app.GlobalStatistics;
import ch.zweivelo.renderer.simple.app.SceneReader;
import ch.zweivelo.renderer.simple.core.Renderer;
import ch.zweivelo.renderer.simple.core.Scene;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;

import static java.lang.String.format;

/**
 * Spring boot application for starting up the renderer.
 *
 * @author <a href="mailto:m.bieri@gmx.net">Michael Bieri</a>
 * @version 0.1
 * @since 30.07.2015
 */

@Slf4j
@SpringBootApplication
public class SimpleRenderer {

    @Autowired
    private CommandlineParser commandlineParser;

    @Autowired
    private GlobalStatistics statistics;

    @Autowired
    private SceneReader sceneReader;

    @Autowired
    private Renderer renderer;

    /**
     * Bootstrap method for the Spring Boot application.
     * @param arguments The commandline arguments
     */
    public static void main(String[] arguments) {

        final ConfigurableApplicationContext applicationContext = SpringApplication.run(SimpleRenderer.class, arguments);

        final SimpleRenderer simpleRenderer = applicationContext.getBean(SimpleRenderer.class);

        simpleRenderer.run(arguments);

        log.info("SimpleRenderer finished");

    }

    /**
     * Application main method. Call this after the Spring Context is configured.
     * @param arguments The commandline arguments
     */
    private void run(String[] arguments) {

        statistics.setStart(Instant.now());

        try {

            final ApplicationConfiguration configuration = commandlineParser.parse(arguments).orElseThrow(() -> new RuntimeException("Unable to read configuration"));

            final Scene scene = sceneReader.getScene(configuration);

            renderer.render(configuration, scene);

        } catch (Throwable throwable) {

            log.error(format("Error during execution: %s%n", throwable.getMessage()), throwable);

        }

        final Duration executionDuration = Duration.between(statistics.getStart(), Instant.now());

        log.info("Execution took {}ms", executionDuration.getNano() / 1_000_000);

    }

}
