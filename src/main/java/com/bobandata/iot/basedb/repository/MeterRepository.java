package com.bobandata.iot.basedb.repository;

import com.bobandata.iot.basedb.entity.Meter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 15:39 2018/7/17.
 */
@Repository
public interface MeterRepository extends BaseRepository<Meter, Integer> {

    @Query("SELECT m FROM Meter m")
    Page<Meter> selectPageList(Pageable pageable);

    @Query("SELECT m.meterId from Meter m group by m.meterId")
    List<Integer> getsMeterId();

}
