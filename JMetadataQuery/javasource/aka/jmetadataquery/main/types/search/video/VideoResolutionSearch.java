package aka.jmetadataquery.main.types.search.video;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataVideo;
import aka.jmetadata.main.constants.video.Resolution;
import aka.jmetadata.main.helper.MediaInfoHelper;
import aka.jmetadataquery.main.types.constants.videos.VideoResolutionSearchEnum;
import aka.jmetadataquery.main.types.search.Criteria;

public class VideoResolutionSearch extends Criteria<VideoResolutionSearchEnum, String> {

    private final Op operation;
    private final List<@NonNull String> extensionList = new ArrayList<>();
    private @NonNull final VideoResolutionSearchEnum videoResolutionSearchEnum;

    /**
     * Constructor.
     *
     * @param operation
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
            final Long widthAsLong = jMetaDataVideo.getWidthAsLong();
            Resolution resolution = null;
            if (widthAsLong != null) {
                resolution = MediaInfoHelper.getClosestResolution(widthAsLong);
            }

            if (resolution != null) {
                result = resolution == this.videoResolutionSearchEnum.getResolution();
            }
        }
        return result;
    }

}
