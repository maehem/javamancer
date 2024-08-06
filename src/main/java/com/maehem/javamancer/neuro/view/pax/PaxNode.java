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
package com.maehem.javamancer.neuro.view.pax;

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.GameState;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class PaxNode extends Pane {

    public static final Logger LOGGER = Logging.LOGGER;
    protected final GameState gameState;
    public final PaxNodeListener listener;
    protected static final double TEXT_SCALE = 1.5;
    protected static final double LINE_SPACING = -7;

    public PaxNode(PaxNodeListener listener, GameState gs) {
        this.listener = listener;
        this.gameState = gs;
    }

    public abstract boolean handleEvent(KeyEvent ke);

    protected VBox addBox(Node... nodes) {
        VBox box = new VBox(nodes);
        box.setSpacing(0);
        box.getTransforms().add(new Scale(TEXT_SCALE, 1.0));
        box.setMinWidth(getPrefWidth());
        box.setPrefWidth(getPrefWidth());
        box.setMinHeight(getPrefHeight());
        box.setMaxHeight(getPrefWidth());
        box.setPadding(new Insets(10, 0, 0, 20));

        getChildren().add(box);

        return box;
    }
}
