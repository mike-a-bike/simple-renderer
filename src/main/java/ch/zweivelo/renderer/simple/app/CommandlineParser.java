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

package ch.zweivelo.renderer.simple.app;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Optional;

/**
 * Encapsulation for parsing a commandline
 *
 * @author Michael Bieri
 * @since 12.06.16
 */

@Slf4j
public class CommandlineParser {

    private static final String DEFAULT_IMAGE_NAME = "image.png";
    private static final String DEFAULT_FORMAT = "PNG";
    private static final String DEFAULT_WIDTH = "640";
    private static final String DEFAULT_HEIGHT = "480";

    public Optional<ApplicationConfiguration> parse(String[] arguments) {

        return parseCommandLine(arguments).map(commandLine -> {
            final String sceneName = commandLine.getOptionValue("s");
            final String imageName = commandLine.getOptionValue("o", DEFAULT_IMAGE_NAME);
            final String format = commandLine.getOptionValue("f", DEFAULT_FORMAT);
            final int width = Integer.parseInt(commandLine.getOptionValue("w", DEFAULT_WIDTH));
            final int height = Integer.parseInt(commandLine.getOptionValue("h", DEFAULT_HEIGHT));
            return ApplicationConfiguration.from(sceneName, width, height, imageName, format);
        });

    }

    private Optional<CommandLine> parseCommandLine(String[] arguments) {
        Options options = createCommandlineOptions();
        DefaultParser parser = new DefaultParser();
        try {
            return Optional.of(parser.parse(options, arguments));
        } catch (ParseException e) {
            log.error(e.getMessage());
            final HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp(999, "java -jar simple-renderer-<version>.jar", null, options, null, true);
        }
        return Optional.empty();
    }

    private Options createCommandlineOptions() {
        final Options options = new Options();

        options.addOption(Option.builder("s").longOpt("scene").hasArg().argName("scene file").required().build());
        options.addOption(Option.builder("w").longOpt("width").hasArg().argName("width in pixel").required(false).build());
        options.addOption(Option.builder("h").longOpt("height").hasArg().argName("height in pixel").required(false).build());
        options.addOption(Option.builder("o").longOpt("image").hasArg().argName("output file").required(false).build());
        options.addOption(Option.builder("f").longOpt("format").hasArg().argName("format: PNG,JPG,EXR").required(false).build());

        return options;
    }


}
