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

import com.maehem.javamancer.neuro.model.BbsMessage;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.item.DeckItem;
import com.maehem.javamancer.neuro.view.PopupListener;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class PantherModernsDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, DOWNLOADS, MSG, SHOW, SEND
    }

    //private Mode mode = Mode.SUB; // Sub-mode handled by superclass.
    private static final String BOB_MSG_TO = "modern bob mod bob";
    private static final String BOB_MSG_BODY = "link code sea hitachi regular fellows";

    /*
    [0] :: * Panther Moderns *
[1] :: X. Exit System 1. Software Library 2. Modern BBS 3. Send Message
[2] :: To:    From: Modern Bob  Got your message. Hitachi link code is "HITACHIBIO". SEA link code is"SOFTEN". Regular Fellows link code is "REGFELLOW".
[3] :: To:   Everyone From: Modern Yutaka  Good one. Cowboy named Chipdancer owed me a favor. Broke into the Hosaka base with Comlink 5.0, used "FUNGEKI", and then added my name to their employee list.  Received paychecks for six weeks before anyone noticed. Only risk was walking in to pick up check.
[4] :: To: All From: Modern Miles    Julius Deane knows Cryptology. He also wanted to let people know hes got some hard to find skill chips, in case anyones interested.
[5] :: To: Everyone From: Polychrome    Screaming Fist has Easy Rider 1.0 in their base. Lets you cross zones without having to go to another cyberjack.
[6] :: To: Angelo From: Lupus Yonderboy    Mr. Who paid us on the SENSE/NET gig.  Hell remain a Mr. Who, not a Mr. Name.  He understands now.  Chaos is our mode and modus.  Our central kick.  Stories to be told, offline in the meeting room.  All you have to do is ask.
[7] :: To: Everyone From: Larry Moe    Dont worry about the meet room, no wilsons will get past me.  If youre Modern, youre in.  Good place for biz.  Ive got CopTalk now, Lupus has Evasion.  See you on the other side.
[8] :: To: Modern Miles From: Polychrome    Heard you were looking for a place. Cheap Hotel link code is CHEAPO.
[9] :: To: Everyone From: Modern Bob    I have the link code for Hitachi and the SEA.  If anyones interested, leave me a message.
[10] ::  Software Library  X. Exit To Main 1. Comlink 3.0 2. Mindbender 3.0 3. Chaos Videosoft 1.0
[11] :: 4. BlowTorch 3.0 5. Decoder 2.0 6. ThunderHead 1.0 7. Cyberspace 1.0
[12] :: Date:  To: From: 
[13] :: Send this message? (Y/N)
[14] :: Press ESC to end.
[15] :: To: Lupus From: Modern Jane    Congrats on burning Gemeinschaft.  Heard the fire was so small they dont even know who started it.
[16] :: To:  From: Matt Shaw    Word of warning: some dumb wilson just got fried by jacking into cyberspace from the Losers outlet. His first (and last) try. The ICE is softer at the bases you can reach from the Cheap Hotels jack, so brush up on your cyberspace techniques there first.

     */
    public PantherModernsDatabaseView(GameState gs, Pane p, PopupListener l) {
        super(gs, p, l);
        landingPage();
    }

    @Override
    protected final void landingPage() {
        pane.getChildren().clear();

        Text linePadding = new Text("\n\n\n\n\n\n");

        TextFlow tf = pageTextFlow(headingText, linePadding, CONTINUE_TEXT);
        pane.getChildren().add(tf);
    }

    @Override
    protected final void siteContent() {
        mainMenu();
    }

    private void mainMenu() {
        pane.getChildren().clear();
        //mode = Mode.MENU;
        messageIndex = 0;

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
                downloads();
            }
            case "2" -> {
                messages();
            }
            case "3" -> {
                composeMessage();
            }
        }
    }

    @Override
    protected BbsMessage sendMessage(String to, String message) {
        BbsMessage msg = super.sendMessage(to, message);

        if (BOB_MSG_TO.contains(msg.to.toLowerCase())) {
            for (String s : BOB_MSG_BODY.split(" ")) {
                if (msg.body.toLowerCase().contains(s.toLowerCase())) {
                    for (int i = 0; i < database.bbsMessages.size(); i++) {
                        BbsMessage item = database.bbsMessages.get(i);
                        if (item.prefillIndex == 2) {
                            enableMessage(item);
                            break; // break inner loop
                        }
                    }
                    break; // break outer loop
                }
            }
        }

        return msg;
    }

}
