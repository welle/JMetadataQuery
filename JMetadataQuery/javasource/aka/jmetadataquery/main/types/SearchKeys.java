package aka.jmetadataquery.main.types;

import org.eclipse.jdt.annotation.NonNull;

import com.healthmarketscience.sqlbuilder.Condition;

import aka.jmetadataquery.main.types.constants.interfaces.Search;

/**
 * Search keys manage the search enum with a criteria.
 *
 * @author charlottew
 */
public final class SearchKeys {

    private @NonNull final Search<?> search;
    private @NonNull final Condition condition;

    /**
     * Constructor.
     *
     * @param search search enum value
     * @param condition criteria condition
     */
    public SearchKeys(@NonNull final Search<?> search, @NonNull final Condition condition) {
        this.search = search;
        this.condition = condition;
    }

    /**
     * @return the search
     */
    @NonNull
    public Search<?> getSearch() {
        return this.search;
    }

    /**
     * @return the condition
     */
    @NonNull
    public Condition getCondition() {
        return this.condition;
    }
}
