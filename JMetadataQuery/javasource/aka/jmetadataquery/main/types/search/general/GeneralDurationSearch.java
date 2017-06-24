package aka.jmetadataquery.main.types.search.general;

import java.util.HashSet;
import java.util.Set;

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
    public @NonNull Set<@NonNull Integer> getStreamsIDInFileMatchingCriteria(@NonNull final JMetaData jMetaData) {
        final Set<@NonNull Integer> result = new HashSet<>();

        final Long durationGeneral = jMetaData.getGeneral().getDurationAsLong();
        if (durationGeneral != null) {
            Integer idAsInteger = jMetaData.getGeneral().getIDAsInteger();
            if (idAsInteger == null) {
                idAsInteger = Integer.valueOf(-1);
            }
            final boolean match = conditionMatch(durationGeneral, this.duration, this.operation);
            if (match) {
                result.add(idAsInteger);
            }
        }
        return result;
    }

}
