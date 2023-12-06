package com.mergeco.oiljang.userSanctions.controller;


import com.mergeco.oiljang.common.paging.Criteria;
import com.mergeco.oiljang.common.paging.PageDTO;
import com.mergeco.oiljang.common.paging.PagingResponseDTO;
import com.mergeco.oiljang.common.restApi.LoginMessage;
import com.mergeco.oiljang.userSanctions.dto.UserSanctionsDTO;
import com.mergeco.oiljang.userSanctions.service.SanctionsService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sanctions")
@Slf4j
public class UserSanctionsController {

    private final SanctionsService sanctionsService;

    public UserSanctionsController(SanctionsService sanctionsService) {
        this.sanctionsService = sanctionsService;
    }

    @ApiOperation(value = "제제내역 조회", notes = "사용자 제제조회", tags = {"SanctionsController"})
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

    @ApiOperation(value = "제제 등록", notes = "제제 등록 합니다.", tags = {"SanctionsController"})
    @PostMapping("sanction")
    public ResponseEntity<?> registSanctions(@RequestBody UserSanctionsDTO sanctions) {
        log.info("[SanctionsController] registSanctions Start ===============");
        log.info("[SanctionsController] registSanctions END =================");
        return ResponseEntity.ok()
                .body(new LoginMessage(HttpStatus.OK, "제제 입력 성공", sanctionsService.registSanctions(sanctions)));
    }

    @ApiOperation(value = "제제 처리 ", notes = "관리자가 제제내용을 처리합니다.", tags = {"SanctionsController"})
    @PutMapping("/sanction")
    public ResponseEntity<?> sanctionsModify(@RequestBody UserSanctionsDTO userSanctions) {

        log.info("DTO 받아 오나요 ? : " + userSanctions);

        return ResponseEntity.ok().body(new LoginMessage(HttpStatus.OK, "접수된 제제 처리 완료", sanctionsService.modifySanctions(userSanctions)));
    }
}
