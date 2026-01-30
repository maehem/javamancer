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
package com.maehem.javamancer.neuro.view.database;

import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.TextResource;
import com.maehem.javamancer.neuro.view.PopupListener;
import java.util.logging.Level;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * <pre>
 * [0] :: * Psychologist *
 * [1] :: X. Exit System 1. Read Molly Sample 2. Read Corto Sample 3. Read Snowman Sample
 * [2] :: 4. Activate mindprobe 5. Review last session
 * [3] :: 6. Software Library
 * [4] :: PSYCHOLOGIST is an intensely personal analysis service for an elite clientele.  It provides a socially- acceptable outlet for private frustrations, phobias, and general concerns.  New users can sample on-going mindprobe sessions for insight into their own personal problems.  After the initial contact, new users will be assigned a personal password.
 * [5] :: If you wish to initiate your own mindprobe session by entering information for the Psych to analyze, you must log in under your personal password.  The reasonable fee for this service will vary according to the severity of your problem. If youre a new user, enter the password, "NEW MO".
 * [6] :: Welcome to PSYCHOLOGIST.  If it makes you feel more comfortable, you can think of me as "Sigmund."  If you decide to proceed with analysis under your personal password, I will be your analyst.  At the moment, however, your options are limited to browsing through other peoples mindprobe sessions. In future contact with this service, your personal password will be: BABYLON
 * [7] :: Welcome to PSYCHOLOGIST.  Im Sigmund, your personal mindprobe analyst.  You now have the choice of browsing through other mindprobe sessions or starting one of your own.  Your fee will be based on the severity of your problem if you proceed with a personal mindprobe.  After completing a mindprobe session, Ill need a few hours to review your case, so theres no need for you to wait around.  I wont be offended if you leave abruptly.  By activating a mindprobe session, you accept full responsibility for your own well-being.  You also agree to hold the Psych staff harmless from any loss, expense, mental damage, or liability arising out of mindprobe analysis.  This is no place for a weak mind.
 * [8] ::  Software Library  X. Exit To Main 1. Thunderhead 1.0
 * [9] :: Enter your thoughts.
 * [10] :: Send these thoughts. (Y/N)
 * [11] :: Press ESC to end.
 *
 * </pre>
 */
