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

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

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

    public static void main(String[] arguments) {

        SpringApplication.run(SimpleRenderer.class, arguments);

        LOGGER.info("Starting SimpleRenderer");

        CommandLine commandLine = pareseCommandLine(arguments);

        if (commandLine != null) {

            String sceneFileName = commandLine.getOptionValue("s");
            LOGGER.info("Loading scene: " + sceneFileName);

            Yaml yaml = new Yaml();
            try (InputStream yamlStream = FileUtils.openInputStream(new File(sceneFileName))) {

                final Map<String, Object> scene = (Map<String, Object>) yaml.load(yamlStream);

                LOGGER.debug("Loaded scene: " + scene);

            } catch (IOException exeption) {
                LOGGER.error("Error reading scene file: " + exeption.getMessage(), exeption);
            }
        }

        LOGGER.info("SimpleRenderer finished");
    }

    private static CommandLine pareseCommandLine(String[] arguments) {
        Options options = createCommandlineOptions();
        DefaultParser parser = new DefaultParser();
        try {
            return parser.parse(options, arguments);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
            final HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp(999, "java -jar SimpleRendere.jar", null, options, null, true);
        }
        return null;
    }

    private static Options createCommandlineOptions() {
        final Options options = new Options();

        options.addOption(Option.builder("s").longOpt("scene").hasArg().argName("scene file").required().build());

        return options;
    }

}
