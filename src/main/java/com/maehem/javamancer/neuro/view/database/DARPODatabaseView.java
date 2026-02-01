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
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/*
 * <pre>
[0] :: * Defense Advanced Research Projects *
[1] :: X. Exit System 1. Current Research Topics 2. Software Library
[2] :: This system contains RESTRICTED data as defined in the Tempest Restrictions of 2025.  Unauthorized disclosure subject to administrative and criminal sanctions.  Dissemination and extraction of information from this system is controlled by DARPO and is not releasable to foreign nationals.
[3] ::  Software Library  X. Exit To Main 1. Thunderhead 3.0 2. Injector 3.0 3. Jammies 2.0 4. Concrete 2.0 5. Drill 3.0
[4] ::  Research Topics  X. Exit To Main  1. Hypersonic Weapon 2. Photon Echo Memories 3. Kinetic Energy Weapons 4. Monoclonal Antibodies 5. Laser Techniques
[5] :: DARPO 44-15.8 Transfer alignment techniques for hypersonic weapon applications  Description:  For hypervelocity aircraft, the alignment transfer from the aircrafts internal navigation systems (INS) to the weapons INS differ in the excitation requirements levied upon the aircraft to create weapon internal navigation unit (INU) measurement observability. Hypersonic endoatmospheric flight (Mach 5-20) addresses issues which are not necessarily key drivers for supersonic weapons initialization.  This on-going research is investigating current hypersonic weapon delivery systems and INU alignment requirements.
[6] :: DARPO  21-99.2 Photon echo memories  Description:  The quantum mechanical interaction of laser light with electronic states in certain cryogenically cooled crystals can lead to the phenomenon of the photon echo, in which re-emission of the photon occurs after a known delay time. Theory indicates that the delay can be long (compatible with RAM refresh modes in matrix simulators). Computation indicates high-order storage capacity and access times in the sub-picosecond range.  Applications involve high performance matrix simulator imaging systems and possible upgrades to existing Intrusion Countermeasure Electronics systems.
[7] :: DARPO 44-12 Kinetic energy weapons  Description:  Development is proceeding along the lines of DARPO 16-54.7.  The systems being modified for higher performance include electromagnetic rail guns, plasma guns, and other hypervelocity projectile weapons, as well as chemically-propelled interceptors. Included in the kinetic energy weapons area are smart projectiles, homing devices, launcher design, engagement tactics, hypervelocity aerocontrol/thermal protection, and countermeasures.
[8] :: DARPO 210-21 Monoclonal antibodies and hybridization probes in genetically engineered weapon systems  This summary undergoing modification.
[9] :: DARPO 58-29 Frequency-scanning laser techniques for active detection and discrimination of cyberspace threats  This summary undergoing modification.
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class DARPODatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, RESEARCH
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public DARPODatabaseView(GameState gs, Pane p, PopupListener l) {
        super(gs, p, l);
        landingPage();
    }

    @Override
    protected final void landingPage() {
        pane.getChildren().clear();
        mode = Mode.SUB;

        Text helloText = new Text(dbTextResource.get(2) + "\n\n\n\n");

        TextFlow tf = pageTextFlow(headingText, helloText, CONTINUE_TEXT);
        pane.getChildren().add(tf);
    }

    @Override
    protected final void siteContent() {
        switch (mode) {
            case MENU ->
                mainMenu();
            case RESEARCH ->
                research();
            default -> {
                mainMenu();
            }
        }
    }

    private void mainMenu() {
        pane.getChildren().clear();
        mode = Mode.MENU;
        subMode = SubMode.MAIN;

        TextFlow tf = pageTextFlow(headingText);

        String menuString = dbTextResource.get(1);
        String[] split = menuString.split("\\r");
        for (String s : split) {
            Text menuItem = new Text("\n         " + s);
            tf.getChildren().add(menuItem);
            menuItem.setOnMouseClicked((t) -> {
                t.consume();
                mainMenuClicked(s.trim().substring(0, 1));
            });
        }

        pane.getChildren().add(tf);
        pane.setOnMouseClicked(null);
    }

    private void mainMenuClicked(String itemLetter) {
        switch (itemLetter) {
            case "X" -> {
                listener.popupExit();
            }
            case "1" -> { // Research
                research();
            }
            case "2" -> { // Downloads
                downloads();
            }
        }
    }

    private void research() {
        pane.getChildren().clear();
        mode = Mode.RESEARCH;
        subMode = SubMode.MAIN;

        TextFlow tf = pageTextFlow(headingText);

        String menuString = dbTextResource.get(4);
        String[] split = menuString.split("\\r");
        for (String s : split) {
            Text menuItem = new Text("\n         " + s);
            tf.getChildren().add(menuItem);
            menuItem.setOnMouseClicked((t) -> {
                t.consume();
                researchClicked(s.trim().substring(0, 1));
            });
        }

        pane.getChildren().add(tf);
        pane.setOnMouseClicked(null);
    }

    private void researchClicked(String itemLetter) {
        switch (itemLetter) {
            case "X" -> {
                mainMenu();
            }
            case "1" -> {
                viewText(5);
            }
            case "2" -> {
                viewText(6);
            }
            case "3" -> {
                viewText(7);
            }
            case "4" -> {
                viewText(8);
            }
            case "5" -> {
                viewText(9);
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
                    LOGGER.log(Level.INFO, "Menu wants to exit system.");
                    keyEvent.consume();
                    return true;
                } else if (code.isDigitKey()) {
                    keyEvent.consume();
                    mainMenuClicked(code.getChar());
                    return false;
                }
            }
            case RESEARCH -> {
                if (code.equals(KeyCode.X)
                        || code.equals(KeyCode.ESCAPE)) {
                    LOGGER.log(Level.FINE, "Go back up menu level.");
                    if (subMode == SubMode.VIEW_TEXT) {
                        research();
                    } else {
                        mainMenu();
                    }
                    keyEvent.consume();
                    return false;
                }
                if (code.equals(KeyCode.DIGIT1)
                        || code.equals(KeyCode.DIGIT2)
                        || code.equals(KeyCode.DIGIT3)
                        || code.equals(KeyCode.DIGIT4)
                        || code.equals(KeyCode.DIGIT5)) {
                    viewText(code.getCode() - 48 + 4); // DIGIT0 == 48
                    keyEvent.consume();

                    return false;
                }
            }
            // else ignore key

        }
        return super.handleKeyEvent(keyEvent);
    }
}
