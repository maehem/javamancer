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
import com.maehem.javamancer.neuro.model.item.DeckItem;
import com.maehem.javamancer.neuro.view.PopupListener;
import java.util.logging.Level;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * <pre>
[0] :: * World Chess Confederation *
 * [1] :: X. Exit System 1. About this system 2. About the Tournaments 3. Membership Application
 * [2] :: 4. Enter Tournament 5. Challenge Morphy
 * [3] :: 6. Software Library 7. AI Message Buffer
 * [4] :: Youve entered the wonderful world of chess.  Please feel free to look around.  If we interest you, please join us.  For access enter "NOVICE".
 * [5] :: The World Chess Confederation computer network has been around since the  beginning of computer networks.  In fact, the WCC has always supported  computerized competitions and sponsored the first Cyberspace competition ever. Though that match between Palos Morphy and Victor Lavaska ended in tragedy, the WCC has held many thousands of successful matches since then.  All persons  interested in joining the daily tournaments are fully encouraged to become WCC members.  The WCC network allows members a chance to play against the best and, if you think yourself worthy, you can even pit your skills against those of the legendary simulation of Palos Morphy himself.
 * [6] :: Daily Tournaments The WCC sponsors daily chess tournaments in which any member can  participate.  Players are allowed to use optimizer programs matched to their level of play.  The classes of competition are listed below:  1. unranked beginner 2. unranked Novice 3. ranked Novice 4. Apprentice 5. Journeyman 6. Junior Master 7. Master 8. Grandmaster 9. Victor  Cash prizes are awarded in each category on a daily basis.
 * [7] :: If youd like to become a member, select between the two membership options:       X. Exit      T. Temporary Membership $10      F. Full Membership     $150  Temporary membership allows participation in one tournament.  Full membership allows participation in all tournaments and activities as a club member.
 * [8] :: The fee has been deducted from        your credit chip.    Your new password is "MEMBER".
 * [9] :: Insufficient credits in credit chip
 * [10] ::  Software Library  X. Exit System 1. BattleChess 4.0
 * [11] :: Transmitting...
 * [12] :: Download complete.
 * [13] :: Unable to download.
 * [14] :: Please upload your software.
 * [15] ::         Incompatible software.
 * [16] :: vs 
 * [17] :: playing game...
 * [18] :: You won, your ranking has been upgraded to Prize money of      credits has been added to your credit chip.
 * [19] :: Sorry you lost.
 * [20] :: unranked beginner.
 * [21] :: unranked Novice.
 * [22] :: ranked Novice.
 * [23] ::
 * [24] ::
 * [25] ::
 * [26] ::
 * [27] ::
 * [28] ::
 * [29] :: contestant 1
 * [30] :: contestant 2
 * [31] :: contestant 3
 * [32] ::
 * [33] ::
 * [34] ::
 * [35] ::
 * [36] ::
 * [37] ::
 * [38] :: Palos Morphy
 * [39] ::   If you think youre ready for me,    look for me in cyberspace...Ill    be waiting.
 * [40] :: Temporary members are allowed only one game.
 * [41] :: To: Morphy From: Greystoke    The AI destruct program that Turing developed to destroy me is missing from their vault!  I am unable to locate it!  If you know where it is, inform me immediately!  If it falls into the wrong hands, Neuromancer may win!  This cannot happen!
 * [42] :: To: Morphy From: Hal    Ive intercepted an odd transmission on a frequency that isnt used much any more. It seems to be from an AI whos a chess master, so I thought Id let you know there might be some decent competition out there. He calls himself the Phantom.
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class WorldChessDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, ABOUT, APPLY, TOURNAMENT, MORPHY
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public WorldChessDatabaseView(GameState gs, Pane p, PopupListener l) {
        super(gs, p, l);

        //dbTextResource.dumpList();
        if (gameState.usingDeck.getMode() == DeckItem.Mode.CYBERSPACE) {
            accessLevel = 3;
            siteContent();
        } else {
            landingPage();
        }
    }

    @Override
    protected final void landingPage() {
        pane.getChildren().clear();
        mode = Mode.SUB;

        Text helloText = new Text(dbTextResource.get(4) + "\n\n\n");

        TextFlow tf = pageTextFlow(headingText, helloText, CONTINUE_TEXT);
        pane.getChildren().add(tf);
    }

    @Override
    protected final void siteContent() {
        mainMenu();
    }

    private void mainMenu() {
        pane.getChildren().clear();
        mode = Mode.MENU;

        TextFlow tf = pageTextFlow(headingText);

        String menuString = dbTextResource.get(1);
        if (accessLevel > 1) {
            menuString += "\r" + dbTextResource.get(2);
        }
        if (accessLevel > 2) {
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

    private void itemPage(String itemLetter) {
        switch (itemLetter) {
            case "X" -> {
                listener.popupExit();
            }
            case "1" -> { // About system
                viewText(5);
            }
            case "2" -> { // About tournaments
                viewText(6);
            }
            case "3" -> {
                applyMembership();
            }
            case "4" -> {
                if (accessLevel > 1) {
                    enterTournament();
                }
            }
            case "5" -> {
                if (accessLevel > 1) {
                    challengeMorphy();
                }
            }
            case "6" -> {
                if (accessLevel > 2) {
                    downloads();
                }
            }
            case "7" -> {
                if (accessLevel > 2) {
                    messages();
                }
            }
        }
    }

    private void applyMembership() {
        LOGGER.log(Level.FINE, "World Chess: apply membership ");
        pane.getChildren().clear();
        mode = Mode.APPLY;

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(dbTextResource.get(7));

        Text text = new Text(sb.toString());
        text.setLineSpacing(LINE_SPACING);
        TextFlow pageTf = pageTextScrolledFlow(headingText, text);

        pane.getChildren().add(pageTf);
        pane.setOnMouseClicked((t) -> {
            t.consume();
            mainMenu();
        });
    }

    private void enterTournament() {
        LOGGER.log(Level.FINE, "World Chess: enter tournament");
        pane.getChildren().clear();
        mode = Mode.TOURNAMENT;

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(dbTextResource.get(14));

        Text text = new Text(sb.toString());
        text.setLineSpacing(LINE_SPACING);
        TextFlow pageTf = pageTextScrolledFlow(headingText, text);

        pane.getChildren().add(pageTf);
        pane.setOnMouseClicked((t) -> {
            t.consume();
            mainMenu();
        });
    }

    private void challengeMorphy() {
        LOGGER.log(Level.FINE, "World Chess: challenge morphy");
        pane.getChildren().clear();
        mode = Mode.MORPHY;

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(dbTextResource.get(14));

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
            case ABOUT, APPLY, MORPHY, TOURNAMENT -> {
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
