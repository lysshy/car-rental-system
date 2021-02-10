package com.also.car.rental.controller;


import com.also.car.rental.entity.base.BaseResult;
import com.also.car.rental.model.LoginReq;
import com.also.car.rental.model.LoginView;
import com.also.car.rental.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@Api(tags = "manager login interface")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("login")
    @ApiOperation("manager login")
    public BaseResult<LoginView> login(@RequestBody @Validated LoginReq loginReq) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        String jwt = jwtUtil.createJWT(authentication, loginReq.isRememberMe());
        LoginView loginView = new LoginView();
        loginView.setToken(jwt);
        loginView.setUsername(loginReq.getUsername());
        return BaseResult.ok(loginView);
    }

}
