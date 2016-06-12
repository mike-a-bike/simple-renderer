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
import ch.zweivelo.renderer.simple.app.SceneReaderException;
import ch.zweivelo.renderer.simple.core.Scene;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static java.lang.String.format;

/**
 * Spring boot application for starting up the renderer.
 *
 * @author <a href="mailto:m.bieri@gmx.net">Michael Bieri</a>
 * @version 0.1
 * @since 30.07.2015
 */
@SpringBootApplication
public class SimpleRenderer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleRenderer.class);

    @Autowired
    private CommandlineParser commandlineParser;

    @Autowired
    private GlobalStatistics statistics;

    @Autowired
    private SceneReader sceneReader;

    /**
     * Bootstrap method for the Spring Boot application.
     * @param arguments The commandline arguments
     */
    public static void main(String[] arguments) {

        final ConfigurableApplicationContext applicationContext = SpringApplication.run(SimpleRenderer.class, arguments);

        final SimpleRenderer simpleRenderer = applicationContext.getBean(SimpleRenderer.class);

        simpleRenderer.run(arguments);

    }

    /**
     * Application main method. Call this after the Spring Context is configured.
     * @param arguments The commandline arguments
     */
    private void run(String[] arguments) {

        statistics.setStart(Instant.now());

        LOGGER.info("Starting SimpleRenderer");

        try {

            final Optional<ApplicationConfiguration> configuration = commandlineParser.parse(arguments);

            final Scene scene = configuration.map(sceneReader::getScene).orElseThrow(() -> new SceneReaderException("Unable to read scene."));

            LOGGER.info("Rendering {}", scene);

        } catch (Exception exception) {
            LOGGER.error(format("Error during execution: %s%n", exception.getMessage()), exception);
        }

        LOGGER.info("SimpleRenderer finished");

        final Duration executionDuration = Duration.between(statistics.getStart(), Instant.now());

        LOGGER.info("Execution took {}ms", executionDuration.getNano() / 1_000_000);

    }

}
