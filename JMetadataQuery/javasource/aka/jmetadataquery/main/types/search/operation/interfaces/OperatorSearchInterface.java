package aka.jmetadataquery.main.types.search.operation.interfaces;

import java.io.File;
import java.util.List;

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
    public List<@NonNull Integer> getStreamsIDInFileMatchingCriteria(@NonNull final File currentFile);
}
