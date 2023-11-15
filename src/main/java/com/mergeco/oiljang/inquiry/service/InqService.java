package com.mergeco.oiljang.inquiry.service;

import com.mergeco.oiljang.inquiry.dto.InqInsertDTO;
import com.mergeco.oiljang.inquiry.dto.InqSelectDetailDTO;
import com.mergeco.oiljang.inquiry.dto.InqSelectListDTO;
import com.mergeco.oiljang.inquiry.entity.Inquiry;
import com.mergeco.oiljang.inquiry.repository.InqRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class InqService {

    private final InqRepository inqRepository;
    private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public InqService(InqRepository inqRepository, ModelMapper modelMapper) {
        this.inqRepository = inqRepository;
        this.modelMapper = modelMapper;
    }


    @Transactional
    public void insertInquiry(InqInsertDTO inqInfo) {
        System.out.println("inqInsertDTO : " + inqInfo);
        inqRepository.save(modelMapper.map(inqInfo, Inquiry.class));
    }





    //내 이름. 아이디는 토큰에서 가져오기...?
    public List<InqSelectDetailDTO> selectInqDetail(int inqCode) {
        String jpql = "SELECT new com.mergeco.oiljang.inquiry.dto.InqSelectDetailDTO(i.inqCode, i.refUserCode, u.name, u.id, i.inqTitle, i.inqContent, i.inqTime, i.inqAnswer, i.inqStatus, c.inqCateCode, c.inqCateName) "
                + "FROM inquiry_and_category i "
                + "LEFT JOIN User u ON i.refUserCode = u.userCode "
                + "LEFT JOIN i.inqCategory c "
                + "WHERE i.inqCode = :inqCode";

        List<InqSelectDetailDTO> selectDetailDTOList = entityManager.createQuery(jpql, InqSelectDetailDTO.class)
                .setParameter("inqCode", inqCode)
                .getResultList();

        System.out.println("문의 상세조회 서비스 : " + selectDetailDTOList);

        return selectDetailDTOList;
    }

    //현재 로그인 상태에 따른 내 이름. 아이디 정보같은 것들은 토큰에서 가져와야 할 것 같다...
    public List<InqSelectListDTO> selectInqList(int userCode, int offset, int limit) {
        String jpql;

        if (userCode == 4) { // userCode가 4번인 경우 ADMIN이라고 가정
            jpql = "SELECT new com.mergeco.oiljang.inquiry.dto.InqSelectListDTO(i.inqCode, i.refUserCode, u.userCode, u.name, u.id, i.inqTitle, i.inqTime, i.inqStatus, c.inqCateCode, c.inqCateName) "
                    + "FROM inquiry_and_category i "
                    + "LEFT JOIN User u ON i.refUserCode = u.userCode "
                    + "LEFT JOIN i.inqCategory c "
                    + "WHERE i.refUserCode != :userCode";
        } else {
            jpql = "SELECT new com.mergeco.oiljang.inquiry.dto.InqSelectListDTO(i.inqCode, i.refUserCode, u.userCode, u.name, u.id, i.inqTitle, i.inqTime, i.inqStatus, c.inqCateCode, c.inqCateName) "
                    + "FROM inquiry_and_category i "
                    + "LEFT JOIN User u ON i.refUserCode = u.userCode "
                    + "LEFT JOIN i.inqCategory c "
                    + "WHERE i.refUserCode = :userCode";
        }


        List<InqSelectListDTO> inqSelectListDTOList = entityManager.createQuery(jpql, InqSelectListDTO.class)
                .setParameter("userCode", userCode)
                .getResultList();

        System.out.println("inqSelectList 서비스 : " + inqSelectListDTOList);
        return inqSelectListDTOList;
    }

    public long countMsgList(int userCode) {
        Long countPage = inqRepository.countByRefUserCode(userCode);
        return countPage;
    }





}
