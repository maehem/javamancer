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

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.warez.Warez;
import com.maehem.javamancer.neuro.view.popup.PopupPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class SoftwarePane extends Pane {

    public static final Logger LOGGER = Logging.LOGGER;

    public enum Mode {
        RUN, ERASE, REPAIR
    }

    private static final int SOFT_LIST_WIDTH = 360;
    private static final int SOFT_LIST_HEIGHT = 130;
    private static final int SOFT_LIST_X = 114;
    private static final int SOFT_LIST_Y = 0;
    private final static int SOFT_LIST_SIZE = 4;

    private int slotBase = 0;
    private final GameState gameState;

    private final Mode mode;
    private Warez usedWarez = null;
    private String usedResponse = "";

    public SoftwarePane(GameState gs, Mode mode) {
        this.gameState = gs;
        this.mode = mode;

        setPrefSize(SOFT_LIST_WIDTH, SOFT_LIST_HEIGHT);
        setMinSize(SOFT_LIST_WIDTH, SOFT_LIST_HEIGHT);
        setMaxSize(SOFT_LIST_WIDTH, SOFT_LIST_HEIGHT);
        setLayoutX(SOFT_LIST_X);
        setLayoutY(SOFT_LIST_Y);
        setId("neuro-popup");

        softwarePrompt();
    }

    public final void softwarePrompt() {
        LOGGER.log(Level.CONFIG, "Cyberspace: Show Software Prompt");
        //mode = Mode.SOFTWARE;

        usedWarez = null;
        usedResponse = "";

        setVisible(true);
        getChildren().clear();
        String modeString;
        switch (mode) {
            default -> {
                modeString = "";
            }
            case ERASE -> {
                modeString = "Erase ";
            }
            case REPAIR -> {
                modeString = "Repair ";
            }
        }
        Text softwareHeading = new Text(modeString + "Software");
        Text exitButton = new Text("exit");
        Text prevButton = new Text("prev");
        Text nextButton = new Text("next");
        TextFlow tf = PopupPane.textFlow(softwareHeading);
        tf.setPrefSize(SOFT_LIST_WIDTH, SOFT_LIST_HEIGHT);

        HBox navBox = new HBox(prevButton, exitButton, nextButton);
        navBox.setSpacing(20);
        navBox.setPadding(new Insets(6, 0, 0, 32));

        for (int i = 0; i < SOFT_LIST_SIZE; i++) {
            try {
                Warez w = gameState.software.get(slotBase + i);
                Text itemText = new Text("\n" + (i + 1) + ". " + w.getMenuString());
                tf.getChildren().add(itemText);

                // Add onMouseClick()
                itemText.setOnMouseClicked((t) -> {
                    t.consume();
                    switch (mode) {
                        case RUN -> {
                            useSoftware(w);
                        }
                        case ERASE -> {
                            if (gameState.software.remove(w)) {
                                LOGGER.log(Level.FINE, "Erased software {0} from deck.", w.getSimpleName());
                                slotBase = 0;
                                softwarePrompt();
                            } else {
                                LOGGER.log(Level.SEVERE, "Something went wrong while erasing software {0} from deck.", w.getSimpleName());
                            }
                        }
                        case REPAIR -> {
                            LOGGER.log(Level.SEVERE, "Software repair not omplemented yet.");

                            //repairSoftware();
                        }
                        default ->
                            throw new AssertionError();
                    }
                });
            } catch (IndexOutOfBoundsException ex) {
                tf.getChildren().add(new Text("\n"));
            }
        }
        tf.getChildren().add(new Text("\n"));
        tf.getChildren().add(navBox);
        prevButton.setVisible(slotBase >= SOFT_LIST_SIZE);
        nextButton.setVisible(slotBase + SOFT_LIST_SIZE < gameState.software.size());

        getChildren().add(PopupPane.makeBox(this, tf));

        if (prevButton.isVisible()) {
            prevButton.setOnMouseClicked((t) -> {
                t.consume();
                slotBase -= SOFT_LIST_SIZE;
                softwarePrompt();
            });
        }
        if (nextButton.isVisible()) {
            nextButton.setOnMouseClicked((t) -> {
                t.consume();
                slotBase += SOFT_LIST_SIZE;
                softwarePrompt();
            });
        }
        exitButton.setOnMouseClicked((t) -> {
            LOGGER.log(Level.INFO, "Cyberspace: Exit Software Menu (via mouse click).");
            t.consume();
            setVisible(false);
            //listener.popupExit();
        });

    }

    private void useSoftware(Warez w) {
        LOGGER.log(Level.INFO, "Cyberspace: Use Software: {0}", w.item.itemName);
        usedResponse = w.use(gameState);
        usedWarez = w;
        if (!usedResponse.equals(Warez.USE_OK)) {
            LOGGER.log(Level.INFO, "Use Software Not OK: {0}", usedResponse);
            //displayResponse(usedResponse);
            // TODO: Play 'bad' sound.
            setVisible(false);
        } else {
            gameState.usingDeck.setCurrentWarez(w);
            LOGGER.log(Level.INFO, "Use Software OK.");
            // TODO: Play 'fire', or 'in-use' sound.
            displayUsingSoftware();
            // Leave software pane up for as long as shot takes
            long startTime = System.nanoTime();
            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    // 'now' is a timestamp in nanoseconds
                    // Calculate elapsed time in seconds
                    double elapsedTime = (now - startTime) / 1_000_000.0;
                    if (elapsedTime > 1700) { // Stay up for this many mS.
                        setVisible(false); // Take down the software popup.
                        this.stop();
                    }
                }
            };

            // Start the timer when the application starts
            timer.start();
        }
    }

