package aka.jmetadataquery.main.types;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import aka.jmetadataquery.main.types.search.Criteria;

/**
 * Search query.
 *
 * @author charlottew
 */
public final class SearchQuery {

    @NonNull
    private final List<@NonNull Criteria> searchEnumList = new ArrayList<>();

    /**
     * Add search criteria.
     *
     * @param searchKeys
     */
    public void addSearchCriteria(@NonNull final Criteria @NonNull... searchKeys) {
        for (final @NonNull Criteria searchkey : searchKeys) {
            this.searchEnumList.add(searchkey);
        }
    }

    /**
     * Remove search criteria.
     *
     * @param searchKeys
     */
    public void removeSearchCriteria(@NonNull final Criteria @NonNull... searchKeys) {
        for (final Criteria searchkey : searchKeys) {
            this.searchEnumList.remove(searchkey);
        }
    }

    public @NonNull List<@NonNull Criteria> getSearchs() {
        return this.searchEnumList;
    }

}
