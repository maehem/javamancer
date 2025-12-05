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

import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.room.DialogCommand;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.NPC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.TO_JAIL;
import com.maehem.javamancer.neuro.model.room.RoomBounds;
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import com.maehem.javamancer.neuro.view.popup.DialogPopup;
import java.util.logging.Level;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R2Extras extends RoomExtras {

    protected static final int[][] DIALOG_CHAIN = {
        {TO_JAIL.num}, // You're under arrest!
        {NPC.num, 1} // Move along.
    };

    @Override
    public void initRoom(GameState gs) {
        if (gs.ratzPaid) {
            gs.room.lockDoor(RoomBounds.Door.TOP);
        }
    }

    @Override
    public double getNpcPosition() {
        return 100;
    }
    
    @Override
    public int[][] getDialogChain() {
        return DIALOG_CHAIN;
    }

    @Override
    public int dialogWarmUp(GameState gs) {
        if (gs.ratzPaid) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * The dialog popup is usually dismissible by the player, but in this
     * case we need to send player to jail if they didn't pay Ratz.
     * 
     * @param gs
     * @param pop 
     */
    @Override
    public void onPopupExit(GameState gs, DialogPopup pop) {
        LOGGER.log(Level.SEVERE, "R2 dialog popup closed. Check if Ratz was paid.");
        if (!gs.ratzPaid) {
            pop.processCommand(DialogCommand.TO_JAIL);
        }
    }
    
}
