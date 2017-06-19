package aka.jmetadataquery.test;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.healthmarketscience.sqlbuilder.BinaryCondition.Op;

import aka.jmetadata.test.JMetaDataMenu_Test;
import aka.jmetadataquery.main.JMediaSearch;
import aka.jmetadataquery.main.types.SearchQuery;
import aka.jmetadataquery.main.types.constants.videos.VideoExtensionSearchEnum;
import aka.jmetadataquery.main.types.search.video.VideoExtensionSearch;

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
        VideoExtensionSearch videoExtensionSearch = new VideoExtensionSearch(Op.EQUAL_TO, VideoExtensionSearchEnum.AVI);
        query.addSearchCriteria(videoExtensionSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertFalse(result);

        query = new SearchQuery();
        videoExtensionSearch = new VideoExtensionSearch(Op.EQUAL_TO, VideoExtensionSearchEnum.MKV);
        query.addSearchCriteria(videoExtensionSearch);

        result = jMediaSearch.isFileMatchingCriteria(file, query);
        Assert.assertTrue(result);
    }
}
