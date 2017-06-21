package aka.jmetadataquery.main.types.search.video;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataVideo;
import aka.jmetadata.main.constants.codecs.interfaces.CodecEnum;
import aka.jmetadataquery.main.types.search.Criteria;

/**
 * Video Format search.
 *
 * @author charlottew
 */
public class VideoCodecIdSearch extends Criteria<CodecEnum, String> {

    private final Op operation;
    private @NonNull final CodecEnum codecEnum;

    /**
     * Constructor.
     *
     * @param operation
     * @param codecEnum
     */
    public VideoCodecIdSearch(final BinaryCondition.Op operation, @NonNull final CodecEnum codecEnum) {
        super(codecEnum);
        this.operation = operation;
        this.codecEnum = codecEnum;
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
            final String codecId = jMetaDataVideo.getCodecIDAsString();
            if (codecId != null) {
                final String format = jMetaDataVideo.getFormatAsString();
                if (format != null) {
                    for (final String codec : this.codecEnum.getValues()) {
                        result = conditionMatch(codec, format, this.operation);
                        if (result && !allMustMatch) {
                            // just break
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

}
