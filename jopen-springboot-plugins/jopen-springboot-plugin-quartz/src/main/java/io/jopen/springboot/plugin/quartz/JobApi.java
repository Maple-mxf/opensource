package io.jopen.springboot.plugin.quartz;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maxuefeng
 * @since 2020/1/31
 */
@RestController
@RequestMapping(value = "/jobApi")
public class JobApi {

    @Autowired
    private JobMonitors jobMonitors;

    @RequestMapping(value = "/listAllDistributeTask")
    public Object listAllDistributeTask() throws SchedulerException {
        return jobMonitors.distributeTaskList();
    }
}
