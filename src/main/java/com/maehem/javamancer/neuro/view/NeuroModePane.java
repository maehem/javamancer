/*
 * MIT License
 *
 * Copyright (c) 2024 Mark J. Koch ( @maehem on GitHub )
 *
 * Portions of this software are Copyright (c) 2018 Henadzi Matuts and are
 * derived from their project: https://github.com/HenadziMatuts/Reuromancer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.maehem.javamancer.neuro.view;

import com.maehem.javamancer.logging.Logging;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class NeuroModePane extends Pane {

    public static final Logger LOGGER = Logging.LOGGER;
    public static final double BUTTON_FONT_SIZE = 24;
    public static final Font VT_FONT = Font.loadFont(TitleMode.class.getResourceAsStream("/fonts/VT323-Regular.ttf"), BUTTON_FONT_SIZE);

    private final NeuroModePaneListener listener;
    private final ResourceManager resourceManager;

    public NeuroModePane(NeuroModePaneListener listener, ResourceManager resourceManager) {
        this.listener = listener;
        this.resourceManager = resourceManager;

        getStylesheets().add(
                getClass().getResource("/style/neuro.css").toExternalForm());

        setOnMouseEntered((t) -> {
            initCursor();
        });

    }

    protected NeuroModePaneListener getListener() {
        return listener;
    }

    protected ResourceManager getResourceManager() {
        return resourceManager;
    }

    public void initCursor() {
        Platform.runLater(() -> {
            double scale = NeuroGamePane.RESOURCE_SCALE * getBoundsInLocal().getWidth() / NeuroGamePane.WIDTH;
            Image cursorImage = getResourceManager().getSprite("CURSORS_1", scale);
            getParent().setCursor(new ImageCursor(cursorImage,
                    2,
                    1));
        });
    }

}
