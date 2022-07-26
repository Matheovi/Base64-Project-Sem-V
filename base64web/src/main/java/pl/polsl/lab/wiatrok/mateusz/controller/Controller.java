/** Controller controls flow from view to model
 *
 */
package pl.polsl.lab.wiatrok.mateusz.controller;

import pl.polsl.lab.wiatrok.mateusz.view.View;
import pl.polsl.lab.wiatrok.mateusz.model.Model;
import pl.polsl.lab.wiatrok.mateusz.model.WrongParameterException;
import pl.polsl.lab.wiatrok.mateusz.model.IllegalCharactersException;

/**
 * Class manager, connects view and model in organized way.
 *
 * @author Mateusz Wiatrok
 * @version 1.0
 */
public class Controller {

        /**
         * Model object
         */
        final private Model model;

        /**
         * View object
         */
        final private View view;
        //final private UserUI newUI;

        /**
         * Default Controller class constructor
         */
        public Controller() {
                this.model = new Model();
                this.view = new View();
                //this.newUI = new newUI();
                this.view.setVisible(true);
        }

        /**
         * Core function that runs all other functions
         *
         * @param args passed arguments from a command line
         */
        public void run(String[] args) {
                view.getEncodeButton().addActionListener(e -> eval(1));
                view.getDecodeButton().addActionListener(e -> eval(2));
                }
        
        
        /**
         * Function running after pressing a button in GUI
         * @param choice this parameter responsible for choice, encoding or decoding
         */
        public void eval(int choice)
        {
                model.setOption(choice);
                String rawString = view.takeInputFromUser();
                model.setURL(view.getMenuItem().getState());
                try {model.isIllegal(rawString);}
                catch(IllegalCharactersException exception){view.printError(exception); return;}
                String toDisplay = model.processStrings(rawString);
                view.display(toDisplay);
                view.printLogToTable();
        }
}
