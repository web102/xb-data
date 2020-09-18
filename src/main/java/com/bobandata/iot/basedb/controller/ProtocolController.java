package com.bobandata.iot.basedb.controller;

import com.bobandata.iot.basedb.service.ProtocolService;
import com.bobandata.iot.basedb.entity.Protocol;
import com.bobandata.iot.util.Constant;
import com.bobandata.iot.util.Result;
import com.bobandata.iot.util.SimpleTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 9:36 2018/7/17.
 */
@RestController
@RequestMapping("/protocol")
public class ProtocolController {

    private static final Logger logger = LoggerFactory.getLogger(ProtocolController.class);

    @Autowired
    private ProtocolService protocolService;


    @RequestMapping("/selectPageList")
    public Result selectPageList(int page, int size){
        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "protocolId");
        Page<Protocol> protocols = protocolService.selectPageList(pageable);
        return new Result(Constant.MethodResult.SUCCESS.getMethodResult(), protocols);
    }

    //主键查询信息
    @RequestMapping("/findOne")
    public Result findOne(Integer id) {
        return deleteOrFind(id, Constant.MethodType.FIND.getMethodType());
    }
    @RequestMapping("/findByProtocolName")
    public Result findOneByProtocolName(String protocolName){
        try{
            List<Protocol> protocols = protocolService.findByProtocolName(protocolName);
            return  new Result(Constant.MethodResult.SUCCESS.getMethodResult(), protocols);
        }catch (Exception e){
            return new Result(Constant.ErrorCode.EXCEPTION.getErrorCode(), Constant.MethodResult.FAIL.getMethodResult(), Constant.ResultType.B00.getResultType(), false);
        }
    }
    @RequestMapping("/findAll")
    public Result findAll() {
        try{
            List<Protocol> protocols = protocolService.findAll();
            return  new Result(Constant.MethodResult.SUCCESS.getMethodResult(), protocols);
        }catch (Exception e){
            return new Result(Constant.ErrorCode.EXCEPTION.getErrorCode(), Constant.MethodResult.FAIL.getMethodResult(), Constant.ResultType.B00.getResultType(), false);
        }
    }
    //保存记录：不带ID是新增，带ID是更新
    @RequestMapping(value = "/save")
    public Result addProtocol(@RequestBody Protocol protocol) {
        try {
            Protocol p = protocolService.save(protocol);
            if (p != null) {
                return new Result(Constant.MethodResult.SUCCESS.getMethodResult(), true);
            } else {
                return new Result(Constant.MethodResult.FAIL.getMethodResult(), false);
            }
        } catch (Exception e) {
            return new Result(Constant.ErrorCode.EXCEPTION.getErrorCode(), Constant.MethodResult.FAIL.getMethodResult(), Constant.ResultType.B00.getResultType(), false);
        }
    }

    //主键删除记录
    @RequestMapping(value = "/delete")
    public Result deleterProtocol(Integer id) {
        return deleteOrFind(id, Constant.MethodType.DEL.getMethodType());
    }

    /**
     * 根据ID执行删除或者查找操作
     * @param id 主键
     * @param methodType 方法类型
     * @return
     */
    public Result deleteOrFind(Integer id, String methodType){
        try {
            if(methodType.equals(Constant.MethodType.DEL.getMethodType())){
                protocolService.delete(id);
                return new Result(Constant.MethodResult.SUCCESS.getMethodResult(), true);
            }else{
                Protocol protocol = protocolService.findOne(id);
                return new Result(Constant.MethodResult.SUCCESS.getMethodResult(), protocol);
            }
        } catch (Exception e) {
            logger.error(e.getMessage().toString());
            return new Result(Constant.ErrorCode.EXCEPTION.getErrorCode(), Constant.MethodResult.FAIL.getMethodResult(), Constant.ResultType.B00.getResultType(), false);
        }
    }

    @RequestMapping("/protocolTree")
    public Result protocolTree(){
        try {
            SimpleTree tree=protocolService.protocolTree();
            return  new Result(Constant.MethodResult.SUCCESS.getMethodResult(), tree);
        }catch (Exception e){
            return new Result(Constant.ErrorCode.EXCEPTION.getErrorCode(), Constant.MethodResult.FAIL.getMethodResult(), Constant.ResultType.B00.getResultType(), false);
        }
    }

    @RequestMapping("/findSimilar")
    public Result findSimilar(String protocolName){
        try{
            List<Protocol> protocols = protocolService.findSimilar(protocolName);
            return  new Result(Constant.MethodResult.SUCCESS.getMethodResult(), protocols);
        }catch (Exception e){
            return new Result(Constant.ErrorCode.EXCEPTION.getErrorCode(), Constant.MethodResult.FAIL.getMethodResult(), Constant.ResultType.B00.getResultType(), false);
        }
    }
}
