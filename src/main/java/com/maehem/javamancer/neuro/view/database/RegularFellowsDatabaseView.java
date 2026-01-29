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

/**
 * <pre>
 * [0] :: * Regular Fellows *
 * [1] :: X. Exit System 1. The Gallery 2. Checkout Counter 3. Critics Corner
 * [2] ::
 * [3] ::             Its Showtime!  Welcome to the Regular Fellows.  Were a group of joeboys who consider themselves artists.  If you think youre an artist as far as cranking warez are concerned, youre a Regular fellow.  Just enter the password, "VISITOR". Have fun.
 * [4] ::    X. Exit To Main   1. BattleChess 2.0   2. Scout 1.0
 * [5] ::   3. Probe 3.0
 * [6] :: We try it before you buy it.       X. Exit To Main      1. Scout 1.0      2. BattleChess 2.0
 * [7] :: Scout 1.0, recon program Reviewed by Zelig Dorn  Floats like a butterfly and stings like a rail gun.  This little program is the answer to your wildest dreams.  Imagine youve just made contact with a new base. You want to know how many levels it has.  Are there secret levels? Enter Scout 1.0. Using Scout after linking into a base, and while still on the intro screen, gives you an accurate reading of how many levels the base contains.
 * [8] :: BattleChess 2.0 chess program extraordinaire reviewed by Matt Shaw  Okay, so forget I did the mod on this puppy.  Ill admit its true that I took a Panther Moderns ear and made a synthesilk purse out of it, but then  miracles just flow when these magic fingers do their work.  I doubled the depth of algorithms this warez handles, plus speeded it up and increased the history files it has to include even some of the games Morphy played near the end.  Now this binary chess wiz has responsive reciprocal mobility and total transitional capability.  Labor of love it was. Best of all, I left the initialization protocols all alone so the World Chess Confederation still thinks its BattleChess 1.0. Check it out.
 * [9] :: To: Raphael Spraycan From: Deathangels Shadow  Saw the work on the wall over by Crazy Edos.  Did he pay you for that or what?  Nice touch, the fish eggs on toast.  Really rad, you know!
 * [10] :: To: All From: Matt Shaw  World Chess Confederation (WORLDCHESS) is having another of their regular tournaments.  Ive uploaded my jacked up version of BattleChess 2.0.  Itll pass their screening and ought to blow away the ChiCom warez running against it.
 * [11] :: To: All From: Mo #243  Hey, guys, I need some help.  Im jacked in on my friends Ausgezeichnet 188 BJB.  My Yamamitsu UXB flamed out yesterday.  What should I do?
 * [12] :: To: Mo #243 From: Deathangels Shadow  Quit whining and get a real deck. That goes for your friend, too.  But dont come back here with messages asking us what to buy.  Were not Consumer Review.  Return only after you get a real box to put your warez in.
 * [13] :: To: All From: Harpo  Just found second level password for the Cheap Hotel; its "COCKROACH".
 * [14] :: To: Harpo From: Matt Shaw  Checked on the password for Asanos second level as you requested. Its "PANCAKE", but it seems to be encoded.
 * [15] :: To: Red Snake From: Scorpion  Red! Do you know the link codes for Fuji or Hosaka?
 * [16] :: To: Scorpion From: Red Snake  Why do you keep asking me about Japanese companies? Do I look like Julius Deane?
 * [17] :: To: All From: Deathangels Shadow  The ICE out there has gotten smarter. Each time you use the same ware against it, it does less damage. Always keep a variety of different warez with you.
 * [18] ::       Critics Corner
 * [19] ::   Checkout Counter
 *
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class RegularFellowsDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, GALLERY
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public RegularFellowsDatabaseView(GameState gs, Pane p, PopupListener l) {
        super(gs, p, l);
        landingPage();
    }

    @Override
    protected final void landingPage() {
        pane.getChildren().clear();
        mode = Mode.SUB;

        Text helloText = new Text(dbTextResource.get(3) + "\n\n\n");

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
                gallery();
            }
            case "2" -> {
                downloads();
            }
            case "3" -> {
                messages();
            }
        }
    }

    private void gallery() {
        LOGGER.log(Level.FINE, "RegFellows gallery");
        pane.getChildren().clear();
        mode = Mode.GALLERY;

        TextFlow tf = simpleTextFlow();
        for (String item : dbTextResource.get(6).split("\r")) {
            Text t = new Text("\n" + item);
            if (item.contains("1.")) {
                t.setOnMouseClicked((me) -> {
                    me.consume();
                    viewText(7);
                    pane.setOnMouseClicked((tt) -> {
                        tt.consume();
                        gallery();
                    });
                });
            } else if (item.contains("2.")) {
                t.setOnMouseClicked((me) -> {
                    me.consume();
                    viewText(8);
                    pane.setOnMouseClicked((tt) -> {
                        tt.consume();
                        gallery();
                    });
                });
            }
            tf.getChildren().add(t);
        }

        TextFlow pageTf = pageHeadingTextFlow(tf);

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
            case GALLERY -> {
                switch (code) {
                    case X, ESCAPE -> {
                        LOGGER.log(Level.FINE, "Go back up menu level.");
                        mainMenu();
                        keyEvent.consume();
                        return false;
                    }
                    case DIGIT1 -> {
                        keyEvent.consume();
                        viewText(7);
                        //return false;
                    }
                    case DIGIT2 -> {
                        keyEvent.consume();
                        viewText(8);
                        //return false;
                    }
                }
            }
            // else ignore key

        }
        return super.handleKeyEvent(keyEvent);
    }
}
