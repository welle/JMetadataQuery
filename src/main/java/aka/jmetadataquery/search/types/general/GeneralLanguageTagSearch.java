package aka.jmetadataquery.search.types.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadataquery.helpers.SearchHelper;
import aka.jmetadataquery.search.Criteria;
import aka.jmetadataquery.search.constants.LanguageTagEnum;
import aka.jmetadataquery.search.constants.LanguageTagSearchEnum;

/**
 * General language tag search.
 *
 * @author charlottew
 */
public class GeneralLanguageTagSearch extends Criteria<LanguageTagSearchEnum, String> {

    private final Op operation;
    private @NonNull final LanguageTagSearchEnum languageTagSearchEnum;

    /**
     * Constructor.
     *
     * @param operation
     * @param languageTagSearchEnum
     */
    public GeneralLanguageTagSearch(final BinaryCondition.Op operation, @NonNull final LanguageTagSearchEnum languageTagSearchEnum) {
        super(languageTagSearchEnum);
        this.operation = operation;
        this.languageTagSearchEnum = languageTagSearchEnum;
    }

    @Override
    public boolean matchCriteria(@NonNull final JMetaData jMetaData) {
        final Map<@NonNull Integer, Boolean> map = getStreamsIDInFileMatchingCriteria(jMetaData);
        final List<@NonNull Map<@NonNull Integer, Boolean>> idMapList = new ArrayList<>();
        idMapList.add(map);
        return SearchHelper.isMatching(idMapList, 1, false);
    }

    @Override
    public @NonNull Map<@NonNull Integer, Boolean> getStreamsIDInFileMatchingCriteria(@NonNull final JMetaData jMetaData) {
        final Map<@NonNull Integer, Boolean> result = new HashMap<>();

        final List<LanguageTagEnum> languageList = this.languageTagSearchEnum.getLanguagesList();
        final @NonNull List<@NonNull String> expectedLanguages = new ArrayList<>();
        for (final LanguageTagEnum languageTagEnum : languageList) {
            expectedLanguages.add(languageTagEnum.getValue());
        }
        final String generalTitle = jMetaData.getGeneral().getTitleAsString();
        result.putAll(getMatchingCriteria(jMetaData, expectedLanguages, generalTitle));
        final String generalTitleMore = jMetaData.getGeneral().getTitleMoreAsString();
        result.putAll(getMatchingCriteria(jMetaData, expectedLanguages, generalTitleMore));
        final String movieTitle = jMetaData.getGeneral().getMovieAsString();
        result.putAll(getMatchingCriteria(jMetaData, expectedLanguages, movieTitle));
        return result;
    }

    @NonNull
    private Map<@NonNull Integer, Boolean> getMatchingCriteria(@NonNull final JMetaData jMetaData, @NonNull final List<@NonNull String> expectedLanguages, @Nullable final String currentTitle) {
        final Map<@NonNull Integer, Boolean> result = new HashMap<>();
        int i = -1;
        if (currentTitle != null) {
            Integer idAsInteger = jMetaData.getGeneral().getIDAsInteger();
            if (idAsInteger == null) {
                idAsInteger = Integer.valueOf(i);
                i--;
            }
            for (final String expectedLanguage : expectedLanguages) {
                final boolean match = conditionMatch(currentTitle, expectedLanguage, this.operation);
                if (!result.containsKey(idAsInteger) || (result.get(idAsInteger) != null && !result.get(idAsInteger).booleanValue())) {
                    result.put(idAsInteger, Boolean.valueOf(match));
                }
            }
        }
        return result;
    }

}
