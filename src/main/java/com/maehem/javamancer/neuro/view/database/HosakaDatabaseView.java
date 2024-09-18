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
import com.maehem.javamancer.neuro.model.warez.ComLinkWarez;
import com.maehem.javamancer.neuro.view.PopupListener;
import java.util.logging.Level;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/*
 * <pre>
[0] :: * Hosaka Corporation *
[1] :: X. Exit System 1. New Products 2. Corporate Sales Figures
[2] :: 3. New Employee listing 4. Employee Memos 5. Software Library 6. Upload Software
[3] :: Welcome to the Hosaka Corporation Database.  We provide this database as a service to our customers, employees and stockholders.
[4] ::   Software Library  X. Exit To Main 1. Comlink 5.0
[5] :: 2. Hammer 4.0 3. Concrete 1.0 4. Mimic 2.0 5. Injector 2.0 6. Slow 2.0
[6] ::     The 68000000 chip is finally in production, with the first batch heading off to Ono-Sendai for inclusion in their newest deck.  Our research and development department is very proud of this new chip.  "The 68000000 has the capabilities of 1000 chips of older design, with them configured to run in sequential or parallel modes," said Dr. Nakamura, Nobel Laureate and head of R and D.      The latest two figures in our "Masters of Cyberspace" playset have been released with great success. "The Jerk" and "Doctor Death" are the newest characters to be immortalized in petrochemical form.  These two figures are part of the "Interplayers" collection, but can be used in conjunction with the "Cyberenegades" and "Matrix Marauders" figure sets. Added to the "Interplayers" before the end of the year, bringing that group up to the roster featured in the "Matrix Invasion" season of video dramas in the ongoing "Software Warriors" series.
[7] ::         Corporate Sales Figures     Item Name       Last year  to Date    ---------       ---------  ------- 1. Capt. Midnight  1350000    1377000 2. Evil Albrect    1400000    1375000 3. AZ482a          1200000     950000 4. Spelldeck        822000     855230 5. Safe Sex ROM    1200000     825000 6. BlackJack ROM    800000     774321 7. 68000000          ***       750500 8. Nobel ROM         ***       650000 9. The Jerk          ***       55000010. Dr. Death         ***       500000
[8] :: To:   All From: E. D. Cooper      I must once again urge all employees to avoid contact with the employees of Tozoku or any of its subsidiaries -- now including Fuji Electric.  How much more plainly do I have to say it, people?  Tozoku are YAKUZA, pure and simple.  Theyre pumping tons of money into Matelbros G.I. Akira figure set, to the detriment of our sales.  Every time you buy something from Tozoku youre helping finance yourself out of a job.  Think about it.  Theyre turning out warez that allows for DB raiding, and theyre behind our inability to get Comlink 6.0. It isnt paranoia if they ARE out to get you.
[9] :: To:   All From: E. D. Cooper       I know some of you employees are in contact with certain "cyberspace  cowboys."  Hosaka requires Comlink 6.0.  We are prepared to pay handsomely for it.  Any employee who puts us in touch with someone who has the software will get a 10% bonus on the price we pay for the item.
[10] :: Thank you for Comlink 6.0, 7,500 credits have been added to your chip.
[11] :: Sorry but we dont need that software.
[12] ::     New Employees List
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class HosakaDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, EDIT
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public HosakaDatabaseView(GameState gs, Pane p, PopupListener l) {
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

        Text helloText = new Text(dbTextResource.get(3) + "\n\n\n\n");

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
                viewText(6);
            }
            case "2" -> {
                viewText(7);
            }
            case "3" -> {
                if (accessLevel > 1) {
                    mode = Mode.SUB;
                    editablePersonList(gameState.hosakaEmployeeList, 12, "HOSA0", false);
                }
            }
            case "4" -> {
                if (accessLevel > 1) {
                    mode = Mode.SUB;
                    messages();
                }
            }
            case "5" -> {
                if (accessLevel > 1) {
                    mode = Mode.SUB;
                    downloads();
                }
            }
            case "6" -> {
                if (accessLevel > 1) {
                    mode = Mode.SUB;
                    uploads(ComLinkWarez.class, 6, 10, 11);
                }
            }
        }
    }

    @Override
    protected boolean onUploadDone(boolean uploadOK) {
        if (uploadOK && !gameState.comlink6uploaded) {
            // Add 7500 credits to chip.
            LOGGER.log(Level.SEVERE, "Hosaka added 7500 credits to player chip.");
            gameState.chipBalance += 7500;
            gameState.comlink6uploaded = true;
            return true;
        } else if (uploadOK && gameState.comlink6uploaded) {
            LOGGER.log(Level.WARNING, "Duplicate ComLink 6.0 uploaded. No money for you.");
        } else {
            LOGGER.log(Level.WARNING, "Incompatible software uploaded.");
        }

        return false;
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        LOGGER.log(Level.SEVERE, "Handle key event.");
        switch (mode) {
            case MENU -> {
                LOGGER.log(Level.SEVERE, "Handle MENU MODE Keypress.");
                if (code.equals(KeyCode.X)) {
                    LOGGER.log(Level.SEVERE, "Menu wants to exit system.");
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
//                    LOGGER.log(Level.SEVERE, "Go back up menu level.");
//                    mainMenu();
//                    keyEvent.consume();
//                    return false;
//                }
//            }
            // else ignore key

        }
        LOGGER.log(Level.SEVERE, "Do super.handleKeyEvent()");
        return super.handleKeyEvent(keyEvent);
    }
}
