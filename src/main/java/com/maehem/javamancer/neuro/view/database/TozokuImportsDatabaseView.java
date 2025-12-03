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

/*
 * <pre>
[0] :: * Tozoku Imports *
[1] :: X. Exit System 1. Order Status 2. Specials Available 3. Software Library
[2] :: 4. Jobs listing 5. Message Base
[3] ::   Software Library  X. Exit To Main 1. Comlink 6.0  2. Blowtorch 1.0 3. Decoder 1.0
[4] :: 4. BlowTorch 3.0 5. Drill 2.0 6. Acid 1.0
[5] ::              Order Status   The Star of Iowa is reported to be on its way from the Peoples Republic of New Zealand.  It has most of our mutton orders, as well as the Johnson sweater order.  We expect it in dock soon.    The Popul Vox is due in port next week with its cargo hold full of pre-Columbian artwork.  Some of the cargo has been damaged because of shifting during a typhoon, but insurance will cover all damages.  The crew has assured us that all damage is minor.
[6] ::     Tozoku Imports Special Offers   We anticipate a certain number of cosmetically imperfect examples of pre-Columbian artwork to be made available in the near future.  These works, crafted by long-dead workers, do show some signs of deterioration as caused by acid rain, but the original work is still visible and quite beautiful.  Many of the pieces are hollow and make for a perfect place to hide valuables.  Prices will vary depending upon condition of the piece, but each and every one of them would make a perfect addition to any home.
[7] ::           Yakuza Assignments Needed: 3 men with experience in         breaking and entering. Object: Penetration of Hosaka plant.    Industry rumors suggest Hosakas Software Warrior series will feature the deaths of a number of figures that bear a striking resemblance to our own G.I. Akira action figures.  We must have the rushes of their new Matrix Invasion season.
[8] :: Needed: 1 woman Object: Seduction of Musabori         Executive    We are continuing to receive payments in accordance with our agreement to help ruin Hosaka, but the amounts were tied to the profits Musabori Industries has made from this situation.  We want one woman to become involved with Clarence Hartesty, CPA, to determine if we are being stiffed.  Hartesty has not had a sexual encounter in the past fifteen years, so this should be a simple target.
[9] :: Needed: 3 men and a baby Object: Movement of contraband    The Popul Vox is full of contraband alkaloid substances that need delivery.  Two men will unload and cut the stuff, while the man with the baby will carry it to our distribution points under the guise of interviewing the day care centers as possible places to put his "child."
[10] :: To:   Iemoto From: Tanenaga    As it has been six months since I terminated the two women using the nail polishing machines and no indictments have come down, may I please return to your service?  While I enjoy the travel, my cover as the apprentice to a concert pianist is not quite convincing.
[11] :: To:   Tanenaga From: Iemoto    Yes, if it is your wish to do so, please return to Chiba City with all due haste.  This woman who has thwarted us before is still a thorn in our side.  I believe you could be the one who could take care of her.
[12] :: To:   Iemoto From: Tanenaga    I was wrong in asking you to reverse yourself in assigning me this duty.  I will remain here until I am truly needed and have no chance of being identified in the Insta-Nails case.  To do any less would shame you.
[13] ::   These warez are provided by the Yakuza for use against all who are not our allies.  Downloading them makes one responsible for repaying this favor at a later date.  Use of them against the Yakuza would be the breaking of a sacred vow.
[14] :: To: Iemoto From: Phillip dArgent    I am having some difficulties making my payment this month, the bank keeps losing funds mysteriously.  Please contact me here at Bank Zurich -- Orbital, the link code is "BOZOBANK".  Thank you in advance for being merciful
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class TozokuImportsDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, EDIT
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public TozokuImportsDatabaseView(GameState gs, Pane p, PopupListener l) {
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
                viewText(5);
            }
            case "2" -> {
                viewText(6);
            }
            case "3" -> {
                downloads();
                // TODO: Message 13 regards the downloading of warez. Need to integrate it.
            }
            case "4" -> {
                if (accessLevel > 1) {
                    viewText(7, 8, 9);
                }
            }
            case "5" -> {
                if (accessLevel > 1) {
                    messages();
                }
            }
        }
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
//            case EDIT -> {
//                if (code.equals(KeyCode.X)
//                        || code.equals(KeyCode.ESCAPE)) {
//                    LOGGER.log(Level.FINE, "Go back up menu level.");
//                    mainMenu();
//                    keyEvent.consume();
//                    return false;
//                }
//            }
            // else ignore key

        }
        return super.handleKeyEvent(keyEvent);
    }
}
