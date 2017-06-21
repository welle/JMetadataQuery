package aka.jmetadataquery.main.types.search.audio;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataAudio;
import aka.jmetadataquery.main.types.search.Criteria;

/**
 * Audio max bit rate search.
 *
 * @author charlottew
 */
public class AudioMaxBitRateSearch extends Criteria<Long, Long> {

    private final Op operation;
    private @NonNull final Long maxBitRate;

    /**
     * Constructor.
     *
     * @param operation
     * @param maxBitRate maximum bit rate in byte
     */
    public AudioMaxBitRateSearch(final BinaryCondition.Op operation, @NonNull final Long maxBitRate) {
        super(maxBitRate);
        this.operation = operation;
        this.maxBitRate = maxBitRate;
    }

    @Override
    public boolean matchCriteria(@NonNull final JMetaData jMetaData) {
        boolean result = false;

        @NonNull
        final List<@NonNull JMetaDataAudio> audioStreams = jMetaData.getAudioStreams();
        for (final JMetaDataAudio jMetaDataAudio : audioStreams) {
            final Long rate = jMetaDataAudio.getBitRateMaximumAsLong();

            if (rate != null) {
                result = conditionMatch(rate, this.maxBitRate, this.operation);
            }
            if (result) {
                break;
            }
        }
        return result;
    }

}
