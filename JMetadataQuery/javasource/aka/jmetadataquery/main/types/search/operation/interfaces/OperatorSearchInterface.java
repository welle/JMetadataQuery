package aka.jmetadataquery.main.types.search.operation.interfaces;

import java.io.File;
import java.util.Map;

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

    /**
     * Which stream in the given file match the given query.
     *
     * @param currentFile
     * @return List of stream id founded
     */
    @NonNull
    public Map<@NonNull Integer, Boolean> getStreamsIDInFileMatchingCriteria(@NonNull final File currentFile);
}
