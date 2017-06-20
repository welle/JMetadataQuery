package aka.jmetadataquery.main.types.search.video;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataVideo;
import aka.jmetadataquery.main.types.constants.videos.VideoCodecSearchEnum;
import aka.jmetadataquery.main.types.search.Criteria;

/**
 * Video Codec search.
 *
 * @author charlottew
 */
public class VideoCodecSearch extends Criteria<VideoCodecSearchEnum, String> {

    private final Op operation;
    private @NonNull final VideoCodecSearchEnum videoCodecSearchEnum;

    /**
     * Constructor.
     *
     * @param operation
     * @param videoCodecSearchEnum
     */
    public VideoCodecSearch(final BinaryCondition.Op operation, @NonNull final VideoCodecSearchEnum videoCodecSearchEnum) {
        super(videoCodecSearchEnum);
        this.operation = operation;
        this.videoCodecSearchEnum = videoCodecSearchEnum;
    }

    @Override
    public boolean matchCriteria(@NonNull final JMetaData jMetaData) {
        boolean result = false;
        @NonNull
        final List<@NonNull JMetaDataVideo> videoStreams = jMetaData.getVideoStreams();
        if (!videoStreams.isEmpty()) {
            final JMetaDataVideo jMetaDataVideo = videoStreams.get(0);
            final boolean allMustMatch = this.operation == BinaryCondition.Op.NOT_EQUAL_TO;
            @Nullable
            final String formatCommercial = jMetaDataVideo.getFormatCommercialAsString();
            if (formatCommercial == null) {
                final String format = jMetaDataVideo.getFormatAsString();
                if (format != null) {
                    for (final String codec : this.videoCodecSearchEnum.getValues()) {
                        result = conditionMatch(codec, format, this.operation);
                        if (result && !allMustMatch) {
                            // just break
                            break;
                        }
                    }
                }
            } else {
                for (final String codec : this.videoCodecSearchEnum.getValues()) {
                    result = conditionMatch(codec, formatCommercial, this.operation);
                    if (result && !allMustMatch) {
                        // just break
                        break;
                    }
                }
                if (!result) {
                    final String format = jMetaDataVideo.getFormatAsString();
                    if (format != null) {
                        for (final String codec : this.videoCodecSearchEnum.getValues()) {
                            result = conditionMatch(codec, format, this.operation);
                            if (result && !allMustMatch) {
                                // just break
                                break;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

}
