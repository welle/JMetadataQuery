package aka.jmetadataquery.search.types.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataVideo;
import aka.jmetadata.main.constants.video.AspectRatio;
import aka.jmetadata.main.helper.MediaInfoHelper;
import aka.jmetadataquery.helpers.SearchHelper;
import aka.jmetadataquery.search.Criteria;
import aka.jmetadataquery.search.constants.conditions.Operator;

/**
 * Video aspect ratio search.
 *
 * @author charlottew
 */
public class VideoAspectRatioSearch extends Criteria<AspectRatio, AspectRatio> {

    private final Operator operation;
    private @NonNull final AspectRatio aspectRatio;

    /**
     * Constructor.
     *
     * @param operation
     * @param aspectRatio
     */
    public VideoAspectRatioSearch(final Operator operation, @NonNull final AspectRatio aspectRatio) {
        super(aspectRatio);
        this.operation = operation;
        this.aspectRatio = aspectRatio;
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
        @NonNull
        final List<@NonNull JMetaDataVideo> videoStreams = jMetaData.getVideoStreams();
        for (final @NonNull JMetaDataVideo jMetaDataVideo : videoStreams) {
            Integer idAsInteger = jMetaDataVideo.getIDAsInteger();
            if (idAsInteger == null) {
                idAsInteger = Integer.valueOf(i);
                i--;
            }
            @Nullable
            final Long widthAsLong = jMetaDataVideo.getWidthAsLong();
            final Long heightAsLong = jMetaDataVideo.getHeightAsLong();
            final AspectRatio ratio = MediaInfoHelper.getClosestRatio(widthAsLong, heightAsLong);
            if (ratio != null) {
                final boolean match = conditionMatch(ratio, this.aspectRatio, this.operation);
                if (!result.containsKey(idAsInteger)) {
                    result.put(idAsInteger, Boolean.valueOf(match));
                }
            }
        }
        return result;
    }

}
