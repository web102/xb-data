package com.bobandata.iot.basedb.service.impl;

import com.bobandata.iot.basedb.repository.ProtocolRepository;
import com.bobandata.iot.basedb.service.ProtocolService;
import com.bobandata.iot.basedb.entity.Protocol;
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
 * @Date: Created in 16:44 2018/7/16.
 */
@Service
public class ProtocolServiceImpl extends BaseServiceImpl<Protocol, Integer> implements ProtocolService {

    @Autowired
    private ProtocolRepository protocolRepository;

    @Override
    public Page<Protocol> selectPageList(Pageable pageable) {
        return protocolRepository.selectPageList(pageable);
    }

    @Override
    public SimpleTree protocolTree() {
        SimpleTree treeHead =new SimpleTree(0,"规约",0,false);
        List<SimpleTree> childrenHead =new ArrayList<>();
        treeHead.setChildren(childrenHead);
        List<Protocol> protocols = this.findAll();
        for(Protocol protocol : protocols){
            SimpleTree tree =new SimpleTree(protocol.getProtocolId(),protocol.getProtocolName(),0,false);
            childrenHead.add(tree);
        }
        return treeHead;
    }

    @Override
    public List<Protocol> findByProtocolName(String protocolName) {
        return protocolRepository.findByProtocolName(protocolName);
    }

    @Override
    public List<Protocol> findSimilar(String protocolName) {
        List<Protocol> all = protocolRepository.findAll();

        List<Protocol> similar = new ArrayList<>();
        List<Protocol> identical = new ArrayList<>();
        List<Protocol> start = new ArrayList<>();
        List<Protocol> indexOf = new ArrayList<>();

        for(Protocol protocol : all){
            if(protocol.getProtocolName().equals(protocolName)){
                identical.add(protocol);
            }
            else if(protocol.getProtocolName().startsWith(protocolName)){
                start.add(protocol);
            }
            else if(protocol.getProtocolName().indexOf(protocolName)!=-1){
                indexOf.add(protocol);
            }
        }
        similar.addAll(identical);
        similar.addAll(start);
        similar.addAll(indexOf);
        return similar;
    }

}
