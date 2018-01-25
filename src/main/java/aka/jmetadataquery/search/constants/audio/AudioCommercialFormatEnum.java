package aka.jmetadataquery.search.constants.audio;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Enum of available language.
 *
 * @author charlottew
 */
public enum AudioCommercialFormatEnum {

    /**
     * AAC.
     */
    AAC("AAC"),

    /**
     * AC-3.
     */
    AC_3("AC-3"),

    /**
     * ALAC.
     */
    ALAC("ALAC"),

    /**
     * DTS.
     */
    DTS("DTS"),

    /**
     * E-AC-3.
     */
    E_AC_3("E-AC-3"),

    /**
     * Flac.
     */
    FLAC("E-Flac-3"),

    /**
     * MLP.
     */
    MLP("MLP"),

    /**
     * MPEG Audio.
     */
    MPEG_AUDIO("MPEG Audio"),

    /**
     * Opus.
     */
    OPUS("Opus"),

    /**
     * PCM.
     */
    PCM("PCM"),

    /**
     * VSELP.
     */
    VSELP("VSELP"),

    /**
     * G.728.
     */
    G_728("G.728"),

    /**
     * Atrac.
     */
    ATRAC("Atrac"),

    /**
     * Cooker.
     */
    COOKER("Cooker"),

    /**
     * RealAudio Lossless.
     */
    REALAUDIO_LOSSLESS("RealAudio Lossless"),

    /**
     * TrueHD.
     */
    TRUEHD("TrueHD"),

    /**
     * TTA.
     */
    TTA("TTA"),

    /**
     * Vorbis.
     */
    VORBIS("Vorbis"),

    /**
     * WavPack.
     */
    WAVPACK("WavPack"),

    /**
     * ADPCM.
     */
    ADPCM("ADPCM"),

    /**
     * WMA.
     */
    WMA("WMA");

    private @NonNull String value;

    AudioCommercialFormatEnum(@NonNull final String value) {
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
    public AudioCommercialFormatEnum getCommercialFormatEnum(@Nullable final String param) {
        AudioCommercialFormatEnum result = null;
        if (param != null) {
            final String trimmedParam = param.trim();
            if (trimmedParam.length() > 0) {
                for (final AudioCommercialFormatEnum compressionModeEnum : AudioCommercialFormatEnum.values()) {
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
