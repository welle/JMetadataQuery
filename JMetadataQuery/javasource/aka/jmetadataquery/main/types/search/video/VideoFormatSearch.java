package aka.jmetadataquery.main.types.search.video;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataVideo;
import aka.jmetadata.main.constants.format.FormatEnum;
import aka.jmetadataquery.main.types.search.Criteria;

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
        final boolean result = !getStreamsIDInFileMatchingCriteria(jMetaData).isEmpty();
        return result;
    }

    @Override
    public @NonNull List<@NonNull Integer> getStreamsIDInFileMatchingCriteria(@NonNull final JMetaData jMetaData) {
        final List<@NonNull Integer> result = new ArrayList<>();
        @NonNull
        final List<@NonNull JMetaDataVideo> videoStreams = jMetaData.getVideoStreams();
        if (!videoStreams.isEmpty()) {
            final JMetaDataVideo jMetaDataVideo = videoStreams.get(0);
            final Integer idAsInteger = jMetaDataVideo.getIDAsInteger();
            @Nullable
            final String formatCommercial = jMetaDataVideo.getFormatCommercialAsString();
            if (formatCommercial == null) {
                final String format = jMetaDataVideo.getFormatAsString();
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
                    final String format = jMetaDataVideo.getFormatAsString();
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
