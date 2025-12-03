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
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.DIGIT1;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class CheapHotelDatabaseView extends DatabaseView {

    private static final int CAVIAR_PRICE = 200;
    private static final int SAKE_PRICE = 15;

    private final Text CURSOR_TEXT = new Text("<");

    private enum Mode {
        SUB, MENU, ROOM, LOCAL, BILL, EDIT
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

//    static final Map<String, Integer> LOCAL_MAP = Map.of( // limit 10 items. :(
//            "11/16/58 Donut World", 4,
//            "11/16/58 Manyusha Wanna Massage", 5,
//            "11/16/58 Psychologist", 6,
//            "11/16/58 Crazy Edo's", 7
//    );
    private final StringBuilder typedBalance = new StringBuilder();
    private final Text typedBalanceText = new Text();

    private boolean roomServiceOk = false;

    /*
    [0] :: * The Cheap Hotel *
    [1] :: X. Exit System 1. Room Service 2. Local Things to do 3. Review Bill
    [2] :: 4. Edit Bill
    [3] :: Hey, its better than sleeping in the streets! Just enter the password "GUEST" to enter our system.
    [4] ::    We at Donut World hope to be your breakfast, lunch or dinner place. As per usual, 15% discount for all SEA agents. Open 24 hours.
    [5] :: Got them Jacked-in stiffies in your shoulders and back? Do you have a bad sector in your spine? Fret no more. Our Manyusha Wana Massage Parlor is waiting for you just around the corner. Weve got services and prices to fit any budget. Our masseuses are prettier than an Ono-Sendai Cyberspace VII and theyve got their own ideas about jacking in.... If you need to be unwound, come our way. You cant afford to miss what we can do for you. Between Shins Pawn Shop and Larrys.
    [6] :: PSYCHOLOGIST is an intensely personal analysis service for an elite clientele.  It provides a socially- acceptable outlet for private frustrations, phobias, and general concerns.  New users can sample on-going mindprobe sessions for insight into their own personal problems.  After the initial contact, new users will be assigned a personal password. You can reach us at "PSYCHO".
    [7] ::            Crazy Edos Why buy new when you can buy used for less?  Face it, you know the things work.  You dont have to rip open hundreds of boxes to sub-assemble this stuff.  Its all here. Weve got the almost latest in both warez and K-boxes.  Check us out!  Between Metro Holografix and the Matrix Restaurant.
    [8] ::      Room: 92  Name:  --------------------------------------    Total Charges               O. On account:                    Balance:                    --------------------------------------             exit  pay bill
    [9] ::
    [10] :: --------------------------------------
    [11] :: You must pay your bill first.
    [12] ::
    [13] ::    item               in stock  cost
    [14] :: 1. Karanakov Caviar             $    2. Yomiuchi brand Sake          $

     */
//    static final Map<String, int[]> MENU_MAP = Map.of(
//            "1", new int[]{11, 12, 13, 14},
//            "2", new int[]{4, 5, 6, 7},
//            "3", new int[]{8, 9, 10}
//    );
    public CheapHotelDatabaseView(GameState gs, Pane p, PopupListener l) {
        super(gs, p, l);

        CURSOR_TEXT.setOpacity(0);

        //headingText.setText(centeredText(dbTextResource.get(0)) + "\n\n");
        if (gameState.usingDeck.getMode() == DeckItem.Mode.CYBERSPACE) {
            mainMenu();
        } else {
            landingPage();
        }
    }

    @Override
    protected final void landingPage() {
        pane.getChildren().clear();
        mode = Mode.SUB;

        Text helloText = new Text(dbTextResource.get(3) + "\n\n\n");

        TextFlow tf = pageTextFlow(headingText, helloText, CONTINUE_TEXT);
        pane.getChildren().add(tf);
    }

    @Override
    protected void siteContent() { // After access is cleared.
        mainMenu();
    }

    private void mainMenu() {
        pane.getChildren().clear();
        mode = Mode.MENU;

        roomServiceOk = gameState.hotelOnAccount >= gameState.hotelCharges;

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
                    return true;
                } else if (code.isDigitKey()) {
                    itemPage(code.getChar());
                }
            }
            case ROOM -> {
                if (roomServiceOk) {
                    switch (code) {
                        case X, ESCAPE -> {
                            mainMenu();
                            return false;
                        }
                        case DIGIT1, DIGIT2 -> {
                            buyItem(String.valueOf(code));
                        }
                    }
                }
            }
            case LOCAL -> {
                switch (code) {
                    case X, ESCAPE -> {
                        mainMenu();
                        return false;
                    }
                }
            }
            case BILL -> {
                switch (code) {
                    case X, ESCAPE -> {
                        mainMenu();
                        return false;
                    }
                }
            }
            case EDIT -> {
                switch (code) {
                    case X, ESCAPE -> {
                        reviewBill(true);
                        return false;
                    }
                    case BACK_SPACE -> {
                        // erase typed thing.
                        if (typedBalance.length() > 0) {
                            typedBalance.setLength(typedBalance.length() - 1);
                        }
                        updateTypedBalance(typedBalance.toString());
                        return false;
                    }
                    case ENTER -> {
                        // seal the value
                        if (typedBalance.toString().isBlank()) {
                            gameState.hotelOnAccount = 0;
                        } else {
                            gameState.hotelOnAccount = Integer.parseInt(typedBalance.toString());
                        }
                        reviewBill(true);
                        return false;
                    }
                }
                if (code.isDigitKey()) {
                    // check len < 7
                    // add digit to typed.
                    if (typedBalance.length() < 6) {
                        typedBalance.append(code.getChar());
                        updateTypedBalance(typedBalance.toString());
                    }
                }
            }
            // else ignore key

        }
        return super.handleKeyEvent(keyEvent);
    }

    private void itemPage(String itemLetter) {
        switch (itemLetter) {
            case "X" -> {
                listener.popupExit();
            }
            case "1" -> {
                roomService();
            }
            case "2" -> {
                messages();
            }
            case "3" -> {
                reviewBill(false);
            }
            case "4" -> {
                reviewBill(true);
            }
        }
    }

    private void roomService() {
        LOGGER.log(Level.SEVERE, "Do Room Service.");
        pane.getChildren().clear();
        mode = Mode.ROOM;
        TextFlow tf = pageTextFlow(headingText);

        if (roomServiceOk) {
            tf.getChildren().add(new Text(dbTextResource.get(13)));
            // Items
            String[] split = dbTextResource.get(14).split("\\r");
            for (String s : split) {
                final String ss;
                if (s.startsWith("1")) {
                    s = s.trim().replace("$", "$" + CAVIAR_PRICE);
                    ss = s.replace("     $", gameState.hotelCaviar + "    $");
                } else if (s.startsWith("2")) {
                    s = s.trim().replace("$", "$" + SAKE_PRICE);
                    ss = s.replace("     $", gameState.hotelSake + "    $");
                } else {
                    ss = "???"; // Should never happen.
                }
                Text menuItem = new Text("\n" + ss);
                tf.getChildren().add(menuItem);
                menuItem.setOnMouseClicked((t) -> {
                    t.consume();
                    buyItem(ss.substring(0, 1));
                });
            }
        } else {
            Text t = new Text(dbTextResource.get(11));
            tf.getChildren().add(t);
        }
        pane.getChildren().add(tf);
        pane.setOnMouseClicked((t) -> {
            t.consume();
            mainMenu();
        });
    }

