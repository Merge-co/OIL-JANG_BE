package com.mergeco.oiljang.inquery.service;

import com.mergeco.oiljang.inquery.dto.InqInsertDTO;
import com.mergeco.oiljang.inquery.entity.Inquiry;
import com.mergeco.oiljang.inquery.repository.InqRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InqService {

    private final InqRepository inqRepository;

    private final ModelMapper modelMapper;

    public InqService(InqRepository inqRepository, ModelMapper modelMapper) {
        this.inqRepository = inqRepository;
        this.modelMapper = modelMapper;
    }


    @Transactional
    public void insertInquiry(InqInsertDTO inqInsertDTO) {
        System.out.println("inqInsertDTO : " + inqInsertDTO);
        inqRepository.save(modelMapper.map(inqInsertDTO, Inquiry.class));
    }
}
