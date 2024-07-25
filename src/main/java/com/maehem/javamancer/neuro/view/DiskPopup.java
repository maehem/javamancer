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
import java.util.logging.Level;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.DIGIT1;
import static javafx.scene.input.KeyCode.DIGIT2;
import static javafx.scene.input.KeyCode.DIGIT3;
import static javafx.scene.input.KeyCode.DIGIT4;
import static javafx.scene.input.KeyCode.L;
import static javafx.scene.input.KeyCode.N;
import static javafx.scene.input.KeyCode.P;
import static javafx.scene.input.KeyCode.Q;
import static javafx.scene.input.KeyCode.S;
import static javafx.scene.input.KeyCode.X;
import static javafx.scene.input.KeyCode.Y;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class DiskPopup extends SmallPopupPane {

    private final PopupListener listener;

    private enum Mode {
        DISK_OPTIONS, LOAD_SCREEN, SAVE_SCREEN, LOAD_SAVE_SUMMARY, LOAD_SUMMARY, PAUSE, QUIT
    }

    private Mode mode = Mode.DISK_OPTIONS;

    public DiskPopup(PopupListener l, GameState gs) {
        super(gs, 280, 128, 110, 240);
        this.listener = l;
        mainMenu();
    }

    private void mainMenu() {
        getChildren().clear();
        mode = Mode.DISK_OPTIONS;
        Text heading = new Text("  Disk Options");

        Text loadText = new Text("L. Load\n");
        Text saveText = new Text("S. Save\n");
        Text pauseText = new Text("P. Pause\n");
        Text quitText = new Text("Q. Quit");
        Text exitText = new Text("       exit");

        TextFlow tf = new TextFlow(loadText, saveText, pauseText, quitText);
        tf.setLineSpacing(-10);

        addBox(heading, tf, exitText);

        exitText.setOnMouseClicked((t) -> {
            listener.popupExit();
        });
        loadText.setOnMouseClicked((t) -> {
            loadScreen();
        });
        saveText.setOnMouseClicked((t) -> {
            saveScreen();
        });
        pauseText.setOnMouseClicked((t) -> {
            pauseScreen();
        });
        quitText.setOnMouseClicked((t) -> {
            quitAskScreen();
        });
    }

    private void quitAskScreen() {
        getChildren().clear();
        mode = Mode.QUIT;
        Text heading = new Text("      Quit");
        Text okToQuitText = new Text("OK to QUIT ");
        Text yesText = new Text("Y");
        Text slashText = new Text("/");
        Text noText = new Text("N");

        TextFlow tf = new TextFlow(okToQuitText, yesText, slashText, noText);
        tf.setLineSpacing(-10);
        tf.setPadding(new Insets(0, 0, 0, 18));

        addBox(heading, tf).setSpacing(20);

        yesText.setOnMouseClicked((t) -> {
            super.quitGame();
        });
        noText.setOnMouseClicked((t) -> {
            mainMenu(); // Back to item's menu.
        });

    }

    private void saveScreen() {
        getChildren().clear();
        mode = Mode.SAVE_SCREEN;
        Text heading = new Text("    Save Game");
        Text oneText = new Text("1");
        Text twoText = new Text("2");
        Text threeText = new Text("3");
        Text fourText = new Text("4");
        Text exitText = new Text("      exit");

        HBox optionsBox = new HBox(oneText, twoText, threeText, fourText);
        optionsBox.setSpacing(24);
        optionsBox.setPadding(new Insets(0, 0, 0, 30));

        addBox(heading, optionsBox, exitText).setSpacing(20);

        oneText.setOnMouseClicked((t) -> {
            saveSlot(0);
        });
        twoText.setOnMouseClicked((t) -> {
            saveSlot(1);
        });
        threeText.setOnMouseClicked((t) -> {
            saveSlot(2);
        });
        fourText.setOnMouseClicked((t) -> {
            saveSlot(3);
        });
        exitText.setOnMouseClicked((t) -> {
            mainMenu();
        });
    }

    private void saveSlot(int slot) {
        LOGGER.log(Level.SEVERE, "Do Load Slot: {0}", slot);
        loadSaveStatus(slot, true);
    }

    private void loadSaveStatus(int slot, boolean status) {
        String modeText1;
        String modeText2;
        switch (mode) {
            case LOAD_SCREEN -> {
                modeText1 = "Load";
                modeText2 = "Loaded";
            }
            default -> {
                modeText1 = "Save";
                modeText2 = "Saved";
            }
        }
        getChildren().clear();
        mode = Mode.LOAD_SAVE_SUMMARY;
        Text heading = new Text("    " + modeText1 + " Game");
        Text statusText;
        if (status) {
            statusText = new Text("   Slot " + (slot + 1) + " " + modeText2);
        } else {
            statusText = new Text("     ERROR!");
        }
        Text okText = new Text("        ok");

        VBox box = addBox(heading, statusText, okText);
        box.setSpacing(20);

        box.setOnMouseClicked((t) -> {
            mainMenu();
        });

    }

    private void loadScreen() {
        getChildren().clear();
        mode = Mode.LOAD_SCREEN;
        Text heading = new Text("    Load Game");
        Text oneText = new Text("1");
        Text twoText = new Text("2");
        Text threeText = new Text("3");
        Text fourText = new Text("4");
        Text exitText = new Text("      exit");

        HBox optionsBox = new HBox(oneText, twoText, threeText, fourText);
        optionsBox.setSpacing(24);
        optionsBox.setPadding(new Insets(0, 0, 0, 30));

        addBox(heading, optionsBox, exitText).setSpacing(20);

        oneText.setOnMouseClicked((t) -> {
            loadSlot(0);
        });
        twoText.setOnMouseClicked((t) -> {
            loadSlot(1);
        });
        threeText.setOnMouseClicked((t) -> {
            loadSlot(2);
        });
        fourText.setOnMouseClicked((t) -> {
            loadSlot(3);
        });
        exitText.setOnMouseClicked((t) -> {
            mainMenu();
        });
    }

    private void loadSlot(int slot) {
        LOGGER.log(Level.SEVERE, "Do Load Slot: {0}", slot);
        loadSaveStatus(slot, true);
    }

    private void pauseScreen() {

        getChildren().clear();
        mode = Mode.LOAD_SAVE_SUMMARY;
        Text heading = new Text("    Game Paused");
        Text statusText = new Text("   press any key");

        VBox box = addBox(heading, statusText);
        box.setSpacing(20);

        box.setOnMouseClicked((t) -> {
            mainMenu();
        });

    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();

        switch (mode) {
            case DISK_OPTIONS -> {
                switch (code) {
                    case L -> {
                        loadScreen();
                    }
                    case S -> {
                        saveScreen();
                    }
                    case P -> {
                        pauseScreen();
                    }
                    case Q -> {
                        quitAskScreen();
                    }
                    case X -> {
                        return super.handleKeyEvent(keyEvent);
                    }
                }
            }
            case LOAD_SCREEN -> {
                switch (code) {
                    case DIGIT1 -> {
                        loadSlot(0);
                    }
                    case DIGIT2 -> {
                        loadSlot(1);
                    }
                    case DIGIT3 -> {
                        loadSlot(2);
                    }
                    case DIGIT4 -> {
                        loadSlot(3);
                    }
                    case X -> {
                        mainMenu();
                    }
                }
            }
            case SAVE_SCREEN -> {
                switch (code) {
                    case DIGIT1 -> {
                        saveSlot(0);
                    }
                    case DIGIT2 -> {
                        saveSlot(1);
                    }
                    case DIGIT3 -> {
                        saveSlot(2);
                    }
                    case DIGIT4 -> {
                        saveSlot(3);
                    }
                    case X -> {
                        mainMenu();
                    }
                }
            }
            case PAUSE, LOAD_SAVE_SUMMARY, LOAD_SUMMARY -> {
                mainMenu();
            }
            case QUIT -> {
                switch (code) {
                    case Y -> {
                        super.quitGame();
                    }
                    case N -> {
                        mainMenu();
                    }
                }
            }
        }

        return false;
    }

}
