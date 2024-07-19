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
package com.maehem.javamancer.neuro.view.room;

import com.maehem.javamancer.neuro.view.ResourceManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class PlayerNode extends StackPane {
    private static final String PREFIX = "SPRITES";
    private static final int SPRITE_W = 32; // Max
    private static final int SPRITE_H = 64; // Max

    public enum Direction {
        AWAY(0), RIGHT(1), TOWARD(2), LEFT(3);

        public final int index;

        private Direction(int index) {
            this.index = index;
        }

    }

    ImageView[][] sprite = new ImageView[4][8];

    Direction direction = Direction.TOWARD; // Away,Right,Toward,Left
    int step = 0; // 0..7

    public PlayerNode(ResourceManager resourceManager) {
        for ( int i=0; i< 32; i++ ) {
            Image spriteImg = resourceManager.getSprite(PREFIX + "_" + (i + 1));
            ImageView imageView = new ImageView(spriteImg);
            this.sprite[i / 8][i % 8] = imageView;
            getChildren().add(imageView);
        }
        setTranslateX(-SPRITE_W * ResourceManager.IMG_SCALE / 2);
        setTranslateY(-SPRITE_H * ResourceManager.IMG_SCALE);
        // Layout location is referenced from bottom, center.

        updateSprite();
    }

    public final void updateSprite() {
        for (int i = 0; i < 32; i++) {
            this.sprite[i / 8][i % 8].setVisible(false);
        }
        this.sprite[direction.index][step].setVisible(true);
    }

    public void step() {
        this.step++;
        if (step > 7) {
            step = 0;
        }
        updateSprite();
    }

    public void setDirection(Direction d) {
        this.direction = d;
        updateSprite();
    }

}
