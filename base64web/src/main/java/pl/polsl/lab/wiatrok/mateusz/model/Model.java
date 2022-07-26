/**Model handles data in program and manipulates it.
 * 
 */
package pl.polsl.lab.wiatrok.mateusz.model;

import java.util.ArrayList;
import java.util.List;
import static java.util.Collections.*;
import pl.polsl.lab.wiatrok.mateusz.annotations.URLsafe;
import java.lang.reflect.*;
import java.util.*;

/*
('A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9', '+', '/')
*/

/**
 * Class responsible for logic, and coding/decoding base64 algorithms
 *
 * @author Mateusz Wiatrok
 * @version 1.0
 */
public class Model {

    /**
     * List of characters in base64 encoding. Index of one element is at the
     * same time 6-bit value of this element
     */
    private final List<Character> base64items;

    /**
     * List of characters in base64-URL safe encoding. Index of one element is at the
     * same time 6-bit value of this element.
     * Additionaly + and / chars were substituted with - and / characters.
     */
    @URLsafe
    private final List<Character> base64itemsURL;
    
    /**
     * Boolean value necessary for choosing correct char array in decoding or encoding algorithm
     */
    private boolean urlsafe = false;
    
    /**
     * Option for user to choose a algorithm
     */
    private int option;
    
    /**
     * input from Standard input stream.
     */
    private String rawinput = "";
   
    /**
     * boolean checking if my annotation is present
     */
    private boolean myAnnotationCheck = false;
    

    /**
     * default Model class constructor
     */
    public Model() {
            Character[]creator={'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9', '+', '/'};
        this.base64items = new ArrayList<>();
        addAll(base64items, creator);
        
        this.base64itemsURL = new ArrayList<>();
        addAll(base64itemsURL, creator);
        replaceAll(base64itemsURL, '+', '-');
        replaceAll(base64itemsURL, '/', '_');
        
        
        
        try{
        Class c = Class.forName("pl.polsl.lab.wiatrok.mateusz.model.Model");
        Field f = c.getField("base64itemsURL");
        myAnnotationCheck = f.isAnnotationPresent(URLsafe.class);
        }catch(ClassNotFoundException cnf_e){}
        catch(NoSuchFieldException nsf_e){}
        
    }
    /**
     * Utility function, that shifts bits and masks bits, to copy value from one place to another.
     * @param from from which byte value should the function copy
     * @param mask mask indicates on which position there is 1 value
     * @param iters how many times should the loop iterate
     * @return result of masking
     */
    byte maskAndShift(byte from, byte mask, int iters) {
        byte result = (byte) 0;
        for (int j = 0; j < iters; j++) {
            result |= from & mask;
            mask = (byte) (mask >> 1);
        }
        return result;
    }


