package com.bobandata.iot.basedb.controller;

import com.bobandata.iot.basedb.service.TaskItemService;
import com.bobandata.iot.basedb.entity.TaskItem;
import com.bobandata.iot.util.Constant;
import com.bobandata.iot.util.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("taskItem")
public class TaskItemController {
    @Resource
    private TaskItemService taskItemService;

    @RequestMapping("/findAll")
    public Result findAll() {
        try{
            List<TaskItem> protocols = taskItemService.findAll();
            return  new Result(Constant.MethodResult.SUCCESS.getMethodResult(), protocols);
        }catch (Exception e){
            return new Result(Constant.ErrorCode.EXCEPTION.getErrorCode(), Constant.MethodResult.FAIL.getMethodResult(), Constant.ResultType.B00.getResultType(), false);
        }
    }
}
