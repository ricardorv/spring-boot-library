package com.github.ricardorv.starter.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
public class BookDto {

    private Integer id;
    @NotBlank
    @ApiModelProperty(required = true)
    private String title;

}
