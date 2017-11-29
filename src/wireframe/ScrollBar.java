package wireframe;

public class ScrollBar extends AbstractWireframeElement {

	private int progress = 0;
	
	public ScrollBar(Location location) {
		super(location);
		this.type = Enums.ElementType.SCROLL_BAR;
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
