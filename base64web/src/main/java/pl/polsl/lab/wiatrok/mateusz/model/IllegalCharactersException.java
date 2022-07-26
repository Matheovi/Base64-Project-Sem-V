
package pl.polsl.lab.wiatrok.mateusz.model;

/**Exception class that is used when illegal characters are inputted to the decoder/encoder
 *
 * @author Mateusz Wiatrok
 * @version 1.0
 */
public class IllegalCharactersException extends Exception {
    
    
    /**
     * default constructor of my Exception
     * @param message message containing information what happened so that exception occurred
     */
    public IllegalCharactersException(String message)
    {
        super(message);
    }
    
}
