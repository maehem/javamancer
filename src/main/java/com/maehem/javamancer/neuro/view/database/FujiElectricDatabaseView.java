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
import com.maehem.javamancer.neuro.view.PopupListener;
import java.util.logging.Level;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/*
 * <pre>
[0] :: * Fuji Electric *
[1] :: X. Exit System 1. Company News 2. Executive Survival Kit 3. Press Releases
[2] :: 4. Personnel Management 5. Memo
[3] :: Our Romcards Never Forget
[4] :: Company News   ***3rd Quarter profits are up!  Thanks to the effort of each and every one of  you, our employees, weve increased productivity.  This drives our costs down and jacks our profits ever higher.  With the demand for quality products out there as insatiable as it is, were going to be on top of the world.  Management has increased meat puppet breaks from 10 to 11.5 minutes for all line employees just to show you they care.  ***R and Ds Esther Radklif and Andy Raymond have finally gotten married. They had planned a skiing honeymoon in the Alps, but Andy would have had to have sold both legs to afford it, so they decided to forego that pleasure. Theyll just remain here in Chiba City and cozy up their nest a bit.  I know we all wish them well.  ***New Hires:  Mishiji, Takoda  Security OBrien, Akira   Management Kharkov, Sven    Line production Bors, Melissa    Employee Services Wang, P. Ryan    Acquisitions Bear, M. C.      Legal Moe, Larry       Consultant
[5] :: Executive Survival Kit Welcome. We have two rules around here: 1) Mr. Watkins is always right. 2) see rule #1  Follow the rules and well get along fine.  Remember to cover your ass with paper, but never stray far from a paper shredder and youll have a long career with Fuji Electric. Welcome aboard -- dont rock the boat. (signed) Harry Watkins
[6] :: ***For Immediate Release NASA and FUJI ELECTRIC DO BUSINESS  In anticipation of next centurys manned shot at Alpha Centuri, NASA signed a multi-trillion dollar contract with Fuji Electric to provide ROMcards and software development for the Prometheus ship. Fuji Spokesman Harry Watkins said, "This is the smartest move NASA has made since the dawn of their program." Fuji anticipates unparalleled growth over the next decade.  "Well have new employees coming on every day.  If not for our own computer system, wed have no way to keep track of them. Technology has made the human race great, and we want to lead the pack," said Watkins.  For further information contact Watkins at FUJI or Bob Shepherd at VOYAGER.  (Just want you to know, my fellow employees, this contract was awarded on the strength of your work. Thanks, Harry...)
[7] :: To: Management Level Employees From: Harry Watkins RE: TOZOKU merger  It is true that TOZOKU has made us an offer we have accepted concerning the ownership of FUJI.  The days of a dynastic company have ended and I gladly consider turning control over to the people of TOZOKU.  I know some of you think of them as common criminals, but I can assure you there is nothing common about them at all. I thank all of you who were concerned about my wife and child.  Theyve been returned to me and weve found all of little Harrys parts.  The doctors say hes got an excellent chance of recovery and I agree that he doesnt really look like the Frankenstein monster at all.  Harry
[8] ::     New Employees List
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class FujiElectricDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, EDIT
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public FujiElectricDatabaseView(GameState gs, Pane p, PopupListener l) {
        super(gs, p, l);
        landingPage();
    }

    @Override
    protected final void landingPage() {
        pane.getChildren().clear();
        mode = Mode.SUB;

        Text helloText = new Text(dbTextResource.get(3) + "\n\n\n\n\n\n");

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
                viewText(4);
            }
            case "2" -> {
                viewText(5);
            }
            case "3" -> {
                viewText(6);
            }
            case "4" -> {
                if (accessLevel > 1) {
                    personList(8, "FUJI0", false);
                }
            }
            case "5" -> {
                if (accessLevel > 1) {
                    viewText(7);
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
