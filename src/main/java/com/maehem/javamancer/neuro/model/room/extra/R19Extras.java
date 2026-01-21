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
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DIALOG_CLOSE;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.EXIT_R;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.EXIT_SHUTTLE_FS;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.EXIT_SHUTTLE_ZION;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LONG_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SHORT_DESC;
import com.maehem.javamancer.neuro.model.room.RoomBounds;
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import java.util.logging.Level;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R19Extras extends RoomExtras { // Spaceport Chiba

    private static final int FS_TICKET = 1000;
    private static final int ZION_TICKET = 500;

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC.num}, {SHORT_DESC.num}, //  [0] ::
        {3, 4, 5}, // [2] :: Konnichiwa!  Would you like to buy a ticket?
        {10}, // [3] :: Sure and begorrah, youre a fine looking lass. Have you got a special fare for police officers?
        {7}, // [4] :: Id like to buy a ticket.
        {11}, // [5] :: I just stumbled in here by mistake. Excuse me.
        {}, // [6] ::
        {12, 13, 14}, // [7] :: We have non-stop flights leaving for Freeside at $1000 and Zion Cluster at $500.  Where would you like to go?
        {}, // [8] ::
        {}, // [9] ::
        {12, 13, 14}, // [10] :: Of course, officer. We have flights to Freeside for $1000 and Zion Cluster for $500.  Where would you like to go?
        {EXIT_R.num}, // [11] :: Then you can just stumble right back out, wilson!
        {16}, // [12] :: Freeside. One person. Non-smoking. Whats the holo-movie on this flight?
        {17}, // [13] :: Zion Cluster. One person. Non-smoking.  Whats the holo-movie on this flight?
        {DIALOG_CLOSE.num}, // [14] :: Ive changed my mind.  I think Ill remain in Chiba for now.
        {}, // [15] ::
        {EXIT_SHUTTLE_FS.num}, // [16] :: Enjoy your flight to Freeside. You can buy a return ticket when you arrive. The holo-movie is "Burning Chrome."
        {EXIT_SHUTTLE_ZION.num}, // [17] :: Enjoy your flight to Zion. You can buy a return ticket when you arrive. The holo-movie is "Aliens III."
        {}, // [18] ::
        {}, // [19] ::
        {DIALOG_CLOSE.num}, // [20] :: Im afraid you dont have enough money, citizen.
    };

    // Animation
    protected final int[][] ANIMATION_FLAGS = {
        {1},  // Ticket Agent Face
        {1},  // Ticket Agent Arms
        {1}   // Plane Crash
    };

    @Override
    public int[][] getAnimationFlags() {
        return ANIMATION_FLAGS;
    }
    
    @Override
    public void initRoom(GameState gs) {
    }

    @Override
    public int[][] getDialogChain() {
        return DIALOG_CHAIN;
    }

    @Override
    public int dialogWarmUp(GameState gs) {
        return 2;
    }

    @Override
    public int onDialogPreCommand(GameState gs, DialogCommand command) {
        switch (command) {
            case EXIT_SHUTTLE_FS -> {
                LOGGER.log(Level.INFO, "Do something about purchase of ticket Free Side.");
                if (gs.moneyChipBalance < FS_TICKET) {
                    LOGGER.log(Level.FINE, "Not enough money for Free Side.");
                    return 20;
                } else {
                    gs.moneyChipBalance -= FS_TICKET;
                    gs.shuttleDest = RoomBounds.Door.FREESIDE;
                }
            }
            case EXIT_SHUTTLE_ZION -> {
                LOGGER.log(Level.INFO, "Do something about purchase of ticket Zion.");
                if (gs.moneyChipBalance < ZION_TICKET) {
                    LOGGER.log(Level.FINE, "Not enough money for Zion.");
                    return 20;
                } else {
                    gs.moneyChipBalance -= ZION_TICKET;
                    gs.shuttleDest = RoomBounds.Door.ZION;
                }
            }
        }

        return -1;
    }


}
