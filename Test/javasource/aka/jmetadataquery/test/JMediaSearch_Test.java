package aka.jmetadataquery.test;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.constants.codecs.AudioMatroskaCodecIdEnum;
import aka.jmetadata.main.constants.codecs.VideoMatroskaCodecIdEnum;
import aka.jmetadata.main.constants.format.FormatEnum;
import aka.jmetadata.main.constants.profile.AudioProfileEnum;
import aka.jmetadata.main.constants.video.AspectRatio;
import aka.jmetadata.test.JMetaDataMenu_Test;
import aka.jmetadataquery.main.JMediaSearch;
import aka.jmetadataquery.main.types.SearchQuery;
import aka.jmetadataquery.main.types.constants.CompressionModeEnum;
import aka.jmetadataquery.main.types.constants.LanguageEnum;
import aka.jmetadataquery.main.types.constants.file.FileExtensionSearchEnum;
import aka.jmetadataquery.main.types.search.audio.AudioBitRateSearch;
import aka.jmetadataquery.main.types.search.audio.AudioChannelSearch;
import aka.jmetadataquery.main.types.search.audio.AudioCodecIdSearch;
import aka.jmetadataquery.main.types.search.audio.AudioCompressionModeSearch;
import aka.jmetadataquery.main.types.search.audio.AudioFormatSearch;
import aka.jmetadataquery.main.types.search.audio.AudioLanguageSearch;
import aka.jmetadataquery.main.types.search.audio.AudioProfileSearch;
import aka.jmetadataquery.main.types.search.constants.video.VideoResolutionSearchEnum;
import aka.jmetadataquery.main.types.search.file.FileExtensionSearch;
import aka.jmetadataquery.main.types.search.file.FileSizeSearch;
import aka.jmetadataquery.main.types.search.general.GeneralDurationSearch;
import aka.jmetadataquery.main.types.search.text.TextLanguageSearch;
import aka.jmetadataquery.main.types.search.video.VideoAspectRatioSearch;
import aka.jmetadataquery.main.types.search.video.VideoCodecIdSearch;
import aka.jmetadataquery.main.types.search.video.VideoFormatSearch;
import aka.jmetadataquery.main.types.search.video.VideoMaxBitRateSearch;
import aka.jmetadataquery.main.types.search.video.VideoResolutionSearch;

public class JMediaSearch_Test {

