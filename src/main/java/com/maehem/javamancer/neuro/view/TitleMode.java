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
import com.maehem.javamancer.neuro.view.ui.LoadSaveDialog;
import com.maehem.javamancer.neuro.view.ui.NakedButton;
import com.maehem.javamancer.neuro.view.ui.NameChooserDialog;
import java.util.logging.Level;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class TitleMode extends NeuroModePane {

    private NameChooserDialog newGameDialog = null;
    private LoadSaveDialog loadSaveDialog = null;
    private final TextFlow quitBox;

    public TitleMode(NeuroModePaneListener listener, GameState gameState) {
        super(listener, gameState);
        gameState.resourceManager.musicManager.playTrack(MusicManager.Track.TITLE, 0.7, 30000, 1500, 500);

        ImageView titleView = new ImageView(getResourceManager().getSprite("TITLE_1"));
        ImageView snowBackground = makeSnowBackground(
                titleView.getImage().getWidth(),
                titleView.getImage().getHeight(),
                4);

        getChildren().addAll(snowBackground, titleView);

        Text quitButton = new Text("Quit");

        quitButton.setOnMouseClicked((t) -> {
            getListener().neuroModeActionPerformed(NeuroModePaneListener.Action.QUIT, null);
        });

        // For reasons I don't understand, the text flow must have an
        // actual JavaFX Button inside it for any key events to work. No idea
        // why. Tried all kinds of things.
        // Had to put one in both text flows for CSS reasons. Not able to
        // figure out padding numbers that would work for both.
        Button fakeButton = new NakedButton("");
        Button fakeButton2 = new NakedButton("");
        Text newButton = new Text(" New");
        newButton.setOnMouseClicked((t) -> {
            doNewGameDialog();
        });
        Text loadButton = new Text("Load ");
        loadButton.setOnMouseClicked((t) -> {
            doLoadSaveDialog();
        });

        TextFlow newLoadBox2 = new TextFlow(newButton, new Text("/"), loadButton, fakeButton);
        newLoadBox2.setId("neuro-popup");
        newLoadBox2.setLayoutX(80);
        newLoadBox2.setLayoutY(310);
        newLoadBox2.getTransforms().add(TEXT_SCALE);

        quitBox = new TextFlow(quitButton, fakeButton2);
        quitBox.setId("neuro-popup");
        quitBox.setLayoutX(476);
        quitBox.setLayoutY(310);
        quitBox.getTransforms().add(TEXT_SCALE);

        getChildren().addAll(newLoadBox2, quitBox);

        addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent t) -> {
            LOGGER.log(Level.SEVERE, "TitleMode: Key pressed.");
            if (newGameDialog != null) {
                if (t.getCode().equals(KeyCode.ESCAPE)) {
                    getChildren().remove(newGameDialog);
                    newGameDialog = null;
                    quitBox.setVisible(true);
                } else {
                    newGameDialog.keyEvent(t);
                }
            } else if (loadSaveDialog != null && loadSaveDialog.isVisible()) {
                if (loadSaveDialog.keyEvent(t)) {
                    getChildren().remove(loadSaveDialog);
                    loadSaveDialog = null;
                }
            } else {
                switch (t.getCode()) {
                    case KeyCode.N -> {
                        doNewGameDialog();
                    }
                    case L -> {
                        doLoadSaveDialog();
                    }
                    case Q, X -> {
                        //quitButton.fire();
                        getListener().neuroModeActionPerformed(NeuroModePaneListener.Action.QUIT, null);
                    }
                    case COMMA -> {
                        LOGGER.log(Level.CONFIG, "User pressed COMMA Key. Toggle Sound Mute");
                        getListener().neuroModeActionPerformed(NeuroModePaneListener.Action.MUTE_MUSIC, null);
                    }
                }
            }
        });

    }

    private void doNewGameDialog() {
        if (newGameDialog != null) {
            getChildren().remove(newGameDialog);
            newGameDialog = null;
        }
        newGameDialog = new NameChooserDialog(this);
        getChildren().add(newGameDialog);
        quitBox.setVisible(false);
    }

    private void doLoadSaveDialog() {
        if (loadSaveDialog != null) {
            getChildren().remove(loadSaveDialog);
            loadSaveDialog = null;
        }
        loadSaveDialog = new LoadSaveDialog(LoadSaveDialog.Type.LOAD, getGameState());
        loadSaveDialog.setLayoutX(82);
        loadSaveDialog.setLayoutY(256);

        getChildren().add(loadSaveDialog);
    }

    public void acceptName(String name) {
        getGameState().resourceManager.musicManager.fadeOutTrack(MusicManager.Track.TITLE, 3000);

        getListener().neuroModeActionPerformed(NeuroModePaneListener.Action.NEW_GAME, new Object[]{name});
    }

    private ImageView makeSnowBackground(double w, double h, int intensity) {

        WritableImage img = new WritableImage((int) w, (int) h);
        PixelWriter pw = img.getPixelWriter();
        for (int y = 0; y < h; y += 2) {
            for (int x = 0; x < w; x += 2) {
                Color randomGrey = randomGrey(intensity);
                pw.setColor(x, y, randomGrey);
                pw.setColor(x + 1, y, randomGrey);
                pw.setColor(x, y + 1, randomGrey);
                pw.setColor(x + 1, y + 1, randomGrey);
            }
        }

        return new ImageView(img);
    }

    /**
     * The sky above the port was the color of television tuned to a dead
     * channel.
     *
     * @param intensity
     * @return
     */
    private Color randomGrey(int intensity) {
        if (intensity > 64) {
            intensity = 64;
        }
        if (intensity < 0) {
            intensity = 0;
        }
        double random = Math.random();
        if (random > 0.8) {
            random += Math.random() * 1.0;
        }

        double grey = random * random * intensity / 64;
        return new Color(grey, grey, grey, 1.0);
    }

    @Override
    public void tick() {

    }

    @Override
    public void destroy() {
        newGameDialog = null;
        loadSaveDialog = null;
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
