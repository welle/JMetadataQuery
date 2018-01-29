package aka.jmetadataquery.search.constants.conditions;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Enum representing the binary midfix operations supported in a SQL
 * condition, e.g.
 * <code>"(&lt;column1&gt; &lt;binaryOp&gt; &lt;column2&gt;)"</code>.
 */
public enum Operator {

    /**
     * Less than ( < ).
     */
    LESS_THAN(" < "),

    /**
     * Less than or equal to ( <= ).
     */
    LESS_THAN_OR_EQUAL_TO(" <= "),

    /**
     * Greater than ( > ).
     */
    GREATER_THAN(" > "),

    /**
     * Greater than or equal to ( >= ).
     */
    GREATER_THAN_OR_EQUAL_TO(" >= "),

    /**
     * Equal to ( = ).
     */
    EQUAL_TO(" = "),

    /**
     * Not equal to ( <> ).
     */
    NOT_EQUAL_TO(" <> "),

    /**
     * Like ( LIKE ).
     */
    LIKE(" LIKE "),

    /**
     * Not like ( NOT LIKE ).
     */
    NOT_LIKE(" NOT LIKE ");

    @NonNull
    private final String opStr;

    private Operator(@NonNull final String opStr) {
        this.opStr = opStr;
    }

    @Override
    public String toString() {
        return this.opStr;
    }
}