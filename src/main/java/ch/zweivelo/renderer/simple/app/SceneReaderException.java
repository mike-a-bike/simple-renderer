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

/**
 * Signal an error from a scene reader
 *
 * @author Michael Bieri
 * @since 12.06.16
 */
public class SceneReaderException extends RuntimeException {
    public SceneReaderException() {
    }

    public SceneReaderException(String message) {
        super(message);
    }

    public SceneReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
