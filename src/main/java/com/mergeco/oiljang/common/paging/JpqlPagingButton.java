package com.mergeco.oiljang.common.paging;

import java.util.HashMap;
import java.util.Map;

public class JpqlPagingButton {
    public static Map<String, Integer> JpqlPagingNumCount(int page, int totalPage) {
        int pageBtn = 5;

        int FIRST_PAGE = 1;
        int before = (page - 1) / pageBtn * pageBtn;

        int after = 0;
        if(before + pageBtn + 1 > totalPage) {
            after = totalPage;
        } else {
            after = before + pageBtn + 1;
        }

        int lastButton = 0;
        if(before + 5 >= totalPage) {
            lastButton = totalPage;
        } else {
            lastButton = before + pageBtn;
        }

        Map<String, Integer> pageNo = new HashMap<>();
        pageNo.put("firstPage", FIRST_PAGE);
        pageNo.put("before", before);
        if(before == 0) {
            pageNo.put("before", 1);
        }

        for(int i = before + 1, j = 1; i <= lastButton; i++, j++) {
            if(i == page) {
                pageNo.put("numBtn" + String.valueOf(j) + ", current", i);
            } else {
                pageNo.put("numBtn" + String.valueOf(j), i);
            }
        }

        pageNo.put("after", after);
        pageNo.put("lastPage", totalPage);
        return pageNo;
    }
}
