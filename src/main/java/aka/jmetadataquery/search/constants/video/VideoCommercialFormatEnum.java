package aka.jmetadataquery.search.constants.video;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Enum of available language.
 *
 * @author charlottew
 */
public enum VideoCommercialFormatEnum {

    /**
     * Dirac.
     */
    DIRAC("Dirac"),

    /**
     * FFV1.
     */
    FFV1("FFV1"),

    /**
     * MPEG Video.
     */
    MPEG_VIDEO("MPEG Video"),

    /**
     * MPEG-4 Visual.
     */
    MPEG_4_VISUAL("MPEG-4 Visual"),

    /**
     * AVC.
     */
    AVC("AVC"),

    /**
     * HEVC.
     */
    HEVC("HEVC"),

    /**
     * VC-1.
     */
    VC_1("VC-1"),

    /**
     * ProRes.
     */
    PRORES("ProRes"),

    /**
     * RealVideo 1.
     */
    REALVIDEO_1("RealVideo 1"),

    /**
     * RealVideo 2.
     */
    REALVIDEO_2("RealVideo 2"),

    /**
     * RealVideo 3.
     */
    REALVIDEO_3("RealVideo 3"),

    /**
     * Theora.
     */
    THEORA("Theora"),

    /**
     * RGB.
     */
    RGB("RGB"),

    /**
     * RGBA.
     */
    RGBA("RGBA"),

    /**
     * H.261.
     */
    H_261("H.261"),

    /**
     * H.263.
     */
    H_263("H.263"),

    /**
     * H.264.
     */
    H_264("H.264"),

    /**
     * YUV.
     */
    YUV("YUV"),

    /**
     * DV.
     */
    DV("DV"),

    /**
     * JPEG.
     */
    JPEG("JPEG"),

    /**
     * Gray.
     */
    GRAY("Gray"),

    /**
     * Gray/Alpha.
     */
    GRAY_APLHA("Gray/Alpha"),

    /**
     * Avid Meridien Compressed.
     */
    AVID_MERIDIEN_COMPRESSED("Avid Meridien Compressed"),

    /**
     * VC-3.
     */
    VC_3("VC-3"),

    /**
     * VP8.
     */
    VP8("VP8"),

    /**
     * VP9.
     */
    VP9("VP9");

    private @NonNull String value;

    VideoCommercialFormatEnum(@NonNull final String value) {
        this.value = value;
    }

    /**
     * Get the values of the ENUM.
     *
     * @return the values of the ENUM
     */
    @NonNull
    public String getValue() {
        return this.value;
    }

    /**
     * Get CommercialFormatEnum corresponding to given string.
     *
     * @param param
     * @return corresponding CommercialFormatEnum
     */
    @Nullable
    public VideoCommercialFormatEnum getCommercialFormatEnum(@Nullable final String param) {
        VideoCommercialFormatEnum result = null;
        if (param != null) {
            final String trimmedParam = param.trim();
            if (trimmedParam.length() > 0) {
                for (final VideoCommercialFormatEnum compressionModeEnum : VideoCommercialFormatEnum.values()) {
                    if (compressionModeEnum.value.equalsIgnoreCase(trimmedParam)) {
                        result = compressionModeEnum;
                        // found, just break
                        break;
                    }
                }
            }
        }

        return result;
    }
}
