package wireframe;

import java.util.ArrayList;

public class ComboBox extends AbstractWireframeElement {

	private ArrayList<String> options = new ArrayList<String>();
	
	public ComboBox(Location location) {
		super(location);
		this.type = Enums.ElementType.COMBO_BOX;
	}

	void addOption(String option){
		if(option != null){
			options.add(option);
		}	
	}
	
	void removeOption(String option){
		if(option != null){
			options.remove(option);
		}	
	}
	
	final ArrayList<String> getOptions() {
		return options;
	}
	
	

}
