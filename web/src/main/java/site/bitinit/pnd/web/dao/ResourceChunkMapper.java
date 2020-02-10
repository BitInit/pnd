package site.bitinit.pnd.web.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.bitinit.pnd.web.entity.ResourceChunk;

/**
 * @author john
 * @date 2020-01-27
 */
@Mapper
public interface ResourceChunkMapper {

    /**
     * 根据identifier和chunkNumber查找块
     * @param identifier identifier
     * @param chunkNumber 当前块编号
     * @return ResourceChunk
     */
    ResourceChunk findByIdentifierAndChunkNumber(@Param("identifier") String identifier,
                                                 @Param("chunkNumber") Integer chunkNumber);

    /**
     * 保存块
     * @param resourceChunk chunk
     * @return id
     */
    Long save(ResourceChunk resourceChunk);

    /**
     * 删除文件块
     * @param identifier identifier
     */
    void deleteChunk(@Param("identifier") String identifier);
}
