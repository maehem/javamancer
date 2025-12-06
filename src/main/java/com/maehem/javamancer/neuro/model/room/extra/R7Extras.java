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
import com.maehem.javamancer.neuro.model.JackZone;
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.item.RealItem;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DIALOG_CLOSE;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DIALOG_END;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.EXIT_R;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LONG_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SHORT_DESC;
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import java.util.logging.Level;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R7Extras extends RoomExtras { // Cheap Hotel

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC.num}, {SHORT_DESC.num}, // 0, 1
        {DIALOG_CLOSE.num}, // [2] :: Your room service order is delivered to you.
        {EXIT_R.num}, // [3] :: The management kicks you out
        {}, // [4] :: Youre carrying too much stuff.
    };

    @Override
    public void initRoom(GameState gs) {
        //gs.resourceManager.getRoomText(Room.R7).dumpList();
        
    }

    @Override
    public JackZone jackZone() {
        return JackZone.ZERO;
    }

    @Override
    public boolean hasPAX() {
        return true;
    }

    @Override
    public int[][] getDialogChain() {
        return DIALOG_CHAIN;
    }

    @Override
    public int dialogWarmUp(GameState gs) {
        if (!gs.roomCanTalk()) {
            return DIALOG_END.num;
        }
        if (gs.hotelOnAccount < gs.hotelCharges) {
            return 3;
        }
        if (gs.hotelDeliverCaviar > 0 || gs.hotelDeliverSake > 0) {
            while (gs.hotelDeliverCaviar > 0) {
                LOGGER.log(Level.FINE, "Add one Caviar to player inventory.");
                gs.hotelDeliverCaviar--;
                gs.inventory.add(new RealItem(Item.Catalog.CAVIAR, 0));
            }
            while (gs.hotelDeliverSake > 0) {
                LOGGER.log(Level.FINE, "Add one Sake to player inventory.");
                gs.hotelDeliverSake--;
                gs.inventory.add(new RealItem(Item.Catalog.SAKE, 0));
            }
            return 2;
        }

        return DIALOG_CLOSE.num;
    }

}