    @Test
    public void testFileExtensionSearch() {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").getFile());

        final JMediaSearch jMediaSearch = new JMediaSearch();
        SearchQuery query = new SearchQuery();

        boolean result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        FileExtensionSearch fileExtensionSearch = new FileExtensionSearch(Op.NOT_EQUAL_TO, FileExtensionSearchEnum.AVI);
        query.addSearchCriteria(fileExtensionSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        fileExtensionSearch = new FileExtensionSearch(Op.EQUAL_TO, FileExtensionSearchEnum.MKV);
        query.addSearchCriteria(fileExtensionSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);
    }

    @Test
    public void testAudioCompressionModeSearch() {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").getFile());

        final JMediaSearch jMediaSearch = new JMediaSearch();
        SearchQuery query = new SearchQuery();

        boolean result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        AudioCompressionModeSearch audioCompressionModeSearch = new AudioCompressionModeSearch(Op.NOT_EQUAL_TO, CompressionModeEnum.LOSSLESS);
        query.addSearchCriteria(audioCompressionModeSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        audioCompressionModeSearch = new AudioCompressionModeSearch(Op.EQUAL_TO, CompressionModeEnum.LOSSY);
        query.addSearchCriteria(audioCompressionModeSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);
    }

    @Test
    public void testAudioProfileSearch() {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").getFile());

        final JMediaSearch jMediaSearch = new JMediaSearch();
        SearchQuery query = new SearchQuery();

        boolean result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        AudioProfileSearch audioProfileSearch = new AudioProfileSearch(Op.NOT_EQUAL_TO, AudioProfileEnum.AC_3);
        query.addSearchCriteria(audioProfileSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        audioProfileSearch = new AudioProfileSearch(Op.EQUAL_TO, AudioProfileEnum.LAYER_3);
        query.addSearchCriteria(audioProfileSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);
    }

    @Test
    public void testVideoFormatSearch() {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").getFile());

        final JMediaSearch jMediaSearch = new JMediaSearch();
        SearchQuery query = new SearchQuery();

        boolean result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        VideoFormatSearch videoFormatSearch = new VideoFormatSearch(Op.NOT_EQUAL_TO, FormatEnum.HEVC);
        query.addSearchCriteria(videoFormatSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        videoFormatSearch = new VideoFormatSearch(Op.EQUAL_TO, FormatEnum.AVC);
        query.addSearchCriteria(videoFormatSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);
    }

    @Test
    public void testFileSizeSearch() {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").getFile());

        final JMediaSearch jMediaSearch = new JMediaSearch();
        SearchQuery query = new SearchQuery();

        boolean result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        FileSizeSearch videoFormatSearch = new FileSizeSearch(Op.NOT_EQUAL_TO, Long.valueOf(6299255));
        query.addSearchCriteria(videoFormatSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        videoFormatSearch = new FileSizeSearch(Op.EQUAL_TO, Long.valueOf(6299254));
        query.addSearchCriteria(videoFormatSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        videoFormatSearch = new FileSizeSearch(Op.GREATER_THAN, Long.valueOf(6299253));
        query.addSearchCriteria(videoFormatSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        videoFormatSearch = new FileSizeSearch(Op.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(6299253));
        query.addSearchCriteria(videoFormatSearch);
        videoFormatSearch = new FileSizeSearch(Op.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(6299254));
        query.addSearchCriteria(videoFormatSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        videoFormatSearch = new FileSizeSearch(Op.LESS_THAN, Long.valueOf(6299255));
        query.addSearchCriteria(videoFormatSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        videoFormatSearch = new FileSizeSearch(Op.LESS_THAN_OR_EQUAL_TO, Long.valueOf(6299255));
        query.addSearchCriteria(videoFormatSearch);
        videoFormatSearch = new FileSizeSearch(Op.LESS_THAN_OR_EQUAL_TO, Long.valueOf(6299254));
        query.addSearchCriteria(videoFormatSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);
    }

    @Test
    public void testVideoMaxBitRateSearch() {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").getFile());

        final JMediaSearch jMediaSearch = new JMediaSearch();
        SearchQuery query = new SearchQuery();

        boolean result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        VideoMaxBitRateSearch videoMaxBitRateSearch = new VideoMaxBitRateSearch(Op.NOT_EQUAL_TO, Long.valueOf(19999745));
        query.addSearchCriteria(videoMaxBitRateSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        videoMaxBitRateSearch = new VideoMaxBitRateSearch(Op.EQUAL_TO, Long.valueOf(19999744));
        query.addSearchCriteria(videoMaxBitRateSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        videoMaxBitRateSearch = new VideoMaxBitRateSearch(Op.GREATER_THAN, Long.valueOf(19999743));
        query.addSearchCriteria(videoMaxBitRateSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        videoMaxBitRateSearch = new VideoMaxBitRateSearch(Op.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(19999743));
        query.addSearchCriteria(videoMaxBitRateSearch);
        videoMaxBitRateSearch = new VideoMaxBitRateSearch(Op.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(19999744));
        query.addSearchCriteria(videoMaxBitRateSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        videoMaxBitRateSearch = new VideoMaxBitRateSearch(Op.LESS_THAN, Long.valueOf(19999745));
        query.addSearchCriteria(videoMaxBitRateSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        videoMaxBitRateSearch = new VideoMaxBitRateSearch(Op.LESS_THAN_OR_EQUAL_TO, Long.valueOf(19999745));
        query.addSearchCriteria(videoMaxBitRateSearch);
        videoMaxBitRateSearch = new VideoMaxBitRateSearch(Op.LESS_THAN_OR_EQUAL_TO, Long.valueOf(19999744));
        query.addSearchCriteria(videoMaxBitRateSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);
    }

    @Test
    public void testAudioBitRateSearch() {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").getFile());

        final JMediaSearch jMediaSearch = new JMediaSearch();
        SearchQuery query = new SearchQuery();

        boolean result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        AudioBitRateSearch audioBitRateSearch = new AudioBitRateSearch(Op.NOT_EQUAL_TO, Long.valueOf(192001));
        query.addSearchCriteria(audioBitRateSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        audioBitRateSearch = new AudioBitRateSearch(Op.EQUAL_TO, Long.valueOf(192000));
        query.addSearchCriteria(audioBitRateSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        audioBitRateSearch = new AudioBitRateSearch(Op.GREATER_THAN, Long.valueOf(19200));
        query.addSearchCriteria(audioBitRateSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        audioBitRateSearch = new AudioBitRateSearch(Op.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(19200));
        query.addSearchCriteria(audioBitRateSearch);
        audioBitRateSearch = new AudioBitRateSearch(Op.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(192000));
        query.addSearchCriteria(audioBitRateSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        audioBitRateSearch = new AudioBitRateSearch(Op.LESS_THAN, Long.valueOf(192001));
        query.addSearchCriteria(audioBitRateSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        audioBitRateSearch = new AudioBitRateSearch(Op.LESS_THAN_OR_EQUAL_TO, Long.valueOf(192001));
        query.addSearchCriteria(audioBitRateSearch);
        audioBitRateSearch = new AudioBitRateSearch(Op.LESS_THAN_OR_EQUAL_TO, Long.valueOf(192000));
        query.addSearchCriteria(audioBitRateSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);
    }

    @Test
    public void testAudioChannelSearch() {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").getFile());

        final JMediaSearch jMediaSearch = new JMediaSearch();
        SearchQuery query = new SearchQuery();

        boolean result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        AudioChannelSearch audioChannelSearch = new AudioChannelSearch(Op.NOT_EQUAL_TO, Long.valueOf(3));
        query.addSearchCriteria(audioChannelSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        audioChannelSearch = new AudioChannelSearch(Op.EQUAL_TO, Long.valueOf(2));
        query.addSearchCriteria(audioChannelSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        audioChannelSearch = new AudioChannelSearch(Op.GREATER_THAN, Long.valueOf(1));
        query.addSearchCriteria(audioChannelSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        audioChannelSearch = new AudioChannelSearch(Op.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(1));
        query.addSearchCriteria(audioChannelSearch);
        audioChannelSearch = new AudioChannelSearch(Op.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(2));
        query.addSearchCriteria(audioChannelSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        audioChannelSearch = new AudioChannelSearch(Op.LESS_THAN, Long.valueOf(3));
        query.addSearchCriteria(audioChannelSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        audioChannelSearch = new AudioChannelSearch(Op.LESS_THAN_OR_EQUAL_TO, Long.valueOf(3));
        query.addSearchCriteria(audioChannelSearch);
        audioChannelSearch = new AudioChannelSearch(Op.LESS_THAN_OR_EQUAL_TO, Long.valueOf(2));
        query.addSearchCriteria(audioChannelSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);
    }

    @Test
    public void testGeneralDurationSearch() {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").getFile());

        final JMediaSearch jMediaSearch = new JMediaSearch();
        SearchQuery query = new SearchQuery();

        boolean result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        GeneralDurationSearch generalDurationSearch = new GeneralDurationSearch(Op.NOT_EQUAL_TO, Long.valueOf(898169));
        query.addSearchCriteria(generalDurationSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        generalDurationSearch = new GeneralDurationSearch(Op.EQUAL_TO, Long.valueOf(898167));
        query.addSearchCriteria(generalDurationSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        generalDurationSearch = new GeneralDurationSearch(Op.GREATER_THAN, Long.valueOf(898166));
        query.addSearchCriteria(generalDurationSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        generalDurationSearch = new GeneralDurationSearch(Op.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(898167));
        query.addSearchCriteria(generalDurationSearch);
        generalDurationSearch = new GeneralDurationSearch(Op.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(898166));
        query.addSearchCriteria(generalDurationSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        generalDurationSearch = new GeneralDurationSearch(Op.LESS_THAN, Long.valueOf(898168));
        query.addSearchCriteria(generalDurationSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        generalDurationSearch = new GeneralDurationSearch(Op.LESS_THAN_OR_EQUAL_TO, Long.valueOf(898167));
        query.addSearchCriteria(generalDurationSearch);
        generalDurationSearch = new GeneralDurationSearch(Op.LESS_THAN_OR_EQUAL_TO, Long.valueOf(898168));
        query.addSearchCriteria(generalDurationSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);
    }

    @Test
    public void testAudioFormatSearch() {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").getFile());

        final JMediaSearch jMediaSearch = new JMediaSearch();
        SearchQuery query = new SearchQuery();

        boolean result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        AudioFormatSearch audioFormatSearch = new AudioFormatSearch(Op.NOT_EQUAL_TO, FormatEnum.AAF);
        query.addSearchCriteria(audioFormatSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        audioFormatSearch = new AudioFormatSearch(Op.EQUAL_TO, FormatEnum.MPEG_AUDIO);
        query.addSearchCriteria(audioFormatSearch);
        audioFormatSearch = new AudioFormatSearch(Op.EQUAL_TO, FormatEnum.AAC);
        query.addSearchCriteria(audioFormatSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);
    }

    @Test
    public void testVideoCodecSearch() {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").getFile());

        final JMediaSearch jMediaSearch = new JMediaSearch();
        SearchQuery query = new SearchQuery();

        boolean result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        VideoCodecIdSearch videoCodecSearch = new VideoCodecIdSearch(Op.NOT_EQUAL_TO, VideoMatroskaCodecIdEnum.V_MPEG4_IS0_ASP);
        query.addSearchCriteria(videoCodecSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        videoCodecSearch = new VideoCodecIdSearch(Op.EQUAL_TO, VideoMatroskaCodecIdEnum.V_MPEG4_ISO_AVC);
        query.addSearchCriteria(videoCodecSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);
    }

    @Test
    public void testAudioLanguageSearch() {
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
    public void testTextLanguageSearch() {
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
    public void testAudioCodecSearch() {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").getFile());

        final JMediaSearch jMediaSearch = new JMediaSearch();
        SearchQuery query = new SearchQuery();

        boolean result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        AudioCodecIdSearch audioCodecIdSearch = new AudioCodecIdSearch(Op.NOT_EQUAL_TO, AudioMatroskaCodecIdEnum.A_AAC_MPEG2_LC);
        query.addSearchCriteria(audioCodecIdSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        audioCodecIdSearch = new AudioCodecIdSearch(Op.EQUAL_TO, AudioMatroskaCodecIdEnum.A_AAC);
        query.addSearchCriteria(audioCodecIdSearch);
        audioCodecIdSearch = new AudioCodecIdSearch(Op.EQUAL_TO, AudioMatroskaCodecIdEnum.A_MPEG_L3);
        query.addSearchCriteria(audioCodecIdSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);
    }

    @Test
    public void testVideoResolutionSearch() {
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
    public void testVideoAspectRatioSearch() {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").getFile());

        final JMediaSearch jMediaSearch = new JMediaSearch();
        SearchQuery query = new SearchQuery();

        boolean result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        VideoAspectRatioSearch videoAspectRatioSearch = new VideoAspectRatioSearch(Op.NOT_EQUAL_TO, AspectRatio.AS_2_20);
        query.addSearchCriteria(videoAspectRatioSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertFalse(result);

        query = new SearchQuery();
        videoAspectRatioSearch = new VideoAspectRatioSearch(Op.EQUAL_TO, AspectRatio.AS_2_20);
        query.addSearchCriteria(videoAspectRatioSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);

        query = new SearchQuery();
        videoAspectRatioSearch = new VideoAspectRatioSearch(Op.GREATER_THAN, AspectRatio.AS_1_66);
        query.addSearchCriteria(videoAspectRatioSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);
    }
}
