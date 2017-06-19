package aka.jmetadataquery.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.annotation.NonNull;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.exception.LibNotfoundException;
import aka.jmetadataquery.main.types.SearchKeys;
import aka.jmetadataquery.main.types.constants.interfaces.AudioSearch;
import aka.jmetadataquery.main.types.constants.interfaces.Search;
import aka.jmetadataquery.main.types.constants.interfaces.VideoSearch;
import aka.jmetadataquery.main.types.constants.videos.VideoExtensionSearchEnum;
import aka.jmetadataquery.main.types.constants.videos.subtypes.VideoExtensionEnum;

/**
 * Main class.
 * Entry point for all search in metadata files.
 *
 * @author cha
 */
public final class JMediaSearch {

    @NonNull
    private static final Logger LOGGER = Logger.getLogger(JMediaSearch.class.getPackage().getName());

    @NonNull
    private final Map<Class<? extends Search>, List<aka.jmetadataquery.main.types.SearchKeys>> searchEnumMap = new HashMap<>();
    private final Map<Class<? extends Search>, List<aka.jmetadataquery.main.types.SearchKeys>> searchVideoEnumMap = new HashMap<>();
    private final Map<Class<? extends Search>, List<aka.jmetadataquery.main.types.SearchKeys>> searchAudioEnumMap = new HashMap<>();
    private final Map<Class<? extends Search>, List<aka.jmetadataquery.main.types.SearchKeys>> searchSubtitleEnumMap = new HashMap<>();

    /**
     * Add search criteria.
     *
     * @param searchKeys
     */
    public void addSearchCriteria(@NonNull final SearchKeys @NonNull... searchKeys) {
        for (final @NonNull SearchKeys searchkey : searchKeys) {
            @NonNull
            final Search<?> search = searchkey.getSearch();
            final @NonNull Class<? extends Search> clazz = search.getClass();

            List<SearchKeys> list = null;
            if (search instanceof VideoSearch) {
                list = this.searchVideoEnumMap.get(clazz);
                if (list == null) {
                    list = new ArrayList<>();
                    this.searchVideoEnumMap.put(clazz, list);
                }
            } else if (search instanceof AudioSearch) {
                list = this.searchAudioEnumMap.get(clazz);
                if (list == null) {
                    list = new ArrayList<>();
                    this.searchAudioEnumMap.put(clazz, list);
                }
            } else {
                list = this.searchEnumMap.get(clazz);
                if (list == null) {
                    list = new ArrayList<>();
                    this.searchEnumMap.put(clazz, list);
                }
            }

            list.add(searchkey);
        }
    }

    /**
     * Remove search criteria.
     *
     * @param searchKeys
     */
    public void removeSearchCriteria(@NonNull final SearchKeys @NonNull... searchKeys) {
        for (final SearchKeys searchkey : searchKeys) {
            @NonNull
            final Search<?> search = searchkey.getSearch();
            final @NonNull Class<? extends Search> clazz = search.getClass();

            List<SearchKeys> list = null;
            if (search instanceof VideoSearch) {
                list = this.searchVideoEnumMap.get(clazz);
            } else if (search instanceof AudioSearch) {
                list = this.searchAudioEnumMap.get(clazz);
            } else {
                list = this.searchEnumMap.get(clazz);
            }

            if (list != null) {
                list.remove(searchkey);
            }
        }

//        final SearchQuery query = new SearchQuery(new SearchKeys(VideoExtensionSearchEnum.AVI, BinaryCondition.lessThanOrEq(VideoExtensionSearchEnum.AVI, null)), null);
    }

    /**
     * Search into the given directory (recursively if depth is set to <code>true</code>) for files matching the list of search criteria.
     *
     * @param directory root directory
     * @param depth if <code>true</code> search into sub-directories
     * @return List of file founded
     */
    @NonNull
    public List<@NonNull File> searchIntoDirectory(@NonNull final File directory, final boolean depth) {
        final List<@NonNull File> result = new ArrayList<>();

        if (directory.isDirectory()) {
            final @NonNull List<@NonNull String> extensions = getExtensions();
            final String[] stringArray = extensions.toArray(new String[extensions.size()]);
            final List<@NonNull File> files = (List<@NonNull File>) FileUtils.listFiles(directory, stringArray, depth);
            for (final @NonNull File currentFile : files) {
                if (isFileMatchingCriteria(currentFile)) {
                    result.add(currentFile);
                }
            }
        }

        return result;
    }

    @NonNull
    private List<@NonNull String> getExtensions() {
        final List<aka.jmetadataquery.main.types.SearchKeys> list = this.searchEnumMap.get(VideoExtensionSearchEnum.class);
        List<@NonNull String> result = new ArrayList<>();
        if (list == null) {
            result = VideoExtensionEnum.getAllExtensions();
        } else {
            for (final SearchKeys searchKey : list) {
                final Search<?> searchEnum = searchKey.getSearch();
                if (searchEnum instanceof VideoExtensionSearchEnum) {
                    final VideoExtensionSearchEnum videoExtensionSearchEnum = (VideoExtensionSearchEnum) searchEnum;
                    final List<aka.jmetadataquery.main.types.constants.videos.subtypes.VideoExtensionEnum> videoExtensionSearchEnumList = videoExtensionSearchEnum.getExtensionsList();
                    for (final VideoExtensionEnum videoExtensionEnum : videoExtensionSearchEnumList) {
                        result.add(videoExtensionEnum.getValue());
                    }
                }
            }
        }

        return result;
    }

    private boolean isFileMatchingCriteria(@NonNull final File currentFile) {
        final boolean isFileMatchingCriteria = false;
        try {
            final JMetaData jMetaData = new JMetaData();
            if (jMetaData.open(currentFile)) {
                // Explode query

                // Video criteria

                // Audio criteria

                // Subtitle criteria

            }
        } catch (IOException | LibNotfoundException e) {
            LOGGER.logp(Level.SEVERE, "JMediaSearch", "isFileMatchingCriteria", e.getMessage(), e);
        }

        return isFileMatchingCriteria;
    }

}
