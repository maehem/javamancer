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
[0] :: * Bank Gemeinschaft *
[1] :: X. Exit System 1. List of Services 2. Current Rates 3. Recommended Securities
[2] :: 4. Message Base 5. Software Library 6. Funds Transfer
[3] ::   Welcome to Bank Gemeinschaft. We are now in our 6th century of service to customers of discretion.
[4] ::  Software Library  X. Exit To Main 1. Decoder 1.0 2. BudgetPal 24.0 3. Receipt Forger 7.0
[5] ::     Bank Gemeinschaft prides itself on its long years of service to individuals and nations.  The North family still draws a substantial "pension" from the funds we hid from Congressional investigators and the funds entrusted to us by President Nkrumah are still gathering interest. Below is a list of our services.
[6] :: 1) Automatic Funds Transfer:  Our AFT program runs 3.7 nanoseconds faster than our competition, yet we credit you with interest including the time of the transfer.
[7] :: 2) Investment Counciling:  We maintain vast portfolios of securities making us a force within world markets.  If we decide to rock the boat, we will inform our customers.
[8] :: 3) Fully Automated Bank Transfers: Need an anonymous deposit in a discreet account at another bank? We do that easier than pouring cash into a paper bag.
[9] :: 1) Automatic Funds Transfer: .001%,    charged against base account. 2) Investment Counciling: .05%    commission charged on buying and    selling securities, though they    are held in our ultra-secure    vaults at no charge. 3) Fully Automated Bank Transfers:    .002% on individuals, .01% on    government and drug transactions.     Interest Rates paid: 1) 10% on normal savings 2) 15% on Money Market Funds 3) 15-20 on Certificates of Deposit
[10] ::     As always, we suggest Bank Gemeinschaft as a viable investment. Our investments have a 99.4% security rating, and show an annual return rate of 22.1% per year.  Our stock reflects this.  In the interest of those who wish to diversify their portfolios, we provide the following list of other solid investments, all of which can be accessed through our brokerage affiliate.  1) Bell Europa 2) Musabori 3) Hitachi Biotech 4) Maas Biolabs 5) Hosaka 6) Fuji Electric 7) Tessier-Ashpool 8) Allard Technologies    (a T-A subsidiary)
[11] :: To: Herr Geistjager From: M. Godot      Thank you for the tip about Fuji Electric.  I have transferred the $30,000 to your discretionary account as you requested.
[12] :: To: Herr Geistjager From: Roger Kaliban      I resent very strongly your suggestion that I am in any way involved in banking irregularities, but I do appreciate your having brought your suspicions to me before taking them to banking officials.  I have deposited a token of my appreciation in your discretionary account.
[13] :: To: Epkot Foundation From: Roger Kaliban      I have looked into your complaint concerning the delay in cash transfers  to the Kryo-sleep Corp. of Veracruz. I have apologized to them on your behalf and have assured them there will be no further delays in forwarding payments to them.  They said Walt had not begun to thaw anyway, so no harm was done. Your obedient servant, RR
[14] :: To:  From: Matt Shaw       I figured this would be a safe place to dump the warez I figure youll need in the future.  Check the library section.
[15] :: To: Adrian Finch From: Thomas Cole  Adrian,     Im uneasy about the moves Musabori is making on the international scene.  Phillip over at Bank of Zurich is uncovering all sorts of irregularities with their accounts.  Musabori says it is because of cyberspace Cowboy meddling, but  I am not convinced.  Theyve lost some money over there to an embezzler, if rumors are at all true.  We should be careful.
[16] :: To: Thomas Cole From: Adrian Finch      Ive foreseen the problem, Thomas, and have taken steps to correct it.   Roger Kaliban is a security expert. If Zurich had had him on their staff,  theyd not have had any problem.
[17] ::             Funds Transfer  Enter source account number:
[18] :: Enter source authorization code:
[19] :: account no#: 646328356481     credits:
[20] :: Enter amount to transfer:
[21] :: Enter destination bank link code:
[22] :: Enter destination account number
[23] :: Transferring...
[24] :: Transfer complete.
[25] :: Incorrect authorization code.
[26] :: Unknown bank.
[27] :: Unknown account.
[28] ::
[29] :: Unable to transfer
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class BankGemeinschaftDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, EDIT
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public BankGemeinschaftDatabaseView(GameState gs, Pane p, PopupListener l) {
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
                viewText(5, 6, 7, 8);
            }
            case "2" -> {
                viewText(9);
            }
            case "3" -> {
                viewText(10);

            }
            case "4" -> {  // Faculty news
                if (accessLevel > 1) {
                    messages();
                }
            }
            case "5" -> {  // Faculty news
                if (accessLevel > 1) {
                    downloads();
                }
            }
            case "6" -> {  // Faculty news
                if (accessLevel > 1) {
                    transfer();
                }
            }
        }
    }

    private void transfer() {
        LOGGER.log(Level.SEVERE, "Bank Gemeinschaft: funds transfer");
        pane.getChildren().clear();

        Text subHeadingText = new Text("\n"
                + dbTextResource.get(17)
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
