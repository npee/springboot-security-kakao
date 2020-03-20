package com.npeeproject.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

    @ApiModelProperty(value = "응답 성공여부 : true/false")
    private boolean success;

    @ApiModelProperty(value = "응답 코드 : +정상, -비정상")
    private int code;

    @ApiModelProperty(value = "응답 메시지")
    private String message;
}
