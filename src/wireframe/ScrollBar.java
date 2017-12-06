package wireframe;

/**
 *A ScrollBar element represents a vertical progress bar in a wireframe (like a sidebar).
 * The progress of the ScrollBar can be adjusted.
 *
 */
public class ScrollBar extends AbstractWireframeElement {

	private int progress = 0;
	
	/**Creates a ScrollBar at specified location with default progress 0%.
	 * @param location the location to place the ScrollBar
	 */
	ScrollBar(Location location) {
		super(location);
		setType(Enums.ElementType.SCROLL_BAR);
	}
	
	/**Returns the percent progress of this ScrollBar.
	 * @return the percent progress of this ScrollBar
	 */
	final int getProgress() {
		return progress;
	}

	/**Sets the progress of this ScrollBar to a percent between 0 and 100, otherwise does nothing.
	 * @param progress the percent to set to, between 0 and 100
	 */
	final void setProgress(int progress) {
		if(progress >= 0 && progress <= 100){
			this.progress = progress;
		}
	}

}
