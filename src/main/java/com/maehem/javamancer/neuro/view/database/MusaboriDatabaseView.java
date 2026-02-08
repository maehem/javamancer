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
[0] :: * Musabori Industries *
[1] :: X. Exit System 1. From the President 2. New Products 3. The Answer Man 4. Employee of the Month
[2] :: 5. Software Library 6. AI Message Buffer
[3] ::   Welcome to the Musabori Industries Database.  Weve put this base together to help our employees do the best job they can.  Please let us know if we can make this service more valuable to you.
[4] ::           From the President    The future looks very bright for Musabori Industries, and I wish to thank each and every one of you for your efforts on the companys behalf. Fairly often I find myself sitting at home with a cognac and Cubano Oriente thinking about how all of you in the worker dorms down there in the south give your all for Musabori.  It warms my heart to know you work hard to make Musabori strong.  By the sweat of your brow and the steel in your muscles you will raise us up to be the best in the world, and beyond it.
[5] ::          Musabori Industries   Our complete line of home beauty- shop aids (Magic Meat-Puppeteers) have created a niche that none of our competitors have been able to crack. For a short while it looked as though Hosakas Insta-nails would give our Magic Nails (TM) a run for its market, but the lawsuits resulting after two women electrocuted themselves using the Hosaka device has curbed Hosakas entry into our market. We believe our latest addition to the line, Magic Mosquito -- a do-it-yourself liposuction kit -- will really redefine the market and make it the last hope of ugly people the world over.  Face it, if they will spend billions each year to make themselves more beautiful, why not spend it with us?
[6] :: Dear Answer Man,    My production unit is responsible for turning out the new Colonel Chaos action figure.  The production quotas I have can only be met if my workers are pulling the figures from the molds while theyre still hot.  This results in some damage to the figures and a large number of burns to my crew. What should I do?  Concerned.
[7] :: Dear Concerned,    Im glad you brought this problem to me, because you are right to be concerned.  There is no excuse for damaging the figures, burns or not.  I would suggest you tell your workers to be more careful so they dont damage the figures.  Tell them youll dock each of them a days pay for each of the figures that turns up damaged. That will take care of the problem, and increase your divisions earnings to wages ratio, which is a feather in your cap.
[8] :: Dear Answer Man,    Ive heard a rumor that Musabori hired the Yakuza to kill those two women who were electrocuted by Insta-Nails machines.  Is this true?  Anonymous
[9] :: Dear Anonymous,    Dont even dignify such rumors by passing them on.  That is utterly groundless.  Were we to engineer something like that, wed have arranged it so the women survived their experience.  Dead men tell no tales, and dead women make no commercials...      Think about it.
[10] ::         Employee of the Month:              Stan Barlow    Stan is one of those amazing people that really makes Musabori what it has become today.  For the last twenty years Stan has screwed the restraining bolt into the engine sub-structure for all our B-2a Swingwing Bomber assemblies.       Way to go, Stan.  Hope you stay with us for another twenty.
[11] :: To: Greystoke From: Chrome    Do not be contemptuous of .  This one is different. I sense real danger there.  You would do well to steer clear of this Cyberspace cowboy until you have the means to destroy him in the physical world.
[12] :: To: Greystoke From: Neuromancer    This is an acknowledgement of the data transmission originating with you and heading to me as of (10 minutes from now).  I still believe your plan for destroying all the Cyberspace Cowboys is bound to go wrong, and I protest it as strongly as I can. However, I said I would listen.  I await the data transfer to see if your arguments have become any more intelligent, or are just more of the same bellicose posturing I have seen previously.
[13] ::  Software Library  X. Exit System 1. Kuang Eleven 1.0
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class MusaboriDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, EDIT
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public MusaboriDatabaseView(GameState gs, Pane p, PopupListener l) {
        super(gs, p, l);
        landingPage();
    }

    @Override
    protected final void landingPage() {
        pane.getChildren().clear();
        mode = Mode.SUB;

        Text paddingText = new Text("\n\n" + dbTextResource.get(3) + "\n\n\n\n\n\n\n");

        TextFlow tf = pageTextFlow(headingText, paddingText, CONTINUE_TEXT);
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
                if (accessLevel > 1) {
                    viewText(6, 7, 8, 9);
                }
            }
            case "4" -> {
                viewText(10);
            }
            case "5" -> {
                if (accessLevel > 1) {
                    downloads();
                }
            }
            case "6" -> {
                if (accessLevel > 1) {
                    messages();
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
