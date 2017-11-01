package pic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Handy class to intelligently log errors.
 *
 * Most common usage: set an error file at beginning of program, then log to it
 * whenever you have an exception you want to record.
 *
 * Created by Brian on 11/10/2016.
 */
class ErrorHandler {

    //////////////////////////////////
    // FIELDS
    //////////////////////////////////

    private static final Logger LOGGER = Logger.getLogger(PictureLogger.class.getName());


    //////////////////////////////////
    // INTERFACE
    //////////////////////////////////

    static void setupErrorLog(File errorFile){
        try {
            System.setErr(new PrintStream(new FileOutputStream(errorFile)));
        }
        catch(IOException e){
            System.setErr(System.err);
        }
    }

    static void logError(PictureException e){
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }
}
