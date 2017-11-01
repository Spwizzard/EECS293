package pic;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class representing a solution to a pictures problem.
 *
 * A well formed pictures problem input WILL have a PictureOrdering. That is to say,
 * a PictureOrdering can represent both a successful ordering of input pictures OR
 * the scenario that there is no possible ordering.
 *
 * That is why the from method does not throw a PictureException. By the time we
 * have a PictureInput object, we know that we will be able to get a PictureOrdering
 * object of this description.
 *
 * In either case, the toString method will produce a succinct description of
 * the solution.
 *
 * Created by Brian on 11/10/2016.
 */
class PictureOrdering {

    //////////////////////////////////
    // FIELDS
    //////////////////////////////////

    private final List<Character> removed;
    private final List<SubPicture> toRemove;
    private final PictureInput input;
    private final CompositePicture composite;
    private final int originalNumberOfInputPictures;


    //////////////////////////////////
    // CONSTRUCTORS
    //////////////////////////////////

    private PictureOrdering(final PictureInput input) {
        this.input = input;
        this.toRemove = input.getInputPictures();
        this.removed = new ArrayList<>();
        this.composite = input.getCompositePicture();
        this.originalNumberOfInputPictures = input.numberOfInputPictures();
    }

    static PictureOrdering from(final PictureInput input){
        PictureOrdering ordering = new PictureOrdering(input);
        ordering.order();
        return ordering;
    }


    //////////////////////////////////
    // INTERFACE
    //////////////////////////////////

    @Override
    public String toString() {
        return isSuccess() ? orderString() : Constants.DEFAULT_FAILURE_MESSAGE;
    }


    //////////////////////////////////
    // HELPERS
    //////////////////////////////////

    private void order(){

        Optional<SubPicture> nextPictureToRemove;

        while(!isSuccess()){
            nextPictureToRemove = nextPictureToRemove();
            if(nextPictureToRemove.isPresent()){
            	nextPictureToRemove.ifPresent(this::remove);
            }
            else{
            	break;
            }
            
        }
    }

    /**
     * Finds next picture to remove. Specifies there must be one and only one next option.
     * @return Optional containing next picture to remove if one can be deduced
     */
    private Optional<SubPicture> nextPictureToRemove(){

        // possible pictures to remove
        List<SubPicture> removalCandidates = toRemove
                .stream()
                .filter(removalCandidate -> !composite.isSubPictureObstructed(removalCandidate, removed))
                .collect(Collectors.toList());
        if(removalCandidates.size() != 1){
        	//There are either no candidates or multiple candidates! 
        	//Either way, something is wrong and we can't figure out the solution
        	return Optional.empty();    	
        }        
        return Optional.of(removalCandidates.get(0));
    }

    /**
     * The PictureOutput currently represents a solution to the input
     * @return true if success, false otherwise
     */
    private boolean isSuccess() {
        return removed.size() == originalNumberOfInputPictures;
    }

    private void remove(final SubPicture subPicture){
        removed.add(0,subPicture.getOverallCharacter());
        toRemove.remove(subPicture);
    }

    private String orderString() {
        StringBuilder builder = new StringBuilder();
        for(Character c : removed){
            builder.append(c);
        }
        return builder.toString();
    }
}
