package com.bobandata.iot.basedb.service;

import com.bobandata.iot.basedb.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 11:49 2018/7/17.
 */
public interface UserService extends BaseService<User, Integer> {
    /**
     * 分页查询
     * @param pageable 分页参数
     * @return
     */
    Page<User> selectPageList(Pageable pageable);

    //查询姓名为pp，密码为123的用户，判断用户存不存在，存在返回true\不存在返回false
    User login(String username, String password);

    List<User> findByName(String username);
}
