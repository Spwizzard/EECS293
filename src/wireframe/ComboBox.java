package wireframe;

import java.util.ArrayList;

/**
 * A ComboBox element is a box in a wireframe that holds multiple options that can be selected.
 *
 */
public class ComboBox extends AbstractWireframeElement {

	private ArrayList<String> options = new ArrayList<String>();
	
	/**Creates a ComboBox at specified location with no default options
	 * @param location the location to be placed at
	 */
	ComboBox(Location location) {
		super(location);
		setType(Enums.ElementType.COMBO_BOX);
	}

	/**Adds an option to the ComboBox
	 * @param option the String representing the option to add
	 */
	void addOption(String option){
		if(option != null){
			options.add(option);
		}	
	}
	
	/**Removes an optino from the ComboBox, if it exist
	 * @param option the String representing the option to remove
	 */
	void removeOption(String option){
		if(option != null){
			options.remove(option);
		}	
	}
	
	/**Returns the list of options that this ComboBox holds
	 * @return the list of options that this ComboBox holds
	 */
	final ArrayList<String> getOptions() {
		return options;
	}
	
	

}
