package wireframe;

public class ProgressBar extends AbstractWireframeElement {

	private int progress = 0;
	
	public ProgressBar(Location location) {
		super(location);
		this.type = Enums.ElementType.PROGRESS_BAR;
	}
	
	final int getProgress() {
		return progress;
	}

	final void setProgress(int progress) {
		if(progress >= 0 && progress <= 100){
			this.progress = progress;
		}
	}
}
