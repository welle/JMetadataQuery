package aka.jmetadataquery.main.types.search.audio;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        final boolean result = !getStreamsIDInFileMatchingCriteria(jMetaData).isEmpty();
        return result;
    }

    @Override
    public @NonNull Set<@NonNull Integer> getStreamsIDInFileMatchingCriteria(@NonNull final JMetaData jMetaData) {
        final Set<@NonNull Integer> result = new HashSet<>();

        @NonNull
        final List<@NonNull JMetaDataAudio> audioStreams = jMetaData.getAudioStreams();
        for (final JMetaDataAudio jMetaDataAudio : audioStreams) {
            Integer idAsInteger = jMetaDataAudio.getIDAsInteger();
            if (idAsInteger == null) {
                idAsInteger = Integer.valueOf(-1);
            }
            final String mode = jMetaDataAudio.getCompressionModeAsString();
            if (mode != null) {
                final boolean match = conditionMatch(mode, this.compressionModeEnum.getValue(), this.operation);
                if (match) {
                    result.add(idAsInteger);
                }
            }
        }
        return result;
    }

}
