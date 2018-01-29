package aka.jmetadataquery.search.types.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataText;
import aka.jmetadataquery.helpers.SearchHelper;
import aka.jmetadataquery.search.Criteria;
import aka.jmetadataquery.search.constants.LanguageEnum;
import aka.jmetadataquery.search.constants.conditions.Operator;

/**
 * Text language search.
 *
 * @author charlottew
 */
public class TextLanguageSearch extends Criteria<LanguageEnum, String> {

    private final Operator operation;
    private @NonNull final LanguageEnum languageEnum;

    /**
     * Constructor.
     *
     * @param operation
     * @param languageEnum
     */
    public TextLanguageSearch(final Operator operation, @NonNull final LanguageEnum languageEnum) {
        super(languageEnum);
        this.operation = operation;
        this.languageEnum = languageEnum;
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

        int i = -1;
        final @NonNull List<@NonNull String> expectedLanguages = this.languageEnum.getValues();
        @NonNull
        final List<@NonNull JMetaDataText> textStreams = jMetaData.getSubtitleStreams();
        for (final JMetaDataText jMetaDataText : textStreams) {
            Integer idAsInteger = jMetaDataText.getIDAsInteger();
            if (idAsInteger == null) {
                idAsInteger = Integer.valueOf(i);
                i--;
            }
            final String language = jMetaDataText.getLanguageAsString();
            if (language != null) {
                for (final String expectedLanguage : expectedLanguages) {
                    final boolean match = conditionMatch(language, expectedLanguage, this.operation);
                    if (!result.containsKey(idAsInteger) || (result.get(idAsInteger) != null && !result.get(idAsInteger).booleanValue())) {
                        result.put(idAsInteger, Boolean.valueOf(match));
                    }
                }
            }
        }
        return result;
    }

}
