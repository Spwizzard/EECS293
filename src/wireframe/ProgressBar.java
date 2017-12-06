package wireframe;

/**
 *A ProgressBar element represents a horizontal progress bar in a wireframe (like a loading bar).
 * The progress of the ProgressBar can be adjusted.
 *
 */
public class ProgressBar extends AbstractWireframeElement {

	private int progress = 0;
	
	/**Creates a ProgressBar at specified location with default progress 0%.
	 * @param location the location to place the ProgressBar
	 */
	ProgressBar(Location location) {
		super(location);
		setType(Enums.ElementType.PROGRESS_BAR);
	}
	
	/**Returns the percent progress of this ProgressBar.
	 * @return the percent progress of this ProgressBar
	 */
	final int getProgress() {
		return progress;
	}

	/**Sets the progress of this ProgressBar to a percent between 0 and 100, otherwise does nothing.
	 * @param progress the percent to set to, between 0 and 100
	 */
	final void setProgress(int progress) {
		if(progress >= 0 && progress <= 100){
			this.progress = progress;
		}
	}
}
