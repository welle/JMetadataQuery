package aka.jmetadataquery.main.types.constants.file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import aka.jmetadataquery.main.types.constants.video.VideoExtensionEnum;

/**
 * @author charlottew
 *
 */
public enum FileExtensionSearchEnum {

    /**
     * Matroska.
     */
    MKV(VideoExtensionEnum.MKV),

    /**
     * AVI.
     */
    AVI(VideoExtensionEnum.AVI),

    /**
     * MP4.
     */
    MP4(VideoExtensionEnum.MP4, VideoExtensionEnum.M4V),

    /**
     * MPEG.
     */
    MPEG(VideoExtensionEnum.M2V, VideoExtensionEnum.MP2, VideoExtensionEnum.MPEG, VideoExtensionEnum.MPG),

    /**
     * Windows Media Video.
     */
    WMV(VideoExtensionEnum.WMV);

    @NonNull
    private final List<VideoExtensionEnum> extensionList = new ArrayList<>();

    FileExtensionSearchEnum(@NonNull final VideoExtensionEnum @NonNull... languages) {
        this.extensionList.addAll(Arrays.asList(languages));
    }

    /**
     * Get the list of the corresponding extensions of the ENUM.
     *
     * @return the value of the ENUM
     */
    @NonNull
    public List<VideoExtensionEnum> getExtensionsList() {
        return this.extensionList;
    }

    /**
     * Get VideoExtensionSearchEnum corresponding to given string.
     *
     * @param extension
     * @return corresponding VideoExtensionSearchEnum
     */
    @Nullable
    public static FileExtensionSearchEnum getSearchEnum(@Nullable final String extension) {
        FileExtensionSearchEnum result = null;
        if (extension != null) {
            final String trimmedLanguage = extension.trim();
            if (trimmedLanguage.length() > 0) {
                for (final FileExtensionSearchEnum videoSearchEnum : FileExtensionSearchEnum.values()) {
                    final boolean containsSearchStr = videoSearchEnum.getExtensionsList().stream().filter(s -> s.getValue().equalsIgnoreCase(extension)).findFirst().isPresent();
                    if (containsSearchStr) {
                        result = videoSearchEnum;
                        // found, just break
                        break;
                    }
                }
            }
        }

        return result;
    }
}
