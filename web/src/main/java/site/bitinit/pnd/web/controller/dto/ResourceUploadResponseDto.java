package site.bitinit.pnd.web.controller.dto;

/**
 * @author: john
 * @date: 2019/4/21
 */
public class ResourceUploadResponseDto {
    /**
     * 上传成功
     */
    public static final int SUCCESS = 0;

    /**
     * 上传已经暂停
     */
    public static final int PAUSED = 1;

    /**
     * 上传文件错误
     */
    public static final int FAILED = -1;

    private int code;
    private long completeBytes;

    public ResourceUploadResponseDto() {
    }

    public ResourceUploadResponseDto(int code) {
        this.code = code;
    }

    public ResourceUploadResponseDto(int code, long completeBytes) {
        this.code = code;
        this.completeBytes = completeBytes;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getCompleteBytes() {
        return completeBytes;
    }

    public void setCompleteBytes(long completeBytes) {
        this.completeBytes = completeBytes;
    }

    public static ResourceUploadResponseDto success(long completeBytes){
        return new ResourceUploadResponseDto(SUCCESS, completeBytes);
    }

    public static ResourceUploadResponseDto paused(long completeBytes){
        return new ResourceUploadResponseDto(PAUSED, completeBytes);
    }

    public static ResourceUploadResponseDto failed(){
        return new ResourceUploadResponseDto(FAILED);
    }
}
