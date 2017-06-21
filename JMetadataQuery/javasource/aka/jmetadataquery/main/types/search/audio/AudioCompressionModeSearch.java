package aka.jmetadataquery.main.types.search.audio;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataAudio;
import aka.jmetadataquery.main.types.constants.CompressionModeEnum;
import aka.jmetadataquery.main.types.search.Criteria;

/**
 * Audio compression mode search.
 *
 * @author charlottew
 */
public class AudioCompressionModeSearch extends Criteria<CompressionModeEnum, String> {

    private final Op operation;
    private @NonNull final CompressionModeEnum compressionModeEnum;

    /**
     * Constructor.
     *
     * @param operation
     * @param compressionModeEnum Compression mode
     */
    public AudioCompressionModeSearch(final BinaryCondition.Op operation, @NonNull final CompressionModeEnum compressionModeEnum) {
        super(compressionModeEnum);
        this.operation = operation;
        this.compressionModeEnum = compressionModeEnum;
    }

    @Override
    public boolean matchCriteria(@NonNull final JMetaData jMetaData) {
        boolean result = false;

        @NonNull
        final List<@NonNull JMetaDataAudio> audioStreams = jMetaData.getAudioStreams();
        for (final JMetaDataAudio jMetaDataAudio : audioStreams) {
            final String mode = jMetaDataAudio.getCompressionModeAsString();
            if (mode != null) {
                result = conditionMatch(mode, this.compressionModeEnum.getValue(), this.operation);
            }
            if (result) {
                break;
            }
        }
        return result;
    }

}
