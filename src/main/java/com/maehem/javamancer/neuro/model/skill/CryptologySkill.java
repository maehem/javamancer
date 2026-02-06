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
import java.util.logging.Level;

/**
 * Cryptology. Input coded words to get decoded passwords.
 *
 * <pre>
 * Obtained from: Shiva in the Gentlemen Loser by asking about “Cryptology”.
 *
 * Upgrade: To a max level of 4 by Julius Deane or at the INSA database.
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class CryptologySkill extends Skill {

    public CryptologySkill(int level) {
        super(Item.Catalog.CRYPTOLOGY, level, 4);
    }

    @Override
    public void use() {
        LOGGER.log(Level.INFO, "Use Skill: {0}", new Object[]{catalog.itemName});
        // Should invoke a popup for password to decode.
    }

    @Override
    public String getDescription() {
        return "Decode encoded passwords.";
    }

    private final CryptWord[][] WORD_LIST = new CryptWord[][]{
        { // Cryptology Level 1
            new CryptWord("PANCAKE", "VENDORS"), // Asano lvl.2
            new CryptWord("DUMBO", "ROMCARDS"), // Fuji lvl.1
            new CryptWord("TURNIP", "LOSER"), // Loser lvl.2         
        },
        { // Cryptology Level 2
            new CryptWord("ABURAKKOI", "UCHIKATSU"), // Fuji lvl.2
            new CryptWord("SMEEGLDIPO", "PERMAFROST"), // SEA lvl.2
            new CryptWord("SNORSKEE", "SUPERTAC"), // Chiba Police lvl.2 
            new CryptWord("EGGPLANT", "LONGISLAND"), // Fission Auth. lvl.1
            new CryptWord("GALILEO", "APOLLO"), // NASA lvl.1
        },
        { // Cryptology Level 3
            new CryptWord("VULCAN", "BIOSOFT"), // Hosaka lvl.2
            new CryptWord("AGABATUR", "EINTRITT"), // Bank Gemeinschaft lvl.1
            new CryptWord("EINHOVEN", "VERBOTEN"), // Bank Gemeinschaft lvl.2
        },
        { // Cryptology Level 4
            new CryptWord("PLEIADES", "SUBARU"), // Musabori lvl.2
        }
    };

    /**
     * Decode an encrypted word/password.
     *
     * @param encryptedWord
     * @return decrypted word, blank string if not known, or null if not
     * sufficient level.
     */
    public String decode(String encryptedWord) {
        String plainWord = "";
        int foundLevel = -1;
        for (int i = 0; i < 4; i++) {
            for (CryptWord cw : WORD_LIST[i]) {
                if (encryptedWord.equalsIgnoreCase(cw.encrypted)) { // Match
                    foundLevel = i;
                    plainWord = cw.plain;
                    i = 5; // Cause end of for-loop.
                    break; // Break this loop.
                }
            }
        }

        if (level < (foundLevel + 1)) { // Class level is 1-4.
            return null; // Word found but Not sufficient Skill.
        }

        if (plainWord.isBlank()) {
            return randomPassword();
        }

        return plainWord; // blank or plain word if it was found.    
    }

    /**
     * Generate random 10 char password.
     *
     * @return
     */
    private String randomPassword() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(Character.toString((int) (Math.random() * (126 - 32) + 32)));
        }

        return sb.toString();
    }

    private class CryptWord {

        public String encrypted;
        public String plain;

        public CryptWord(String crypt, String plain) {
            this.encrypted = crypt;
            this.plain = plain;
        }
    }
}
