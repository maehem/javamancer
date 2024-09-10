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
[0] :: * Nihilist *
[1] :: X. Exit System 1. Statement of Purpose 2. Philosophical Diatribe 3. Software Library
[2] ::  Software Library  X. Exit To Main 1. Python 5.0 2. Slow 4.0 3. Acid 3.0 4. Logic Bomb 3.0
[3] ::      While acknowledging the completely contrary nature of sponsoring a board dedicated to nihilism, the Nihilists believe it will help achieve the results we most desire.  In short, we desire complete and utter change.  Change to the truth.       What is the truth, you ask?  This is a question beyond answering because of the trappings of the society in which the question is asked.  Is the truth presented in PAX news reports? Is money that is nothing more than a manipulable set of numbers in a bank computer the truth?  Is the truth found in the corporate databases?  Of course not!       PAX news is an assortment of pablum presented to be as palatable to the PAX masters as possible.  All the informing it does is inform those working for PAX that they have done their job of misleading the world. Bank balances can be manipulated easily, and corporate bases lie to low level users while giving away their true purposes to those who have higher access.       What is our purpose, then?  It is to combat this misrepresentation of the world.  How will we do that?  We offer warez that will put the lie to these truths.       Live and learn, then destroy....
[4] ::      What is Nihilism?  Nihilism is the belief that society is so bad it must be destroyed for its own sake. It differentiates itself from other philosophies concerning the evil of society because the other philosophies wish to make changes to advance their own position.  Examine, for example, the simple idea of an eye for an eye and a tooth for a tooth.  As old as mankind, this philosophy has an endpoint -- namely a society where one will not transgress against another. In pushing this basic idea of Lex Talionis, we will one day come to a utopia of sharing and brotherhood.       Nihilists reject this as the fallacy of "moral truth."  One group of society imposes its view on the rest -- or eliminates detractors -- because it is the "right and true" thing to do.  This is, obviously, hokum because the idea originates and is put into action by humans.  Humans, a flawed vessel at best, bend things to their own ends, thereby perverting their truth even as they seek to expand its acceptable borders.       Nihilists accept that truth cannot be known, and that all truths advanced by mankind have been or will be warped toward evil ends.  Because of this we urge and facilitate the utter and complete destruction of society as we know it.  Society, because of the goals it imposes upon us, steers all toward one view of virtue and perfection.  In reality the only virtue is the total lack of desire, and the only perfection comes with nothingness.  In the absence of existence, we have everything.
[5] ::      By clinging to what they see as the truth, people deny truth.  By being tied to property or power or love people are blinded to the ultimate reality.  While our philosophy would seem to favor a suicidal cult (and has indeed, over the years, spawned many of the same) we must survive to spread the word among the unconverted.  Everyone must be made to see, either by persuasion or violence, how futile the world is. All must be encouraged to work toward nothing.       Consider that each of us creates his or her own reality, but we are born into a world where the realities created by others impinge upon us. Recall that fashion pundits tell us brown shoes clash with blue slacks. Why?  What has made them judges of what is aesthetically pleasing?  What if you like brown and blue?  Why should their view of reality change your reality?  Why should you be subjected to ridicule for doing what you wish?  You should not, but because others dare not control their own reality, and follow these witless masters of opinion, you are damaged.       Our way is to destroy those outside influences so your reality can flourish.  Join us.  The Matrix is the key.
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class NihilistDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, EDIT
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public NihilistDatabaseView(GameState gs, Pane p, PopupListener l) {
        super(gs, p, l);

        dbTextResource.dumpList();
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

        Text helloText = new Text("\n\n\n\n\n\n\n\n\n");

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
                viewText(3);
            }
            case "2" -> {
                viewText(4, 5);
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
