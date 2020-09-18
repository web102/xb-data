package com.bobandata.iot.basedb.service;

import com.bobandata.iot.basedb.entity.Ertu;
import com.bobandata.iot.util.SimpleTree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 15:34 2018/7/17.
 */
public interface ErtuService extends BaseService<Ertu, Integer> {

    /**
     * 分页查询
     * @param pageable 分页参数
     * @return
     */
    Page<Ertu> selectPageList(Pageable pageable);

    List<Ertu> findByName(String ertuName);

    List<Ertu> findSimilar(String ertuName);

    Ertu findByAddress(String address);

    SimpleTree ertuTree();

    SimpleTree ertuMeterTree(Boolean idIsNull);
}
