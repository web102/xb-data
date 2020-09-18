package com.bobandata.iot.basedb.repository;

import com.bobandata.iot.basedb.entity.Ertu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 15:38 2018/7/17.
 */
@Repository
public interface ErtuRepository extends BaseRepository<Ertu, Integer> {

    @Query("SELECT e FROM Ertu e")
    Page<Ertu> selectPageList(Pageable pageable);

    List<Ertu> findByErtuName(String ertuName);

    Ertu findByAddress(String address);

}