package com.bobandata.iot.basedb.controller;

import com.bobandata.iot.basedb.service.ErtuService;
import com.bobandata.iot.basedb.entity.Ertu;
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
 * @Description: 终端对外接口
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 15:31 2018/7/17.
 */
@RestController
@RequestMapping("/ertu")
public class ErtuController {

    private static final Logger logger = LoggerFactory.getLogger(Ertu.class);

    @Autowired
    private ErtuService ertuService;

    //主键查询信息，根据id查询
    @RequestMapping("/findOne")
    public Result findOne(Integer id) {
        return deleteOrFind(id, Constant.MethodType.FIND.getMethodType());
    }

    //根据名称查询
    @RequestMapping("findByName")
    public Result findByName(String ertuName) {
        try {
            List<Ertu> ertus = ertuService.findByName(ertuName);
            return new Result(Constant.MethodResult.SUCCESS.getMethodResult(), ertus);
        } catch (Exception e) {
            return new Result(Constant.ErrorCode.EXCEPTION.getErrorCode(), Constant.MethodResult.FAIL.getMethodResult(), Constant.ResultType.B00.getResultType(), false);
        }
    }

    @RequestMapping("findByAddress")
    public Result findByAddress(String address){
        try {
            Ertu ertu = ertuService.findByAddress(address);
            return new Result(Constant.MethodResult.SUCCESS.getMethodResult(), ertu);
        } catch (Exception e) {
            return new Result(Constant.ErrorCode.EXCEPTION.getErrorCode(), Constant.MethodResult.FAIL.getMethodResult(), Constant.ResultType.B00.getResultType(), false);
        }
    }

    //查询全部信息
    @RequestMapping("/findAll")

    public Result findAll() {
        try {
            List<Ertu> all = ertuService.findAll();
            return new Result(Constant.MethodResult.SUCCESS.getMethodResult(), all);
        } catch (Exception e) {
            return new Result(Constant.ErrorCode.EXCEPTION.getErrorCode(), Constant.MethodResult.FAIL.getMethodResult(), Constant.ResultType.B00.getResultType(), false);
        }
    }

    //模糊查询
    @RequestMapping("findSimilar")

    public Result findSimilar(String ertuName) {
        try {
            List<Ertu> similar = ertuService.findSimilar(ertuName);
            return new Result(Constant.MethodResult.SUCCESS.getMethodResult(), similar);
        } catch (Exception e) {
            return new Result(Constant.ErrorCode.EXCEPTION.getErrorCode(), Constant.MethodResult.FAIL.getMethodResult(), Constant.ResultType.B00.getResultType(), false);
        }
    }

    //终端电表树接口
    @RequestMapping("/ertuTree")

    public Result ertuTree() {
        try {
            SimpleTree tree = ertuService.ertuTree();
            return new Result(Constant.MethodResult.SUCCESS.getMethodResult(), tree);
        } catch (Exception e) {
            return new Result(Constant.ErrorCode.EXCEPTION.getErrorCode(), Constant.MethodResult.FAIL.getMethodResult(), Constant.ResultType.B00.getResultType(), false);
        }
    }

    //终端电表树接口
    @RequestMapping("/ertuMeterTree")

    public Result ertuMeterTree(Boolean idIsNull) {
        try {
            SimpleTree tree = ertuService.ertuMeterTree(idIsNull);
            return new Result(Constant.MethodResult.SUCCESS.getMethodResult(), tree);
        } catch (Exception e) {
            return new Result(Constant.ErrorCode.EXCEPTION.getErrorCode(), Constant.MethodResult.FAIL.getMethodResult(), Constant.ResultType.B00.getResultType(), false);
        }
    }


    //保存记录：不带ID是新增，带ID是更新
    @RequestMapping(value = "/save")

    public Result add(@RequestBody Ertu ertu) {
        try {
            Ertu p = ertuService.save(ertu);
            if (p != null) {
                return new Result(Constant.MethodResult.SUCCESS.getMethodResult(), true);
            } else {
                return new Result(Constant.MethodResult.FAIL.getMethodResult(), false);
            }
        } catch (Exception e) {
            return new Result(Constant.ErrorCode.EXCEPTION.getErrorCode(), Constant.MethodResult.FAIL.getMethodResult(), Constant.ResultType.B00.getResultType(), false);
        }
    }

    //终端数据分页
    @RequestMapping("/selectPageList")
    public Result selectPageList(int page, int size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "ertuId");
        Page<Ertu> ertus = ertuService.selectPageList(pageable);
        return new Result(Constant.MethodResult.SUCCESS.getMethodResult(), ertus);
    }

    //主键删除记录
    @RequestMapping(value = "/delete")

    public Result deleterAcquired(Integer id) {
        return deleteOrFind(id, Constant.MethodType.DEL.getMethodType());
    }

    /**
     * 根据ID执行删除或者查找操作
     *
     * @param id         主键
     * @param methodType 方法类型
     * @return
     */
    public Result deleteOrFind(Integer id, String methodType) {
        try {
            if (methodType.equals(Constant.MethodType.DEL.getMethodType())) {
                ertuService.delete(id);
                return new Result(Constant.MethodResult.SUCCESS.getMethodResult(), true);
            } else {
                Ertu ertu = ertuService.findOne(id);
                return new Result(Constant.MethodResult.SUCCESS.getMethodResult(), ertu);
            }
        } catch (Exception e) {
            logger.error(e.getMessage().toString());
            return new Result(Constant.ErrorCode.EXCEPTION.getErrorCode(), Constant.MethodResult.FAIL.getMethodResult(), Constant.ResultType.B00.getResultType(), false);
        }
    }
}