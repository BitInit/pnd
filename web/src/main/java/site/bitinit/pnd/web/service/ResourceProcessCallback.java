package site.bitinit.pnd.web.service;

import site.bitinit.pnd.web.model.PndResourceState;

/**
 * @author: john
 * @date: 2019/4/21
 */
public interface ResourceProcessCallback {

    /**
     * 资源片段处理前回调
     * @param state
     */
    default void onStart(PndResourceState state){
    }

    /**
     * 资源片段上传完成回调
     * @param state
     */
    default void onComplete(PndResourceState state){
    }

    /**
     * 整个文件上传成功时回调
     * @param state
     */
    default void onSuccess(PndResourceState state){
    }

    /**
     * 处理过程中出现错误
     * @param e
     */
    default void onError(Exception e){
    }
}
