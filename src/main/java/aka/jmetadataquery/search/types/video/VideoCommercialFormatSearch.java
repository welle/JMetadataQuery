package aka.jmetadataquery.search.types.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataVideo;
import aka.jmetadataquery.helpers.SearchHelper;
import aka.jmetadataquery.search.Criteria;
import aka.jmetadataquery.search.constants.conditions.Operator;
import aka.jmetadataquery.search.constants.video.VideoCommercialFormatEnum;

/**
 * Video Commercial Format search.
 *
 * @author charlottew
 */
public class VideoCommercialFormatSearch extends Criteria<VideoCommercialFormatEnum, String> {

    private final Operator operation;
    private @NonNull final VideoCommercialFormatEnum formatEnum;

    /**
     * Constructor.
     *
     * @param operation
     * @param formatEnum
     */
    public VideoCommercialFormatSearch(final Operator operation, @NonNull final VideoCommercialFormatEnum formatEnum) {
        super(formatEnum);
        this.operation = operation;
        this.formatEnum = formatEnum;
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
            final String formatCommercial = jMetaDataVideo.getFormatCommercialAsString();
            if (formatCommercial != null) {
                final String codec = this.formatEnum.getValue();
                boolean match = conditionMatch(codec, formatCommercial, this.operation);
                if (!result.containsKey(idAsInteger)) {
                    result.put(idAsInteger, Boolean.valueOf(match));
                }
                if (!match) {
                    final String format = jMetaDataVideo.getFormatAsString();
                    if (format != null) {
                        match = conditionMatch(codec, format, this.operation);
                        if (!result.containsKey(idAsInteger)) {
                            result.put(idAsInteger, Boolean.valueOf(match));
                        }
                    }
                }
            }
        }
        return result;
    }

}
