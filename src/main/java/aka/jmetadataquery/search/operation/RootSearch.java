package aka.jmetadataquery.search.operation;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

import aka.jmetadataquery.search.operation.interfaces.OperatorSearchInterface;

/**
 * Root search.
 *
 * @author charlottew
 */
public class RootSearch implements OperatorSearchInterface {

    @NonNull
    private final OperatorSearchInterface query;

    /**
     * Constructor.
     *
     * @param query
     */
    public RootSearch(@NonNull final OperatorSearchInterface query) {
        this.query = query;
    }

    /**
     * Is the given file match the query.
     *
     * @param currentFile
     * @return <code>true</code> if the given file match the query
     */
    @Override
    public boolean isFileMatchingCriteria(@NonNull final File currentFile) {

        return this.query.isFileMatchingCriteria(currentFile);
    }

    @Override
    public @NonNull Map<@NonNull Integer, Boolean> getStreamsIDInFileMatchingCriteria(@NonNull final File currentFile) {
        return new HashMap<>();
    }
}
