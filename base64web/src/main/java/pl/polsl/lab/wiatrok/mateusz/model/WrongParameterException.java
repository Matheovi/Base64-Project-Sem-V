
package pl.polsl.lab.wiatrok.mateusz.model;

/**Exception class that is used when wrong parameter is passed in command line
 *
 * @author Mateusz Wiatrok
 * @version 1.0
 * 
 */
public class WrongParameterException extends Exception{
    
    
    /**
     * default constructor of my exception.
     * @param message message containing information what happened so that exception occurred
     */
    public WrongParameterException(String message)
    {
        super(message);
    }
    
    
}