//    private void localThings() {
//        LOGGER.log(Level.FINE, "Do Local Things.");
//        pane.getChildren().clear();
//        mode = Mode.LOCAL;
//
//        StringBuilder sb = new StringBuilder();
//        LOCAL_MAP.forEach((heading, index) -> {
//            sb.append(heading).append("\n")
//                    .append(dbTextResource.get(index)).append("\n\n");
//        });
//
//        TextFlow pageTf = pageTextScrolledFlow(headingText, new Text(sb.toString()));
//
//        pane.getChildren().add(pageTf);
//        pane.setOnMouseClicked((t) -> {
//            t.consume();
//            mainMenu();
//        });
//    }
    private void reviewBill(boolean allowEdit) {
        LOGGER.log(Level.FINE, "Do Review Bill.");
        pane.getChildren().clear();
        mode = Mode.BILL;
        CURSOR_TEXT.setOpacity(0.0);

        TextFlow tf = pageTextFlow(headingText);
        tf.setPadding(new Insets(0, 0, 0, 40));

        String[] split = dbTextResource.get(8).split("\\r");
        for (String s : split) {
            int len = s.length();
            final String ss;
            if (s.startsWith("   Total")) {
                s = s.substring(0, len - 10);
                ss = s + String.format("%10s", gameState.hotelCharges); // Format leading space.
                addMenuItem(tf, ss);
            } else if (s.startsWith("O. On ")) {
                if (allowEdit) {
                    s = s.substring(0, len - 10);
                    ss = s;// + String.format("%10s", gameState.hotelOnAccount); // Format leading space.
                    Node item = addMenuItem(tf, ss);
                    updateTypedBalance(String.valueOf(gameState.hotelOnAccount));
                    //typedBalanceText.setText(String.format("%10s", gameState.hotelOnAccount));
                    item.setOnMouseClicked((t) -> {
                        t.consume();
                        mode = Mode.EDIT;
                        CURSOR_TEXT.setOpacity(1.0);
                        //updateTypedBalance(CURSOR_STR);
                        //typedBalanceText.setText(CURSOR_STR);
                        item.setOnMouseClicked(null);
                        typedBalanceText.setOnMouseClicked(null);
                        //editBill();
                    });
                    typedBalanceText.setOnMouseClicked((t) -> {
                        t.consume();
                        mode = Mode.EDIT;
                        CURSOR_TEXT.setOpacity(1.0);
                        //updateTypedBalance(CURSOR_STR);
                        item.setOnMouseClicked(null);
                        typedBalanceText.setOnMouseClicked(null);
                    });
                    tf.getChildren().addAll(typedBalanceText, CURSOR_TEXT);
                } else {
                    s = s.substring(3, len - 10);
                    ss = "   " + s + String.format("%10s", gameState.hotelOnAccount); // Format leading space.
                    addMenuItem(tf, ss);
                }
            } else if (s.startsWith("   Bal")) {
                s = s.substring(0, len - 10);
                ss = s + String.format("%10s", (gameState.hotelCharges - gameState.hotelOnAccount)); // Format leading space.
                addMenuItem(tf, ss);
            } else if (s.startsWith("     Room")) {
                s = s.replace("\1", gameState.name);
                ss = s; // Format leading space.
                addMenuItem(tf, ss);
            } else if (s.contains("exit")) {
                ss = "";
                addMenuItem(tf, ss);
            } else if (s.startsWith("---")) {
                ss = s; // Format leading space.
                addMenuItem(tf, ss);
            } else {
                ss = "???"; // Should never happen.
                addMenuItem(tf, ss);
            }

        }
        Text exitText = new Text("exit");
        Text payText = new Text("pay bill");
        tf.getChildren().addAll(new Text("          "),
                exitText, new Text("    "), payText
        );
        exitText.setOnMouseClicked((t) -> {
            t.consume();
            mainMenu();
        });
        payText.setOnMouseClicked((t) -> {
            t.consume();
            attemptPayment();
            reviewBill(allowEdit); // Reload menu
        });
        pane.getChildren().add(tf);
        pane.setOnMouseClicked(null);
    }

    private Node addMenuItem(TextFlow menu, String itemStr) {
        Text text = new Text("\n" + itemStr);
        menu.getChildren().add(text);

        return text;
    }

    private void updateTypedBalance(String str) {
        typedBalanceText.setText(String.format("%10s", str));
    }

    private void editBill() {
        if (gameState.usingDeck.getMode() != DeckItem.Mode.CYBERSPACE) {
            // only in cyberspace
            return;
        }
        LOGGER.log(Level.SEVERE, "Do Edit Bill.");
        pane.getChildren().clear();
        mode = Mode.EDIT;
        TextFlow tf = pageTextFlow(headingText);

        pane.getChildren().add(tf);
        pane.setOnMouseClicked((t) -> {
            t.consume();
            mainMenu();
        });
    }

    private void buyItem(String itemLetter) {
        switch (itemLetter) {
            case "1" -> { // Caviar
                if (gameState.hotelCaviar > 0) {
                    LOGGER.log(Level.FINE, "Buy Item: Caviar");
                    gameState.hotelCharges += CAVIAR_PRICE;
                    gameState.hotelCaviar--;
                    gameState.hotelDeliverCaviar++; // visit real cheap hotel to get items into inventory.
                    roomService();
                }
            }
            case "2" -> { // Sake
                if (gameState.hotelSake > 0) {
                    LOGGER.log(Level.FINE, "Buy Item: Sake");
                    gameState.hotelCharges += SAKE_PRICE;
                    gameState.hotelSake--;
                    gameState.hotelDeliverSake++; // visit real cheap hotel to get items into inventory.
                    roomService();
                }
            }
        }
    }

    private void attemptPayment() {
        LOGGER.log(Level.FINE, "Player attempts to pay hotel bill...");
        int available = gameState.chipBalance;
        int hotelBalance = gameState.hotelCharges - gameState.hotelOnAccount;

        if (hotelBalance == 0) {
            LOGGER.log(Level.FINE, "Zero hotel balance. Nothing to pay.");
            return;
        }
        if (available < hotelBalance) {
            LOGGER.log(Level.FINE, "Not enough credits to pay hotel bill!");
            // unable to pay
            // Play "bad" sound.
        } else {
            LOGGER.log(Level.FINE, "Hotel bill paid.");
            gameState.chipBalance -= hotelBalance;
            gameState.hotelOnAccount += hotelBalance;
            // Play "good" sound.
        }

    }
}
