package aka.jmetadataquery.main.types.search.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataVideo;
import aka.jmetadataquery.main.types.search.Criteria;
import aka.jmetadataquery.main.types.search.helpers.SearchHelper;

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
        final Map<@NonNull Integer, Boolean> map = getStreamsIDInFileMatchingCriteria(jMetaData);
        final List<@NonNull Map<@NonNull Integer, Boolean>> idMapList = new ArrayList<>();
        idMapList.add(map);
        return SearchHelper.isMatching(idMapList, 1);
    }

    @Override
    public @NonNull Map<@NonNull Integer, Boolean> getStreamsIDInFileMatchingCriteria(@NonNull final JMetaData jMetaData) {
        final Map<@NonNull Integer, Boolean> result = new HashMap<>();

        int i = -1;
        @NonNull
        final List<@NonNull JMetaDataVideo> videoStreams = jMetaData.getVideoStreams();
        for (final @NonNull JMetaDataVideo jMetaDataVideo : videoStreams) {
            Integer idAsInteger = jMetaDataVideo.getIDAsInteger();
            if (idAsInteger == null) {
                idAsInteger = Integer.valueOf(i);
                i--;
            }

            final Long rate = jMetaDataVideo.getBitRateMaximumAsLong();
            if (rate != null) {
                final boolean match = conditionMatch(rate, this.maxBitRate, this.operation);
                if (!result.containsKey(idAsInteger)) {
                    result.put(idAsInteger, match);
                }
            }
        }
        return result;
    }

}
