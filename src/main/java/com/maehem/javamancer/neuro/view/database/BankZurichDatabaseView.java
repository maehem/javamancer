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
[0] :: * Bank Zurich -- Orbital *
[1] :: X. Exit System 1. Open an Account 2. Current Rates 3. Of Interest 4. Message Base 5. Account Operations
[2] ::
[3] ::    Current Rates 1) Automatic Funds Transfer: .003%,    charged against base account. 2) Investment Counciling: .06%    commission charged on buying and    selling securities, though they    are held in our ultra-secure    vaults at no charge. 3) Fully Automated Bank Transfers:    .01% on individuals, .05% on    government and drug transactions.     Interest Rates 1) 10% on normal savings 2) 15% on secret accounts 3) 15-20% on Certificates of Deposit 4) Prime Interest Rate : 24.358%
[4] ::              Of Interest       an information service of         Bank of Zurich Orbital      Art aficionados will be pleased to hear that Vincent Van Goghs Sunflowers #39 went up on the auction block at Christies in London.  Bidders  from all over the world fought over the painting, but the pack quickly thinned as the bidding got steep. Bank of Zurich Orbital acted for one of our customers in this matter, but she dropped out when the price hit 2.5 billion.  The painting sold for a record 5.6 billion.  Rumor has it that the purchaser was a Third World Warlord who sold his country to a multinat to secure the painting.      Bank of Berne has denied rumors that money has been chiseled from some of its accounts.  Cyberspace Cowboys, according to some wild reports, are able to siphon funds from accounts, but Bank Zurich Orbitals security specialist, Roger Trinculo, says, "That couldnt happen here.  Things are too tightly controlled at BZO."
[5] :: To:  From: Phillip dArgent      Thank you for opening an account with us.  I am certain you will find us the soul of discretion.  We look forward to a long and fruitful relationship with you.
[6] :: To:  From: Matt Shaw      Theres definitely some strange things going on in the matrix, thats for certain.  Ive not heard from Distress Damsel or the Sumdiv Kid lately.  Have you?  Its really strange not to have them out here messing around.
[7] :: To: Graceland Foundation From: Phillip dArgent      I have taken the steps you have requested to squash the unauthorized  cloning attempt that concerned you. Our experts agree that obtaining another DNA sample will be difficult. We have planted a story within the Presley underground that the clone attempt was by a Rastafarian group wishing to use the King as a symbol.     We do suggest you reinforce the concrete over the grave and add  cyberhounds to patrol at night to prevent another attempt at resurrection. Our experts have been paid from your account and we feel certain the spectacular reports of the Delhi explosion will prevent others from attempting to clone your client.  Your obedient servant, PdA
[8] :: To: Phillip dArgent From: Thomas Cole  Phillip,     Thank you for being so frank about your losses.  We have an excellent security man, Roger Kaliban, would could be of service to you, if you need him.  I appreciate your sharing your information with me. We shall be on the look-out for any strangeness that occurs in our accounts.
[9] :: Sorry, we are not accepting new accounts at this time.
[10] :: Enter amount to upload from chip:
[11] :: Minimum opening amount is 1000 credits
[12] :: name:  chip =          account =           X. Exit To Main         D. Download Credits         U. Upload Credits
[13] :: Enter amount to download:
[14] :: Enter amount to upload:
[15] ::
[16] :: Thank you for joining our bank. Your account number is 712345450134.
[17] :: You must have an account first.
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class BankZurichDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, EDIT
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public BankZurichDatabaseView(GameState gs, Pane p, PopupListener l) {
        super(gs, p, l);

        landingPage();
    }

    @Override
    protected final void landingPage() {
        pane.getChildren().clear();
        mode = Mode.SUB;
        CONTINUE_TEXT.setVisible(true);

        Text helloText = new Text("\n\n\n\n\n\n\n\n\n");

        TextFlow tf = pageTextFlow(headingText, helloText, CONTINUE_TEXT);

        pane.getChildren().add(tf);
        pane.setOnMouseClicked((t) -> {
            t.consume();
            landingContinue();
        });

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
            case "1" -> {
                if (accessLevel > 2) {
                    if (gameState.bamaZurichId == null) {
                        gameState.bamaZurichId = "712345450134";
                        database.bbsMessages.forEach((t) -> {
                            t.show = true;
                        });
                        buildVisibleMessagesList();
                    }
                    viewText(16);
                } else {
                    viewText(9);
                }
            }
            case "2" -> {
                viewText(3);
            }
            case "3" -> {
                viewText(4);
            }
            case "4" -> {
                messages();
            }
            case "5" -> {
                if (gameState.bamaZurichId == null) {
                    viewText(17);
                } else {
                    viewText(12);
                    // TODO: Add upload/download credits.
                }
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
