package com.mergeco.oiljang.userSanctions.service;

import com.mergeco.oiljang.userSanctions.entity.UserSanctions;
import com.mergeco.oiljang.userSanctions.repository.UserSanctionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserSanctionsService {

    private final UserSanctionsRepository userSanctionsRepository;
    public UserSanctionsService(UserSanctionsRepository userSanctionsRepository) {
        this.userSanctionsRepository = userSanctionsRepository;
    }

    public List<UserSanctions> selectUserSanctions() {
        log.info("[userSanctions] selectUSerSanctions Start=========================");
        List<UserSanctions> sanctions = userSanctionsRepository.findAll();
        log.info("[userSanctions] selectUSerSanctions END=========================");
        return sanctions;
    }
}
