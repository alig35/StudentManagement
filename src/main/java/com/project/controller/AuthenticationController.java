package com.project.controller;

import com.project.payload.messages.SuccessMessages;
import com.project.payload.request.LoginRequest;
import com.project.payload.request.business.UpdatePasswordRequest;
import com.project.payload.response.AuthResponse;
import com.project.payload.response.UserResponse;
import com.project.service.AuthenticationService;
import com.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login") // http://localhost:8080/auth/login  + POST + JSON
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid LoginRequest loginRequest){

        return authenticationService.authenticateUser(loginRequest);
    }


    @GetMapping("/user") // http://localhost:8080/auth/user + GET
    @PreAuthorize("hasAnyAuthority('ADMIN')") // Admin
    public ResponseEntity<UserResponse> findByUsername(HttpServletRequest request){
       String username = (String) request.getAttribute("username");
       UserResponse userResponse =  authenticationService.findByUsername(username);
       return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("/updatePassword")  // http://localhost:8080/auth/updatePassword + PATCH + JSON
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER','STUDENT')")
    public ResponseEntity<String> updatePassword(@RequestBody @Valid UpdatePasswordRequest updatePasswordRequest,
                                                 HttpServletRequest request){
       authenticationService.updatePassword(updatePasswordRequest, request);
       String response = SuccessMessages.PASSWORD_CHANGED_RESPONSE_MESSAGE;
       return ResponseEntity.ok(response);
    }
}





















