package com.bobandata.iot.basedb.service.impl;

import com.bobandata.iot.basedb.repository.TaskItemRepository;
import com.bobandata.iot.basedb.service.TaskItemService;
import com.bobandata.iot.basedb.entity.TaskItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TaskItemImpl implements TaskItemService {
    @Resource
    private TaskItemRepository taskItemRepository;

    @Override
    public List<TaskItem> findAll() {
        return taskItemRepository.findAll();
    }
}
