package aka.jmetadataquery.main.types.search.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import aka.jmetadataquery.main.types.constants.LanguageTagEnum;

/**
 * @author charlottew
 *
 */
public enum LanguageTagSearchEnum {

    /**
     * Version Originale (generic).
     */
    VO(LanguageTagEnum.VO),

    /**
     * Version Francaise (generic).
     */
    VF(LanguageTagEnum.VF),

    /**
     * Version Francophone Quebec.
     */
    VQF(LanguageTagEnum.VQF, LanguageTagEnum.VFQ, LanguageTagEnum.VQ),

    /**
     * Version Francophone Francaise.
     */
    VFF(LanguageTagEnum.VFF, LanguageTagEnum.VFI, LanguageTagEnum.VFB, LanguageTagEnum.VF_TRUEFRENCH);

    @NonNull
    private final List<@NonNull LanguageTagEnum> languageList = new ArrayList<>();

    LanguageTagSearchEnum(@NonNull final LanguageTagEnum @NonNull... languages) {
        this.languageList.addAll(Arrays.asList(languages));
    }

    /**
     * Get the list of the corresponding languages of the ENUM.
     *
     * @return the value of the ENUM
     */
    @NonNull
    public List<@NonNull LanguageTagEnum> getLanguagesList() {
        return this.languageList;
    }

    /**
     * Get LanguageSearchEnum corresponding to given string.
     *
     * @param language
     * @return corresponding LanguageSearchEnum
     */
    @Nullable
    public static LanguageTagSearchEnum getSearchEnum(@Nullable final String language) {
        LanguageTagSearchEnum result = null;
        if (language != null) {
            final String trimmedLanguage = language.trim();
            if (trimmedLanguage.length() > 0) {
                for (final LanguageTagSearchEnum languageSearchEnum : LanguageTagSearchEnum.values()) {
                    final boolean containsSearchStr = languageSearchEnum.getLanguagesList().stream().filter(s -> s.getValue().equalsIgnoreCase(language)).findFirst().isPresent();
                    if (containsSearchStr) {
                        result = languageSearchEnum;
                        // found, just break
                        break;
                    }
                }
            }
        }

        return result;
    }
}
