package com.github.ricardorv.starter.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class AuthorDto {

    private Integer id;
    @NotBlank
    @ApiModelProperty(required = true)
    private String name;

}