/**
 *
 * TODO: Mind Probe
 *
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class PsychologistDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, PRE_MENU, MENU, PROBE, REVIEW
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.
    private boolean preMenuShown = false;

    public PsychologistDatabaseView(GameState gs, Pane p, PopupListener l) {
        super(gs, p, l);
        landingPage();
    }

    @Override
    protected final void landingPage() {
        pane.getChildren().clear();
        mode = Mode.SUB;

        Text helloText;
        if (accessLevel > 1) { // babylon
            helloText = new Text("\n" + dbTextResource.get(4) + "\n\n");
        } else { // new mo
            helloText = new Text("\n" + dbTextResource.get(5) + "\n\n");
        }

        TextFlow tf = pageTextFlow(headingText, helloText, CONTINUE_TEXT);
        pane.getChildren().add(tf);
    }

    @Override
    protected final void siteContent() {
        mainMenu();
    }

    private void mainMenu() {
        pane.getChildren().clear();

        if (!preMenuShown) {

            mode = Mode.PRE_MENU;
            preMenuShown = true;
            // Show pre-menu content based on access level.
            TextFlow tf;
            if (accessLevel < 2) {
                Text text = new Text("\n" + dbTextResource.get(6) + "\n\n");
                tf = pageTextFlow(headingText, text, CONTINUE_TEXT);
            } else {
                Text text = new Text(dbTextResource.get(7));
                tf = pageTextScrolledFlow(headingText, text);
            }
            pane.getChildren().add(tf);
            pane.setOnMouseClicked((t) -> {
                t.consume();
                mainMenu();
            });
        } else {
            mode = Mode.MENU;

            TextFlow tf = pageTextFlow(headingText);

            String menuString = dbTextResource.get(1);
            if (accessLevel > 1) { // Your own personal mind probe
                menuString += "\r" + dbTextResource.get(2);
            }
            if (accessLevel > 2) { // Software Library
                menuString += "\r" + dbTextResource.get(3);
            }
            String[] split = menuString.split("\\r");
            for (String s : split) {
                Text menuItem = new Text("\n         " + s);
                tf.getChildren().add(menuItem);
                menuItem.setOnMouseClicked((t) -> {
                    t.consume();
                    itemPage(s.trim().substring(0, 1));
                });
            }

            pane.getChildren().add(tf);
            pane.setOnMouseClicked(null);
        }
        pane.layout();
    }

    private void itemPage(String itemLetter) {
        switch (itemLetter) {
            case "X" -> {
                listener.popupExit();
            }
            case "1" -> {
                readReport(2);
            }
            case "2" -> {
                readReport(3);
            }
            case "3" -> {
                readReport(1);
            }
            case "4" -> {
                if (accessLevel > 1) {
                    mindProbe();
                }
            }
            case "5" -> {
                if (accessLevel > 1) {
                    readReport(0);
                }
            }
            case "6" -> {
                if (accessLevel > 2) {
                    downloads();
                }
            }
        }
    }

    private void readReport(int index) {
        LOGGER.log(Level.FINE, "Read Report.");
        pane.getChildren().clear();
        mode = Mode.REVIEW;

        TextResource textResource = gameState.resourceManager.getBihResource("PSYCHO" + index);

        textResource.dumpList();

        StringBuilder sb = new StringBuilder();
        // TODO: Index 0 is player reports. There are 4 possible reports.
        // TODO: gameState.psychoVisits
        if (index == 0) {
            int subIndex = gameState.psychoProbeCount - 1;
            if (subIndex < 0) { // Never probed.
                // dbTextResource
                // 6 = access > 1 && probeCount == 0;
                // 7 = access > 1 && probeCount > 0
                if (accessLevel <= 1) {
                    sb.append(dbTextResource.get(5));
                } else if (gameState.psychoProbeCount < 1) {
                    sb.append(dbTextResource.get(6));
                } else {
                    sb.append(dbTextResource.get(7));
                }
            } else if (subIndex < 4) { // First four probes.
                sb.append(textResource.get(subIndex));
            } else { // Max probes.
                sb.append(textResource.get(3));
            }
        } else {
            sb.append(textResource.get(0));
        }

        Text text = new Text(sb.toString());
        TextFlow pageTf = pageTextScrolledFlow(headingText, text);

        pane.getChildren().add(pageTf);
        pane.setOnMouseClicked((t) -> {
            t.consume();
            mainMenu();
        });
    }

    private void mindProbe() {
        LOGGER.log(Level.FINE, "Mind Probe. TODO");
        pane.getChildren().clear();
        mode = Mode.PROBE;

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("TODO:  Mind Probe...");
        gameState.psychoProbeCount++;

        Text text = new Text(sb.toString());
        text.setLineSpacing(LINE_SPACING);
        TextFlow pageTf = pageTextScrolledFlow(headingText, text);

        pane.getChildren().add(pageTf);
        pane.setOnMouseClicked((t) -> {
            t.consume();
            mainMenu();
        });
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        LOGGER.log(Level.FINE, "Handle key event.");
        switch (mode) {
            case MENU -> {
                if (code.equals(KeyCode.X)
                        || code.equals(KeyCode.SPACE)
                        || code.equals(KeyCode.ESCAPE)) {
                    LOGGER.log(Level.INFO, "Menu wants to exit system.");
                    keyEvent.consume();
                    return true;
                } else if (code.isDigitKey()) {
                    keyEvent.consume();
                    itemPage(code.getChar());
                    return false;
                }
            }
            case PRE_MENU -> {
                if (code.equals(KeyCode.SPACE)) {
                    LOGGER.log(Level.FINE, "Proceed to main menu.");
                    mainMenu();
                    keyEvent.consume();
                    return false;
                }
            }
            case PROBE, REVIEW -> {
                if (code.equals(KeyCode.X)
                        || code.equals(KeyCode.ESCAPE)) {
                    LOGGER.log(Level.FINE, "Go back up menu level.");
                    mainMenu();
                    keyEvent.consume();
                    return false;
                }
            }
            // else ignore key

        }
        return super.handleKeyEvent(keyEvent);
    }
}
