package aka.jmetadataquery.main.types.search.audio;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        final boolean result = !getStreamsIDInFileMatchingCriteria(jMetaData).isEmpty();
        return result;
    }

    @Override
    public @NonNull Set<@NonNull Integer> getStreamsIDInFileMatchingCriteria(@NonNull final JMetaData jMetaData) {
        final Set<@NonNull Integer> result = new HashSet<>();

        @NonNull
        final List<@NonNull JMetaDataAudio> audioStreams = jMetaData.getAudioStreams();
        for (final JMetaDataAudio jMetaDataAudio : audioStreams) {
            final Long chan = jMetaDataAudio.getChannelsAsLong();
            if (chan != null) {
                Integer idAsInteger = jMetaData.getGeneral().getIDAsInteger();
                if (idAsInteger == null) {
                    idAsInteger = Integer.valueOf(-1);
                }
                final boolean match = conditionMatch(chan, this.channel, this.operation);
                if (match) {
                    result.add(idAsInteger);
                }
            }
        }
        return result;
    }

}
