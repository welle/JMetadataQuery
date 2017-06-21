package aka.jmetadataquery.main.types.search.operation.interfaces;

import java.io.File;

import org.eclipse.jdt.annotation.NonNull;

/**
 *
 * @author charlottew
 */
public interface OperatorSearchInterface {

    /**
     * Is the given file match the given query.
     *
     * @param currentFile
     * @return List of file founded
     */
    public boolean isFileMatchingCriteria(@NonNull final File currentFile);
}
