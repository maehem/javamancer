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

/**
 * Top, Right, Bottom, Left of walk area. Player can walk inside a rectangle
 * zone defined above.
 *
 * Door positions: T(x,w), R(y,w),B(x,w),L(y,w) Doors rest on edge define above
 * with position defined by two numbers x or y and width defined by w.
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public enum RoomBounds { // and Doors

    R1(184, 580, 224, 40, 0, 0, 0, 0, 436, 144, 0, 0),
    R2(174, 570, 224, 166, 450, 100, 174, 50, 0, 0, 0, 0),
    R3(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R4(180, 400, 224, 40, 0, 0, 0, 0, 140, 70, 0, 0),
    R5(174, 560, 224, 50, 120, 130, 150, 80, 250, 120, 174, 50), // Street Body Shop
    R6(174, 400, 224, 40, 240, 100, 0, 0, 0, 0, 0, 0),
    R7(180, 600, 224, 160, 0, 0, 180, 40, 0, 0, 0, 0), // Cheap Hotel
    R8(180, 560, 224, 40, 0, 0, 180, 40, 0, 0, 0, 0),
    R9(174, 580, 224, 40, 0, 0, 170, 50, 0, 0, 0, 0),
    R10(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R11(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R12(182, 580, 224, 20, 20, 80, 0, 0, 230, 170, 0, 0),
    R13(174, 560, 224, 50, 260, 120, 170, 50, 120, 400, 174, 50),
    R14(160, 560, 224, 80, 80, 430, 174, 50, 80, 440, 0, 0),
    R15(158, 530, 224, 60, 120, 330, 176, 50, 120, 380, 164, 50),// Street Cheap Hotel
    R16(160, 500, 224, 70, 90, 460, 0, 0, 90, 500, 150, 60), // Street G-Loser
    R17(158, 540, 224, 60, 120, 380, 160, 70, 100, 400, 150, 60),
    R18(158, 540, 224, 60, 120, 380, 0, 0, 0, 0, 170, 54),
    R19(180, 540, 224, 40, 0, 0, 180, 40, 0, 0, 0, 0),  // Chiba Space Port
    R20(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R21(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R22(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R23(176, 400, 224, 20, 0, 0, 0, 0, 20, 80, 0, 0),
    R24(180, 480, 224, 20, 0, 0, 0, 0, 0, 0, 180, 50),
    R25(180, 580, 224, 20, 0, 0, 0, 0, 0, 0, 180, 50),
    R26(180, 600, 224, 20, 0, 0, 180, 46, 0, 0, 180, 46),
    R27(180, 580, 224, 20, 0, 0, 0, 0, 0, 0, 180, 40),
    R28(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R29(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R30(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R31(178, 570, 224, 40, 70, 120, 180, 44, 0, 0, 180, 44), // Street Metro Holo
    R32(180, 340, 224, 50, 0, 0, 0, 0, 100, 80, 0, 0),
    R33(174, 580, 224, 40, 220, 200, 0, 0, 0, 0, 0, 0),
    R34(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R35(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R36(180, 580, 224, 40, 0, 0, 0, 0, 240, 120, 0, 0), // House of Pong
    R37(176, 560, 224, 60, 240, 160, 0, 0, 80, 440, 0, 0),
    R38(150, 560, 224, 60, 100, 420, 170, 50, 60, 500, 0, 0), // Street Asano's
    R39(170, 560, 224, 20, 80, 440, 178, 44, 30, 110, 178, 44),// Street Crazy Edo's
    R40(180, 580, 224, 30, 30, 100, 0, 0, 0, 0, 0, 0), // Crazy Edo's
    R41(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R42(174, 580, 224, 40, 220, 200, 0, 0, 0, 0, 0, 0),
    R43(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R44(180, 580, 224, 20, 0, 0, 0, 0, 0, 0, 180, 44),
    R45(180, 580, 224, 20, 0, 0, 180, 44, 300, 100, 180, 44),
    R46(174, 500, 224, 40, 300, 120, 0, 0, 0, 0, 0, 0), // Matrix restaurant
    R47(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R48(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R49(180, 560, 224, 40, 0, 0, 180, 44, 0, 0, 180, 44),
    R50(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R51(180, 300, 224, 40, 0, 0, 180, 44, 0, 0, 180, 44),
    R52(180, 540, 224, 20, 0, 0, 180, 44, 0, 0, 180, 44),
    R53(180, 370, 224, 40, 0, 0, 0, 0, 250, 90, 0, 0),
    R54(168, 560, 224, 60, 210, 170, 170, 40, 100, 400, 170, 40),
    R55(170, 570, 224, 20, 100, 420, 170, 54, 50, 110, 170, 54),
    R56(180, 570, 224, 40, 40, 100, 0, 0, 0, 0, 0, 0),
    R57(180, 290, 224, 40, 0, 0, 0, 0, 0, 0, 182, 42),
    R58(180, 580, 224, 350, 0, 0, 180, 50, 0, 0, 0, 0);  // Musabori

    public enum Door {
        NONE, TOP, RIGHT, BOTTOM, LEFT, JAIL, STREET_CHAT, BODY_SHOP, SHUTTLE, FREESIDE, ZION, SPACEPORT
    };

    public final int tBound; // Upper walk Line
    public final int rBound; // Right Walk Line
    public final int bBound; // Bottom Walk Line
    public final int lBound; // Left Walk Line
    public final int tx; // Top Door x location. 0 = none.
    public final int tw; // Top Door width. 0 = none.
    public final int ry; // Right Door  y location.
    public final int rw; // Right Door width.
    public final int bx; // Bottom Door x location.
    public final int bw; // Bottom Door width.
    public final int ly; // Left Door y location.
    public final int lw; // Left Door width.

    private RoomBounds(
            int tBound, int rBound, int bBound, int lBound,
            int tx, int tw, int ry, int rw, int bx, int bw, int ly, int lw
    ) {
        this.tBound = tBound;
        this.rBound = rBound;
        this.bBound = bBound;
        this.lBound = lBound;
        this.tx = tx;
        this.tw = tw;
        this.ry = ry;
        this.rw = rw;
        this.bx = bx;
        this.bw = bw;
        this.ly = ly;
        this.lw = lw;
    }

    public static RoomBounds get(Room room) {
        for (RoomBounds rb : values()) {
            if (rb.name().equals(room.name())) {
                return rb;
            }
        }

        return null;
    }
}