    /**
     * function taking 4* 6 bits in one 24 bit integer, and making them 3 * 8 bits
     * @param value integer that is evaluated
     * @return byte array. every element of array is a char in 8bit form
     */
    public byte[] decode4(int value) {
        byte[] bytes = new byte[3];
        byte mask = (byte) 0b00000001; 

        for (int i = 0; i < 8; i++) {
            bytes[2] |= value & mask;
            mask = (byte) (mask << 1);
        }
        mask = (byte) 0b00000001;
        value = value >> 8;
        for (int i = 0; i < 8; i++) {
            bytes[1] |= value & mask;
            mask = (byte) (mask << 1);
        }

        mask = (byte) 0b00000001;
        value = value >> 8;
        for (int i = 0; i < 8; i++) {
            bytes[0] |= value & mask;
            mask = (byte) (mask << 1);
        }

        return bytes;

    }

    
    /**
     * Main encoding logic
     * @param entry string to be encoded
     * @return string encoded in base64
     */
    public String encode(String entry) {
             
        String entrytext = entry;
        String output = "";
        byte[] temporary = new byte[3];
        byte mask = (byte) 0b10000000;
        byte result = (byte) 0;

        int howManyTriples = entry.length() / 3;
        int leftover = entry.length() % 3;
        for (int i = 0; i < howManyTriples * 3; i += 3) {
            temporary[0] = (byte) entrytext.charAt(i);
            temporary[1] = (byte) entrytext.charAt(i + 1);
            temporary[2] = (byte) entrytext.charAt(i + 2);

            mask = (byte) 0b10000000;
            result = (byte) 0;

            //MASK AND SHIFT
            result = maskAndShift(temporary[0], mask, 6);
            //SAVE
            result = (byte) (result >> 2);
            if (urlsafe && myAnnotationCheck) {
                output += base64itemsURL.toArray()[result];
            } else {
                output += base64items.toArray()[result];
            }

            //RESET
            result = (byte) 0;
            mask = (byte) 0b00000010;

            //MASK AND SHIFT
            result = maskAndShift(temporary[0], mask, 2);

            byte last_result = (byte) (result << 4);

            //RESET
            result = (byte) 0;
            mask = (byte) 0b10000000;

            //MASK AND SHIFT
            result = maskAndShift(temporary[1], mask, 4);
            //SAVE
            result = (byte) (result >> 4);
            result |= last_result | result;
            if (urlsafe && myAnnotationCheck) {
                output += base64itemsURL.toArray()[result];
            } else {
                output += base64items.toArray()[result];
            }

            //RESET
            result = (byte) 0;
            mask = (byte) 0b00001000;

            result = maskAndShift(temporary[1], mask, 4);

            //SAVE PREVIOUS
            last_result = (byte) (result << 2);

            //RESET
            result = (byte) 0;
            mask = (byte) 0b10000000;

            //MASK AND SHIFT
            result = maskAndShift(temporary[2], mask, 2);
            //SAVE
            result = (byte) (result >> 6);
            result |= last_result | result;
            if (urlsafe && myAnnotationCheck) {
                output += base64itemsURL.toArray()[result];
            } else {
                output += base64items.toArray()[result];
            }

            //RESET
            result = (byte) 0;
            mask = (byte) 0b00100000;

            //MASK AND SHIFT
            result = maskAndShift(temporary[2], mask, 6);
            //SAVE
            if (urlsafe && myAnnotationCheck) {
                output += base64itemsURL.toArray()[result];
            } else {
                output += base64items.toArray()[result];
            }

        }

        switch (leftover) {
            case 0:
                break;
            case 1:
                temporary[2] = (byte) entrytext.charAt(entrytext.length() - 1);
                result = (byte) 0;
                mask = (byte) 0b10000000;
                result = maskAndShift(temporary[2], mask, 6);
                result = (byte) (result >> 2);
                if (urlsafe && myAnnotationCheck) {
                    output += base64itemsURL.toArray()[result];
                } else {
                    output += base64items.toArray()[result];
                }

                mask = (byte) 0b00000010;
                result = (byte) 0;
                result = maskAndShift(temporary[2], mask, 2);
                result = (byte) (result << 4);
                if (urlsafe && myAnnotationCheck) {
                    output += base64itemsURL.toArray()[result];
                } else {
                    output += base64items.toArray()[result];
                }
                output += "==";

                break;
            case 2:
                temporary[1] = (byte) entrytext.charAt(entrytext.length() - 2);
                temporary[2] = (byte) entrytext.charAt(entrytext.length() - 1);
                result = (byte) 0;
                mask = (byte) 0b10000000;
                result = maskAndShift(temporary[1], mask, 6);
                result = (byte) (result >> 2);
                if (urlsafe && myAnnotationCheck) {
                    output += base64itemsURL.toArray()[result];
                } else {
                    output += base64items.toArray()[result];
                }

                mask = (byte) 0b00000010;
                result = (byte) 0;
                result = maskAndShift(temporary[1], mask, 2);
                result = (byte) (result << 4);
                byte lastResult = (byte) result;

                mask = (byte) 0b10000000;
                result = (byte) 0;
                result = maskAndShift(temporary[2], mask, 4);
                result = (byte) (result >> 4);
                result |= lastResult | result;
                if (urlsafe && myAnnotationCheck) {
                    output += base64itemsURL.toArray()[result];
                } else {
                    output += base64items.toArray()[result];
                }
                result = (byte) 0;
                mask = (byte) 0b00001000;
                result = maskAndShift(temporary[2], mask, 4);
                result = (byte) (result << 2);
                if (urlsafe && myAnnotationCheck) {
                    output += base64itemsURL.toArray()[result];
                } else {
                    output += base64items.toArray()[result];
                }
                output += "=";
        }

        return output;
    }

