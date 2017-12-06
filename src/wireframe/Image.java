package wireframe;

import java.awt.image.BufferedImage;

/**
 * An Image element represents a picture on a wireframe.
 *
 */
public class Image extends AbstractWireframeElement {

	private BufferedImage image;
	
	/**Creates an Image at specified location with no default picture.
	 * @param location the location to place the Image
	 */
	Image(Location location){
		super(location);
		setType(Enums.ElementType.IMAGE);
	}
	
	/**Returns the image that this Image is storing.
	 * @return the image that this Image is storing
	 */
	final BufferedImage getImage() {
		return image;
	}

	/**Sets the image of this Image to the specified BufferedImage.
	 * @param image the image to store in this Image
	 */
	final void setImage(BufferedImage image) {
		this.image = image;
	}

}
