package aka.jmetadataquery;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jdt.annotation.NonNull;

import aka.jmetadataquery.consumer.FileConsumer;
import aka.jmetadataquery.consumer.FileConsumerContext;
import aka.jmetadataquery.helpers.FileHelper;
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
     * @param directory path of the root directory
     * @param recursive if <code>true</code> search into sub-directories
     * @param query
     * @return List of file founded
     */
    @NonNull
    public <T extends OperatorSearchInterface> List<@NonNull File> searchIntoDirectory(@NonNull final String directory, final boolean recursive, @NonNull final T query) {

        final List<@NonNull FileConsumerContext> appliedIntoDirectoryFileConsumerContextList = applyIntoDirectory(directory, recursive, query);
        final List<@NonNull File> result = appliedIntoDirectoryFileConsumerContextList.stream()
                .filter(line -> line.getResult())
                .map(line -> line.getFile())
                .collect(Collectors.toList());

        return result;
    }

    /**
     * Search into the given directory (recursively if depth is set to <code>true</code>) for files and apply the list of search criteria.
     *
     * @param directory path of the root directory
     * @param recursive if <code>true</code> search into sub-directories
     * @param query
     * @return List of FileConsumerContext
     */
    @NonNull
    public <T extends OperatorSearchInterface> List<@NonNull FileConsumerContext> applyIntoDirectory(@NonNull final String directory, final boolean recursive, @NonNull final T query) {
        final List<@NonNull FileConsumerContext> fileConsumerContextsList = FileHelper.getFileConsumerContextInPath(directory, query, recursive);
        final FileConsumer consumer = new FileConsumer();
        fileConsumerContextsList.stream()
                .parallel()
                .forEach(consumer);

        return fileConsumerContextsList;
    }
}
