package aka.jmetadataquery.main.types.search.file;

import org.eclipse.jdt.annotation.NonNull;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadataquery.main.types.search.Criteria;

/**
 * File size search.
 *
 * @author charlottew
 */
public class FileSizeSearch extends Criteria<Long, Long> {

    private final Op operation;
    private @NonNull final Long fileSize;

    /**
     * Constructor.
     *
     * @param operation
     * @param fileSize in byte
     */
    public FileSizeSearch(final BinaryCondition.Op operation, @NonNull final Long fileSize) {
        super(fileSize);
        this.operation = operation;
        this.fileSize = fileSize;
    }

    @Override
    public boolean matchCriteria(@NonNull final JMetaData jMetaData) {
        final Long size = jMetaData.getGeneral().getFileSizeAsLong();

        boolean result = false;
        if (size != null) {
            result = conditionMatch(size, this.fileSize, this.operation);
        }
        return result;
    }

}
