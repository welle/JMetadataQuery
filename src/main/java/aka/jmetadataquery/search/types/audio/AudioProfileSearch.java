package aka.jmetadataquery.search.types.audio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataAudio;
import aka.jmetadata.main.constants.profile.AudioProfileEnum;
import aka.jmetadataquery.helpers.SearchHelper;
import aka.jmetadataquery.search.Criteria;
import aka.jmetadataquery.search.constants.conditions.Operator;

/**
 * Audio profile search.
 *
 * @author charlottew
 */
public class AudioProfileSearch extends Criteria<AudioProfileEnum, String> {

    private final Operator operation;
    private @NonNull final AudioProfileEnum audioProfileEnum;

    /**
     * Constructor.
     *
     * @param operation
     * @param audioProfileEnum
     */
    public AudioProfileSearch(final Operator operation, @NonNull final AudioProfileEnum audioProfileEnum) {
        super(audioProfileEnum);
        this.operation = operation;
        this.audioProfileEnum = audioProfileEnum;
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

        @NonNull
        final List<@NonNull JMetaDataAudio> audioStreams = jMetaData.getAudioStreams();
        int i = -1;
        for (final JMetaDataAudio jMetaDataAudio : audioStreams) {
            final String profile = jMetaDataAudio.getFormatProfileAsString();
            Integer idAsInteger = jMetaDataAudio.getIDAsInteger();
            if (idAsInteger == null) {
                idAsInteger = Integer.valueOf(i);
                i--;
            }
            if (profile != null) {
                final boolean match = conditionMatch(profile, this.audioProfileEnum.getName(), this.operation);
                if (!result.containsKey(idAsInteger)) {
                    result.put(idAsInteger, Boolean.valueOf(match));
                }
            }
        }
        return result;
    }

}
