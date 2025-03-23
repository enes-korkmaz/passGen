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

package de.hhn.it.devtools.components.passGen;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.components.passGen.provider.SimplePasscodeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPasscodeGeneratorGoodCases {
  private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TestPasscodeGeneratorBadCases.class);
  SimplePasscodeGenerator simplePasscodeGenerator;

  @BeforeEach
  void setup() {
    simplePasscodeGenerator = new SimplePasscodeGenerator();
  }


  @Test
  @DisplayName("Test if passcode generator generates a passcode")
  public void testGeneratePasscode() throws IllegalParameterException {
    int passcode = simplePasscodeGenerator.createPasscode();
    logger.info("The generated passcode is ad follows: " + passcode);
    assertTrue(passcode > 0);
  }

  @Test
  @DisplayName("Test if passcode generator generates a passcode with default length")
  public void testGeneratePasscodeWithDefaultLength() throws IllegalParameterException {
    final int defaultLength = 6;
    int generatedPasscode = simplePasscodeGenerator.createPasscode();
    String stringifiedPasscode = String.valueOf(generatedPasscode);
    logger.info("The generated passcode is as follows: " + stringifiedPasscode);
    assertEquals(defaultLength, stringifiedPasscode.length());
  }
}