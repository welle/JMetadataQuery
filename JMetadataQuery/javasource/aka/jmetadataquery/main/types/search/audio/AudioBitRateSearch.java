package aka.jmetadataquery.main.types.search.audio;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataAudio;
import aka.jmetadataquery.main.types.search.Criteria;

/**
 * Audio Bit rate search.
 *
 * @author charlottew
 */
public class AudioBitRateSearch extends Criteria<Long, Long> {

    private final Op operation;
    private @NonNull final Long bitRate;

    /**
     * Constructor.
     *
     * @param operation
     * @param bitRate in byte
     */
    public AudioBitRateSearch(final BinaryCondition.Op operation, @NonNull final Long bitRate) {
        super(bitRate);
        this.operation = operation;
        this.bitRate = bitRate;
    }

    @Override
    public boolean matchCriteria(@NonNull final JMetaData jMetaData) {
        boolean result = false;

        @NonNull
        final List<@NonNull JMetaDataAudio> audioStreams = jMetaData.getAudioStreams();
        for (final JMetaDataAudio jMetaDataAudio : audioStreams) {
            final Long rate = jMetaDataAudio.getBitRateAsLong();
            if (rate != null) {
                result = conditionMatch(rate, this.bitRate, this.operation);
            }
            if (result) {
                break;
            }
        }
        return result;
    }

}
