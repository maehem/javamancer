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
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.DIGIT1;
import static javafx.scene.input.KeyCode.DIGIT2;
import static javafx.scene.input.KeyCode.DIGIT3;
import static javafx.scene.input.KeyCode.X;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class RomPopup extends SmallPopupPane {

    private final static int WIDTH = 360;

    private final PopupListener listener;

    private enum Mode {
        INTRO, MAIN, DEBUG, ANALYSIS, MONITOR
    }

    private Mode mode = Mode.INTRO;

    public RomPopup(PopupListener l, GameState gs) {
        super(gs, WIDTH, 128, 80, 256);
        this.listener = l;
        romInit();
    }

    private void romInit() {
        getChildren().clear();
        mode = Mode.INTRO;
        Text romSays = new Text("Hey, bro! I'm the Dixie Flatline, best cowboy that ever punched deck.");
        Text continueText = new Text("   Button or [space]");

        TextFlow tf = new TextFlow(romSays);
        tf.setLineSpacing(-10);

        VBox box = addBox(tf, continueText);
        box.setSpacing(30);
        tf.setMaxWidth(WIDTH / 1.333 - 10);

        box.setOnMouseClicked((t) -> {
            mainMenu();
        });
    }

    private void mainMenu() {
        getChildren().clear();
        mode = Mode.MAIN;
        Text heading = new Text(" --- Dixie Flatline ---");

        Text exitText = new Text("X. Exit ROM Construct\n");
        Text oneText = new Text("1. Software Debug\n");
        Text twoText = new Text("2. Software Analysis\n");
        Text threeText = new Text("3. Monitor Mode");

        TextFlow tf = new TextFlow(exitText, oneText, twoText, threeText);
        tf.setLineSpacing(-10);

        addBox(heading, tf);

        exitText.setOnMouseClicked((t) -> {
            listener.popupExit();
        });
        oneText.setOnMouseClicked((t) -> {
            debugScreen();
        });
        twoText.setOnMouseClicked((t) -> {
            analysisScreen();
        });
        threeText.setOnMouseClicked((t) -> {
            monitorScreen();
        });
    }

    private void debugScreen() {
        getChildren().clear();
        mode = Mode.DEBUG;
        Text heading = new Text(" Software Debug");
        Text oneText = new Text("1");
        Text twoText = new Text("2");
        Text threeText = new Text("3");
        Text fourText = new Text("4");
        Text exitText = new Text("      exit");

        HBox optionsBox = new HBox(oneText, twoText, threeText, fourText);
        optionsBox.setSpacing(24);
        optionsBox.setPadding(new Insets(0, 0, 0, 30));

        addBox(heading, optionsBox, exitText).setSpacing(20);

        exitText.setOnMouseClicked((t) -> {
            mainMenu();
        });
    }

    private void analysisScreen() {
        getChildren().clear();
        mode = Mode.ANALYSIS;
        Text heading = new Text("Software Analysis");
        Text oneText = new Text("1");
        Text twoText = new Text("2");
        Text threeText = new Text("3");
        Text fourText = new Text("4");
        Text exitText = new Text("      exit");

        HBox optionsBox = new HBox(oneText, twoText, threeText, fourText);
        optionsBox.setSpacing(24);
        optionsBox.setPadding(new Insets(0, 0, 0, 30));

        addBox(heading, optionsBox, exitText).setSpacing(20);

        exitText.setOnMouseClicked((t) -> {
            mainMenu();
        });
    }

    private void monitorScreen() {
        getChildren().clear();
        mode = Mode.ANALYSIS;
        Text heading = new Text("Software Analysis");
        Text oneText = new Text("1");
        Text twoText = new Text("2");
        Text threeText = new Text("3");
        Text fourText = new Text("4");
        Text exitText = new Text("      exit");

        HBox optionsBox = new HBox(oneText, twoText, threeText, fourText);
        optionsBox.setSpacing(24);
        optionsBox.setPadding(new Insets(0, 0, 0, 30));

        addBox(heading, optionsBox, exitText).setSpacing(20);

        exitText.setOnMouseClicked((t) -> {
            mainMenu();
        });
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();

        switch (mode) {
            case INTRO -> {
                if (code.equals(KeyCode.SPACE)) {
                    mainMenu();
                }
            }
            case MAIN -> {
                switch (code) {
                    case DIGIT1 -> {
                        debugScreen();
                    }
                    case DIGIT2 -> {
                        analysisScreen();
                    }
                    case DIGIT3 -> {
                        monitorScreen();
                    }
                    case X -> {
                        return super.handleKeyEvent(keyEvent);
                    }
                }
            }
            case DEBUG -> {
                switch (code) {
                    case X -> {
                        mainMenu();
                    }
                }
            }
            case ANALYSIS -> {
                switch (code) {
                    case X -> {
                        mainMenu();
                    }
                }
            }
            case MONITOR -> {
                switch (code) {
                    case X -> {
                        mainMenu();
                    }
                }
            }
        }

        return false;
    }

}
