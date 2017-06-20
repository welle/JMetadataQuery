package aka.jmetadataquery.test;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.test.JMetaDataMenu_Test;
import aka.jmetadataquery.main.JMediaSearch;
import aka.jmetadataquery.main.types.SearchQuery;
import aka.jmetadataquery.main.types.constants.subtypes.LanguageEnum;
import aka.jmetadataquery.main.types.constants.videos.VideoAspectRatioSearchEnum;
import aka.jmetadataquery.main.types.constants.videos.VideoExtensionSearchEnum;
import aka.jmetadataquery.main.types.constants.videos.VideoResolutionSearchEnum;
import aka.jmetadataquery.main.types.search.audio.AudioLanguageSearch;
import aka.jmetadataquery.main.types.search.text.TextLanguageSearch;
import aka.jmetadataquery.main.types.search.video.VideoAspectRatioSearch;
import aka.jmetadataquery.main.types.search.video.VideoExtensionSearch;
import aka.jmetadataquery.main.types.search.video.VideoResolutionSearch;

public class JMediaSearch_Test {

    @Test
    public void testVideoExtension() {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").getFile());

        final JMediaSearch jMediaSearch = new JMediaSearch();
        SearchQuery query = new SearchQuery();

        boolean result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        VideoExtensionSearch videoExtensionSearch = new VideoExtensionSearch(Op.NOT_EQUAL_TO, VideoExtensionSearchEnum.AVI);
        query.addSearchCriteria(videoExtensionSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        videoExtensionSearch = new VideoExtensionSearch(Op.EQUAL_TO, VideoExtensionSearchEnum.MKV);
        query.addSearchCriteria(videoExtensionSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);
    }

    @Test
    public void testAudioLanguage() {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").getFile());

        final JMediaSearch jMediaSearch = new JMediaSearch();
        SearchQuery query = new SearchQuery();

        boolean result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        AudioLanguageSearch audioLanguageSearch = new AudioLanguageSearch(Op.NOT_EQUAL_TO, LanguageEnum.FRENCH);
        query.addSearchCriteria(audioLanguageSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        audioLanguageSearch = new AudioLanguageSearch(Op.EQUAL_TO, LanguageEnum.ENGLISH);
        query.addSearchCriteria(audioLanguageSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);
    }

    @Test
    public void testTextLanguage() {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").getFile());

        final JMediaSearch jMediaSearch = new JMediaSearch();
        SearchQuery query = new SearchQuery();

        boolean result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        TextLanguageSearch textLanguageSearch = new TextLanguageSearch(Op.NOT_EQUAL_TO, LanguageEnum.FRENCH);
        query.addSearchCriteria(textLanguageSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        textLanguageSearch = new TextLanguageSearch(Op.EQUAL_TO, LanguageEnum.GERMAN);
        query.addSearchCriteria(textLanguageSearch);
        textLanguageSearch = new TextLanguageSearch(Op.EQUAL_TO, LanguageEnum.ENGLISH);
        query.addSearchCriteria(textLanguageSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);
    }

    @Test
    public void testVideoResolution() {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").getFile());

        final JMediaSearch jMediaSearch = new JMediaSearch();
        SearchQuery query = new SearchQuery();

        boolean result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        VideoResolutionSearch videoResolutionSearch = new VideoResolutionSearch(Op.NOT_EQUAL_TO, VideoResolutionSearchEnum.R_720);
        query.addSearchCriteria(videoResolutionSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertFalse(result);

        query = new SearchQuery();
        videoResolutionSearch = new VideoResolutionSearch(Op.EQUAL_TO, VideoResolutionSearchEnum.R_720);
        query.addSearchCriteria(videoResolutionSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);

        query = new SearchQuery();
        videoResolutionSearch = new VideoResolutionSearch(Op.GREATER_THAN, VideoResolutionSearchEnum.R_480);
        query.addSearchCriteria(videoResolutionSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);
    }

    @Test
    public void testVideoAspectRatio() {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").getFile());

        final JMediaSearch jMediaSearch = new JMediaSearch();
        SearchQuery query = new SearchQuery();

        boolean result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        VideoAspectRatioSearch videoAspectRatioSearch = new VideoAspectRatioSearch(Op.NOT_EQUAL_TO, VideoAspectRatioSearchEnum.AS_2_20);
        query.addSearchCriteria(videoAspectRatioSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertFalse(result);

        query = new SearchQuery();
        videoAspectRatioSearch = new VideoAspectRatioSearch(Op.EQUAL_TO, VideoAspectRatioSearchEnum.AS_2_20);
        query.addSearchCriteria(videoAspectRatioSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        videoAspectRatioSearch = new VideoAspectRatioSearch(Op.GREATER_THAN, VideoAspectRatioSearchEnum.AS_1_66);
        query.addSearchCriteria(videoAspectRatioSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);
    }
}
