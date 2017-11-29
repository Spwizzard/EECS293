package wireframe;

import java.awt.image.BufferedImage;

public class Image extends AbstractWireframeElement {

	private BufferedImage image;
	
	Image(Location location){
		super(location);
		this.type = Enums.ElementType.IMAGE;
	}
	
	final BufferedImage getImage() {
		return image;
	}

	final void setImage(BufferedImage image) {
		this.image = image;
	}

}
