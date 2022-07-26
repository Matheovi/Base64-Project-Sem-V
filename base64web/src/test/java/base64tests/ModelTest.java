package base64tests;

import pl.polsl.lab.wiatrok.mateusz.model.Model;
import pl.polsl.lab.wiatrok.mateusz.model.IllegalCharactersException;
import pl.polsl.lab.wiatrok.mateusz.model.WrongParameterException;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.Assert.*;

/**
 * Testing various methods in Model class
 *
 * @author Mateusz Wiatrok
 * @version 1.0
 */
public class ModelTest {

        /**
         * model class that we test
         */
        private Model model;

        /**
         * each time test is run, new model instance is being created
         */
        @BeforeEach
        public void setUp() {
                model = new Model();
        }

        /**
         * parametrized test testing all possible string combinations for encryption
         * @param providedInput input that we feed into the program (in normal)
         * @param expectedOutput we expect the string after the function executes. it should be encoded message
         * @param option if we want to decode or encode
         */
        @ParameterizedTest
        @CsvSource({"abc,YWJj,1", "abcd,YWJjZA==,1", "abc d,YWJj ZA==,1", "abc def,YWJj ZGVm,1"})
        void testEncryptionMethod(String providedInput, String expectedOutput, String option) {
                model.setOption(Integer.parseInt(option));
                assertEquals(expectedOutput + " ", model.processStrings(providedInput), "Result is different from expected");
        }

        /**
         * parametrized test testing all possible string combinations for decryption
         * @param providedInput input that we feed into the program (in base64)
         * @param expectedOutput we expect the string after the function executes. it should be decoded message
         * @param option if we want to decode or encode
         */
        @ParameterizedTest
        @CsvSource({"YWJj,abc,2", "YWJjZA==,abcd,2", "YWJj ZA==,abc d,2"})
        void testDecryptionMethod(String providedInput, String expectedOutput, String option) {
                model.setOption(Integer.parseInt(option));
                assertEquals(expectedOutput + " ", model.processStrings(providedInput), "Result is different from expected");
        }


        /**
         * testing decoding 4bytes * 6 bits into 3bytes * 8 bits
         */
        @Test
        void testDecode4() {
                int input = 0x00FB1C4D;
                byte[] tested = model.decode4(input);
                assertEquals(tested[0], (byte) 0xFB);
                assertEquals(tested[1], (byte) 0x1C);
                assertEquals(tested[2], (byte) 0x4D);
        
        }

        /**
         * testing raw encoding function.
         */
        @Test
        void testEncoding() {
                String input = "zzz";
                assertEquals("enp6", model.encode(input));
                input = "!cv";
                assertEquals("IWN2", model.encode(input));
                input = "alamakota";
                assertEquals("YWxhbWFrb3Rh", model.encode(input));
                input = "justyou";
                assertEquals("anVzdHlvdQ==", model.encode(input));
                input = "k";
                assertEquals("aw==", model.encode(input));
        }

        /**
         * testing raw decoding function.
         */
        @Test
        void testDecoding() {
                String input = "R1JD";
                assertEquals("GRC", model.decode(input));
                input = "enp6";
                assertEquals("zzz", model.decode(input));
                input = "IWN2";
                assertEquals("!cv",model.decode(input));
                input = "YWxhbWFrb3Rh";
                assertEquals("alamakota", model.decode(input));
                input = "anVzdHlvdQ==";
                assertEquals("justyou", model.decode(input));
                input = "aw==";
                assertEquals("k", model.decode(input));
                
        }

        /**
         * here we test only invalid arguments passed to the program. If there is exception, catch block allows to exit test successfully
         */
        @Test
        void testInvalidArguments() {
            try{
                String [] args = {"-c", "-z", "YWJjZA=="};
                model.handleArguments(args);
                fail("exception was not met");
            }catch(WrongParameterException exception)
            {
                
            }
        }
        
        /**
         * test only valid arguments passed to the program. If exception, it fails.
         */
        @Test
        void testValidArguments() {
            try{
                String [] args = {"-d", "-t", "YWJjZA=="};
                model.handleArguments(args);
            }catch(WrongParameterException exception)
            {
                fail("exception shouldn't be executed");
            }
        }
        
        
        /**
         * test only illegal characters passed as input string.
         */
        @Test
        void testIllegalCharacters() {
            try{
                String string = "*&^%$#@!";
                model.isIllegal(string);
                fail("exception was not met");
            }catch(IllegalCharactersException illegalChar)
            {
                
            }
        }
            
        /**
         * test only legal characters passed as input string.
         */
        @Test
        void testLegalCharacters()
        {
            try
            {
            String string = "aAbB17690/_- ";
                    model.isIllegal(string);
        }catch(IllegalCharactersException illegalChar)
            {
                fail("legal characters shouldnt make exception");
            }
        }

}
