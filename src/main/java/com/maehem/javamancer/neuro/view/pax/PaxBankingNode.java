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
package com.maehem.javamancer.neuro.view.pax;

import com.maehem.javamancer.neuro.model.BankTransaction;
import com.maehem.javamancer.neuro.model.GameState;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class PaxBankingNode extends PaxNode {

    private enum Mode {
        MAIN, UPLOAD, DOWNLOAD, TRANSACTIONS
    }

    private Mode mode = Mode.MAIN;

    private final String enterPrefix = "Enter amount:";
    private final String enterCursor = "<";
    private final Text enterCode = new Text();
    private final Text insufficientFunds = new Text("Insufficient Funds!");
    private final StringBuilder enteredNumber = new StringBuilder();
    private final Text titleItem = new Text("   First Orbital Bank of Switzerland");

    public PaxBankingNode(PaxNodeListener l, GameState gameState) {
        super(l, gameState);

        insufficientFunds.setVisible(false);

        mainMenu();
    }

    private void mainMenu() {

        getChildren().clear();
        mode = Mode.MAIN;
        Text exitItem = new Text("X. Exit to Main\n");
        Text downItem = new Text("D. Download credits.\n");
        Text upItem = new Text("U. Upload credits\n");
        Text transItem = new Text("T. Transaction Record");
        TextFlow menuItems = new TextFlow(
                exitItem,
                downItem,
                upItem,
                transItem
        );
        menuItems.setLineSpacing(LINE_SPACING);

        VBox box = addBox(titleItem,
                infoBox2(),
                menuItems);
        box.setSpacing(4);


        exitItem.setOnMouseClicked((t) -> {
            listener.paxNodeExit();
        });
        downItem.setOnMouseClicked((t) -> {
            downloadCredit();
        });
        upItem.setOnMouseClicked((t) -> {
            uploadCredit();
        });
        transItem.setOnMouseClicked((t) -> {
            transactionHistory();
        });
    }

    private Node infoBox2() {

        Text row1 = new Text(
                "   "
                + String.format("%-20s", "name = " + gameState.name)
                + "id = " + gameState.PLAYER_BAMA + "\n"
        );
        Text row2 = new Text(
                "   "
                + String.format("%-15s", "chip = " + gameState.chipBalance)
                + "account = " + gameState.bankBalance
        );

        TextFlow tf = new TextFlow(row1, row2);
        tf.setLineSpacing(LINE_SPACING);

        return tf;
    }

    @Override
    public boolean handleEvent(KeyEvent ke) {
        switch (mode) {
            case MAIN -> {
                if (ke.getCode().isLetterKey()) {
                    switch (ke.getCode()) {
                        case X, ESCAPE -> {
                            return true;
                        }
                        case D -> {
                            LOGGER.log(Level.CONFIG, "Main Mode: Download selected.");
                            downloadCredit();
                        }
                        case U -> {
                            LOGGER.log(Level.CONFIG, "Main Mode: Upload selected.");
                            uploadCredit();
                        }
                        case T -> {
                            LOGGER.log(Level.CONFIG, "Main Mode: Transaction History selected.");
                            transactionHistory();
                        }
                    }
                }

            }
            case DOWNLOAD -> {
                if (ke.getCode().isLetterKey()) {
                    switch (ke.getCode()) {
                        case X, ESCAPE -> {
                            mainMenu();
                            return false;
                        }
                    }
                }

                handleEnteredNumber(ke);

                return false;
            }
            case UPLOAD -> {
                if (ke.getCode().isLetterKey()) {
                    switch (ke.getCode()) {
                        case X, ESCAPE -> {
                            mainMenu();
                            return false;
                        }
                    }
                }

                handleEnteredNumber(ke);

                return false;
            }
            case TRANSACTIONS -> {
                if (ke.getCode().isLetterKey()) {
                    switch (ke.getCode()) {
                        case X, ESCAPE -> {
                            mainMenu();
                            return false;
                        }
                    }
                }

            }
        }
        return false;
    }

    private void handleEnteredNumber(KeyEvent ke) {
        if (enteredNumber.length() < 12 && ke.getCode().isDigitKey()) {
            LOGGER.log(Level.FINEST, "Typed: {0}", ke.getText());
            enteredNumber.append(ke.getText());
            enterCode.setText(enterPrefix + enteredNumber.toString() + enterCursor);
            checkAmount(Integer.parseInt(enteredNumber.toString()));
        } else if (enteredNumber.length() > 0 && ke.getCode().equals(KeyCode.BACK_SPACE)) {
            LOGGER.log(Level.FINEST, "Backspace.");
            enteredNumber.delete(enteredNumber.length() - 1, enteredNumber.length());
            enterCode.setText(enterPrefix + enteredNumber.toString() + enterCursor);
            if (enteredNumber.isEmpty()) {
                checkAmount(0);
            } else {
                checkAmount(Integer.parseInt(enteredNumber.toString()));
            }
        } else if (ke.getCode().equals(KeyCode.ENTER)) {
            int value = Integer.parseInt(enteredNumber.toString());
            if (moveMoney(value)) {
                mainMenu();
            } else {
                LOGGER.log(Level.INFO, "Insufficient Funds!");
            }
        }
    }

    private void downloadCredit() {
        getChildren().clear();
        mode = Mode.DOWNLOAD;

        enterCode.setText(enterPrefix + enterCursor);
        VBox box = addBox(titleItem,
                infoBox2(),
                enterCode,
                insufficientFunds);
        box.setSpacing(4);

    }

    private void uploadCredit() {
        getChildren().clear();
        mode = Mode.UPLOAD;

        enterCode.setText(enterPrefix + enterCursor);
        VBox box = addBox(titleItem,
                infoBox2(),
                enterCode,
                insufficientFunds);
        box.setSpacing(4);
    }

    /**
     * move amount between chip and bank. Negative amount moves credits from
     * chip into bank. Positive amount moves credits from bank to chip.
     *
     * @param amount
     * @return
     */
    private boolean checkAmount(int amount) {
        switch (mode) {
            case UPLOAD -> {
                LOGGER.log(Level.FINER, "Upload: move from chip.");
                // Move to bank
                if (gameState.chipBalance < amount) {
                    // can't do it.
                    LOGGER.log(Level.INFO, "Not enough money on chip!");
                    insufficientFunds.setVisible(true);
                    return false;
                } else {
                    insufficientFunds.setVisible(false);
                }
            }
            case DOWNLOAD -> {
                LOGGER.log(Level.FINER, "Download: move from bank.");
                // Move to chip
                if (gameState.bankBalance < amount) {
                    // can't do it.
                    LOGGER.log(Level.INFO, "Not enough money in bank!");
                    insufficientFunds.setVisible(true);
                    return false;
                } else {
                    insufficientFunds.setVisible(false);
                }
            }
        }
        return true;
    }

    private boolean moveMoney(int amount) {
        if (checkAmount(amount)) {
            switch (mode) {
                case DOWNLOAD -> {
                    gameState.chipBalance += amount;
                    gameState.bankBalance -= amount;
                    enteredNumber.setLength(0);
                }
                case UPLOAD -> {
                    gameState.chipBalance -= amount;
                    gameState.bankBalance += amount;
                    enteredNumber.setLength(0);
                }
            }
        }

        return true;
    }

    private void transactionHistory() {
        getChildren().clear();
        mode = Mode.TRANSACTIONS;
        Text header = new Text("    day      type            amount");
        VBox box = addBox(titleItem,
                infoBox2(),
                header,
                transactionList());
        box.setSpacing(4);

        box.setOnMouseClicked((t) -> {
            mainMenu();
        });
    }

    private Node transactionList() {
        TextFlow tf = new TextFlow();
        ArrayList<BankTransaction> list = gameState.bankTransactionRecord;
        List<BankTransaction> subList = list.subList(list.size() - 4, list.size());
        subList.forEach((bt) -> {
            tf.getChildren().add(new Text(bt.toString() + "\n"));
        });

        tf.setLineSpacing(LINE_SPACING);
        ScrollPane sp = new ScrollPane(tf);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setPrefSize(440, 94);
        sp.setPadding(new Insets(0, 0, 0, 40));

        return sp;
    }
}
