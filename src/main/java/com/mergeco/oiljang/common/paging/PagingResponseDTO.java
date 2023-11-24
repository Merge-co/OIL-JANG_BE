package com.mergeco.oiljang.common.paging;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

@Getter
@Setter
@ToString
public class PagingResponseDTO {

    private Object data;
    private PageDTO pageInfo;
}
