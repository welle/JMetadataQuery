package aka.jmetadataquery.main.types.search.operation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import aka.jmetadata.main.JMetaData;
import aka.jmetadataquery.main.types.search.operation.interfaces.OperatorSearchInterface;

/**
 * Combo condition "OR" of two OperatorSearchInterface.
 *
 * @author charlottew
 */
public class OrSearch implements OperatorSearchInterface {

    private @NonNull final OperatorSearchInterface query1;
    private @NonNull final OperatorSearchInterface query2;

    /**
     * Constructor.
     *
     * @param query1
     * @param query2
     */
    public OrSearch(@NonNull final OperatorSearchInterface query1, @NonNull final OperatorSearchInterface query2) {
        this.query1 = query1;
        this.query2 = query2;
    }

    /**
     * Is the given file match the given query.
     *
     * @param currentFile
     * @return List of file founded
     */
    @Override
    public boolean isFileMatchingCriteria(@NonNull final File currentFile) {
        final boolean isFileMatchingCriteria = this.query1.isFileMatchingCriteria(currentFile) || this.query2.isFileMatchingCriteria(currentFile);
        return isFileMatchingCriteria;
    }

    @Override
    public @NonNull List<@NonNull Integer> getStreamsIDInFileMatchingCriteria(@NonNull final JMetaData jMetaData) {
        return new ArrayList<>();
    }

}
