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
[0] :: * Gentleman Loser *
[1] :: X. Exit System 1. Loser BBS
[2] :: 2. Sorceror BBS 3. Software Library
[3] ::  Software Library  X. Exit To Main 1. PikUpGirls 1.0 2. BlowTorch 1.0 3. Hammer 1.0 4. Probe 3.0
[4] :: The faces of the neon forest welcome you to the Gentleman Loser base. Enter and know the feeling of black fire racing through your nerves.
[5] :: To: Bleys From: Chaos   I dont know where Amber is, either. I saw her at Gridpoint three weeks ago.  She said shed found a big stash of softwarez and she was looking for some Thunderhead to crack in and get it.  Check the messages at Eastern Seaboard (link # EASTSEABOD).
[6] :: To: Keefer From: Bleys    Any idea what happened to Amber? I hear rumors shes off in a secret game somewhere in cyberspace.  I was supposed to meet her at the Loser yesterday, but she never showed.
[7] :: To: Bleys From: Keefer    Nobodys seen Amber for two weeks now. Maybe shes gone over to the other side.  Try Chaos.  Maybe he knows something.
[8] :: To: Ulm Kris From: Lord B4    Hey, remember me?  You still owe me 4000 credits for the Bushido I sold you.  Seems like an eternitys gone by since then.  If I dont hear from you soon, Ill send the Ripper out after you.
[9] :: To: Pol Oconner From: Osric    Theyve been asking for you at the Body Shop in Chiba.  Some buyer named Random wants a complete set of body parts from you.  Hes offering to replace all the parts you sell him with sturdy plastic replacements. He says itll make a New Human out of you.
[10] :: To: Everyone From: Red Jack    Bank Gemeinschafts been ripping my account again.  Im not going to take this lying down, so Im asking everyone to dial into their base and tie up their link lines.  The link  code is "BANKGEMEIN" if you want to help me out.  Poke around while youre in there---maybe youll find something useful.
[11] :: To: Dr. Asano From: Matsumoto    Gorotas missing and its all your fault, you crazy old man!  You activated that COMSAT for him at Mission Control!  Now hes way off in cyberspace somewhere!  Youd better get out and start looking for him, or Ill tell the Turing people all about your pet project!
[12] :: To: Matsumoto From: Turing    We just happened to glance over your message to Dr. Asano.  Wed like to hear about this pet project you were referring to.
[13] :: To: Count Floyd From: Matt Shaw    Chill out, Count.  Quixote blipped into Gridpoint early this morning. He said he had to leave town because of a death in the family.  No worries.
[14] :: To: Chipdancer From: Count Floyd    Want to hear something scary?  Quixote disappeared on his way out to Bank G. last night.  Sancho stayed with him for half the trip, but he had to bail out when Quixote sent him back for more icebreakers.  When Sancho got back, Q was gone.
[15] :: To: Don Quixote From: Chipdancer    After those windmills again, eh?  If youre serious about hitting Bank Gem. again, EINHOVEN should make it easier.
[16] :: To:  From: Matt Shaw    Watch out for BLAMMO software.  Itll put your eye out.
[17] :: To: Ernie From: Bert    Thanks for turning me on to that great software in the Loser library!  I downloaded the PikUpGirls soft and it changed my whole life!  It told me all about What WoMen Really Want!  Now Ive got so many women after me, I barely have time to jack in!
[18] :: To: Red Jack From: Someone    The word is that Lupus and his modern friends are the ones burning the banks.  So ask him about your dough.
[19] :: 5. Slow 1.0 6. Injector 1.0 7. Drill 1.0
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class GentlemanLoserDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public GentlemanLoserDatabaseView(GameState gs, Pane p, PopupListener l) {
        super(gs, p, l);
        landingPage();
    }

    @Override
    protected final void landingPage() {
        pane.getChildren().clear();
        mode = Mode.SUB;

        Text paddingText = new Text(dbTextResource.get(4) + "\n\n\n\n");

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
            case "1" -> { // Notes of interest
                messageIndex = 0;
                messages();
            }
            case "2" -> {
                if (accessLevel > 1) {
                    messageIndex = 0;
                    messages2();
                }
            }
            case "3" -> {
                if (accessLevel > 1) {
                    downloads();
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
            // else ignore key

        }
        return super.handleKeyEvent(keyEvent);
    }
}
