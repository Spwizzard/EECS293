package wireframe;

public class Slider extends AbstractWireframeElement{

private int progress = 0;
	
	public Slider(Location location) {
		super(location);
		this.type = Enums.ElementType.SLIDER;
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
