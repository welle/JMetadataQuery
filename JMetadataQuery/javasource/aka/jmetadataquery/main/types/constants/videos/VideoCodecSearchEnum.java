package aka.jmetadataquery.main.types.constants.videos;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * @author charlottew
 *
 */
public enum VideoCodecSearchEnum {

    /**
     * Unknown.
     */
    UNKNOWN(""),

    /**
     * MPEG-1.
     */
    MPEG_1("MPEG-1"),

    /**
     * MPEG-2.
     */
    MPEG_2("MPEG-2"),

    /**
     * MPEG-4 ASP.
     */
    MPEG_4_ASP("MPEG-4 ASP", "MPEG-4 Part 2", "MPEG-4 Visual"),

    /**
     * H.264.
     */
    H264("H.264", "H264", "H 264", "MPEG-4 AVC", "MPEG-4 Part 10", "AVC"),

    /**
     * HEVC.
     */
    HEVC("HEVC", " MPEG-4 HEVC", "H 265", "H.265", "H265");

    @NonNull
    private final List<@NonNull String> codecs;

    VideoCodecSearchEnum(@NonNull final String @NonNull... codecsParam) {
        this.codecs = Arrays.asList(codecsParam);
    }

    /**
     * Get the codecs of the ENUM.
     *
     * @return the codecs of the ENUM
     */
    @NonNull
    public List<@NonNull String> getValues() {
        for (final String string : this.codecs) {
            System.err.println("getValues:" + string);
        }
        return this.codecs;
    }

    /**
     * Get VideoCodecSearchEnum corresponding to given string.
     *
     * @param param
     * @return corresponding VideoCodecSearchEnum
     */
    @Nullable
    public static VideoCodecSearchEnum getVideoCodecSearchEnum(@Nullable final String param) {
        VideoCodecSearchEnum result = UNKNOWN;
        if (param != null) {
            final String trimmedParam = param.trim().toLowerCase();
            if (trimmedParam.length() > 0) {
                for (final VideoCodecSearchEnum videoSearchEnum : VideoCodecSearchEnum.values()) {
                    final List<@NonNull String> values = videoSearchEnum.codecs;
                    for (final String expectedCodec : values) {
                        if (trimmedParam.equals(expectedCodec)) {
                            result = videoSearchEnum;
                            // found, just break
                            break;
                        }
                    }
                }
            }
        }

        return result;
    }
}
