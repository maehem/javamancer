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
[0] :: * Bank Of Berne *
[1] :: X. Exit System 1. Current Rates 2. In The Know 3. Message base 4. Funds Transfer 5. Software Library 6. AI Message Buffer
[2] ::  Software Library  X. Exit To Main 1. ArmorAll 1.0 2. Slow 3.0 3. Probe 10.0
[3] ::    Current Rates 1) Automatic Funds Transfer: .004%,    charged against base account. 2) Investment Counciling: .10%     commission charged on buying and    selling securities, though they    are held in our ultra-secure    vaults at no charge. 3) Fully Automated Bank Transfers:    .02% on individuals, .075% on    government and drug transactions.     Interest Rates 1) 11% on normal savings 2) 16% on secret accounts 3) 15-25% on Certificates of Deposit 4) Prime Interest Rate : 24.358%
[4] ::              In the Know         an information service           of Bank of Berne.    The Bank of Bernes security chief, Roger Stefano, is dead.  "Roger died in a terrorist strike against one of our bank branches.  The attackers, members of the National Liberation Front for the Canary Islands, were intent upon kidnapping April Heurse, the daughter of the Canary Islands leading industrialist.  Roger stopped two bullets meant for her, and April, drawing a fully automatic mini-uzi from her purse, killed the three attackers.    We deeply regret Rogers loss, but we would like to point out that all of our customers can expect this sort of dedication from our people.    Bank Gemeinschaft has denied rumors that money has been chiseled from some of its accounts.  Cyberspace Cowboys, according to some wild reports, are able to syphon funds from accounts, but Bank of Bernes new security specialist, Horatio Ariel, says, "That couldnt happen here.  Things are too tightly controlled at the Bank of Berne."
[5] :: To: Tozoku Imports From: Anson Auric    We are inquiring about a shipment for Mr. STEFAN ROGERS.  We understand it might arrive in pieces, and we have no difficulty in accepting it this way.  How soon will it be delivered?  We have, as you will recall, paid in advance for this shipment, and our client is very concerned over it.  We hope it has not disappeared.  Sincerely, Anson Auric
[6] :: To: Anson Auric From: Tozuku Imports Re: Rogers shipment    Not lost, merely hiding.  We are closing in on it and expect delivery soonest.  We will notify you when things are under control.
[7] :: To: Anson Auric From: Thomas Cole  Anson,   Thank you for being so frank about your losses.  We have an excellent security man, Roger Kaliban, who could be of service to you, if you need him.  I appreciate your sharing your information with me.  We shall be on the look-out for any strangeness that occurs in our accounts.
[8] :: To:  Anson Auric From: Administration    Due to the security problems weve been having, weve changed the master authorization code for the Funds Transfer system to LYMA1211MARZ.  The Funds Reserve Account number is still 121519831200.  Thank you for your cooperation in this matter.
[9] ::
[10] :: To: Gold From: Greystoke    Yes, yes, the cyberscum must be stopped from raping the banks.  You are right to bring this to our attention.  Do what you must to stop them.
[11] :: To: Gold From: Neuromancer    Gold, I believe you should not take offense at the pilfering of rounding errors.  We all know you keep track of them down to the hundredth digit.  That these cowboys place a value on that sort of thing does not make you any less powerful.  If they attack you, by all means, defend yourself, but we should not take the offensive, no matter what Greystoke predicts.
[12] ::             Funds Transfer  Enter source account number:
[13] :: Enter source authorization code:
[14] :: account no#: 121519831200     credits:
[15] :: Enter amount to transfer:
[16] :: Enter destination bank link code:
[17] :: Enter destination account number
[18] :: Transferring...
[19] :: Transfer complete.
[20] :: Incorrect authorization code.
[21] :: Unknown bank.
[22] :: Unknown account.
[23] ::
[24] :: Unable to transfer
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class BankBerneDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public BankBerneDatabaseView(GameState gs, Pane p, PopupListener l) {
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

        Text helloText = new Text("\n\n\n\n");

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
            case "1" -> {
                viewText(3);
            }
            case "2" -> {
                viewText(4);
            }
            case "3" -> {
                messages();
            }
            case "4" -> {
                transfer();
            }
            case "5" -> {
                downloads();
            }
            case "6" -> {
                messages2();
            }
        }
    }

    private void transfer() {
        LOGGER.log(Level.FINE, "Bank Berne: transfer");
        pane.getChildren().clear();

        Text subHeadingText = new Text("\n"
                + dbTextResource.get(15) + "\n\nTODO\n\n"
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
        LOGGER.log(Level.FINE, "Handle key event.");
        switch (mode) {
            case MENU -> {
                if (code.equals(KeyCode.X)
                        || code.equals(KeyCode.SPACE)
                        || code.equals(KeyCode.ESCAPE)) {
                    LOGGER.log(Level.WARNING, "Menu wants to exit system.");
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
