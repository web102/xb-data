package com.bobandata.iot.basedb.service.impl;

import com.bobandata.iot.basedb.repository.ErtuRepository;
import com.bobandata.iot.basedb.repository.MeterRepository;
import com.bobandata.iot.basedb.service.ErtuService;
import com.bobandata.iot.basedb.entity.Ertu;
import com.bobandata.iot.basedb.entity.Meter;
import com.bobandata.iot.util.SimpleTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 15:37 2018/7/17.
 */
@Service
public class ErtuServicelmpl extends BaseServiceImpl<Ertu, Integer> implements ErtuService {

    @Autowired
    private ErtuRepository ertuRepository;

    @Autowired
    private MeterRepository meterRepository;


    @Override
    public Page<Ertu> selectPageList(Pageable pageable) {
        return ertuRepository.selectPageList(pageable);
    }

    @Override
    public List<Ertu> findByName(String ertuName) {
        return ertuRepository.findByErtuName(ertuName);
    }

    @Override
    public SimpleTree ertuTree() {
        SimpleTree tree = new SimpleTree(0,"tree",false);
        List<SimpleTree> children = new ArrayList<>();

        List<Ertu> all = ertuRepository.findAll();
        for(Ertu ertu : all){
            SimpleTree ertus = new SimpleTree(ertu.getErtuId(),ertu.getErtuName(),ertu.getProtocolId(),false);
            children.add(ertus);
        }
        tree.setChildren(children);
        return tree;
    }

    @Override
    public SimpleTree ertuMeterTree(Boolean idIsNull) {
        SimpleTree tree = new SimpleTree(0,"tree",false);
        List<SimpleTree> children = new ArrayList<>();

        List<Ertu> ertuAll = ertuRepository.findAll();
        List<Meter> meterAll = meterRepository.findAll();
        for(Ertu ertu : ertuAll){
            Integer ertuId = ertu.getErtuId();
            if(idIsNull) {
                ertuId = 0;
            }
            SimpleTree ertus = new SimpleTree(ertuId,ertu.getErtuName(),ertu.getProtocolId(),false);
            List<SimpleTree> ertuChildren = new ArrayList<>();
            for (Meter meter : meterAll) {
                if (meter.getErtuId() == ertu.getErtuId()) {
                    SimpleTree meters = new SimpleTree(meter.getMeterId(), meter.getMeterName(),Integer.parseInt(meter.getMeterAddr()), false);
                    ertuChildren.add(meters);
                }
            }
            ertus.setChildren(ertuChildren);
            children.add(ertus);
        }
        tree.setChildren(children);
        return tree;
    }

    @Override
    public List<Ertu> findSimilar(String ertuName) {
        List<Ertu> all = ertuRepository.findAll();

        List<Ertu> similar = new ArrayList<>();
        List<Ertu> identical = new ArrayList<>();
        List<Ertu> start = new ArrayList<>();
        List<Ertu> indexOf = new ArrayList<>();

        for(Ertu ertu : all){
            if(ertu.getErtuName().equals(ertuName)){
                identical.add(ertu);
            }
            else if(ertu.getErtuName().startsWith(ertuName)){
                start.add(ertu);
            }
            else if(ertu.getErtuName().indexOf(ertuName)!=-1){
                indexOf.add(ertu);
            }
        }
        similar.addAll(identical);
        similar.addAll(start);
        similar.addAll(indexOf);
        return similar;
    }

    @Override
    public Ertu findByAddress(String address) {
        return ertuRepository.findByAddress(address);
    }


}