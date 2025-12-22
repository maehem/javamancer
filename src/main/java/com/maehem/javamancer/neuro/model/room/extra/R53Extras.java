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
package com.maehem.javamancer.neuro.model.room.extra;

import com.maehem.javamancer.neuro.model.BodyPart;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.JackZone;
import com.maehem.javamancer.neuro.model.room.DialogCommand;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DECK_WAIT;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DIALOG_CLOSE;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.EXIT_B;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LONG_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LUNGS;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.NPC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SHORT_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.TO_JAIL;
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import java.util.logging.Level;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R53Extras extends RoomExtras { // Hitachi

    boolean deckJustUsed = false;

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC.num}, {SHORT_DESC.num}, //  [0][1]
        {3, 4, 5, 6}, // [2] :: Hello!  Im the lab technician. You must be a volunteer for the lung experiment?
        {7}, // [3] :: Er...yes.  I suppose so....
        {8}, // [4] :: Im so embarrassed. I just stumbled inhere by mistake.  Excuse me.
        {9}, // [5] :: Actually, Im just here to steal some time on your cyberspace jack. I know its illegal, but what the hell....
        {10}, // [6] :: Actually, Im just here to see you. Ive heard youre the kind of woman who likes to have fun....
        {DIALOG_CLOSE.num, DECK_WAIT.num, 11}, // [7] :: Great!  We're currently paying our volunteers $3000 apiece.  Wait here and Ill be back in a few minutes.
        {EXIT_B.num}, // [8] :: You should be embarrassed, you fool! Get out of here!
        {TO_JAIL.num}, // [9] :: Youre right. It is illegal. Have a nice trip to the Justice Booth.
        {NPC.num, 15}, // [10] :: Ive heard youre the kind of turnip- head who likes getting arrested.
        {NPC.num, 12}, // [11] :: Thanks for waiting. This wont hurt a bit. Well, maybe a little bit, but you wont feel anything after the
        {NPC.num, 13}, // [12] :: anesthetic takes effect. At least, not while youre asleep. After youre awake, of course, thats another
        {DESC.num, NPC.num, 14}, // [13] :: matter entirely. Then itll hurt like hell. But, hey, you volunteered for this. Nobody forced you into it.
        {LUNGS.num, NPC.num, 16}, // [14] :: The Technician painfully removes both of your lungs.
        {DESC.num, EXIT_B.num}, // [15] :: The Technician kicks you out of the lab.
        {EXIT_B.num}, // [16] :: Thank you, goodbye.
        {EXIT_B.num}, // [17] :: Hey you dont have organic lungs. Get out of here!
    };

    protected final int[][] ANIMATION_FLAGS = {
        {1} // Woman
    };

    @Override
    public int[][] getAnimationFlags() {
        return ANIMATION_FLAGS;
    }

    @Override
    public int onDialogIndex(GameState gs, int index) {
        if (index == 7 && gs.soldBodyParts.contains(BodyPart.LUNGS)) {
            LOGGER.log(Level.FINEST, "onDialogIndex() change dialogIndex because lung was already removed.");
            return 17;
        }
        if (index == 9) { // Thrown in jail for hacking.
            gs.hitachiVolunteer = false; // clear it just in case.
        }
        return index;
    }

    @Override
    public int onDialogPreCommand(GameState gs, DialogCommand command) {
        LOGGER.log(Level.CONFIG, "Dialog Pre Command called. command = " + command.name());
        if (command.equals(DIALOG_CLOSE)) { // Woman has left the room.
            LOGGER.log(Level.CONFIG, "Woman animation not visible.");
            // Woman asks you to wait. Hide her animation.
            ANIMATION_FLAGS[0][0] = 0;
        } else if (command.equals(LUNGS)) {
            gs.chipBalance += 3000;
            gs.soldBodyParts.add(BodyPart.LUNGS);
            // TODO: Verify if player can buy lung back at bodyshop in original game.
        }
        return super.onDialogPreCommand(gs, command); // Default value.
    }

    @Override
    public boolean onDeckFinished(GameState gameState) {
        LOGGER.log(Level.CONFIG, "Deck has finished. What shall we do?");
        // make woman visible.
        ANIMATION_FLAGS[0][0] = 1;
        deckJustUsed = true;
        setRequestDialogPopup(true);
        return true;
    }

    @Override
    public JackZone jackZone() {
        return JackZone.TWO;
    }

    @Override
    public void initRoom(GameState gs) {
        //gs.resourceManager.getRoomText(Room.R53).dumpList();
    }

    @Override
    public int[][] getDialogChain() {
        return DIALOG_CHAIN;
    }

    @Override
    public int dialogWarmUp(GameState gs) {
        LOGGER.log(Level.CONFIG, "dialogWarmup()");
        if (deckJustUsed) {
            deckJustUsed = false;
            return 11;
        }
        return 2;
    }

}
