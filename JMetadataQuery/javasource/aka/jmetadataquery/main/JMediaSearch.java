package aka.jmetadataquery.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.annotation.NonNull;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.exception.LibNotfoundException;
import aka.jmetadataquery.main.types.SearchQuery;
import aka.jmetadataquery.main.types.search.Criteria;

/**
 * Main class.
 * Entry point for all search in metadata files.
 *
 * @author cha
 */
public final class JMediaSearch {

    @NonNull
    private static final Logger LOGGER = Logger.getLogger(JMediaSearch.class.getPackage().getName());

    /**
     * Search into the given directory (recursively if depth is set to <code>true</code>) for files matching the list of search criteria.
     *
     * @param directory root directory
     * @param depth if <code>true</code> search into sub-directories
     * @param query
     * @return List of file founded
     */
    @NonNull
    public List<@NonNull File> searchIntoDirectory(@NonNull final File directory, final boolean depth, @NonNull final SearchQuery query) {
        final List<@NonNull File> result = new ArrayList<>();

        if (directory.isDirectory()) {
            final List<@NonNull File> files = (List<@NonNull File>) FileUtils.listFiles(directory, null, depth);
            for (final @NonNull File currentFile : files) {
                if (isFileMatchingCriteria(currentFile, query)) {
                    result.add(currentFile);
                }
            }
        }

        return result;
    }

    /**
     * Is the given file match the given query.
     *
     * @param currentFile
     * @param query
     * @return List of file founded
     */
    public boolean isFileMatchingCriteria(@NonNull final File currentFile, @NonNull final SearchQuery query) {
        boolean isFileMatchingCriteria = true;
        try {
            final JMetaData jMetaData = new JMetaData();
            jMetaData.open(currentFile);
            if (jMetaData.open(currentFile)) {
                for (final Criteria<?, ?> criteria : query.getSearchs()) {
                    isFileMatchingCriteria = criteria.matchCriteria(jMetaData);
                    if (!isFileMatchingCriteria) {
                        break;
                    }
                }
            }
        } catch (IOException | LibNotfoundException e) {
            LOGGER.logp(Level.SEVERE, "JMediaSearch", "isFileMatchingCriteria", e.getMessage(), e);
        }

        return isFileMatchingCriteria;
    }

}
