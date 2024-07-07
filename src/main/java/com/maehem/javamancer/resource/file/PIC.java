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
public enum PIC implements Resource {

    R1(0, 0x5EE, 0x1346),
    R2(0, 0x1CE0, 0x1CB3),
    R3(0, 0x45CA, 0x1B06),
    R4(0, 0x654A, 0x2646),
    R5(0, 0x914C, 0x13FF),
    R6(0, 0xAD36, 0xFDC),
    R7(0, 0xCD55, 0x1AD2),
    R8(0, 0xF1CB, 0x187B),
    R9(0, 0x110A6, 0x20F8),
    R10(0, 0x13561, 0x1051),
    R11(0, 0x14D4E, 0x11DC),
    R12(0, 0x16BE8, 0x1A35),
    R13(0, 0x191B2, 0x1A9F),
    R14(0, 0x1AC7E, 0x1DFE),
    R15(0, 0x1CAA9, 0x1D0B),
    R16(0, 0x1E7E1, 0x1CC0),
    R17(0, 0x2055A, 0x1B13),
    R18(0, 0x2209A, 0x1730),
    R19(0, 0x23C63, 0x1336),
    R20(0, 0x27416, 0x164C),
    R21(0, 0x28A88, 0xA53),
    R22(0, 0x2976E, 0x690),
    R23(0, 0x2A6BE, 0x1A98),
    R24(0, 0x2C6D9, 0x1DDD),
    R25(0, 0x2F436, 0xB59),
    R26(0, 0x307A2, 0x1DD3),
    R27(0, 0x338EB, 0x133E),
    R29(1, 0x33010, 0x1160),
    R30(1, 0x342A0, 0xCB9),
    R31(1, 0x34F86, 0x133B),
    R32(1, 0x3694C, 0xC29),
    R33(1, 0x3759B, 0xA3A),
    R34(1, 0x3846D, 0x5CF),
    R35(1, 0x38F24, 0xA3E),
    R36(1, 0x39EEB, 0x1C8F),
    R37(1, 0x3C10D, 0x1253),
    R38(1, 0x3D38D, 0x1570),
    R39(1, 0x3E92A, 0x13FD),
    R40(1, 0x40259, 0xD39),
    R41(1, 0x41216, 0x707),
    R42(1, 0x41B89, 0x96D),
    R44(1, 0x42E0E, 0x12F3),
    R45(1, 0x445A7, 0x16AA),
    R46(1, 0x468D1, 0xAE2),
    R47(1, 0x47462, 0x139C),
    R49(1, 0x4882B, 0x14B7),
    R50(1, 0x4A2C9, 0x216D),
    R51(1, 0x4CBC9, 0xAC3),
    R52(1, 0x4DAF2, 0x2151),
    R53(1, 0x5046E, 0x876),
    R54(1, 0x51455, 0x1396),
    R55(1, 0x52811, 0x13B1),
    R56(1, 0x53F4A, 0x1612),
    R57(1, 0x55765, 0x15F7),
    R58(1, 0x56E0F, 0x170C);

    public final int fileNum;
    public final int offset; // DOS long == Java int (32-bits)
    public final int size;

    private PIC(int fileNum, int offset, int size) {
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
    public String getName() {
        return name();
    }

    @Override
    public void decompress(byte[] compressedData, byte[] destination) {
//	uint8_t pic[64000];
//
//	huffman_decompress(src, pic);
//	decode_rle(pic, 152 * 112, dst);
//	xor_rows(dst, 152, 112);
//
//	return 152 * 112;
    }
}
