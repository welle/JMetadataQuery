package aka.jmetadataquery.main.types.search.constants.video;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import aka.jmetadata.main.constants.video.AspectRatio;

/**
 * @author charlottew
 *
 */
public enum VideoAspectRatioSearchEnum {

    /**
     * AS_1_33.
     */
    AS_1_33(AspectRatio.AS_1_33),

    /**
     * AS_1_66.
     */
    AS_1_66(AspectRatio.AS_1_66),

    /**
     * AS_1_78.
     */
    AS_1_78(AspectRatio.AS_1_78),

    /**
     * AS_1_85.
     */
    AS_1_85(AspectRatio.AS_1_85),

    /**
     * AS_2_20.
     */
    AS_2_20(AspectRatio.AS_2_20),

    /**
     * AS_2_35.
     */
    AS_2_35(AspectRatio.AS_2_35);

    @NonNull
    private final AspectRatio ratio;

    VideoAspectRatioSearchEnum(@NonNull final AspectRatio ratio) {
        this.ratio = ratio;
    }

    /**
     * Get the aspect ratio of the ENUM.
     *
     * @return the aspect ratio of the ENUM
     */
    @NonNull
    public AspectRatio getAspectRatio() {
        return this.ratio;
    }

    /**
     * Get VideoAspectRatioSearchEnum corresponding to given string.
     *
     * @param aspectRatioStr
     * @return corresponding VideoResolutionSearchEnum
     */
    @Nullable
    public static VideoAspectRatioSearchEnum getSearchEnum(@Nullable final String aspectRatioStr) {
        VideoAspectRatioSearchEnum result = null;
        if (aspectRatioStr != null) {
            final String trimmedLanguage = aspectRatioStr.trim();
            if (trimmedLanguage.length() > 0) {
                for (final VideoAspectRatioSearchEnum videoSearchEnum : VideoAspectRatioSearchEnum.values()) {
                    if (aspectRatioStr.equals(videoSearchEnum.getAspectRatio())) {
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
