package aka.jmetadataquery.main.types.search.video;

import java.util.ArrayList;
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
        final boolean allMustMatch = this.operation == BinaryCondition.Op.NOT_EQUAL_TO;

        boolean result;
        if (allMustMatch) {
            result = getStreamsIDInFileMatchingCriteria(jMetaData).size() == jMetaData.getVideoStreams().size();
        } else {
            result = !getStreamsIDInFileMatchingCriteria(jMetaData).isEmpty();
        }
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
            final String codecId = jMetaDataVideo.getCodecIDAsString();
            if (codecId != null) {
                for (final String codec : this.codecEnum.getValues()) {
                    final boolean match = conditionMatch(codec, codecId, this.operation);
                    if (match && idAsInteger != null) {
                        result.add(idAsInteger);
                    }
                }
            }
        }
        return result;
    }

}
