package aka.jmetadataquery.main.types.search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jdt.annotation.NonNull;

import com.healthmarketscience.sqlbuilder.BinaryCondition;

import aka.jmetadata.main.JMetaData;
import aka.jmetadataquery.main.types.search.operation.interfaces.OperatorSearchInterface;

/**
 * Abstract class to be implemented to be a search.
 *
 * @author charlottew
 *
 * @param <S> Search enum
 * @param <T> Type for compare
 */
public abstract class Criteria<S, T extends Comparable<T>> implements OperatorSearchInterface {

    /**
     * Constructor.
     *
     * @param s
     */
    public Criteria(@NonNull final S s) {
        // Nothing to do
        // Purpose is to force the parent object to be T typed
    }

    /**
     * Return true if the given file (enclosed in jMetaData) match the current criteria.
     *
     * @param jMetaData
     * @return <code>true</code> if the file match the criteria
     */
    public abstract boolean matchCriteria(@NonNull JMetaData jMetaData);

    /**
     * Which stream in the given file match the given query.
     *
     * @param jMetaData
     * @return List of stream id founded
     */
    @NonNull
    public abstract Set<@NonNull Integer> getStreamsIDInFileMatchingCriteria(@NonNull JMetaData jMetaData);

    /**
     * Is the given file match the given query.
     *
     * @param currentFile
     * @return <code>true</code>If file match the given criteria
     */
    @Override
    @NonNull
    public List<@NonNull Integer> getStreamsIDInFileMatchingCriteria(@NonNull final File currentFile) {
        final List<@NonNull Integer> result = new ArrayList<>();
        final JMetaData jMetaData = new JMetaData();
        jMetaData.open(currentFile);
        if (jMetaData.open(currentFile)) {
            result.addAll(getStreamsIDInFileMatchingCriteria(jMetaData));
        }
        jMetaData.close();

        return result;
    }

    /**
     * Is the given file match the given query.
     *
     * @param currentFile
     * @return <code>true</code>If file match the given criteria
     */
    @Override
    public boolean isFileMatchingCriteria(@NonNull final File currentFile) {
        boolean isFileMatchingCriteria = true;
        final JMetaData jMetaData = new JMetaData();
        if (jMetaData.open(currentFile)) {
            isFileMatchingCriteria = matchCriteria(jMetaData);
        } else {
            Logger.getAnonymousLogger().logp(Level.SEVERE, "Criteria", "isFileMatchingCriteria", "Can not open file!");
            throw new RuntimeException();
        }
        jMetaData.close();

        return isFileMatchingCriteria;
    }

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
                result = wantedValue.compareTo(currentValue) == 0;
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
                result = wantedValue.compareTo(currentValue) != 0;
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
        assert result != null;
        return result;
    }
}
