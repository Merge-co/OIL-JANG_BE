package com.mergeco.oiljang.inquiry.controller;

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
import java.rmi.server.ObjID;
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


//    @ApiOperation(value = "카테고리 조회")
//    @GetMapping("/categories")
//    public List<InqCategoryDTO> findCategoryList() {
//
//        HttpHeaders headers = new HttpHeaders();
//
//        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//
//        inqService.findAllCategory();
//        System.out.println("controller inqInsertDTO : " + inqInsertDTO);
//        Map<String, Object> responseMap = new HashMap<>();
//        responseMap.put("inqInsertDTO", inqInsertDTO);
//
//        ResponseMessage responseMessage = new ResponseMessage(200, "문의 등록 성공", responseMap);
//        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
//    }
//




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
            @PathVariable int userCode){

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

        ResponseMessage responseMessage = new ResponseMessage(200, "문의 리스트 조회 성공", responseMap);

        return new ResponseEntity<>(inqSelectListDTOList, HttpStatus.OK);
    }



    @ApiOperation(value = "문의 카테고리로 조회")
    @GetMapping("/users/{userCode}/inquiries/categories/{inqCateCode}")
    public ResponseEntity<ResponseMessage> inqSelectCategory(
            @RequestParam(required = false) Integer page,
            @PathVariable int userCode,
            @PathVariable int inqCateCode){

        if(page == null){
            page = 1;
        }

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        int limit = 3;
        int offset = limit * (page - 1);

        List<InqSelectListDTO> inqSelectListDTOList = inqService.selectInqListCate(userCode, inqCateCode, offset, limit);


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

        ResponseMessage responseMessage = new ResponseMessage(200, "문의 카테고리별 리스트 조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }


    @ApiOperation(value = "답변 여부로 조회")
    @GetMapping("/users/{userCode}/inquiries/status/{inqStatus}")
    public ResponseEntity<ResponseMessage> inqSelectStatus(
            @RequestParam(required = false) Integer page,
            @PathVariable int userCode,
            @PathVariable String inqStatus){

        if(page == null){
            page = 1;
        }

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        int limit = 3;
        int offset = limit * (page - 1);

        List<InqSelectListDTO> inqSelectListDTOList = inqService.selectInqStatus(userCode, inqStatus, offset, limit);


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

        ResponseMessage responseMessage = new ResponseMessage(200, "문의 답변여부별 리스트 조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }





    @ApiOperation(value = "검색어로 조회")
    @GetMapping("/users/{userCode}/inquiries?keyword={keyword}")
    public ResponseEntity<ResponseMessage> inqSelectLike(
            @RequestParam(required = false) Integer page,
            @PathVariable int userCode,
            @PathVariable String keyword){

        if(page == null){
            page = 1;
        }

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        int limit = 3;
        int offset = limit * (page - 1);

        List<InqSelectListDTO> inqSelectListDTOList = inqService.selectInqLike(userCode, offset, limit, keyword);


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

        System.out.println(result);

        Map<String, Object> reponseMap = new HashMap<>();
        reponseMap.put("inqDTO", inqDTO);
        ResponseMessage responseMessage = new ResponseMessage(200, "문의 수정 성공", reponseMap);
        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }
}
