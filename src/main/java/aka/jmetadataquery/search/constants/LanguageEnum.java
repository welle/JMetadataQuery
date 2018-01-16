package aka.jmetadataquery.search.constants;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Enum of available language.
 *
 * @author charlottew
 */
public enum LanguageEnum {

    /**
     * English.
     */
    ENGLISH("English", "Eng", "En"),

    /**
     * Francais.
     */
    FRENCH("French", "Fre", "Fr"),

    /**
     * German.
     */
    GERMAN("German", "Gr", "De");

    private @NonNull List<@NonNull String> languages;

    LanguageEnum(@NonNull final String @NonNull... languages) {
        this.languages = Arrays.asList(languages);
    }

    /**
     * Get the values of the ENUM.
     *
     * @return the values of the ENUM
     */
    @NonNull
    public List<@NonNull String> getValues() {
        return this.languages;
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
                    for (final String language : this.languages) {
                        if (language.equalsIgnoreCase(trimmedLanguage)) {
                            result = languageEnum;
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
