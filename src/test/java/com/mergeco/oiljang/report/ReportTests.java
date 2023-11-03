package com.mergeco.oiljang.report;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.mergeco.oiljang.report.entity.Report;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ReportTests {

    @PersistenceContext
    private EntityManager entityManager;

    @DisplayName("일단은 number로조회 해봅시다.")
    @Test
    @Transactional
    public void testReporyFindByNo() {

        //given
        int reportNo = 1;
        //when
        String query = "SELECT report_no, report_user_nick, report_comment, report_date," +
                "FROM report " +
                "WHERE report_no = ?";

        Query reportNoQuery = entityManager.createNativeQuery(query, Report.class)
        .setParameter(1, reportNo);

        Report foundReport = (Report) reportNoQuery.getSingleResult();
        //then
        Assertions.assertNotNull(foundReport);
        Assertions.assertTrue(entityManager.contains(foundReport));
        System.out.println("잘나오는지 확인 : " + foundReport);

    }
}
