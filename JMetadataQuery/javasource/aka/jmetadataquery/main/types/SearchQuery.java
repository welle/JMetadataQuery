package aka.jmetadataquery.main.types;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

import com.healthmarketscience.sqlbuilder.ComboCondition;

/**
 * Search query.
 *
 * @author charlottew
 */
public final class SearchQuery {

    private @NonNull final Map<SearchKeys, ComboCondition.Op> searchKeysMap = new HashMap<>();

    /**
     * Constructor.
     *
     * @param searchKey searchkey
     * @param operation ComboCondition.Op
     */
    public SearchQuery(@NonNull final SearchKeys searchKey, final ComboCondition.Op operation) {
        this.searchKeysMap.put(searchKey, operation);
    }

}
