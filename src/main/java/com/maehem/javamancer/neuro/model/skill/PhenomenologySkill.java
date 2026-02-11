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
package com.maehem.javamancer.neuro.model.skill;

import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.item.Item;
import static com.maehem.javamancer.neuro.model.skill.Skill.LOGGER;
import java.util.logging.Level;

/**
 * Damages AIs. 
 * 
 * <pre>
 * Obtained from: Julius Deane for $1000. 
 * 
 * Upgrade: by one level automatically each time player fights an AI and 
 * damages the AI with the skill.
 * Also upgrade directly to level 5 at Turing Registry.
* <pre>
* 
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class PhenomenologySkill extends Skill {

    public PhenomenologySkill(int level) {
        super(Item.Catalog.PHENOMENOLOGY, level, 8);
    }


    @Override
    public void use() {
        LOGGER.log(Level.INFO, "Use skill: {0}", catalog.itemName);
    }

    @Override
    public String getDescription() {
        return "AI fight skill. +1 after every AI victory.";
    }
    
    @Override
    public int getEffect(GameState gs) {
        return level * level * 200;
    }
}