    /**
     * Main decoding logic
     * @param entry string to be decoded
     * @return string in normal ascii representation
     */
    public String decode(String entry) {

        String output = "";
        int elem1;
        int elem2;
        int elem3;
        int elem4;
        int x;
        byte[] bytes = new byte[3];

        for (int i = 0; i < (entry.length() / 4); i++) {

            elem1 = 0;
            elem2 = 0;
            elem3 = 0;
            elem4 = 0;
            if (entry.charAt(i * 4) != '=') {
                if (urlsafe && myAnnotationCheck) {
                    //elem1 = findIndex(base64itemsURL, s.charAt(i * 4));
                    elem1 = base64itemsURL.indexOf(entry.charAt(i*4));
                    
                } else {
                    //elem1 = findIndex(base64items, s.charAt(i * 4));
                    elem1 = base64items.indexOf(entry.charAt(i*4));
                }
            }
            if (entry.charAt(i * 4 + 1) != '=') {
                if (urlsafe && myAnnotationCheck) {
                    //elem2 = findIndex(base64itemsURL, s.charAt(i * 4 + 1));
                    elem2 = base64itemsURL.indexOf(entry.charAt(i*4 +1));
                } else {
                    //elem2 = findIndex(base64items, s.charAt(i * 4 + 1));
                    elem2 = base64items.indexOf(entry.charAt(i*4 +1));
                }

            }
            if (entry.charAt(i * 4 + 2) != '=') {
                if (urlsafe && myAnnotationCheck) {
                    //elem3 = findIndex(base64itemsURL, s.charAt(i * 4 + 2));
                    elem3 = base64itemsURL.indexOf(entry.charAt(i*4 + 2));
                } else {
                    //elem3 = findIndex(base64items, s.charAt(i * 4 + 2));
                    elem3 = base64items.indexOf(entry.charAt(i*4 + 2));
                }

            }
            if (entry.charAt(i * 4 + 3) != '=') {
                if (urlsafe && myAnnotationCheck) {
                    //elem4 = findIndex(base64itemsURL, s.charAt(i * 4 + 3));
                    elem4 = base64itemsURL.indexOf(entry.charAt(i*4 + 3));
                } else {
                    //elem4 = findIndex(base64items, s.charAt(i * 4 + 3));
                    elem4 = base64items.indexOf(entry.charAt(i*4 + 3));
                }

            }

//            System.out.println("elem1: " + elem1);
//            System.out.println("elem2: " + elem2);
//            System.out.println("elem3: " + elem3);
//            System.out.println("elem4: " + elem4);
//
//            System.out.println("elem1bin: " + Integer.toBinaryString(elem1));
//            System.out.println("elem2bin: " + Integer.toBinaryString(elem2));
//            System.out.println("elem3bin: " + Integer.toBinaryString(elem3));
//            System.out.println("elem4bin: " + Integer.toBinaryString(elem4));

//            System.out.println(Integer.toBinaryString((elem1 << 18) + (elem2 << 12) + (elem3 << 6) + elem4));
            x = (elem1 << 18) + (elem2 << 12) + (elem3 << 6) + elem4;
            //System.out.println(Integer.toBinaryString(x));

            bytes = decode4(x);

//            System.out.println(bytes[0]);
//            System.out.println(bytes[1]);
//            System.out.println(bytes[2]);

            for (int j = 0; j < 3; ++j) {
                    if(bytes[j] != 0)
                output += (char) bytes[j];
            }
        }
        return output;
    }

    /**
     * function Parsing command line arguments
     * @param args arguments from a command line 
     * @throws WrongParameterException when there is invalid parameter inputed
     */
    @Deprecated
    public void handleArguments(String[] args) throws WrongParameterException {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-u")) {
                urlsafe = true;
            } else if (args[i].equals("-e")) {
                option = 1;
            } else if (args[i].equals("-d")) {
                option = 2;
            } else if (args[i].equals("-t")) {
                for (int j = i + 1; j < args.length; j++) {
                    if (args[j].charAt(0) == '-') {
                        break;
                    }
                    this.rawinput = this.rawinput + args[j] + " ";
                }
                break;
            }
            if (args[i].charAt(0) == '-') {
                if (args[i].charAt(1) != 'u' && args[i].charAt(1) != 'e' && args[i].charAt(1) != 'd' && args[i].charAt(1) != 't') {
                    throw new WrongParameterException("incorrect arguments");
                }
            }

        }
        if(args.length == 0)
        {
        	throw new  WrongParameterException("no arguments");
        }
    }

    /**
     * Helper Function, that takes raw input string, and cuts words between space. Then every word is encoded/decoded.
     * @param string raw string to cut from
     * @return encoded/decoded string
     */
    public String processStrings(String string) {
        String[] input = string.trim().split("\\s+");
        String output = "";
        for (String item : input) {
            if (option == 1) {
                output += encode(item) + " ";
            }
            if (option == 2) {
                output += decode(item) + " ";
            }
        }
        return output;
    }

    /**
     * Function getting private option field.
     * @return private option field
     */
    public int getOption() {
        return option;
    }

    /**
     * Function setting option integer from user
     * @param option setter for option field
     */
    public void setOption(int option) {
    	this.option = option;
    }

    
    /**
     * Function setting url boolean
     * @param value boolean value
     */
    public void setURL(boolean value)
    {
            urlsafe = value;
    }

    /**
     * Function returning raw input
     * @return raw input
     */
    public String getInput() {
        return rawinput;
    }
    
    /**
     * Functon setting the input 
     * @param rawinput sets raw input field
     */
    public void setInput(String rawinput)
    {
        this.rawinput = rawinput;
    }
    
    /**
     * function checking if all characters to be decoded/encoded are legal
     * @param string string to be checked
     * @throws IllegalCharactersException if in input string are illegal characters
     */
    public void isIllegal(String string) throws IllegalCharactersException {
		  if(!string.matches("[0-9a-zA-Z\\d=+\\/ _-]+"))
      		throw new IllegalCharactersException("String is illegal to convert into/from base64");
    }
    
}