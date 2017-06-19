package aka.jmetadataquery.main.types.constants.videos;

import org.eclipse.jdt.annotation.Nullable;

import aka.jmetadata.main.constants.video.Resolution;

/**
 * @author charlottew
 *
 */
public enum VideoResolutionSearchEnum {

    /**
     * R_1080.
     */
    R_1080(Resolution.R_1080),

    /**
     * R_2160.
     */
    R_2160(Resolution.R_2160),

    /**
     * R_4320.
     */
    R_4320(Resolution.R_4320),

    /**
     * R_480.
     */
    R_480(Resolution.R_480),

    /**
     * R_540.
     */
    R_540(Resolution.R_540),

    /**
     * R_576.
     */
    R_576(Resolution.R_576),

    /**
     * R_720.
     */
    R_720(Resolution.R_720),

    /**
     * R_1440.
     */
    R_1440(Resolution.R_1440);

    private final Resolution resolution;

    VideoResolutionSearchEnum(final Resolution resolution) {
        this.resolution = resolution;
    }

    /**
     * Get the resolution of the ENUM.
     *
     * @return the value of the ENUM
     */
    public Resolution getResolution() {
        return this.resolution;
    }

    /**
     * Get VideoResolutionSearchEnum corresponding to given string.
     *
     * @param resolutionStr
     * @return corresponding VideoResolutionSearchEnum
     */
    @Nullable
    public static VideoResolutionSearchEnum getSearchEnum(@Nullable final String resolutionStr) {
        VideoResolutionSearchEnum result = null;
        if (resolutionStr != null) {
            final String trimmedLanguage = resolutionStr.trim();
            if (trimmedLanguage.length() > 0) {
                for (final VideoResolutionSearchEnum videoSearchEnum : VideoResolutionSearchEnum.values()) {
                    if (resolutionStr.equalsIgnoreCase(videoSearchEnum.resolution.getResolutionName())) {
                        result = videoSearchEnum;
                        // found, just break
                        break;
                    }
                }
            }
        }

        return result;
    }
}
