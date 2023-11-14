package com.mergeco.oiljang.inquery.controller;

import com.mergeco.oiljang.inquery.service.InqService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(tags = "문의 컨트롤러")
public class InqController {

    public final InqService inqService;


    public InqController(InqService inqService) {
        this.inqService = inqService;
    }
}
