package aka.jmetadataquery;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.annotation.NonNull;

import aka.jmetadataquery.search.operation.interfaces.OperatorSearchInterface;

/**
 * Main class.
 * Entry point for all search in metadata files.
 *
 * @author cha
 */
public final class JMetadataQuery {

    /**
     * Search into the given directory (recursively if depth is set to <code>true</code>) for files matching the list of search criteria.
     *
     * @param directory root directory
     * @param depth if <code>true</code> search into sub-directories
     * @param query
     * @return List of file founded
     */
    @NonNull
    public List<@NonNull File> searchIntoDirectory(@NonNull final File directory, final boolean depth, @NonNull final OperatorSearchInterface query) {
        final List<@NonNull File> result = new ArrayList<>();

        if (directory.isDirectory()) {
            final List<@NonNull File> files = (List<@NonNull File>) FileUtils.listFiles(directory, null, depth);
            for (final @NonNull File currentFile : files) {
                if (query.isFileMatchingCriteria(currentFile)) {
                    result.add(currentFile);
                }
            }
        }

        return result;
    }
}
