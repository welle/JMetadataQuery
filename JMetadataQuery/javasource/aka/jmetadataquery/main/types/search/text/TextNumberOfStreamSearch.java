package aka.jmetadataquery.main.types.search.text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataText;
import aka.jmetadataquery.main.types.search.Criteria;

/**
 * Text number of stream search.
 *
 * @author charlottew
 */
public class TextNumberOfStreamSearch extends Criteria<Long, Long> {

    private final Op operation;
    private @NonNull final Long numberOfStream;

    /**
     * Constructor.
     *
     * @param operation
     * @param numberOfStream
     */
    public TextNumberOfStreamSearch(final BinaryCondition.Op operation, @NonNull final Long numberOfStream) {
        super(numberOfStream);
        this.operation = operation;
        this.numberOfStream = numberOfStream;
    }

    @Override
    public boolean matchCriteria(@NonNull final JMetaData jMetaData) {
        final List<@NonNull JMetaDataText> textStreams = jMetaData.getSubtitleStreams();
        final Long size = Long.valueOf(textStreams.size());
        final boolean match = conditionMatch(size, this.numberOfStream, this.operation);
        return match;
    }

    @Override
    public @NonNull Map<@NonNull Integer, Boolean> getStreamsIDInFileMatchingCriteria(@NonNull final JMetaData jMetaData) {
        final Map<@NonNull Integer, Boolean> result = new HashMap<>();
        return result;
    }

}
