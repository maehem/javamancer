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
import java.util.Properties;
import java.util.logging.Logger;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class Item {

    public static final Logger LOGGER = Logging.LOGGER;

    public enum Catalog {
        MIMIC(0, "Mimic", MimicWarez.class),
        JAMMIES(1, "Jammies", JammiesWarez.class),
        THUNDERHEAD(2, "ThunderHead", ThunderheadWarez.class),
        VACCINE(3, "Vaccine", VaccineWarez.class),
        BLAMMO(4, "Blammo", BlammoWarez.class),
        DOORSTOP(5, "DoorStop", DoorStopWarez.class),
        DECODER(6, "Decoder", DecoderWarez.class),
        SEQUENCER(7, "Sequencer", SequencerWarez.class),
        ARMORALL(8, "ArmorAll", ArmorallWarez.class),
        KGB(9, "KGB", KGBWarez.class),
        COMLINK(10, "Comlink", ComLinkWarez.class),
        BLOWTORCH(11, "BlowTorch", BlowTorchWarez.class),
        PROBE(12, "Probe", ProbeWarez.class),
        DRILL(13, "Drill", DrillWarez.class),
        HAMMER(14, "Hammer", HammerWarez.class),
        PYTHON(15, "Python", PythonWarez.class),
        ACID(16, "Acid", AcidWarez.class),
        INJECTOR(17, "Injector", InjectorWarez.class),
        DEPTHCHARGE(18, "DepthCharge", DepthChargeWarez.class),
        CONCRETE(19, "Concrete", ConcreteWarez.class),
        EASYRIDER(20, "EasyRider", EasyRiderWarez.class),
        LOGICBOMB(21, "LogicBomb", LogicBombWarez.class),
        CYBERSPACE(22, "Cyberspace", CyberspaceWarez.class),
        SLOW(23, "Slow", SlowWarez.class),
        BATTLECHESS(24, "BattleChess", BattleChessWarez.class),
        BATTLECHESS2(25, "BattleChess II", BattleChessWarez.class),
        SCOUT(26, "Scout", ScoutWarez.class),
        HEMLOCK(27, "Hemlock", HemlockWarez.class),
        KUANGELEVEN(28, "KuangEleven", KuangElevenWarez.class),
        HIKIGAERU(29, "Hiki Gaeru", HikiGaeruDeckItem.class),
        GAIJIN(30, "Gaijin", GaijinDeckItem.class),
        BUSHIDO(31, "Bushido", BushidoDeckItem.class),
        EDOKKO(32, "Edokko", EdokkoDeckItem.class),
        KATANA(33, "Katana", KatanaDeckItem.class),
        TOFU(34, "Tofu", TofuDeckItem.class),
        SHOGUN(35, "Shogun", ShogunDeckItem.class),
        _188BJB(36, "188BJB", BJB188DeckItem.class), // DeckItem?
        _350SL(37, "350SL", SL350DeckItem.class), // DeckItem?
        UNK01(38, "", Object.class),
        UNK02(39, "", Object.class),
        UXB(40, "UXB", UXBDeckItem.class),
        UNK03(41, "", Object.class),
        ZXB(42, "ZXB", ZXBDeckItem.class),
        CYBERSPACEII(43, "Cyberspace II", Cyberspace2DeckItem.class),
        CYBERSPACEIII(44, "Cyberspace III", Cyberspace3DeckItem.class),
        UNK04(45, "", Object.class),
        CYBERSPACEVII(46, "Cyberspace VII", Cyberspace7DeckItem.class),
        NINJA2000(47, "Ninja 2000", Ninja2000DeckItem.class),
        NINJA3000(48, "Ninja 3000", Ninja3000DeckItem.class),
        NINJA4000(49, "Ninja 4000", Ninja4000DeckItem.class),
        NINJA5000(50, "Ninja 5000", Ninja5000DeckItem.class),
        BLUELIGHTSPEC(51, "Blue Light Spec.", BlueLightSpecialDeckItem.class),
        SAMURAISEVEN(52, "Samurai Seven", SamuraiSevenDeckItem.class),
        MINDBENDER(53, "Mindbender", MindBenderWarez.class),
        VIDEOSOFT(54, "Chaos Videosoft", ChaosWarez.class),
        PICKUP(55, "PickUpGirls", PickUpGirlsWarez.class),
        TOXIN(56, "Toxin", ToxinWarez.class),
        SNAIL(57, "SnailBait", SnailBaitWarez.class),
        MEGADEATH(58, "MegaDeath", MegaDeathWarez.class),
        CENTURION(59, "Centurion", CenturionWarez.class),
        BUDGETPAL(60, "Budget Pal", BudgetPalWarez.class),
        RECEIPTFORGER(61, "Receipt Forger", ReceiptForgerWarez.class),
        UNK14(62, "", Object.class),
        UNK15(63, "", Object.class),
        UNK16(64, "", Object.class),
        UNK17(65, "", Object.class),
        UNK18(66, "", Object.class),
        BARGANING(67, "Bargaining", SkillItem.class, BargainingSkill.class),
        COPTALK(68, "CopTalk", SkillItem.class, CopTalkSkill.class),
        WAREZANALYSIS(69, "Warez Analysis", SkillItem.class, WarezAnalysisSkill.class),
        DEBUG(70, "Debug", SkillItem.class, DebugSkill.class),
        HARDWAREREPAIR(71, "Hardware Repair", SkillItem.class, HardwareRepairSkill.class),
        ICEBREAKING(72, "ICE Breaking", SkillItem.class, IceBreakingSkill.class),
        EVASION(73, "Evasion", SkillItem.class, EvasionSkill.class),
        CRYPTOLOGY(74, "Cryptology", SkillItem.class, CryptologySkill.class),
        JAPANESE(75, "Japanese", SkillItem.class, JapaneseSkill.class),
        LOGIC(76, "Logic", SkillItem.class, LogicSkill.class),
        PSYCHOANALYSIS(77, "Psychoanalysis", SkillItem.class, PsychoanalysisSkill.class),
        PHENOMENOLOGY(78, "Phenomenology", SkillItem.class, PhenomenologySkill.class),
        PHILOSOPHY(79, "Philosophy", SkillItem.class, PhilosophySkill.class),
        SOPHISTRY(80, "Sophistry", SkillItem.class, SophistrySkill.class),
        ZEN(81, "Zen", SkillItem.class, ZenSkill.class),
        MUSICIANSHIP(82, "Musicianship", SkillItem.class, MusicianshipSkill.class),
        CYBEREYES(83, "CyberEyes", DeckItem.class),
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
        public final Class skillClazz;

        private Catalog(int num, String name, Class clazz) {
            this.num = num;
            this.itemName = name;
            this.clazz = clazz;
            this.skillClazz = null; // Unused in this instance.
        }
        
        // Special for Skill Item to Skill Class lookup.
        private Catalog(int num, String name, Class itemClazz, Class skillClazz) {
            this.num = num;
            this.itemName = name;
            this.clazz = itemClazz;
            this.skillClazz = skillClazz;
        }

    }

    public final Catalog item;

    public int quantity = 1; // When used in vending list.
    public int price = -1;

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

    public static Catalog lookup(String itemName) {
        for (Catalog cat : Catalog.values()) {
            if (cat.name().equals(itemName)) {
                return cat;
            }
        }

        return null;
    }

    /**
     * Prefix might look like "inventory.0." We might add UXB, UXB.damage
     *
     * Override for additional sub-properties.
     *
     * @param prefix
     * @param p
     */
    public void putProps(String prefix, Properties p) {
        p.put(prefix, item.name());
    }

//    public Item getItemInstance(Class<? extends Item> itemClazz) {
//
//        try {
//            Constructor<?> ctor = itemClazz.getConstructor();
//            Object object = ctor.newInstance(new Object[]{});
//            LOGGER.log(Level.INFO, "Item Object created: " + itemClazz.getSimpleName());
//            if (object instanceof Item freshItem) {
//                return freshItem;
//            } else {
//                LOGGER.log(Level.SEVERE, "AI Creation Failed: " + itemClazz.getSimpleName());
//            }
//        } catch (InvocationTargetException
//                | InstantiationException
//                | IllegalAccessException
//                | IllegalArgumentException
//                | NoSuchMethodException
//                | SecurityException ex) {
//            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
//            ex.printStackTrace();
//
//        }
//
//        return null;
//    }


}
