package oleg.sopilnyak.persistence.model;

import lombok.Data;
import oleg.sopilnyak.common.model.business.Photo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Persistence Model type for photos
 */
@Data
@Embeddable
class PhotoEntity implements Photo, Serializable {
    @Column(name = "photo_image")
    private byte[] image;
    @Column(name = "photo_contentType")
    private String contentType;
}
