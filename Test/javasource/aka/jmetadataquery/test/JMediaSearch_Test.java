package aka.jmetadataquery.test;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;

import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.main.constants.codecs.AudioMatroskaCodecIdEnum;
import aka.jmetadata.main.constants.codecs.VideoMatroskaCodecIdEnum;
import aka.jmetadata.main.constants.format.FormatEnum;
import aka.jmetadata.main.constants.profile.AudioProfileEnum;
import aka.jmetadata.main.constants.video.AspectRatio;
import aka.jmetadata.test.JMetaDataMenu_Test;
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
import aka.jmetadataquery.main.types.search.operation.AndSearch;
import aka.jmetadataquery.main.types.search.operation.OrSearch;
import aka.jmetadataquery.main.types.search.text.TextLanguageSearch;
import aka.jmetadataquery.main.types.search.video.VideoAspectRatioSearch;
import aka.jmetadataquery.main.types.search.video.VideoCodecIdSearch;
import aka.jmetadataquery.main.types.search.video.VideoFormatSearch;
import aka.jmetadataquery.main.types.search.video.VideoMaxBitRateSearch;
import aka.jmetadataquery.main.types.search.video.VideoResolutionSearch;

public class JMediaSearch_Test {

    @Test
    public void testORSearch() throws URISyntaxException {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").toURI());

        final FileExtensionSearch fileExtensionSearch = new FileExtensionSearch(Op.EQUAL_TO, FileExtensionSearchEnum.AVI);
        final FileExtensionSearch fileExtensionSearch2 = new FileExtensionSearch(Op.EQUAL_TO, FileExtensionSearchEnum.MKV);
        final OrSearch orSearch = new OrSearch(fileExtensionSearch, fileExtensionSearch2);

        final boolean result = orSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);
    }

    @Test
    public void testANDSearch() throws URISyntaxException {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").toURI());

        final FileExtensionSearch fileExtensionSearch = new FileExtensionSearch(Op.NOT_EQUAL_TO, FileExtensionSearchEnum.AVI);
        final FileExtensionSearch fileExtensionSearch2 = new FileExtensionSearch(Op.EQUAL_TO, FileExtensionSearchEnum.MKV);
        final AndSearch andSearch = new AndSearch(fileExtensionSearch, fileExtensionSearch2, false);

        final boolean result = andSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);
    }

    @Test
    public void testSameStreamSearch() throws URISyntaxException {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").toURI());

        final FileExtensionSearch fileExtensionSearch = new FileExtensionSearch(Op.NOT_EQUAL_TO, FileExtensionSearchEnum.AVI);
        final FileExtensionSearch fileExtensionSearch2 = new FileExtensionSearch(Op.NOT_EQUAL_TO, FileExtensionSearchEnum.MKV);
        AndSearch andSearch = new AndSearch(fileExtensionSearch, fileExtensionSearch2, true);

        boolean result = andSearch.isFileMatchingCriteria(file);
        Assert.assertFalse(result);

        final AudioCompressionModeSearch audioCompressionModeSearch = new AudioCompressionModeSearch(Op.EQUAL_TO, CompressionModeEnum.LOSSY);
        final VideoFormatSearch videoFormatSearch = new VideoFormatSearch(Op.EQUAL_TO, FormatEnum.AVC);
        andSearch = new AndSearch(audioCompressionModeSearch, videoFormatSearch, true);

        result = videoFormatSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);
    }

    @Test
    public void testFileExtensionSearch() throws URISyntaxException {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").toURI());

        FileExtensionSearch fileExtensionSearch = new FileExtensionSearch(Op.NOT_EQUAL_TO, FileExtensionSearchEnum.AVI);
        boolean result = fileExtensionSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        fileExtensionSearch = new FileExtensionSearch(Op.EQUAL_TO, FileExtensionSearchEnum.MKV);
        result = fileExtensionSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);
    }

    @Test
    public void testAudioCompressionModeSearch() throws URISyntaxException {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").toURI());

        AudioCompressionModeSearch audioCompressionModeSearch = new AudioCompressionModeSearch(Op.NOT_EQUAL_TO, CompressionModeEnum.LOSSLESS);
        boolean result = audioCompressionModeSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        audioCompressionModeSearch = new AudioCompressionModeSearch(Op.EQUAL_TO, CompressionModeEnum.LOSSY);

        result = audioCompressionModeSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);
    }

    @Test
    public void testAudioProfileSearch() throws URISyntaxException {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").toURI());

        AudioProfileSearch audioProfileSearch = new AudioProfileSearch(Op.NOT_EQUAL_TO, AudioProfileEnum.AC_3);
        boolean result = audioProfileSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        audioProfileSearch = new AudioProfileSearch(Op.EQUAL_TO, AudioProfileEnum.LAYER_3);
        result = audioProfileSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);
    }

    @Test
    public void testVideoFormatSearch() throws URISyntaxException {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").toURI());

        VideoFormatSearch videoFormatSearch = new VideoFormatSearch(Op.NOT_EQUAL_TO, FormatEnum.HEVC);

        boolean result = videoFormatSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        videoFormatSearch = new VideoFormatSearch(Op.EQUAL_TO, FormatEnum.AVC);
        result = videoFormatSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);
    }

    @Test
    public void testFileSizeSearch() throws URISyntaxException {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").toURI());

        FileSizeSearch videoFormatSearch = new FileSizeSearch(Op.NOT_EQUAL_TO, Long.valueOf(6299255));
        boolean result = videoFormatSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        videoFormatSearch = new FileSizeSearch(Op.EQUAL_TO, Long.valueOf(6299254));
        result = videoFormatSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        videoFormatSearch = new FileSizeSearch(Op.GREATER_THAN, Long.valueOf(6299253));
        result = videoFormatSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        videoFormatSearch = new FileSizeSearch(Op.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(6299253));
        result = videoFormatSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        videoFormatSearch = new FileSizeSearch(Op.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(6299254));
        result = videoFormatSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        videoFormatSearch = new FileSizeSearch(Op.LESS_THAN, Long.valueOf(6299255));
        result = videoFormatSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        videoFormatSearch = new FileSizeSearch(Op.LESS_THAN_OR_EQUAL_TO, Long.valueOf(6299255));
        result = videoFormatSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        videoFormatSearch = new FileSizeSearch(Op.LESS_THAN_OR_EQUAL_TO, Long.valueOf(6299254));
        result = videoFormatSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);
    }

    @Test
    public void testVideoMaxBitRateSearch() throws URISyntaxException {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").toURI());

        VideoMaxBitRateSearch videoMaxBitRateSearch = new VideoMaxBitRateSearch(Op.NOT_EQUAL_TO, Long.valueOf(19999745));

        boolean result = videoMaxBitRateSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        videoMaxBitRateSearch = new VideoMaxBitRateSearch(Op.EQUAL_TO, Long.valueOf(19999744));
        result = videoMaxBitRateSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        videoMaxBitRateSearch = new VideoMaxBitRateSearch(Op.GREATER_THAN, Long.valueOf(19999743));
        result = videoMaxBitRateSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        videoMaxBitRateSearch = new VideoMaxBitRateSearch(Op.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(19999743));
        result = videoMaxBitRateSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        videoMaxBitRateSearch = new VideoMaxBitRateSearch(Op.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(19999744));
        result = videoMaxBitRateSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        videoMaxBitRateSearch = new VideoMaxBitRateSearch(Op.LESS_THAN, Long.valueOf(19999745));
        result = videoMaxBitRateSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        videoMaxBitRateSearch = new VideoMaxBitRateSearch(Op.LESS_THAN_OR_EQUAL_TO, Long.valueOf(19999745));
        result = videoMaxBitRateSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        videoMaxBitRateSearch = new VideoMaxBitRateSearch(Op.LESS_THAN_OR_EQUAL_TO, Long.valueOf(19999744));
        result = videoMaxBitRateSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);
    }

    @Test
    public void testAudioBitRateSearch() throws URISyntaxException {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").toURI());

        AudioBitRateSearch audioBitRateSearch = new AudioBitRateSearch(Op.NOT_EQUAL_TO, Long.valueOf(192001));
        boolean result = audioBitRateSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        audioBitRateSearch = new AudioBitRateSearch(Op.EQUAL_TO, Long.valueOf(192000));
        result = audioBitRateSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        audioBitRateSearch = new AudioBitRateSearch(Op.GREATER_THAN, Long.valueOf(19200));
        result = audioBitRateSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        audioBitRateSearch = new AudioBitRateSearch(Op.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(19200));
        result = audioBitRateSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        audioBitRateSearch = new AudioBitRateSearch(Op.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(192000));
        result = audioBitRateSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        audioBitRateSearch = new AudioBitRateSearch(Op.LESS_THAN, Long.valueOf(192001));
        result = audioBitRateSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        audioBitRateSearch = new AudioBitRateSearch(Op.LESS_THAN_OR_EQUAL_TO, Long.valueOf(192001));
        result = audioBitRateSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        audioBitRateSearch = new AudioBitRateSearch(Op.LESS_THAN_OR_EQUAL_TO, Long.valueOf(192000));
        result = audioBitRateSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);
    }

    @Test
    public void testAudioChannelSearch() throws URISyntaxException {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").toURI());

        AudioChannelSearch audioChannelSearch = new AudioChannelSearch(Op.NOT_EQUAL_TO, Long.valueOf(3));

        boolean result = audioChannelSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        audioChannelSearch = new AudioChannelSearch(Op.EQUAL_TO, Long.valueOf(2));
        result = audioChannelSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        audioChannelSearch = new AudioChannelSearch(Op.GREATER_THAN, Long.valueOf(1));
        result = audioChannelSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        audioChannelSearch = new AudioChannelSearch(Op.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(1));
        result = audioChannelSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        audioChannelSearch = new AudioChannelSearch(Op.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(2));
        result = audioChannelSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        audioChannelSearch = new AudioChannelSearch(Op.LESS_THAN, Long.valueOf(3));

        result = audioChannelSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        audioChannelSearch = new AudioChannelSearch(Op.LESS_THAN_OR_EQUAL_TO, Long.valueOf(3));
        result = audioChannelSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        audioChannelSearch = new AudioChannelSearch(Op.LESS_THAN_OR_EQUAL_TO, Long.valueOf(2));
        result = audioChannelSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);
    }

    @Test
    public void testGeneralDurationSearch() throws URISyntaxException {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").toURI());

        GeneralDurationSearch generalDurationSearch = new GeneralDurationSearch(Op.NOT_EQUAL_TO, Long.valueOf(898169));

        boolean result = generalDurationSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        generalDurationSearch = new GeneralDurationSearch(Op.EQUAL_TO, Long.valueOf(898167));
        result = generalDurationSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        generalDurationSearch = new GeneralDurationSearch(Op.GREATER_THAN, Long.valueOf(898166));
        result = generalDurationSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        generalDurationSearch = new GeneralDurationSearch(Op.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(898167));
        result = generalDurationSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        generalDurationSearch = new GeneralDurationSearch(Op.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(898166));
        result = generalDurationSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        generalDurationSearch = new GeneralDurationSearch(Op.LESS_THAN, Long.valueOf(898168));
        result = generalDurationSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        generalDurationSearch = new GeneralDurationSearch(Op.LESS_THAN_OR_EQUAL_TO, Long.valueOf(898167));
        result = generalDurationSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        generalDurationSearch = new GeneralDurationSearch(Op.LESS_THAN_OR_EQUAL_TO, Long.valueOf(898168));
        result = generalDurationSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);
    }

    @Test
    public void testAudioFormatSearch() throws URISyntaxException {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").toURI());

        AudioFormatSearch audioFormatSearch = new AudioFormatSearch(Op.NOT_EQUAL_TO, FormatEnum.AAF);
        boolean result = audioFormatSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        audioFormatSearch = new AudioFormatSearch(Op.EQUAL_TO, FormatEnum.MPEG_AUDIO);
        result = audioFormatSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        audioFormatSearch = new AudioFormatSearch(Op.EQUAL_TO, FormatEnum.AAC);
        result = audioFormatSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        result = audioFormatSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);
    }

    @Test
    public void testVideoCodecSearch() throws URISyntaxException {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").toURI());

        VideoCodecIdSearch videoCodecSearch = new VideoCodecIdSearch(Op.NOT_EQUAL_TO, VideoMatroskaCodecIdEnum.V_MPEG4_IS0_ASP);
        boolean result = videoCodecSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        videoCodecSearch = new VideoCodecIdSearch(Op.EQUAL_TO, VideoMatroskaCodecIdEnum.V_MPEG4_ISO_AVC);
        result = videoCodecSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);
    }

    @Test
    public void testAudioLanguageSearch() throws URISyntaxException {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").toURI());

        AudioLanguageSearch audioLanguageSearch = new AudioLanguageSearch(Op.NOT_EQUAL_TO, LanguageEnum.FRENCH);
        boolean result = audioLanguageSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        audioLanguageSearch = new AudioLanguageSearch(Op.EQUAL_TO, LanguageEnum.ENGLISH);
        result = audioLanguageSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);
    }

    @Test
    public void testTextLanguageSearch() throws URISyntaxException {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").toURI());

        TextLanguageSearch textLanguageSearch = new TextLanguageSearch(Op.NOT_EQUAL_TO, LanguageEnum.FRENCH);
        boolean result = textLanguageSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        textLanguageSearch = new TextLanguageSearch(Op.EQUAL_TO, LanguageEnum.GERMAN);
        result = textLanguageSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        textLanguageSearch = new TextLanguageSearch(Op.EQUAL_TO, LanguageEnum.ENGLISH);
        result = textLanguageSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        result = textLanguageSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);
    }

    @Test
    public void testAudioCodecSearch() throws URISyntaxException {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").toURI());

        AudioCodecIdSearch audioCodecIdSearch = new AudioCodecIdSearch(Op.NOT_EQUAL_TO, AudioMatroskaCodecIdEnum.A_AAC_MPEG2_LC);
        boolean result = audioCodecIdSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        audioCodecIdSearch = new AudioCodecIdSearch(Op.EQUAL_TO, AudioMatroskaCodecIdEnum.A_AAC);
        result = audioCodecIdSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        audioCodecIdSearch = new AudioCodecIdSearch(Op.EQUAL_TO, AudioMatroskaCodecIdEnum.A_MPEG_L3);
        result = audioCodecIdSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        result = audioCodecIdSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);
    }

    @Test
    public void testVideoResolutionSearch() throws URISyntaxException {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").toURI());

        VideoResolutionSearch videoResolutionSearch = new VideoResolutionSearch(Op.NOT_EQUAL_TO, VideoResolutionSearchEnum.R_720);
        boolean result = videoResolutionSearch.isFileMatchingCriteria(file);
        Assert.assertFalse(result);

        videoResolutionSearch = new VideoResolutionSearch(Op.EQUAL_TO, VideoResolutionSearchEnum.R_720);
        result = videoResolutionSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        videoResolutionSearch = new VideoResolutionSearch(Op.GREATER_THAN, VideoResolutionSearchEnum.R_480);
        result = videoResolutionSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);
    }

    @Test
    public void testVideoAspectRatioSearch() throws URISyntaxException {
        final ClassLoader classLoader = JMetaDataMenu_Test.class.getClassLoader();
        final File file = new File(classLoader.getResource("Sintel_DivXPlus_6500kbps.mkv").toURI());

        VideoAspectRatioSearch videoAspectRatioSearch = new VideoAspectRatioSearch(Op.NOT_EQUAL_TO, AspectRatio.AS_2_20);
        boolean result = videoAspectRatioSearch.isFileMatchingCriteria(file);
        Assert.assertFalse(result);

        videoAspectRatioSearch = new VideoAspectRatioSearch(Op.EQUAL_TO, AspectRatio.AS_2_20);
        result = videoAspectRatioSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);

        videoAspectRatioSearch = new VideoAspectRatioSearch(Op.GREATER_THAN, AspectRatio.AS_1_66);
        result = videoAspectRatioSearch.isFileMatchingCriteria(file);
        Assert.assertTrue(result);
    }
}
