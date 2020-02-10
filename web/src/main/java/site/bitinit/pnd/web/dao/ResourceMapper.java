package site.bitinit.pnd.web.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.bitinit.pnd.web.entity.Resource;

/**
 * @author john
 * @date 2020-01-11
 */
@Mapper
public interface ResourceMapper {

    /**
     * 保存resource
     * @param resource resource
     * @return id
     */
    Long save(Resource resource);

    /**
     * 根据id获取Resource
     * @param id id
     * @return Resource
     */
    Resource findById(Long id);

    /**
     * 根据id获取Resource，并加锁
     * @param id id
     * @return Resource
     */
    Resource findByIdForUpdate(Long id);

    /**
     * 根据id删除resource
     * @param id id
     */
    void delete(Long id);

    /**
     * 更新link
     * @param id id
     * @param link link
     */
    void updateLink(@Param("id") Long id, @Param("link") Integer link);
}
