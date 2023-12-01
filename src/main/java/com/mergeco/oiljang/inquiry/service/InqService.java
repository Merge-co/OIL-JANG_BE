package com.mergeco.oiljang.inquiry.service;

import com.mergeco.oiljang.common.UserRole;
import com.mergeco.oiljang.inquiry.dto.*;
import com.mergeco.oiljang.inquiry.entity.InqCategory;
import com.mergeco.oiljang.inquiry.entity.Inquiry;
import com.mergeco.oiljang.inquiry.repository.InqCategoryRepository;
import com.mergeco.oiljang.inquiry.repository.InqRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InqService {

    private final InqRepository inqRepository;
    private final ModelMapper modelMapper;

    private final InqCategoryRepository inqCategoryRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public InqService(InqRepository inqRepository, ModelMapper modelMapper, InqCategoryRepository inqCategoryRepository) {
        this.inqRepository = inqRepository;
        this.modelMapper = modelMapper;
        this.inqCategoryRepository = inqCategoryRepository;
    }


    @Transactional
    public void insertInquiry(InqInsertDTO inqInfo) {
        System.out.println("inqInsertDTO : " + inqInfo);
        inqRepository.save(modelMapper.map(inqInfo, Inquiry.class));
    }

//
//   public List<InqCategoryDTO> findAllCategory(){
//        List<InqCategory> categoryList = inqCategoryRepository.findAllCategory();
//
//        return categoryList.stream()
//                .map(category -> modelMapper.map(category, InqCategoryDTO,class))
//       .collect(Collectors.toList());
//   }
//


    //내 이름. 아이디는 토큰에서 가져오기...?
    public List<InqSelectDetailDTO> selectInqDetail(int inqCode) {
        String jpql = "SELECT new com.mergeco.oiljang.inquiry.dto.InqSelectDetailDTO(i.inqCode, i.refUserCode, u.name, u.id, i.inqTitle, i.inqContent, i.inqTime, i.inqAnswer, i.inqStatus, c.inqCateCode, c.inqCateName) "
                + "FROM inquiry_and_category i "
                + "LEFT JOIN User u ON i.refUserCode = u.userCode "
                + "JOIN i.inqCategory c "
                + "WHERE i.inqCode = :inqCode";

        List<InqSelectDetailDTO> selectDetailDTOList = entityManager.createQuery(jpql, InqSelectDetailDTO.class)
                .setParameter("inqCode", inqCode)
                .getResultList();

        System.out.println("문의 상세조회 서비스 : " + selectDetailDTOList);

        return selectDetailDTOList;
    }

    //현재 로그인 상태에 따른 내 이름. 아이디 정보같은 것들은 토큰에서 가져와야 할 것 같다...
    public List<InqSelectListDTO> selectInqList(int page, int userCode, UserRole role, int offset, int limit, String keyword) {
        String jpql;

        if ("ROLE_ADMIN".equals(role)) { // userCode가 4번인 경우 ADMIN이라고 가정
            jpql = "SELECT new com.mergeco.oiljang.inquiry.dto.InqSelectListDTO(i.inqCode, i.refUserCode, u.userCode, u.name, u.id, u.role, i.inqTitle, i.inqTime, i.inqStatus, c.inqCateCode, c.inqCateName) "
                    + "FROM inquiry_and_category i "
                    + "LEFT JOIN User u ON i.refUserCode = u.userCode "
                    + "JOIN i.inqCategory c "
                    + "WHERE (u.userCode != :userCode "
                    + "AND (u.name LIKE :keyword OR i.inqTitle LIKE :keyword OR c.inqCateName LIKE :keyword))";
        } else {
            System.out.println(" 거쳐가는지?================================================");
            jpql = "SELECT new com.mergeco.oiljang.inquiry.dto.InqSelectListDTO(i.inqCode, i.refUserCode, u.userCode, u.name, u.id, u.role, i.inqTitle, i.inqTime, i.inqStatus, c.inqCateCode, c.inqCateName) "
                    + "FROM inquiry_and_category i "
                    + "LEFT JOIN User u ON i.refUserCode = u.userCode "
                    + "JOIN i.inqCategory c "
                    + "WHERE (i.refUserCode = :userCode "
                    + "AND (u.name LIKE :keyword OR i.inqTitle LIKE :keyword OR c.inqCateName LIKE :keyword))";
            System.out.println(" 거쳐가는지?================================================");
        }


        List<InqSelectListDTO> inqSelectListDTOList = entityManager.createQuery(jpql, InqSelectListDTO.class)
                .setParameter("userCode", userCode)
                .setParameter("keyword", keyword)
                .getResultList();

        System.out.println("inqSelectList 서비스 : " + inqSelectListDTOList);
//        for(Object[] a : inqSelectListDTOList) {
//            for(Object c : a) {
//                System.out.println(c);
//            }
//        }
        return inqSelectListDTOList;
    }

    public long countMsgList1(int page, int userCode, UserRole role, String keyword) {
        Long countPage = inqRepository.countByRefUserCode(userCode);
        return countPage;
    }

    public long countMsgList2(int page, int userCode, int inqCateCode, String role) {
        Long countPage = inqRepository.countByRefUserCode(userCode);
        return countPage;
    }

    public long countMsgList3(int page, int userCode, String inqStatus, String role) {
        Long countPage = inqRepository.countByRefUserCode(userCode);
        return countPage;
    }

    public List<InqSelectListDTO> selectInqListCate(int page, int userCode, int inqCateCode,  String role, int offset, int limit) {

        String jpql;
        if ("ROLE_ADMIN".equals(role)) { // userCode가 4번인 경우 ADMIN이라고 가정
            jpql = "SELECT new com.mergeco.oiljang.inquiry.dto.InqSelectListDTO(i.inqCode, i.refUserCode, u.userCode, u.name, u.id, u.role, i.inqTitle, i.inqTime, i.inqStatus, c.inqCateCode, c.inqCateName) "
                    + "FROM inquiry_and_category i "
                    + "LEFT JOIN User u ON i.refUserCode = u.userCode "
                    + "JOIN i.inqCategory c "
                    + "WHERE u.userCode != :userCode AND c.inqCateCode = :inqCateCode";
        } else {
            System.out.println(" 거쳐가는지?================================================");
            jpql = "SELECT new com.mergeco.oiljang.inquiry.dto.InqSelectListDTO(i.inqCode, i.refUserCode, u.userCode, u.name, u.id, u.role, i.inqTitle, i.inqTime, i.inqStatus, c.inqCateCode, c.inqCateName) "
                    + "FROM inquiry_and_category i "
                    + "LEFT JOIN User u ON i.refUserCode = u.userCode "
                    + "JOIN i.inqCategory c "
                    + "WHERE i.refUserCode = :userCode AND c.inqCateCode = :inqCateCode";
            System.out.println(" 거쳐가는지?================================================");
        }

        List<InqSelectListDTO> inqSelectListDTOList = entityManager.createQuery(jpql, InqSelectListDTO.class)
                .setParameter("userCode", userCode)
                .setParameter("inqCateCode", inqCateCode)
                .getResultList();

        return inqSelectListDTOList;
    }

    public List<InqSelectListDTO> selectInqStatus(int page, int userCode, String inqStatus, String role, int offset, int limit) {

       String jpql;


       jpql = "SELECT new com.mergeco.oiljang.inquiry.dto.InqSelectListDTO(i.inqCode, i.refUserCode, u.userCode, u.name, u.id, u.role, i.inqTitle, i.inqTime, i.inqStatus, c.inqCateCode, c.inqCateName) "
                + "FROM inquiry_and_category i "
                + "LEFT JOIN User u ON i.refUserCode = u.userCode "
                + "JOIN i.inqCategory c "
                + "WHERE u.userCode != :userCode AND i.inqStatus = :inqStatus";


        List<InqSelectListDTO> inqSelectListDTOList = entityManager.createQuery(jpql, InqSelectListDTO.class)
                .setParameter("userCode", userCode)
                .setParameter("inqStatus", inqStatus)
                .getResultList();

        return inqSelectListDTOList;

    }


//    public List<InqSelectListDTO> selectInqLike(int userCode, int page, int offset, int limit, String keyword) {
//
//
//        String jpql;
//        if (userCode == 4) { // userCode가 4번인 경우 ADMIN이라고 가정
//            jpql = "SELECT new com.mergeco.oiljang.inquiry.dto.InqSelectListDTO(i.inqCode, i.refUserCode, u.userCode, u.name, u.id, i.inqTitle, i.inqTime, i.inqStatus, c.inqCateCode, c.inqCateName) "
//                    + "FROM inquiry_and_category i "
//                    + "LEFT JOIN User u ON i.refUserCode = u.userCode "
//                    + "JOIN i.inqCategory c "
//                    + "WHERE u.userCode != :userCode "
//                    + "AND u.name LIKE :keyword OR i.inqTitle LIKE :keyword OR c.inqCateName LIKE :keyword";
//        } else {
//            System.out.println(" 거쳐가는지?================================================");
//            jpql = "SELECT new com.mergeco.oiljang.inquiry.dto.InqSelectListDTO(i.inqCode, i.refUserCode, u.userCode, u.name, u.id, i.inqTitle, i.inqTime, i.inqStatus, c.inqCateCode, c.inqCateName) "
//                    + "FROM inquiry_and_category i "
//                    + "LEFT JOIN User u ON i.refUserCode = u.userCode "
//                    + "JOIN i.inqCategory c "
//                    + "WHERE i.refUserCode = :userCode "
//                    + "AND u.name LIKE :keyword OR i.inqTitle LIKE :keyword OR c.inqCateName LIKE :keyword";
//            System.out.println(" 거쳐가는지?================================================");
//        }
//
//        List<InqSelectListDTO> inqSelectListDTOList = entityManager.createQuery(jpql, InqSelectListDTO.class)
//                .setParameter("userCode", userCode)
//                .setParameter("keyword", "%" + keyword + "%")
//                .getResultList();
//
//        return inqSelectListDTOList;
//    }


    @Transactional
    public int updateInq(InqDTO inqDTO, int userCode, int inqCode) {
        int result = 0;

        try{
            Inquiry inquiry = inqRepository.findById(inqDTO.getInqCode()).get();
            System.out.println("서비스 inquiry: " + inquiry);

            if(userCode == 4){
                inquiry = inquiry.inqCode(inqDTO.getInqCode())
                        .inqTitle(inqDTO.getInqTitle())
                        .inqContent(inqDTO.getInqContent())
                        .inqAnswer(inqDTO.getInqAnswer())
                        .inqTime(inqDTO.getInqTime())
                        .refUserCode(inqDTO.getRefUserCode())
                        .inqCategory(inqDTO.getInqCateCode(), inqDTO.getInqCateName())
                        .inqStatus("Y").builder();
                System.out.println("조건1 확인=================================");
            }else{
                inquiry = inquiry.inqCode(inqDTO.getInqCode())
                        .inqTitle(inqDTO.getInqTitle())
                        .inqContent(inqDTO.getInqContent())
                        .inqAnswer(inqDTO.getInqAnswer())
                        .inqTime(inqDTO.getInqTime())
                        .refUserCode(inqDTO.getRefUserCode())
                        .inqCategory(inqDTO.getInqCateCode(), inqDTO.getInqCateName())
                        .inqStatus(inqDTO.getInqStatus()).builder();
                System.out.println("조건2 확인=================================");
            }



            inqRepository.save(inquiry);
            System.out.println("문의 수정 서비스 : " + inquiry);
            result = 1;
        } catch (Exception e){
            System.out.println("실패!");
        }


        return (result > 0) ? 1 : -1;
    }

    @Transactional
    public int deleteInq(int inqCode, int userCode) {

        int result = 0;

        try{
            inqRepository.deleteById(inqCode);

            result = 1;
        }catch (Exception e){
            System.out.println("실패~");
        }


        return (result > 0) ? 1 : -1;
    }

    public List<InqCategoryDTO> findAllCategory() {
        List<InqCategory> categoryList = inqCategoryRepository.findAllCategory();

        return categoryList.stream()
                .map(category -> modelMapper.map(category, InqCategoryDTO.class))
                .collect(Collectors.toList());
    }


//    @Transactional
//    public String updateInqStatus(int inqCode) {
//        int result = 0;
//
//        try{
//            Inquiry inquiry = inqRepository.findById(inqCode).orElseThrow(IllegalArgumentException::new);
//
//            inquiry.inqStatus("Y").builder();
//            inquiry = inquiry.inqStatus("Y").builder();
//            inqRepository.save(inquiry);
//
//            System.out.println(inquiry);
//
//            result = 1;
//        } catch (Exception e){
//            System.out.println("실패!");
//        }
//
//
//        return (result > 0) ? "문의 상태 수정 성공" : "문의 상태 수정 실패";
//    }


}
