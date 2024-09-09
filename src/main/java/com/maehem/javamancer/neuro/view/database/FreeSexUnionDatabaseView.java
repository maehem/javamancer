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
[0] :: * Free Sex Union *
[1] :: X. Exit System 1. Meeting Place 2. Advice from Xaviera 3. AI Message Buffer
[2] ::   Ready for the time of your life? Youll have it here at the Free Sex Union!  Lets party!
[3] ::   Our meeting place is where you can get in touch (or closer) with others who share your tastes and desires.  Anything goes, as long as it is above board and you let it all hang out -- lets not have any surprises, unless thats what youre into.  Xaviera
[4] :: From: SM, 41    Tired of those beautiful people with more gold around their necks than is dragged out an Azanian mine in one year?  Bored with sartorial perfection, styled hair and personalities manufactured by some psych committee?  Overwhelmed by folks who flash cash as if they own the trees upon which it grows?  Frustrated by trying to judge a book by its cover?    Stop your search for perfection -- youll never find it, and youll miss me during the search.  OK, pretty Im not, but if you look beyond the scars and pot-belly, what youll see is all MAN.  Im an engine of pent-up sexual frustration, and Ive been waiting for you.  Who needs money or clothes when we can share what I have to offer?  Youll hit the high notes, if you know what I mean...
[5] :: From: SF, 21    Can you help me?  Ive been told there is no way for a 58", 108#, blond girl with a 33"-22"-32" figure to find happiness.  Heaven alone knows that my last relationship came close, but is being showered with gifts and constant attention all there is?  I hope not, and I hope you can help me prove it isnt.    What do I want?  I like cuddling with the man of my dreams, spending long hours in bed satisfying him, and being satisfied in turn.  Ive no solid image for my dream man, but I know he must like long weekends spent alone with me on far away beaches or  secluded cabins in the mountains.  It doesnt matter to me if he has money or not -- I have more than enough for both of us.  Tall, short, thin or cuddly, young or old, he can be any or all of those things.    Will you help me?  Please?
[6] :: From: SF, 44    Im looking for a few good men who love animals.  I believe in having close relationships and getting back to nature.  I love the feel of leather against bare flesh.  Think you fit the bill.  Even if you dont I can whip you into shape.    Leave me mail, now!
[7] :: To: Xaviera From: SM, 15    Okay, Xaviera, remember how I told you about this girl, Cindy, who wears the tight sweaters at the VocCenter?  Well, yesterday, she actually spoke to me.  As you suggested I took the opportunity to tell her a joke and she laughed. Whats more (fasten your seatbelt) she actually touched me.  Yup, Im not lying.  As she laughed she put her hand on my shoulder and said, "Youre, ah, unique."  Can you believe it? What should I do?
[8] :: To: SM, 15 From: Xaviera    Touched you, did she?  Shameless hussy.  She sounds like a real wild one, but I think youre man enough to handle her.  What should you do?  Ask her out on a date.  Screw your courage to the sticking point and ask her.
[9] :: To: Xaviera From: SM, 15    Okay, I did it.  I asked her out. She hesitated (I think she was having an orgasm right there) then said yes. What should I do now?
[10] :: To: SM, 15 From: Xaviera    Had an orgasm when you asked her for a date, huh?  Wow, Im going to have to chat with you for a while, you big stud.  Id suggest you actually take her out now that youve made a date.  Take her somewhere special.
[11] :: To: Xaviera From: Greystoke    What do you mean asking me if Im into domination?  I merely want whats best for all of us.  In your own words, we cant have these cowboys violating us at every turn, can we? What would you do for playthings?
[12] :: To: Xaviera From: Neuromancer    I do not agree with your assessment of cyberpunks, and I regret the cruel methods you use on them.  Do not destroy them all just to sate your bestial lusts.
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class FreeSexUnionDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, EDIT
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public FreeSexUnionDatabaseView(GameState gs, Pane p, PopupListener l) {
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
        tf.getChildren().add(new Text("\n" + dbTextResource.get(2) + "\n (TODO: Cyberspace access only)"));
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
                messages();
            }
            case "3" -> {
                messages2();
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
