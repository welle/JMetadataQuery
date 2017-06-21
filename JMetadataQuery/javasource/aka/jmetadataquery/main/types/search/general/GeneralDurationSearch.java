package aka.jmetadataquery.main.types.search.general;

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
        final Long durationGeneral = jMetaData.getGeneral().getDurationAsLong();

        boolean result = false;
        if (durationGeneral != null) {
            result = conditionMatch(durationGeneral, this.duration, this.operation);
        }
        return result;
    }

}
