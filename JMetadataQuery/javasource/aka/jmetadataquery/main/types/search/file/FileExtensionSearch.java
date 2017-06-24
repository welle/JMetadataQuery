package aka.jmetadataquery.main.types.search.file;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadataquery.main.types.constants.file.FileExtensionSearchEnum;
import aka.jmetadataquery.main.types.constants.video.VideoExtensionEnum;
import aka.jmetadataquery.main.types.search.Criteria;

/**
 * File extension search.
 *
 * @author charlottew
 */
public class FileExtensionSearch extends Criteria<FileExtensionSearchEnum, String> {

    private final Op operation;
    private final List<@NonNull String> extensionList = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param operation
     * @param videoExtensionSearchEnum
     */
    public FileExtensionSearch(final BinaryCondition.Op operation, @NonNull final FileExtensionSearchEnum videoExtensionSearchEnum) {
        super(videoExtensionSearchEnum);
        this.operation = operation;

        final List<VideoExtensionEnum> videoExtensionSearchEnumList = videoExtensionSearchEnum.getExtensionsList();
        for (final VideoExtensionEnum videoExtensionEnum : videoExtensionSearchEnumList) {
            this.extensionList.add(videoExtensionEnum.getValue());
        }
    }

    @Override
    public boolean matchCriteria(@NonNull final JMetaData jMetaData) {
        final boolean result = !getStreamsIDInFileMatchingCriteria(jMetaData).isEmpty();
        return result;
    }

    @Override
    public @NonNull Set<@NonNull Integer> getStreamsIDInFileMatchingCriteria(@NonNull final JMetaData jMetaData) {
        final Set<@NonNull Integer> result = new HashSet<>();

        final String extensionFile = jMetaData.getGeneral().getFileExtensionAsString();
        if (extensionFile != null) {
            Integer idAsInteger = jMetaData.getGeneral().getIDAsInteger();
            if (idAsInteger == null) {
                idAsInteger = Integer.valueOf(-1);
            }
            for (final String extension : this.extensionList) {
                final boolean match = conditionMatch(extensionFile, extension, this.operation);
                if (match) {
                    result.add(idAsInteger);
                }
            }
        }
        return result;
    }

}
