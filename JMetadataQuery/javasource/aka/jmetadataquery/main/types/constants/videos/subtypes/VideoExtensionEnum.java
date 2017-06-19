package aka.jmetadataquery.main.types.constants.videos.subtypes;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Enum of available video extension.
 *
 * @author charlottew
 */
public enum VideoExtensionEnum {

    /**
     * Matroska.
     */
    MKV("mkv", "Matroska"),
    /**
     * AVI.
     */
    AVI("avi", "AVI"),
    /**
     * Windows Media Video.
     */
    WMV("wmv", "Windows Media Video"),
    /**
     * MP4.
     */
    MP4("mp4", "MPEG-4 Part 14 (MP4)"),
    /**
     * M4V.
     */
    M4V("m4v", "MPEG-4 Part 14 (MP4)"),
    /**
     * MPEG.
     */
    MPG("mpg", "MPEG"),
    /**
     * MPEG.
     */
    MP2("mp2", "MPEG"),
    /**
     * MPEG.
     */
    MPEG("mpeg", "MPEG"),
    /**
     * MPEG.
     */
    M2V("m2v", "MPEG");

    private @NonNull String extension;
    private @NonNull String description;
    private static @NonNull final List<@NonNull String> ALL_EXTENSIONS = new ArrayList<>();
    static {
        for (final @NonNull VideoExtensionEnum videoExtensionEnum : VideoExtensionEnum.values()) {
            ALL_EXTENSIONS.add(videoExtensionEnum.extension);
        }
    }

    VideoExtensionEnum(@NonNull final String extension, @NonNull final String description) {
        this.extension = extension;
        this.description = description;
    }

    /**
     * Get the value of the ENUM.
     *
     * @return the value of the ENUM
     */
    @NonNull
    public String getValue() {
        return this.extension;
    }

    /**
     * Get the description of the ENUM.
     *
     * @return the description of the ENUM
     */
    @NonNull
    public String getDescription() {
        return this.description;
    }

    /**
     * Get VideoExtensionEnum corresponding to given string.
     *
     * @param extensionStr
     * @return corresponding VideoExtensionEnum
     */
    @Nullable
    public VideoExtensionEnum geVideoExtensionEnum(@Nullable final String extensionStr) {
        VideoExtensionEnum result = null;
        if (extensionStr != null) {
            final String trimmedLanguage = extensionStr.trim();
            if (trimmedLanguage.length() > 0) {
                for (final VideoExtensionEnum videoExtensionEnum : VideoExtensionEnum.values()) {
                    if (videoExtensionEnum.getValue().equalsIgnoreCase(trimmedLanguage)) {
                        result = videoExtensionEnum;
                        // found, just break
                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Get all extensions.
     *
     * @return all available extensions
     */
    @NonNull
    public static List<@NonNull String> getAllExtensions() {
        return ALL_EXTENSIONS;
    }

}
