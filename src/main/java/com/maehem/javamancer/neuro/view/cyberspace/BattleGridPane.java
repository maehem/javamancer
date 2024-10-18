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
import com.maehem.javamancer.neuro.model.item.DeckItem;
import com.maehem.javamancer.neuro.model.warez.ChessWarez;
import com.maehem.javamancer.neuro.model.warez.CorruptorWarez;
import com.maehem.javamancer.neuro.model.warez.IceBreakerWarez;
import com.maehem.javamancer.neuro.model.warez.LinkWarez;
import com.maehem.javamancer.neuro.model.warez.ShotgunWarez;
import com.maehem.javamancer.neuro.model.warez.VirusWarez;
import com.maehem.javamancer.neuro.model.warez.Warez;
import com.maehem.javamancer.neuro.view.SoundEffectsManager;
import java.util.logging.Level;
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

    private static final int ICE_X = 210;
    private static final int SHOT_X = 310;
    private static final int EXPLODE_X = 284;

    //private final int[] aiPos;
    private final ImageStack aiFace;
    private boolean dbAttackRunning;

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

        shotsLivePlayerPane = new ImageStack(SHOT_X, 130, this.shotsLivePlayer);
        shotsExplodePlayerPane = new ImageStack(EXPLODE_X, 190, this.shotsExplodePlayer);
        shotsLiveDBPane = new ImageStack(SHOT_X, 130, this.shotsLiveDB);
        shotsExplodeDBPane = new ImageStack(EXPLODE_X, 98, this.shotsExplodeDB);
        iceRearPane = new ImageStack(ICE_X, 46, this.iceRear);
        iceFrontPane = new ImageStack(ICE_X, 82, this.iceFront);
        iceVirusRearPane = new ImageStack(ICE_X, 46, this.iceVirusRear);
        iceVirusFrontPane = new ImageStack(ICE_X, 82, this.iceVirusFront);
        iceVirusRotRearPane = new ImageStack(ICE_X, 46, this.iceVirusRotRear);
        iceVirusRotFrontPane = new ImageStack(ICE_X, 82, this.iceVirusRotFront);

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

        shotExplodePlayerSequence = new FrameSequence(shotsExplodePlayer, false, false);
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
        if (gameState.isFlatline()) {
            cleanup();
            return;
        }

        // Update the current state of the battle.
        DeckItem deck = gameState.usingDeck;
        if (deck.getCurrentWarez() != null) {
            Warez w = deck.getCurrentWarez();
            switch (w) {
                case LinkWarez ww -> {
                    // Do nothing
                    w.setRunning(true); // must toggle for event to work
                    w.setRunning(false);
                    deck.setCurrentWarez(null);
                }
                case IceBreakerWarez ww -> {
                    LOGGER.log(Level.SEVERE, "Using IceBreaker Warez... {0}", w.item.itemName);
                    if (!ww.isRunning()) {
                        ww.setRunning(true);
                        fireShotPlayer();
                    }
                }
                case VirusWarez ww -> { // ICE Breaker but deletes after use.
                    //LOGGER.log(Level.SEVERE, "Using Virus Warez...{0}.  Todo: What happens here?", w.item.itemName);
                    if (!ww.isRunning()) {
                        ww.setRunning(true);
                        fireShotPlayer();
                    }
                }
                case CorruptorWarez ww -> {
                    LOGGER.log(Level.SEVERE, "Using Corruptor Warez...{0}.  Todo: What happens here?", w.item.itemName);
                    if (!ww.isRunning()) {
                        ww.setRunning(true);
                        fireShotPlayer();
                    }
                }
                case ShotgunWarez ww -> {
                    LOGGER.log(Level.SEVERE, "Using Shotgun Warez...{0}.  Todo: What happens here?", w.item.itemName);
                    if (!ww.isRunning()) {
                        ww.setRunning(true);
                        fireShotPlayer();
                    }
                }
                case ChessWarez ww -> {
                    LOGGER.log(Level.SEVERE, "Using Chess Warez...{0}.  Todo: What happens here?", w.item.itemName);
                    w.setRunning(true); // must toggle for event to work
                    w.setRunning(false);
                    deck.setCurrentWarez(null);
                }
                default -> {
                    LOGGER.log(Level.SEVERE, "Using Non-Applicable Warez...{0}. Remove from use.", w.item.itemName);
                    w.setRunning(true); // must toggle for event to work
                    w.setRunning(false);
                    deck.setCurrentWarez(null);
                }
            }
        }
        if (!dbAttackRunning && gameState.database.getIce() > 0) {
            // DB attack
            fireShotDB();
        }
    }

    void resetBattle() {
        Database db = gameState.database;

        if (db.getIce() > 0) {
            setIceMode(IceMode.BASIC);
            iceRearSequence.start();
            iceFrontSequence.start();
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

        //iceVirusRearSequence.start();
        //iceVirusFrontSequence.start();
        //iceVirusRotRearSequence.start();
        //iceVirusRotFrontSequence.start();
        //shotExplodePlayerSequence.start();
        //shotExplodeDBSequence.start();
        //fireShotPlayer();
        //fireShotDB();
    }

    private void fireShotPlayer() {
        LOGGER.log(Level.SEVERE, "Player Fires Attack Round...");
        DeckItem deck = gameState.usingDeck;
        Warez w = deck.getCurrentWarez();

        shotsLivePlayerPane.setLayoutY(200);
        shotsLivePlayerPane.setVisible(true);
        shotLivePlayerSequence.start();
        gameState.resourceManager.soundFxManager.playTrack(SoundEffectsManager.Sound.PLAYER_FIRE);

        TranslateTransition tt = shotMoveInit(
                shotsLivePlayerPane,
                -70,
                w.getRunDuration()
        );
        tt.setOnFinished((t) -> {
            LOGGER.log(Level.SEVERE, "Warez " + w.item.itemName + " finished. Un-slot.");
            w.setRunning(false);
            deck.setCurrentWarez(null);

            tt.setNode(null);
            shotLivePlayerSequence.stop();
            shotsLivePlayerPane.setVisible(false);
            shotsLivePlayerPane.setTranslateY(0);
            shotExplodeDBSequence.start();
            // Apply damage, considering weaknesses.
            gameState.database.applyWarezAttack(w, gameState);

            if (w instanceof VirusWarez) {
                gameState.eraseSoftware(w);
            } else {
                // Software has a chance of being worn/damaged.
                LOGGER.log(Level.SEVERE, "TODO: Wear or Damage software.");
            }

            if (gameState.database.getIce() == 0) {
                gameState.resourceManager.soundFxManager.playTrack(SoundEffectsManager.Sound.ICE_BROKEN);
                iceBroken(iceFrontPane);
                iceBroken(iceRearPane);
            }
        });
    }

    private void fireShotDB() {
        LOGGER.log(Level.SEVERE, "Database Fires Attack Round...");
        Database db = gameState.database;

        shotsLiveDBPane.setLayoutY(120);
        shotsLiveDBPane.setVisible(true);
        shotLiveDBSequence.start();
        dbAttackRunning = true;
        gameState.resourceManager.soundFxManager.playTrack(SoundEffectsManager.Sound.ICE_HIT);


        TranslateTransition tt = shotMoveInit(shotsLiveDBPane, 80, db.shotDuration);
        tt.setOnFinished((t) -> {
            LOGGER.log(Level.SEVERE, "DB Attack finished.");
            dbAttackRunning = false;

            //int effect = db.getEffect(gameState);
            tt.setNode(null);
            shotLiveDBSequence.stop();
            shotsLiveDBPane.setVisible(false);
            shotsLiveDBPane.setTranslateY(0);
            shotExplodePlayerSequence.start();
            gameState.applyDbAttack(); // Handles death and constitution.
        });
    }

    /**
     * ICE Opacity fades over two seconds
     */
    private FadeTransition iceBroken(Node fadingNode) {

        fadingNode.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(3200), fadingNode);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);

        ft.setOnFinished((t) -> {
            fadingNode.setVisible(false);
            fadingNode.setOpacity(1.0);
            gameState.setIceBroken(true);
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

    /**
     * Called if battle ends or death.
     */
    public void cleanup() {
        //
    }
}
