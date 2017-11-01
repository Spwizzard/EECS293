package pic;

import java.util.List;

import static pic.Constants.DEFAULT_LOG_FILEPATH;

/**
 * Driver class for analyzing problems of the type described in EECS 293 PA10.
 *
 * Usage:
 * ant run -Darg0 <problem_filename>
 *
 * Positional:
 *
 * problem_filename     -   text file containing a problem input of the type in PA10. Should
 *                          be relative to the pictures project root.
 *
 * This class handles all the user input and IO for the pictures module.
 *
 * Created by Brian on 11/10/2016.
 */
public class PictureDriver {

    //////////////////////////////////
    // MAIN
    //////////////////////////////////

    /**
     * Read an input problem filename from stdin, analyze the problem, write a summary to stdout.
     *
     * Also logs more detailed errors to log/log.txt
     *
     * @param args java CL arguments
     */
    public static void main(String[] args){

        String inputFilename = parseFilenameFromArgs(args);
        ErrorHandler.setupErrorLog(DEFAULT_LOG_FILEPATH);

        PictureDriver driver = new PictureDriver();
        String result = driver.runPictureAnalysis(inputFilename);

        System.out.println(result);
    }


    //////////////////////////////////
    // INTERFACE
    //////////////////////////////////

    /**
     * Analyze the picture problem possibly contained in inputFilename and returns
     * a String description of the result.
     * @param inputFilename filename of a pictures problem text file
     * @return the subpicture ordering if it exists, or the string 'error' otherwise
     */
    String runPictureAnalysis(String inputFilename){

        try {
            PictureOrdering ordering = calculateOrdering(inputFilename);
            return ordering.toString();
        }
        catch(PictureException e){
            ErrorHandler.logError(e);
            System.out.println(e.getMessage());
            return Constants.DEFAULT_FAILURE_MESSAGE;
        }
    }


    //////////////////////////////////
    // HELPERS
    //////////////////////////////////

    /**
     * Produce a PictureOrdering from the pictures problem contained in inputFilename.
     *
     * If the problem is incorrectly formatted in the text file, this method will raise
     * a PictureException.
     *
     * If the problem is properly stated but does not have a well defined solution, this
     * method will return a PictureOrdering indicating an error state.
     *
     * @param inputFilename filename of a pictures problem text file
     * @return a PictureOrdering of the input problem
     * @throws PictureException if the input file is improperly formatted
     */
    private PictureOrdering calculateOrdering(String inputFilename) throws PictureException{

        PictureReader reader = new PictureReader();

        List<String> inputLines = reader.inputLines(inputFilename);

        PictureInput input = PictureInput.inputOf(inputLines);

        return PictureOrdering.from(input);
    }

    /**
     * This method represents a decision to go with a lightweight approach to argument
     * parsing. There's only one argument that accepts any string value, so it doesn't
     * need to be all that rigorous.
     * @param args String[] of java CL arguments
     * @return the first argument
     */
    private static String parseFilenameFromArgs(String[] args){
        if(args.length != 1){
            throw new IllegalArgumentException("Must provide exactly one argument");
        }
        return args[0];
    }
}
