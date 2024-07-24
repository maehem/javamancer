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
package com.maehem.javamancer.neuro.model.item;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class Item {

    public enum Catalog {
        MIMIC(0, "Mimic", Type.SOFTWARE),
        JAMMIES(1, "Jammies", Type.SOFTWARE),
        THUNDERHEAD(2, "ThunderHead", Type.SOFTWARE),
        VACCINE(3, "Vaccine", Type.SOFTWARE),
        BLAMMO(4, "Blammo", Type.SOFTWARE),
        DOORSTOP(5, "DoorStop", Type.SOFTWARE),
        DECODER(6, "Decoder", Type.SOFTWARE),
        SEQUENCER(7, "Sequencer", Type.SOFTWARE),
        ARMORALL(8, "ArmorAll", Type.SOFTWARE),
        KGB(9, "KGB", Type.SOFTWARE),
        COMLINK(10, "Comlink", Type.SOFTWARE),
        BLOWTORCH(11, "BlowTorch", Type.SOFTWARE),
        PROBE(12, "Probe", Type.SOFTWARE),
        DRILL(13, "Drill", Type.SOFTWARE),
        HAMMER(14, "Hammer", Type.SOFTWARE),
        PYTHON(15, "Python", Type.SOFTWARE),
        ACID(16, "Acid", Type.SOFTWARE),
        INJECTOR(17, "Injector", Type.SOFTWARE),
        DEPTHCHARGE(18, "DepthCharge", Type.SOFTWARE),
        CONCRETE(19, "Concrete", Type.SOFTWARE),
        EASYRIDER(20, "EasyRider", Type.SOFTWARE),
        LOGICBOMB(21, "LogicBomb", Type.SOFTWARE),
        CYBERSPACE(22, "Cyberspace", Type.SOFTWARE),
        SLOW(23, "Slow", Type.SOFTWARE),
        BATTLECHESS(24, "BattleChess", Type.SOFTWARE),
        BATTLECHESS2(25, "BattleChess II", Type.SOFTWARE),
        SCOUT(26, "Scout", Type.SOFTWARE),
        HEMLOCK(27, "Hemlock", Type.SOFTWARE),
        KUANGELEVEN(28, "KuangEleven", Type.SOFTWARE),
        HIKIGAERU(29, "Hiki Gaeru", Type.SOFTWARE),
        GAIJIN(30, "Gaijin", Type.SOFTWARE),
        BUSHIDO(31, "Bushido", Type.SOFTWARE),
        EDOKKO(32, "Edokko", Type.SOFTWARE),
        KATANA(33, "Katana", Type.SOFTWARE),
        TOFU(34, "Tofu", Type.SOFTWARE),
        SHOGUN(35, "Shogun", Type.SOFTWARE),
        _188BJB(36, "188BJB", Type.SOFTWARE),
        _350SL(37, "350SL", Type.SOFTWARE),
        UNK01(38, "", Type.UNKNOWN),
        UNK02(39, "", Type.UNKNOWN),
        UXB(40, "UXB", Type.DECK),
        UNK03(41, "", Type.UNKNOWN),
        ZXB(42, "ZXB", Type.DECK),
        CYBERSPACEII(43, "Cyberspace II", Type.DECK),
        CYBERSPACEIII(44, "Cyberspace III", Type.DECK),
        UNK04(45, "", Type.UNKNOWN),
        CYBERSPACEVII(46, "Cyberspace VII", Type.DECK),
        NINJA2000(47, "Ninja 2000", Type.DECK),
        NINJA3000(48, "Ninja 3000", Type.DECK),
        NINJA4000(49, "Ninja 4000", Type.DECK),
        NINJA5000(50, "Ninja 5000", Type.DECK),
        BLUELIGHTSPEC(51, "Blue Light Spec.", Type.DECK),
        SAMURAISEVEN(52, "Samurai Seven", Type.DECK),
        UNK05(53, "", Type.UNKNOWN),
        UNK06(54, "", Type.UNKNOWN),
        UNK07(55, "", Type.UNKNOWN),
        UNK08(56, "", Type.UNKNOWN),
        UNK09(57, "", Type.UNKNOWN),
        UNK10(58, "", Type.UNKNOWN),
        UNK11(59, "", Type.UNKNOWN),
        UNK12(60, "", Type.UNKNOWN),
        UNK13(61, "", Type.UNKNOWN),
        UNK14(62, "", Type.UNKNOWN),
        UNK15(63, "", Type.UNKNOWN),
        UNK16(64, "", Type.UNKNOWN),
        UNK17(65, "", Type.UNKNOWN),
        UNK18(66, "", Type.UNKNOWN),
        BARGANING(67, "Bargaining", Type.SKILL),
        COPTALK(68, "CopTalk", Type.SKILL),
        WAREZANALYSIS(69, "Warez Analysis", Type.SKILL),
        DEBUG(70, "Debug", Type.SKILL),
        HARDWAREREPAIR(71, "Hardware Repair", Type.SKILL),
        ICEBREAKING(72, "ICE Breaking", Type.SKILL),
        EVASION(73, "Evasion", Type.SKILL),
        CRYPTOLOGY(74, "Cryptology", Type.SKILL),
        JAPANESE(75, "Japanese", Type.SKILL),
        LOGIC(76, "Logic", Type.SKILL),
        PSYCHOANALYSIS(77, "Psychoanalysis", Type.SKILL),
        PHENOMENOLOGY(78, "Phenomenology", Type.SKILL),
        PHILOSOPHY(79, "Philosophy", Type.SKILL),
        SOPHISTRY(80, "Sophistry", Type.SKILL),
        ZEN(81, "Zen", Type.SKILL),
        MUSICIANSHIP(82, "Musicianship", Type.SKILL),
        CYBEREYES(83, "CyberEyes", Type.REAL),
        UNK19(84, "", Type.UNKNOWN),
        UNK20(85, "", Type.UNKNOWN),
        GUESTPASS(86, "guest pass", Type.REAL),
        UNK21(87, "", Type.UNKNOWN),
        UNK22(88, "", Type.UNKNOWN),
        JOYSTICK(89, "joystick", Type.REAL),
        UNK23(90, "", Type.UNKNOWN),
        UNK24(91, "", Type.UNKNOWN),
        UNK25(92, "", Type.UNKNOWN),
        UNK26(93, "", Type.UNKNOWN),
        CAVIAR(94, "caviar", Type.REAL),
        PAWNTICKET(95, "pawn ticket", Type.REAL),
        SECURITYPASS(96, "Security Pass", Type.REAL),
        ZIONTICKET(97, "Zion ticket", Type.REAL),
        FREESIDETICKET(98, "Freeside ticket", Type.REAL),
        UNK27(99, "", Type.UNKNOWN),
        CHIBATICKET(100, "Chiba ticket", Type.REAL),
        GASMASK(101, "gas mask", Type.REAL),
        UNK28(102, "", Type.UNKNOWN),
        SAKE(103, "sake", Type.REAL),
        CREDITS(666, "Credits", Type.CREDITS),
        NONE(999, "none", Type.UNKNOWN);

        public enum Type {
            REAL, SOFTWARE, DECK, SKILL, CREDITS, UNKNOWN
        }

        public int num;
        public String itemName;
        public final Type type;

        private Catalog(int num, String name, Type type) {
            this.num = num;
            this.itemName = name;
            this.type = type;
        }

    }

    public final Catalog item;

    // Condition?
    public Item(Catalog item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return item.itemName;
    }

}
