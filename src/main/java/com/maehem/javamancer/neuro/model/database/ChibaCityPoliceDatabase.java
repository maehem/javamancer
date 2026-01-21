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
package com.maehem.javamancer.neuro.model.database;

import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.Person;
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * Name: Chiba City Tactical Police
 * Number: 9
 * Zone: 1
 * ComLink: 3.0
 * LinkCode: keisatsu
 * Passwords: (1) warrants (2) supertac
 * Warez: none
 * Matrix: 288, 112
 * AI: none
 * Weakness: none
 * ICE: 150
 * Content: enter Larry Moe's name and ID (062788138) into Warrant List
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class ChibaCityPoliceDatabase extends Database {

    public ChibaCityPoliceDatabase(ResourceManager rm) {
        super("Chiba City Tactical Police", 9,
                1,
                3,
                "keisatsu",
                "warrants", "supertac", null,
                288, 112,
                null,
                150,
                rm
        );
    }

    @Override
    public void handlePersonListChanged(GameState gameState) {
        // List contains Larry's BAMA?
        for (Person p : gameState.chibaWantedList) {
            if (p.getBama().equals(GameState.LARRY_MOE_BAMA)) {
                gameState.larryMoeWanted = true;
                return;
            }
        }
    }
}
