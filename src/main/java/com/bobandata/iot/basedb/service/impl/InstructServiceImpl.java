package com.bobandata.iot.basedb.service.impl;

import com.bobandata.iot.basedb.repository.InstructRepository;
import com.bobandata.iot.basedb.service.InstructService;
import com.bobandata.iot.basedb.service.Instruct_protocol_setService;
import com.bobandata.iot.basedb.entity.Instruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author: lizhipeng
 * @Description:
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 10:53 2018/7/17.
 */
@Service
public class InstructServiceImpl extends BaseServiceImpl<Instruct, Integer> implements InstructService {

    @Autowired
    private InstructRepository instructRepository;
    @Autowired
    private Instruct_protocol_setService ipsService;

    @Override
    public Page<Instruct> selectPageList(Pageable pageable) {
        return instructRepository.selectPageList(pageable);
    }

    /**
     * 通过类型给指令分类
     * @return map<类型名，list<指令></>></>
     */
    @Override
    public Map<String,List<Instruct>> instructSortByType(List<Instruct> instructs){
        Map<String,List<Instruct>> map = new HashMap<>();

        List<Instruct> instructTypes = instructRepository.findAllInstructTypes();
        for(Instruct instructType:instructTypes) {
            if(instructType.getInstructType().equals("")){
                map.put("null",new ArrayList<>());
            }
            map.put(instructType.getInstructType(), new ArrayList<>());
        }
        for(Instruct instruct:instructs) {
            map.get(instruct.getInstructType()).add(instruct);
        }
        return map;
    }

    /**
     *指令模糊查询
     * @param instructName  指令表中全部指令
     * @param modelId    模板id=？的全部指令
     * @param protocolId 规约id=? 的全部指令
     * @return list<指令></>
     */
    @Override
    public List<Instruct> findSimilar(String instructName, Integer modelId,Integer protocolId) {
        List<Instruct> all = new ArrayList<>();
        if(modelId==0&&protocolId==0){
            all = instructRepository.findAll();
        }
        else if(modelId==0){
            all = ipsService.protocolInstruct(protocolId);
        }
        List<Instruct> similar = new ArrayList<>();
        List<Instruct> identical = new ArrayList<>();
        List<Instruct> start = new ArrayList<>();
        List<Instruct> indexOf = new ArrayList<>();
        for(Instruct instruct : all){
            if(instruct.getInstructName().equals(instructName)){
                identical.add(instruct);
            }
            else if(instruct.getInstructName().startsWith(instructName)){
                start.add(instruct);
            }
            else if(instruct.getInstructName().indexOf(instructName)!=-1){
                indexOf.add(instruct);
            }
        }
        similar.addAll(identical);
        similar.addAll(start);
        similar.addAll(indexOf);
        return similar;
    }

    @Override
    public List<Instruct> findByInstructName(String instructName) {
        return instructRepository.findByInstructName(instructName);
    }
}
