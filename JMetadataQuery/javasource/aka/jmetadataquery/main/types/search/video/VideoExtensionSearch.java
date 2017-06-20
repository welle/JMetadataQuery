package aka.jmetadataquery.main.types.search.video;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadataquery.main.types.constants.videos.VideoExtensionSearchEnum;
import aka.jmetadataquery.main.types.constants.videos.subtypes.VideoExtensionEnum;
import aka.jmetadataquery.main.types.search.Criteria;

/**
 * Video extension search.
 *
 * @author charlottew
 */
public class VideoExtensionSearch extends Criteria<VideoExtensionSearchEnum, String> {

    private final Op operation;
    private final List<@NonNull String> extensionList = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param operation
     * @param videoExtensionSearchEnum
     */
    public VideoExtensionSearch(final BinaryCondition.Op operation, @NonNull final VideoExtensionSearchEnum videoExtensionSearchEnum) {
        super(videoExtensionSearchEnum);
        this.operation = operation;

        final List<VideoExtensionEnum> videoExtensionSearchEnumList = videoExtensionSearchEnum.getExtensionsList();
        for (final VideoExtensionEnum videoExtensionEnum : videoExtensionSearchEnumList) {
            this.extensionList.add(videoExtensionEnum.getValue());
        }
    }

    @Override
    public boolean matchCriteria(@NonNull final JMetaData jMetaData) {
        final String extensionFile = jMetaData.getGeneral().getFileExtensionAsString();

        boolean result = false;
        if (extensionFile != null) {
            for (final String extension : this.extensionList) {
                result = conditionMatch(extensionFile, extension, this.operation);
                if (result) {
                    // just break
                    break;
                }
            }
        }
        return result;
    }

}
