package aka.jmetadataquery.search.types.audio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataAudio;
import aka.jmetadataquery.helpers.SearchHelper;
import aka.jmetadataquery.search.Criteria;
import aka.jmetadataquery.search.constants.audio.AudioCommercialFormatEnum;

/**
 * Audio Commercial Format search.
 *
 * @author charlottew
 */
public class AudioCommericalFormatSearch extends Criteria<AudioCommercialFormatEnum, String> {

    private final Op operation;
    private @NonNull final AudioCommercialFormatEnum commercialFormatEnum;

    /**
     * Constructor.
     *
     * @param operation
     * @param formatEnum
     */
    public AudioCommericalFormatSearch(final BinaryCondition.Op operation, @NonNull final AudioCommercialFormatEnum formatEnum) {
        super(formatEnum);
        this.operation = operation;
        this.commercialFormatEnum = formatEnum;
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
        final List<@NonNull JMetaDataAudio> audioStreams = jMetaData.getAudioStreams();
        for (final JMetaDataAudio jMetaDataAudio : audioStreams) {
            Integer idAsInteger = jMetaDataAudio.getIDAsInteger();
            if (idAsInteger == null) {
                idAsInteger = Integer.valueOf(i);
                i--;
            }
            @Nullable
            final String formatCommercial = jMetaDataAudio.getFormatCommercialAsString();
            if (formatCommercial == null) {
                final String format = jMetaDataAudio.getFormatAsString();
                if (format != null) {
                    final String codec = this.commercialFormatEnum.getValue();
                    final boolean match = conditionMatch(codec, format, this.operation);
                    if (!result.containsKey(idAsInteger)) {
                        result.put(idAsInteger, Boolean.valueOf(match));
                    }
                }
            } else {
                final String codec = this.commercialFormatEnum.getValue();
                boolean match = conditionMatch(codec, formatCommercial, this.operation);
                if (!result.containsKey(idAsInteger)) {
                    result.put(idAsInteger, Boolean.valueOf(match));
                }
                if (!match) {
                    final String format = jMetaDataAudio.getFormatAsString();
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
