package com.mergeco.oiljang.common.paging;

import java.util.ArrayList;
import java.util.List;

public class JpqlPagingButton {
    public static List<String> JpqlPagingNumCount(int page, int totalPage) {
        int pageBtn = 5;

        int FIRST_PAGE = 1;
        int before = (page - 1) / pageBtn * pageBtn;

        int after = 0;
        if(before + pageBtn + 1 > totalPage) {
            after = 0;
        } else {
            after = before + pageBtn + 1;
        }

        int lastButton = 0;
        if(before + 5 >= totalPage) {
            lastButton = totalPage;
        } else {
            lastButton = before + pageBtn;
        }

        List<String> pageNo = new ArrayList<>();
        pageNo.add("firstPage : " + FIRST_PAGE);
        pageNo.add("before : " + before);

        for(int i = before + 1; i <= lastButton; i++) {
            if(i == page) {
                pageNo.add("current : " + i);
            } else {
                pageNo.add("numButton : " + i);
            }
        }

        pageNo.add("after : " + after);
        pageNo.add("lastPage : " + totalPage);
        return pageNo;
    }
}
