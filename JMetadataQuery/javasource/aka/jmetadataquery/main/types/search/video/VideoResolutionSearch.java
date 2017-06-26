package aka.jmetadataquery.main.types.search.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import aka.jmetadataquery.main.types.search.helpers.SearchHelper;

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
        final Map<@NonNull Integer, Boolean> map = getStreamsIDInFileMatchingCriteria(jMetaData);
        final List<@NonNull Map<@NonNull Integer, Boolean>> idMapList = new ArrayList<>();
        idMapList.add(map);
        return SearchHelper.isMatching(idMapList, 1);
    }

    @Override
    public @NonNull Map<@NonNull Integer, Boolean> getStreamsIDInFileMatchingCriteria(@NonNull final JMetaData jMetaData) {
        final Map<@NonNull Integer, Boolean> result = new HashMap<>();

        int i = -1;
        final List<@NonNull JMetaDataVideo> videoStreams = jMetaData.getVideoStreams();
        for (final @NonNull JMetaDataVideo jMetaDataVideo : videoStreams) {
            Integer idAsInteger = jMetaDataVideo.getIDAsInteger();
            if (idAsInteger == null) {
                idAsInteger = Integer.valueOf(i);
                i--;
            }
            @Nullable
            final Long heightAsLong = jMetaDataVideo.getHeightAsLong();
            Resolution resolution = null;
            if (heightAsLong != null) {
                resolution = MediaInfoHelper.getClosestResolution(heightAsLong.doubleValue());
            }

            final Resolution expectedResolution = this.videoResolutionSearchEnum.getResolution();
            if (resolution != null) {
                final boolean match = conditionMatch(resolution, expectedResolution, this.operation);
                if (!result.containsKey(idAsInteger)) {
                    result.put(idAsInteger, match);
                }
            }
        }
        return result;
    }

}
