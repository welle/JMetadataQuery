package aka.jmetadataquery.main.types.search.audio;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataAudio;
import aka.jmetadata.main.constants.profile.AudioProfileEnum;
import aka.jmetadataquery.main.types.search.Criteria;

/**
 * Audio profile search.
 *
 * @author charlottew
 */
public class AudioProfileSearch extends Criteria<AudioProfileEnum, String> {

    private final Op operation;
    private @NonNull final AudioProfileEnum audioProfileEnum;

    /**
     * Constructor.
     *
     * @param operation
     * @param audioProfileEnum
     */
    public AudioProfileSearch(final BinaryCondition.Op operation, @NonNull final AudioProfileEnum audioProfileEnum) {
        super(audioProfileEnum);
        this.operation = operation;
        this.audioProfileEnum = audioProfileEnum;
    }

    @Override
    public boolean matchCriteria(@NonNull final JMetaData jMetaData) {
        boolean result = false;

        @NonNull
        final List<@NonNull JMetaDataAudio> audioStreams = jMetaData.getAudioStreams();
        for (final JMetaDataAudio jMetaDataAudio : audioStreams) {
            final String profile = jMetaDataAudio.getFormatProfileAsString();
            if (profile != null) {
                result = conditionMatch(profile, this.audioProfileEnum.getName(), this.operation);
                if (result) {
                    // just break
                    break;
                }
            }
            if (result) {
                break;
            }
        }
        return result;
    }

}
