package aka.jmetadataquery.main.types.search.audio;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataAudio;
import aka.jmetadataquery.main.types.constants.LanguageEnum;
import aka.jmetadataquery.main.types.search.Criteria;

/**
 * Audio language search.
 *
 * @author charlottew
 */
public class AudioLanguageSearch extends Criteria<LanguageEnum, String> {

    private final Op operation;
    private @NonNull final LanguageEnum languageEnum;

    /**
     * Constructor.
     *
     * @param operation
     * @param languageEnum
     */
    public AudioLanguageSearch(final BinaryCondition.Op operation, @NonNull final LanguageEnum languageEnum) {
        super(languageEnum);
        this.operation = operation;
        this.languageEnum = languageEnum;
    }

    @Override
    public boolean matchCriteria(@NonNull final JMetaData jMetaData) {
        final boolean result = !getStreamsIDInFileMatchingCriteria(jMetaData).isEmpty();
        return result;
    }

    @Override
    public @NonNull List<@NonNull Integer> getStreamsIDInFileMatchingCriteria(@NonNull final JMetaData jMetaData) {
        final List<@NonNull Integer> result = new ArrayList<>();

        final @NonNull List<@NonNull String> expectedLanguages = this.languageEnum.getValues();
        @NonNull
        final List<@NonNull JMetaDataAudio> audioStreams = jMetaData.getAudioStreams();
        for (final JMetaDataAudio jMetaDataAudio : audioStreams) {
            final String language = jMetaDataAudio.getLanguageAsString();

            if (language != null) {
                final Integer idAsInteger = jMetaData.getGeneral().getIDAsInteger();
                for (final String expectedLanguage : expectedLanguages) {
                    final boolean match = conditionMatch(language, expectedLanguage, this.operation);
                    if (match && idAsInteger != null) {
                        result.add(idAsInteger);
                    }
                }
            }
        }
        return result;
    }

}
