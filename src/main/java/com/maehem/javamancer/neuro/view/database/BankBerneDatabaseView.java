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
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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

    private int transferTicks = 0;
    private final static int TX_TICKS = 60;
    private int transferAmount;
    private boolean srcAcctCorrect = false;

    private enum Mode {
        SUB, MENU, TRANSFER1, TRANSFER2
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    private enum TransferMode {
        LINK, AMOUNT, DEST_ACCT, TRANSFERING, RESULT
    }
    private TransferMode txMode = TransferMode.LINK;

    private final Text CURSOR_TXT_1 = new Text(CURSOR);
    private final Text CURSOR_TXT_2 = new Text(CURSOR);
    private final Text CURSOR_TXT_3 = new Text(CURSOR);
    private final Text typedSourceAccountNumber = new Text();
    private final Text typedAuthCode = new Text();
    private final Text typedAmount = new Text();
    private final Text typedDestLink = new Text();
    private final Text typedDestAccount = new Text();

    private final String SRC_ACCT_LABEL_STR = dbTextResource.get(12);
    private final String FUNC_LABEL = SRC_ACCT_LABEL_STR.split("\r")[0].trim();
    private final Text SRC_ACCT_LABEL_TXT = new Text(SRC_ACCT_LABEL_STR);

    private final Text AUTH_CODE_LBL_TXT = new Text("\n" + dbTextResource.get(13));
    private final Text CREDITS_LBL_TXT = new Text("\n" + dbTextResource.get(14) + " ");
    private final Text AMOUNT_LABEL_TXT = new Text("\n" + dbTextResource.get(15));
    private final Text DEST_CODE_LABEL_TXT = new Text("\n" + dbTextResource.get(16));
    private final Text DEST_ACCT_LABEL_TXT = new Text("\n" + dbTextResource.get(17));
    private final Text TX_MESSAGE_TXT = new Text(dbTextResource.get(18));
    private final Text TX_COMPLETE_TXT = new Text(dbTextResource.get(19));
    private final String AUTH_INCORRECT_STR = dbTextResource.get(20);
    private final String UNKNOWN_BANK_STR = dbTextResource.get(21);
    private final String UNKNOWN_ACCT_STR = dbTextResource.get(22);
    private final String TX_UNABLE_STR = dbTextResource.get(24);

    private final Text txResultMessage = new Text("Transmit Result Message");
    private final Text bankBalanceText = new Text(String.valueOf(gameState.bankBerneBalance));

    public BankBerneDatabaseView(GameState gs, Pane p, PopupListener l) {
        super(gs, p, l);
        landingPage();
    }

    @Override
    protected final void landingPage() {
        pane.getChildren().clear();
        mode = Mode.SUB;

        Text paddingText = new Text("\n\n\n\n\n\n\n\n\n\n\n\n");

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
                transfer1();
            }
            case "5" -> {
                downloads();
            }
            case "6" -> {
                messages2();
            }
        }
    }

    private void transfer1() {
        LOGGER.log(Level.FINE, "Bank of Berne: funds transfer part 1");
        mode = Mode.TRANSFER1;
        pane.getChildren().clear();
        srcAcctCorrect = false;

        TextFlow contentTf = simpleTextFlow(SRC_ACCT_LABEL_TXT,
                new Text("\n"),
                typedSourceAccountNumber, CURSOR_TXT_1,
                new Text("\n\n"),
                AUTH_CODE_LBL_TXT,
                new Text("\n"),
                typedAuthCode, CURSOR_TXT_2,
                new Text("\n\n"),
                txResultMessage,
                new Text("\n")
        );
        contentTf.setMinHeight(230);
        contentTf.setTextAlignment(TextAlignment.LEFT);

        TextFlow pageTf = pageTextScrolledFlow(headingText, contentTf);

        pane.getChildren().add(pageTf);
        pane.setOnMouseClicked(null);
        pane.layout();

        Platform.runLater(() -> {
            typedSourceAccountNumber.setText("");
            typedAuthCode.setText("");

            contentTf.layout();
            txResultMessage.setVisible(false);
            AUTH_CODE_LBL_TXT.setVisible(false);
            typedAuthCode.setVisible(false);
            CURSOR_TXT_2.setVisible(false);
        });
    }

    private void transfer2() {
        LOGGER.log(Level.FINE, "Bank of Berne: funds transfer part 2");
        mode = Mode.TRANSFER2;
        txMode = TransferMode.LINK;

        pane.getChildren().clear();

        CONTINUE_TEXT.setVisible(true);

        TextFlow contentTf = simpleTextFlow(new Text(centeredText(FUNC_LABEL)),
                CREDITS_LBL_TXT, bankBalanceText,
                new Text("\n"),
                DEST_CODE_LABEL_TXT, new Text("\n"),
                typedDestLink, CURSOR_TXT_1, // BOZOBANK
                AMOUNT_LABEL_TXT, new Text("\n"), // Amount to transfer
                typedAmount, CURSOR_TXT_2,
                DEST_ACCT_LABEL_TXT, new Text("\n"),
                typedDestAccount, CURSOR_TXT_3,
                new Text("\n\n"),
                TX_MESSAGE_TXT, TX_COMPLETE_TXT, txResultMessage,
                new Text("\n"),
                CONTINUE_TEXT,
                new Text("\n")
        );

        contentTf.setLineSpacing(contentTf.getLineSpacing() + 0.2);
        TextFlow pageTf = pageTextScrolledFlow(headingText, contentTf);
        

        pane.getChildren().add(pageTf);
        pane.setOnMouseClicked(null);
        pane.layout();

        // Initial state of transfer page.
        Platform.runLater(() -> {
            txResultMessage.setText("");
            typedDestLink.setText("");

            // Invisible until link OK.
            AMOUNT_LABEL_TXT.setVisible(false);
            typedAmount.setText("");
            CURSOR_TXT_2.setText(" ");

            // Invisible until amount OK.
            DEST_ACCT_LABEL_TXT.setVisible(false);
            typedDestAccount.setText("");
            CURSOR_TXT_3.setText(" ");

            TX_MESSAGE_TXT.setVisible(false);
            TX_COMPLETE_TXT.setVisible(false);

            // Invisible until tranmit finished.
            CONTINUE_TEXT.setVisible(false);
            pane.layout();
        });
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        LOGGER.log(Level.FINEST, "Handle key event.");
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
            case TRANSFER1 -> {
                if (code == KeyCode.ESCAPE) {
                    siteContent();
                    return false;
                }
                txResultMessage.setVisible(false);

                Text typedText;
                if (!srcAcctCorrect) {
                    typedText = typedSourceAccountNumber;
                } else {
                    typedText = typedAuthCode;
                }

                if (code == KeyCode.X || code == KeyCode.ESCAPE) {
                    siteContent();
                } else if (code.isDigitKey()) {
                    String typed = typedText.getText();
                    if (typed.length() < 12) {
                        typedText.setText(typed + code.getChar());
                    }
                } else if (srcAcctCorrect && code.isLetterKey()) {
                    String typed = typedText.getText();
                    if (typed.length() < 12) {
                        typedText.setText(typed + code.getChar().toUpperCase());
                    }
                } else if (code == KeyCode.BACK_SPACE) {
                    String txt = typedText.getText();
                    if (!txt.isEmpty()) {
                        typedText.setText(txt.substring(0, txt.length() - 1));
                    }
                } else if (code == KeyCode.ENTER) {
                    if (!srcAcctCorrect) { // Source Acct Entry
                        if (typedText.getText().equals(GameState.BANK_BERNE_ID)) {
                            LOGGER.log(Level.FINE, "Correct Account ID entered.");
                            srcAcctCorrect = true; // Dialog can move to auth code entry.
                            AUTH_CODE_LBL_TXT.setVisible(true);
                            typedAuthCode.setVisible(true);
                            CURSOR_TXT_2.setVisible(true);
                            CURSOR_TXT_1.setVisible(false);
                            txResultMessage.setVisible(false);
                        } else {
                            LOGGER.log(Level.WARNING, "Wrong source acct number");
                            Platform.runLater(() -> {
                                txResultMessage.setText(UNKNOWN_ACCT_STR);
                                txResultMessage.setVisible(true);
                                //pane.layout();
                            });
                        }
                    } else { // Auth Code Entry
                        if (typedText.getText().equals(GameState.BANK_BERNE_AUTH_CODE)) {
                            LOGGER.log(Level.FINE, "Correct Auth Code entered.");
                            transfer2();
                        } else {
                            LOGGER.log(Level.WARNING, "Wrong auth code entered.");
                            Platform.runLater(() -> {
                                txResultMessage.setText(AUTH_INCORRECT_STR);
                                txResultMessage.setVisible(true);
                                //pane.layout();
                            });
                        }
                    }
                }
            }

            case TRANSFER2 -> {
                handleTransfer2KeyEvent(keyEvent);
            }

        }
        return super.handleKeyEvent(keyEvent);
    }

    private void handleTransfer2KeyEvent(KeyEvent ke) {
        KeyCode code = ke.getCode();
        LOGGER.log(Level.FINEST, () -> "Handle TX2 Code: " + code.getName());
        if (code == KeyCode.ESCAPE) {
            siteContent();
            return;
        }

        switch (txMode) {
            case LINK -> {
                // Typing link
                if (code.isLetterKey()) {
                    if (!txResultMessage.getText().isEmpty()) {
                        txResultMessage.setText("");
                        typedDestLink.setText("");
                    }
                    String typed = typedDestLink.getText();
                    if (typed.length() < 12) {
                        typedDestLink.setText(typed + code.getChar());
                    }
                } else if (code == KeyCode.ENTER) {
                    // Evaluate
                    if (typedDestLink.getText()
                            .toUpperCase()
                            .equals("BOZOBANK")) {
                        LOGGER.log(Level.FINE, "Link Code BOZOBANK accepted.");
                        AMOUNT_LABEL_TXT.setVisible(true);
                        CURSOR_TXT_2.setText(CURSOR);
                        txMode = TransferMode.AMOUNT;
                    } else { // invaalid link code
                        LOGGER.log(Level.WARNING, "Unknown bank link code.");
                        txResultMessage.setText(UNKNOWN_BANK_STR);
                    }
                } else if (code == KeyCode.BACK_SPACE) {
                    if (!txResultMessage.getText().isEmpty()) {
                        txResultMessage.setText("");
                        typedDestLink.setText("");
                    }
                    String typed = typedDestLink.getText();
                    if (!typed.isEmpty()) {
                        typedDestLink.setText(typed.substring(0, typed.length() - 1));
                    }
                }
            }
            case AMOUNT -> {
                // Typing amount
                if (code.isDigitKey()) {
                    if (txResultMessage.isVisible()) {
                        txResultMessage.setText("");
                        typedAmount.setText("");
                    }
                    String typed = typedAmount.getText();
                    if (typed.length() < 9) {
                        typedAmount.setText(typed + code.getChar());
                    }
                } else if (code == KeyCode.ENTER) {
                    // Evaluate
                    int amount = Integer.parseInt(typedAmount.getText());
                    if (amount <= gameState.bankBerneBalance) {
                        DEST_ACCT_LABEL_TXT.setVisible(true);
                        CURSOR_TXT_3.setText(CURSOR);
                        this.transferAmount = amount;
                        txMode = TransferMode.DEST_ACCT;
                    } else { // invaalid link code
                        LOGGER.log(Level.WARNING, "Wrong amount.");
                        txResultMessage.setText(TX_UNABLE_STR);
                    }
                } else if (code == KeyCode.BACK_SPACE) {
                    if (!txResultMessage.getText().isEmpty()) {
                        txResultMessage.setText("");
                        typedAmount.setText("");
                    }
                    String typed = typedAmount.getText();
                    if (!typed.isEmpty()) {
                        typedAmount.setText(typed.substring(0, typed.length() - 1));
                    }
                }
            }
            case DEST_ACCT -> {
                // Typing Dest Account
                txResultMessage.setText("");
                if (code.isDigitKey()) {
                    if (!txResultMessage.getText().isEmpty()) {
                        txResultMessage.setText("");
                        typedDestAccount.setText("");
                    }
                    String typed = typedDestAccount.getText();
                    if (typed.length() < 12) {
                        typedDestAccount.setText(typed + code.getChar());
                    }
                } else if (code == KeyCode.ENTER) {
                    // Evaluate
                    if (typedDestAccount.getText().equals(GameState.BANK_ZURICH_ID)) {
                        txMode = TransferMode.TRANSFERING;
                        TX_MESSAGE_TXT.setVisible(true);
                        transferTicks = TX_TICKS;
                    } else { // invaalid link code
                        LOGGER.log(Level.WARNING, "Wrong account.");
                        txResultMessage.setText(UNKNOWN_ACCT_STR);
                        txResultMessage.setVisible(true);
                        //pane.layout();
                    }
                } else if (code == KeyCode.BACK_SPACE) {
                    if (!txResultMessage.getText().isEmpty()) {
                        txResultMessage.setText("");
                        typedDestAccount.setText("");
                    }
                    String typed = typedDestAccount.getText();
                    if (!typed.isEmpty()) {
                        typedDestAccount.setText(typed.substring(0, typed.length() - 1));
                    }
                }
            }
            case TRANSFERING -> {
                // No interaction allowed. See tick().
            }
            default -> { // RESULT
                // Result shown. Space to continue.
                if (code == KeyCode.SPACE) {
                    siteContent();
                }
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (mode == Mode.TRANSFER2 && txMode == TransferMode.TRANSFERING) {
            if (transferTicks > 0) {
                transferTicks--;
            } else {
                // CHeck if error.
                //txResultMessage.setText(txCompleteStr);
                gameState.bankBerneBalance -= transferAmount;
                gameState.bankZurichBalance += transferAmount;
                bankBalanceText.setText(String.valueOf(gameState.bankBerneBalance));
                CONTINUE_TEXT.setVisible(true);
                TX_COMPLETE_TXT.setVisible(true);
                //txResultMessage.setText("transfer complete.");
                txMode = TransferMode.RESULT;
                LOGGER.log(Level.INFO, "Bank Transfer completed.");
            }
        }
    }

}
