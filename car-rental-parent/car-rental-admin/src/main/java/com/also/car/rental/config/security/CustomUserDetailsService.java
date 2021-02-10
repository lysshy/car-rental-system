package com.also.car.rental.config.security;


import com.also.car.rental.entity.BaseUser;
import com.also.car.rental.service.BaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component(value="CustomUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final BaseUserService baseUserService;

    @Autowired
    public CustomUserDetailsService(BaseUserService baseUserService) {
        this.baseUserService = baseUserService;
    }

    @Override
    public UserDetail loadUserByUsername(String name) throws UsernameNotFoundException {
        BaseUser user = baseUserService.findByUsername(name);
        if (user == null) {
            throw new UsernameNotFoundException("user.username.notFound");
        }
        UserDetail userDetail = new UserDetail();
        userDetail.setUsername(user.getUsername());
        userDetail.setId(user.getId());
        userDetail.setPassword(user.getPassword());
        return userDetail;
    }

}
