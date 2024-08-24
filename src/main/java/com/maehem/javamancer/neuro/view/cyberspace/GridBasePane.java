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
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class GridBasePane extends Pane {

    private static final int HORIZON = 100;

    private final ImageView cspace;
    private final ImageView[] gridLR;
    private final ImageView[] gridFB;
    private final ImageView[] dbThing;
    private final ImageView[] shotsLive;
    private final ImageView[] shotsExplode;
    private final ImageView[] iceRear;
    private final ImageView[] iceFront;
    private final ImageView[] iceVirusRear;
    private final ImageView[] iceVirusFront;
    private final ImageView[] iceVirusRotRear;
    private final ImageView[] iceVirusRotFront;
    private final ResourceManager resourceManager;
    private final ImageView gridbase;
    private final ImageView dbGrid;
    private final FrameSequence gridRight;
    private final FrameSequence gridLeft;
    private final FrameSequence gridForward;
    private final FrameSequence gridBackward;
    private final FrameSequence shotExplodeSequence;
    private final FrameSequence shotLiveSequence;
    private final FrameSequence iceRearSequence;
    private final FrameSequence iceFrontSequence;
    private final FrameSequence iceVirusRearSequence;
    private final FrameSequence iceVirusFrontSequence;
    private final ImageStack database;

    public enum Direction {
        LEFT, RIGHT, FORWARD, BACKWARD
    }

    public GridBasePane(ResourceManager rm) {
        this.resourceManager = rm;
        this.cspace = new ImageView(resourceManager.getSprite("CSPACE_1"));
        this.gridbase = new ImageView(resourceManager.getSprite("GRIDBASE_1"));
        this.dbGrid = new ImageView(resourceManager.getSprite("CSDB_1"));
        this.iceRear = new ImageView[]{
            new ImageView(resourceManager.getSprite("ICE_1")),
            new ImageView(resourceManager.getSprite("ICE_3")),
            new ImageView(resourceManager.getSprite("ICE_5")),
            new ImageView(resourceManager.getSprite("ICE_7"))};
        this.iceFront = new ImageView[]{
            new ImageView(resourceManager.getSprite("ICE_2")),
            new ImageView(resourceManager.getSprite("ICE_4")),
            new ImageView(resourceManager.getSprite("ICE_6")),
            new ImageView(resourceManager.getSprite("ICE_8"))};
        this.iceVirusRear = new ImageView[]{
            new ImageView(resourceManager.getSprite("VIRUSICE_1")),
            new ImageView(resourceManager.getSprite("VIRUSICE_3")),
            new ImageView(resourceManager.getSprite("VIRUSICE_5")),
            new ImageView(resourceManager.getSprite("VIRUSICE_7")),
            new ImageView(resourceManager.getSprite("VIRUSICE_9")),
            new ImageView(resourceManager.getSprite("VIRUSICE_11")),
            new ImageView(resourceManager.getSprite("VIRUSICE_13")),
            new ImageView(resourceManager.getSprite("VIRUSICE_15"))};
        this.iceVirusFront = new ImageView[]{
            new ImageView(resourceManager.getSprite("VIRUSICE_2")),
            new ImageView(resourceManager.getSprite("VIRUSICE_4")),
            new ImageView(resourceManager.getSprite("VIRUSICE_6")),
            new ImageView(resourceManager.getSprite("VIRUSICE_8")),
            new ImageView(resourceManager.getSprite("VIRUSICE_10")),
            new ImageView(resourceManager.getSprite("VIRUSICE_12")),
            new ImageView(resourceManager.getSprite("VIRUSICE_14")),
            new ImageView(resourceManager.getSprite("VIRUSICE_16"))};
        this.iceVirusRotRear = new ImageView[]{
            new ImageView(resourceManager.getSprite("VIRUSROT_1")),
            new ImageView(resourceManager.getSprite("VIRUSROT_3")),
            new ImageView(resourceManager.getSprite("VIRUSROT_5")),
            new ImageView(resourceManager.getSprite("VIRUSROT_7"))};
        this.iceVirusRotFront = new ImageView[]{
            new ImageView(resourceManager.getSprite("VIRUSROT_2")),
            new ImageView(resourceManager.getSprite("VIRUSROT_4")),
            new ImageView(resourceManager.getSprite("VIRUSROT_6")),
            new ImageView(resourceManager.getSprite("VIRUSROT_8"))};
        this.dbThing = new ImageView[]{
            new ImageView(resourceManager.getSprite("DBSPR_1")), // Close
            new ImageView(resourceManager.getSprite("DBSPR_2")), // Mid
            new ImageView(resourceManager.getSprite("DBSPR_3"))};// Far
        this.shotsLive = new ImageView[]{
            new ImageView(resourceManager.getSprite("SHOTS_1")), // Live 1
            new ImageView(resourceManager.getSprite("SHOTS_2"))};// Explode 5
        this.shotsExplode = new ImageView[]{
            new ImageView(resourceManager.getSprite("SHOTS_3")), // Explode 1
            new ImageView(resourceManager.getSprite("SHOTS_4")), // Explode 2
            new ImageView(resourceManager.getSprite("SHOTS_5")), // Explode 3
            new ImageView(resourceManager.getSprite("SHOTS_6")), // Explode 4
            new ImageView(resourceManager.getSprite("SHOTS_7"))};// Explode 5
        this.gridLR = new ImageView[]{
            new ImageView(resourceManager.getSprite("GRIDS_1")),
            new ImageView(resourceManager.getSprite("GRIDS_2")),
            new ImageView(resourceManager.getSprite("GRIDS_3"))};
        this.gridFB = new ImageView[]{
            new ImageView(resourceManager.getSprite("GRIDS_4")),
            new ImageView(resourceManager.getSprite("GRIDS_5")),
            new ImageView(resourceManager.getSprite("GRIDS_6"))};

        StackPane shotsLivePane = new StackPane(this.shotsLive);
        shotsLivePane.setLayoutX(305);
        shotsLivePane.setLayoutY(130);
        StackPane shotsExplodePane = new StackPane(this.shotsExplode);
        shotsExplodePane.setLayoutX(280);
        shotsExplodePane.setLayoutY(80);
        StackPane iceRearPane = new StackPane(this.iceRear);
        iceRearPane.setLayoutX(206);
        iceRearPane.setLayoutY(30);
        StackPane iceFrontPane = new StackPane(this.iceFront);
        iceFrontPane.setLayoutX(206);
        iceFrontPane.setLayoutY(62);
        StackPane iceVirusRearPane = new StackPane(this.iceVirusRear);
        iceVirusRearPane.setLayoutX(396);
        iceVirusRearPane.setLayoutY(30);
        StackPane iceVirusFrontPane = new StackPane(this.iceVirusFront);
        iceVirusFrontPane.setLayoutX(396);
        iceVirusFrontPane.setLayoutY(62);


        database = new ImageStack(dbThing);
        database.setLayoutX(274);
        database.setLayoutY(20);
        database.show(2);

        getChildren().addAll(cspace,
                gridbase, dbGrid,
                gridLR[0], gridLR[1], gridLR[2],
                gridFB[0], gridFB[1], gridFB[2],
                iceRearPane, iceVirusRearPane,
                database,
                iceFrontPane, iceVirusFrontPane,
                shotsLivePane, shotsExplodePane
        );

        initImages();

        gridRight = new FrameSequence(gridLR, true, false);
        gridLeft = new FrameSequence(gridLR, false, false);
        gridForward = new FrameSequence(gridFB, true, false);
        gridBackward = new FrameSequence(gridFB, false, false);

        shotExplodeSequence = new FrameSequence(shotsExplode, false, true);
        shotLiveSequence = new FrameSequence(shotsLive, false, true);

        iceRearSequence = new FrameSequence(iceRear, false, true);
        iceFrontSequence = new FrameSequence(iceFront, false, true);
        iceVirusRearSequence = new FrameSequence(iceVirusRear, false, true);
        iceVirusFrontSequence = new FrameSequence(iceVirusFront, false, true);


        Platform.runLater(() -> {
            shotLiveSequence.start();
            shotExplodeSequence.start();
            iceRearSequence.start();
            iceFrontSequence.start();
            iceVirusRearSequence.start();
            iceVirusFrontSequence.start();
        });
    }

    public final void animate(Direction dir) {
        switch (dir) {
            case FORWARD -> {
                gridForward.start();
            }
            case BACKWARD -> {
                gridBackward.start();
            }
            case LEFT -> {
                gridLeft.start();
            }
            case RIGHT -> {
                gridRight.start();
            }
        }
    }

    private class FrameSequence extends AnimationTimer {

        int index = 0;
        long last = 0;
        ImageView frames[];
        private final boolean reverse;
        private final boolean repeat;

        public FrameSequence(ImageView[] frames, boolean reverse, boolean repeat) {
            this.frames = frames;
            this.reverse = reverse;
            this.repeat = repeat;
        }

        @Override
        public void handle(long now) {
            if (now - 100000000 > last) { // Fire - 100000000nS == 100mS
                last = now;
                for (ImageView frame : frames) {
                    frame.setVisible(false);
                }
                if (index < frames.length) {
                    frames[reverse ? frames.length - 1 - index : index].setVisible(true);
                    index++;
                    if (repeat && index >= frames.length) {
                        index = 0;
                    }
                } else { // Blank everything and stop on one-shot.
                    index = 0;
                    if (!repeat) {
                        stop();
                    }
                }
            }
        }
    }

    private class ImageStack extends StackPane {

        private int current = 0;

        public ImageStack(Node... nodes) {
            super(nodes);

            show(current);
        }

        public final void show(int i) {
            current = i % getChildren().size();
            for (Node n : getChildren()) {
                n.setVisible(false);
            }
            getChildren().get(current).setVisible(true);
        }

        public int next() {
            show(current + 1);
            return current;
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
