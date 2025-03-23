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

import de.hhn.it.devtools.components.passGen.provider.SimplePasscodeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPasscodeGeneratorBadCases {
  private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TestPasscodeGeneratorBadCases.class);
  SimplePasscodeGenerator simplePasscodeGenerator;

  @BeforeEach
  void setup() {
    simplePasscodeGenerator = new SimplePasscodeGenerator();
  }

  @Test
  @DisplayName("Test creation of passcode with null reference")
  public void testPasscodeGenerationWithWrongLength() {
    final int falseLength = 10;
    //normal usage of passcode generator does not directly call generatePasscode() - only test purpose
    int generatedPasscode = simplePasscodeGenerator.generatePasscode(10).length();
    logger.debug(System.out.format("Inputted length [ %d ] and generated passcode length [ %d ] ", falseLength, generatedPasscode).toString());
    assertFalse(falseLength == generatedPasscode);
  }

  @Test
  @DisplayName("Test creation of passcode with negative length")
  public void testPasscodeGenerationWithNegativeLength() {
    final int falseLength = -1;
    //normal usage of passcode generator does not directly call generatePasscode() - only test purpose
    int actualLength = simplePasscodeGenerator.generatePasscode(falseLength).length();
    logger.debug(System.out.format("Input length [ %d ] and actual passcode length [ %d ] ", falseLength, actualLength).toString());
    assertFalse(falseLength == actualLength);
  }

  @Test
  @DisplayName("Test creation of passcode with zero length")
  public void testPasscodeGenerationWithZeroLength() {
    final int falseLength = 0;
    //normal usage of passcode generator does not directly call generatePasscode() - only test purpose
    //calling createPasscode() will ensure such scenarios get filtered while creation of passcode
    int actualLength = simplePasscodeGenerator.generatePasscode(falseLength).length();
    logger.debug(System.out.format("Input length [ %d ] and actual passcode length [ %d ] ", falseLength, actualLength).toString());
    assertFalse(falseLength == actualLength);
  }

  @Test
  @DisplayName("Test creation of passcode with null reference")
  public void testPasscodeGenerationWithNullReference() {
    final Integer falseLength = null;
    logger.debug(System.out.format("Input of Integer value [ %d ] should cause a NullPointerException", falseLength).toString());
    NullPointerException exception = assertThrows(NullPointerException.class, () -> simplePasscodeGenerator.generatePasscode(falseLength));
  }
}