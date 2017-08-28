package oleg.sopilnyak.common.model.business;

import java.io.Serializable;

/**
 * Business Model type for manage photos
 */
public interface Photo extends Serializable{
    /**
     * Binary image of the photo
     *
     * @return image
     */
    byte[] getImage();

    /**
     * To get content type of image
     *
     * @return content-type
     */
    String getContentType();
}
