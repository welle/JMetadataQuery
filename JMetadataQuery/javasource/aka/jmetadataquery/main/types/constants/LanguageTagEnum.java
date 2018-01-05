package aka.jmetadataquery.main.types.constants;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Enum of available language TAG.
 *
 * @author charlottew
 */
public enum LanguageTagEnum {

    /**
     * Version Originale (generic).
     */
    VO("VO", "Version Originale"),

    /**
     * Version Francaise (generic).
     */
    VF("VF", "Version Francaise"),

    /**
     * Version Francaise (TrueFrench).
     */
    VF_TRUEFRENCH("Truefrench", "Version Francaise (TrueFrench)"),

    /**
     * Version Francophone Quebecoise.
     */
    VQF("VQF", "Version Francophone Quebecoise"),

    /**
     * Version Francophone Quebecoise.
     */
    VFQ("VFQ", "Version Francophone Quebecoise"),

    /**
     * Version Quebecoise.
     */
    VQ("VQ", "Version Quebecoise"),

    /**
     * Version Francophone Francaise.
     */
    VFF("VFF", "Version Francophone Francaise"),

    /**
     * Version Francophone Internationale.
     */
    VFI("VFI", "Version Francophone Internationale"),

    /**
     * Version Francophone Belge.
     */
    VFB("VFB", "Version Francophone Belge"),

    /**
     * Version Belge Francophone.
     */
    VBF("VFB", "Version Belge Francophone");

    private @NonNull String language;
    private @NonNull String description;

    LanguageTagEnum(@NonNull final String language, @NonNull final String description) {
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
    public LanguageTagEnum getLanguageTagEnum(@Nullable final String languageStr) {
        LanguageTagEnum result = null;
        if (languageStr != null) {
            final String trimmedLanguage = languageStr.trim();
            if (trimmedLanguage.length() > 0) {
                for (final LanguageTagEnum languageEnum : LanguageTagEnum.values()) {
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
