package site.bitinit.pnd.web.model;

import site.bitinit.pnd.common.util.Assert;
import site.bitinit.pnd.web.config.SystemConstants;

import java.io.*;

/**
 * @author: john
 * @date: 2019/4/20
 */
public class PndResourceState extends PndResource {

    public static final String PAUSE = "pause";
    public static final String RESUME = "resume";

    private long finishedUploadBytes = 0;
    private boolean paused = false;
    private String fileName;
    private long parentId;
    private File file;

    /**
     * 文件输出流
     */
    private OutputStream outputStream;

    public PndResourceState() {
    }

    private PndResourceState(PndResourceStateBuilder builder){
        fillData(builder);
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

    public synchronized boolean isPaused() {
        return paused;
    }

    public synchronized void setPaused(boolean paused) {
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

    private void fillData(PndResourceStateBuilder builder){
        setId(builder.id);
        setLink(builder.link);
        setMd5(builder.md5);
        setGmtModified(builder.gmtModified);
        setGmtCreate(builder.gmtCreate);
        setPath(builder.path);
        setSize(builder.size);
        setStatus(builder.status.name());
        setUuid(builder.uuid);

        this.fileName = builder.fileName;
        this.parentId = builder.parentId;

        this.file = builder.file;
        Assert.notNull(file, "file can't be null");
        try {
            this.outputStream = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static PndResourceStateBuilder builder() {
        return new PndResourceStateBuilder();
    }

    public static class PndResourceStateBuilder{

        Long id;
        Long size;
        String path;
        String uuid;
        String md5;
        SystemConstants.ResourceState status;
        Long gmtCreate;
        Long gmtModified;
        Integer link;

        String fileName;
        long parentId;
        File file;

        public PndResourceStateBuilder pndResource(PndResource resource){
            this.id = resource.getId();
            this.size = resource.getSize();
            this.path = resource.getPath();
            this.uuid = resource.getUuid();
            this.md5 = resource.getMd5();
            this.status = SystemConstants.ResourceState.valueOf(resource.getStatus());
            this.gmtCreate = resource.getGmtCreate();
            this.gmtModified = resource.getGmtModified();
            this.link = resource.getLink();
            return this;
        }

        public PndResourceStateBuilder id(Long id){
            this.id = id;
            return this;
        }

        public PndResourceStateBuilder size(Long size){
            this.size = size;
            return this;
        }

        public PndResourceStateBuilder path(String path){
            this.path = path;
            return this;
        }

        public PndResourceStateBuilder uuid(String uuid){
            this.uuid = uuid;
            return this;
        }

        public PndResourceStateBuilder md5(String md5){
            this.md5 = md5;
            return this;
        }

        public PndResourceStateBuilder status(SystemConstants.ResourceState status){
            this.status = status;
            return this;
        }

        public PndResourceStateBuilder gmtCreate(Long gmtCreate){
            this.gmtCreate = gmtCreate;
            return this;
        }

        public  PndResourceStateBuilder gmtModified(Long gmtModified){
            this.gmtModified = gmtModified;
            return this;
        }

        public PndResourceStateBuilder link(Integer link){
            this.link = link;
            return this;
        }

        public PndResourceStateBuilder fileName(String fileName){
            this.fileName = fileName;
            return this;
        }

        public PndResourceStateBuilder parentId(Long parentId){
            this.parentId = parentId;
            return this;
        }

        public PndResourceStateBuilder file(File file){
            this.file = file;
            return this;
        }

        public PndResourceState build() throws FileNotFoundException {
            return new PndResourceState(this);
        }

        public PndResourceState build(PndResourceState state){
            state.fillData(this);
            return state;
        }
    }
}
