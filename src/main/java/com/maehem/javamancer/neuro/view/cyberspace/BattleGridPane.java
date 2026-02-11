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
import com.maehem.javamancer.neuro.model.ai.NeuromancerAI;
import com.maehem.javamancer.neuro.model.database.Database;
import com.maehem.javamancer.neuro.model.database.KGBDatabase;
import com.maehem.javamancer.neuro.model.item.DeckItem;
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.room.Room;
import com.maehem.javamancer.neuro.model.room.RoomBounds;
import com.maehem.javamancer.neuro.model.skill.LogicSkill;
import com.maehem.javamancer.neuro.model.skill.PhenomenologySkill;
import com.maehem.javamancer.neuro.model.skill.PhilosophySkill;
import com.maehem.javamancer.neuro.model.skill.PsychoanalysisSkill;
import com.maehem.javamancer.neuro.model.skill.Skill;
import com.maehem.javamancer.neuro.model.skill.SophistrySkill;
import com.maehem.javamancer.neuro.model.warez.ChessWarez;
import com.maehem.javamancer.neuro.model.warez.CorruptorWarez;
import com.maehem.javamancer.neuro.model.warez.IceBreakerWarez;
import com.maehem.javamancer.neuro.model.warez.LinkWarez;
import com.maehem.javamancer.neuro.model.warez.ShotgunWarez;
import com.maehem.javamancer.neuro.model.warez.VirusWarez;
import com.maehem.javamancer.neuro.model.warez.Warez;
import com.maehem.javamancer.neuro.view.PopupListener;
import com.maehem.javamancer.neuro.view.RoomMode;
import com.maehem.javamancer.neuro.view.SoundEffectsManager;
import java.util.ArrayList;
import java.util.logging.Level;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class BattleGridPane extends GridPane implements PopupListener {

    /*
    <pre>
    TODO:   
    
      - ICE is always the first battle.
      - If site has AI then AI battle begins after ICE.
      - AI insults player
      - AI attack is similar to ICE with exceptions:
          - Offensive software does not work on AIs. Must use:
              - Skills: Philosophy, Sophistry, Phenomenology and Logic.
              - These skills upgrade when you use them.
          - Player cannot quit AI battle. Use Evasion 2.0 or KGB.
          - Reveal AI weakness with Psychoanalysis Skill.
          - When a AI cracks player shield, player looses CON.
          - AI battles allow use of Zen to heal player CON.
          - Zen has only 2 heals max per battle.
    
    TODO: Once player shield is depleted, they take CON damage and software damage.
    
    </pre>
     */
    private static final int ICE_X = 210;
    private static final int SHOT_X = 310;
    private static final int EXPLODE_X = 284;
    private AI ai;
    private Database db;

    /**
     * ICE Mode / Battle states
     */
    private enum IceMode {
        BASIC, // Normal ICE mode.
        VIRUS, // ICE animation alt.
        ROT, // ICE animation alt.
        BROKEN, // ICE down. Animations finishing. Start AI.
        AI, // AI Face present, second battle.
        AI_DEATH, // AI Dead. Face fading.
        NEUROMANCER, // Neuromancer pre-beach. He only talks to you.
        NONE   // Battles over. OK to open DB page.
    }

    // When player uses a skill/warez agains AI, it is added here.
    // Of player wins battle with AI, each skill/warez is upgraded by 1.
    private final ArrayList<Skill> usedOnAISkill = new ArrayList<>();
    private final ArrayList<Warez> usedOnAIWarez = new ArrayList<>();

    private final ImageStack aiFace;
    private boolean dbAttackRunning;
    private boolean aiAttackRunning;
    private IceMode mode = IceMode.NONE;

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

    private final AiTalkPane talkPane;

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

        talkPane = new AiTalkPane(this, gameState);

        getChildren().addAll(
                battleGrid,
                iceRearPane, iceVirusRearPane, iceVirusRotRearPane,
                database, aiFace,
                iceFrontPane, iceVirusFrontPane, iceVirusRotFrontPane,
                shotsLiveDBPane, shotsExplodeDBPane,
                shotsLivePlayerPane, shotsExplodePlayerPane,
                talkPane
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
        LOGGER.log(Level.FINEST, "Battle pane tick().");
        if (gameState.isFlatline()) {
            cleanup();
            return;
        }

        // Process already running warez each tick().
        processWarezTick();

        switch (mode) {
            case NONE -> {
                // ICE or AI Death animations finished. Proceed to DB page.
                if (ai != null && !gameState.isAiDefeated(ai.getClass())) {
                    gameState.defeatedAiList.add(ai); // Remember this defeated AI
                    // Upgrade AI Fight Skills used in battle.
                    usedOnAISkill.forEach((skill) -> {
                        if (skill instanceof PsychoanalysisSkill
                                || skill instanceof SophistrySkill
                                || skill instanceof LogicSkill
                                || skill instanceof PhenomenologySkill) {
                            skill.upgrade();
                        }
                    });
                    usedOnAISkill.clear();
                    usedOnAIWarez.clear();
                }

                talkPane.setVisible(false);
            }
            case BROKEN -> {
                // Animations are running. Do nothing for now.
            }
            case AI -> {
                if (ai.getConstitution() > 0) {
                    // Player can still attack AI with Skill.
                    // activeSkill is set by player ControlPanel UI interaction.
                    Skill skill = gameState.activeSkill;
                    if (skill != null) {
                        LOGGER.log(Level.FINER, "Skill is: {0}", skill.getVersionedName());
                        // Apply use of certain skills.
                        if (skill instanceof PhenomenologySkill
                                || skill instanceof PhilosophySkill
                                || skill instanceof SophistrySkill
                                || skill instanceof PsychoanalysisSkill
                                || skill instanceof LogicSkill) {
                            fireShotPlayer(skill);
                        } else {
                            LOGGER.log(Level.FINE, "Skill: {0} is not a AI battle skill.", skill.catalog.name());
                        }
                        gameState.activeSkill = null; // Consume skill
                    }
                    DeckItem deck = gameState.usingDeck;
                    if (deck.getCurrentWarez() != null) { // player selected a warez in the UI.
                        Warez w = deck.getCurrentWarez();
                        switch (w) {
                            case ShotgunWarez ww -> { // Use on AI.
                                LOGGER.log(Level.FINER, "Using Shotgun Warez...{0}.", w.item.itemName);
                                if (!ww.isRunning()) {
                                    fireShotPlayer(ww);
                                }
                            }
                            case ChessWarez ww -> { // Use on AI
                                if (ww.item == Item.Catalog.BATTLECHESS && ww.version == 4) {
                                    LOGGER.log(Level.FINE, "Using Chess Warez... {0} {1}.", new Object[]{w.item.itemName, w.version});
                                    if (!ww.isRunning()) {
                                        fireShotPlayer(ww);
                                    }
                                } else if (ww.item == Item.Catalog.KGB) { // Escape AI Battle
                                    // Transport user to KGB Base.
                                    setIceMode(IceMode.NONE);
                                    KGBDatabase kgbDatabase = new KGBDatabase(resourceManager);
                                    gameState.matrixPosX = kgbDatabase.matrixX;
                                    gameState.matrixPosY = kgbDatabase.matrixY;
                                    // TODO: Cause Battle to exit into matrix.

                                } else {
                                    LOGGER.log(Level.FINE, "Using Chess Warez...{0}.{1}. Wrong version used agains AI.", new Object[]{w.item.itemName, w.version});
                                }
                            }
                            default -> {
                                LOGGER.log(Level.SEVERE, "Warez {0} is not usable in AI battle! ", w.getSimpleName());
                                gameState.resourceManager.soundFxManager.playTrack(SoundEffectsManager.Sound.DENIED);
                            }
                        }
                        deck.setCurrentWarez(null); // Consume it.

                    }

                    // AI can still attack player.
                    if (!aiAttackRunning) {
                        fireShotAI(); // AI attacks Player
                    }
                } else { // AI Fight is won.
                    LOGGER.log(Level.INFO, "AI Defeated.");
                    gameState.resourceManager.soundFxManager.playTrack(SoundEffectsManager.Sound.ICE_BROKEN);
                    aiDefeatedAnimation(aiFace); // Changes mode at end of animation.
                    talkPane.say(AiTalkPane.Message.LAST);
                    setIceMode(IceMode.AI_DEATH);
                }
            }
            case AI_DEATH -> {
                // AI Animation running. Do nothing.
            }
            case NEUROMANCER -> { // Set up for final boss scenes.
                // AI apepars and says a couple things.

                // Player is transported to Beach (R50).
            }
            default -> { // All DB battle modes.
                DeckItem deck = gameState.usingDeck;
                if (db.getIce() <= 0) {
                    // End fight and hand off to AI Fight Mode (if present)
                    LOGGER.log(Level.INFO, "ICE Fight Ended.");
                    // Animations
                    //iceBrokenAnimation(iceFrontPane, iceRearPane);
                    gameState.resourceManager.soundFxManager.playTrack(SoundEffectsManager.Sound.ICE_BROKEN);
                    switch (mode) {
                        case AI -> {
                            if (ai.getConstitution() <= 0) {
                                LOGGER.log(Level.INFO, "AI Defeated.");
                                // TODO: New sound for AI defeated.
                                gameState.resourceManager.soundFxManager.playTrack(SoundEffectsManager.Sound.ICE_BROKEN);
                                aiDefeatedAnimation(aiFace); // Changes mode at end of animation.

                            }
                        }
                        case BASIC, ROT, VIRUS -> { // Any ICE battle mode.
                            if (db.getIce() <= 0) {
                                LOGGER.log(Level.INFO, "ICE Broken.");
                                mode = IceMode.BROKEN; // Allow animations to finish.

                                // Animations
                                iceBrokenAnimation(iceFrontPane, iceRearPane);
                                gameState.resourceManager.soundFxManager.playTrack(SoundEffectsManager.Sound.ICE_BROKEN);

                            }
                        }
                        default -> {
                            // Do nothing.
                        }
                    }
                    mode = IceMode.BROKEN;
                    deck.setCurrentWarez(null);
                } else {
                    // Update the current state of the battle.
                    if (deck.getCurrentWarez() != null) { // player selected a warez in the UI.
                        Warez w = deck.getCurrentWarez();
                        switch (w) {
                            case LinkWarez ww -> { // No effect for battle.
                            }
                            case IceBreakerWarez ww -> { // Standard ICE Damage. Not for AI.
                                LOGGER.log(Level.FINER, "Using IceBreaker Warez... {0}", ww.item.itemName);
                                if (!ww.isRunning()) {
                                    fireShotPlayer(ww);
                                }
                            }
                            case VirusWarez ww -> { // ICE Breaker but deletes after use.
                                LOGGER.log(Level.FINER, "Using Virus Warez...{0}.  Todo: What happens here?", ww.item.itemName);
                                if (!ww.isRunning()) {
                                    fireShotPlayer(ww);
                                }
                            }
                            case CorruptorWarez ww -> {
                                LOGGER.log(Level.FINER, "Using Corruptor Warez...{0}.  Todo: What happens here?", ww.item.itemName);
                                if (!ww.isRunning()) {
                                    fireShotPlayer(ww);
                                }
                            }
                            case ShotgunWarez ww -> { // Use on AI.
                                LOGGER.log(Level.FINE, "Using Shotgun Warez...{0}.  Todo: What happens here?", ww.item.itemName);
                                if (!ww.isRunning()) {
                                    fireShotPlayer(ww);
                                }
                            }
                            case ChessWarez ww -> { // Use on AI
                                LOGGER.log(Level.FINER, "Using Chess Warez...{0}.  Todo: What happens here?", ww.item.itemName);
                            }
                            default -> {
                                LOGGER.log(Level.FINER, "Using Non-Applicable Warez...{0}. Nothing happens.", w.item.itemName);
                            }
                        }
                        deck.setCurrentWarez(null); // Consume warez request.

                    }
                }

                if (!dbAttackRunning && gameState.database.getIce() > 0) {
                    // DB attack
                    fireShotDB();
                }
            }

        }

    }

    public boolean isDone() {
        return mode == IceMode.NONE;
    }
    
    public void setupNeuromancerFinalBattle() {
        LOGGER.log(Level.INFO, "BattleGrid:  Invoke Neuromancer Fight.");
        setIceMode(IceMode.AI);
    }

    void resetBattle() {
        db = gameState.database;
        ai = db.getAI(gameState);
        talkPane.setAi(ai);
        talkPane.setVisible(false);

        db.resetIce();
        setIceMode(IceMode.BASIC);
        iceRearSequence.start();
        iceFrontSequence.start();
        iceVirusRearSequence.start();
        iceVirusFrontSequence.start();
        iceVirusRotRearSequence.start();
        iceVirusRotFrontSequence.start();
        aiFace.show(-1);

        shotsLiveDBPane.setVisible(false);
        shotsExplodeDBPane.setVisible(true);
        shotsLivePlayerPane.setVisible(false);
        shotsExplodePlayerPane.setVisible(true);
    }

    /**
     * Initiate a Skill shot from player.
     *
     * This method kicks off the animation to send the attack towards the enemy.
     * Once the animation ends and also hits the enemy, the Skill effects are
     * then applied and the players use of the Skill ends.
     *
     * @param skill
     */
    private void fireShotPlayer(Skill skill) {
        LOGGER.log(Level.FINE, "Player Fires Skill Attack Round: {0}", skill.getVersionedName());

        shotsLivePlayerPane.setLayoutY(200);
        shotsLivePlayerPane.setVisible(true);
        shotLivePlayerSequence.start();
        gameState.resourceManager.soundFxManager.playTrack(SoundEffectsManager.Sound.PLAYER_FIRE);

        TranslateTransition tt = shotMoveInit(
                shotsLivePlayerPane,
                -70,
                2000
        );
        tt.setOnFinished((t) -> {
            LOGGER.log(Level.FINE, "Skill {0} finished. Un-slot.", skill.catalog.itemName);

            tt.setNode(null);
            shotLivePlayerSequence.stop();
            shotsLivePlayerPane.setVisible(false);
            shotsLivePlayerPane.setTranslateY(0);
            shotExplodeDBSequence.start();

            gameState.activeSkill = null; // Consume the skill.
            // Apply damage, considering weaknesses.
            if (mode == IceMode.AI) {
                ai.applySkillAttack(skill, gameState);
                talkPane.say(AiTalkPane.Message.RANDOM);
            } else {
                gameState.database.applySkillAttack(skill, gameState);
            }
        });
    }

    private void fireShotPlayer(Warez w) {
        LOGGER.log(Level.FINE, "Player Fires Warez Attack Round:{0} v{1}", new Object[]{w.item.itemName, w.version});
        DeckItem deck = gameState.usingDeck;

        // Set up animation.
        shotsLivePlayerPane.setLayoutY(200);
        shotsLivePlayerPane.setVisible(true);
        shotLivePlayerSequence.start();
        gameState.resourceManager.soundFxManager.playTrack(SoundEffectsManager.Sound.PLAYER_FIRE);

        TranslateTransition tt = shotMoveInit(
                shotsLivePlayerPane,
                -70,
                2000
        );
        tt.setOnFinished((t) -> {
            LOGGER.log(Level.FINE, "Warez {0} strikes enemy.", w.item.itemName);
            //deck.setCurrentWarez(null);

            tt.setNode(null);
            shotLivePlayerSequence.stop();
            shotsLivePlayerPane.setVisible(false);
            shotsLivePlayerPane.setTranslateY(0);
            shotExplodeDBSequence.start();

            // Start the Warez running. Next tick() will handle the rest.
            w.start();

            // Change any animations for ICE.
            // TODO: Do this instead at each tick() ???
            if (mode == IceMode.BASIC || mode == IceMode.VIRUS || mode == IceMode.ROT) {
                if (w instanceof VirusWarez) { // Thunderhead, Injector, Python, Acid
                    setIceMode(IceMode.VIRUS);
                    // Animation runs for duration of Warez.
                    long startTime = System.nanoTime();
                    AnimationTimer vt = new AnimationTimer() {
                        @Override
                        public void handle(long now) {
                            double elapsedTime = (now - startTime) / 1_000_000.0; // mS
                            if (elapsedTime > (w.getRunDuration())) {
                                if (mode == IceMode.VIRUS) { // Only expire if still in this mode.
                                    setIceMode(IceMode.BASIC);
                                }
                                this.stop();
                            }
                        }
                    };
                    vt.start();
                    //gameState.eraseSoftware(w);
                } else if (w instanceof CorruptorWarez) { // 
                    setIceMode(IceMode.ROT);
                    // Animation runs for duration of Warez.
                    long startTime = System.nanoTime();
                    AnimationTimer vt = new AnimationTimer() {
                        @Override
                        public void handle(long now) {
                            double elapsedTime = (now - startTime) / 1_000_000.0; // mS
                            if (elapsedTime > (w.getRunDuration())) {
                                if (mode == IceMode.ROT) { // Only expire if still in this mode.
                                    setIceMode(IceMode.BASIC);
                                }
                                this.stop();
                            }
                        }
                    };
                    vt.start();
                    //gameState.eraseSoftware(w);
                } else {
                    // Software has a chance of being worn/damaged.
                    LOGGER.log(Level.INFO, "TODO: Wear or Damage software.");
                }
            }
        });
    }

    private void fireShotDB() {
        LOGGER.log(Level.INFO, "Database Fires Attack Round...");

        shotsLiveDBPane.setLayoutY(120);
        shotsLiveDBPane.setVisible(true);
        shotLiveDBSequence.start();
        dbAttackRunning = true;
        gameState.resourceManager.soundFxManager.playTrack(SoundEffectsManager.Sound.ICE_HIT);

        TranslateTransition tt = shotMoveInit(shotsLiveDBPane, 80, db.shotDuration);
        tt.setOnFinished((t) -> {
            LOGGER.log(Level.INFO, "DB Attack finished.");
            dbAttackRunning = false;

            tt.setNode(null);
            shotLiveDBSequence.stop();
            shotsLiveDBPane.setVisible(false);
            shotsLiveDBPane.setTranslateY(0);
            shotExplodePlayerSequence.start();
            gameState.applyEnemyAttack(db.getEffect(gameState)); // Handles death and constitution.
        });
    }

    private void fireShotAI() {
        LOGGER.log(Level.INFO, "Database Fires Attack Round...");

        shotsLiveDBPane.setLayoutY(120);
        shotsLiveDBPane.setVisible(true);
        shotLiveDBSequence.start();
        aiAttackRunning = true;
        gameState.resourceManager.soundFxManager.playTrack(SoundEffectsManager.Sound.ICE_HIT);

        TranslateTransition tt = shotMoveInit(shotsLiveDBPane, 80, db.shotDuration);
        tt.setOnFinished((t) -> {
            LOGGER.log(Level.INFO, "AI Attack finished.");
            aiAttackRunning = false;

            tt.setNode(null);
            shotLiveDBSequence.stop();
            shotsLiveDBPane.setVisible(false);
            shotsLiveDBPane.setTranslateY(0);
            shotExplodePlayerSequence.start();
            gameState.applyEnemyAttack(ai.getEffect()); // Handles death and constitution.
        });
    }

    /**
     * ICE Opacity fades over two seconds
     */
    private void iceBrokenAnimation(Node fadeNode1, Node fadeNode2) {

        fadeNode1.setVisible(true);
        FadeTransition ft1 = new FadeTransition(Duration.millis(3200), fadeNode1);
        ft1.setFromValue(1.0);
        ft1.setToValue(0.0);
        ft1.setCycleCount(1);
        ft1.setAutoReverse(false);
        ft1.setOnFinished((t) -> {
            LOGGER.log(Level.FINE, "ICE Broken Animation Finished.");
            fadeNode1.setVisible(false);
            fadeNode1.setOpacity(1.0);
            if (db.aiClazz != null && !gameState.isAiDefeated(db.aiClazz)) { // Begin AI fight.
                if (!db.aiClazz.equals(NeuromancerAI.class)) {
                    setIceMode(IceMode.AI); // Normal AI fight begin.
                } else {
                    setIceMode(IceMode.NEUROMANCER); // Final boss set up.
                }
            } else {
                LOGGER.log(Level.INFO, "Switch to DB Terminal...");
                setIceMode(IceMode.NONE);
            }
        });
        ft1.play();

        fadeNode2.setVisible(true);
        FadeTransition ft2 = new FadeTransition(Duration.millis(3200), fadeNode2);
        ft2.setFromValue(1.0);
        ft2.setToValue(0.0);
        ft2.setCycleCount(1);
        ft2.setAutoReverse(false);
        ft2.setOnFinished((t) -> {
            fadeNode2.setVisible(false);
            fadeNode2.setOpacity(1.0);
        });
        ft2.play();
    }

    /**
     * AI Face Opacity fades over two seconds
     */
    private FadeTransition aiDefeatedAnimation(Node fadingNode) {

        fadingNode.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(3200), fadingNode);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.setOnFinished((t) -> {
            fadingNode.setVisible(false);
            fadingNode.setOpacity(1.0);
            mode = IceMode.NONE;
            // Next visual pane tick should pick this up and change to DB mode.
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

    /**
     * Changes take place immediatly. Call at animation finishes.
     *
     * @param mode
     */
    private void setIceMode(IceMode mode) {
        LOGGER.log(Level.INFO, "ICE Mode changed to: {0}", mode.name());
        this.mode = mode;
        database.show(mode == IceMode.AI ? -1 : 1);
        gameState.usingDeck.setCurrentWarez(null);

        iceFrontPane.setVisible(mode == IceMode.BASIC);
        iceRearPane.setVisible(mode == IceMode.BASIC);
        iceVirusFrontPane.setVisible(mode == IceMode.VIRUS);
        iceVirusRearPane.setVisible(mode == IceMode.VIRUS);
        iceVirusRotFrontPane.setVisible(mode == IceMode.ROT);
        iceVirusRotRearPane.setVisible(mode == IceMode.ROT);

        if (mode == IceMode.AI || mode == IceMode.NEUROMANCER) {
            if (ai != null) {
                LOGGER.log(Level.INFO, "Begin AI Fight...");
                // Dump the AI responses to LOGGER.
                ai.getDialogs(gameState).dumpList(); // For debug
                aiFace.show(ai.index);
                database.setVisible(false);
                talkPane.setAi(ai);
                talkPane.say(AiTalkPane.Message.LAST);
                gameState.databaseBattle = true;
            } else {
                setIceMode(IceMode.NONE);
                aiFace.show(-1);
            }
        }
    }

    private void processWarezTick() {
        LOGGER.log(Level.FINEST, "processWarezTick()");
        gameState.software.forEach((warez) -> {
            if (warez.isRunning()) {
                // Apply any warez operation to battle.
                switch (warez) {
                    case LinkWarez ww -> { // No effect for battle.
                        // Ignore
                    }

                    // Don't fireShotPlayer() here.  WarezTick is for when the warez attack
                    // has already hit the enemy and does damage instantly or over time.
                    case IceBreakerWarez ww -> { // Standard ICE Damage. Not for AI.
                        switch (mode) {
                            case BASIC, VIRUS, ROT -> {
                                LOGGER.log(Level.FINER, "Using IceBreaker Warez... {0}", warez.item.itemName);
                                db.applyWarezAttack(ww, gameState);
                                ww.tick(gameState);
                            }
                            default -> {
                                LOGGER.log(Level.FINER, "Using IceBreaker Warez...{0}.  Not applicable to non-ICE attacks.", warez.item.itemName);
                                ww.abort(gameState);
                                gameState.resourceManager.soundFxManager.playTrack(SoundEffectsManager.Sound.DENIED);
                            }
                        }
                    }
                    case VirusWarez ww -> { // ICE Breaker but deletes after use.
                        if (mode == IceMode.VIRUS) {
                            LOGGER.log(Level.FINER, "Applying Virus Warez damage {0} to target.", warez.item.itemName);
                            db.applyWarezAttack(ww, gameState);
                            ww.tick(gameState);
                        } else {
                            ww.abort(gameState);
                        }
                    }
                    case CorruptorWarez ww -> {
                        if (mode == IceMode.ROT) {
                            LOGGER.log(Level.FINER, "Applying Corruptor Warez damage {0} to target.", warez.item.itemName);
                            db.applyWarezAttack(ww, gameState);
                            ww.tick(gameState);
                        } else {
                            ww.abort(gameState);
                        }
                    }
                    case ShotgunWarez ww -> { // Use on AI.
                        if (mode == IceMode.AI) {
                            LOGGER.log(Level.FINER, "Using Shotgun Warez...{0}.", warez.item.itemName);
                            ai.applyWarezAttack(ww, gameState);
                            ww.tick(gameState);
                        } else {
                            LOGGER.log(Level.FINER, "Using Shotgun Warez...{0}.  Not applicable to ICE attacks.", warez.item.itemName);
                            ww.abort(gameState);
                            gameState.resourceManager.soundFxManager.playTrack(SoundEffectsManager.Sound.DENIED);
                        }
                    }
                    case ChessWarez ww -> { // Use on AI
                        if (mode == IceMode.AI) {
                            LOGGER.log(Level.FINE, "Using Chess Warez on AI... {0}.", warez.item.itemName);
                            ai.applyWarezAttack(warez, gameState);
                            ww.tick(gameState);
                        } else {
                            LOGGER.log(Level.FINER, "Using Chess Warez...{0}. Not applicable to ICE attacks.", warez.item.itemName);
                            ww.abort(gameState);
                            gameState.resourceManager.soundFxManager.playTrack(SoundEffectsManager.Sound.DENIED);
                        }
                    }
                    default -> {
                        LOGGER.log(Level.FINEST, "Using Non-Applicable Warez...{0}. Ignoring.", warez.item.itemName);
                        warez.abort(gameState);
                    }
                }
            }
        });
    }

    protected void handleKeyEvent(KeyEvent keyEvent) {
        // Only used for when Neuromancer first pops up. Any key press will
        // transport player to Beach scene.
        if (mode == IceMode.NEUROMANCER) {
            LOGGER.log(Level.FINER, "User pressed key to do beach scene.");
            gameState.useDoor = RoomBounds.Door.BEACH;
            cleanup();
        }
    }

    /**
     * Required by AiTalkPane. Not used here.
     *
     * @return
     */
    @Override
    public boolean popupExit() {
        return true;
    }

    @Override
    public void popupExit(RoomMode.Popup newPopup) {
    }

    @Override
    public void showMessage(String message) {
    }

    /**
     * Called if battle ends or death.
     */
    public void cleanup() {
        //
    }
}
