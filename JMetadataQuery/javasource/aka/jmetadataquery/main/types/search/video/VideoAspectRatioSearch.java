package aka.jmetadataquery.main.types.search.video;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataVideo;
import aka.jmetadata.main.constants.video.AspectRatio;
import aka.jmetadata.main.helper.MediaInfoHelper;
import aka.jmetadataquery.main.types.search.Criteria;

/**
 * Video aspect ratio search.
 *
 * @author charlottew
 */
public class VideoAspectRatioSearch extends Criteria<AspectRatio, AspectRatio> {

    private final Op operation;
    private @NonNull final AspectRatio aspectRatio;

    /**
     * Constructor.
     *
     * @param operation
     * @param aspectRatio
     */
    public VideoAspectRatioSearch(final BinaryCondition.Op operation, @NonNull final AspectRatio aspectRatio) {
        super(aspectRatio);
        this.operation = operation;
        this.aspectRatio = aspectRatio;
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
            final Long heightAsLong = jMetaDataVideo.getHeightAsLong();
            final AspectRatio ratio = MediaInfoHelper.getClosestRatio(widthAsLong, heightAsLong);
            if (ratio != null) {
                result = conditionMatch(ratio, this.aspectRatio, this.operation);
            }
        }
        return result;
    }

}
