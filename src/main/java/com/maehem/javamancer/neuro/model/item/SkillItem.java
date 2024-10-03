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

import com.maehem.javamancer.neuro.model.skill.Skill;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class SkillItem extends Item {

    public int level;

    public SkillItem(Catalog item, int level) {
        this(item, level, 0);
    }

    public SkillItem(Catalog item, int level, int price) {
        super(item);
        this.level = level;
        this.price = price;
    }

    @Override
    public void use() {
        // Currently handled by inventory popup, but maybe move
        // that install code here?

        // Install into Skills.
        LOGGER.log(Level.SEVERE, "Install Skill Item: " + item.itemName);
    }

    public static boolean hasSkill(SkillItem skillItem, ArrayList<Skill> skills) {
        for (Skill skill : skills) {
            if (skill.catalog.equals(skillItem.item) && skillItem.level == skill.level) {
                LOGGER.log(Level.CONFIG, "{0} already installed.", skill.catalog.itemName);

                return true;
            }
        }

        return false;
    }

    public static Skill getSkill(SkillItem skillItem, ArrayList<Skill> skills) {
        for (Skill skill : skills) {
            if (skill.catalog.equals(skillItem.item) && skillItem.level == skill.level) {
                LOGGER.log(Level.CONFIG, "Found skill {0} in list.", skill.catalog.itemName);

                return skill;
            }
        }

        return null;
    }

    /**
     * Install or Upgrade SkillItem
     *
     * @param skillItem
     * @param skills
     * @return true if installed or upgraded. false if already installed.
     */
    public static Skill installSkillItem(SkillItem skillItem, ArrayList<Skill> skills) {
        LOGGER.log(Level.CONFIG, "Start install skill item.");

        Skill skill;
        if ((skill = getSkill(skillItem, skills)) != null) {
            int oldLevel = skill.level;
            LOGGER.log(Level.CONFIG, "Seem to have the skill aready? Try upgrade...");
            if (skillItem.level > skill.level) {
                skill.level = skillItem.level;
                LOGGER.log(Level.CONFIG, "Upgraded skill {0} from {1} to {2}.",
                        new Object[]{skill.catalog.itemName, oldLevel, skill.level});
                return skill;
            }
        } else {
            LOGGER.log(Level.FINER, "Skill OK to install.");
            Skill instance = Skill.getInstance(skillItem.item, skillItem.level);
            if (instance != null) {
                LOGGER.log(Level.CONFIG,
                        "Install {0} Skill...",
                        instance.catalog.itemName);
                skills.add(instance);
                return instance;
            } else {
                LOGGER.log(Level.SEVERE, "Unable to install skill " + skillItem.item.name());
            }
        }

        return null;
    }
}
