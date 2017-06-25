package aka.jmetadataquery.main.types.search.operation;

import java.io.File;
import java.util.ArrayList;
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

    private boolean sameStream = false;
    @NonNull
    private final List<@NonNull OperatorSearchInterface> queries = new ArrayList<>();

    /**
     * Constructor.
     */
    public AndSearch() {
        // Default constructor.
    }

    /**
     * Constructor.
     *
     * @param sameStream both query must be applied for the same streams ?
     */
    public AndSearch(final boolean sameStream) {
        this.sameStream = sameStream;
    }

    /**
     * Constructor.
     *
     * @param queries
     * @param sameStream both query must be applied for the same streams ?
     */
    public AndSearch(final boolean sameStream, @NonNull final OperatorSearchInterface @NonNull... queries) {
        for (final OperatorSearchInterface operatorSearchInterface : queries) {
            this.queries.add(operatorSearchInterface);
        }
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

    /**
     * Check for the same stream ?
     *
     * @param sameStream
     */
    public void setSameStream(final boolean sameStream) {
        this.sameStream = sameStream;
    }

    @Override
    public @NonNull List<@NonNull Integer> getStreamsIDInFileMatchingCriteria(@NonNull final File currentFile) {
        return new ArrayList<>();
    }
}
