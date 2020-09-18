package com.bobandata.iot.basedb.service;

import com.bobandata.iot.basedb.entity.TaskItem;

import java.util.List;

public interface TaskItemService {
    List<TaskItem> findAll();
}
