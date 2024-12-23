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
package com.maehem.javamancer.neuro.view.popup;

import com.maehem.javamancer.neuro.view.ResourceManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class DialogBubble extends ImageView {

    private static final int FLIP_X = 300;
    private int index;
    private double playerX;
    private double npcX;

    protected enum Mode {
        NONE, PLAYER_SAY, PLAYER_THINK, NPC_SAY
    }

    private Mode mode = Mode.NONE;
    private final Image view[] = new Image[5];

    public DialogBubble(ResourceManager rm, double playerX, double npcX, double posY) {
        view[0] = null;
        for (int i = 1; i < view.length; i++) {
            view[i] = rm.getSprite("BUBBLES_" + i);
        }
        this.playerX = playerX;
        this.npcX = npcX;
        this.setLayoutX(playerX + (playerX < FLIP_X ? 38 : -38));
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
            case PLAYER_SAY -> {
                index = 1 + (playerX < FLIP_X ? 1 : 0);
                this.setLayoutX(playerX + (playerX < FLIP_X ? 38 : -38));

            }
            case PLAYER_THINK -> {
                index = 3 + (playerX < FLIP_X ? 1 : 0);
                this.setLayoutX(playerX + (playerX < FLIP_X ? 38 : -38));
            }
            case NPC_SAY -> {
                index = 1 + (npcX < FLIP_X ? 1 : 0);
                this.setLayoutX(npcX + (npcX < FLIP_X ? 38 : -38));
            }
        }
        setImage(view[index]);
    }
}
