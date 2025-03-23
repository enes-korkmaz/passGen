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

package de.hhn.it.devtools.components.passGen.provider;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.apis.passGen.PasscodeGenerator;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple passcode generator that generates a random passcode of a given length.
 */
public class SimplePasscodeGenerator implements PasscodeGenerator {
  private final ArrayList<String> tabooSyllables;
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(SimplePasscodeGenerator.class);

  /**
   * Constructs a new SimplePasscodeGenerator with default prefixes and suffixes.
   */
  public SimplePasscodeGenerator() {
    // List of number combinations that are not allowed to be in generated passcode.
    tabooSyllables = new ArrayList<>(Arrays.asList("000", "111", "222", "333", "444",
        "555", "666", "777", "888", "999"));
  }

  /**
   * Creates a new passcode.
   *
   * @return the generated passcode
   * @throws IllegalParameterException if the passcode is invalid
   */
  @Override
  public int createPasscode() throws IllegalParameterException {
    String stringNumbers;
    boolean checkSyllables = false;
    do {
      stringNumbers = generatePasscode(DEFAULT_LENGTH);

      for (String syllables : tabooSyllables) {
        if (stringNumbers.contains(syllables)) {
          checkSyllables = true;
          break;
        }
      }
    } while (stringNumbers.length() != DEFAULT_LENGTH && checkSyllables);

    return Integer.parseInt(stringNumbers);
  }

  /**
   * Creates a new passcode of the specified length.
   *
   * @param length is the length of the passcode
   * @return the generated passcode as a String
   */
  public String generatePasscode(int length) {
    String newNumbers = "";
    double temp = Math.random() * Math.pow(10, length);
    newNumbers += String.valueOf(temp);
    newNumbers = newNumbers.replace(".", "");
    // cropping the passcode if it is longer than 6 digits
    newNumbers = newNumbers.substring(0, DEFAULT_LENGTH);

    return newNumbers;
  }

  //  /**
  //   * Main method for testing the passcode generator.
  //   *
  //   * @param args command line arguments
  //   */
  //  public static void main(String[] args) throws IllegalParameterException {
  //    SimplePasscodeGenerator passGen = new SimplePasscodeGenerator();
  //    try {
  //      int v = 8;
  //      int p = 0;
  //      while (p < 100) {
  //        System.out.println("Input length: " + v + " - new passcode: "
  //        + passGen.createPasscode());
  //        p++;
  //      }
  //    } catch (IllegalParameterException e) {
  //      throw new IllegalParameterException(e.getMessage());
  //    }
  //  }
}