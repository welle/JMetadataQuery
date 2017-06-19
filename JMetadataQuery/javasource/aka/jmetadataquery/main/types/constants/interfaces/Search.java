package aka.jmetadataquery.main.types.constants.interfaces;

import org.eclipse.jdt.annotation.Nullable;

/**
 * Generic search enum.
 *
 * @author charlottew
 * @param <T> parameter of the search
 */
public interface Search<T> {

    /**
     * Get <T> corresponding to given string.
     *
     * @param language
     * @return corresponding LanguageSearchEnum
     */
    @Nullable
    public T getSearchEnum(@Nullable final String language);

}
