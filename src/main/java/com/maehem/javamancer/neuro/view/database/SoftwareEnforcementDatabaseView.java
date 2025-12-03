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
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * <pre>
 * [0] :: * Software Enforcement Agency *
 * [1] :: X. Exit System 1. Supervisors Memo 2. Bulletin Board 3. Software Library 4. Skill Upgrade 5. View Arrest Warrant List
 * [2] :: 6. View Surveillance List
 * [3] :: SEA FIELD SUPERVISORS:  Field Supervisors should take note of the new matrix simulator protection softwarez that have just been made available on this system.  It will be the responsibility of the Field Supervisors to disseminate copies of these warez to their qualified cyberspace operatives. Due to the high number of requests for current information, the Administrative Bureau has approved the activation of a question-and-answer bulletin board service on this system.  Proper use of this service should increase the speed of response for Field Supervisor information-gathering purposes.
 * [4] :: SEA FIELD AGENTS:  Field Agents who would like to improve their interrogation and conversational abilities would be well advised to make use of the COPTALK tutorial.  Fourth level CopTalk ability is required for Senior Field Agent status.  While this tutorial has been available to download for the last eight months, our records show that usage has been light.  Lets see more ambition out there!
 * [5] ::  Software Library  X. Exit To Main 1. Comlink 4.0 2. Sequencer 1.0
 * [6] :: 3. ThunderHead 2.0
 * [7] :: Date: 11/16/58 From: Gibson, W.  One of my field agents, working undercover as a cyberspace cowboy known as "MR. DOS," seems to have vanished.  He was on the trail of some microsoft design thieves in cyberspace when I last heard from him.  Now hes been missing for two weeks.  Has he been reassigned to a deep cover on another operation?  Is he on loan to the Turing people?  And why wasnt I informed that he was off my team?
 * [8] :: Date: 11/16/58 To:   GIBSON, W.  With regard to your missing field agent, operating as "MR. DOS," we have no idea where he is.  He has not been reassigned elsewhere.  Maybe you misplaced him?
 * [9] :: Date: 11/16/58 From: GIBSON, W.  Regarding my field agent, "MR. DOS," I respectfully request an explanation as to how you think I could have misplaced him.  This isnt a piece of software were talking about.  MR. DOS is a human field agent.
 * [10] :: Date: 11/16/58 To:   GIBSON, W.  Solve your own problems.  Thats what we hired you for in the first place.
 * [11] ::     Arrest Warrants
 * [12] ::     Surveillance List
 * [13] ::   Skill Upgrade  X. Exit To Main 1. Coptalk (lvl 2)
 * [14] :: 2. Coptalk (lvl 4)
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class SoftwareEnforcementDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, WARRANTS, SKILL
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public SoftwareEnforcementDatabaseView(GameState gs, Pane p, PopupListener l) {
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

        Text helloText = new Text("\n\n\n\n");

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
        if (accessLevel > 2) {
            menuString += "\r" + dbTextResource.get(2);
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
            case "1" -> {
                memos();
            }
            case "2" -> {
                messages();
            }
            case "3" -> {
                downloads();
            }
            case "4" -> {
                skillUpgrade();
            }
            case "5" -> {
                //if (accessLevel > 1) {
                mode = Mode.SUB;
                editablePersonList(gameState.seaWantedList, 11, "SEA0", true);
                //}
                //personList(11, "SEA0", true);
            }
            case "6" -> {
                if (accessLevel > 2) {
                    personList(12, "SEA1", true);
                }
            }
        }
    }

    private void memos() {
        LOGGER.log(Level.FINE, "Software Enforcement: warrant list");
        pane.getChildren().clear();

        Text subHeadingText = new Text("\n"
                + dbTextResource.get(3) + "\n\n"
                + dbTextResource.get(4) + "\n\n"
        );

        TextFlow contentTf = simpleTextFlow(subHeadingText);
        contentTf.setPadding(new Insets(0, 0, 0, 30));

        TextFlow pageTf = pageTextScrolledFlow(headingText, contentTf);

        pane.getChildren().add(pageTf);
        pane.setOnMouseClicked((t) -> {
            t.consume();
            mainMenu();
        });
    }

    private void skillUpgrade() {
        pane.getChildren().clear();
        mode = Mode.SKILL;

        TextFlow tf = pageTextFlow(headingText);

        String menuString = dbTextResource.get(13);
        String[] split = menuString.split("\\r");
        for (String s : split) {
            Text menuItem = new Text("\n         " + s);
            tf.getChildren().add(menuItem);
            if (s.startsWith("X.")
                    || s.startsWith("1.")) {
                menuItem.setOnMouseClicked((t) -> {
                    t.consume();
                    switch (s.trim().substring(0, 1)) {
                        case "X" -> {
                            mainMenu();
                        }
                        case "1" -> {
                            attemptSkillUpgrade();
                        }
                    }

                });
            }
        }

        pane.getChildren().add(tf);
        pane.setOnMouseClicked(null);
    }

//    private void _warrantList(int num) {
//        LOGGER.log(Level.FINE, "Software Enforcement: warrant list");
//        pane.getChildren().clear();
//        mode = Mode.WARRANTS;
//
//        Text subHeadingText = new Text("\n" + dbTextResource.get(11) + "\n\n");
//
//        TextFlow contentTf = simpleTextFlow(subHeadingText);
//        contentTf.setPadding(new Insets(0, 0, 0, 30));
//
//        TextResource personList = gameState.resourceManager.getTextResource("SEA" + num);
//        int i = 0;
//        for (String item : personList) {
//            String[] split = item.split("\t");
//            int reason = split[2].charAt(3);
//            Text t = new Text("\n" + split[0] + split[1] + " " + WANTED[reason]);
//
//            contentTf.getChildren().add(t);
//            i++;
//        }
//
//        TextFlow pageTf = pageTextScrolledFlow(headingText, contentTf);
//
//        pane.getChildren().add(pageTf);
//        pane.setOnMouseClicked((t) -> {
//            t.consume();
//            mainMenu();
//        });
//    }
    private void attemptSkillUpgrade() {
        LOGGER.log(Level.FINE, "SEA: Attempt Skill Upgrade");
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
            case WARRANTS -> {
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

    @Override
    protected void handlePersonListChanged() {
        // TODO: What NPCs are affected by this?
//        for (Person p : gameState.seaWantedList) {
//            if (p.getBama().equals(GameState.LARRY_MODE_BAMA)) {
//                gameState.larryMoeWanted = true;
//                return;
//            }
//        }
    }
}
