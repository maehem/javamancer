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
public class BankTransaction {
    public static final Logger LOGGER = Logging.LOGGER;

    public enum Operation {
        Download, Upload, Fine, TransferIn, TransferOut
    }

    public final String date;
    public final Operation op;
    public final int amount;

    public BankTransaction(String date, Operation op, int amount) {
        this.date = date;
        this.op = op;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return date + " " + String.format("%-11s", op.name()) + " " + String.format("%1$10s", amount);
    }

    /**
     *
     * @param prefix
     * @param p
     */
    public void putProps(String prefix, Properties p) {
        p.put(prefix + ".date", date);
        p.put(prefix + ".op", op.name());
        p.put(prefix + ".amount", String.valueOf(amount));
    }

    public static BankTransaction pullTransaction(String prefix, Properties p) {
        String date = p.getProperty(prefix + ".date", "ERROR");
        String op = p.getProperty(prefix + ".op", "ERROR");
        int amount = Integer.parseInt(p.getProperty(prefix + ".amount", "ERROR"));
        LOGGER.log(Level.SEVERE, "Restore Bank Transaction: {0} :: {1}", new Object[]{date, op});

        return new BankTransaction(date, lookup(op), amount);
    }

    private static Operation lookup(String opName) {
        for (Operation op : Operation.values()) {
            if (opName.equals(op.name())) {
                return op;
            }
        }

        return null;
    }

}
