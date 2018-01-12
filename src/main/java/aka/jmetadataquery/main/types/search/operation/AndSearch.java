package aka.jmetadataquery.main.types.search.operation;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

import aka.jmetadataquery.main.types.search.helpers.SearchHelper;
import aka.jmetadataquery.main.types.search.operation.interfaces.OperatorSearchInterface;

/**
 * Combo condition "AND" of n OperatorSearchInterface.
 *
 * @author charlottew
 */
public class AndSearch extends AsbtractOperatorSearch {

    /**
     * Constructor.
     */
    public AndSearch() {
        // Default constructor.
        super();
    }

    /**
     * Constructor.
     *
     * @param sameStream queries must be applied for the same streams ?
     */
    public AndSearch(final boolean sameStream) {
        super(sameStream);
    }

    /**
     * Constructor.
     *
     * @param queries
     * @param sameStream queries must be applied for the same streams ?
     */
    public AndSearch(final boolean sameStream, @NonNull final OperatorSearchInterface @NonNull... queries) {
        super(sameStream, queries);
    }

    /**
     * Is the given file match the query.
     *
     * @param currentFile
     * @return <code>true</code> if the given file match the query
     */
    @Override
    public boolean isFileMatchingCriteria(@NonNull final File currentFile) {
        boolean isFileMatchingCriteria = true;

        if (checkSameStream()) {
            final List<@NonNull Map<@NonNull Integer, Boolean>> idMapList = getIdMapList(currentFile, getQueries());
            isFileMatchingCriteria = SearchHelper.isMatching(idMapList, getQueries().size(), true);
        } else {
            for (final OperatorSearchInterface operatorSearchInterface : getQueries()) {
                isFileMatchingCriteria = operatorSearchInterface.isFileMatchingCriteria(currentFile) && isFileMatchingCriteria;
                if (!isFileMatchingCriteria) {
                    // no need to continue
                    break;
                }
            }
        }
        return isFileMatchingCriteria;
    }

    @Override
    public @NonNull Map<@NonNull Integer, Boolean> getStreamsIDInFileMatchingCriteria(@NonNull final File currentFile) {
        return new HashMap<>();
    }

}
