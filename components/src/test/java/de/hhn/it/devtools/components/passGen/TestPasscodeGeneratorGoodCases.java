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