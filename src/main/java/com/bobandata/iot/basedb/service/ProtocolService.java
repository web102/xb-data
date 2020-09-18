package com.bobandata.iot.basedb.service;

import com.bobandata.iot.basedb.entity.Protocol;
import com.bobandata.iot.util.SimpleTree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 16:17 2018/7/16.
 */
public interface ProtocolService extends BaseService<Protocol, Integer>{

    /**
     * 分页查询
     * @param pageable 分页参数
     * @return
     */
    Page<Protocol> selectPageList(Pageable pageable);

    SimpleTree protocolTree();

    List<Protocol> findByProtocolName(String protocolName);

    List<Protocol> findSimilar(String protocolName);

}
