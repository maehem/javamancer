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
package com.maehem.javamancer.neuro.model.room;

import com.maehem.javamancer.neuro.model.Room;
import com.maehem.javamancer.neuro.model.RoomBounds;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public enum RoomMap {
    R1(Room.R1, null, null, Room.R2, null),
    R2(Room.R2, null, null, null, Room.R1);

    public final Room room;
    public final Room t;
    public final Room r;
    public final Room b;
    public final Room l;

    private RoomMap(Room room, Room t, Room r, Room b, Room l) {
        this.room = room;
        this.t = t;
        this.r = r;
        this.b = b;
        this.l = l;
    }

    public static Room getRoom(Room r, RoomBounds.Door d) {
        for (RoomMap rm : values()) {
            if (r.equals(rm.room)) {
                switch (d) {
                    case TOP -> {
                        return rm.t;
                    }
                    case RIGHT -> {
                        return rm.r;
                    }
                    case BOTTOM -> {
                        return rm.b;
                    }
                    case LEFT -> {
                        return rm.l;
                    }
                }
            }
        }
        return null;
    }
}
