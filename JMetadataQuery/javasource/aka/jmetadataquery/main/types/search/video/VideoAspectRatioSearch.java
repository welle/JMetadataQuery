package aka.jmetadataquery.main.types.search.video;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        final boolean result = !getStreamsIDInFileMatchingCriteria(jMetaData).isEmpty();
        return result;
    }

    @Override
    public @NonNull Set<@NonNull Integer> getStreamsIDInFileMatchingCriteria(@NonNull final JMetaData jMetaData) {
        final Set<@NonNull Integer> result = new HashSet<>();

        @NonNull
        final List<@NonNull JMetaDataVideo> videoStreams = jMetaData.getVideoStreams();
        if (!videoStreams.isEmpty()) {
            final JMetaDataVideo jMetaDataVideo = videoStreams.get(0);
            Integer idAsInteger = jMetaDataVideo.getIDAsInteger();
            if (idAsInteger == null) {
                idAsInteger = Integer.valueOf(-1);
            }
            @Nullable
            final Long widthAsLong = jMetaDataVideo.getWidthAsLong();
            final Long heightAsLong = jMetaDataVideo.getHeightAsLong();
            final AspectRatio ratio = MediaInfoHelper.getClosestRatio(widthAsLong, heightAsLong);
            if (ratio != null) {
                final boolean match = conditionMatch(ratio, this.aspectRatio, this.operation);
                if (match) {
                    result.add(idAsInteger);
                }
            }
        }
        return result;
    }

}
