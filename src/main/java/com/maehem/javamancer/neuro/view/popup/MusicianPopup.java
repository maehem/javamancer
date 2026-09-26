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
package com.maehem.javamancer.neuro.view.popup;

import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.skill.MusicianshipSkill;
import com.maehem.javamancer.neuro.model.skill.Skill;
import com.maehem.javamancer.neuro.view.MusicManager;
import com.maehem.javamancer.neuro.view.PopupListener;
import com.maehem.javamancer.neuro.view.room.RoomMusic;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.X;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Disk Operations Popup Load, Save Pause and Quit functions.
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class MusicianPopup extends SmallPopupPane {

    //private final PopupListener listener;
    private enum Mode {
        CHOOSE, RESPONSE
    }

    private static final String DUB_STR = "Dub";
    private static final String JAZZ_STR = "Jazz";
    private static final String NEWAV_STR = "New Wave";
    private static final String CLASSIC_STR = "Classical";

    private static final Text HEADING_TXT = new Text("       Musicianship");
    private static final Text MENU_X_TXT = new Text("X. Exit Skill Chip");
    private static final Text MENU_1_TXT = new Text("1. Play " + DUB_STR);
    private static final Text MENU_2_TXT = new Text("2. Play " + JAZZ_STR);
    private static final Text MENU_3_TXT = new Text("3. Play " + NEWAV_STR);
    private static final Text MENU_4_TXT = new Text("4. Play " + CLASSIC_STR);

    private static final Text RESPONSE_TXT = new Text("Playing ");

    private Mode mode = Mode.CHOOSE;
    Text typedText = new Text("");
    Text responseText = new Text("RESPONSE");
    Text cursorText = new Text("<");

    public MusicianPopup(PopupListener l, GameState gs) {
        super(l, gs, 380, 128, 100, 240);
        mainMenu();
    }

    /*  TODO: Implement "use item"
    
    use_musicianship() {
	neuro_menu_flush();
	neuro_menu_flush_items();

	skills_draw_item_desc(0x52, 0, 0, 6, 0);
	neuro_menu_draw_text("X. Exit Skill Chip", 3, 1);
	neuro_menu_draw_text("1. Play Dub", 3, 2);
	neuro_menu_draw_text("2. Play Jazz", 3, 3);
	neuro_menu_draw_text("3. Play New Wave", 3, 4);
	neuro_menu_draw_text("4. Play Classical", 3, 5);

    
     */
    private void mainMenu() {
        getChildren().clear();
        mode = Mode.CHOOSE;

        TextFlow tf = new TextFlow(
                //HEADING_TXT,
               // new Text("\n\n       "),
                MENU_X_TXT,
                new Text("\n"),
                MENU_1_TXT,
                new Text("\n"),
                MENU_2_TXT,
                new Text("\n"),
                MENU_3_TXT,
                new Text("\n"),
                MENU_4_TXT,
                new Text("\n")
        );
        tf.setLineSpacing(LINE_SPACING);
        addBox(HEADING_TXT, tf);

        MENU_X_TXT.setOnMouseClicked((t) -> {
            LOGGER.log(Level.FINE, "Musicianship Popup Exit clicked.");
            listener.popupExit();
        });
        MENU_1_TXT.setOnMouseClicked((t) -> {
            LOGGER.log(Level.FINE, "Musicianship Popup User selected 1");
            setMusic(1);
        });
        MENU_2_TXT.setOnMouseClicked((t) -> {
            LOGGER.log(Level.FINE, "Musicianship Popup User selected 2");
            setMusic(2);
        });
        MENU_3_TXT.setOnMouseClicked((t) -> {
            LOGGER.log(Level.FINE, "Musicianship Popup User selected 3");
            setMusic(3);
        });
        MENU_4_TXT.setOnMouseClicked((t) -> {
            LOGGER.log(Level.FINE, "Musicianship Popup User selected 4");
            setMusic(4);
        });

    }

    private void showResponse() {
        getChildren().clear();
        mode = Mode.RESPONSE;

        TextFlow tf = new TextFlow(
                HEADING_TXT,
                new Text("\n\n       "),
                RESPONSE_TXT, responseText, // Ex. Playing Jazz
                new Text("\n\n"),
                MENU_X_TXT
        );
        tf.setLineSpacing(LINE_SPACING);

        addBox(HEADING_TXT, tf);
        MENU_X_TXT.setOnMouseClicked((t) -> {
            LOGGER.log(Level.FINE, "Musicianship Popup Exit clicked.");
            listener.popupExit();
        });


    }

    private void setMusic(int choice) {
        LOGGER.log(Level.FINE, "Set Music.");
        MusicManager mm = gameState.resourceManager.musicManager;
        
        // Fade out any playing music.
        mm.fadeAll(3000);
        
        Skill activeSkill = gameState.activeSkill;
        if (activeSkill instanceof MusicianshipSkill cs) {
            switch (choice) {
                case 1 -> {
                    cs.setMode(MusicianshipSkill.Mode.DUB);
                    mm.playTrack(RoomMusic.DUB);
                    
                    responseText.setText("Dub");
                }
                case 2 -> {
                    cs.setMode(MusicianshipSkill.Mode.JAZZ);
                    mm.playTrack(RoomMusic.JAZZ);
                    responseText.setText("Jazz");
                }
                case 3 -> {
                    cs.setMode(MusicianshipSkill.Mode.NEW_WAVE);
                    mm.playTrack(RoomMusic.NEW_WAVE);
                    responseText.setText("New Wave");
                }
                case 4 -> {
                    cs.setMode(MusicianshipSkill.Mode.CLASSICAL);
                    mm.playTrack(RoomMusic.CLASSICAL);
                    responseText.setText("Classical");
                }
            }
        } else {
            LOGGER.log(Level.SEVERE, "Musicianship popup visible when not active Skill!");
            responseText.setText("***ERROR***");
        }
        showResponse();

    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();

        switch (mode) {
            case CHOOSE -> {
                switch (code) {
                    case X, ESCAPE -> {
                        return super.handleKeyEvent(keyEvent);
                    }
                    case DIGIT1, DIGIT2, DIGIT3, DIGIT4 -> {
                        setMusic(Integer.parseInt(code.getName()));
                    }
                }
            }
            case RESPONSE -> {
                switch (code) {
                    case X, ESCAPE -> {
                        return super.handleKeyEvent(keyEvent);
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void cleanup() {
    }
}
