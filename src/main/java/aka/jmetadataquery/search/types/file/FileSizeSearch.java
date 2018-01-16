package aka.jmetadataquery.search.types.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadataquery.helpers.SearchHelper;
import aka.jmetadataquery.search.Criteria;

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
        final Map<@NonNull Integer, Boolean> map = getStreamsIDInFileMatchingCriteria(jMetaData);
        final List<@NonNull Map<@NonNull Integer, Boolean>> idMapList = new ArrayList<>();
        idMapList.add(map);
        return SearchHelper.isMatching(idMapList, 1, false);
    }

    @Override
    public @NonNull Map<@NonNull Integer, Boolean> getStreamsIDInFileMatchingCriteria(@NonNull final JMetaData jMetaData) {
        final Map<@NonNull Integer, Boolean> result = new HashMap<>();

        int i = -1;
        final Long size = jMetaData.getGeneral().getFileSizeAsLong();
        if (size != null) {
            Integer idAsInteger = jMetaData.getGeneral().getIDAsInteger();
            if (idAsInteger == null) {
                idAsInteger = Integer.valueOf(i);
                i--;
            }
            final boolean match = conditionMatch(size, this.fileSize, this.operation);
            if (!result.containsKey(idAsInteger)) {
                result.put(idAsInteger, Boolean.valueOf(match));
            }
        }
        return result;
    }

}
