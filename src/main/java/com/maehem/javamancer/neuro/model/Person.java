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
package com.maehem.javamancer.neuro.model;

import com.maehem.javamancer.logging.Logging;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class Person {

    public static final Logger LOGGER = Logging.LOGGER;

    public static final int MAX_NAME_LENGTH = 18;
    public static final int MAX_ID_LENGTH = 9;

    private String name;
    private String bama;
    private String auxData;

    public Person(String name, String bama, String auxData) {
        this.name = name;
        this.bama = bama;
        this.auxData = auxData;
    }

    /**
     * Create from TAB separated text line NAME<tab>NUMBER<TAB>data
     *
     * @param resourceString
     */
    public Person(String resourceString) {
        String[] split = resourceString.split("\t");
        name = split[0];
        bama = split[1];
        auxData = split[2];
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the BAMA ID
     */
    public String getBama() {
        return bama;
    }

    /**
     * @param bama the BAMA ID to set
     */
    public void setBama(String bama) {
        this.bama = bama;
    }

    /**
     * For WARRANT info in auxData.
     *
     * @return reason.
     */
    public int getReason() {
        return auxData.charAt(3);
    }

    public String getAuxData() {
        return auxData;
    }

    public static final boolean isValidNameCharacter(byte code) {
        // Some non-allowed
        if (code == '\\') {
            return false; // No Back-slash ? Test me.
        }
        //Otherwise any printable ASCII char. is OK.
        return (code >= 32 && code <= 126);
    }

    /**
     * aux is stored as string of readable/editable hex values. But stored
     * in-game as byte char values for raw flag detection/manipulation.
     *
     * <pre>
     * Example:  In-game aux raw chars:  <null><null><(byte)34><null>.
     * Example:  In-vae file :  aux=00003400  <== in properties file.
     * </pre>
     *
     * @param prefix
     * @param p
     */
    public void putProps(String prefix, Properties p) {
        p.put(prefix + ".name", name);
        p.put(prefix + ".bama", bama);

        byte[] bytes = auxData.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
        String hexOutput = HexFormat.of().formatHex(bytes);

        p.put(prefix + ".aux", hexOutput);
    }

    public static Person pullPerson(String prefix, Properties p) {
        String name = p.getProperty(prefix + ".name", "ERROR");
        String bama = p.getProperty(prefix + ".bama", "ERROR");

        String hexAuxInput = p.getProperty(prefix + ".aux", "00000000");
        String aux = new String(
                HexFormat.of().parseHex(hexAuxInput),
                StandardCharsets.US_ASCII
        );

        LOGGER.log(Level.FINE, "Restore Person: {0} :: {1}  aux: {2}", new Object[]{name, bama, hexAuxInput});

        return new Person(name, bama, aux);
    }

}
