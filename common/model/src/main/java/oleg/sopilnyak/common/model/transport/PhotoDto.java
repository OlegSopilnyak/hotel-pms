package oleg.sopilnyak.common.model.transport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oleg.sopilnyak.common.model.business.Photo;

/**
 * Transport Model type for manage photos
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDto implements Photo {
    private static final long serialVersionUID = 4182865430800098633L;
    private byte[] image;
    private String contentType;

}
