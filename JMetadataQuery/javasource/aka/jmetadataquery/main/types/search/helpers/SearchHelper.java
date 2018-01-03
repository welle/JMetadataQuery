package aka.jmetadataquery.main.types.search.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Search helper.
 *
 * @author charlottew
 */
public class SearchHelper {

    /**
     * All match ?
     *
     * @param idMapList
     * @param size
     * @param allMustMatch
     * @return <code>true</code> if match
     */
    public static boolean isMatching(@NonNull final List<@NonNull Map<@NonNull Integer, Boolean>> idMapList, final int size, final boolean allMustMatch) {
        final @NonNull List<@NonNull Boolean> resultList = getResultList(idMapList, size, allMustMatch);
        return getMatchingResult(resultList);
    }

    /**
     * Get result list.
     *
     * @param idMapList
     * @param size
     * @param allMustMatch
     * @return list of result
     */
    @NonNull
    public static List<@NonNull Boolean> getResultList(@NonNull final List<@NonNull Map<@NonNull Integer, Boolean>> idMapList, final int size, final boolean allMustMatch) {
        final List<@NonNull Boolean> result = new ArrayList<>();

        // Check if at least one of Integer (stream Id) is in each map and has same boolean
        // Get list of Integer present in each map
        final List<@NonNull Integer> presentIdList = new ArrayList<>();
        for (final @NonNull Map<@NonNull Integer, Boolean> map : idMapList) {
            presentIdList.addAll(map.keySet());
        }

        final Map<@NonNull Integer, List<@NonNull Boolean>> idBooleanListMap = new HashMap<>();
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
        for (final Entry<@NonNull Integer, List<@NonNull Boolean>> entry : idBooleanListMap.entrySet()) {
            if (allMustMatch) {
                final boolean allEqual = entry.getValue().stream().distinct().limit(2).count() <= 1;
                if (allEqual) {
                    result.add(entry.getValue().get(0));
                } else {
                    result.add(Boolean.FALSE);
                }
            } else {
                final boolean atLeastOne = entry.getValue().contains(Boolean.valueOf(true));
                result.add(Boolean.valueOf(atLeastOne));
            }
        }

        return result;
    }

    @SuppressWarnings("null")
    private static boolean getMatchingResult(@NonNull final List<@NonNull Boolean> resultList) {
        boolean isMatching = true;
        if (resultList.size() >= 1) {
            isMatching = resultList.get(0).booleanValue();
        }
        for (int i = 1; i < resultList.size(); i++) {
            isMatching = resultList.get(i).booleanValue() || isMatching;
        }

        return isMatching;
    }

    private SearchHelper() {
        // Singleton
    }
}
