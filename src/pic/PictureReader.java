package pic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/**
 * Class to extract the text from a pictures problem text file.
 *
 * inputLines method will throw a PictureException if it can't read a List of
 * Strings from the file at the supplied filename.
 */
class PictureReader {

    //////////////////////////////////
    // INTERFACE
    //////////////////////////////////

    List<String> inputLines(final String filename) throws PictureException {

        Path inputPath = makePath(filename);

        try {
            return Files.readAllLines(inputPath);
        }
        catch(IOException e){
            return Collections.emptyList();
        }
    }


    //////////////////////////////////
    // HELPERS
    //////////////////////////////////

    private Path makePath(final String filename) throws PictureException {

        File file = makeFile(filename);

        try{
            return file.toPath();
        }
        catch(InvalidPathException e){
            throw new PictureException("Can't find file " + filename, e);
        }
    }

    private File makeFile(final String filename) throws PictureException {
        try{
            return new File(filename);
        }
        catch(NullPointerException e){
            throw new PictureException("Invalid null input filename", e);
        }
    }
}
