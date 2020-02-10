package site.bitinit.pnd.web.config;

/**
 * @author john
 * @date 2020-01-11
 */
public enum FileType {
    /**
     * 文件类型
     */
    DEFAULT,
    FOLDER,
    VIDEO,
    AUDIO,
    PDF,
    COMPRESS_FILE,
    PICTURE,
    DOC,
    PPT,
    TXT,
    TORRENT,
    WEB,
    CODE
    ;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
