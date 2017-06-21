package aka.jmetadataquery.main.types.search.audio;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataAudio;
import aka.jmetadata.main.constants.format.FormatEnum;
import aka.jmetadataquery.main.types.search.Criteria;

/**
 * Audio Format search.
 *
 * @author charlottew
 */
public class AudioFormatSearch extends Criteria<FormatEnum, String> {

    private final Op operation;
    private @NonNull final FormatEnum formatEnum;

    /**
     * Constructor.
     *
     * @param operation
     * @param formatEnum
     */
    public AudioFormatSearch(final BinaryCondition.Op operation, @NonNull final FormatEnum formatEnum) {
        super(formatEnum);
        this.operation = operation;
        this.formatEnum = formatEnum;
    }

    @Override
    public boolean matchCriteria(@NonNull final JMetaData jMetaData) {
        final boolean result = !getStreamsIDInFileMatchingCriteria(jMetaData).isEmpty();
        return result;
    }

    @Override
    public @NonNull List<@NonNull Integer> getStreamsIDInFileMatchingCriteria(@NonNull final JMetaData jMetaData) {
        final List<@NonNull Integer> result = new ArrayList<>();
        final List<@NonNull JMetaDataAudio> audioStreams = jMetaData.getAudioStreams();
        for (final JMetaDataAudio jMetaDataAudio : audioStreams) {
            final Integer idAsInteger = jMetaDataAudio.getIDAsInteger();
            @Nullable
            final String formatCommercial = jMetaDataAudio.getFormatCommercialAsString();
            if (formatCommercial == null) {
                final String format = jMetaDataAudio.getFormatAsString();
                if (format != null) {
                    final String codec = this.formatEnum.getName();
                    final boolean match = conditionMatch(codec, format, this.operation);
                    if (match && idAsInteger != null) {
                        result.add(idAsInteger);
                    }
                }
            } else {
                final String codec = this.formatEnum.getName();
                boolean match = conditionMatch(codec, formatCommercial, this.operation);
                if (match && idAsInteger != null) {
                    result.add(idAsInteger);
                }
                if (!match) {
                    final String format = jMetaDataAudio.getFormatAsString();
                    if (format != null) {
                        match = conditionMatch(codec, format, this.operation);
                        if (match && idAsInteger != null) {
                            result.add(idAsInteger);
                        }
                    }
                }
            }
        }
        return result;
    }

}
