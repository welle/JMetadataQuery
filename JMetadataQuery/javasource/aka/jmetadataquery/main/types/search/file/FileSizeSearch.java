package aka.jmetadataquery.main.types.search.file;

import java.util.ArrayList;
import java.util.List;

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
        final boolean result = !getStreamsIDInFileMatchingCriteria(jMetaData).isEmpty();
        return result;
    }

    @Override
    public @NonNull List<@NonNull Integer> getStreamsIDInFileMatchingCriteria(@NonNull final JMetaData jMetaData) {
        final List<@NonNull Integer> result = new ArrayList<>();

        final Long size = jMetaData.getGeneral().getFileSizeAsLong();
        if (size != null) {
            Integer idAsInteger = jMetaData.getGeneral().getIDAsInteger();
            final boolean match = conditionMatch(size, this.fileSize, this.operation);
            if (match) {
                if (idAsInteger == null) {
                    idAsInteger = Integer.valueOf(-1);
                }
                result.add(idAsInteger);
            }
        }
        return result;
    }

}
