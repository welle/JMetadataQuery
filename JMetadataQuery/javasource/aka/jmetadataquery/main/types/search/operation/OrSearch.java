package aka.jmetadataquery.main.types.search.operation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

import aka.jmetadataquery.main.types.search.helpers.SearchHelper;
import aka.jmetadataquery.main.types.search.operation.interfaces.OperatorSearchInterface;

/**
 * Combo condition "OR" of n OperatorSearchInterface.
 *
 * @author charlottew
 */
public class OrSearch extends AsbtractOperatorSearch {

    /**
     * Constructor.
     */
    public OrSearch() {
        // Default constructor.
        super();
    }

    /**
     * Constructor.
     *
     * @param sameStream queries must be applied for the same streams ?
     */
    public OrSearch(final boolean sameStream) {
        super(sameStream);
    }

    /**
     * Constructor.
     *
     * @param queries
     * @param sameStream queries must be applied for the same streams ?
     */
    public OrSearch(final boolean sameStream, @NonNull final OperatorSearchInterface @NonNull... queries) {
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
            final List<@NonNull Map<@NonNull Integer, Boolean>> idMapList = new ArrayList<>();
            for (final OperatorSearchInterface operatorSearchInterface : getQueries()) {
                final @NonNull Map<@NonNull Integer, Boolean> idList = operatorSearchInterface.getStreamsIDInFileMatchingCriteria(currentFile);
                idMapList.add(idList);
            }
            isFileMatchingCriteria = SearchHelper.isMatching(idMapList, getQueries().size(), false);
        } else {
            final List<@NonNull OperatorSearchInterface> queryList = getQueries();
            if (queryList.size() > 0) {
                final OperatorSearchInterface firstOperatorInterface = queryList.get(0);
                isFileMatchingCriteria = firstOperatorInterface.isFileMatchingCriteria(currentFile);
                for (int i = 1; i < queryList.size(); i++) {
                    final OperatorSearchInterface operatorSearchInterface = queryList.get(i);
                    isFileMatchingCriteria = operatorSearchInterface.isFileMatchingCriteria(currentFile) || isFileMatchingCriteria;
                    if (isFileMatchingCriteria) {
                        // no need to continue
                        break;
                    }
                }
            }
        }

        return isFileMatchingCriteria;
    }

}
