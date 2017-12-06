package wireframe;

/**
 *A Slider element represents a horizontal slider in a wireframe.
 * The progress of the Slider can be adjusted.
 *
 */
public class Slider extends AbstractWireframeElement{

	private int progress = 0;
	
	/**Creates a Slider at specified location with default progress 0%.
	 * @param location the location to place the Slider
	 */
	Slider(Location location) {
		super(location);
		setType(Enums.ElementType.SLIDER);
	}
	
	/**Returns the percent progress of this Slider.
	 * @return the percent progress of this Slider
	 */
	final int getProgress() {
		return progress;
	}

	/**Sets the progress of this Slider to a percent between 0 and 100, otherwise does nothing.
	 * @param progress the percent to set to, between 0 and 100
	 */
	final void setProgress(int progress) {
		if(progress >= 0 && progress <= 100){
			this.progress = progress;
		}
	}

}
