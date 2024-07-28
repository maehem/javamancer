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
package com.maehem.javamancer.neuro.model;

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
    R2(174, 580, 224, 40, 0, 0, 174, 50, 0, 0, 0, 0),
    R3(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R4(174, 580, 224, 40, 0, 0, 0, 0, 220, 200, 0, 0),
    R5(174, 580, 224, 40, 220, 200, 0, 0, 0, 0, 174, 50),
    R6(174, 580, 224, 40, 220, 200, 0, 0, 0, 0, 0, 0),
    R7(174, 580, 224, 40, 0, 0, 170, 50, 0, 0, 0, 0),
    R8(174, 580, 224, 40, 0, 0, 170, 50, 0, 0, 0, 0),
    R9(174, 580, 224, 40, 0, 0, 170, 50, 0, 0, 0, 0),
    R10(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R11(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R12(174, 580, 224, 40, 20, 80, 0, 0, 220, 200, 0, 0),
    R13(174, 580, 224, 40, 220, 200, 170, 50, 220, 200, 170, 50),
    R14(174, 580, 224, 40, 220, 200, 150, 80, 220, 200, 0, 0),
    R15(174, 580, 224, 40, 220, 200, 150, 80, 220, 200, 150, 80),
    R16(174, 580, 224, 40, 220, 200, 0, 0, 220, 200, 150, 80),
    R17(174, 580, 224, 40, 200, 200, 150, 80, 220, 200, 150, 80),
    R18(174, 580, 224, 40, 220, 200, 0, 0, 0, 0, 150, 80),
    R19(174, 580, 224, 40, 0, 0, 150, 80, 0, 0, 0, 0),
    R20(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R21(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R22(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R23(174, 580, 224, 40, 0, 0, 0, 0, 220, 200, 0, 0),
    R24(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 180, 50),
    R25(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 180, 50),
    R26(174, 580, 224, 40, 0, 0, 150, 80, 0, 0, 150, 80),
    R27(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 170, 55),
    R28(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R29(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R30(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R31(174, 580, 224, 40, 220, 200, 150, 80, 0, 0, 150, 800),
    R32(174, 580, 224, 40, 0, 0, 0, 0, 220, 200, 0, 0),
    R33(174, 580, 224, 40, 220, 200, 0, 0, 0, 0, 0, 0),
    R34(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R35(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R36(174, 580, 224, 40, 0, 0, 0, 0, 220, 200, 0, 0),
    R37(174, 580, 224, 40, 220, 200, 0, 0, 100, 400, 0, 0),
    R38(174, 580, 224, 40, 100, 400, 150, 80, 100, 400, 0, 0),
    R39(174, 580, 224, 40, 100, 400, 150, 80, 220, 200, 150, 80),
    R40(174, 580, 224, 40, 20, 200, 0, 0, 0, 0, 0, 0),
    R41(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R42(174, 580, 224, 40, 220, 200, 0, 0, 0, 0, 0, 0),
    R43(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R44(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 150, 80),
    R45(174, 580, 224, 40, 0, 0, 150, 80, 220, 200, 150, 80),
    R46(174, 580, 224, 40, 400, 100, 0, 0, 0, 0, 0, 0),
    R47(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R48(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R49(174, 580, 224, 40, 0, 0, 150, 80, 0, 0, 150, 80),
    R50(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R51(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R52(174, 580, 224, 40, 0, 0, 150, 80, 0, 0, 150, 80),
    R53(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R54(174, 580, 224, 40, 220, 200, 150, 80, 100, 400, 150, 80),
    R55(174, 580, 224, 40, 100, 400, 150, 80, 150, 200, 150, 80),
    R56(174, 580, 224, 40, 200, 200, 0, 0, 0, 0, 0, 0),
    R57(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0),
    R58(174, 580, 224, 40, 0, 0, 0, 0, 0, 0, 0, 0);

    public enum Door {
        NONE, TOP, RIGHT, BOTTOM, LEFT
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
