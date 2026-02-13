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

import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.view.ui.EndGameDialog;
import java.util.logging.Level;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class EndGameMode extends NeuroModePane {

    private EndGameDialog endGameDialog = null;

    public EndGameMode(NeuroModePaneListener listener, GameState gameState) {
        super(listener, gameState);

        endGameDialog = new EndGameDialog(gameState);

        // TODO Music Block.mp3
        gameState.resourceManager.musicManager.playTrack(MusicManager.Track.END_GAME, 0.5, 5000, 1500, 500);

        Image sprite = getResourceManager().getSprite("ENDGAME_1");
        ImageView backdropView = new ImageView(sprite);
        Rectangle r = new Rectangle(sprite.getWidth(), sprite.getHeight());
        r.setFill(Color.BLACK);

        getChildren().addAll(r, backdropView, endGameDialog);

        addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent t) -> {
            LOGGER.log(Level.FINE, "EndGameMode: Key pressed.");

            switch (t.getCode()) {
                case Q, X, ESCAPE -> {
                    //quitButton.fire();
                    getListener().neuroModeActionPerformed(NeuroModePaneListener.Action.QUIT, null);
                    LOGGER.log(Level.FINE, "Finished with end game dialog. Move to credits.");
                }
                case COMMA -> {
                    LOGGER.log(Level.CONFIG, "User pressed COMMA Key. Toggle Sound Mute");
                    getListener().neuroModeActionPerformed(NeuroModePaneListener.Action.MUTE_MUSIC, null);
                }
                case SPACE -> {
                    endGameDialog.keyEvent(t);
                }
            }

        });

    }

    @Override
    public void tick() {

    }

    @Override
    public void destroy() {
    }

    /**
     * GameState values have changed. Update relevant things.
     *
     */
    @Override
    public void updateStatus() {
        // Not used here.
    }

}
