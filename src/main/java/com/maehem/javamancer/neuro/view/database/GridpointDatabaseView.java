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
[0] :: * GridPoint *
[1] :: X. Exit System 1. Message Base 2. Look Out 3. Software Library
[2] ::  Software Library  X. Exit To Main 1. Jammies 3.0 2. ThunderHead 3.0 3. Hammer 5.0 4. Injector 3.0 5. ArmorAll 2.0
[3] ::      Okay, so you made it to The GRIDPOINT.  Whatdaya want, a medal? Listen, arriving here is its own reward.  This is it, the place the great come to be with other great cyberspace Cowboys.  Check out our services, youll agree its all been worth it.
[4] ::   Weve heard some weird stuff about some bases:  1) Psycho -- it has been rumored that some of the files on that base have been used by law enforcement agencies to get at some of us.  This is not good.  2) Free Sex Union -- That "free" is something of a misnomer.  Watch out!  3) Musabori -- be careful in and around Musabori.  Anonymous Bosch was headed for it when he was last pushing data through the matrix.  Probably no connection because weve all broken in there, but someone may have picked him up outside it.
[5] :: To: Anonymous Bosch From: Deathangels Shadow    Hey, Bosch, where are you?  I went back to our rendezvous point to see if youd picked up that copy of PYTHON 6.0 I left you.  It was still there, and there was no copy of SLOW 3.0 for me.  Whats the deal?
[6] :: To: Deathangels Shadow From: Matt Shaw    Ive been looking around for Bosch, but hes nowhere to be found. Think he got busted or something?  The Federales might have napped him if he was fooling around with the Hidalgo budget again.
[7] :: To: Matt Shaw From: El Aquila    Hola, amigo.  I cracked the Federales infonet down here.  No mention of Bosch -- whenever he hit the budget he sliced a bit off for the Federales themselves.  Rumor down here has it that hes been flatlined... Some said it was a physical disconnection -- he was seeing the wife of a jealous man, if you catch my drift.  Hope thats true, otherwise folks is leaving cyberspace at an alarming rate...  Hasta Luego,
[8] :: To: ALL From: Matt Shaw    Lets have a show of hands. Whos still around?
[9] :: To: ALL From: Matt Shaw    Okay, enough fooling around. Its been 24 hours since I posted my first roll call request.  Stop joking and post.  Youre scaring me.
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class GridpointDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, EDIT
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public GridpointDatabaseView(GameState gs, Pane p, PopupListener l) {
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

        Text helloText = new Text(dbTextResource.get(3) + "\n\n\n\n\n");

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
            case "1" -> { // Notes of interest
                messages();
            }
            case "2" -> {
                viewText(4);
            }
            case "3" -> {
                downloads();
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
