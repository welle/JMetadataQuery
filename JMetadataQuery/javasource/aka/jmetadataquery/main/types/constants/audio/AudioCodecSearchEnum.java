package aka.jmetadataquery.main.types.constants.audio;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * @author charlottew
 *
 */
public enum AudioCodecSearchEnum {

    /**
     * Unknown.
     */
    UNKNOWN(""),

    /**
     * AAC.
     */
    AAC("AAC"),

    /**
     * AC-3.
     */
    AC_3("AC-3", "Dolby Digital"),

    /**
     * MPEG-1/2 Audio Layer III (mp3).
     */
    MP_3("MPEG-1/2 Audio Layer III", "mp3", "MPEG-4 Visual"),

    /**
     * DTS.
     */
    DTS("A_DTS", "H264", "H 264", "MPEG-4 AVC", "MPEG-4 Part 10", "AVC"),

    /**
     * HEVC.
     */
    HEVC("HEVC", " MPEG-4 HEVC", "H 265", "H.265", "H265");

    @NonNull
    private final List<@NonNull String> codecs;

    AudioCodecSearchEnum(@NonNull final String @NonNull... codecsParam) {
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
    public static AudioCodecSearchEnum getVideoCodecSearchEnum(@Nullable final String param) {
        AudioCodecSearchEnum result = UNKNOWN;
        if (param != null) {
            final String trimmedParam = param.trim().toLowerCase();
            if (trimmedParam.length() > 0) {
                for (final AudioCodecSearchEnum videoSearchEnum : AudioCodecSearchEnum.values()) {
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
