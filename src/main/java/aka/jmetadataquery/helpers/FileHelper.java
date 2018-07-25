package aka.jmetadataquery.helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.annotation.NonNull;

import aka.jmetadataquery.consumer.FileConsumerContext;
import aka.jmetadataquery.search.operation.interfaces.OperatorSearchInterface;

/**
 * File utils.
 *
 * @author charlottew
 */
public class FileHelper {

    /**
     * Get List of files found in the given path.
     *
     * @param path where to search files
     * @param recursive if true all subdirectories are searched as well
     * @return List of files found recursively in the given path.
     */
    @NonNull
    public static List<@NonNull File> getFilesInPath(@NonNull final String path, final boolean recursive) {
        @NonNull
        final List<@NonNull File> result = new ArrayList<>();
        try {
            final File directory = new File(path);
            if (directory.isDirectory()) {
                final List<@NonNull File> files = (List<@NonNull File>) FileUtils.listFiles(directory, null, recursive);
                for (final @NonNull File currentFile : files) {
                    result.add(currentFile);
                }
            }
        } catch (final IllegalArgumentException e) {
            // Nothing to do
        }

        return result;
    }

    /**
     * Get List of FileConsumerContext based on the files found recursively in the given path.
     *
     * @param path where to search files
     * @param rootSearch the search to be applied to the files in the path
     * @param recursive if true all subdirectories are searched as well
     * @return List of FileConsumerContext based on the files found recursively in the given path.
     */
    @NonNull
    public static <T extends OperatorSearchInterface> List<@NonNull FileConsumerContext> getFileConsumerContextInPath(@NonNull final String path, @NonNull final T rootSearch, final boolean recursive) {
        final List<@NonNull File> fileList = getFilesInPath(path, recursive);
        List<@NonNull FileConsumerContext> result = fileList.stream()
                .map(p -> new FileConsumerContext(rootSearch, p))
                .collect(Collectors.toList());

        if (result == null) {
            result = new ArrayList<>();
        }

        return result;
    }

}
