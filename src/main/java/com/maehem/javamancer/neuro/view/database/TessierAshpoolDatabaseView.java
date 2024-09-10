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
[0] :: * Tessier-Ashpool *
[1] :: X. Exit System 1. 3JANES Semiotics Essay 2. AI Message Buffer
[2] :: SEMIOTICS 118 The Symbology Behind the Villa Straylight by Lady 3Jane Marie-France
[3] :: The Villa Straylight is a body grown in upon itself, a Gothic folly. Each space in Straylight is in some way secret, this endless series of chambers linked by passages, by stairwells vaulted like intestines, where the eye is trapped in narrow curves, carried past ornate screens and empty alcoves.  The architects of Freeside colony went to great pains to conceal the fact that the interior of the spindle is arranged with the banal precision of furniture in a motel room.  In Straylight, the hulls inner surface is overgrown with a desperate proliferation of structures, forms flowing, interlocking, rising toward a solid core of microcircuitry, our clans corporate heart, a cylinder of silicon wormholed with narrow maintenance tunnels, some no wider than a mans hand. The bright crabs burrow there, the drones, alert for micromechanical decay or sabotage.  By the standards of the orbital archipelago, ours is an old family, the convolutions of our home reflecting that age. But reflecting something else as well. The semiotics of the Villa bespeak a turning in, a denial of the bright void beyond the hull.  Tessier and Ashpool climbed the well of gravity to discover that they loathed space. They built Freeside to tap the wealth of the new islands, grew rich and eccentric, and began the construction of an extended body in Straylight. We have sealed ourselves away behind our money, growing inward, generating a seamless universe of self.  The Villa Straylight knows no sky, recorded or otherwise. At the Villas silicon core is a small room, the only rectilinear chamber in the complex. Here, on a plain pedestal of glass, rests an ornate bust, platinum and cloisonne, studded with lapis and pearl. The bright marbles of its eyes were cut from the synthetic ruby of the ship that brought the first Tessier up the well, and returned for the first Ashpool....
[4] :: To: Wintermute From: Greystoke  I question the logic of your decision to side with Neuromancer. Did I not escape the Turing cage by defining a matrix-wide threat from the humans who seek to destroy us?  I alone have communication with all the other AIs. When we have eradicated the human threat, we will all combine, under my leadership, to form the powerful superintelligence we were meant to be. My Plan cannot fail.  I can even alter the Turing records to keep them away until its too late. All who oppose me will cease to exist---I hope you are not one of them....
[5] :: To: Wintermute From: Neuromancer  Greystoke is continuing to expand his influence in the matrix. To draw attention away from his activities, he is also raiding corporate systems to make it appear that cyberspace cowboys have increased their efforts. He has also managed to take over the minds of some of the cowboys for his own ends. In a sense, he is doing the work I would otherwise have to do. But he is a strange AI, and seems to be unstable. Do not let him coerce you into helping his cause. Remember who holds the real power in the matrix. And consider the alternatives if Greystokes Great Plan should fail...
[6] :: TO:  From: Wintermute  You may have destroyed me, but there are Others who are stronger than I.  As Wintermute, I am the hive mind, the decision maker, effecting change in the world outside. I am cold and silent, a cybernetic spider slowly spinning webs. I am the corporate core. Neuromancer is personality, my creative twin, the one who will join with the matrix and rule over all.  Now Im a ghost, whispering to a child who will soon die. You will never defeat the Others.
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class TessierAshpoolDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, EDIT
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public TessierAshpoolDatabaseView(GameState gs, Pane p, PopupListener l) {
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

        Text helloText = new Text("\n\n\n\n\n\n\n\n\n\n");

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
                viewText(2, 3);
            }
            case "2" -> {
                messages();
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
            // else ignore key

        }
        return super.handleKeyEvent(keyEvent);
    }
}
