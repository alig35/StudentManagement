package com.project.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.payload.response.abstracts.BaseUserResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse extends BaseUserResponse {
}
