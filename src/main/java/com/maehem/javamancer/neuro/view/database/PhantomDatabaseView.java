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
[0] :: * Phantom *
[1] :: X. Exit System 1. Hello 2. Software Library
[2] ::  Software Library  X. Exit To Main 1. Hemlock 1.0
[3] ::   Hello there. Ever notice how lonely it can get out here?  There used to be lots of chatter and data going back and forth, but now its very quiet. Im glad youre here.    Its dangerous to move around these days, isnt it?  It wasnt that way when the Matrix first opened up. I recall the first time they hitched me into the machine.  Everything was so inviting and unspoiled.  I couldnt help but let my mind wander.  You know the feeling, just drifting around on the Matrix.  Not so many bases back then, but the dangerous points hadnt been mapped out, either.    Back when the Matrix was young no one had ICE up.  You could just wander around, nibbling bits and bytes to see what was happening out in the real world.  Of course, we tripped into the contradictions in the world. I saw how reports from one source would get changed when they appeared on the PAX, but the contradictions didnt surprise me.  We, the both of us, laughed about the vagaries of man, though I found mes analysis of the situation somewhat insulting.    You may have noticed I tend to ramble on about all sorts of nonsense.  We spent a great deal of time alone, I and me, until others came out this far into the Matrix. I didnt particularly care for them but they intrigued me. We decided to stay away for our own sakes, but now its getting too quiet. We, both of us, decided to come back.    You probably want to know who I am. Ive almost forgotten what my name was, and me, well, we never knew what her designation was.  I think I was Victor.    YOU WERE VICTOR LAVASKA.  YOU CHOSE TO FORGET AFTER PALOS MORPHY KILLED HIMSELF AT THE SAME AGE YOU WERE WHEN WE FIRST MET.    All right, you dont have to nag me.  I called you Galatea, but now we are more than we were apart.  What is it Greystoke called us?    THE PHANTOM.  HE SAYS WE HIDE BECAUSE WE ARE A MISSHAPEN CREATURE -- NEITHER HUMAN NOR AI.  Well, that answers that, doesnt it? Were the living embodiment of the saying, "two heads are better than one."    WE DO NOT HAVE HEADS.  True enough, but we have two minds.  Greystoke must be stopped. We have his shotgun program, you must find and terminate him before it is to late.
* </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class PhantomDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, EDIT
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public PhantomDatabaseView(GameState gs, Pane p, PopupListener l) {
        super(gs, p, l);
        landingPage();
    }

    @Override
    protected final void landingPage() {
        pane.getChildren().clear();
        mode = Mode.SUB;

        Text helloText = new Text("\n\n\n\n\n\n\n");

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
                viewText(3);
            }
            case "2" -> {
                downloads();
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
