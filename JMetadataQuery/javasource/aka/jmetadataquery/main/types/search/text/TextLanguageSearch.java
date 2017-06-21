package aka.jmetadataquery.main.types.search.text;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.JMetaData;
import aka.jmetadata.main.JMetaDataText;
import aka.jmetadataquery.main.types.constants.subtypes.LanguageEnum;
import aka.jmetadataquery.main.types.search.Criteria;

/**
 * Text language search.
 *
 * @author charlottew
 */
public class TextLanguageSearch extends Criteria<LanguageEnum, String> {

    private final Op operation;
    private @NonNull final LanguageEnum languageEnum;

    /**
     * Constructor.
     *
     * @param operation
     * @param languageEnum
     */
    public TextLanguageSearch(final BinaryCondition.Op operation, @NonNull final LanguageEnum languageEnum) {
        super(languageEnum);
        this.operation = operation;
        this.languageEnum = languageEnum;
    }

    @Override
    public boolean matchCriteria(@NonNull final JMetaData jMetaData) {
        boolean result = false;

        final @NonNull List<@NonNull String> expectedLanguages = this.languageEnum.getValues();
        @NonNull
        final List<@NonNull JMetaDataText> textStreams = jMetaData.getSubtitleStreams();
        for (final JMetaDataText jMetaDataText : textStreams) {
            final String language = jMetaDataText.getLanguageAsString();

            if (language != null) {
                for (final String expectedLanguage : expectedLanguages) {
                    result = conditionMatch(language, expectedLanguage, this.operation);
                    if (result) {
                        break;
                    }
                }
            }
            if (result) {
                break;
            }
        }

        return result;
    }

}
