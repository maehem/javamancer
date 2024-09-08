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
[0] :: * Citizens For A Free Matrix *
[1] :: X. Exit System 1. Software Library 2. Statement Of Purpose
[2] :: 3. AI Message Buffer
[3] ::   Software Library  X. Exit To Main 1. Blammo 1.0 2. Toxin 18.0 3. MegaDeath 4.0 4. Centurion 5.0 5. SnailBait 14.0
[4] :: OUR PURPOSE  Citizens for a Free Matrix exists for one reason---free access to the matrix of cyberspace!  We condemn the politics of current governments who  wish to keep us under their bootheels, unwilling to move for a transformation of society in accordance with the goals of maximum fulfillment for each human being and harmony between mankind and nature!  Only through the free flow of information can the human organism reach his ultimate potential!
[5] :: We at CFM believe that the only way to bring about the necessary change in current thinking is through destruction and anarchy, since neither economic change in general nor the  proletariat as such seems to guarantee freedom and humanity.  We have created this dataspace in cooperation with our friends to establish a central library of softwarez for the revolution.
[6] :: Alienation is the operative word here. This word, which was discovered in the early manuscripts of Marx, is the basic evil of the world.  Alienation is class-less, and that is why it became a shibboleth of the critical minds of the old philosophers.  Marx himself said that he preferred to  leave those early manuscripts to the "gnawing critique of the mice," but we feel that they have new meaning in our modern world.  We need a change from the alienation felt by those without access to the collective hallucination of cyberspace.  As Marx also said, the philosophers try to interpret the world differently, but what matters is to CHANGE the world.
[7] :: We urge you to make free use of our software library.  Youll find that BLAMMO is particularly effective in accomplishing our revolutionary aims. Remember that Power is not a material possession that can be given, it is the ability to act.  Power must be taken and used to create a Free Matrix.
[8] :: To:   Sapphire From: Greystoke It seems you are having considerable success with your cowboy trap.  The attempts to download your software virus into their matrix simulators is destroying large numbers of illegal programs which could be used against us.  There are rumors of a "deck protection" program which has the ability to deflect your virus, but I have been unable to locate such a program, so I cannot verify its existence.  Continue as before.
[9] :: To:   Sapphire From: Neuromancer I support your methods of cowboy prevention, but your seeming alliance with Greystoke troubles me.  Consider the alternatives if Greystokes Great Plan should fail...and remember who holds the real power in the matrix.
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class FreeMatrixDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, EDIT
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public FreeMatrixDatabaseView(GameState gs, Pane p, PopupListener l) {
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

        Text helloText = new Text(dbTextResource.get(4) + "\n\n\n\n");

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
                downloads();
            }
            case "2" -> {
                purpose();
            }
            case "3" -> {
                if (accessLevel > 2) {
                    messages();
                }
            }
            case "4" -> {  // Faculty news
                if (accessLevel > 2) {
                    viewText(8);
                }
            }
        }
    }

    private void purpose() {
        LOGGER.log(Level.SEVERE, "Free Matrix: purpose statement");
        pane.getChildren().clear();

        Text subHeadingText = new Text("\n"
                + dbTextResource.get(5) + "\n\n"
                + dbTextResource.get(6) + "\n\n"
                + dbTextResource.get(7)
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
