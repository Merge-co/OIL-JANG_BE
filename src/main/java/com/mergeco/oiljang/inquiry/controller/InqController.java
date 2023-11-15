package com.mergeco.oiljang.inquiry.controller;

import com.mergeco.oiljang.common.paging.JpqlPagingButton;
import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.inquiry.dto.InqInsertDTO;
import com.mergeco.oiljang.inquiry.dto.InqSelectDetailDTO;
import com.mergeco.oiljang.inquiry.dto.InqSelectListDTO;
import com.mergeco.oiljang.inquiry.service.InqService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Api(tags = "문의 컨트롤러")
public class InqController {

    public final InqService inqService;


    public InqController(InqService inqService) {
        this.inqService = inqService;
    }

    @ApiOperation(value = "문의 등록")
    @PostMapping("/inquiries")
    public ResponseEntity<ResponseMessage> inqInsert(@RequestBody InqInsertDTO inqInsertDTO){
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        inqService.insertInquiry(inqInsertDTO);
        System.out.println("controller inqInsertDTO : " + inqInsertDTO);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("inqInsertDTO", inqInsertDTO);

        ResponseMessage responseMessage = new ResponseMessage(200, "문의 등록 성공", responseMap);
        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }



    @ApiOperation(value = "문의 상세 조회")
    @GetMapping("/inquiries/{inqCode}")
    public ResponseEntity<ResponseMessage> selectInqDetail(@PathVariable int inqCode){

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<InqSelectDetailDTO> inqSelectDetailDTOList = inqService.selectInqDetail(inqCode);
        System.out.println("문의 상세 조회 컨트롤러 : " + inqSelectDetailDTOList);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("inqSelectDetailDTOList", inqSelectDetailDTOList);

        ResponseMessage responseMessage = new ResponseMessage(200, "문의 상세 조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }



    @ApiOperation(value = "회원용 문의 리스트 조회")
    @GetMapping("/users/{userCode}/inquiries")
    public ResponseEntity<List<InqSelectListDTO>> inqSelectList(
            @RequestParam(required = false) Integer page,
            @PathVariable int userCode,
            @RequestParam String authName){

        if(page == null){
            page = 1;
        }

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        int limit = 3;
        int offset = limit * (page - 1);

        List<InqSelectListDTO> inqSelectListDTOList = inqService.selectInqList(userCode, offset, limit);


        double totalMsg = Long.valueOf(inqService.countMsgList(userCode)).doubleValue();
        int totalPage = (int) Math.ceil(totalMsg / limit);

        if(page >= totalPage){
            page = totalPage;
        }else if(page < 1){
            page = 1;
        }

        Map<String, Map<String, Integer>> pagingBtn = JpqlPagingButton.JpqlPagingNumCount(page, totalPage);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("inqSelectListDTOList", inqSelectListDTOList);
        responseMap.put("pageingBtn", pagingBtn);

        ResponseMessage responseMessage = new ResponseMessage(200, "쪽지 리스트 조회 성공", responseMap);

        return new ResponseEntity<>(inqSelectListDTOList, HttpStatus.OK);
    }




}
