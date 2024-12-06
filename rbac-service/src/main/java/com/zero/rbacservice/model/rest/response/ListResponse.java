package com.zero.rbacservice.model.rest.response;

import com.zero.rbacservice.model.dtos.Pagination;
import com.zero.rbacservice.model.dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListResponse<T> {
    List<T> data;
    Pagination pagination;
}
