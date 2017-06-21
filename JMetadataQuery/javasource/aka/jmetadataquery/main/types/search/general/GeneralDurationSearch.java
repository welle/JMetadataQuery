package aka.jmetadataquery.main.types.search.general;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadataquery.main.types.search.Criteria;

/**
 * General duration search.
 *
 * @author charlottew
 */
public class GeneralDurationSearch extends Criteria<Long, Long> {

    private final Op operation;
    private @NonNull final Long duration;

    /**
     * Constructor.
     *
     * @param operation
     * @param duration in milliseconds
     */
    public GeneralDurationSearch(final BinaryCondition.Op operation, @NonNull final Long duration) {
        super(duration);
        this.operation = operation;
        this.duration = duration;
    }

    @Override
    public boolean matchCriteria(@NonNull final JMetaData jMetaData) {
        final boolean result = !getStreamsIDInFileMatchingCriteria(jMetaData).isEmpty();
        return result;
    }

    @Override
    public @NonNull List<@NonNull Integer> getStreamsIDInFileMatchingCriteria(@NonNull final JMetaData jMetaData) {
        final List<@NonNull Integer> result = new ArrayList<>();

        final Long durationGeneral = jMetaData.getGeneral().getDurationAsLong();
        if (durationGeneral != null) {
            final Integer idAsInteger = jMetaData.getGeneral().getIDAsInteger();
            final boolean match = conditionMatch(durationGeneral, this.duration, this.operation);
            if (match && idAsInteger != null) {
                result.add(idAsInteger);
            }
        }
        return result;
    }

}
