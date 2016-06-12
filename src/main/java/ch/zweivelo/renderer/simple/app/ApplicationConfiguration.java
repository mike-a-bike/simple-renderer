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

import ch.zweivelo.renderer.simple.core.Scene;

/**
 * Settings object containing the result of the commandline parsing.
 *
 * @author Michael Bieri
 * @since 12.06.16
 */
public class ApplicationConfiguration {

    private final String sceneName;
    private final String imageFileName;
    private final String format;

    private Scene scene;

    public ApplicationConfiguration(String sceneName, String imageFileName, String format) {
        this.sceneName = sceneName;
        this.imageFileName = imageFileName;
        this.format = format;
    }

    public String getSceneName() {
        return sceneName;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public String getFormat() {
        return format;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}