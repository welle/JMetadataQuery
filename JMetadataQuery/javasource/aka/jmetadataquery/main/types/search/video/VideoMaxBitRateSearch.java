package aka.jmetadataquery.main.types.search.video;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataVideo;
import aka.jmetadataquery.main.types.search.Criteria;

/**
 * Video max bit rate search.
 *
 * @author charlottew
 */
public class VideoMaxBitRateSearch extends Criteria<Long, Long> {

    private final Op operation;
    private @NonNull final Long maxBitRate;

    /**
     * Constructor.
     *
     * @param operation
     * @param maxBitRate maximum bit rate in byte
     */
    public VideoMaxBitRateSearch(final BinaryCondition.Op operation, @NonNull final Long maxBitRate) {
        super(maxBitRate);
        this.operation = operation;
        this.maxBitRate = maxBitRate;
    }

    @Override
    public boolean matchCriteria(@NonNull final JMetaData jMetaData) {
        final boolean result = !getStreamsIDInFileMatchingCriteria(jMetaData).isEmpty();
        return result;
    }

    @Override
    public @NonNull List<@NonNull Integer> getStreamsIDInFileMatchingCriteria(@NonNull final JMetaData jMetaData) {
        final List<@NonNull Integer> result = new ArrayList<>();

        @NonNull
        final List<@NonNull JMetaDataVideo> videoStreams = jMetaData.getVideoStreams();
        if (!videoStreams.isEmpty()) {
            final JMetaDataVideo jMetaDataVideo = videoStreams.get(0);
            final Integer idAsInteger = jMetaDataVideo.getIDAsInteger();

            final Long rate = jMetaDataVideo.getBitRateMaximumAsLong();
            if (rate != null) {
                final boolean match = conditionMatch(rate, this.maxBitRate, this.operation);
                if (match && idAsInteger != null) {
                    result.add(idAsInteger);
                }
            }
        }
        return result;
    }

}
