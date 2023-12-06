package com.mergeco.oiljang.userSanctions.service;

import com.mergeco.oiljang.common.paging.Criteria;
import com.mergeco.oiljang.product.entity.Product;
import com.mergeco.oiljang.product.entity.SellStatus;
import com.mergeco.oiljang.report.entity.Report;
import com.mergeco.oiljang.user.repository.UserRepository;
import com.mergeco.oiljang.userSanctions.dto.SanctionsDetailDTO;
import com.mergeco.oiljang.userSanctions.dto.UserSanctionsDTO;
import com.mergeco.oiljang.userSanctions.entity.UserSanctions;
import com.mergeco.oiljang.userSanctions.repository.UserSanctionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SanctionsService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;
    private final UserSanctionsRepository sanctionsRepository;

    @Autowired
    public SanctionsService(UserSanctionsRepository sanctionsRepository, EntityManager entityManager
    , ModelMapper modelMapper) {
        this.sanctionsRepository = sanctionsRepository;
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
    }


    public int selectSanctionsTotal() {
        log.info("[SnactionsService] selectSnactionsTotal Start ===========");
        List<UserSanctions> sanctionsList = sanctionsRepository.findAll();
        log.info("[SnactionsService] sanctionsList.size: {}", sanctionsList.size());
        log.info("[SnactionsService] selectSnactionsTotal END ===========");
        return sanctionsList.size();
    }

    // 페이징 처리 리스트 조회
    public Page<SanctionsDetailDTO> selectSanctionsListWithPaging(Criteria cri) {
        log.info("[SanctionsService] selectSanctionsListWithPaging Start =======");

        int index = cri.getPageNum() - 1;
        int count = cri.getAmount();

        Pageable pageable = PageRequest.of(index, count, Sort.by("sanctionsCode").descending());

        String jpql = "SELECT new com.mergeco.oiljang.userSanctions.dto.SanctionsDetailDTO (" +
                "s.sanctionsCode, s.sanctionsDate, s.refUserCode.userCode, s.refUserCode.id, s.refUserCode.nickname, " +
                "(SELECT COUNT(p) " +
                "FROM Product p " +
                "WHERE p.SellStatus.sellStatusCode = 3 " +
                "AND p.refUserCode = s.refUserCode.userCode)) " +
                "FROM Sanctions s ";


        TypedQuery<SanctionsDetailDTO> query = entityManager.createQuery(jpql, SanctionsDetailDTO.class);
        List<SanctionsDetailDTO> management = query.getResultList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), management.size());
        Page<SanctionsDetailDTO> sancations = new PageImpl<>(management.subList(start, end), pageable, management.size());

        log.info("[SanctionsService] selectSanctionsListWithPaging END =======");

        return sancations;
    }

    @Transactional
    public String registSanctions(UserSanctionsDTO sanctionsInfo) {
        log.info("[SanctionsService] insertSanctions Start ===========================");

        int result = 0 ;

        try{
            UserSanctions insertSanctions = modelMapper.map(sanctionsInfo, UserSanctions.class);
            sanctionsRepository.save(insertSanctions);
        }catch (Exception e) {
            log.info("[SanctionsRegist] Error : {} ", e);
            throw new RuntimeException(e);
        }
        log.info("[SanctionsService] insertSanctions END ===========================");
        return (result > 0 ) ? "제제등록 완료" : "제제등록 실패 ";
    }

     @Transactional
    public String modifySanctions(UserSanctionsDTO userSanctions) {

        log.info("[snactionsService] updateSanctions Start =============================");

        System.out.println("콘솔로드로 받아오는 UserSanctions : " + userSanctions);

        int result = 0;

        try {
            UserSanctions sanctions = sanctionsRepository.findByRefUserCode(userSanctions.getRefUserCode());
            sanctions = sanctions
                    .managerDate(userSanctions.getManagerDate())
                    .sanctionsDate(userSanctions.getSanctionsDate())
                    .build();
            result = 1 ;

        } catch (Exception e) {
            log.info("[Sanctions update] Exception !!" + e);
        }
        log.info("[snactionsService] updateSanctions END ================================");
        return (result > 0) ? "처리 완료" : "처리 실패";
    }


    public Optional<LocalDate> selectSanctionsByUser(int userCode) {
        log.info("[SanctionsService] selectSanctionsByUser Start =======");

        System.out.println("userCode : " + userCode);

        Date sqlDate = sanctionsRepository.findByUserDate(userCode);

        if (sqlDate != null) {
            LocalDate sanctionsDate = sqlDate.toLocalDate();
            System.out.println("sanctionsDate : " + sanctionsDate);
            return Optional.of(sanctionsDate);
        } else {
            log.info("[SanctionsService] sanctionsDate not found for userCode: {}", userCode);
            return Optional.empty();
        }
    }
}
