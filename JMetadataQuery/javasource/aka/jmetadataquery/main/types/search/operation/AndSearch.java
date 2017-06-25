package aka.jmetadataquery.main.types.search.operation;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import aka.jmetadataquery.main.types.search.operation.interfaces.OperatorSearchInterface;

/**
 * Combo condition "AND" of two OperatorSearchInterface.
 *
 * @author charlottew
 */
public class AndSearch implements OperatorSearchInterface {

    private final boolean sameStream;
    @NonNull
    private final List<@NonNull OperatorSearchInterface> queries;

    /**
     * Constructor.
     *
     * @param queries
     * @param sameStream both query must be applied for the same streams ?
     */
    public AndSearch(final boolean sameStream, @NonNull final OperatorSearchInterface @NonNull... queries) {
        this.queries = Arrays.asList(queries);
        this.sameStream = sameStream;
    }

    /**
     * Is the given file match the given query.
     *
     * @param currentFile
     * @return List of file founded
     */
    @Override
    public boolean isFileMatchingCriteria(@NonNull final File currentFile) {
        boolean isFileMatchingCriteria = true;

        if (this.sameStream) {
            final List<@NonNull List<@NonNull Integer>> idListList = new ArrayList<>();
            for (final OperatorSearchInterface operatorSearchInterface : this.queries) {
                final List<Integer> idList = operatorSearchInterface.getStreamsIDInFileMatchingCriteria(currentFile);
                idListList.add(idList);
            }
            List<Integer> idList1 = null;
            for (final List<Integer> idList : idListList) {
                if (idList1 != null) {
                    isFileMatchingCriteria = !Collections.disjoint(idList1, idList);
                }
                idList1 = idList;
            }
        } else {
            for (final OperatorSearchInterface operatorSearchInterface : this.queries) {
                isFileMatchingCriteria = operatorSearchInterface.isFileMatchingCriteria(currentFile) || isFileMatchingCriteria;
            }
        }
        return isFileMatchingCriteria;
    }

    /**
     * Add search.
     *
     * @param operatorSearchInterface
     */
    public void addSearch(@NonNull final OperatorSearchInterface operatorSearchInterface) {
        this.queries.add(operatorSearchInterface);
    }

    @Override
    public @NonNull List<@NonNull Integer> getStreamsIDInFileMatchingCriteria(@NonNull final File currentFile) {
        return new ArrayList<>();
    }
}
