package aka.jmetadataquery.search.types.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

import aka.jmetadata.main.JMetaData;
import aka.jmetadataquery.helpers.SearchHelper;
import aka.jmetadataquery.search.Criteria;
import aka.jmetadataquery.search.constants.conditions.Operator;

/**
 * General duration search.
 *
 * @author charlottew
 */
public class GeneralDurationSearch extends Criteria<Long, Long> {

    private final Operator operation;
    private @NonNull final Long duration;

    /**
     * Constructor.
     *
     * @param operation
     * @param duration in milliseconds
     */
    public GeneralDurationSearch(final Operator operation, @NonNull final Long duration) {
        super(duration);
        this.operation = operation;
        this.duration = duration;
    }

    @Override
    public boolean matchCriteria(@NonNull final JMetaData jMetaData) {
        final Map<@NonNull Integer, Boolean> map = getStreamsIDInFileMatchingCriteria(jMetaData);
        final List<@NonNull Map<@NonNull Integer, Boolean>> idMapList = new ArrayList<>();
        idMapList.add(map);
        return SearchHelper.isMatching(idMapList, 1, false);
    }

    @Override
    public @NonNull Map<@NonNull Integer, Boolean> getStreamsIDInFileMatchingCriteria(@NonNull final JMetaData jMetaData) {
        final Map<@NonNull Integer, Boolean> result = new HashMap<>();

        int i = -1;
        final Long durationGeneral = jMetaData.getGeneral().getDurationAsLong();
        if (durationGeneral != null) {
            Integer idAsInteger = jMetaData.getGeneral().getIDAsInteger();
            if (idAsInteger == null) {
                idAsInteger = Integer.valueOf(i);
                i--;
            }
            final boolean match = conditionMatch(durationGeneral, this.duration, this.operation);
            if (!result.containsKey(idAsInteger)) {
                result.put(idAsInteger, Boolean.valueOf(match));
            }
        }
        return result;
    }

}
