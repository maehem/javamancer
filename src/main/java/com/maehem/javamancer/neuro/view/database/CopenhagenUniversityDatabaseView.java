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
 * [0] :: * Copenhagen University *
 * [1] :: X. Exit System 1. Notes of Interest 2. Message Base
 * [2] :: 3. Software Library 4. Faculty/Alum news
 * [3] ::          You have reached....    The Copenhagen Message base.  We welcome all free thinkers and students of life.
 * [4] ::        Copenhagen University      Since the moment the Matrix made  universal education possible, those of  us at CU have done our best to create  an educational atmosphere for our  students.  Our students come from  around the world and are counted among  the worlds important people.  We  serve those who wish to free  themselves of the mundane and dare to  think of the possibilities the world  offers.  We also have an excellent job  placement office.  Please feel free to  look around and leave a message for  the Registrar if you wish to join us.   For alums and other friends, welcome  back.  We are glad to continue to  serve as an important part of your  life.
 * [5] ::  Software Library  X. Exit To Main 1. Comlink 4.0 2. Decoder 1.0
 * [6] :: 3. Probe 4.0 4. Jammies 1.0 5. DoorStop 1.0
 * [7] :: Notes of Interest          Copenhagen University    Our Polar Bears finished their Bloodsport season 12-3-1, winning the Pan European Championship in a hard fought contest against Leningrad Universitys Molotov Cocktails.  Our star, Lars Mbutu, finished the final game despite nearly having his left leg severed halfway through the first day of competition.  Doctors report his replacement will work almost like new, and we hope hell be back for Cross-Country competition in the fall.  "Cyberspace and Addictive Personalities" was a paper delivered by our own Professor Marsha Sanderson at last months Psychologists Convention in Paris.  She suggested that computers can be addictive and very time consuming, but she reports no hard evidence that computers are harmful.  "Computers are the soul of current society -- to embrace them is to embrace life itself."  She also pointed out that the pseudonyms used by so-call "Cyberspace Cowboys" are merely a manifestation of their true selves and "no different from the Japanese custom in which samurai would adopt new names repeatedly during their careers."    Copenhagen University is leading the effort to discover why numbers of very adept cyberspace operators have been dropping out of the communications network.  Said Michael Austin, an aide to Dr. Marsha Sanderson, "Were focusing our study on withdrawal symptoms and feelings of rejection on the networks.  Something out there is alienating users, and we hope to determine what it is." The research, funded by a grant from Tessier-Ashpool funneled through their Allard Technologies subdivision, begins immediately.
 * [8] :: Faculty/Alum news    Dr. Heinrich Gott has been made Professor Emeritus in Economics/Political Science.  He is best known for pioneering the combination of public, private and economic pressure in an effort to oust unfavorable dictators from small nations and getting them to cough up the money they have stolen.  The so-called "Heinrich Maneuver" has been successfully employed to strip illegal wealth from a half-dozen African leaders.
 * [9] :: To:   Lars Mbutu From: Deathangels Shadow    Great game, Lars.  Sorry about those fingers you lost in the last period.  I guess you wont be typing back a reply that quickly, will you? Hope the spare parts shop in Copenhagen is better than the one in Chiba City.  This pancreas I got sucks.
 * [10] :: To:   Dr. Marsha Sanderson From: Habitual User    Saw a vid of your paper delivery the other day.  Think I got most of the French.  Hit the nail on the head -- no harm in decks.  Promote intellectual development.
 * [11] :: To:   Deathangels Shadow From: Lars Mbutu    Thnk you for your msg bout th gm. you r right tht loing fingr on my lft hn ill mk for iffikult riting, but i ill try.  i njoy th gm.  my lg i lot bttr no.  ont brly limp.  By for no.
 * [12] :: To:    From: Deathangels Shadow    Its getting really spooky out here.  Was supposed to get some information from the Sumdiv Kid, but hes gone null.  Have you seen him?
 * [13] :: To:   All From: Deathangels Shadow    All you new moes remember that all ICE breakers arent created equal. So being the cool guy that I am, I leave the following info for all.  Good:   Decoder, BlowTorch, Hammer Better: DoorStop, Drill Best:   Concrete, DepthCharge,         Logic Bomb  Good Luck
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class CopenhagenUniversityDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, EDIT
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public CopenhagenUniversityDatabaseView(GameState gs, Pane p, PopupListener l) {
        super(gs, p, l);
        landingPage();
    }

    @Override
    protected final void landingPage() {
        pane.getChildren().clear();
        mode = Mode.SUB;

        Text paddingText = new Text(dbTextResource.get(3) + "\n\n\n\n");

        // TODO: If visited copenhagen, show 4? or is 4 the header for messages?
        TextFlow tf = pageTextFlow(headingText, paddingText, CONTINUE_TEXT);
        pane.getChildren().add(tf);
    }

    @Override
    protected final void siteContent() {
        mainMenu();
    }

    private void mainMenu() { // Site has multi access level main menu.
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
                viewText(7);
            }
            case "2" -> {
                messages();
            }
            case "3" -> {
                if (accessLevel > 2) {
                    downloads();
                }
            }
            case "4" -> {  // Faculty news
                if (accessLevel > 2) {
                    viewText(8);
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