//    private void displayResponse(String response) {
//        LOGGER.log(Level.INFO, "Show Deck use() response");
//
//        getChildren().clear();
//        setVisible(true);
//        Text heading = new Text(response);
//
//        getChildren().add(PopupPane.makeBox(this, PopupPane.textFlow(heading)));
//
//        setOnMouseClicked((t) -> {
//            t.consume();
//            // Allow user to view response and go back to menu when clicked.
//            setOnMouseClicked(null);
//            softwarePrompt();
//        });
//    }
    private void displayUsingSoftware() {
        LOGGER.log(Level.INFO, "Show Warez use() in progress");

        getChildren().clear();
        setVisible(true);
        Text heading = new Text(usedWarez.getMenuString());

        getChildren().add(PopupPane.makeBox(this, PopupPane.textFlow(heading)));

    }

    public boolean handleKeyEvent(KeyEvent ke) {

        ke.consume();
        KeyCode code = ke.getCode();
        ke.consume();
        switch (code) {
            case X, ESCAPE -> {
                LOGGER.log(Level.INFO, "Cyberspace: SoftwarePane: Exit Pressed...");
                setVisible(false);
            }
            case DIGIT1 -> { // Inventory/Software/Slots
                LOGGER.log(Level.INFO, "Cyberspace: SoftwarePane: 1 Pressed...");
            }
            case DIGIT2 -> {
                LOGGER.log(Level.INFO, "Cyberspace: SoftwarePane: 2 Pressed...");
            }
            case DIGIT3 -> {
                LOGGER.log(Level.INFO, "Cyberspace: SoftwarePane: 3 Pressed...");
            }
            case DIGIT4 -> {
                LOGGER.log(Level.INFO, "Cyberspace: SoftwarePane: 4 Pressed...");
            }
            case N -> {
                LOGGER.log(Level.INFO, "Cyberspace: SoftwarePane: Previous Pressed...");
            }
            case M -> {
                LOGGER.log(Level.INFO, "Cyberspace: SoftwarePane: More Pressed...");
            }
        }

        return false;
    }

    public Warez getUsedWarez() {
        return usedWarez;
    }

    public String getUsedWarezResponse() {
        return usedResponse;
    }
}
