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
import com.maehem.javamancer.neuro.model.skill.LogicSkill;
import com.maehem.javamancer.neuro.model.skill.PhenomenologySkill;
import com.maehem.javamancer.neuro.model.skill.PhilosophySkill;
import com.maehem.javamancer.neuro.model.skill.PsychoanalysisSkill;
import com.maehem.javamancer.neuro.model.skill.SophistrySkill;
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
[0] :: * Turing Registry *
[1] :: X. Exit System 1. AI Registry 2. AI Reports 3. Skill Upgrade
[2] :: NOTICE TO TURING FIELD OPERATIVES: Turing Field Operatives are  encouraged to make use of the new  tutorials that have been installed on this system.  If you have had the proper skill chips implanted, these tutorials can be used to upgrade your skill and proficiency in the areas of: Logic, Phenomenology, Philosophy, Sophistry, and Psychoanalysis. You can never be too careful when dealing with an Artificial Intelligence---it pays to be prepared.
[3] ::    Artificial Intelligence Registry Code        Registration   Citizenship Neuromancer 918724597 TA   Brazil Wintermute  714555681 TA   Switz. Greystoke   111957245 NZRT Africa Chrome      927084445 YKZA Japan Sapphire    222598544 GDAY Australia Hal         900041583 NASA U.S.A. Colossus    508923754 GM   U.S.A. Xaviera     818905987 TRLP Monaco Lucifer     374893629 KGB  U.S.S.R. Sangfroid   657345405 MAAS F.R.G. Gold        230007467 PNDA China
[4] :: Monthly AI Surveillance Reports November, 2058 Recognition code: Chrome Registration NO.: 9270844465 YKZA Citizenship:      Japan Surveillance:     Michele M.  Summary:  Chrome is presently operating an on-line psychology service catering primarily to cyberspace cowboys. Its reasons for operating this service are unknown at this time. It seems to be gathering data on human neuroses as well as encouraging some of the cowboys to cease their illegal activities. There is insufficient data for speculation as to Chromes true intentions at this time.
[5] ::   Skill Upgrade  X. Exit To Main 1. Phenomenology (lvl 5) 2. Philosophy (lvl 5) 3. Sophistry (lvl 5) 4. Logic (lvl 5) 5. Psychoanalysis (lvl 4)
[6] ::
[7] ::
[8] ::
[9] :: GENERAL ALERT!  GENERAL ALERT!  Two of our specialized AI destruct programs are missing from our vault! Junior Turing operatives should be informed that these warez are to be considered extremely dangerous!  Both are part of our battle library to ensure that mainframe AIs belonging to Allard Technologies and Musabori Industries do not get out of hand! And considering how much trouble our field people are having with the AIs lately, we may have a need for these programs very soon!  Our Head Software Designer was murdered recently when he asked for a raise, so these warez cannot be duplicated! If anyone knows the whereabouts of these warez, please inform your superiors!  There will be no questions asked!
[10] :: PRIMARY ALERT!  PRIMARY ALERT!  Weve just received an urgent report from a field operative who was investigating certain AIs owned by Tessier-Ashpool S.A., which is based at the Villa Straylight on Freeside. The operative died before completing her report, but she has discovered that the AIs seem to have a certain ability to alter reality.  If true, we may soon find ourselves in deep software.  Our staff psychiatrist is currently attempting to determine if our field operatives reality was altered before she filed her report.
[11] :: X. Exit To Main 1. Monthly AI Report 2. General Alert 3. Primary Alert
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class TuringRegistryDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, SKILL
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public TuringRegistryDatabaseView(GameState gs, Pane p, PopupListener l) {
        super(gs, p, l);
        landingPage();
    }

    @Override
    protected final void landingPage() {
        pane.getChildren().clear();
        mode = Mode.SUB;

        Text helloText = new Text(dbTextResource.get(2) + "\n\n");

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
            case "1" -> { // AI Registry
                viewText(1);
            }
            case "2" -> { // AI Reports
                reports();
            }
            case "3" -> { // Skill Upgrade
                skillUpgrade();
            }
        }
    }

    private void reports() {
        LOGGER.log(Level.FINE, "Turing Registry: reports");
        pane.getChildren().clear();

        Text subHeadingText = new Text("""
                                       
                                       TODO
                                       """
                + dbTextResource.get(11)
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

    private void skillUpgrade() {
        pane.getChildren().clear();
        mode = Mode.SKILL;

        TextFlow tf = pageTextFlow(headingText);

        String menuString = dbTextResource.get(5);
        String[] split = menuString.split("\\r");
        for (String s : split) {
            Text menuItem = new Text("\n         " + s);
            tf.getChildren().add(menuItem);
            if (s.startsWith("X.")
                    || s.startsWith("1.")
                    || s.startsWith("2.")
                    || s.startsWith("3.")
                    || s.startsWith("4.")
                    || s.startsWith("5.")) {
                menuItem.setOnMouseClicked((t) -> {
                    t.consume();
                    switch (s.trim().substring(0, 1)) {
                        case "X" -> {
                            mainMenu();
                        }
                        case "1" -> {
                            attemptSkillUpgrade(new PhenomenologySkill(5));
                        }
                        case "2" -> {
                            attemptSkillUpgrade(new PhilosophySkill(5));
                        }
                        case "3" -> {
                            attemptSkillUpgrade(new SophistrySkill(5));
                        }
                        case "4" -> {
                            attemptSkillUpgrade(new LogicSkill(5));
                        }
                        case "5" -> {
                            attemptSkillUpgrade(new PsychoanalysisSkill(4));
                        }
                    }

                });
            }
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
                    keyEvent.consume();
                    return true;
                } else if (code.isDigitKey()) {
                    keyEvent.consume();
                    itemPage(code.getChar());
                    return false;
                }
            }
            case SKILL -> {
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
