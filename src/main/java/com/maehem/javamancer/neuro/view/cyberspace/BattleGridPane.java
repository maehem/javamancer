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

import com.maehem.javamancer.neuro.model.GameState;
import javafx.scene.image.ImageView;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class BattleGridPane extends GridPane {

    private final ImageView[] dbThing;
    private final ImageView[] shotsLive;
    private final ImageView[] shotsExplode;
    private final ImageView[] iceRear;
    private final ImageView[] iceFront;
    private final ImageView[] iceVirusRear;
    private final ImageView[] iceVirusFront;
    private final ImageView[] iceVirusRotRear;
    private final ImageView[] iceVirusRotFront;
    private final ImageView battleGrid;
    private final FrameSequence shotExplodeSequence;
    private final FrameSequence shotLiveSequence;
    private final FrameSequence iceRearSequence;
    private final FrameSequence iceFrontSequence;
    private final FrameSequence iceVirusRearSequence;
    private final FrameSequence iceVirusFrontSequence;
    private final FrameSequence iceVirusRotRearSequence;
    private final FrameSequence iceVirusRotFrontSequence;
    private final ImageStack database;
    private final ImageStack shotsLivePane;
    private final ImageStack shotsExplodePane;
    private final ImageStack iceRearPane;
    private final ImageStack iceFrontPane;
    private final ImageStack iceVirusRearPane;
    private final ImageStack iceVirusFrontPane;
    private final ImageStack iceVirusRotRearPane;
    private final ImageStack iceVirusRotFrontPane;

    public BattleGridPane(GameState gs) {
        super(gs);
        this.battleGrid = new ImageView(resourceManager.getSprite("CSDB_1"));

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

        shotsLivePane = new ImageStack(305, 130, this.shotsLive);
        shotsExplodePane = new ImageStack(280, 80, this.shotsExplode);
        iceRearPane = new ImageStack(206, 46, this.iceRear);
        iceFrontPane = new ImageStack(206, 82, this.iceFront);
        iceVirusRearPane = new ImageStack(396, 46, this.iceVirusRear);
        iceVirusFrontPane = new ImageStack(396, 82, this.iceVirusFront);
        iceVirusRotRearPane = new ImageStack(396, 46, this.iceVirusRotRear);
        iceVirusRotFrontPane = new ImageStack(396, 82, this.iceVirusRotFront);

        database = new ImageStack(276, 44, dbThing);
        database.show(0);
        database.setScaleY(1.06);

        getChildren().addAll(
                battleGrid,
                iceRearPane, iceVirusRearPane, iceVirusRotRearPane,
                database,
                iceFrontPane, iceVirusFrontPane,
                shotsLivePane, shotsExplodePane, iceVirusRotFrontPane
        );

        shotExplodeSequence = new FrameSequence(shotsExplode, false, true);
        shotLiveSequence = new FrameSequence(shotsLive, false, true);

        iceRearSequence = new FrameSequence(iceRear, false, true);
        iceFrontSequence = new FrameSequence(iceFront, false, true);
        iceVirusRearSequence = new FrameSequence(iceVirusRear, false, true);
        iceVirusFrontSequence = new FrameSequence(iceVirusFront, false, true);
        iceVirusRotRearSequence = new FrameSequence(iceVirusRotRear, false, true);
        iceVirusRotFrontSequence = new FrameSequence(iceVirusRotFront, false, true);

    }

    protected void tick() {
        // Update the current state of the battle.
    }

    void resetBattle() {
        iceRearPane.setVisible(true);
        iceVirusRearPane.setVisible(false);
        iceVirusRotRearPane.setVisible(false);

        iceFrontPane.setVisible(true);
        iceVirusFrontPane.setVisible(false);
        iceVirusRotFrontPane.setVisible(false);

        shotsLivePane.setVisible(false);
        shotsExplodePane.setVisible(false);

        iceRearSequence.start();
        iceFrontSequence.start();
    }

}
