package com.mergeco.oiljang.common.paging;

import java.util.HashMap;
import java.util.Map;

public class JpqlPagingButton {
    public static Map<String, Map<String, Integer>> JpqlPagingNumCount(int page, int totalPage) {
        int pageBtn = 5;

        int FIRST_PAGE = 1;
        int before = (page - 1) / pageBtn * pageBtn;

        int after = Math.min(before + pageBtn + 1, totalPage);

        int lastButton = Math.min(before + pageBtn, totalPage);

        Map<String, Map<String, Integer>> pagingBtn = new HashMap<>();
        Map<String, Integer> pageStatus = new HashMap<>();
        Map<String, Integer> numPageBtn = new HashMap<>();

        pageStatus.put("current", page);
        pageStatus.put("firstPage", FIRST_PAGE);
        pageStatus.put("before", before);
        pageStatus.put("after", after);
        if(before == 0) {
            pageStatus.put("before", FIRST_PAGE);
        }

        for(int i = before + 1, j = 1; i <= lastButton; i++, j++) {
            numPageBtn.put("numBtn" + j, i);
        }

        pagingBtn.put("pageStatus", pageStatus);
        pagingBtn.put("numPageBtn", numPageBtn);
        return pagingBtn;
    }
}