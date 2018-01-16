package aka.jmetadataquery.search.constants.audio;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Enum of available compression mode.
 *
 * @author charlottew
 */
public enum CompressionModeEnum {

    /**
     * Lossless (no loss).
     */
    LOSSLESS("Lossless"),

    /**
     * Lossy (compressed).
     */
    LOSSY("Lossy");

    private @NonNull String value;

    CompressionModeEnum(@NonNull final String value) {
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
     * Get CompressionModeEnum corresponding to given string.
     *
     * @param param
     * @return corresponding CompressionModeEnum
     */
    @Nullable
    public CompressionModeEnum getLanguageEnum(@Nullable final String param) {
        CompressionModeEnum result = null;
        if (param != null) {
            final String trimmedParam = param.trim();
            if (trimmedParam.length() > 0) {
                for (final CompressionModeEnum compressionModeEnum : CompressionModeEnum.values()) {
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
