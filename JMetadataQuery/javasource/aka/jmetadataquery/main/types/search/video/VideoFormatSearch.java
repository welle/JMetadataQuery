package aka.jmetadataquery.main.types.search.video;

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
 * Video Codec search.
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
        boolean result = false;
        @NonNull
        final List<@NonNull JMetaDataVideo> videoStreams = jMetaData.getVideoStreams();
        if (!videoStreams.isEmpty()) {
            final JMetaDataVideo jMetaDataVideo = videoStreams.get(0);
            @Nullable
            final String formatCommercial = jMetaDataVideo.getFormatCommercialAsString();
            if (formatCommercial == null) {
                final String format = jMetaDataVideo.getFormatAsString();
                if (format != null) {
                    final String codec = this.formatEnum.getName();
                    result = conditionMatch(codec, format, this.operation);
                }
            } else {
                final String codec = this.formatEnum.getName();
                result = conditionMatch(codec, formatCommercial, this.operation);
                if (!result) {
                    final String format = jMetaDataVideo.getFormatAsString();
                    if (format != null) {
                        result = conditionMatch(codec, format, this.operation);
                    }
                }
            }
        }
        return result;
    }

}
