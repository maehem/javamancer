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
import com.maehem.javamancer.neuro.view.ui.BorderButton;
import com.maehem.javamancer.neuro.view.ui.LoadSaveDialog;
import com.maehem.javamancer.neuro.view.ui.NakedButton;
import java.util.Optional;
import java.util.logging.Level;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class TitleMode extends NeuroModePane {

    private NewGameDialog newGameDialog = null;

    public TitleMode(NeuroModePaneListener listener, ResourceManager resourceManager, GameState gameState) {
        super(listener, resourceManager, gameState);

        ImageView titleView = new ImageView(getResourceManager().getSprite("TITLE_1"));
        ImageView snowBackground = makeSnowBackground(
                titleView.getImage().getWidth(),
                titleView.getImage().getHeight(),
                4);

        getChildren().addAll(snowBackground, titleView);

        Button newButton = new NakedButton(" New");
        newButton.setOnAction((t) -> {
            if (newGameDialog != null) {
                getChildren().remove(newGameDialog);
                newGameDialog = null;
            }
            newGameDialog = new NewGameDialog(this);
            getChildren().add(newGameDialog);
        });
        Button loadButton = new NakedButton("Load ");
        loadButton.setOnAction((t) -> {
            doLoadDialog();
        });

        TextFlow newLoadBox2 = new TextFlow(newButton, new Text("/"), loadButton);
        newLoadBox2.setId("neuro-popup");
        newLoadBox2.setLayoutX(80);
        newLoadBox2.setLayoutY(310);
        newLoadBox2.getTransforms().add(TEXT_SCALE);

        Button quit = new BorderButton("Quit", 476, 310);
        quit.getTransforms().add(TEXT_SCALE);

        quit.setOnAction((t) -> {
            getListener().neuroModeActionPerformed(NeuroModePaneListener.Action.QUIT, null);
        });

        getChildren().addAll(newLoadBox2, quit);

        setOnKeyPressed((t) -> {
            if (newGameDialog != null) {
                newGameDialog.keyEvent(t);
            } else {
                switch (t.getCode()) {
                    case KeyCode.N -> {
                        newButton.fire();
                    }
                    case L -> {
                        loadButton.fire();
                    }
                    case Q, X -> {
                        quit.fire();
                    }
                }
            }
        });

    }

    public void acceptName(String name) {
        getListener().neuroModeActionPerformed(NeuroModePaneListener.Action.NEW_GAME, new Object[]{name});
    }

    private void doLoadDialog() {
        LoadSaveDialog dialog = new LoadSaveDialog(LoadSaveDialog.Type.LOAD, getScene().getWindow());
        Optional<Integer> result = dialog.showAndWait();
        Integer selected = null;
        if (result.isPresent()) {
            selected = result.get();
            LOGGER.log(Level.SEVERE, "User wants to load {0}.", selected);
            getListener().neuroModeActionPerformed(NeuroModePaneListener.Action.LOAD, new Object[]{selected});
        } else if (selected == null) {
            // Nothing happes.
            LOGGER.log(Level.SEVERE, "User aborted Load dialog.");
        }
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

    }

    /**
     * GameState values have changed. Update relevant things.
     *
     */
    @Override
    public void updateStatus() {

    }

    private class NewGameDialog extends SmallPopupPane {

        private final StringBuilder typedName = new StringBuilder();
        private final Text typedText = new Text();
        private final TitleMode titleMode;

        public NewGameDialog(TitleMode l) {
            super(null, null);
            this.titleMode = l;

            setLayoutX(146);
            setLayoutY(106);


            TextFlow tf = new TextFlow(
                    new Text("YOUR NAME?\n\n"),
                    typedText, new Text("<")
            );
            tf.getTransforms().add(new Scale(TEXT_SCALE, 1.0));
            tf.setPadding(new Insets(10));
            getChildren().add(tf);
        }

        public void keyEvent(KeyEvent ke) {
            KeyCode code = ke.getCode();
            if ((code.isLetterKey() | code.isDigitKey()) && typedName.length() < 12) {
                typedName.append(code.getChar().toUpperCase());
                typedText.setText(typedName.toString());
            } else if (code.equals(KeyCode.ENTER)) {
                // Accept and finish
                titleMode.acceptName(typedName.toString());
            } else if (code.equals(KeyCode.ESCAPE)) {
                // Escape
                setVisible(false);
            } else if (code.equals(KeyCode.BACK_SPACE)) {
                if (typedName.length() > 0) {
                    typedName.delete(typedName.length() - 1, typedName.length());
                    typedText.setText(typedName.toString());
                }
            }
        }

    }

}
