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

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.deck.*;
import com.maehem.javamancer.neuro.model.skill.*;
import com.maehem.javamancer.neuro.model.warez.*;
import java.util.logging.Logger;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class Item {

    public static final Logger LOGGER = Logging.LOGGER;

    public enum Catalog {
        MIMIC(0, "Mimic", Warez.class),
        JAMMIES(1, "Jammies", Warez.class),
        THUNDERHEAD(2, "ThunderHead", Warez.class),
        VACCINE(3, "Vaccine", Warez.class),
        BLAMMO(4, "Blammo", Warez.class),
        DOORSTOP(5, "DoorStop", Warez.class),
        DECODER(6, "Decoder", Warez.class),
        SEQUENCER(7, "Sequencer", Warez.class),
        ARMORALL(8, "ArmorAll", Warez.class),
        KGB(9, "KGB", Warez.class),
        COMLINK(10, "Comlink", Warez.class),
        BLOWTORCH(11, "BlowTorch", Warez.class),
        PROBE(12, "Probe", Warez.class),
        DRILL(13, "Drill", Warez.class),
        HAMMER(14, "Hammer", Warez.class),
        PYTHON(15, "Python", Warez.class),
        ACID(16, "Acid", Warez.class),
        INJECTOR(17, "Injector", Warez.class),
        DEPTHCHARGE(18, "DepthCharge", Warez.class),
        CONCRETE(19, "Concrete", Warez.class),
        EASYRIDER(20, "EasyRider", Warez.class),
        LOGICBOMB(21, "LogicBomb", Warez.class),
        CYBERSPACE(22, "Cyberspace", Warez.class),
        SLOW(23, "Slow", Warez.class),
        BATTLECHESS(24, "BattleChess", Warez.class),
        BATTLECHESS2(25, "BattleChess II", Warez.class),
        SCOUT(26, "Scout", Warez.class),
        HEMLOCK(27, "Hemlock", Warez.class),
        KUANGELEVEN(28, "KuangEleven", Warez.class),
        HIKIGAERU(29, "Hiki Gaeru", Warez.class),
        GAIJIN(30, "Gaijin", Warez.class),
        BUSHIDO(31, "Bushido", Warez.class),
        EDOKKO(32, "Edokko", Warez.class),
        KATANA(33, "Katana", Warez.class),
        TOFU(34, "Tofu", Warez.class),
        SHOGUN(35, "Shogun", Warez.class),
        _188BJB(36, "188BJB", Warez.class), // DeckItem?
        _350SL(37, "350SL", Warez.class), // DeckItem?
        UNK01(38, "", Object.class),
        UNK02(39, "", Object.class),
        UXB(40, "UXB", UXBDeckItem.class),
        UNK03(41, "", Object.class),
        ZXB(42, "ZXB", DeckItem.class),
        CYBERSPACEII(43, "Cyberspace II", DeckItem.class),
        CYBERSPACEIII(44, "Cyberspace III", DeckItem.class),
        UNK04(45, "", Object.class),
        CYBERSPACEVII(46, "Cyberspace VII", DeckItem.class),
        NINJA2000(47, "Ninja 2000", DeckItem.class),
        NINJA3000(48, "Ninja 3000", DeckItem.class),
        NINJA4000(49, "Ninja 4000", DeckItem.class),
        NINJA5000(50, "Ninja 5000", DeckItem.class),
        BLUELIGHTSPEC(51, "Blue Light Spec.", DeckItem.class),
        SAMURAISEVEN(52, "Samurai Seven", DeckItem.class),
        UNK05(53, "", Object.class),
        UNK06(54, "", Object.class),
        UNK07(55, "", Object.class),
        UNK08(56, "", Object.class),
        UNK09(57, "", Object.class),
        UNK10(58, "", Object.class),
        UNK11(59, "", Object.class),
        UNK12(60, "", Object.class),
        UNK13(61, "", Object.class),
        UNK14(62, "", Object.class),
        UNK15(63, "", Object.class),
        UNK16(64, "", Object.class),
        UNK17(65, "", Object.class),
        UNK18(66, "", Object.class),
        BARGANING(67, "Bargaining", Skill.class),
        COPTALK(68, "CopTalk", CopTalkSkill.class),
        WAREZANALYSIS(69, "Warez Analysis", Skill.class),
        DEBUG(70, "Debug", Skill.class),
        HARDWAREREPAIR(71, "Hardware Repair", Skill.class),
        ICEBREAKING(72, "ICE Breaking", Skill.class),
        EVASION(73, "Evasion", Skill.class),
        CRYPTOLOGY(74, "Cryptology", Skill.class),
        JAPANESE(75, "Japanese", Skill.class),
        LOGIC(76, "Logic", Skill.class),
        PSYCHOANALYSIS(77, "Psychoanalysis", Skill.class),
        PHENOMENOLOGY(78, "Phenomenology", Skill.class),
        PHILOSOPHY(79, "Philosophy", Skill.class),
        SOPHISTRY(80, "Sophistry", Skill.class),
        ZEN(81, "Zen", Skill.class),
        MUSICIANSHIP(82, "Musicianship", Skill.class),
        CYBEREYES(83, "CyberEyes", RealItem.class),
        UNK19(84, "", Object.class),
        UNK20(85, "", Object.class),
        GUESTPASS(86, "guest pass", RealItem.class),
        UNK21(87, "", Object.class),
        UNK22(88, "", Object.class),
        JOYSTICK(89, "joystick", RealItem.class),
        UNK23(90, "", Object.class),
        UNK24(91, "", Object.class),
        UNK25(92, "", Object.class),
        UNK26(93, "", Object.class),
        CAVIAR(94, "caviar", RealItem.class),
        PAWNTICKET(95, "pawn ticket", RealItem.class),
        SECURITYPASS(96, "Security Pass", RealItem.class),
        ZIONTICKET(97, "Zion ticket", RealItem.class),
        FREESIDETICKET(98, "Freeside ticket", RealItem.class),
        UNK27(99, "", Object.class),
        CHIBATICKET(100, "Chiba ticket", RealItem.class),
        GASMASK(101, "gas mask", RealItem.class),
        UNK28(102, "", Object.class),
        SAKE(103, "sake", RealItem.class),
        CREDITS(666, "Credits", CreditsItem.class),
        NONE(999, "none", Object.class);

        public enum Type {
            REAL, SOFTWARE, DECK, SKILL, CREDITS, UNKNOWN
        }

        public int num;
        public String itemName;
        public final Class clazz;

        private Catalog(int num, String name, Class clazz) {
            this.num = num;
            this.itemName = name;
            this.clazz = clazz;
        }

    }

    public final Catalog item;

    // Condition?
    public Item(Catalog item) {
        this.item = item;
    }

    public String getName() {
        return item.itemName;
    }

    public abstract void use();

    @Override
    public String toString() {
        return item.itemName;
    }

}
