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
    R58(1, 0x56D5C, 0xB3),
    COPEN0(1, 0x00000, 0x3D2),
    COPEN1(1, 0x003D2, 0x39B),
    DB0(1, 0x0076D, 0x09F2), // RegFellow
    DB1(1, 0x0115F, 0x0EC5),
    DB2(1, 0x02024, 0x0514),
    DB3(1, 0x02538, 0x0920),
    DB4(1, 0x02E58, 0x0665),
    DB5(1, 0x034BD, 0x076D),
    DB6(1, 0x03C2A, 0x0811),
    DB7(1, 0x0443B, 0x0A02),
    DB8(1, 0x04E3D, 0x089D),
    DB9(1, 0x056DA, 0x0129),
    DB10(1, 0x05803, 0x0768),
    DB11(1, 0x05F6B, 0x0BB5),
    DB12(1, 0x06B20, 0x0880),
    DB13(1, 0x073A0, 0x0778),
    DB14(1, 0X07B18, 0x0E2C),
    DB15(1, 0x08944, 0x0AE6),
    DB16(1, 0x0942A, 0x0A4A),
    DB17(1, 0x09E74, 0x091F),
    DB18(1, 0x0A793, 0x0D5A),
    DB19(1, 0x0B4ED, 0x0A99),
    DB20(1, 0x0BF86, 0x07B5),
    DB21(1, 0x0C73B, 0x0A85),
    DB22(1, 0x0D1C0, 0x05F0),
    DB23(1, 0x0D7B0, 0x0508),
    DB24(1, 0x0DCB8, 0x09B2),
    DB25(1, 0x0E66A, 0x0A29),
    DB26(1, 0x0F093, 0x02E1),
    DB27(1, 0x0F374, 0x069A),
    DB28(1, 0x0FA0E, 0x03F1),
    DB29(1, 0x0FDFF, 0x0AD2),
    DB30(1, 0x108D1, 0x0C90),
    DB31(1, 0x11561, 0x0793),
    DB32(1, 0x11CF4, 0x0953),
    DB33(1, 0x12647, 0x087F),
    DB34(1, 0x12EC6, 0x086D),
    DB35(1, 0x13733, 0x02DB),
    DB36(1, 0x13A0E, 0x0627),
    DB37(1, 0x14035, 0x0967),
    DB38(1, 0x1499C, 0x001B),
    FUJI0(1, 0x149B7, 0x00B4),
    HITACHI0(1, 0x14A6B, 0x0107),
    HOSA0(1, 0x14B72, 0x00B9),
    ICE(1, 0x14C2B, 0X0450),
    IRS0(1, 0x1507B, 0x0087),
    IRS1(1, 0x15102, 0x02A1),
    JUSTICE0(1, 0x153A3, 0x0095),
    JUSTICE1(1, 0x15438, 0x0099),
    POLICE0(1, 0x175AE, 0x008B),
    PSYCHO0(1, 0x17639, 0x030D),
    PSYCHO1(1, 0x17946, 0xE103),
    PSYCHO2(1, 0x17D27, 0x040C),
    PSYCHO3(1, 0x18133, 0x0265),
    SEA0(1, 0x18398, 0x008C),
    SEA1(1, 0x18424, 0x008D),
    SEA2(1, 0x184B1, 0x028A);

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
/*
final byte[] data = {
    0x43, 0x4F, 0x50, 0x45, 0x4E, 0x31, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0xD2, 0x03, 0x00, 0x00, 0x9B, 0x03, 0x00, 0x00, // COPEN1
    0x44, 0x42, 0x30, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x6D, 0x07, 0x00, 0x00, 0xF2, 0x09, 0x00, 0x00, // DB0.BIH
    0x44, 0x42, 0x31, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x5F, 0x11, 0x00, 0x00, 0xC5, 0x0E, 0x00, 0x00, // DB1
    0x44, 0x42, 0x32, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x24, 0x20, 0x00, 0x00, 0x14, 0x05, 0x00, 0x00, // DB2
    0x44, 0x42, 0x33, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x38, 0x25, 0x00, 0x00, 0x20, 0x09, 0x00, 0x00, // DB3
    0x44, 0x42, 0x34, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x58, 0x2E, 0x00, 0x00, 0x65, 0x06, 0x00, 0x00, // DB4
    0x44, 0x42, 0x35, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xBD, 0x34, 0x00, 0x00, 0x6D, 0x07, 0x00, 0x00, // DB5
    0x44, 0x42, 0x36, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x2A, 0x3C, 0x00, 0x00, 0x11, 0x08, 0x00, 0x00, // DB6
    0x44, 0x42, 0x37, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3B, 0x44, 0x00, 0x00, 0x02, 0x0A, 0x00, 0x00, // DB7
    0x44, 0x42, 0x38, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3D, 0x4E, 0x00, 0x00, 0x9D, 0x08, 0x00, 0x00, // DB8
    0x44, 0x42, 0x39, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xDA, 0x56, 0x00, 0x00, 0x29, 0x01, 0x00, 0x00, // DB9
    0x44, 0x42, 0x31, 0x30, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0x58, 0x00, 0x00, 0x68, 0x07, 0x00, 0x00, // DB10
    0x44, 0x42, 0x31, 0x31, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x6B, 0x5F, 0x00, 0x00, 0xB5, 0x0B, 0x00, 0x00, // DB11
    0x44, 0x42, 0x31, 0x32, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x20, 0x6B, 0x00, 0x00, 0x80, 0x08, 0x00, 0x00, // DB12
    0x44, 0x42, 0x31, 0x33, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xA0, 0x73, 0x00, 0x00, 0x78, 0x07, 0x00, 0x00, // DB13
    0x44, 0x42, 0x31, 0x34, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x18, 0x7B, 0x00, 0x00, 0x2C, 0x0E, 0x00, 0x00, // DB14
    0x44, 0x42, 0x31, 0x35, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x44, 0x89, 0x00, 0x00, 0xE6, 0x0A, 0x00, 0x00, // DB15
    0x44, 0x42, 0x31, 0x36, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x2A, 0x94, 0x00, 0x00, 0x4A, 0x0A, 0x00, 0x00, // DB16
    0x44, 0x42, 0x31, 0x37, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x74, 0x9E, 0x00, 0x00, 0x1F, 0x09, 0x00, 0x00, // DB17
    0x44, 0x42, 0x31, 0x38, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x93, 0xA7, 0x00, 0x00, 0x5A, 0x0D, 0x00, 0x00, // DB18
    0x44, 0x42, 0x31, 0x39, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xED, 0xB4, 0x00, 0x00, 0x99, 0x0A, 0x00, 0x00, // DB19
    0x44, 0x42, 0x32, 0x30, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x86, 0xBF, 0x00, 0x00, 0xB5, 0x07, 0x00, 0x00, // DB20
    0x44, 0x42, 0x32, 0x31, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3B, 0xC7, 0x00, 0x00, 0x85, 0x0A, 0x00, 0x00, // DB21
    0x44, 0x42, 0x32, 0x32, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xC0, 0xD1, 0x00, 0x00, 0xF0, 0x05, 0x00, 0x00, // DB22
    0x44, 0x42, 0x32, 0x33, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xB0, 0xD7, 0x00, 0x00, 0x08, 0x05, 0x00, 0x00, // DB23
    0x44, 0x42, 0x32, 0x34, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xB8, 0xDC, 0x00, 0x00, 0xB2, 0x09, 0x00, 0x00, // DB24
    0x44, 0x42, 0x32, 0x35, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x6A, 0xE6, 0x00, 0x00, 0x29, 0x0A, 0x00, 0x00, // DB25
    0x44, 0x42, 0x32, 0x36, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x93, 0xF0, 0x00, 0x00, 0xE1, 0x02, 0x00, 0x00, // DB26
    0x44, 0x42, 0x32, 0x37, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x74, 0xF3, 0x00, 0x00, 0x9A, 0x06, 0x00, 0x00, // DB27
    0x44, 0x42, 0x32, 0x38, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0E, 0xFA, 0x00, 0x00, 0xF1, 0x03, 0x00, 0x00, // DB28
    0x44, 0x42, 0x32, 0x39, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFF, 0xFD, 0x00, 0x00, 0xD2, 0x0A, 0x00, 0x00, // DB29
    0x44, 0x42, 0x33, 0x30, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xD1, 0x08, 0x01, 0x00, 0x90, 0x0C, 0x00, 0x00, // DB30
    0x44, 0x42, 0x33, 0x31, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x61, 0x15, 0x01, 0x00, 0x93, 0x07, 0x00, 0x00, // DB31
    0x44, 0x42, 0x33, 0x32, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xF4, 0x1C, 0x01, 0x00, 0x53, 0x09, 0x00, 0x00, // DB32
    0x44, 0x42, 0x33, 0x33, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x47, 0x26, 0x01, 0x00, 0x7F, 0x08, 0x00, 0x00, // DB33
    0x44, 0x42, 0x33, 0x34, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xC6, 0x2E, 0x01, 0x00, 0x6D, 0x08, 0x00, 0x00, // DB34
    0x44, 0x42, 0x33, 0x35, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x33, 0x37, 0x01, 0x00, 0xDB, 0x02, 0x00, 0x00, // DB35
    0x44, 0x42, 0x33, 0x36, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0E, 0x3A, 0x01, 0x00, 0x27, 0x06, 0x00, 0x00, // DB36
    0x44, 0x42, 0x33, 0x37, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x35, 0x40, 0x01, 0x00, 0x67, 0x09, 0x00, 0x00, // DB37
    0x44, 0x42, 0x33, 0x38, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x9C, 0x49, 0x01, 0x00, 0x1B, 0x00, 0x00, 0x00, // DB38
    0x46, 0x55, 0x4A, 0x49, 0x30, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0xB7, 0x49, 0x01, 0x00, 0xB4, 0x00, 0x00, 0x00, // FUJI0
    0x48, 0x49, 0x54, 0x41, 0x43, 0x48, 0x49, 0x30, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x6B, 0x4A, 0x01, 0x00, 0x07, 0x01, 0x00, 0x00, // HITACHI0
    0x48, 0x4F, 0x53, 0x41, 0x30, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x72, 0x4B, 0x01, 0x00, 0xB9, 0x00, 0x00, 0x00,
    0x49, 0x43, 0x45, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x2B, 0x4C, 0x01, 0x00, 0x50, 0x04, 0x00, 0x00,
    0x49, 0x52, 0x53, 0x30, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7B, 0x50, 0x01, 0x00, 0x87, 0x00, 0x00, 0x00,
    0x49, 0x52, 0x53, 0x31, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0x51, 0x01, 0x00, 0xA1, 0x02, 0x00, 0x00,
    0x4A, 0x55, 0x53, 0x54, 0x49, 0x43, 0x45, 0x30, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0xA3, 0x53, 0x01, 0x00, 0x95, 0x00, 0x00, 0x00,
    0x4A, 0x55, 0x53, 0x54, 0x49, 0x43, 0x45, 0x31, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x38, 0x54, 0x01, 0x00, 0x99, 0x00, 0x00, 0x00,
    0x4E, 0x45, 0x57, 0x53, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xD1, 0x54, 0x01, 0x00, 0x6E, 0x14, 0x00, 0x00,
    0x50, 0x41, 0x58, 0x42, 0x42, 0x53, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x3F, 0x69, 0x01, 0x00, 0x6F, 0x0C, 0x00, 0x00,
    0x50, 0x4F, 0x4C, 0x49, 0x43, 0x45, 0x30, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0xAE, 0x75, 0x01, 0x00, 0x8B, 0x00, 0x00, 0x00,
    0x50, 0x53, 0x59, 0x43, 0x48, 0x4F, 0x30, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x39, 0x76, 0x01, 0x00, 0x0D, 0x03, 0x00, 0x00,
    0x50, 0x53, 0x59, 0x43, 0x48, 0x4F, 0x31, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x46, 0x79, 0x01, 0x00, 0xE1, 0x03, 0x00, 0x00,
    0x50, 0x53, 0x59, 0x43, 0x48, 0x4F, 0x32, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x27, 0x7D, 0x01, 0x00, 0x0C, 0x04, 0x00, 0x00,
    0x50, 0x53, 0x59, 0x43, 0x48, 0x4F, 0x33, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x33, 0x81, 0x01, 0x00, 0x65, 0x02, 0x00, 0x00,
    0x53, 0x45, 0x41, 0x30, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x98, 0x83, 0x01, 0x00, 0x8C, 0x00, 0x00, 0x00,
    0x53, 0x45, 0x41, 0x31, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x24, 0x84, 0x01, 0x00, 0x8D, 0x00, 0x00, 0x00,
    0x53, 0x45, 0x41, 0x32, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xB1, 0x84, 0x01, 0x00, 0x8A, 0x02, 0x00, 0x00,
    0x41, 0x49, 0x50, 0x30, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3B, 0x87, 0x01, 0x00, 0x34, 0x03, 0x00, 0x00,
    0x41, 0x49, 0x50, 0x31, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x6F, 0x8A, 0x01, 0x00, 0x2A, 0x03, 0x00, 0x00,
    0x41, 0x49, 0x50, 0x32, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x99, 0x8D, 0x01, 0x00, 0x08, 0x03, 0x00, 0x00,
    0x41, 0x49, 0x50, 0x33, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xA1, 0x90, 0x01, 0x00, 0xD2, 0x02, 0x00, 0x00,
    0x41, 0x49, 0x50, 0x34, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x73, 0x93, 0x01, 0x00, 0x35, 0x03, 0x00, 0x00,
    0x41, 0x49, 0x50, 0x35, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xA8, 0x96, 0x01, 0x00, 0x42, 0x03, 0x00, 0x00,
    0x41, 0x49, 0x50, 0x36, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xEA, 0x99, 0x01, 0x00, 0x73, 0x03, 0x00, 0x00,
    0x41, 0x49, 0x50, 0x37, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x5D, 0x9D, 0x01, 0x00, 0xEA, 0x02, 0x00, 0x00,
    0x41, 0x49, 0x50, 0x38, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x47, 0xA0, 0x01, 0x00, 0x16, 0x03, 0x00, 0x00,
    0x41, 0x49, 0x50, 0x39, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x5D, 0xA3, 0x01, 0x00, 0xF8, 0x02, 0x00, 0x00,
    0x41, 0x49, 0x50, 0x31, 0x30, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x55, 0xA6, 0x01, 0x00, 0xD2, 0x03, 0x00, 0x00,
    0x41, 0x49, 0x50, 0x31, 0x31, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x27, 0xAA, 0x01, 0x00, 0x35, 0x03, 0x00, 0x00,
    0x43, 0x53, 0x44, 0x42, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x5C, 0xAD, 0x01, 0x00, 0xBD, 0x15, 0x00, 0x00,
    0x43, 0x53, 0x50, 0x41, 0x43, 0x45, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0x00, 0x00, 0x19, 0xC3, 0x01, 0x00, 0x6D, 0x10, 0x00, 0x00,
    0x43, 0x53, 0x50, 0x41, 0x4E, 0x45, 0x4C, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0x00, 0x86, 0xD3, 0x01, 0x00, 0x33, 0x07, 0x00, 0x00,
    0x44, 0x42, 0x53, 0x50, 0x52, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0xB9, 0xDA, 0x01, 0x00, 0x6B, 0x02, 0x00, 0x00,
    0x45, 0x4E, 0x44, 0x47, 0x41, 0x4D, 0x45, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0x00, 0x24, 0xDD, 0x01, 0x00, 0x92, 0x3E, 0x00, 0x00,
    0x47, 0x52, 0x49, 0x44, 0x42, 0x41, 0x53, 0x45, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0xB6, 0x1B, 0x02, 0x00, 0x9B, 0x10, 0x00, 0x00,
    0x47, 0x52, 0x49, 0x44, 0x53, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x51, 0x2C, 0x02, 0x00, 0x84, 0x60, 0x00, 0x00,
    0x49, 0x43, 0x45, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xD5, 0x8C, 0x02, 0x00, 0xE1, 0x22, 0x00, 0x00,
    0x53, 0x48, 0x4F, 0x54, 0x53, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0xB6, 0xAF, 0x02, 0x00, 0x5A, 0x05, 0x00, 0x00,
    0x56, 0x49, 0x52, 0x55, 0x53, 0x49, 0x43, 0x45, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0x10, 0xB5, 0x02, 0x00, 0x7F, 0x46, 0x00, 0x00,
    0x56, 0x49, 0x52, 0x55, 0x53, 0x52, 0x4F, 0x54, 0x2E, 0x49, 0x4D, 0x48, 0x00, 0x00, 0x8F, 0xFB, 0x02, 0x00, 0x3F, 0x22, 0x00, 0x00,
    0x41, 0x49, 0x54, 0x41, 0x4C, 0x4B, 0x2E, 0x54, 0x58, 0x48, 0x00, 0x00, 0x00, 0x00, 0xCE, 0x1D, 0x03, 0x00, 0x9B, 0x05, 0x00, 0x00,
    0x45, 0x4E, 0x44, 0x47, 0x41, 0x4D, 0x45, 0x2E, 0x54, 0x58, 0x48, 0x00, 0x00, 0x00, 0x69, 0x23, 0x03, 0x00, 0xF9, 0x03, 0x00, 0x00,
    0x49, 0x43, 0x45, 0x2E, 0x54, 0x58, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x62, 0x27, 0x03, 0x00, 0x51, 0x04, 0x00, 0x00,
    0x52, 0x32, 0x39, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xB3, 0x2B, 0x03, 0x00, 0x5D, 0x04, 0x00, 0x00,
    0x52, 0x32, 0x39, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x10, 0x30, 0x03, 0x00, 0x60, 0x11, 0x00, 0x00,
    0x52, 0x32, 0x39, 0x2E, 0x41, 0x4E, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x70, 0x41, 0x03, 0x00, 0x0A, 0x01, 0x00, 0x00,
    0x52, 0x33, 0x30, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7A, 0x42, 0x03, 0x00, 0x26, 0x00, 0x00, 0x00,
    0x52, 0x33, 0x30, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xA0, 0x42, 0x03, 0x00, 0xB9, 0x0C, 0x00, 0x00,
    0x52, 0x33, 0x31, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x59, 0x4F, 0x03, 0x00, 0x2D, 0x00, 0x00, 0x00,
    0x52, 0x33, 0x31, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x86, 0x4F, 0x03, 0x00, 0x3B, 0x13, 0x00, 0x00,
    0x52, 0x33, 0x32, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xC1, 0x62, 0x03, 0x00, 0x8B, 0x06, 0x00, 0x00,
    0x52, 0x33, 0x32, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x4C, 0x69, 0x03, 0x00, 0x29, 0x0C, 0x00, 0x00,
    0x52, 0x33, 0x33, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x75, 0x75, 0x03, 0x00, 0x26, 0x00, 0x00, 0x00,
    0x52, 0x33, 0x33, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x9B, 0x75, 0x03, 0x00, 0x3A, 0x0A, 0x00, 0x00,
    0x52, 0x33, 0x34, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xD5, 0x7F, 0x03, 0x00, 0x98, 0x04, 0x00, 0x00,
    0x52, 0x33, 0x34, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x6D, 0x84, 0x03, 0x00, 0xCF, 0x05, 0x00, 0x00,
    0x52, 0x33, 0x34, 0x2E, 0x41, 0x4E, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3C, 0x8A, 0x03, 0x00, 0x20, 0x04, 0x00, 0x00,
    0x52, 0x33, 0x35, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x5C, 0x8E, 0x03, 0x00, 0xC8, 0x00, 0x00, 0x00,
    0x52, 0x33, 0x35, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x24, 0x8F, 0x03, 0x00, 0x3E, 0x0A, 0x00, 0x00,
    0x52, 0x33, 0x36, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x62, 0x99, 0x03, 0x00, 0x89, 0x05, 0x00, 0x00,
    0x52, 0x33, 0x36, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xEB, 0x9E, 0x03, 0x00, 0x8F, 0x1C, 0x00, 0x00,
    0x52, 0x33, 0x36, 0x2E, 0x41, 0x4E, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7A, 0xBB, 0x03, 0x00, 0x66, 0x05, 0x00, 0x00,
    0x52, 0x33, 0x37, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xE0, 0xC0, 0x03, 0x00, 0x2D, 0x00, 0x00, 0x00,
    0x52, 0x33, 0x37, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0D, 0xC1, 0x03, 0x00, 0x53, 0x12, 0x00, 0x00,
    0x52, 0x33, 0x38, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x60, 0xD3, 0x03, 0x00, 0x2D, 0x00, 0x00, 0x00,
    0x52, 0x33, 0x38, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x8D, 0xD3, 0x03, 0x00, 0x70, 0x15, 0x00, 0x00,
    0x52, 0x33, 0x39, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFD, 0xE8, 0x03, 0x00, 0x2D, 0x00, 0x00, 0x00,
    0x52, 0x33, 0x39, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x2A, 0xE9, 0x03, 0x00, 0xFD, 0x13, 0x00, 0x00,
    0x52, 0x34, 0x30, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x27, 0xFD, 0x03, 0x00, 0x32, 0x05, 0x00, 0x00,
    0x52, 0x34, 0x30, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x59, 0x02, 0x04, 0x00, 0x39, 0x0D, 0x00, 0x00,
    0x52, 0x34, 0x31, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x92, 0x0F, 0x04, 0x00, 0x84, 0x02, 0x00, 0x00,
    0x52, 0x34, 0x31, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x16, 0x12, 0x04, 0x00, 0x07, 0x07, 0x00, 0x00,
    0x52, 0x34, 0x31, 0x2E, 0x41, 0x4E, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1D, 0x19, 0x04, 0x00, 0x3E, 0x01, 0x00, 0x00,
    0x52, 0x34, 0x32, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x5B, 0x1A, 0x04, 0x00, 0x2E, 0x01, 0x00, 0x00,
    0x52, 0x34, 0x32, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x89, 0x1B, 0x04, 0x00, 0x6D, 0x09, 0x00, 0x00,
    0x52, 0x34, 0x34, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xF6, 0x24, 0x04, 0x00, 0x18, 0x09, 0x00, 0x00,
    0x52, 0x34, 0x34, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0E, 0x2E, 0x04, 0x00, 0xF3, 0x12, 0x00, 0x00,
    0x52, 0x34, 0x34, 0x2E, 0x41, 0x4E, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x41, 0x04, 0x00, 0xCE, 0x03, 0x00, 0x00,
    0x52, 0x34, 0x35, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xCF, 0x44, 0x04, 0x00, 0xD8, 0x00, 0x00, 0x00,
    0x52, 0x34, 0x35, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xA7, 0x45, 0x04, 0x00, 0xAA, 0x16, 0x00, 0x00,
    0x52, 0x34, 0x35, 0x2E, 0x41, 0x4E, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x51, 0x5C, 0x04, 0x00, 0x89, 0x04, 0x00, 0x00,
    0x52, 0x34, 0x36, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xDA, 0x60, 0x04, 0x00, 0xF7, 0x07, 0x00, 0x00,
    0x52, 0x34, 0x36, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xD1, 0x68, 0x04, 0x00, 0xE2, 0x0A, 0x00, 0x00,
    0x52, 0x34, 0x37, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xB3, 0x73, 0x04, 0x00, 0xAF, 0x00, 0x00, 0x00,
    0x52, 0x34, 0x37, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x62, 0x74, 0x04, 0x00, 0x9C, 0x13, 0x00, 0x00,
    0x52, 0x34, 0x39, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFE, 0x87, 0x04, 0x00, 0x2D, 0x00, 0x00, 0x00,
    0x52, 0x34, 0x39, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x2B, 0x88, 0x04, 0x00, 0xB7, 0x14, 0x00, 0x00,
    0x52, 0x35, 0x30, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xE2, 0x9C, 0x04, 0x00, 0xE7, 0x05, 0x00, 0x00,
    0x52, 0x35, 0x30, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xC9, 0xA2, 0x04, 0x00, 0x6D, 0x21, 0x00, 0x00,
    0x52, 0x35, 0x30, 0x2E, 0x41, 0x4E, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x36, 0xC4, 0x04, 0x00, 0xD4, 0x06, 0x00, 0x00,
    0x52, 0x35, 0x31, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0A, 0xCB, 0x04, 0x00, 0xBF, 0x00, 0x00, 0x00,
    0x52, 0x35, 0x31, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xC9, 0xCB, 0x04, 0x00, 0xC3, 0x0A, 0x00, 0x00,
    0x52, 0x35, 0x32, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x8C, 0xD6, 0x04, 0x00, 0x66, 0x04, 0x00, 0x00,
    0x52, 0x35, 0x32, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xF2, 0xDA, 0x04, 0x00, 0x51, 0x21, 0x00, 0x00,
    0x52, 0x35, 0x32, 0x2E, 0x41, 0x4E, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x43, 0xFC, 0x04, 0x00, 0x1B, 0x03, 0x00, 0x00,
    0x52, 0x35, 0x33, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x5E, 0xFF, 0x04, 0x00, 0x10, 0x05, 0x00, 0x00,
    0x52, 0x35, 0x33, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x6E, 0x04, 0x05, 0x00, 0x76, 0x08, 0x00, 0x00,
    0x52, 0x35, 0x33, 0x2E, 0x41, 0x4E, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xE4, 0x0C, 0x05, 0x00, 0x4B, 0x07, 0x00, 0x00,
    0x52, 0x35, 0x34, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x2F, 0x14, 0x05, 0x00, 0x26, 0x00, 0x00, 0x00,
    0x52, 0x35, 0x34, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x55, 0x14, 0x05, 0x00, 0x96, 0x13, 0x00, 0x00,
    0x52, 0x35, 0x35, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xEB, 0x27, 0x05, 0x00, 0x26, 0x00, 0x00, 0x00,
    0x52, 0x35, 0x35, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x11, 0x28, 0x05, 0x00, 0xB1, 0x13, 0x00, 0x00,
    0x52, 0x35, 0x36, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xC2, 0x3B, 0x05, 0x00, 0x88, 0x03, 0x00, 0x00,
    0x52, 0x35, 0x36, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x4A, 0x3F, 0x05, 0x00, 0x12, 0x16, 0x00, 0x00,
    0x52, 0x35, 0x37, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x5C, 0x55, 0x05, 0x00, 0x09, 0x02, 0x00, 0x00,
    0x52, 0x35, 0x37, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x65, 0x57, 0x05, 0x00, 0xF7, 0x15, 0x00, 0x00,
    0x52, 0x35, 0x38, 0x2E, 0x42, 0x49, 0x48, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x5C, 0x6D, 0x05, 0x00, 0xB3, 0x00, 0x00, 0x00,
    0x52, 0x35, 0x38, 0x2E, 0x50, 0x49, 0x43, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0F, 0x6E, 0x05, 0x00, 0x0C, 0x17, 0xD0, 0x0C,
};
*/
