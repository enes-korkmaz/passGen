/**
 * This file is part of PassGen.
 *
 * Copyright (c) 2025 Enes Korkmaz, Nico Staudacher, Nadine Schoch and Nazanin Golalizadeh
 *
 * PassGen is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License Version 3 as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package de.hhn.it.devtools.apis.exceptions;

/**
 * This class represents an exception that is thrown when the user tries to generate
 * a token while still having an active one.
 */
public class TooManyTokensException extends Exception {
    private static final org.slf4j.Logger logger =
            org.slf4j.LoggerFactory.getLogger(TooManyTokensException.class);

    /**
     * Creates a new TooManyTokensException with the given message.
     *
     * @param message the message of the exception
     */
    public TooManyTokensException(String message) {
        super(message);
    }
}