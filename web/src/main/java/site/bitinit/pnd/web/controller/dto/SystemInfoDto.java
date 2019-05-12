package site.bitinit.pnd.web.controller.dto;

/**
 * @author: john
 * @date: 2019/5/6
 */
public class SystemInfoDto {

    private Long diskCapacity;
    private Long diskFree;
    private Long fileNum;
    private Long garbageFileNum;
    private Long folderNum;
    private Long videoNum;
    private Long audioNum;

    public Long getDiskCapacity() {
        return diskCapacity;
    }

    public void setDiskCapacity(Long diskCapacity) {
        diskCapacity = ifNullGetZero(diskCapacity);
        this.diskCapacity = diskCapacity;
    }

    public Long getDiskFree() {
        return diskFree;
    }

    public void setDiskFree(Long diskFree) {
        diskFree = ifNullGetZero(diskFree);
        this.diskFree = diskFree;
    }

    public Long getFileNum() {
        return fileNum;
    }

    public void setFileNum(Long fileNum) {
        fileNum = ifNullGetZero(fileNum);
        this.fileNum = fileNum;
    }

    public Long getGarbageFileNum() {
        return garbageFileNum;
    }

    public void setGarbageFileNum(Long garbageFileNum) {
        garbageFileNum = ifNullGetZero(garbageFileNum);
        this.garbageFileNum = garbageFileNum;
    }

    public Long getFolderNum() {
        return folderNum;
    }

    public void setFolderNum(Long folderNum) {
        folderNum = ifNullGetZero(folderNum);
        this.folderNum = folderNum;
    }

    public Long getVideoNum() {
        return videoNum;
    }

    public void setVideoNum(Long videoNum) {
        videoNum = ifNullGetZero(videoNum);
        this.videoNum = videoNum;
    }

    public Long getAudioNum() {
        return audioNum;
    }

    public void setAudioNum(Long audioNum) {
        audioNum = ifNullGetZero(audioNum);
        this.audioNum = audioNum;
    }

    private Long ifNullGetZero(Long val){
        return val == null? 0L: val;
    }
}
