package aka.jmetadataquery.main.types.search.audio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataAudio;
import aka.jmetadataquery.main.types.constants.LanguageTagEnum;
import aka.jmetadataquery.main.types.search.Criteria;
import aka.jmetadataquery.main.types.search.constants.LanguageTagSearchEnum;
import aka.jmetadataquery.main.types.search.helpers.SearchHelper;

/**
 * Audio language tag search.
 *
 * @author charlottew
 */
public class AudioLanguageTagSearch extends Criteria<LanguageTagSearchEnum, String> {

    private final Op operation;
    private @NonNull final LanguageTagSearchEnum languageTagSearchEnum;

    /**
     * Constructor.
     *
     * @param operation
     * @param languageTagSearchEnum
     */
    public AudioLanguageTagSearch(final BinaryCondition.Op operation, @NonNull final LanguageTagSearchEnum languageTagSearchEnum) {
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
        int i = -1;
        @NonNull
        final List<@NonNull JMetaDataAudio> audioStreams = jMetaData.getAudioStreams();
        for (final JMetaDataAudio jMetaDataAudio : audioStreams) {
            final String language = jMetaDataAudio.getTitleAsString();
            Integer idAsInteger = jMetaDataAudio.getIDAsInteger();
            if (idAsInteger == null) {
                idAsInteger = Integer.valueOf(i);
                i--;
            }
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
