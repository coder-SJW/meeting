
package com.senior.service.component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.senior.domain.bo.request.SysLogCreateBoRequest;
import com.senior.service.SysLogService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author senior
 */
@Slf4j
@Component
public class SysLogAppender {
    private static final int MAX_QUEUE_SIZE = 1000;
    private static final int BATCH_INSERT_SIZE = 100;
    private static final int INSERT_THREAD = 1;
    private final LinkedBlockingQueue<SysLogCreateBoRequest> insertQueue = new LinkedBlockingQueue<>(
            MAX_QUEUE_SIZE);
    private ExecutorService executorService;
    @Resource
    private SysLogService sysLogService;

    private volatile boolean taskRunning = true;

    @PostConstruct
    public void init() {
        executorService = new ThreadPoolExecutor(INSERT_THREAD, INSERT_THREAD, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new BasicThreadFactory.Builder().daemon(true).namingPattern("sys-log-insert-%d").build());
        executorService.submit(() -> {
            List<SysLogCreateBoRequest> dataList = Lists.newArrayList();
            while (taskRunning) {
                try {
                    int count = 0;
                    while (count < BATCH_INSERT_SIZE) {
                        SysLogCreateBoRequest targetData = insertQueue.take();
                        dataList.add(targetData);
                        count++;
                        if (insertQueue.isEmpty()) {
                            break;
                        }
                    }
                    sysLogService.batchCreate(dataList);
                } catch (Exception e) {
                    dataList.forEach(data -> log.error("批量插入日志失败, data:{}, error:{}", data, e.getMessage()));
                } finally {
                    dataList.clear();
                }
            }
        });
    }

    @PreDestroy
    public void destroy() {
        executorService.shutdown();
        taskRunning = false;
        List<SysLogCreateBoRequest> dataList = Lists.newArrayList();
        while (!insertQueue.isEmpty()) {
            SysLogCreateBoRequest targetData = insertQueue.poll();
            if (targetData != null) {
                dataList.add(targetData);
            }
        }
        if (!CollectionUtils.isEmpty(dataList)) {
            sysLogService.batchCreate(dataList);
        }
    }

    /**
     * 插入
     *
     * @param request
     */
    public void append(SysLogCreateBoRequest request) {
        if (taskRunning) {
            try {
                insertQueue.put(request);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        } else {
            sysLogService.create(request);
        }
    }
}
