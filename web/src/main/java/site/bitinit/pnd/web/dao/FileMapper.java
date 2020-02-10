package site.bitinit.pnd.web.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.bitinit.pnd.web.entity.File;

import java.util.Date;
import java.util.List;

/**
 * @author john
 * @date 2020-01-08
 */
@Mapper
public interface FileMapper {

    /**
     * 根据id获取文件
     * @param id id
     * @return file
     */
    File findById(Long id);

    /**
     * 通过parentId获取所有子文件
     * @param parentId parentId
     * @param sort 是否根据id排序
     * @return list
     */
    List<File> findByParentId(@Param("parentId") Long parentId, @Param("sort") boolean sort);

    /**
     * 通过parentId获取所有子文件，悲观锁
     * @param parentId parentId
     * @return list
     */
    List<File> findByParentIdForUpdate(@Param("parentId") Long parentId);

    /**
     * 保存文件
     * @param file file
     */
    void save(File file);

    /**
     * 更新文件：fileName或parentId
     * @param file file
     */
    void update(File file);

    /**
     * 批量更新parentId
     * @param ids ids
     * @param parentId parentId
     * @param updateTime updateTime
     */
    void updateParentId(@Param("ids") List<Long> ids,
                        @Param("parentId") Long parentId,
                        @Param("updateTime") Date updateTime);

    /**
     * 根据id批量删除file
     * @param ids ids
     */
    void deleteByIds(@Param("ids") List<Long> ids);

    /**
     * 获取文件信息
     * @return list
     */
    List<File> getAllFileType();
}
