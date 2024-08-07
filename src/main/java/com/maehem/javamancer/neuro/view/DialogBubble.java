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

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class DialogBubble extends ImageView {

    private static final int FLIP_X = 300;
    private int index;
    private double position;

    protected enum Mode {
        NONE, SAY, THINK
    }

    private Mode mode = Mode.NONE;
    private final Image view[] = new Image[5];

    public DialogBubble(ResourceManager rm, double posX, double posY) {
        view[0] = null;
        for (int i = 1; i < view.length; i++) {
            view[i] = rm.getSprite("BUBBLES_" + i);
        }
        this.position = posX;
        this.setLayoutX(posX + (posX < FLIP_X ? 38 : -38));
        this.setLayoutY(posY);
        update();
    }

    public void setMode(Mode m) {
        this.mode = m;
        update();
    }

    private void update() {
        index = 0;
        switch (mode) {
            case NONE -> {
                index = 0;
            }
            case SAY -> {
                index = 1 + (position < FLIP_X ? 1 : 0);
            }
            case THINK -> {
                index = 3 + (position < FLIP_X ? 1 : 0);
            }
        }
        setImage(view[index]);
    }
}
