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

import com.maehem.javamancer.neuro.model.GameState;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class SmallPopupPane extends PopupPane {

    private static final int WIDTH = 360;
    private static final int HEIGHT = 130;
    private static final int X = 116;
    private static final int Y = 256;

    public SmallPopupPane(PopupListener l, GameState gs) {
        this(l, gs, WIDTH);
    }

    public SmallPopupPane(PopupListener l, GameState gs, int width) {
        this(l, gs, width, HEIGHT, X, Y);
    }

    public SmallPopupPane(PopupListener l, GameState gs, int width, int height, int x, int y) {
        super(l, gs);
        setPrefSize(width, height);
        setMinSize(width, height);
        setMaxSize(width, height);
        setLayoutX(x);
        setLayoutY(y);
        setId("neuro-popup");
    }

    protected VBox addBox(Node... nodes) {
        VBox box = new VBox(nodes);
        box.setSpacing(0);
        box.getTransforms().add(new Scale(1.33, 1.0));
        box.setMinWidth(getPrefWidth());
        box.setPrefWidth(getPrefWidth());
        box.setMinHeight(getPrefHeight());
        box.setMaxHeight(getPrefWidth());
        box.setPadding(new Insets(0, 0, 0, 10));

        getChildren().add(box);

        return box;
    }

}
