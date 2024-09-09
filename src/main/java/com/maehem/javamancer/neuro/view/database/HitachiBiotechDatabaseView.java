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
[0] :: * Hitachi Biotech *
[1] :: X. Exit System 1. Lung Report
[2] :: 2. Personnel List
[3] ::       Personnel File
[4] :: Lung Research Report November 2058 Number of Volunteers Tested: 6 Reporting Researcher:  Yuri Azimov  Following the standard method of removing one lung from each of todays volunteers has reinforced previous findings with regard to cloning and regeneration of lung tissues.  As a basis for further discussion, I will now review certain basic considerations regarding the biochemistry of cellular respiration. The energy of cells in general is obtained through oxidative processes, which in most cases involve the utilization of oxygen and the production of carbon dioxide.  Since diffusion alone cannot meet the demands of cellular respiration in mammals, as it does in the lower forms of life, pulmonary respiration couples with specialized physiochemical systems in the circulating blood for the transport of oxygen and carbon dioxide.
[5] :: A man metabolizing average mixed food producing 2500 calories per day uses about 500 liters of oxygen and produces about 400 liters of carbon dioxide, while with a diet of 4000 calories, around 800 liters of oxygen are used and 640 liters of carbon dioxide are formed.  Through action of the lungs and transport by hemoglobin, oxygen is delivered from the air at a pressure of about 158 mm to the capillary beds at a pressure of about 90 mm.  Through buffer mechanisms in the blood, and pulmonary respiration, the enormous acid load is transported from the tissues to the atmosphere with a change in blood PH of only a few hundredths of a unit. The average adult male at rest absorbs and utilizes some 250 ml of oxygen and produces and eliminates about 200 ml of carbon dioxide per minute.  During severe exercise, these quantities may be increased by ten times or more.  The volumes percent differences in the gaseous contents of arterial and venous blood represent the gaseous transport of blood.  Thus, if the oxygen content of arterial blood is 20 volumes percent and of venous blood 13 volumes percent, this means that each 100 ml of arterial blood transports 7 ml of oxygen to the tissues where it is utilized for tissues oxidations.  This represents 70 ml of oxygen transport per liter of blood.
[6] :: Our experiments bear out these results.  Improvements of up to 87 percent can be expected with our basic improvements in lung design as pioneered by Dr. Lum. We will continue to request volunteers until such time as our forced growth tanks are operating at peak efficiency.  A fully improved and integrated lung design which will double the efficiency of an athletes lungs is expected to be realized by the end of the year, in time for the annual stockholders meeting at the end of December.
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class HitachiBiotechDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, EDIT
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public HitachiBiotechDatabaseView(GameState gs, Pane p, PopupListener l) {
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
                viewText(4, 5, 6);
            }
            case "2" -> {
                bamaList(3, "HITACHI0", false);
            }
        }
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
        return super.handleKeyEvent(keyEvent);
    }
}
