package aka.jmetadataquery.main.types.search.operation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import aka.jmetadata.main.JMetaData;
import aka.jmetadataquery.main.types.search.operation.interfaces.OperatorSearchInterface;

/**
 * Combo condition "AND" of two OperatorSearchInterface.
 *
 * @author charlottew
 */
public class AndSearch implements OperatorSearchInterface {

    private @NonNull final OperatorSearchInterface query1;
    private @NonNull final OperatorSearchInterface query2;
    private final boolean sameStream;

    /**
     * Constructor.
     *
     * @param query1
     * @param query2
     */
    public AndSearch(@NonNull final OperatorSearchInterface query1, @NonNull final OperatorSearchInterface query2, final boolean sameStream) {
        this.query1 = query1;
        this.query2 = query2;
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
        boolean isFileMatchingCriteria;
        if (this.sameStream) {
            final List<Integer> idList = this.query1.getStreamsIDInFileMatchingCriteria(jMetaData);
        } else {
            isFileMatchingCriteria = this.query1.isFileMatchingCriteria(currentFile) && this.query2.isFileMatchingCriteria(currentFile);
        }
        return isFileMatchingCriteria;
    }

    @Override
    public @NonNull List<@NonNull Integer> getStreamsIDInFileMatchingCriteria(@NonNull final JMetaData jMetaData) {
        return new ArrayList<>();
    }

}
