package aka.jmetadataquery.main.types.search.operation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import aka.jmetadataquery.main.types.search.operation.interfaces.OperatorSearchInterface;

/**
 * Combo condition "AND" of n OperatorSearchInterface.
 *
 * @author charlottew
 */
public abstract class AsbtractOperatorSearch implements OperatorSearchInterface {

    private boolean sameStream = false;
    @NonNull
    private final List<@NonNull OperatorSearchInterface> queries = new ArrayList<>();

    /**
     * Constructor.
     */
    public AsbtractOperatorSearch() {
        // Default constructor.
    }

    /**
     * Constructor.
     *
     * @param sameStream queries must be applied for the same streams ?
     */
    public AsbtractOperatorSearch(final boolean sameStream) {
        this.sameStream = sameStream;
    }

    /**
     * Constructor.
     *
     * @param queries
     * @param sameStream queries must be applied for the same streams ?
     */
    public AsbtractOperatorSearch(final boolean sameStream, @NonNull final OperatorSearchInterface @NonNull... queries) {
        for (final OperatorSearchInterface operatorSearchInterface : queries) {
            this.queries.add(operatorSearchInterface);
        }
        this.sameStream = sameStream;
    }

    /**
     * Add search.
     *
     * @param operatorSearchInterfaces
     */
    public void addSearch(@NonNull final OperatorSearchInterface @NonNull... operatorSearchInterfaces) {
        for (final OperatorSearchInterface operatorSearchInterface : operatorSearchInterfaces) {
            this.queries.add(operatorSearchInterface);
        }
    }

    /**
     * Check for the same stream ?
     *
     * @param sameStream
     */
    public void setSameStream(final boolean sameStream) {
        this.sameStream = sameStream;
    }

    @Override
    public @NonNull Map<@NonNull Integer, Boolean> getStreamsIDInFileMatchingCriteria(@NonNull final File currentFile) {
        return new HashMap<>();
    }

    /**
     * Is the test must be made on the same stream(s) ?
     *
     * @return <code>true</code> if the test must be made on the same stream(s)
     */
    public boolean checkSameStream() {
        return this.sameStream;
    }

    /**
     * Get list of queries.
     *
     * @return list of queries
     */
    @NonNull
    public List<@NonNull OperatorSearchInterface> getQueries() {
        return this.queries;
    }

    /**
     * Check if both list have same items.
     *
     * @param list1
     * @param list2
     * @return <code>true</code> if both list have same items.
     */
    public boolean equalLists(@Nullable final List<@NonNull Integer> list1, @Nullable final List<@NonNull Integer> list2) {
        List<@NonNull Integer> one = list1;
        List<@NonNull Integer> two = list2;
        if (one == null && two == null) {
            return true;
        }

        if ((one == null && two != null) || (one != null && two == null)) {
            return false;
        }

        // to avoid messing the order of the lists we will use a copy
        // as noted in comments by A. R. S.
        one = new ArrayList<>(one);
        two = new ArrayList<>(two);

        Collections.sort(one);
        Collections.sort(two);
        if (one.size() > two.size()) {
            return one.containsAll(two);
        } else {
            return two.containsAll(one);
        }
    }

    /**
     * Get result list.
     *
     * @param idMapList
     * @param size
     * @return list of result
     */
    @NonNull
    public List<@NonNull Boolean> getResultList(@NonNull final List<@NonNull Map<@NonNull Integer, Boolean>> idMapList, final int size) {
        final List<@NonNull Boolean> result = new ArrayList<>();

        // Check if at least one of Integer (stream Id) is in each map and has same boolean
        // Get list of Integer present in each map
        final List<@NonNull Integer> presentIdList = new ArrayList<>();
        for (final @NonNull Map<@NonNull Integer, Boolean> map : idMapList) {
            presentIdList.addAll(map.keySet());
        }

        final Map<@NonNull Integer, List<Boolean>> idBooleanListMap = new HashMap<>();
        final Map<Integer, Long> idIdCountMap = presentIdList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        for (final Entry<Integer, Long> entry : idIdCountMap.entrySet()) {
            final Long count = entry.getValue();
            if (count.intValue() == size) {
                // Check if has same boolean
                final Integer id = entry.getKey();
                if (id != null) {
                    for (final @NonNull Map<@NonNull Integer, Boolean> entry2 : idMapList) {
                        final Boolean bool = entry2.get(id);
                        if (bool != null) {
                            List<Boolean> boolList = idBooleanListMap.get(id);
                            if (boolList == null) {
                                boolList = new ArrayList<>();
                                idBooleanListMap.put(id, boolList);
                            }
                            boolList.add(bool);
                        }
                    }
                }
            }
        }

        // Check if all bool in list in idBooleanListMap are the same
        for (final Entry<@NonNull Integer, List<Boolean>> entry : idBooleanListMap.entrySet()) {
            final boolean allEqual = entry.getValue().stream().distinct().limit(2).count() <= 1;
            if (allEqual) {
                final Boolean e = entry.getValue().get(0);
                if (e != null) {
                    result.add(e);
                }
            }
        }

        return result;
    }
}
