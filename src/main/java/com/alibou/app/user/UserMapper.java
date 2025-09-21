package com.alibou.app.user;

import com.alibou.app.auth.request.RegistrationRequest;
import com.alibou.app.user.request.ProfileUpdateRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public void mergeUserInfo(final User user, final ProfileUpdateRequest request) {

        if (StringUtils.isNotBlank(request.getFirstName())
         && !user.getFirstName().equals(request.getFirstName())){
            user.setFirstName(request.getFirstName());
        }
        if (StringUtils.isNotBlank(request.getLastName())
                && !user.getLastName().equals(request.getLastName())){
            user.setLastName(request.getLastName());
        }
        if (request.getDateOfBirth() != null
                && !user.getDateOfBirth().equals(request.getDateOfBirth())){
            user.setDateOfBirth((request.getDateOfBirth()));
        }
    }

    public User toUser(RegistrationRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(request.getPassword())
                .enabled(true)
                .locked(false)
                .credentialsExpired(false)
                .emailVerified(false)
                .phoneVerified(false)
                .build();
    }
}
