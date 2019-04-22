package site.bitinit.pnd.web.model;

import site.bitinit.pnd.common.util.Assert;

import java.io.*;

/**
 * @author: john
 * @date: 2019/4/20
 */
public class PndResourceState extends PndResource {

    private long finishedUploadBytes = 0;
    private volatile boolean paused = false;
    private String fileName;
    private long parentId;
    private File file;

    /**
     * 文件输出流
     */
    private OutputStream outputStream;

    public PndResourceState(PndResource resource, String fileName, long parentId, File file) throws FileNotFoundException {
        setId(resource.getId());
        setLink(resource.getLink());
        setFingerPrint(resource.getFingerPrint());
        setGmtCreate(resource.getGmtCreate());
        setGmtModified(resource.getGmtModified());
        setPath(resource.getPath());
        setSize(resource.getSize());
        setStatus(resource.getStatus());
        setUuid(resource.getUuid());
        this.fileName = fileName;
        this.parentId = parentId;

        Assert.notNull(file, "file can't be null");
        this.file = file;
        this.outputStream = new FileOutputStream(file, true);
    }

    public synchronized long getFinishedUploadBytes() {
        return finishedUploadBytes;
    }

    public synchronized void setFinishedUploadBytes(long finishedUploadBytes) {
        this.finishedUploadBytes = finishedUploadBytes;
    }

    public synchronized void addFinishedUploadBytes(long bytes){
        this.finishedUploadBytes += bytes;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
