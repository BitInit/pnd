package site.bitinit.pnd.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import site.bitinit.pnd.web.model.PndResourceState;

import java.io.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author: john
 * @date: 2019/4/20
 */
@Service
public class PersistResourceService {

    private static final Logger logger = LoggerFactory.getLogger(PersistResourceService.class);

    private ScheduledThreadPoolExecutor resourcePersistPools = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(10);

    public void process(PndResourceState state, InputStream inputStream, ResourceProcessCallback processCallback){
        processCallback.onStart(state);
        ResourceChunkWriter writer = new ResourceChunkWriter(state, inputStream, processCallback);
        resourcePersistPools.submit(writer);
    }

    class ResourceChunkWriter implements Callable<Void>{

        private PndResourceState state;
        private ResourceProcessCallback processCallback;
        private InputStream inputStream;

        public ResourceChunkWriter(PndResourceState state, InputStream inputStream, ResourceProcessCallback processCallback) {
            this.state = state;
            this.processCallback = processCallback;
            this.inputStream = inputStream;
        }

        @Override
        public Void call() throws Exception {
            if (state.isPaused()){
                processCallback.onComplete(state);
                return null;
            }

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = inputStream.read(buffer);
            if (bytesRead != -1){
                state.getOutputStream().write(buffer);
                state.addFinishedUploadBytes(bytesRead);

                resourcePersistPools.submit(this);
            } else {
                processCallback.onComplete(state);
            }
            return null;
        }
    }

    private static final int BUFFER_SIZE = 100;
}
