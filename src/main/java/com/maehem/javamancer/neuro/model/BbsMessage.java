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
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class BbsMessage {

    public static final Logger LOGGER = Logging.LOGGER;

    public final int dbNumber; // 0 == PAX
    public String date;
    public final String to;
    public final String from;
    public String body;
    public int prefillIndex;
    public boolean show;

    public static final int defaultShow = 3; // Default show articles 0..3

    /**
     * For game time sent or generated messages.
     *
     * @param date
     * @param to
     * @param from
     * @param body
     * @param show
     */
    public BbsMessage(int dbNumber, String date, String to, String from, String body, boolean show) {
        this.dbNumber = dbNumber;
        this.date = date;
        this.to = to;
        this.from = from;
        this.body = body;
        this.prefillIndex = -1;
        this.show = show;
    }

    /**
     * For static messages from game content files.
     *
     * @param date
     * @param to
     * @param from
     * @param index
     * @param show
     */
    public BbsMessage(String date, String to, String from, int index, boolean show) {
        this.dbNumber = -1;
        this.date = date;
        this.to = to;
        this.from = from;
        this.prefillIndex = index;
        this.show = show;
    }

    public String toListString(String playerName) {
        String fromStr = from.replace("\1", playerName);
        String toStr;
        if (to != null) {
            toStr = to.replace("\1", playerName);
            toStr = String.format("%-13s", toStr);
        } else {
            toStr = "";
        }
        return date + " " + toStr + " " + fromStr;
    }

    /**
     *
     * @param prefix
     * @param p
     */
    public void putProps(String prefix, Properties p) {
        p.put(prefix + ".dbNumber", String.valueOf(dbNumber));
        p.put(prefix + ".date", date);
        p.put(prefix + ".to", to);
        p.put(prefix + ".from", from);
        p.put(prefix + ".body", body);
        p.put(prefix + ".index", String.valueOf(prefillIndex));
        p.put(prefix + ".show", String.valueOf(show));
    }

    /**
     * Prefix is in the form example: "messages.0"
     *
     * @param prefix
     * @param p
     * @return
     */
    public static BbsMessage pullMessage(String prefix, Properties p) {
        int dbNumber = Integer.parseInt(p.getProperty(prefix + ".dbNumber", "999"));
        String date = p.getProperty(prefix + ".date", "ERROR");
        String to = p.getProperty(prefix + ".to", "ERROR");
        String from = p.getProperty(prefix + ".from", "ERROR");
        String body = p.getProperty(prefix + ".body", "ERROR");
        int prefillIndex = Integer.parseInt(p.getProperty(prefix + ".index", "-1"));
        boolean show = Boolean.parseBoolean(p.getProperty(prefix + ".show", "false"));
        LOGGER.log(Level.SEVERE, "Restore Message: {0} :: {1}", new Object[]{dbNumber, to});

        BbsMessage bbsMessage = new BbsMessage(dbNumber, date, to, from, body, show);
        bbsMessage.prefillIndex = prefillIndex;

        return bbsMessage;
    }

}
