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
package com.maehem.javamancer.neuro.view.room;

import com.maehem.javamancer.neuro.model.room.Room;
import com.maehem.javamancer.neuro.view.MusicManager.Track;
import static com.maehem.javamancer.neuro.view.MusicManager.Track.*;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public enum RoomMusic {
    R1(CHATSUBO, 0.5, 8000, 3000, 2000),
    R2(STREET_1, 0.44, 30000, 2000, 2000),
    R5(STREET_1, 0.44, 30000, 2000, 2000),
    R13(STREET_1, 0.44, 30000, 2000, 2000),
    R14(STREET_1, 0.44, 30000, 2000, 2000),
    R15(STREET_1, 0.44, 30000, 2000, 2000),
    R16(STREET_1, 0.44, 30000, 2000, 2000),
    R17(STREET_1, 0.44, 30000, 2000, 2000),
    R18(STREET_1, 0.44, 30000, 2000, 2000),
    R22(TESSIER, 0.44, 0, 0, 0),
    R26(STREET_2, 0.44, 30000, 2000, 2000),
    R31(STREET_2, 0.44, 30000, 2000, 2000),
    R37(STREET_2, 0.44, 30000, 2000, 2000),
    R38(STREET_2, 0.44, 30000, 2000, 2000),
    R39(STREET_2, 0.44, 30000, 2000, 2000),
    R45(STREET_2, 0.44, 30000, 2000, 2000),
    R49(STREET_2, 0.44, 30000, 2000, 2000),
    R52(STREET_2, 0.44, 30000, 2000, 2000),
    R54(STREET_3, 0.74, 45000, 2000, 2000),
    R55(STREET_3, 0.74, 45000, 2000, 2000),;

    public final Track track;
    public final double volume;
    public final int fadeIn;
    public final int fadeOut;
    public final long startTime;

    private RoomMusic(Track t, double vol, long startTime, int fadeIn, int fadeOut) {
        this.track = t;
        this.volume = vol;
        this.startTime = startTime;
        this.fadeIn = fadeIn;
        this.fadeOut = fadeOut;
    }

    public static final RoomMusic get(Room r) {
        for (RoomMusic m : values()) {
            if (r.name().equals(m.name())) {
                return m;
            }
        }
        return null;
    }
}
