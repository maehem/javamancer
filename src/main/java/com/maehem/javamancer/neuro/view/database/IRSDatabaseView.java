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
import com.maehem.javamancer.neuro.model.TextResource;
import com.maehem.javamancer.neuro.view.PopupListener;
import java.util.logging.Level;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * <pre>
 * [0] :: * Internal Revenue Service *
 * [1] :: X. Exit System 1. TaxInfo Board
 * [2] :: 2. Supervisors Notice 3. Special Audit Report 4. View Audit List
 * [3] :: 5. Software Library
 * [4] :: IRS Field Supervisors:  Field Supervisors should warn IRS Field Examiners under their command that use of the garrote, the Rack, and the Iron Maiden by Examiners is unwarranted under most Taxpayer Compliance Program cases.  The rubber hose and the insertion of bamboo under the fingernails should be sufficient for Taxpayer compliance in most situations where motivation is required.  Use of stronger forms of interrogation by Field Examiners is frowned upon, unless a Field Supervisor qualified in Interrogation is present, or the Field Examiner is performing a Special Field Audit.
 * [5] :: Due to the high number of Taxpayer requests for current information, possibly due to the Publics inability to remain current with the hourly changes in the Tax Code, the IRS Administration has approved the activation of a question-and-answer bulletin board service on this system. The real purpose of this service is locate and arrest tax offenders who have otherwise managed to be overlooked by our standard procedures. So far, results have been excellent. In two weeks of operation, over four thousand tax offenders have been arrested and sentenced.
 * [6] ::  Software Library  X. Exit To Main 1. Jammies 1.0 2. Hammer 2.0 3. Mimic 1.0
 * [7] :: Date: 11/16/58 From: L. Zone BAMA ID: 1404726431  I had twenty million credits worth of income this year in my black market  pituitary extract operation.  This is my first year in business.  Which forms should I use to report this income?  Can my business startup expenses be deducted?
 * [8] :: Date: 11/16/58 To:   L. Zone  Your business is an illegal operation. We have identified you to the proper law enforcement agencies.  Pending further investigation, your total income for the year has been turned over to us.
 * [9] :: Date: 11/16/58 From: Rafaella Hammer BAMA ID: 2776081129  Due to an oversight on the part of my accountant, I failed to report all of my income for the last year.  What form should I file to correct this oversight and how much of a penalty will I have to pay?
 * [10] :: Date: 11/16/58 To:   Rafaella Hammer  We have given your case careful consideration and have decided that there will be no penalty incurred on the income you failed to report last year.  However, you and your tax accountant are going to jail.
 * [11] ::     Field Audit List
 * [12] :: No report at this time.
 * [13] :: Name:  BAMA ID: 0563061187  Summary: The accused tax offender listed here is scheduled for a Special Field Audit as soon as our IRS Special Forces unit or one of the other police agencies is able to locate the accused. The tax offender named above has mysteriously acquired a lump sum, either illegally obtained or received as some sort of payment for services. Proper forms have not been filed to explain this unusually large sum. Investigation has revealed that one of the orbital Swiss banks has recently "lost" certain cash reserves which are similar in total value to the lump sum acquired by the accused tax offender. Following standard field audit procedure, the field examiner for this case will be allowed to carry large-caliber hand guns as well as chemical and biological weapons.  The accused is to be considered armed and dangerous, so authorization is hereby given to terminate the accused at the slightest provocation or untoward movement.  If necessary for field audit purposes, the field examiner may call in an IRS Special Forces unit.
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class IRSDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, AUDIT
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public IRSDatabaseView(GameState gs, Pane p, PopupListener l) {
        super(gs, p, l);
        landingPage();
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
        if (accessLevel > 1) {
            menuString += "\r" + dbTextResource.get(2);
        }
        if (accessLevel > 2) {
            menuString += "\r" + dbTextResource.get(3);
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
            case "1" -> { // About system
                messages();
            }
            case "2" -> { // About tournaments
                if (accessLevel > 1) {
                    viewText(4);
                }
            }
            case "3" -> {
                if (accessLevel > 1) {
                    if (gameState.bankZurichRobbed) {
                        viewText(13);
                    } else {
                        viewText(12);
                    }
                }
            }
            case "4" -> {
                if (accessLevel > 1) {
                    auditList();
                    // TODO:
                }
            }
            case "5" -> {
                if (accessLevel > 2) {
                    downloads();
                }
            }
        }
    }

    private void auditList() {
        LOGGER.log(Level.FINE, "IRS: audit list");
        pane.getChildren().clear();
        mode = Mode.AUDIT;

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(dbTextResource.get(11));

        Text subHeadingText = new Text(sb.toString() + "\n\n");
        //subHeadingText.setLineSpacing(LINE_SPACING);

        TextFlow contentTf = simpleTextFlow(subHeadingText);
        contentTf.setPadding(new Insets(0, 0, 0, 30));

        TextResource bamaList = gameState.resourceManager.getBihResource("IRS0");
        bamaList.forEach((item) -> {
            Text t;
            if (item.startsWith("\1")) {
                t = new Text(
                        String.format("%-19s", gameState.name)
                        + item.substring(19)
                );
            } else {
                t = new Text("\n" + item);
            }

            // TODO: Link item for editing BAMA ID???
            contentTf.getChildren().add(t);
        });

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
                    LOGGER.log(Level.INFO, "Menu wants to exit system.");
                    keyEvent.consume();
                    return true;
                } else if (code.isDigitKey()) {
                    keyEvent.consume();
                    itemPage(code.getChar());
                    return false;
                }
            }
            case AUDIT -> {
                if (code.equals(KeyCode.X)
                        || code.equals(KeyCode.ESCAPE)) {
                    LOGGER.log(Level.FINE, "Go back up menu level.");
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
