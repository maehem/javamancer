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
package com.maehem.javamancer.neuro.view.cyberspace;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class DamageGauge extends Pane {

    private static final double WIDTH = 16;
    private static final double HEIGHT = 76;

    public enum Orientation {
        HORIZ, VERT
    }

    private Rectangle green = new Rectangle(WIDTH, HEIGHT / 2);
    private Rectangle yellow = new Rectangle(WIDTH, HEIGHT * 0.75);
    private Rectangle red = new Rectangle(WIDTH, HEIGHT);

    public DamageGauge(Orientation orientation) {
        //this.orientation = orientation;

        setMinWidth(WIDTH);
        setMinHeight(HEIGHT);
        setPrefWidth(WIDTH);
        setPrefHeight(HEIGHT);
        //green.setLayoutY(HEIGHT / 2);
        //yellow.setLayoutY(HEIGHT / 4);
        green.setFill(Color.LIME);
        yellow.setFill(Color.GOLD);
        red.setFill(Color.RED);
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        getChildren().addAll(red, yellow, green);

        switch (orientation) {
            case HORIZ -> {
                getTransforms().add(new Rotate(270));
                getTransforms().add(new Scale(1.1, 1.40));
            }
            default -> { // Vertical
                getTransforms().add(new Rotate(180));

            }
        }

        setValue(0);
    }

    public final void setValue(int percent) {
        if (percent < 0) {
            percent = 0;
        }

        int pctGreen = percent;
        if (pctGreen > 50) {
            pctGreen = 50;
        }
        green.setHeight(HEIGHT * pctGreen / 100);
        int pctYellow = percent;
        if (pctYellow > 75) {
            pctYellow = 75;
        }
        yellow.setHeight(HEIGHT * pctYellow / 100);
        int pctRed = percent;
        if (pctRed > 100) {
            pctRed = 100;
        }
        red.setHeight(HEIGHT * pctRed / 100);
    }

}
