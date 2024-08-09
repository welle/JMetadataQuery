package aka.jmetadataquery.test;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.RuntimeErrorException;

import org.eclipse.jdt.annotation.NonNull;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import aka.jmetadata.main.constants.codecs.AudioMatroskaCodecIdEnum;
import aka.jmetadata.main.constants.codecs.VideoMatroskaCodecIdEnum;
import aka.jmetadata.main.constants.format.FormatEnum;
import aka.jmetadata.main.constants.profile.AudioProfileEnum;
import aka.jmetadata.main.constants.video.AspectRatio;
import aka.jmetadataquery.JMetadataQuery;
import aka.jmetadataquery.consumer.FileConsumerContext;
import aka.jmetadataquery.search.constants.LanguageEnum;
import aka.jmetadataquery.search.constants.audio.AudioCommercialFormatEnum;
import aka.jmetadataquery.search.constants.audio.CompressionModeEnum;
import aka.jmetadataquery.search.constants.conditions.Operator;
import aka.jmetadataquery.search.constants.file.FileExtensionSearchEnum;
import aka.jmetadataquery.search.constants.video.VideoCommercialFormatEnum;
import aka.jmetadataquery.search.constants.video.VideoResolutionSearchEnum;
import aka.jmetadataquery.search.operation.AndSearch;
import aka.jmetadataquery.search.operation.OrSearch;
import aka.jmetadataquery.search.types.audio.AudioBitRateSearch;
import aka.jmetadataquery.search.types.audio.AudioChannelSearch;
import aka.jmetadataquery.search.types.audio.AudioCodecIdSearch;
import aka.jmetadataquery.search.types.audio.AudioCommericalFormatSearch;
import aka.jmetadataquery.search.types.audio.AudioCompressionModeSearch;
import aka.jmetadataquery.search.types.audio.AudioFormatSearch;
import aka.jmetadataquery.search.types.audio.AudioLanguageSearch;
import aka.jmetadataquery.search.types.audio.AudioNumberOfStreamSearch;
import aka.jmetadataquery.search.types.audio.AudioProfileSearch;
import aka.jmetadataquery.search.types.file.FileExtensionSearch;
import aka.jmetadataquery.search.types.file.FileSizeSearch;
import aka.jmetadataquery.search.types.general.GeneralDurationSearch;
import aka.jmetadataquery.search.types.text.TextLanguageSearch;
import aka.jmetadataquery.search.types.text.TextNumberOfStreamSearch;
import aka.jmetadataquery.search.types.video.VideoAspectRatioSearch;
import aka.jmetadataquery.search.types.video.VideoCodecIdSearch;
import aka.jmetadataquery.search.types.video.VideoCommercialFormatSearch;
import aka.jmetadataquery.search.types.video.VideoFormatSearch;
import aka.jmetadataquery.search.types.video.VideoMaxBitRateSearch;
import aka.jmetadataquery.search.types.video.VideoResolutionSearch;

@SuppressWarnings("javadoc")
public class JMetadataQuery_Test {
	private static @NonNull final Logger LOGGER = Logger.getLogger(JMetadataQuery_Test.class.getName());

	private static File FILE;

