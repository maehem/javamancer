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
[0] :: * Strikeforce Screaming Fist *
[1] :: X. Exit System 1. Software Library 2. Operational Reports
[2] :: This system contains RESTRICTED data as defined in the Tempest Restrictions of 2025.  Unauthorized disclosure subject to administrative and criminal sanctions.  Dissemination and extraction of information from this system is controlled by the DOD and is not releasable to foreign nationals.
[3] ::  Software Library  X. Exit To Main 1. Slow 3.0 2. Depth Charge 3.0 3. Python 3.0 4. KGB 1.0 5. ArmorAll 1.0 6. Easy Rider 1.0
[4] :: To:    General Davis From:  Ossian Intercept Operational Code:  HAMMER The following report has been intercepted in transmission.  It has been routed to you since it may have an impact on the Hammer operation involving Colonel Willis Corto.  It appears to be a private summary of several reports concerning Colonel Corto.
[5] :: 11/12/58maximfargocortohammer To:       General Maxim  From:     Captain Fargo  Subject:  Narrative Historical Summary of Colonel Willis Corto, S.F., and his Involvement with Omaha Thunder General, this summary will conclude our earlier briefing.  Picking up where we left off...
[6] :: Colonel Willis Corto had plummeted through a blind spot in the Russian defenses over Kirensk. The shuttles had created the holes with pulse bombs, and Cortos team had dropped in in Nightwing microlights, their wings snapping taut in moonlight, reflected in jags of silver along the rivers Angara and Podhamennaya, the last light Corto would see for 15 months.   The microlights had been unarmed, stripped to compensate for the weight of a console operator, a prototype deck, and a virus program called Mole IX, the first true virus in the history of cybernetics. Corto and his team had been training for the run for three years. They were through the ice, ready to inject Mole IX, when the emps went off. The Russian pulse guns threw the jockeys into electronic darkness; the Nightwings suffered systems crash, flight circuitry wiped clean. Then the lasers opened up, aiming on infrared, taking out the fragile, radar-transparent assault planes, and Corto and his dead console man fell out of the Siberian sky.  Corto commandeered a Russian gunship and managed to reach Finland. To be gutted, as it landed in a spruce grove, by an antique 20 millimeter cannon manned by a cadre of reservists on dawn alert. Finnish paramedics sawed Corto out of the twisted belly of the helicopter. The war ended 9 days later, and Corto was shipped to a military facility in Utah, blind, legless, and missing most of his jaw.  As you know already, Corto was repaired, with new eyes, legs, plumbing, and extensive cosmetic work, to testify at the Congressional hearings regarding the Omaha Thunder operation. His rehearsed testimony was instrumental in saving the careers of 3 officers directly responsible for the suppression of reports on the building of the emp installations at Kirensk, which would have proved that the entire assault was designed merely as a test of the new virus. As a civilian, Corto proceeded to surface in Thailand as overseer of a heroin factory, then as enforcer for a California gambling cartel, then as a paid killer in the ruins of Bonn. He was later sent to a mental institution in Paris.
[7] :: I can fill you in on the current status of Corto, now living under the name of Armitage, at our meeting next week.
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class ScreamingFistDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public ScreamingFistDatabaseView(GameState gs, Pane p, PopupListener l) {
        super(gs, p, l);
        landingPage();
    }

    @Override
    protected final void landingPage() {
        pane.getChildren().clear();
        mode = Mode.SUB;

        Text paddingText = new Text("\n" + dbTextResource.get(2) + "\n\n\n");

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
            case "1" -> { // Downloads
                downloads();
            }
            case "2" -> { // Reports
                viewText(4, 5, 6, 7);
            }
        }
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
//            case EDIT -> {
//                if (code.equals(KeyCode.X)
//                        || code.equals(KeyCode.ESCAPE)) {
//                    LOGGER.log(Level.FINE, "Go back up menu level.");
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
