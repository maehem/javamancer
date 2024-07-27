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

import com.maehem.javamancer.neuro.model.GameState;
import java.util.logging.Level;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;

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

    public PaxBankingNode(PaxNodeListener l, GameState gameState) {
        super(l, gameState);

        insufficientFunds.setVisible(false);

        getChildren().add(mainMenu());
    }

    private Node mainMenu() {

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
        menuItems.setLineSpacing(-8);

        VBox box = new VBox(
                titleItem(),
                infoBox(),
                menuItems
        );
        box.setSpacing(10);
        box.setPadding(new Insets(0, 0, 0, 20));
        box.getTransforms().add(new Scale(1.3, 1.0));
        box.setLayoutX(30);
        box.setLayoutY(20);

        return box;
    }

    private Node titleItem() {
        Text titleItem = new Text("     First Orbital Bank of Switzerland");
        titleItem.setLayoutX(40);

        return titleItem;
    }

    private Node infoBox() {
        GridPane infoGrid = new GridPane(2, 2);

        Text nameItem = new Text("name: " + gameState.name);
        Text idItem = new Text("id = " + gameState.bamaId);
        Text chipItem = new Text("chip = " + gameState.chipBalance);
        Text bankItem = new Text("account = " + gameState.bankBalance);
        infoGrid.addRow(0, nameItem, idItem);
        infoGrid.addRow(1, chipItem, bankItem);
        infoGrid.getColumnConstraints().add(new ColumnConstraints(180));
        infoGrid.setHgap(10);
        infoGrid.setVgap(0);
        infoGrid.setPadding(new Insets(0));
        Region infoSpacer = new Region();
        infoSpacer.setPrefSize(40, 20);
        HBox infoBox = new HBox(infoSpacer, infoGrid);
        infoBox.setSpacing(0);

        return infoBox;
    }

    @Override
    public boolean handleEvent(KeyEvent ke) {
        switch (mode) {
            case MAIN -> {
                if (ke.getCode().isLetterKey()) {
                    switch (ke.getCode()) {
                        case X -> {
                            return true;
                        }
                        case D -> {
                            LOGGER.log(Level.CONFIG, "Main Mode: Download selected.");
                            getChildren().clear();
                            mode = Mode.DOWNLOAD;
                            getChildren().add(downloadCredit());
                        }
                        case U -> {
                            LOGGER.log(Level.CONFIG, "Main Mode: Upload selected.");
                            getChildren().clear();
                            mode = Mode.UPLOAD;
                            getChildren().add(uploadCredit());
                        }
                        case T -> {
                            LOGGER.log(Level.CONFIG, "Main Mode: Transaction History selected.");
                            getChildren().clear();
                            mode = Mode.TRANSACTIONS;
                            getChildren().add(transactionHistory());
                        }
                    }
                }

            }
            case DOWNLOAD -> {
                if (ke.getCode().isLetterKey()) {
                    switch (ke.getCode()) {
                        case X -> {
                            getChildren().clear();
                            mode = Mode.MAIN;
                            getChildren().add(mainMenu());
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
                        case X -> {
                            getChildren().clear();
                            mode = Mode.MAIN;
                            getChildren().add(mainMenu());
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
                        case X -> {
                            getChildren().clear();
                            mode = Mode.MAIN;
                            getChildren().add(mainMenu());
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
                getChildren().clear();
                mode = Mode.MAIN;
                getChildren().add(mainMenu());
            } else {
                LOGGER.log(Level.INFO, "Insufficient Funds!");
            }
        }
    }

    private Node downloadCredit() {
        //VBox box = new VBox(new Text("Download Credits"));
        enterCode.setText(enterPrefix + enterCursor);
        VBox box = new VBox(
                titleItem(),
                infoBox(),
                enterCode,
                insufficientFunds
        );
        box.setSpacing(10);
        box.setPadding(new Insets(0, 0, 0, 20));
        box.getTransforms().add(new Scale(1.3, 1.0));
        box.setLayoutX(30);
        box.setLayoutY(20);

        return box;
    }

    private Node uploadCredit() {
        //VBox box = new VBox(new Text("Upload Credits"));
        enterCode.setText(enterPrefix + enterCursor);
        VBox box = new VBox(
                titleItem(),
                infoBox(),
                enterCode,
                insufficientFunds
        );
        box.setSpacing(10);
        box.setPadding(new Insets(0, 0, 0, 20));
        box.getTransforms().add(new Scale(1.3, 1.0));
        box.setLayoutX(30);
        box.setLayoutY(20);

        return box;
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
            switch ( mode ) {
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

    private Node transactionHistory() {
        Text header = new Text("    day       type         amount");
        VBox box = new VBox(
                titleItem(),
                infoBox(),
                header,
                transactionList()
        );
        box.setSpacing(0);
        //box.setPadding(new Insets(0, 0, 0, 20));
        box.getTransforms().add(new Scale(1.3, 1.0));
        //box.setLayoutX(30);
        //box.setLayoutY(20);

        return box;
    }

    private Node transactionList() {
        TextFlow list = new TextFlow();
        gameState.bankTransactionRecord.forEach((bt) -> {
            list.getChildren().add(new Text(bt.toString() + "\n"));
        });

//        Text t1 = new Text(
//                "11/16/58  Download          6\n"
//                + "11/16/58  Download         66\n"
//                + "11/16/58  Fined           100\n"
//                + "11/17/58  Download       1000\n"
//                + "11/17/58  Download         53\n"
//                + "11/18/58  Upload          433\n"
//                + "11/18/58  Download       5444\n"
//                + "11/18/58  Download  221000000"
//        );
        list.setLineSpacing(-9);
        ScrollPane sp = new ScrollPane(list);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setPrefSize(440, 94);
        sp.setPadding(new Insets(0, 0, 0, 40));

        return sp;
    }
}
