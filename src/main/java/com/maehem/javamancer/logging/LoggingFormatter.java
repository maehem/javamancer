/*
    Licensed to the Apache Software Foundation (ASF) under one
    or more contributor license agreements.  See the NOTICE file
    distributed with this work for additional information
    regarding copyright ownership.  The ASF licenses this file
    to you under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

 */
package com.maehem.javamancer.logging;

import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 *
 * @author maehem
 */
public class LoggingFormatter extends Formatter {

    private static final String FORMAT = "[%1$tF %1$tT] [%2$-7s] %3$s      <==== %4$s %n";

    @Override
    public synchronized String format(LogRecord lr) {
        // Must process lr message and parameters before final formatting.
        String result = "";
        if (lr.getMessage() != null) {
            result = MessageFormat.format(
                    lr.getMessage(), lr.getParameters()
            );
        }

        //return lr.getMessage();
        return String.format(FORMAT,
                new Date(lr.getMillis()),
                lr.getLevel().getLocalizedName(),
                result,
                lr.getSourceClassName()
        );
    }
};
