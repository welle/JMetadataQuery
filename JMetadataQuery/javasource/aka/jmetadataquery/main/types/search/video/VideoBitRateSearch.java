package aka.jmetadataquery.main.types.search.video;

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
        boolean result = false;

        @NonNull
        final List<@NonNull JMetaDataVideo> videoStreams = jMetaData.getVideoStreams();
        if (!videoStreams.isEmpty()) {
            final JMetaDataVideo jMetaDataVideo = videoStreams.get(0);
            final Long rate = jMetaDataVideo.getBitRateAsLong();
            if (rate != null) {
                result = conditionMatch(rate, this.bitRate, this.operation);
            }
        }
        return result;
    }

}
