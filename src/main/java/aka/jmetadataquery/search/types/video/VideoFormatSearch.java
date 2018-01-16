package aka.jmetadataquery.search.types.video;

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
import aka.jmetadata.main.constants.format.FormatEnum;
import aka.jmetadataquery.helpers.SearchHelper;
import aka.jmetadataquery.search.Criteria;

/**
 * Video Format search.
 *
 * @author charlottew
 */
public class VideoFormatSearch extends Criteria<FormatEnum, String> {

    private final Op operation;
    private @NonNull final FormatEnum formatEnum;

    /**
     * Constructor.
     *
     * @param operation
     * @param formatEnum
     */
    public VideoFormatSearch(final BinaryCondition.Op operation, @NonNull final FormatEnum formatEnum) {
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
            if (formatCommercial == null) {
                final String format = jMetaDataVideo.getFormatAsString();
                if (format != null) {
                    final String codec = this.formatEnum.getName();
                    final boolean match = conditionMatch(codec, format, this.operation);
                    if (!result.containsKey(idAsInteger)) {
                        result.put(idAsInteger, Boolean.valueOf(match));
                    }
                }
            } else {
                final String codec = this.formatEnum.getName();
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
