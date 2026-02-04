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
import com.maehem.javamancer.neuro.view.SoundEffectsManager;
import java.util.logging.Level;
import javafx.geometry.Insets;
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

    private Text finishText;

    private enum Mode {
        SUB, MENU, CREATE, ACCOUNT, TRANSFER
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    private final Text typedNumberAmount = new Text();
    private final Text cursorText = new Text("<\n");
    private final Text chipBalanceText = new Text("         ");
    private final Text zurichBalanceText = new Text(String.valueOf(gameState.bankZurichBalance));
    private boolean transferMode = false; // download == true, upload == false;
    private final Text transferMessageText = new Text("");

    public BankZurichDatabaseView(GameState gs, Pane p, PopupListener l) {
        super(gs, p, l);

        initMessages();

        landingPage();
    }

    @Override
    protected final void landingPage() {
        pane.getChildren().clear();
        mode = Mode.SUB;
        CONTINUE_TEXT.setVisible(true);

        Text helloText = new Text("\n\n\n\n\n\n\n\n\n\n\n");

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
                if (gameState.bankZurichCreated == null) {
                    createAccount();
                } else {
                    viewText(9); // Not accepting new accounts.
                }
            }
            case "2" -> {
                viewText(3);
            }
            case "3" -> {
                viewText(4);
            }
            case "4" -> {
                if (gameState.bankZurichCreated == null) {
                    viewText(17); // Must have account first.
                } else {
                    messages();
                }
            }
            case "5" -> {
                if (gameState.bankZurichCreated == null) {
                    viewText(17); // Must have account first.
                } else {
                    accountOperations();
                }
            }
        }
    }

    private void createAccount() {
        pane.getChildren().clear();
        mode = Mode.CREATE;

        TextFlow tf = pageTextFlow(headingText);

        Text instructionsText;
        if (gameState.bankZurichCreated == null) {
            instructionsText = new Text(dbTextResource.get(10));
        } else {
            instructionsText = new Text(dbTextResource.get(9));
        }
        Text spacingText = new Text("\n             ");

        finishText = new Text("\n" + dbTextResource.get(16) + "\n\n\n\n\n\n\n\n");
        tf.getChildren().addAll(
                instructionsText,
                spacingText, typedNumberAmount, cursorText, new Text("\n"),
                finishText,
                CONTINUE_TEXT
        );

        finishText.setVisible(false);
        CONTINUE_TEXT.setVisible(false);

        pane.getChildren().add(tf);
        pane.setOnMouseClicked(null);
    }

    private void accountSummary(TextFlow tf) {
        String[] split = dbTextResource.get(12).split("\r");
        Text nameText = new Text("     " + split[0].replace("\1", gameState.name) + "\n");
        int acctIndex = split[1].indexOf("acc");
        Text chipLabel = new Text("     " + split[1].substring(0, acctIndex).trim());
        Text acctLabel = new Text("  " + split[1].substring(acctIndex));
//        String amountsStr = split[1].replace(
//                "         ",
//                String.format("%9d", gameState.moneyChipBalance)
//        ) + gameState.bankZurichBalance;
        tf.getChildren().addAll(
                nameText,
                chipLabel, chipBalanceText,
                acctLabel, zurichBalanceText,
                new Text("\n\n")
        );
    }

    private void accountOperations() {
        pane.getChildren().clear();
        mode = Mode.ACCOUNT;

        TextFlow tf = pageTextFlow(headingText);
        tf.setLineSpacing(-4);

        String[] split = dbTextResource.get(12).split("\r");
//        String amountsStr = split[1].replace(
//                "         ",
//                String.format("%9d", gameState.moneyChipBalance)
//        ) + gameState.bankZurichBalance;
//        Text instructionsText = new Text(
//                split[0].trim() + " " + gameState.name + "\n"
//                + amountsStr + "\n"
//                + split[2] + "\n"
//        );

        Text exitText = new Text("   " + split[3] + "\n");
        Text downloadText = new Text("   " + split[4] + "\n");
        Text uploadText = new Text("   " + split[5] + "\n");

        accountSummary(tf);
        tf.getChildren().addAll(
                exitText,
                downloadText,
                uploadText
        );

        pane.getChildren().add(tf);
        pane.setOnMouseClicked((t) -> {
            t.consume();
            siteContent();
        });

        downloadText.setOnMouseClicked((t) -> {
            t.consume();
            transferCredits(true);
        });
        uploadText.setOnMouseClicked((t) -> {
            t.consume();
            transferCredits(false);
        });
    }

    private void transferCredits(boolean txfrMode) {
        LOGGER.log(Level.INFO, "BankZurich: transfer credits");
        pane.getChildren().clear();
        mode = Mode.TRANSFER;
        transferMode = txfrMode;
        typedNumberAmount.setText("");

        TextFlow tf = pageTextFlow(headingText);
        tf.setLineSpacing(-4);


        Text instructionsText;
        if (txfrMode) {
            instructionsText = new Text("      " + dbTextResource.get(13));
        } else {
            instructionsText = new Text("      " + dbTextResource.get(14));
        }
        Text spacingText = new Text("\n             ");

        finishText = new Text("\n" + dbTextResource.get(16) + "\n\n\n\n\n\n\n\n");
        accountSummary(tf);
        tf.getChildren().addAll(
                instructionsText,
                spacingText, typedNumberAmount, cursorText, new Text("\n\n"),
                transferMessageText
        );

        pane.getChildren().add(tf);
        pane.setOnMouseClicked(null);
    }

    private void initMessages() {
        if (gameState.bankZurichCreated != null) {
            // Enable messages.
            database.bbsMessages.forEach((msg) -> {
                if (msg.date.equals("00/00/00")) {
                    msg.date = gameState.bankZurichCreated;
                }
            });
            buildVisibleMessagesList();
        }
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        LOGGER.log(Level.FINEST, "BankZurichDatabaseView: Handle key event.");
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
            case CREATE -> {
                keyEvent.consume();
                if (code == KeyCode.X || code == KeyCode.ESCAPE) {
                    if (!CONTINUE_TEXT.isVisible()) {
                        LOGGER.log(Level.FINE, "Go back up menu level.");
                        mainMenu();
                    }
                    return false;
                } else if (code == KeyCode.SPACE) {
                    if (CONTINUE_TEXT.isVisible()) {
                        siteContent();
                    }
                } else if (code == KeyCode.ENTER) {
                    int amount = Integer.parseInt(typedNumberAmount.getText());
                    if (amount <= gameState.moneyChipBalance) {
                        gameState.moneyChipBalance -= amount;
                        gameState.bankZurichCreated = gameState.getDateString();
                        gameState.bankZurichBalance = amount;
                        initMessages();

                        finishText.setVisible(true);
                        CONTINUE_TEXT.setVisible(true);
                        pane.requestLayout();
                    } else {
                        // Play "bad" sound.
                        gameState.resourceManager.soundFxManager.playTrack(SoundEffectsManager.Sound.DENIED);
                    }
                } else if (code == KeyCode.BACK_SPACE) {
                    String typedAmount = typedNumberAmount.getText();
                    typedNumberAmount.setText(typedAmount.substring(0, typedAmount.length() - 1));
                } else if (code.isDigitKey()) {
                    typedNumberAmount.setText(typedNumberAmount.getText() + code.getChar());
                }
            }
            case ACCOUNT -> {
                keyEvent.consume();
                if (null != code) {
                    switch (code) {
                        case X, ESCAPE -> {
                            LOGGER.log(Level.FINE, "Go back up menu level.");
                            mainMenu();
                            return false;
                        }
                        case D ->
                            transferCredits(true);
                        case U ->
                            transferCredits(false);
                        default -> {
                        }
                    }
                }
            }
            case TRANSFER -> {
                keyEvent.consume();
                if (code == KeyCode.X || code == KeyCode.ESCAPE) {
                    accountOperations();
                } else if (code == KeyCode.ENTER && !typedNumberAmount.getText().isEmpty()) {
                    int amount = Integer.parseInt(typedNumberAmount.getText());
                    if (transferMode) { // true == download to chip
                        if (amount <= gameState.bankZurichBalance) {
                            LOGGER.log(Level.FINE, "Do download of {0} from chip.", amount);
                            gameState.bankZurichBalance -= amount;
                            gameState.moneyChipBalance += amount;

                            accountOperations();
                        } else {
                            LOGGER.log(Level.FINE, "Unable to perform action.");
                            transferMessageText.setText("insufficient funds");
                            // play "bad" sound
                            gameState.resourceManager.soundFxManager.playTrack(SoundEffectsManager.Sound.DENIED);
                        }
                    } else { // upload to Zurich
                        if (amount <= gameState.moneyChipBalance) {
                            LOGGER.log(Level.FINE, "Do upload of {0} from chip.", amount);
                            gameState.bankZurichBalance += amount;
                            gameState.moneyChipBalance -= amount;

                            accountOperations();
                        } else {
                            LOGGER.log(Level.FINE, "Unable to perform action.");
                             transferMessageText.setText("insufficient funds");
                             // play "bad" sound
                             gameState.resourceManager.soundFxManager.playTrack(SoundEffectsManager.Sound.DENIED);
                        }
                    }
                } else if (code == KeyCode.BACK_SPACE && !typedNumberAmount.getText().isEmpty()) {
                    String typedAmount = typedNumberAmount.getText();
                    typedNumberAmount.setText(typedAmount.substring(0, typedAmount.length() - 1));
                } else if (code.isDigitKey()) {
                    typedNumberAmount.setText(typedNumberAmount.getText() + code.getChar());
                }
            }
            // else ignore key

        }
        return super.handleKeyEvent(keyEvent);
    }

    @Override
    public void tick() {
        super.tick();

        if (mode == Mode.ACCOUNT || mode == Mode.TRANSFER) {
            chipBalanceText.setText(
                    String.format("%9d", gameState.moneyChipBalance)
            );
            zurichBalanceText.setText(String.valueOf(gameState.bankZurichBalance));
        }
    }

}
