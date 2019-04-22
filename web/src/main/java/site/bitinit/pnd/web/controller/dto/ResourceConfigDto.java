package site.bitinit.pnd.web.controller.dto;

/**
 * @author: john
 * @date: 2019/4/21
 */
public class ResourceConfigDto {

    private String clientId;
    private Integer maxConcurrentUploadNumbers;
    private Long chunkByteSize;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getMaxConcurrentUploadNumbers() {
        return maxConcurrentUploadNumbers;
    }

    public void setMaxConcurrentUploadNumbers(Integer maxConcurrentUploadNumbers) {
        this.maxConcurrentUploadNumbers = maxConcurrentUploadNumbers;
    }

    public Long getChunkByteSize() {
        return chunkByteSize;
    }

    public void setChunkByteSize(Long chunkByteSize) {
        this.chunkByteSize = chunkByteSize;
    }
}
