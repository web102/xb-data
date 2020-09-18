package com.bobandata.iot.basedb.service.impl;

import com.bobandata.iot.basedb.repository.NetworkRepository;
import com.bobandata.iot.basedb.service.NetworkService;
import com.bobandata.iot.basedb.entity.Network;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 15:43 2018/7/17.
 */
@Service
public class NetworkServicelmpl extends BaseServiceImpl<Network, Integer> implements NetworkService {

    @Resource
    private NetworkRepository networkRepository;


    @Override
    public Page<Network> selectPageList(Pageable pageable) {
        return networkRepository.selectPageList(pageable);
    }

    @Override
    public List<Network> findNetworkByErtuId(Integer ertuId) {
        List<Network> networks = new ArrayList<Network>();

        List<Network> nerworkAll = networkRepository.findAll();
        for(Network network : nerworkAll){
            if(network.getErtuId()==ertuId){
                networks.add(network);
            }
        }
        return networks;
    }
}