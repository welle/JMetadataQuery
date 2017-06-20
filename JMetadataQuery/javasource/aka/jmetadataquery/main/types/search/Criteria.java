package aka.jmetadataquery.main.types.search;

import org.eclipse.jdt.annotation.NonNull;

import com.healthmarketscience.sqlbuilder.BinaryCondition;

import aka.jmetadata.main.JMetaData;

public abstract class Criteria<S, T extends Comparable<T>> {

    public Criteria(final S s) {
        // Nothing to do
    }

    /**
     * Return true if the given file (enclosed in jMetaData) match the current criteria.
     *
     * @param jMetaData
     * @return <code>true</code> if the file match the criteria
     */
    public abstract boolean matchCriteria(@NonNull JMetaData jMetaData);

    /**
     * Are the both param match the criteria ?
     *
     * @param wantedValue
     * @param currentValue
     * @param operation
     * @return <code>true</code> if the both param match the criteria
     */
    public boolean conditionMatch(@NonNull final T wantedValue, @NonNull final T currentValue, final BinaryCondition.Op operation) {
        boolean result = false;

        if (operation == BinaryCondition.Op.EQUAL_TO) {
            result = wantedValue.equals(currentValue);
        } else if (operation == BinaryCondition.Op.GREATER_THAN) {
            result = wantedValue.compareTo(currentValue) > 0;
        } else if (operation == BinaryCondition.Op.GREATER_THAN_OR_EQUAL_TO) {
            result = wantedValue.compareTo(currentValue) >= 0;
        } else if (operation == BinaryCondition.Op.LESS_THAN) {
            result = wantedValue.compareTo(currentValue) < 0;
        } else if (operation == BinaryCondition.Op.LESS_THAN_OR_EQUAL_TO) {
            result = wantedValue.compareTo(currentValue) <= 0;
        } else if (operation == BinaryCondition.Op.LIKE) {
            if (wantedValue instanceof String) {
                final String wantedValueString = (String) wantedValue;
                final String currentValueString = (String) wantedValue;
                result = currentValueString.contains(wantedValueString);
            }
        } else if (operation == BinaryCondition.Op.NOT_EQUAL_TO) {
            result = !wantedValue.equals(currentValue);
        } else if (operation == BinaryCondition.Op.NOT_LIKE) {
            final String wantedValueString = (String) wantedValue;
            final String currentValueString = (String) wantedValue;
            result = !currentValueString.contains(wantedValueString);
        }

        return result;
    }
}
