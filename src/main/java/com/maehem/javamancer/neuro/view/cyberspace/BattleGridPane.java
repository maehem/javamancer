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
import com.maehem.javamancer.neuro.model.ai.AI;
import com.maehem.javamancer.neuro.model.database.Database;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class BattleGridPane extends GridPane {

    //private final int[] aiPos;
    private final ImageStack aiFace;

    private enum IceMode {
        NONE, BASIC, VIRUS, ROT
    }

    private final ImageView[] dbThing;
    private final ImageView[] shotsLivePlayer;
    private final ImageView[] shotsExplodePlayer;
    private final ImageView[] shotsLiveDB;
    private final ImageView[] shotsExplodeDB;
    private final ImageView[] aiImageView;
    private final ImageView[] iceRear;
    private final ImageView[] iceFront;
    private final ImageView[] iceVirusRear;
    private final ImageView[] iceVirusFront;
    private final ImageView[] iceVirusRotRear;
    private final ImageView[] iceVirusRotFront;
    private final ImageView battleGrid;
    private final FrameSequence shotExplodePlayerSequence;
    private final FrameSequence shotLivePlayerSequence;
    private final FrameSequence shotExplodeDBSequence;
    private final FrameSequence shotLiveDBSequence;
    private final FrameSequence iceRearSequence;
    private final FrameSequence iceFrontSequence;
    private final FrameSequence iceVirusRearSequence;
    private final FrameSequence iceVirusFrontSequence;
    private final FrameSequence iceVirusRotRearSequence;
    private final FrameSequence iceVirusRotFrontSequence;
    private final ImageStack database;
    private final ImageStack shotsLivePlayerPane;
    private final ImageStack shotsExplodePlayerPane;
    private final ImageStack shotsLiveDBPane;
    private final ImageStack shotsExplodeDBPane;
    private final ImageStack iceRearPane;
    private final ImageStack iceFrontPane;
    private final ImageStack iceVirusRearPane;
    private final ImageStack iceVirusFrontPane;
    private final ImageStack iceVirusRotRearPane;
    private final ImageStack iceVirusRotFrontPane;

    public BattleGridPane(GameState gs) {
        super(gs);
        this.battleGrid = new ImageView(resourceManager.getSprite("CSDB_1"));

        this.aiImageView = new ImageView[]{
            new ImageView(resourceManager.getSprite("AIP0_1")),
            new ImageView(resourceManager.getSprite("AIP1_1")),
            new ImageView(resourceManager.getSprite("AIP2_1")),
            new ImageView(resourceManager.getSprite("AIP3_1")),
            new ImageView(resourceManager.getSprite("AIP4_1")),
            new ImageView(resourceManager.getSprite("AIP5_1")),
            new ImageView(resourceManager.getSprite("AIP6_1")),
            new ImageView(resourceManager.getSprite("AIP7_1")),
            new ImageView(resourceManager.getSprite("AIP8_1")),
            new ImageView(resourceManager.getSprite("AIP9_1")),
            new ImageView(resourceManager.getSprite("AIP10_1")),
            new ImageView(resourceManager.getSprite("AIP11_1"))};
//        this.aiPos = new int[]{// Y position of AI face.
//            284, 284, 284, 284, 284, 284, 284, 284, 284, 284, 284, 284
//        };

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
        this.shotsLivePlayer = new ImageView[]{
            new ImageView(resourceManager.getSprite("SHOTS_1")), // Live 1
            new ImageView(resourceManager.getSprite("SHOTS_2"))};// Explode 5
        this.shotsExplodePlayer = new ImageView[]{
            new ImageView(resourceManager.getSprite("SHOTS_3")), // Explode 1
            new ImageView(resourceManager.getSprite("SHOTS_4")), // Explode 2
            new ImageView(resourceManager.getSprite("SHOTS_5")), // Explode 3
            new ImageView(resourceManager.getSprite("SHOTS_6")), // Explode 4
            new ImageView(resourceManager.getSprite("SHOTS_7"))};// Explode 5
        this.shotsLiveDB = new ImageView[]{
            new ImageView(resourceManager.getSprite("SHOTS_1")), // Live 1
            new ImageView(resourceManager.getSprite("SHOTS_2"))};// Explode 5
        this.shotsExplodeDB = new ImageView[]{
            new ImageView(resourceManager.getSprite("SHOTS_3")), // Explode 1
            new ImageView(resourceManager.getSprite("SHOTS_4")), // Explode 2
            new ImageView(resourceManager.getSprite("SHOTS_5")), // Explode 3
            new ImageView(resourceManager.getSprite("SHOTS_6")), // Explode 4
            new ImageView(resourceManager.getSprite("SHOTS_7"))};// Explode 5

        shotsLivePlayerPane = new ImageStack(305, 130, this.shotsLivePlayer);
        shotsExplodePlayerPane = new ImageStack(284, 190, this.shotsExplodePlayer);
        shotsLiveDBPane = new ImageStack(305, 130, this.shotsLiveDB);
        shotsExplodeDBPane = new ImageStack(284, 98, this.shotsExplodeDB);
        iceRearPane = new ImageStack(206, 46, this.iceRear);
        iceFrontPane = new ImageStack(206, 82, this.iceFront);
        iceVirusRearPane = new ImageStack(206, 46, this.iceVirusRear);
        iceVirusFrontPane = new ImageStack(206, 82, this.iceVirusFront);
        iceVirusRotRearPane = new ImageStack(206, 46, this.iceVirusRotRear);
        iceVirusRotFrontPane = new ImageStack(206, 82, this.iceVirusRotFront);

        database = new ImageStack(276, 44, dbThing);
        database.show(0);
        database.setScaleY(1.06);

        aiFace = new ImageStack(258, 0, aiImageView);
        aiFace.show(-1);
        aiFace.setScaleY(1.06);

        getChildren().addAll(
                battleGrid,
                iceRearPane, iceVirusRearPane, iceVirusRotRearPane,
                database, aiFace,
                iceFrontPane, iceVirusFrontPane, iceVirusRotFrontPane,
                shotsLiveDBPane, shotsExplodeDBPane,
                shotsLivePlayerPane, shotsExplodePlayerPane
        );

        shotExplodePlayerSequence = new FrameSequence(shotsExplodePlayer, false, true);
        shotLivePlayerSequence = new FrameSequence(shotsLivePlayer, false, true);
        shotExplodeDBSequence = new FrameSequence(shotsExplodeDB, false, false);
        shotLiveDBSequence = new FrameSequence(shotsLiveDB, false, true);

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
        Database db = gameState.database;

        if (db.ice > 0) {
            setIceMode(IceMode.BASIC);
        } else {
            setIceMode(IceMode.NONE);
        }

        if (db.ai != null) {
            AI ai = gameState.getAI(db.ai);
            database.show(-1);
            aiFace.show(ai.index);
        } else {
            database.show(1);
            aiFace.show(-1);
        }

        shotsLiveDBPane.setVisible(false);
        shotsExplodeDBPane.setVisible(true);
        shotsLivePlayerPane.setVisible(false);
        shotsExplodePlayerPane.setVisible(true);

        iceRearSequence.start();
        iceFrontSequence.start();
        //iceVirusRearSequence.start();
        //iceVirusFrontSequence.start();
        //iceVirusRotRearSequence.start();
        //iceVirusRotFrontSequence.start();

        //shotExplodePlayerSequence.start();
        //shotExplodeDBSequence.start();
        fireShotPlayer();
        fireShotDB();
    }

    private void fireShotPlayer() {
        shotsLivePlayerPane.setLayoutY(190);
        shotsLivePlayerPane.setVisible(true);
        shotLivePlayerSequence.start();
        TranslateTransition tt = shotMoveInit(shotsLivePlayerPane, -50, 2000);
        tt.setOnFinished((t) -> {
            tt.setNode(null);
            shotLivePlayerSequence.stop();
            shotsLivePlayerPane.setVisible(false);
            shotExplodeDBSequence.start();
            iceBroken(iceFrontPane);
            iceBroken(iceRearPane);
        });
    }

    private void fireShotDB() {
        shotsLiveDBPane.setLayoutY(140);
        shotsLiveDBPane.setVisible(true);
        shotLiveDBSequence.start();
        TranslateTransition tt = shotMoveInit(shotsLiveDBPane, 50, 2000);
        tt.setOnFinished((t) -> {
            tt.setNode(null);
            shotLiveDBSequence.stop();
            shotsLiveDBPane.setVisible(false);
        });
    }

    /**
     * ICE Opacity fades over two seconds
     */
    private FadeTransition iceBroken(Node fadingNode) {
        fadingNode.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(2000), fadingNode);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);

        ft.setOnFinished((t) -> {
            fadingNode.setVisible(false);
            fadingNode.setOpacity(1.0);
        });
        ft.play();

        return ft;
    }

    private TranslateTransition shotMoveInit(Node movingNode, double movAmt, int duration) {
        movingNode.setVisible(true);
        TranslateTransition tt = new TranslateTransition();
        tt.setDuration(Duration.millis(duration));
        tt.setNode(movingNode);
        tt.setByY(movAmt);
        tt.setCycleCount(1);
        tt.setAutoReverse(false);
        tt.play();

        return tt;
    }

    private void setIceMode(IceMode mode) {
        iceFrontPane.setVisible(mode == IceMode.BASIC);
        iceRearPane.setVisible(mode == IceMode.BASIC);
        iceVirusFrontPane.setVisible(mode == IceMode.VIRUS);
        iceVirusRearPane.setVisible(mode == IceMode.VIRUS);
        iceVirusRotFrontPane.setVisible(mode == IceMode.ROT);
        iceVirusRotRearPane.setVisible(mode == IceMode.ROT);
    }
}
