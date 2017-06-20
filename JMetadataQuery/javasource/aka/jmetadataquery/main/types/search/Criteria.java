package aka.jmetadataquery.main.types.search;

import org.eclipse.jdt.annotation.NonNull;

import com.healthmarketscience.sqlbuilder.BinaryCondition;

import aka.jmetadata.main.JMetaData;

/**
 * Abstract class to be implemented to be a search.
 *
 * @author charlottew
 *
 * @param <S> Search enum
 * @param <T> Type for compare
 */
public abstract class Criteria<S, T extends Comparable<T>> {

    /**
     * Constructor.
     *
     * @param s
     */
    public Criteria(@NonNull final S s) {
        // Nothing to do
        // Purpose is to force the object
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
            if (wantedValue instanceof String) {
                final String wantedValueString = getString((@NonNull String) wantedValue);
                final String currentValueString = getString((@NonNull String) currentValue);
                result = currentValueString.equals(wantedValueString);
            } else {
                result = wantedValue.equals(currentValue);
            }
        } else if (operation == BinaryCondition.Op.GREATER_THAN) {
            if (wantedValue instanceof String) {
                final String wantedValueString = getString((@NonNull String) wantedValue);
                final String currentValueString = getString((@NonNull String) currentValue);
                result = currentValueString.compareTo(wantedValueString) > 0;
            } else {
                result = wantedValue.compareTo(currentValue) > 0;
            }
        } else if (operation == BinaryCondition.Op.GREATER_THAN_OR_EQUAL_TO) {
            if (wantedValue instanceof String) {
                final String wantedValueString = getString((@NonNull String) wantedValue);
                final String currentValueString = getString((@NonNull String) currentValue);
                result = currentValueString.compareTo(wantedValueString) >= 0;
            } else {
                result = wantedValue.compareTo(currentValue) >= 0;
            }
        } else if (operation == BinaryCondition.Op.LESS_THAN) {
            if (wantedValue instanceof String) {
                final String wantedValueString = getString((@NonNull String) wantedValue);
                final String currentValueString = getString((@NonNull String) currentValue);
                result = currentValueString.compareTo(wantedValueString) < 0;
            } else {
                result = wantedValue.compareTo(currentValue) < 0;
            }
        } else if (operation == BinaryCondition.Op.LESS_THAN_OR_EQUAL_TO) {
            if (wantedValue instanceof String) {
                final String wantedValueString = getString((@NonNull String) wantedValue);
                final String currentValueString = getString((@NonNull String) currentValue);
                result = currentValueString.compareTo(wantedValueString) <= 0;
            } else {
                result = wantedValue.compareTo(currentValue) <= 0;
            }
        } else if (operation == BinaryCondition.Op.LIKE) {
            if (wantedValue instanceof String) {
                final String wantedValueString = getString((@NonNull String) wantedValue);
                final String currentValueString = getString((@NonNull String) currentValue);
                result = currentValueString.contains(wantedValueString);
            }
        } else if (operation == BinaryCondition.Op.NOT_EQUAL_TO) {
            if (wantedValue instanceof String) {
                final String wantedValueString = getString((@NonNull String) wantedValue);
                final String currentValueString = getString((@NonNull String) currentValue);
                result = !currentValueString.equals(wantedValueString);
            } else {
                result = !wantedValue.equals(currentValue);
            }
        } else if (operation == BinaryCondition.Op.NOT_LIKE) {
            if (wantedValue instanceof String) {
                final String wantedValueString = getString((@NonNull String) wantedValue);
                final String currentValueString = getString((@NonNull String) currentValue);
                result = !currentValueString.contains(wantedValueString);
            }
        }

        return result;
    }

    @NonNull
    private String getString(@NonNull final String value) {
        String result = value;

        result = result.trim();
        result = result.toLowerCase();

        return result;
    }
}
