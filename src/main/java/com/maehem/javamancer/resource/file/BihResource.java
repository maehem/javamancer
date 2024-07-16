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
package com.maehem.javamancer.resource.file;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public enum BihResource implements Resource {

    R1(0, 0, 0x5EE),
    R2(0, 0x1C19, 0xC7),
    R3(0, 0x3CD3, 0x87F),
    R4(0, 0x60D0, 0x47A),
    R5(0, 0x911F, 0x2D),
    R6(0, 0xA54B, 0x7EB),
    R7(0, 0xCAAE, 0x2A7),
    R8(0, 0xE827, 0x9A4),
    R9(0, 0x10C02, 0x4A4),
    R10(0, 0x1319E, 0x3C3),
    R11(0, 0x145B2, 0x79C),
    R12(0, 0x1629C, 0x94C),
    R13(0, 0x19185, 0x2D),
    R14(0, 0x1AC51, 0x2D),
    R15(0, 0x1CA7C, 0x2D),
    R16(0, 0x1E7B4, 0x2D),
    R17(0, 0x204A1, 0xB9),
    R18(0, 0x2206D, 0x2D),
    R19(0, 0x237CA, 0x499),
    R20(0, 0x26DCC, 0x64A),
    R21(0, 0x28A62, 0x26),
    R22(0, 0x294DB, 0x293),
    R23(0, 0x29EA7, 0x817),
    R24(0, 0x2C156, 0x583),
    R25(0, 0x2F052, 0x3E4),
    R26(0, 0x2FF8F, 0x813),
    R27(0, 0x33100, 0x7EB),
    R28(0, 0x34E5B, 0x412),
    CORNERS(0, 0x3526D, 0x21),
    ROOMPOS(0, 0x3528E, 0x336),
    NEWS(1, 0x154D1, 0x146E),
    PAXBBS(1, 0x1693F, 0xC6F),
    R29(1, 0x32BB3, 0x45D),
    R30(1, 0x3427A, 0x26),
    R31(1, 0x34F59, 0x2D),
    R32(1, 0x362C1, 0x68B),
    R33(1, 0x37575, 0x26),
    R34(1, 0x37FD5, 0x498),
    R35(1, 0x38E5C, 0xC8),
    R36(1, 0x39962, 0x589),
    R37(1, 0x3C0E0, 0x2D),
    R38(1, 0x3D360, 0x2D),
    R39(1, 0x3E8FD, 0x2D),
    R40(1, 0x3FD27, 0x532),
    R41(1, 0x40F92, 0x284),
    R42(1, 0x41A5B, 0x12E),
    R44(1, 0x424F6, 0x918),
    R45(1, 0x444CF, 0xD8),
    R46(1, 0x460DA, 0x7F7),
    R47(1, 0x473B3, 0xAF),
    R49(1, 0x487FE, 0x2D),
    R50(1, 0x49CE2, 0x5E7),
    R51(1, 0x4CB0A, 0xBF),
    R52(1, 0x4D68C, 0x466),
    R53(1, 0x4FF5E, 0x510),
    R54(1, 0x5142F, 0x26),
    R55(1, 0x527EB, 0x26),
    R56(1, 0x53BC2, 0x388),
    R57(1, 0x5555C, 0x209),
    R58(1, 0x56D5C, 0xB3),;

    public final int fileNum;
    public final int offset; // DOS long == Java int (32-bits)
    public final int size;

    private BihResource(int fileNum, int offset, int size) {
        this.fileNum = fileNum;
        this.offset = offset;
        this.size = size;
    }

    @Override
    public int getFileNum() {
        return fileNum;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int decompress(byte[] compressedData, byte[] destination) {
        return Huffman.decompress(compressedData, destination);
    }

    @Override
    public String getName() {
        return name();
    }
}