	/**
	 * Initialize test.
	 */
	@BeforeClass
	public static void beforeUnit() {
		try {
			FILE = new File(ClassLoader.getSystemClassLoader().getResource("Sintel_DivXPlus_6500kbps.mkv").toURI());
		} catch (final Throwable e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, e.getMessage());
			throw new RuntimeErrorException(null, "Can not find file.");
		}
	}

	@Test
	public void testApplyIntoDirectory() {
		FileExtensionSearch fileExtensionSearch = new FileExtensionSearch(Operator.EQUAL_TO,
		        FileExtensionSearchEnum.AVI);
		FileExtensionSearch fileExtensionSearch2 = new FileExtensionSearch(Operator.EQUAL_TO,
		        FileExtensionSearchEnum.MKV);
		OrSearch orSearch = new OrSearch(false, fileExtensionSearch, fileExtensionSearch2);

		final JMetadataQuery jMetadataQuery = new JMetadataQuery();
		List<@NonNull FileConsumerContext> result = jMetadataQuery.applyIntoDirectory(FILE.getParent(), true, orSearch);
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.size());

		fileExtensionSearch = new FileExtensionSearch(Operator.EQUAL_TO, FileExtensionSearchEnum.AVI);
		fileExtensionSearch2 = new FileExtensionSearch(Operator.EQUAL_TO, FileExtensionSearchEnum.MPEG);
		orSearch = new OrSearch(false, fileExtensionSearch, fileExtensionSearch2);

		result = jMetadataQuery.applyIntoDirectory(FILE.getParent(), true, orSearch);
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.size());
		for (final FileConsumerContext fileConsumerContext : result) {
			Assert.assertFalse(fileConsumerContext.getResult());
		}
	}

	@Test
	public void testSearchIntoDirectory() {
		FileExtensionSearch fileExtensionSearch = new FileExtensionSearch(Operator.EQUAL_TO,
		        FileExtensionSearchEnum.AVI);
		FileExtensionSearch fileExtensionSearch2 = new FileExtensionSearch(Operator.EQUAL_TO,
		        FileExtensionSearchEnum.MKV);
		OrSearch orSearch = new OrSearch(false, fileExtensionSearch, fileExtensionSearch2);

		final JMetadataQuery jMetadataQuery = new JMetadataQuery();
		List<@NonNull File> result = jMetadataQuery.searchIntoDirectory(FILE.getParent(), true, orSearch);
		Assert.assertNotNull(result);
		Assert.assertEquals(1, result.size());

		fileExtensionSearch = new FileExtensionSearch(Operator.EQUAL_TO, FileExtensionSearchEnum.AVI);
		fileExtensionSearch2 = new FileExtensionSearch(Operator.EQUAL_TO, FileExtensionSearchEnum.MPEG);
		orSearch = new OrSearch(false, fileExtensionSearch, fileExtensionSearch2);

		result = jMetadataQuery.searchIntoDirectory(FILE.getParent(), true, orSearch);
		Assert.assertNotNull(result);
		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testORSearch() {
		final FileExtensionSearch fileExtensionSearch = new FileExtensionSearch(Operator.EQUAL_TO,
		        FileExtensionSearchEnum.AVI);
		final FileExtensionSearch fileExtensionSearch2 = new FileExtensionSearch(Operator.EQUAL_TO,
		        FileExtensionSearchEnum.MKV);
		final OrSearch orSearch = new OrSearch(false, fileExtensionSearch, fileExtensionSearch2);

		assert FILE != null;
		final boolean result = orSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testMultiple() {
		final AndSearch subRootAndSearch = new AndSearch(true);
		// Get all required
		final FileExtensionSearch fileExtensionSearch = new FileExtensionSearch(Operator.NOT_EQUAL_TO,
		        FileExtensionSearchEnum.AVI);
		subRootAndSearch.addSearch(fileExtensionSearch);
		final FileExtensionSearch fileExtensionSearch2 = new FileExtensionSearch(Operator.EQUAL_TO,
		        FileExtensionSearchEnum.MKV);
		subRootAndSearch.addSearch(fileExtensionSearch2);

		assert FILE != null;
		final boolean result = subRootAndSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testANDSearch() {
		final FileExtensionSearch fileExtensionSearch = new FileExtensionSearch(Operator.NOT_EQUAL_TO,
		        FileExtensionSearchEnum.AVI);
		final FileExtensionSearch fileExtensionSearch2 = new FileExtensionSearch(Operator.EQUAL_TO,
		        FileExtensionSearchEnum.MKV);
		final AndSearch andSearch = new AndSearch(false, fileExtensionSearch, fileExtensionSearch2);

		assert FILE != null;
		final boolean result = andSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testSameStreamSearch() {
		FileExtensionSearch fileExtensionSearch = new FileExtensionSearch(Operator.NOT_EQUAL_TO,
		        FileExtensionSearchEnum.AVI);
		FileExtensionSearch fileExtensionSearch2 = new FileExtensionSearch(Operator.EQUAL_TO,
		        FileExtensionSearchEnum.MKV);
		AndSearch andSearch = new AndSearch(true, fileExtensionSearch, fileExtensionSearch2);

		assert FILE != null;
		boolean result = andSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		fileExtensionSearch = new FileExtensionSearch(Operator.NOT_EQUAL_TO, FileExtensionSearchEnum.AVI);
		fileExtensionSearch2 = new FileExtensionSearch(Operator.EQUAL_TO, FileExtensionSearchEnum.MKV);
		andSearch = new AndSearch(true, fileExtensionSearch, fileExtensionSearch2);

		assert FILE != null;
		result = andSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		final AudioCompressionModeSearch audioCompressionModeSearch = new AudioCompressionModeSearch(Operator.EQUAL_TO,
		        CompressionModeEnum.LOSSY);
		final VideoFormatSearch videoFormatSearch = new VideoFormatSearch(Operator.EQUAL_TO, FormatEnum.AVC);
		andSearch = new AndSearch(true, audioCompressionModeSearch, videoFormatSearch);

		assert FILE != null;
		result = videoFormatSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testAudioNumberOfStream() {
		AudioNumberOfStreamSearch audioNumberOfStreamSearch = new AudioNumberOfStreamSearch(Operator.EQUAL_TO,
		        Long.valueOf(2));
		assert FILE != null;
		boolean result = audioNumberOfStreamSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioNumberOfStreamSearch = new AudioNumberOfStreamSearch(Operator.NOT_EQUAL_TO, Long.valueOf(1));
		assert FILE != null;
		result = audioNumberOfStreamSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testAudioCommercialFormat() {
		AudioCommericalFormatSearch audioCommericalFormatSearch = new AudioCommericalFormatSearch(Operator.EQUAL_TO,
		        AudioCommercialFormatEnum.AAC);
		assert FILE != null;
		boolean result = audioCommericalFormatSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioCommericalFormatSearch = new AudioCommericalFormatSearch(Operator.NOT_EQUAL_TO,
		        AudioCommercialFormatEnum.VORBIS);
		assert FILE != null;
		result = audioCommericalFormatSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testVideoCommercialFormat() {
		VideoCommercialFormatSearch videoCommercialFormatSearch = new VideoCommercialFormatSearch(Operator.EQUAL_TO,
		        VideoCommercialFormatEnum.AVC);
		assert FILE != null;
		boolean result = videoCommercialFormatSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		videoCommercialFormatSearch = new VideoCommercialFormatSearch(Operator.NOT_EQUAL_TO,
		        VideoCommercialFormatEnum.DV);
		assert FILE != null;
		result = videoCommercialFormatSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testTextNumberOfStream() {
		TextNumberOfStreamSearch textNumberOfStreamSearch = new TextNumberOfStreamSearch(Operator.EQUAL_TO,
		        Long.valueOf(8));
		assert FILE != null;boolean result = textNumberOfStreamSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		textNumberOfStreamSearch = new TextNumberOfStreamSearch(Operator.GREATER_THAN, Long.valueOf(2));
		assert FILE != null;result = textNumberOfStreamSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		textNumberOfStreamSearch = new TextNumberOfStreamSearch(Operator.NOT_EQUAL_TO, Long.valueOf(9));
		assert FILE != null;result = textNumberOfStreamSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testFileExtensionSearch() {
		FileExtensionSearch fileExtensionSearch = new FileExtensionSearch(Operator.NOT_EQUAL_TO,
		        FileExtensionSearchEnum.AVI);
		assert FILE != null;boolean result = fileExtensionSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		fileExtensionSearch = new FileExtensionSearch(Operator.EQUAL_TO, FileExtensionSearchEnum.MKV);
		assert FILE != null;result = fileExtensionSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testAudioCompressionModeSearch() {
		AudioCompressionModeSearch audioCompressionModeSearch = new AudioCompressionModeSearch(Operator.NOT_EQUAL_TO,
		        CompressionModeEnum.LOSSLESS);
		assert FILE != null;boolean result = audioCompressionModeSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioCompressionModeSearch = new AudioCompressionModeSearch(Operator.EQUAL_TO, CompressionModeEnum.LOSSY);

		assert FILE != null;result = audioCompressionModeSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testAudioProfileSearch() {
		AudioProfileSearch audioProfileSearch = new AudioProfileSearch(Operator.NOT_EQUAL_TO, AudioProfileEnum.AC_3);
		assert FILE != null;boolean result = audioProfileSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioProfileSearch = new AudioProfileSearch(Operator.NOT_EQUAL_TO, AudioProfileEnum.LAYER_3);
		assert FILE != null;result=audioProfileSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testMultipleSearch() {
		AndSearch sameStreamAndSearch = new AndSearch(false);
		AudioProfileSearch audioProfileSearch = new AudioProfileSearch(Operator.NOT_EQUAL_TO, AudioProfileEnum.AC_3);
		sameStreamAndSearch.addSearch(audioProfileSearch);
		audioProfileSearch = new AudioProfileSearch(Operator.EQUAL_TO, AudioProfileEnum.LAYER_3);
		sameStreamAndSearch.addSearch(audioProfileSearch);
		assert FILE != null;boolean result=sameStreamAndSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		sameStreamAndSearch = new AndSearch(true);
		audioProfileSearch = new AudioProfileSearch(Operator.NOT_EQUAL_TO, AudioProfileEnum.AC_3);
		sameStreamAndSearch.addSearch(audioProfileSearch);
		audioProfileSearch = new AudioProfileSearch(Operator.NOT_EQUAL_TO, AudioProfileEnum.AC_3_ATMOS);
		sameStreamAndSearch.addSearch(audioProfileSearch);
		assert FILE != null;result=sameStreamAndSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		sameStreamAndSearch = new AndSearch(true);
		audioProfileSearch = new AudioProfileSearch(Operator.EQUAL_TO, AudioProfileEnum.AC_3);
		sameStreamAndSearch.addSearch(audioProfileSearch);
		audioProfileSearch = new AudioProfileSearch(Operator.EQUAL_TO, AudioProfileEnum.LAYER_3);
		sameStreamAndSearch.addSearch(audioProfileSearch);
		assert FILE != null;result=sameStreamAndSearch.isFileMatchingCriteria(FILE);
		Assert.assertFalse(result);

		sameStreamAndSearch = new AndSearch(true);
		audioProfileSearch = new AudioProfileSearch(Operator.EQUAL_TO, AudioProfileEnum.AC_3);
		sameStreamAndSearch.addSearch(audioProfileSearch);
		audioProfileSearch = new AudioProfileSearch(Operator.EQUAL_TO, AudioProfileEnum.AC_3_ATMOS);
		sameStreamAndSearch.addSearch(audioProfileSearch);
		assert FILE != null;result=sameStreamAndSearch.isFileMatchingCriteria(FILE);
		Assert.assertFalse(result);
	}

	@Test
	public void testVideoFormatSearch() {
		VideoFormatSearch videoFormatSearch = new VideoFormatSearch(Operator.NOT_EQUAL_TO, FormatEnum.HEVC);

		assert FILE != null;boolean result=videoFormatSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		videoFormatSearch = new VideoFormatSearch(Operator.EQUAL_TO, FormatEnum.AVC);
		assert FILE != null;result=videoFormatSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testFileSizeSearch() {
		FileSizeSearch videoFormatSearch = new FileSizeSearch(Operator.NOT_EQUAL_TO, Long.valueOf(6299255));
		assert FILE != null;boolean result=videoFormatSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		videoFormatSearch = new FileSizeSearch(Operator.EQUAL_TO, Long.valueOf(6299254));
		assert FILE != null;result=videoFormatSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		videoFormatSearch = new FileSizeSearch(Operator.GREATER_THAN, Long.valueOf(6299253));
		assert FILE != null;result=videoFormatSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		videoFormatSearch = new FileSizeSearch(Operator.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(6299253));
		assert FILE != null;result=videoFormatSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		videoFormatSearch = new FileSizeSearch(Operator.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(6299254));
		assert FILE != null;result=videoFormatSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		videoFormatSearch = new FileSizeSearch(Operator.LESS_THAN, Long.valueOf(6299255));
		assert FILE != null;result=videoFormatSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		videoFormatSearch = new FileSizeSearch(Operator.LESS_THAN_OR_EQUAL_TO, Long.valueOf(6299255));
		assert FILE != null;result=videoFormatSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		videoFormatSearch = new FileSizeSearch(Operator.LESS_THAN_OR_EQUAL_TO, Long.valueOf(6299254));
		assert FILE != null;result=videoFormatSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testVideoMaxBitRateSearch() {
		VideoMaxBitRateSearch videoMaxBitRateSearch = new VideoMaxBitRateSearch(Operator.NOT_EQUAL_TO,
		        Long.valueOf(19999745));

		assert FILE != null;boolean result=videoMaxBitRateSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		videoMaxBitRateSearch = new VideoMaxBitRateSearch(Operator.EQUAL_TO, Long.valueOf(19999744));
		assert FILE != null;result=videoMaxBitRateSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		videoMaxBitRateSearch = new VideoMaxBitRateSearch(Operator.GREATER_THAN, Long.valueOf(19999743));
		assert FILE != null;result=videoMaxBitRateSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		videoMaxBitRateSearch = new VideoMaxBitRateSearch(Operator.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(19999743));
		assert FILE != null;result=videoMaxBitRateSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		videoMaxBitRateSearch = new VideoMaxBitRateSearch(Operator.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(19999744));
		assert FILE != null;result=videoMaxBitRateSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		videoMaxBitRateSearch = new VideoMaxBitRateSearch(Operator.LESS_THAN, Long.valueOf(19999745));
		assert FILE != null;result=videoMaxBitRateSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		videoMaxBitRateSearch = new VideoMaxBitRateSearch(Operator.LESS_THAN_OR_EQUAL_TO, Long.valueOf(19999745));
		assert FILE != null;result=videoMaxBitRateSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		videoMaxBitRateSearch = new VideoMaxBitRateSearch(Operator.LESS_THAN_OR_EQUAL_TO, Long.valueOf(19999744));
		assert FILE != null;result=videoMaxBitRateSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testAudioBitRateSearch() {
		AudioBitRateSearch audioBitRateSearch = new AudioBitRateSearch(Operator.NOT_EQUAL_TO, Long.valueOf(192001));
		assert FILE != null;boolean result = audioBitRateSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioBitRateSearch = new AudioBitRateSearch(Operator.EQUAL_TO, Long.valueOf(192000));
		assert FILE != null;result=audioBitRateSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioBitRateSearch = new AudioBitRateSearch(Operator.GREATER_THAN, Long.valueOf(19200));
		assert FILE != null;result=audioBitRateSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioBitRateSearch = new AudioBitRateSearch(Operator.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(19200));
		assert FILE != null;result=audioBitRateSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioBitRateSearch = new AudioBitRateSearch(Operator.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(192000));
		assert FILE != null;result=audioBitRateSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioBitRateSearch = new AudioBitRateSearch(Operator.LESS_THAN, Long.valueOf(192001));
		assert FILE != null;result=audioBitRateSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioBitRateSearch = new AudioBitRateSearch(Operator.LESS_THAN_OR_EQUAL_TO, Long.valueOf(192001));
		assert FILE != null;result=audioBitRateSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioBitRateSearch = new AudioBitRateSearch(Operator.LESS_THAN_OR_EQUAL_TO, Long.valueOf(192000));
		assert FILE != null;result=audioBitRateSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testAudioChannelSearch() {
		AudioChannelSearch audioChannelSearch = new AudioChannelSearch(Operator.NOT_EQUAL_TO, Long.valueOf(3));

		assert FILE != null;boolean result = audioChannelSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioChannelSearch = new AudioChannelSearch(Operator.EQUAL_TO, Long.valueOf(2));
		assert FILE != null;result=audioChannelSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioChannelSearch = new AudioChannelSearch(Operator.GREATER_THAN, Long.valueOf(1));
		assert FILE != null;result=audioChannelSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioChannelSearch = new AudioChannelSearch(Operator.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(1));
		assert FILE != null;result=audioChannelSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioChannelSearch = new AudioChannelSearch(Operator.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(2));
		assert FILE != null;result=audioChannelSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioChannelSearch = new AudioChannelSearch(Operator.LESS_THAN, Long.valueOf(3));

		assert FILE != null;result=audioChannelSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioChannelSearch = new AudioChannelSearch(Operator.LESS_THAN_OR_EQUAL_TO, Long.valueOf(3));
		assert FILE != null;result=audioChannelSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioChannelSearch = new AudioChannelSearch(Operator.LESS_THAN_OR_EQUAL_TO, Long.valueOf(2));
		assert FILE != null;result=audioChannelSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testGeneralDurationSearch() {
		GeneralDurationSearch generalDurationSearch = new GeneralDurationSearch(Operator.NOT_EQUAL_TO,
		        Long.valueOf(898169));

		assert FILE != null;boolean result = generalDurationSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		generalDurationSearch = new GeneralDurationSearch(Operator.EQUAL_TO, Long.valueOf(898167));
		assert FILE != null;result=generalDurationSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		generalDurationSearch = new GeneralDurationSearch(Operator.GREATER_THAN, Long.valueOf(898166));
		assert FILE != null;result=generalDurationSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		generalDurationSearch = new GeneralDurationSearch(Operator.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(898167));
		assert FILE != null;result=generalDurationSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		generalDurationSearch = new GeneralDurationSearch(Operator.GREATER_THAN_OR_EQUAL_TO, Long.valueOf(898166));
		assert FILE != null;result=generalDurationSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		generalDurationSearch = new GeneralDurationSearch(Operator.LESS_THAN, Long.valueOf(898168));
		assert FILE != null;result=generalDurationSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		generalDurationSearch = new GeneralDurationSearch(Operator.LESS_THAN_OR_EQUAL_TO, Long.valueOf(898167));
		assert FILE != null;result=generalDurationSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		generalDurationSearch = new GeneralDurationSearch(Operator.LESS_THAN_OR_EQUAL_TO, Long.valueOf(898168));
		assert FILE != null;result=generalDurationSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testAudioFormatSearch() {
		AudioFormatSearch audioFormatSearch = new AudioFormatSearch(Operator.NOT_EQUAL_TO, FormatEnum.AAF);
		assert FILE != null;boolean result = audioFormatSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioFormatSearch = new AudioFormatSearch(Operator.EQUAL_TO, FormatEnum.MPEG_AUDIO);
		assert FILE != null;result=audioFormatSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioFormatSearch = new AudioFormatSearch(Operator.EQUAL_TO, FormatEnum.AAC);
		assert FILE != null;result=audioFormatSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		assert FILE != null;result=audioFormatSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testVideoCodecSearch() {
		VideoCodecIdSearch videoCodecSearch = new VideoCodecIdSearch(Operator.NOT_EQUAL_TO,
		        VideoMatroskaCodecIdEnum.V_MPEG4_IS0_ASP);
		assert FILE != null;boolean result=videoCodecSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		videoCodecSearch = new VideoCodecIdSearch(Operator.EQUAL_TO, VideoMatroskaCodecIdEnum.V_MPEG4_ISO_AVC);
		assert FILE != null;result=videoCodecSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testAudioLanguageSearch() {
		AudioLanguageSearch audioLanguageSearch = new AudioLanguageSearch(Operator.NOT_EQUAL_TO, LanguageEnum.FRENCH);
		assert FILE != null;boolean result=audioLanguageSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioLanguageSearch = new AudioLanguageSearch(Operator.NOT_EQUAL_TO, LanguageEnum.GERMAN);
		assert FILE != null;result=audioLanguageSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioLanguageSearch = new AudioLanguageSearch(Operator.EQUAL_TO, LanguageEnum.ENGLISH);
		assert FILE != null;result=audioLanguageSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testTextLanguageSearch() {
		TextLanguageSearch textLanguageSearch = new TextLanguageSearch(Operator.NOT_EQUAL_TO, LanguageEnum.FRENCH);
		assert FILE != null;boolean result=textLanguageSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		textLanguageSearch = new TextLanguageSearch(Operator.EQUAL_TO, LanguageEnum.GERMAN);
		assert FILE != null;result=textLanguageSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		textLanguageSearch = new TextLanguageSearch(Operator.EQUAL_TO, LanguageEnum.ENGLISH);
		assert FILE != null;result=textLanguageSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		assert FILE != null;result=textLanguageSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testAudioCodecSearch() {
		AudioCodecIdSearch audioCodecIdSearch = new AudioCodecIdSearch(Operator.NOT_EQUAL_TO,
		        AudioMatroskaCodecIdEnum.A_AAC_MPEG2_LC);
		assert FILE != null;boolean result=audioCodecIdSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioCodecIdSearch = new AudioCodecIdSearch(Operator.NOT_EQUAL_TO, AudioMatroskaCodecIdEnum.A_AAC);
		assert FILE != null;result=audioCodecIdSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		audioCodecIdSearch = new AudioCodecIdSearch(Operator.EQUAL_TO, AudioMatroskaCodecIdEnum.A_MPEG_L3);
		assert FILE != null;result=audioCodecIdSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		assert FILE != null;result=audioCodecIdSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testVideoResolutionSearch() {
		VideoResolutionSearch videoResolutionSearch = new VideoResolutionSearch(Operator.NOT_EQUAL_TO,
		        VideoResolutionSearchEnum.R_720);
		assert FILE != null;boolean result=videoResolutionSearch.isFileMatchingCriteria(FILE);
		Assert.assertFalse(result);

		videoResolutionSearch = new VideoResolutionSearch(Operator.EQUAL_TO, VideoResolutionSearchEnum.R_720);
		assert FILE != null;result=videoResolutionSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		videoResolutionSearch = new VideoResolutionSearch(Operator.GREATER_THAN, VideoResolutionSearchEnum.R_480);
		assert FILE != null;result=videoResolutionSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}

	@Test
	public void testVideoAspectRatioSearch() {
		VideoAspectRatioSearch videoAspectRatioSearch = new VideoAspectRatioSearch(Operator.NOT_EQUAL_TO,
		        AspectRatio.AS_2_20);
		assert FILE != null;
		boolean result=videoAspectRatioSearch.isFileMatchingCriteria(FILE);
		Assert.assertFalse(result);

		videoAspectRatioSearch = new VideoAspectRatioSearch(Operator.EQUAL_TO, AspectRatio.AS_2_20);
		assert FILE != null;result=videoAspectRatioSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);

		videoAspectRatioSearch = new VideoAspectRatioSearch(Operator.GREATER_THAN, AspectRatio.AS_1_66);
		assert FILE != null;result=videoAspectRatioSearch.isFileMatchingCriteria(FILE);
		Assert.assertTrue(result);
	}
}
