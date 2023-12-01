package com.mergeco.oiljang.inquiry.controller;

import com.mergeco.oiljang.common.UserRole;
import com.mergeco.oiljang.common.paging.JpqlPagingButton;
import com.mergeco.oiljang.common.restApi.ResponseMessage;
import com.mergeco.oiljang.inquiry.dto.*;
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


    @ApiOperation(value = "카테고리 조회")
    @GetMapping("/inquiries/categories")
    public ResponseEntity<ResponseMessage> findCategoryList() {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<InqCategoryDTO> inqCategoryList = inqService.findAllCategory();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("inqCategoryList", inqCategoryList);

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



    @ApiOperation(value = "문의 리스트 조회")
    @GetMapping("/users/{userCode}/inquiries")
    public ResponseEntity<ResponseMessage> inqSelectList(
            @RequestParam(required = false) Integer page,
            @PathVariable int userCode,
            @RequestParam UserRole role,
            @RequestParam(required = false) String keyword
          ){

        if(page == null){
            page = 1;
        }

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        int limit = 10;
        int offset = limit * (page - 1);

        List<InqSelectListDTO> inqSelectListDTOList = inqService.selectInqList(page, userCode, role, offset, limit, keyword);



        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("inqSelectListDTOList", inqSelectListDTOList);
        double totalInq = Long.valueOf(inqService.countMsgList1(page, userCode, role, keyword)).doubleValue();
        int totalPage = (int) Math.ceil(totalInq / limit);

        if(page >= totalPage){
            page = totalPage;
        }else if(page < 1){
            page = 1;
        }

        Map<String, Map<String, Integer>> pagingBtn = JpqlPagingButton.JpqlPagingNumCount(page, totalPage);

        responseMap.put("totalInq", totalInq);
        responseMap.put("pageingBtn", pagingBtn);

        ResponseMessage responseMessage = new ResponseMessage(200, "문의 리스트 조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }



    @ApiOperation(value = "문의 카테고리로 조회")
    @GetMapping("/users/{userCode}/inquiries/categories/{inqCateCode}")
    public ResponseEntity<ResponseMessage> inqSelectCategory(
            @RequestParam(required = false) Integer page,
            @PathVariable int userCode,
            @RequestParam String role,
            @PathVariable int inqCateCode){

        if(page == null){
            page = 1;
        }

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        int limit = 10;
        int offset = limit * (page - 1);

        List<InqSelectListDTO> inqSelectListDTOList = inqService.selectInqListCate(page, userCode, inqCateCode, role,offset, limit);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("inqSelectListDTOList", inqSelectListDTOList);
        double totalInq = Long.valueOf(inqService.countMsgList2(page, userCode, inqCateCode, role)).doubleValue();
        int totalPage = (int) Math.ceil(totalInq / limit);

        if(page >= totalPage){
            page = totalPage;
        }else if(page < 1){
            page = 1;
        }

        Map<String, Map<String, Integer>> pagingBtn = JpqlPagingButton.JpqlPagingNumCount(page, totalPage);


        responseMap.put("totalInq", totalInq);
        responseMap.put("pageingBtn", pagingBtn);

        ResponseMessage responseMessage = new ResponseMessage(200, "문의 카테고리별 리스트 조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }


    @ApiOperation(value = "답변 여부로 조회")
    @GetMapping("/users/{userCode}/inquiries/status/{inqStatus}")
    public ResponseEntity<ResponseMessage> inqSelectStatus(
            @RequestParam(required = false) Integer page,
            @PathVariable int userCode,
            @PathVariable String inqStatus,
            @RequestParam String role
    ){

        if(page == null){
            page = 1;
        }

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        int limit = 10;
        int offset = limit * (page - 1);

        List<InqSelectListDTO> inqSelectListDTOList = inqService.selectInqStatus(page, userCode, inqStatus, role, offset, limit);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("inqSelectListDTOList", inqSelectListDTOList);

        double totalInq = Long.valueOf(inqService.countMsgList3(page, userCode, inqStatus, role)).doubleValue();
        int totalPage = (int) Math.ceil(totalInq / limit);

        if(page >= totalPage){
            page = totalPage;
        }else if(page < 1){
            page = 1;
        }

        Map<String, Map<String, Integer>> pagingBtn = JpqlPagingButton.JpqlPagingNumCount(page, totalPage);


        responseMap.put("totalInq", totalInq);
        responseMap.put("pageingBtn", pagingBtn);

        ResponseMessage responseMessage = new ResponseMessage(200, "문의 답변여부별 리스트 조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }




    @ApiOperation(value = "문의 수정")
    @PutMapping("/users/{userCode}/inquiries/{inqCode}")
        public ResponseEntity<ResponseMessage> updateInq(@RequestBody InqDTO inqDTO, @PathVariable int userCode, @PathVariable int inqCode){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        System.out.println("컨트롤러 inqDTO: " + inqDTO);
        int result = inqService.updateInq(inqDTO, userCode, inqCode);

        System.out.println("result : " + result);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("inqDTO", inqDTO);
        ResponseMessage responseMessage = new ResponseMessage(200, "문의 수정 성공", responseMap);
        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }


    @ApiOperation(value = "문의 삭제")
    @DeleteMapping("/users/{userCode}/inquiries/{inqCode}")
    public ResponseEntity<ResponseMessage> deleteInq(@PathVariable int inqCode, @PathVariable int userCode){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        int result = inqService.deleteInq(inqCode, userCode);

        System.out.println("result : " + result);
        Map<String, Object> responseMap = new HashMap<>();
        ResponseMessage responseMessage = new ResponseMessage(200, "문의 삭제 성공", responseMap);
        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }
}
