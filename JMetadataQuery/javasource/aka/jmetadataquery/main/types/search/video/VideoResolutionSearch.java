package aka.jmetadataquery.main.types.search.video;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataVideo;
import aka.jmetadata.main.constants.video.Resolution;
import aka.jmetadata.main.helper.MediaInfoHelper;
import aka.jmetadataquery.main.types.search.Criteria;
import aka.jmetadataquery.main.types.search.constants.video.VideoResolutionSearchEnum;

/**
 * Video resolution search.
 *
 * @author charlottew
 */
public class VideoResolutionSearch extends Criteria<VideoResolutionSearchEnum, Resolution> {

    private final Op operation;
    private @NonNull final VideoResolutionSearchEnum videoResolutionSearchEnum;

    /**
     * Constructor.
     *
     * @param operation
     * @param videoResolutionSearchEnum
     */
    public VideoResolutionSearch(final BinaryCondition.Op operation, @NonNull final VideoResolutionSearchEnum videoResolutionSearchEnum) {
        super(videoResolutionSearchEnum);
        this.operation = operation;
        this.videoResolutionSearchEnum = videoResolutionSearchEnum;
    }

    @Override
    public boolean matchCriteria(@NonNull final JMetaData jMetaData) {
        boolean result = false;
        @NonNull
        final List<@NonNull JMetaDataVideo> videoStreams = jMetaData.getVideoStreams();
        if (!videoStreams.isEmpty()) {
            final JMetaDataVideo jMetaDataVideo = videoStreams.get(0);
            @Nullable
            final Long heightAsLong = jMetaDataVideo.getHeightAsLong();
            Resolution resolution = null;
            if (heightAsLong != null) {
                resolution = MediaInfoHelper.getClosestResolution(heightAsLong);
            }

            final Resolution expectedResolution = this.videoResolutionSearchEnum.getResolution();
            if (resolution != null && expectedResolution != null) {
                result = conditionMatch(resolution, expectedResolution, this.operation);
            }
        }
        return result;
    }

}
