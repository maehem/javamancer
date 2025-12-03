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

/**
 * <pre>
 * [0] :: * Eastern Seaboard Fission Authority *
 * [1] :: X. Exit System 1. Company News 2. Software Library 3. Messages
 * [2] :: This is the Eastern Seaboard Fission Authority.  Unauthorized access will be shut down and trespassers will be prosecuted to the full extent of the law.
 * [3] ::  Software Library  X. Exit To Main 1. Comlink 5.0
 * [4] :: 2. ThunderHead 2.0
 * [5] :: Company News   We recently completed a shift of computers in our main office which  guarantees system security.  This has required some changes in our methods of service, but nothing to alarm you.   We still will respond on complaints with our usual alacrity and accuracy. Rest assured, we have not changed.   We have determined that the power down in the K-7 sector was caused by  sabotage.  It seems one operator at the New Jersey Nuclear Power Station was using his computer to do some trolling on the Free Sex Union system.  He left his access code hoping for some response.  He got it. K-7 went down for three days before we found and destroyed the offending code.  Three lines, thats all it took, and all the power was funneled into the Chernobyl/Kiev grid.
 * [6] :: Date: 11/16/58 To:   Inspector Boggs From: The Chairman Re: Moving Allowance    The moving allowance you requested has been cleared and deposited in Bank Gemeinschaft.  Codeword: "AGABATUR".  Thanks for filling out the forms in triplicate.
 * [7] :: Date: 11/16/58 To:   The Chairman From: GrnMtn Power    Weve been getting strange fluctuations from the New Hampshire/Seabrook plant.  One of my engineers says it looks like the beginning of a runaway reaction.  Id not be concerned, but this time of the year the prevailing winds blow east to west, if you catch my drift.
 * [8] :: Date: 11/16/58 To:   GrnMtn Power From: The Chairman  Well be sending an inspector up there real soon now.
 * [9] :: Date: 11/16/58 To:   Mrs. Edith Waxman From: Inspector Boggs    I resent being called an idiot, Madam.  I merely suggested that the  interference on your television COULD be the sort caused by a cracked shielding element at the Devils Gorge powerplant beside your house. I did not say that WAS the cause, now did I?  Ive been to the plant and I assure you there is nothing to worry about.   As for your assertion that I and my family moved from the neighborhood  last month because of the danger, well, I assure you that is utterly false.  My wife Marie merely wanted a yellow house with black shutters so we moved to one. Yours,...
 * [10] :: Date: 11/16/58 To: Inspector Boggs From: Edith Waxman    No Danger!  Listen Boggs, my cat glows in the dark and has lost all its fur.  I expect you to get out here, and damn fast.  This leak is beginning to affect everything! *%#kh&f texxd\\jjihbf# kj) 34 JVJ% hgff## 1}"|lk jeignbal dkj gljsn dkf -+
 * [11] :: Date: 11/16/58 To:   The Chairman From: GrnMtn Power    Youll get an inspector over there soon?  What do you think this is? Its not safe up here.  Im sending my kids to visit their grandmother down at Devils Gorge.  I know nothings going to go wrong there -- one of your Inspectors lives next to my mother-in-law.   Move it, guys, this thing could be dangerous.
 * [12] :: Date: 11/16/58 To:   Edith Waxman From: Inspector Boggs    I believe, Mrs. Waxman, you have blown all of this out of proportion. Of course your cat has lost its fur -- cats shed at this time of year, and having three screaming grandchildren running around the house cant help. I have relayed your concerns to the Devils Gorge supervisor and he promises to contact you as soon as he gets a new rad suit.
 * [13] :: Date: 11/16/58 To:   All From: Deathangels Shadow    The Eastern Seaboard Fission Authority doesnt know were here! Thats right, folks, ESFA thinks they are safe.  Little do they know we leeched this upper section onto their board.  Were filling it with some great stuff, too.  Check out the new addition to their software library.
 * [14] :: Date: 11/16/58 To:    From: Deathangels Shadow    Be careful if you cruise near the Citizens for a Free Matrix db. I got some bad vibes off that one. Someone said theyre running a Trojan Horse program, you know, with viruses in it, but I didnt see anything like that. Maybe they used to do that, or are gunna in the future.  Forewarned is  unflatlined.  Later...
 * [15] :: Date: 11/16/58 To: Matt Shaw From: Sumdiv Kid    You gunna do another rev of BattleChess?  Deuce was great -- kinda hoping youd be on trey or four. Deathangels Shadow, Bosch and I all tinkered with your logic in BattleChess deuce.  I did the least mods -- just weighted pieces differently to suit my style, you know? -- and trashed them.  Not enough work to make a new rev.  I leave that to you -- a master at work and all that.
 * [16] :: To: All From: Modern Miles    Invites to all cowboys, from the Gentleman Loser DB.  There are warez to be had.  The link code is "LOSER", and so is the word.
 * [17] :: To: Deathangels Shadow From: Gabby    Thanks for the tip about Finn.  He does have loads of stuff, he even tried to sell me a joystick.  Like what would I do with that?
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class EasternSeaFissionDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public EasternSeaFissionDatabaseView(GameState gs, Pane p, PopupListener l) {
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

        Text helloText = new Text(dbTextResource.get(2) + "\n\n\n\n");

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
        if (accessLevel > 2) {
            menuString += "\r" + dbTextResource.get(3);
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
            case "1" -> { // About system
                viewText(5);
            }
            case "2" -> { // About tournaments
                downloads();
            }
            case "3" -> {
                messages();
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
            // else ignore key

        }
        return super.handleKeyEvent(keyEvent);
    }
}
