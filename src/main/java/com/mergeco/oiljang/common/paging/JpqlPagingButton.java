package com.mergeco.oiljang.common.paging;

import java.util.HashMap;
import java.util.Map;

public class JpqlPagingButton {
    public static Map<String, Integer> JpqlPagingNumCount(int page, int totalPage) {
        int pageBtn = 5;

        int FIRST_PAGE = 1;
        int before = (page - 1) / pageBtn * pageBtn;

        int after = Math.min(before + pageBtn + 1, totalPage);

        int lastButton = Math.min(before + pageBtn, totalPage);

        Map<String, Integer> pageNo = new HashMap<>();
        pageNo.put("current", page);
        pageNo.put("firstPage", FIRST_PAGE);
        pageNo.put("before", before);
        if(before == 0) {
            pageNo.put("before", FIRST_PAGE);
        }

        for(int i = before + 1, j = 1; i <= lastButton; i++, j++) {
            pageNo.put("numBtn" + j, i);
        }

        pageNo.put("after", after);
        pageNo.put("lastPage", totalPage);
        return pageNo;
    }
}
