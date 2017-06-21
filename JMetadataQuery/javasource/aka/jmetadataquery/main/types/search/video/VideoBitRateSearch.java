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
 * Video Bit rate search.
 *
 * @author charlottew
 */
public class VideoBitRateSearch extends Criteria<Long, Long> {

    private final Op operation;
    private @NonNull final Long bitRate;

    /**
     * Constructor.
     *
     * @param operation
     * @param bitRate in byte
     */
    public VideoBitRateSearch(final BinaryCondition.Op operation, @NonNull final Long bitRate) {
        super(bitRate);
        this.operation = operation;
        this.bitRate = bitRate;
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
            final Long rate = jMetaDataVideo.getBitRateAsLong();
            if (rate != null) {
                final boolean match = conditionMatch(rate, this.bitRate, this.operation);
                if (match && idAsInteger != null) {
                    result.add(idAsInteger);
                }
            }
        }
        return result;
    }

}
