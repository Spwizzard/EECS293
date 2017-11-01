package pic;

import java.io.File;
import java.util.regex.Pattern;

/**
 * Project-wide constant values.
 *
 * honestly, some of these should probably be moved; some aren't really project wide...
 *
 * Created by Brian on 11/10/2016.
 */
class Constants {

    //////////////////////////////////
    // GENERAL
    //////////////////////////////////

    static final int REQUIRED_NUMBER_OF_INPUT_LINES = 5;
    static final int REQUIRED_NUMBER_OF_CHUNKS = 2;
    static final File DEFAULT_LOG_FILEPATH = new File("log/log.txt");


    //////////////////////////////////
    // PATTERN MATCHING
    //////////////////////////////////

    static final Pattern DIMENSION_PATTERN = Pattern.compile("\\d+");


    //////////////////////////////////
    // ERROR MESSAGES
    //////////////////////////////////

    static final String DEFAULT_FAILURE_MESSAGE = "error";
    static final String INVALID_HEIGHT_MSG = "Invalid height input";
    static final String INVALID_WIDTH_MSG = "Invalid weight input";

}
