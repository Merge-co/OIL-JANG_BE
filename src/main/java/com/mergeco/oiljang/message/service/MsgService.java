package com.mergeco.oiljang.message.service;

import com.mergeco.oiljang.message.dto.MsgInsertDTO;
import com.mergeco.oiljang.message.entity.Message;
import com.mergeco.oiljang.message.repository.MsgDeleteRepository;
import com.mergeco.oiljang.message.repository.MsgRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MsgService {

    private final MsgRepository msgRepository;
    private final ModelMapper modelMapper;

    private final MsgDeleteRepository msgDeleteRepository;

    public MsgService(MsgRepository msgRepository,
                      ModelMapper modelMapper,
                      MsgDeleteRepository msgDeleteRepository) {
        this.msgRepository = msgRepository;
        this.modelMapper = modelMapper;
        this.msgDeleteRepository = msgDeleteRepository;
    }

    @Transactional
        public void insertMsg(MsgInsertDTO msgInfo) {
        System.out.println("msgInfo: " + msgInfo);
        msgRepository.save(modelMapper.map(msgInfo, Message.class));
    }
}
