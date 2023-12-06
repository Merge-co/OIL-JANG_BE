package com.mergeco.oiljang.userSanctions.service;

import com.mergeco.oiljang.common.paging.Criteria;
import com.mergeco.oiljang.userSanctions.dto.SanctionsDetailDTO;
import com.mergeco.oiljang.userSanctions.entity.UserSanctions;
import com.mergeco.oiljang.userSanctions.repository.UserSanctionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

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
    private final UserSanctionsRepository userSanctionsRepository;

    @Autowired
    public SanctionsService(UserSanctionsRepository userSanctionsRepository, EntityManager entityManager) {
        this.userSanctionsRepository = userSanctionsRepository;
        this.entityManager = entityManager;
    }

    // 전체 조회
    public List<UserSanctions> selectUserSanctions() {
        log.info("[userSanctions] selectUSerSanctions Start=========================");
        List<UserSanctions> sanctions = userSanctionsRepository.findAll();
        log.info("[userSanctions] selectUSerSanctions END=========================");
        return sanctions;
    }

    public int selectSanctionsTotal() {
        log.info("[SnactionsService] selectSnactionsTotal Start ===========");
        List<UserSanctions> sanctionsList = userSanctionsRepository.findAll();
        log.info("[SnactionsService] sanctionsList.size: {}", sanctionsList.size());
        log.info("[SnactionsService] selectSnactionsTotal END ===========");
        return sanctionsList.size();
    }

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











    public Optional<LocalDate> selectSanctionsByUser(int userCode) {
        log.info("[SanctionsService] selectSanctionsByUser Start =======");

        System.out.println("userCode : " + userCode);

        Date sqlDate = userSanctionsRepository.findByUserDate(userCode);

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
