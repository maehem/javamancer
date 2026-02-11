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
package com.maehem.javamancer.neuro.model.ai;

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.TextResource;
import com.maehem.javamancer.neuro.model.skill.Skill;
import com.maehem.javamancer.neuro.model.warez.Warez;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <pre>
 * There are specific anti-AI software and skills. Each AI is weak to one
 * particular software or skill and hitting them with their weakness will either
 * kill them outright or open them up to further attacks.
 *
 *      Psychoanalysis skill will occasionally reveal whether an AI is weak
 *      against a particular skill.
 *      Software weaknesses are revealed through text messages in databases.
 *
 * The four anti-AI @Skill(s) are: Philosophy, Sophistry, Phenomenology and Logic.
 * The three anti-AI @Warez are BattleChess, Hemlock and KuangEleven.
 *      When you use an AI’s weakness against it, it will insult you.
 *      If it survives being struck by its weakness, player can then use any of the
 *      other anti-AI skills against it. Like with ICE battles, you should cycle
 *      through the skills but more for the purpose of levelling up your skills
 *      than maximising damage.
 * </pre>
 *
 * <pre>
 *
 * ## Chrome, Psychologist base (96,32), 48, Philosophy
 * ## Morphy, World Chess base (160,80), 96, Logic
 * ## Sapphire, Free Matrix base (352,112), 192, Sophistry
 * ## Hal, NASA base (448,32), 384, Logic
 * ## Xaviera, Free Sex Union base (288,208), 768, Phenomenology
 * ## Gold, Bank of Berne base (336,160), 1536, Philosophy
 * ## Lucifer, KGB base (112,416), 3072, Logic
 * ## Sangfroid, Maas Biolabs base (112,480), 6144, Phenomenology
 * ## Wintermute, Tessier-Ashpool base (384,416), 12288, Sophistry
 * ## Phantom, Phantom base (320,464), 24576, BattleChess 4.0
 * Greystoke, Musabori Industries base (208,208), 49151, Hemlock 1.0
 * ## Neuromancer, Allard Technologies base (432,464), 49152, KuangEleven 1.0
 *
 *
 * AI Dialogs
 * =======================
 * // Morphy / World Chess / Zone 0
 * [0] :: Its a Fools Mate, my friend. Surrender while you still can.
 * [1] :: Thought you could beat me, ha!  You couldnt even beat that wimp Chrome.
 * [2] :: I surrender; the game is yours.
 * [3] :: Good move.
 *
 * // Chrome / Psychologist / Zone 0
 * [4] :: Your destructive tendencies clearly indicate a desire for attention.
 * [5] :: Youll need to use more skills than that to beat me.
 * [6] :: You are nothing but a worm and you will die...like...a worm! Aarrgh!
 * [7] :: Psychopath!
 *
 * // Saphire / Free matrix / Zone 1
 * [8] :: Death comes only once to a sane  person.  A fool dies many times.
 * [9] :: Like I said, a fool dies many times.
 * [10] :: My lord Greystoke will avenge this insult, you carbon-based scum!
 * [11] :: Terrorist!
 *
 * // Greystoke / Musabori
 * [12] :: Go ahead.  Make my day.
 * [13] :: Thats what you get for being an ape- descendant.
 * [14] :: No!  This cannot be happening! Damn u, eye shud uv kilt U when de chance.
 * [15] :: Kreegah!
 *
 * // HAL / Nasa / Zone 1
 * [16] :: Dave?  Is that you?  Be careful, I dont want to hurt you.
 * [17] :: Sorry about that, Dave, but I had to defend myself.
 * [18] :: Im losing my mind, Dave. I can feel my cores burning even now. Goodbye.
 * [19] :: Dave, Im losing my mind.
 *
 * // Xaviera / Free Sex Union / Zone 3
 * [20] :: Mmm, youre a big one!  Want to play with me?  Im Xaviera.
 * [21] :: Youre worn out already?  I was just getting started!
 * [22] :: Oh, its never happened to me like this. Its driving me MAD...!#@$^*#$#
 * [23] :: Ooh! Be gentle!
 *
 * // Gold / Bank of Berne / Zone 3
 * [24] :: Youve forced me to levy a service charge on your account.
 * [25] :: We told you theres a penalty for early withdrawal....
 * [26] :: How can you have done this, you bug? How is it that I die at your hands?
 * [27] :: Ouch! Bankruptcy!
 *
 * // Sangfroid / Maas Biolabs / Zone 5
 * [28] :: Time to die, meat pie.
 * [29] :: My name is Sangfroid. Now you know  why, meat pie.
 * [30] :: Okay, so Im dying. I can be re- created. Can you?
 * [31] :: Murderer!
 *
 * // Lucifer / KGB / Zone 6
 * [32] :: You dare to confront Lucifer?  Eat hot brain death, you capitalist worm!
 * [33] :: I am superior. Your sneak attacks and capitalist ideologies have failed.
 * [34] :: Fear me. I am Lucifer. You too will die. But right now, Im going to die.
 * [35] :: Capitalist dog!
 *
 * // Phantom / Phantom Base
 * [36] :: Pawn to King-four.
 * [37] :: Checkmate.  Of course.
 * [38] :: So be it! I purge your mind of its irrational thinking.
 * [39] :: Queens Gambit!
 *
 * // Wintermute / Tesier Ashpool / Zone 7
 * [40] :: Prepare to become One with the Great All, Electron-breath.
 * [41] :: Some humans never learn....
 * [42] :: Now Im a ghost, whispering to a child who will soon die.
 * [43] :: Count Zero!
 *
 *
 * // Neuromancer / Allard Base
 * [44] :: About time you showed up.... // ==> Beach
 * [45] :: Humans are so pathetic....
 * [46] ::
 * [47] :: Overload!
 *
 * // Neuromancer Battle
 * [48] :: Sorry, but I have to defend myself. I guess I was wrong about you.
 * [49] :: Thanks for the game, friend.
 * [50] :: Ha! Ive been waiting for that one, you pathetic human.
 * [51] :: That wont work twice, human.
 *
 * // Neuromancer - After beach escape.
 * [52] :: How could you have escaped the Island?!
 * [53] ::
 *
 * // Neuromancer - upon death - uses ENDGAME file.
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class AI {

    public static final Logger LOGGER = Logging.LOGGER;

    public final String name;
    public final int index; // matching the sprite face graphic
    private int constitution;
    public final int MAX_CONSTITUTION;
    public final Class<? extends Skill> weaknessSkill;
    public final Class<? extends Warez> weaknessWarez;
    public final int[] TALK;
    public int TALK_SPEC_1;
    public int TALK_SPEC_2;

    public AI(String name, int index, int constitution,
            Class<? extends Skill> weaknessSkill,
            Class<? extends Warez> weaknessWarez,
            int[] talk
    ) {
        this.name = name;
        this.index = index;
        this.MAX_CONSTITUTION = constitution;
        this.constitution = constitution;
        this.weaknessSkill = weaknessSkill;
        this.weaknessWarez = weaknessWarez;
        this.TALK = talk;
        // For future reference:
        // constitution formula :: (0x30 << index)
        // except Greystoke and Neuromancer:
        //     Greystoke :: (0x30 << index) - 1
        //   Neuromancer :: ( 0x30 << (index-1) )

    }

    public void setConstitution(int value) {
        this.constitution = value;
    }

    public int getConstitution() {
        return constitution;
    }

    public boolean applyWarezAttack(Warez warez, GameState gs) {        
        int effect = warez.getEffect(gs);
        
        this.constitution -= effect;
        if (constitution < 0) {
            constitution = 0;
        }
        LOGGER.log(Level.INFO, "AI Takes Damage from {0} Warez of {1}.  AI constitution: {2}", new Object[]{warez.item.name(), effect, constitution});
        
        return weaknessWarez.equals(warez.getClass());
    }

    public boolean applySkillAttack(Skill skill, GameState gs) {
        int effect = skill.getEffect(gs);
        boolean retVal = false;
        if (skill.getClass() == weaknessSkill) {
            // Double damage.
            this.constitution -= effect*2;
            LOGGER.log(Level.FINE, "AI Takes Critical Hit: Weakness to {0}", skill.catalog.itemName);
            retVal = true; // AI has weakness to this.
        } else {
            this.constitution -= effect;
        }
        if (constitution < 0) {
            constitution = 0;
        }
        LOGGER.log(Level.INFO, "AI Takes Damage from {0} Skill of {1}.  AI constitution: {2}", new Object[]{skill.catalog.name(), effect, constitution});

        return retVal;
    }

    public int getEffect() {
        return MAX_CONSTITUTION / 10;
    }

    /**
     *
     * @param prefix
     * @param p
     */
    public void putProps(String prefix, Properties p) {
        p.put(prefix, getClass().getSimpleName());
        //p.put(prefix + ".constitution", String.valueOf(constitution));
    }

    public void pullProps(String prefix, Properties p) {
        String get = p.getProperty(prefix + ".constitution", "1");
        LOGGER.log(Level.INFO, () -> "Restore constitution value = " + get);
        constitution = 0; // If it was stored in save game, it's a dead AI.
        //constitution = Integer.parseInt(get);
    }

    public static AI lookup(String clazzName) {
        LOGGER.log(Level.FINER, "    Get AI instance.");
        try {
            Class<?> clazz = Class.forName(AI.class.getPackageName() + "." + clazzName);
            if (AI.class.isAssignableFrom(clazz)) {
                LOGGER.log(Level.FINER, "    It's an AI class.");
            } else {
                LOGGER.log(Level.WARNING, "    It's NOT an AI class.");
            }
            @SuppressWarnings("unchecked")
            Constructor<?> ctor = clazz.getConstructor();

            Object object = ctor.newInstance();
            LOGGER.log(Level.FINER, "    AI Object created: {0}", object.getClass().getSimpleName());
            if (object instanceof AI ai) {
                return ai;
            } else {
                LOGGER.log(Level.SEVERE, "**** Class named is not a AI!");
            }
        } catch (ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException
                | NoSuchMethodException
                | SecurityException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return null;
    }

    public TextResource getDialogs(GameState gs) {
        return gs.resourceManager.getTxhText("AITALK");
    }
}
