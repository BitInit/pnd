package site.bitinit.pnd.web.controller.dto;

import site.bitinit.pnd.web.model.PndFile;

import java.util.Objects;

/**
 * @author: john
 * @date: 2019/4/4
 */
public class FileDetailDto {

    private Long id;
    private String name;
    private Long parentId;
    private String type;
    private Long gmtCreate;
    private Long gmtModified;
    private Long resourceId;
    private Integer size;

    public FileDetailDto() {
    }

    public FileDetailDto(PndFile file, Integer size) {
        if (Objects.isNull(file) || Objects.isNull(size)){
            return;
        }
        this.id = file.getId();
        this.name = file.getName();
        this.parentId = file.getParentId();
        this.type = file.getType();
        this.gmtCreate = file.getGmtCreate();
        this.gmtModified = file.getGmtModified();
        this.resourceId = file.getResourceId();
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "FileDetailDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", type='" + type + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", resourceId=" + resourceId +
                ", size=" + size +
                '}';
    }
}
