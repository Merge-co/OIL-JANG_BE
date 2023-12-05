package com.mergeco.oiljang.userSanctions.controller;


import com.mergeco.oiljang.common.paging.Criteria;
import com.mergeco.oiljang.common.paging.PageDTO;
import com.mergeco.oiljang.common.paging.PagingResponseDTO;
import com.mergeco.oiljang.common.restApi.LoginMessage;
import com.mergeco.oiljang.userSanctions.service.SanctionsService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sanctions")
@Slf4j
public class UserSanctionsController {

    private final SanctionsService sanctionsService;

    public UserSanctionsController(SanctionsService sanctionsService) {
        this.sanctionsService = sanctionsService;
    }

    @ApiOperation(value = "사용자제제", notes = "사용자 제제조회", tags = {"SanctionsController"})
    @GetMapping("/sanction")
    public ResponseEntity<?> selectSanctions(
            @RequestParam(name = "offset", defaultValue = "1") String offset) {
        log.info("[SanctionsController] selectSanctions Start ==================");
        log.info("[SanctionsController] selectReportSnactions offset : {}", offset);
        try {
            Criteria cri = new Criteria(Integer.valueOf(offset), 10);
            PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();

            int total = sanctionsService.selectSanctionsTotal();
            pagingResponseDTO.setData(sanctionsService.selectSanctionsListWithPaging(cri));
            pagingResponseDTO.setPageInfo(new PageDTO(cri, total));

            log.info("[SanctionsController] selectSanctions END ==================");

            return ResponseEntity.ok().body(new LoginMessage(HttpStatus.OK, "조회 성공", pagingResponseDTO));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginMessage(HttpStatus.INTERNAL_SERVER_ERROR, "요청을 처리하는 동안 에러 발생", null));
        }
    }
}
