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

import com.maehem.javamancer.neuro.view.ResourceManager;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class GridBasePane extends Pane {

    private static final int HORIZON = 100;

    private final ImageView cspace;
    private final ImageView[] gridLR;
    private final ResourceManager resourceManager;
    private final ImageView[] gridFB;
    private final ImageView gridbase;

    public enum Direction {
        LEFT, RIGHT, FORWARD, BACKWARD
    }

    public GridBasePane(ResourceManager rm) {
        this.resourceManager = rm;
        this.cspace = new ImageView(resourceManager.getSprite("CSPACE_1"));
        this.gridbase = new ImageView(resourceManager.getSprite("GRIDBASE_1"));
        this.gridLR = new ImageView[]{
            new ImageView(resourceManager.getSprite("GRIDS_1")),
            new ImageView(resourceManager.getSprite("GRIDS_2")),
            new ImageView(resourceManager.getSprite("GRIDS_3"))};
        this.gridFB = new ImageView[]{
            new ImageView(resourceManager.getSprite("GRIDS_4")),
            new ImageView(resourceManager.getSprite("GRIDS_5")),
            new ImageView(resourceManager.getSprite("GRIDS_6"))};

        getChildren().addAll(cspace, gridbase,
                gridLR[0], gridLR[1], gridLR[2],
                gridFB[0], gridFB[1], gridFB[2]);

        initImages();
        animate(Direction.FORWARD);
    }

    public final void animate(Direction dir) {
        switch (dir) {
            case FORWARD -> {

            }
            case BACKWARD -> {

            }
            case LEFT -> {

            }
            case RIGHT -> {

            }
        }
    }

    private void initImages() {
        gridbase.setLayoutY(HORIZON);
        gridLR[0].setLayoutY(HORIZON);
        gridLR[0].setVisible(false);
        gridLR[1].setLayoutY(HORIZON);
        gridLR[1].setVisible(false);
        gridLR[2].setLayoutY(HORIZON);
        gridLR[2].setVisible(false);

        gridFB[0].setLayoutY(HORIZON);
        gridFB[0].setVisible(false);
        gridFB[1].setLayoutY(HORIZON);
        gridFB[1].setVisible(false);
        gridFB[2].setLayoutY(HORIZON);
        gridFB[2].setVisible(false);

    }
}
