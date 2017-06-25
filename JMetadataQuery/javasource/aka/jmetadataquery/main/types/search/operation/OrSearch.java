package aka.jmetadataquery.main.types.search.operation;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import aka.jmetadataquery.main.types.search.operation.interfaces.OperatorSearchInterface;

/**
 * Combo condition "OR" of two OperatorSearchInterface.
 *
 * @author charlottew
 */
public class OrSearch implements OperatorSearchInterface {

    @NonNull
    private List<@NonNull OperatorSearchInterface> queries = new ArrayList<>();

    /**
     * Constructor.
     *
     */
    public OrSearch() {
        // Default constructor.
    }

    /**
     * Constructor.
     *
     * @param queries
     */
    public OrSearch(@NonNull final OperatorSearchInterface @NonNull... queries) {
        this.queries = Arrays.asList(queries);
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
        for (final OperatorSearchInterface operatorSearchInterface : this.queries) {
            isFileMatchingCriteria = operatorSearchInterface.isFileMatchingCriteria(currentFile) || isFileMatchingCriteria;
        }

        return isFileMatchingCriteria;
    }

    @Override
    public @NonNull List<@NonNull Integer> getStreamsIDInFileMatchingCriteria(@NonNull final File currentFile) {
        return new ArrayList<>();
    }

    /**
     * Add search.
     *
     * @param operatorSearchInterface
     */
    public void addSearch(@NonNull final OperatorSearchInterface operatorSearchInterface) {
        this.queries.add(operatorSearchInterface);
    }
}
