package aka.jmetadataquery.search.types.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataVideo;
import aka.jmetadataquery.helpers.SearchHelper;
import aka.jmetadataquery.search.Criteria;
import aka.jmetadataquery.search.constants.LanguageTagEnum;
import aka.jmetadataquery.search.constants.LanguageTagSearchEnum;
import aka.jmetadataquery.search.constants.conditions.Operator;

/**
 * Video language tag search.
 *
 * @author charlottew
 */
public class VideoLanguageTagSearch extends Criteria<LanguageTagSearchEnum, String> {

    private final Operator operation;
    private @NonNull final LanguageTagSearchEnum languageTagSearchEnum;

    /**
     * Constructor.
     *
     * @param operation
     * @param languageTagSearchEnum
     */
    public VideoLanguageTagSearch(final Operator operation, @NonNull final LanguageTagSearchEnum languageTagSearchEnum) {
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
        result.putAll(getTitleMatchingCriteria(jMetaData, expectedLanguages));
        return result;
    }

    @NonNull
    private Map<@NonNull Integer, Boolean> getTitleMatchingCriteria(@NonNull final JMetaData jMetaData, @NonNull final List<@NonNull String> expectedLanguages) {
        final Map<@NonNull Integer, Boolean> result = new HashMap<>();
        int i = -1;
        final List<@NonNull JMetaDataVideo> videoStreams = jMetaData.getVideoStreams();
        for (final @NonNull JMetaDataVideo jMetaDataVideo : videoStreams) {
            Integer idAsInteger = jMetaDataVideo.getIDAsInteger();
            if (idAsInteger == null) {
                idAsInteger = Integer.valueOf(i);
                i--;
            }

            @Nullable
            final String currentTitle = jMetaDataVideo.getTitleAsString();
            if (currentTitle != null) {
                for (final String expectedLanguage : expectedLanguages) {
                    final boolean match = conditionMatch(currentTitle, expectedLanguage, this.operation);
                    if (!result.containsKey(idAsInteger) || (result.get(idAsInteger) != null && !result.get(idAsInteger).booleanValue())) {
                        result.put(idAsInteger, Boolean.valueOf(match));
                    }
                }
            }
        }
        Integer idAsInteger = jMetaData.getGeneral().getIDAsInteger();
        if (idAsInteger == null) {
            idAsInteger = Integer.valueOf(i);
            i--;
        }
        return result;
    }

}
