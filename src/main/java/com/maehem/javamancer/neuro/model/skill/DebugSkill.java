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

import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.warez.Warez;

/**
 * Fixes corrupted software. 
 * 
 * 
 * <pre>
 * Obtained from: Metro Holografix for $1000. 
 * 
 * Upgrade: To a max level of 4 by Emperor Norton at the Matrix Restaurant. 
 * 
 * 
 * Debug level 4 has a 100% success rate at debugging software.
 * Debug level 3 has a 75% success rate at debugging software.
 * Debug level 2 has a 50% success rate at debugging software.
 * Debug level 1 has a 25% success rate at debugging software.
 *
 * ROMs have Debug 3.
 *
 * When player attempts to debug a program and it fails, subsequent attempts 
 * will always fail to debug that same piece of software except Debug 4.
 * </pre>
 * 
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class DebugSkill extends Skill {

    public enum Result {
        NO_BUGS,
        UNABLE_TO_FIX,
        FIXED,
        NONE
    }

    public DebugSkill(int level) {
        super(Item.Catalog.DEBUG, level, 4);
    }

    @Override
    public String getDescription() {
        return "Fix broken software.";
    }
    
    public Result doDebug( Warez w ) {
        if (w.getDamage() <= 0) {
            return Result.NO_BUGS;
        } else if ( w.getDamage() > level ) {
            return Result.UNABLE_TO_FIX;
        } else {
            w.setDamaged(0);
            return Result.FIXED;
        }
    }
    
    /* TODO:
    	neuro_menu_draw_text("Debug", 9, 0);
	skills_draw_item_desc(item[0], item[1], 0, 0, 2);

	if (item[2] == 0)
	{
		neuro_menu_draw_text("Program has no bugs", 0, 3);
	}
	else
	{
		uint8_t skill_level = g_3f85.skills[DEBUG];

		if (item[2] > g_fix_difficulty[skill_level])
		{
			//* play track 6 
			neuro_menu_draw_text("Unable to fix bugs", 0, 3);
		}
		else
		{
			item[2] = 0;
			///* play track 11 
			neuro_menu_draw_text("Bug fixed, program\nokay", 0, 3);
		}
	}

	neuro_menu_draw_text("Button or [space].", 3, 5);
    */

}
