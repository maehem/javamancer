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
 * THE SOFTWARE IS PROVIDED "AS IS"), WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.maehem.javamancer.neuro.model;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public enum Item {

    /* 0x4469 */
//static char *g_item_names[104] = {
    MIMIC(0, "Mimic"),
    JAMMIES(1, "Jammies"),
    THUNDERHEAD(2, "ThunderHead"),
    VACCINE(3, "Vaccine"),
    BLAMMO(4, "Blammo"),
    DOORSTOP(5, "DoorStop"),
    DECODER(6, "Decoder"),
    SEQUENCER(7, "Sequencer"),
    ARMORALL(8, "ArmorAll"),
    KGB(9, "KGB"),
    COMLINK(10, "Comlink"),
    BLOWTORCH(11, "BlowTorch"),
    PROBE(12, "Probe"),
    DRILL(13, "Drill"),
    HAMMER(14, "Hammer"),
    PYTHON(15, "Python"),
    ACID(16, "Acid"),
    INJECTOR(17, "Injector"),
    DEPTHCHARGE(18, "DepthCharge"),
    CONCRETE(19, "Concrete"),
    EASYRIDER(20, "EasyRider"),
    LOGICBOMB(21, "LogicBomb"),
    CYBERSPACE(22, "Cyberspace"),
    SLOW(23, "Slow"),
    BATTLECHESS(24, "BattleChess"),
    BATTLECHESS2(25, "BattleChess"),
    SCOUT(26, "Scout"),
    HEMLOCK(27, "Hemlock"),
    KUANGELEVEN(28, "KuangEleven"),
    HIKIGAERU(29, "Hiki Gaeru"),
    GAIJIN(30, "Gaijin"),
    BUSHIDO(31, "Bushido"),
    EDOKKO(32, "Edokko"),
    KATANA(33, "Katana"),
    TOFU(34, "Tofu"),
    SHOGUN(35, "Shogun"),
    _188BJB(36, "188BJB"),
    _350SL(37, "350SL"),
    UNK01(38, ""),
    UNK02(39, ""),
    UXB(40, "UXB"),
    UNK03(41, ""),
    ZXB(42, "ZXB"),
    CYBERSPACEII(43, "Cyberspace II"),
    CYBERSPACEIII(44, "Cyberspace III"),
    UNK04(45, ""),
    CYBERSPACEVII(46, "Cyberspace VII"),
    NINJA2000(47, "Ninja 2000"),
    NINJA3000(48, "Ninja 3000"),
    NINJA4000(49, "Ninja 4000"),
    NINJA5000(50, "Ninja 5000"),
    BLUELIGHTSPEC(51, "Blue Light Spec."),
    SAMURAISEVEN(52, "Samurai Seven"),
    UNK05(53, ""),
    UNK06(54, ""),
    UNK07(55, ""),
    UNK08(56, ""),
    UNK09(57, ""),
    UNK10(58, ""),
    UNK11(59, ""),
    UNK12(60, ""),
    UNK13(61, ""),
    UNK14(62, ""),
    UNK15(63, ""),
    UNK16(64, ""),
    UNK17(65, ""),
    UNK18(66, ""),
    BARGANING(67, "Bargaining"),
    COPTALK(68, "CopTalk"),
    WAREZANALYSIS(69, "Warez Analysis"),
    DEBUG(70, "Debug"),
    HARDWAREREPAIR(71, "Hardware Repair"),
    ICEBREAKING(72, "ICE Breaking"),
    EVASION(73, "Evasion"),
    CRYPTOLOGY(74, "Cryptology"),
    JAPANESE(75, "Japanese"),
    LOGIC(76, "Logic"),
    PSYCHOANALYSIS(77, "Psychoanalysis"),
    PHENOMENOLOGY(78, "Phenomenology"),
    PHILOSOPHY(79, "Philosophy"),
    SOPHISTRY(80, "Sophistry"),
    ZEN(81, "Zen"),
    MUSICIANSHIP(82, "Musicianship"),
    CYBEREYES(83, "CyberEyes"),
    UNK19(84, ""),
    UNK20(85, ""),
    GUESTPASS(86, "guest pass"),
    UNK21(87, ""),
    UNK22(88, ""),
    JOYSTICK(89, "joystick"),
    UNK23(90, ""),
    UNK24(91, ""),
    UNK25(92, ""),
    UNK26(93, ""),
    CAVIAR(94, "caviar"),
    PAWNTICKET(95, "pawn ticket"),
    SECURITYPASS(96, "Security Pass"),
    ZIONTICKET(97, "Zion ticket"),
    FREESIDETICKET(98, "Freeside ticket"),
    UNK27(99, ""),
    CHIBATICKET(100, "Chiba ticket"),
    GASMASK(101, "gas mask"),
    UNK28(102, ""),
    SAKE(103, "sake"),
    NONE(999, "none");

    public int num;
    public String itemName;

    private Item(int num, String name) {
        this.num = num;
        this.itemName = name;
    }

}
