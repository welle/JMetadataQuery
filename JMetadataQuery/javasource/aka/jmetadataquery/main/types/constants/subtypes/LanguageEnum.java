package aka.jmetadataquery.main.types.constants.subtypes;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Enum of available language.
 *
 * @author charlottew
 */
public enum LanguageEnum {

    /**
     * Version Originale (générique).
     */
    VO("VO", "Version Originale"),

    /**
     * Version Française (générique).
     */
    VF("VF", "Version Française"),

    /**
     * Version Francophone Québécoise.
     */
    VQF("VQF", "Version Francophone Québécoise"),

    /**
     * Version Francophone Québécoise.
     */
    VFQ("VFQ", "Version Francophone Québécoise"),

    /**
     * Version Québécoise.
     */
    VQ("VQ", "Version Québécoise"),

    /**
     * Version Francophone Française.
     */
    VFF("VFF", "Version Francophone Française"),

    /**
     * Version Francophone Internationale.
     */
    VFI("VFI", "Version Francophone Internationale"),

    /**
     * Version Francophone Belge.
     */
    VFB("VFB", "Version Francophone Belge");

    private @NonNull String language;
    private @NonNull String description;

    LanguageEnum(@NonNull final String language, @NonNull final String description) {
        this.language = language;
        this.description = description;
    }

    /**
     * Get the value of the ENUM.
     *
     * @return the value of the ENUM
     */
    @NonNull
    public String getValue() {
        return this.language;
    }

    /**
     * Get the description of the ENUM.
     *
     * @return the description of the ENUM
     */
    @NonNull
    public String getDescription() {
        return this.description;
    }

    /**
     * Get LanguageEnum corresponding to given string.
     *
     * @param languageStr
     * @return corresponding LanguageEnum
     */
    @Nullable
    public LanguageEnum getLanguageEnum(@Nullable final String languageStr) {
        LanguageEnum result = null;
        if (languageStr != null) {
            final String trimmedLanguage = languageStr.trim();
            if (trimmedLanguage.length() > 0) {
                for (final LanguageEnum languageEnum : LanguageEnum.values()) {
                    if (languageEnum.getValue().equalsIgnoreCase(trimmedLanguage)) {
                        result = languageEnum;
                        // found, just break
                        break;
                    }
                }
            }
        }

        return result;
    }
}
