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

/*
 * <pre>
0] :: * International Security Agency *
[1] :: X. Exit System 1. Codebreaker 2. Data Traffic Schedule 3. Skill Upgrade 4. Software Library
[2] :: This system contains RESTRICTED data as defined in the Tempest Restrictions of 2025.  Unauthorized disclosure subject to administrative and criminal sanctions.  Dissemination and extraction of information from this system is controlled by INSA and is not releasable to foreign nationals.
[3] ::  Software Library  X. Exit To Main 1. ArmorAll 3.0 2. Hammer 6.0 3. Logic Bomb 3.0 4. Injector 5.0 5. DoorStop 4.0
[4] :: Transmission Start: 0300:04:27:32 Frequency:          Daily To:                 Gemeinschaft Bank From:               Mitsubishi Bank Type of Traffic:    Funds transfer INSA Monitoring?    Yes
[5] :: Transmission Start: 0400:00:19:00 Frequency:          Daily To:                 Chiba Tac Police From:               Chiba Central Ops Type of Traffic:    File transfer INSA Monitoring?    Yes
[6] :: Transmission Start: 0410:03:15:02 Frequency:          Daily To:                 Chiba Central Ops From:               Chiba Tac Police Type of Traffic:    File transfer INSA Monitoring?    Yes
[7] :: Transmission Start: 0515:07:40:00 Frequency:          Daily To:                 COMSAT Control From:               NASA Type of Traffic:    Data transfer INSA Monitoring?    No
[8] :: Transmission Start: 0644:02:30:10 Frequency:          Daily To:                 NASA From:               COMSAT Control Type of Traffic:    Data transfer INSA Monitoring?    No
[9] :: Transmission Start: 1130:40:33:00 Frequency:          Weekly To:                 Panther Moderns From:               Musabori Type of Traffic:    Data transfer INSA Monitoring?    No
[10] :: Transmission Start: 1400:01:00:00 Frequency:          Daily To:                 Tozoku From:               Fuji Electric Type of Traffic:    Data transfer INSA Monitoring?    No
[11] :: Transmission Start: 1500:01:00:00 Frequency:          Daily To:                 Fuji Electric From:               Tozoku Type of Traffic:    Data transfer INSA Monitoring?    No
[12] :: Transmission Start: 1930:00:17:10 Frequency:          Daily To:                 Bank Zurich Orbit From:               Bank of Berne Type of Traffic:    Accounting INSA Monitoring?    No
[13] :: Transmission Start: 2217:20:21:00 Frequency:          Daily To:                 Bank Zurich Orbit From:               Bank Gemeinschaft Type of Traffic:    Accounting INSA Monitoring?    Yes
[14] :: Transmission Start: 2240:09:11:00 Frequency:          Daily To:                 Bank Gemeinschaft From:               Bank Zurich Orbit Type of Traffic:    Accounting INSA Monitoring?    Yes
[15] :: Enter word to decode:
[16] :: Uncoded word is:
[17] :: Unable to decode word.
[18] ::   Skill Upgrade  X. Exit To Main 1. Cryptology (lvl 4)
[19] :: Upgrading...
[20] :: Upgrade complete.
[21] :: Unable to upgrade.
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class INSADatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, EDIT
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public INSADatabaseView(GameState gs, Pane p, PopupListener l) {
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

        Text helloText = new Text(dbTextResource.get(2) + "\n\n\n\n");

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
            case "1" -> { // Notes of interest
                codebreaker();
            }
            case "2" -> {
                traffic();
            }
            case "3" -> {
                skillUpgrade();
            }
            case "4" -> {  // Faculty news
                downloads();
            }
        }
    }

    private void codebreaker() {
        LOGGER.log(Level.SEVERE, "INSA: Codebreaker");
        pane.getChildren().clear();

        Text subHeadingText = new Text("\n"
                + dbTextResource.get(15)
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

    private void traffic() {
        LOGGER.log(Level.SEVERE, "INSA: Traffic Schedule");
        pane.getChildren().clear();

        Text subHeadingText = new Text("\nTODO\n\n"
                + dbTextResource.get(4)
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
        LOGGER.log(Level.SEVERE, "INSA: Skill Upgrade");
        pane.getChildren().clear();

        Text subHeadingText = new Text("\nTODO\n\n"
                + dbTextResource.get(18)
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

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        LOGGER.log(Level.SEVERE, "Handle key event.");
        switch (mode) {
            case MENU -> {
                if (code.equals(KeyCode.X)
                        || code.equals(KeyCode.SPACE)
                        || code.equals(KeyCode.ESCAPE)) {
                    LOGGER.log(Level.SEVERE, "Menu wants to exit system.");
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
                    LOGGER.log(Level.SEVERE, "Go back up menu level.");
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
