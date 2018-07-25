package aka.jmetadataquery.consumer;

import java.io.File;

import org.eclipse.jdt.annotation.NonNull;

import aka.jmetadataquery.search.operation.AndSearch;
import aka.jmetadataquery.search.operation.interfaces.OperatorSearchInterface;

/**
 * File consumer context.
 * Will contains informations for the search and the result.
 *
 * @author charlottew
 */
public class FileConsumerContext<T extends OperatorSearchInterface> {

    private boolean isMatching = false;
    private @NonNull final T andSearch;
    private @NonNull final File file;

    /**
     * Constructor.
     *
     * @param andSearch search to handle
     * @param file file to handle
     */
    public FileConsumerContext(@NonNull final T andSearch, @NonNull final File file) {
        this.andSearch = andSearch;
        this.file = file;
    }

    /**
     * Process the andSearch.isFileMatchingCriteria(file).
     */
    public void process() {
        this.isMatching = this.andSearch.isFileMatchingCriteria(this.file);
    }

    /**
     * Return the result of the search.
     *
     * @return result of andSearch.isFileMatchingCriteria(file)
     */
    public boolean getResult() {
        return this.isMatching;
    }

    /**
     * @return the file
     */
    @NonNull
    public File getFile() {
        return this.file;
    }

}
