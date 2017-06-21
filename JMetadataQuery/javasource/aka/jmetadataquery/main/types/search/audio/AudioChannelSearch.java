package aka.jmetadataquery.main.types.search.audio;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataAudio;
import aka.jmetadataquery.main.types.search.Criteria;

/**
 * Audio channel search.
 *
 * @author charlottew
 */
public class AudioChannelSearch extends Criteria<Long, Long> {

    private final Op operation;
    private @NonNull final Long channel;

    /**
     * Constructor.
     *
     * @param operation
     * @param channel number of channels
     */
    public AudioChannelSearch(final BinaryCondition.Op operation, @NonNull final Long channel) {
        super(channel);
        this.operation = operation;
        this.channel = channel;
    }

    @Override
    public boolean matchCriteria(@NonNull final JMetaData jMetaData) {
        boolean result = false;

        @NonNull
        final List<@NonNull JMetaDataAudio> audioStreams = jMetaData.getAudioStreams();
        for (final JMetaDataAudio jMetaDataAudio : audioStreams) {
            final Long chan = jMetaDataAudio.getChannelsAsLong();
            if (chan != null) {
                result = conditionMatch(chan, this.channel, this.operation);
            }
            if (result) {
                break;
            }
        }
        return result;
    }

}
