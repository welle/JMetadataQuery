package aka.jmetadataquery.search.types.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

import aka.jmetadata.main.JMetaData;
import aka.jmetadataquery.helpers.SearchHelper;
import aka.jmetadataquery.search.Criteria;
import aka.jmetadataquery.search.constants.conditions.Operator;
import aka.jmetadataquery.search.constants.file.FileExtensionSearchEnum;
import aka.jmetadataquery.search.constants.video.VideoExtensionEnum;

/**
 * File extension search.
 *
 * @author charlottew
 */
public class FileExtensionSearch extends Criteria<FileExtensionSearchEnum, String> {

    private final Operator operation;
    private final List<@NonNull String> extensionList = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param operation
     * @param videoExtensionSearchEnum
     */
    public FileExtensionSearch(final Operator operation, @NonNull final FileExtensionSearchEnum videoExtensionSearchEnum) {
        super(videoExtensionSearchEnum);
        this.operation = operation;

        final List<VideoExtensionEnum> videoExtensionSearchEnumList = videoExtensionSearchEnum.getExtensionsList();
        for (final VideoExtensionEnum videoExtensionEnum : videoExtensionSearchEnumList) {
            this.extensionList.add(videoExtensionEnum.getValue());
        }
    }

    @Override
    public boolean matchCriteria(@NonNull final JMetaData jMetaData) {
        final Map<@NonNull Integer, Boolean> map = getStreamsIDInFileMatchingCriteria(jMetaData);
        final List<@NonNull Map<@NonNull Integer, Boolean>> idMapList = new ArrayList<>();
        idMapList.add(map);
        return SearchHelper.isMatching(idMapList, 1, false);
    }

    @Override
    public @NonNull Map<@NonNull Integer, Boolean> getStreamsIDInFileMatchingCriteria(@NonNull final JMetaData jMetaData) {
        final Map<@NonNull Integer, Boolean> result = new HashMap<>();

        int i = -1;
        final String extensionFile = jMetaData.getGeneral().getFileExtensionAsString();
        if (extensionFile != null) {
            Integer idAsInteger = jMetaData.getGeneral().getIDAsInteger();
            if (idAsInteger == null) {
                idAsInteger = Integer.valueOf(i);
                i--;
            }
            for (final String extension : this.extensionList) {
                final boolean match = conditionMatch(extensionFile, extension, this.operation);
                if (!result.containsKey(idAsInteger)) {
                    result.put(idAsInteger, Boolean.valueOf(match));
                }
            }
        }
        return result;
    }

}
