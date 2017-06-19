package aka.jmetadataquery.main.types.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import aka.jmetadataquery.main.types.constants.subtypes.LanguageEnum;

/**
 * @author charlottew
 *
 */
public enum LanguageSearchEnum {

    /**
     * Version Originale (générique).
     */
    VO(LanguageEnum.VO),
    /**
     * Version Française (générique).
     */
    VF(LanguageEnum.VF),
    /**
     * Version Francophone Québécoise.
     */
    VQF(LanguageEnum.VQF, LanguageEnum.VFQ, LanguageEnum.VQ),
    /**
     * Version Francophone Française.
     */
    VFF(LanguageEnum.VFF, LanguageEnum.VFI, LanguageEnum.VFB);

    @NonNull
    private final List<aka.jmetadataquery.main.types.constants.subtypes.LanguageEnum> languageList = new ArrayList<>();

    LanguageSearchEnum(@NonNull final LanguageEnum @NonNull... languages) {
        this.languageList.addAll(Arrays.asList(languages));
    }

    /**
     * Get the list of the corresponding languages of the ENUM.
     *
     * @return the value of the ENUM
     */
    @NonNull
    public List<aka.jmetadataquery.main.types.constants.subtypes.LanguageEnum> getLanguagesList() {
        return this.languageList;
    }

    /**
     * Get LanguageSearchEnum corresponding to given string.
     *
     * @param language
     * @return corresponding LanguageSearchEnum
     */
    @Nullable
    public static LanguageSearchEnum getSearchEnum(@Nullable final String language) {
        LanguageSearchEnum result = null;
        if (language != null) {
            final String trimmedLanguage = language.trim();
            if (trimmedLanguage.length() > 0) {
                for (final LanguageSearchEnum languageSearchEnum : LanguageSearchEnum.values()) {
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
