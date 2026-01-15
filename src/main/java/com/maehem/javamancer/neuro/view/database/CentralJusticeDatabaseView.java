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
[0] :: * Chiba City Central Justice System *
 * [1] :: X. Exit System 1. Press Release
 * [2] :: 2. Arrest Warrants 3. Accounting
 * [3] :: Public Defender Press Release ** Administrative Memo 414 ** RE: Text of new Press Release Certain radical factions among the criminal citizens of Chiba City have implied that the Justice Booth system is merely a thinly-disguised method of supplying the city with a needed cash reserve to support its massive expenses. These same individuals have implied that the Public Defenders we provide in each Justice Booth are mental incompetents. Neither of these claims is true. The Justice Booths are a fair means of quickly determining the guilt of any individual who has allegedly committed a crime.  The simple fact is that practically everyone who is brought into a Justice Booth is guilty of something.  We merely provide a means of penalizing these criminals for their antisocial behaviors. As opposed to former methods which involved lengthy trials and vast expenses for both the prosecution and defense, the Justice Booth is cheap to operate and makes its decisions based on pure logic. Justice is quickly served, leaving none of the case backlog which plagued earlier systems. As with the Judges themselves, the Public Defenders are randomly computer generated. Their personalities are based on careful studies of human Public Defenders, allowing the criminal who has been arrested a measure of security and control over his environment.  The fees charged by these Public Defenders are primarily a psychological aid for the criminal, who would otherwise feel that they owe a personal debt to their attorney.
 * [4] ::       Justice System Accounting  Heretofore be it known that the following criminal persons are currently in debt to the Justice system, having previously been tried for their crimes and found guilty. The aforementioned debts remain outstanding and, as a result, liens have been levied against the known orbital bank accounts of the aforesaid criminal persons. Until such time as remuneration is received in full, the aforementioned liens will remain in effect.
 * [5] ::   LAWBOT Arrest Warrants
 * [6] ::    Justice System Liens
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class CentralJusticeDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, EDIT
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public CentralJusticeDatabaseView(GameState gs, Pane p, PopupListener l) {
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
        //accessLevel = 3; // Debug
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
                viewText(3);
            }
            case "2" -> {
                if (accessLevel > 1) {
                    personList(5, "JUSTICE0", true);
                }
            }
            case "3" -> {
                if (accessLevel > 1) { // Leins
                    // TODO: Something like warrant list ???
                    // Show resource 4 and 6 with a list of leins.
                    viewText(4);
                    //warrantList("JUSTICE1");
                }
            }
        }
    }

    private void editWarrant(int index) {
        LOGGER.log(Level.FINE, "SEA: edit warrant");
        pane.getChildren().clear();
        mode = Mode.EDIT;

        Text subHeadingText = new Text("\n" + dbTextResource.get(11) + "\n\n");

        TextFlow contentTf = simpleTextFlow(subHeadingText);
        contentTf.setPadding(new Insets(0, 0, 0, 30));

        TextResource bamaList = gameState.resourceManager.getBihResource("POLICE0");
        bamaList.forEach((item) -> {
            Text t;
            if (item.startsWith("\1")) {
                t = new Text(
                        String.format("%-19s", gameState.name)
                        + item.substring(19)
                );
            } else {
                t = new Text("\n" + item);
            }

            // TODO: Link item for editing BAMA ID???
            contentTf.getChildren().add(t);
        });

        TextFlow pageTf = pageTextScrolledFlow(headingText, contentTf);

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
            case EDIT -> {
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
