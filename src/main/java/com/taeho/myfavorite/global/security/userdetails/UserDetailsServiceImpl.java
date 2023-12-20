package com.taeho.myfavorite.global.security.userdetails;

import com.taeho.myfavorite.user.entity.User;
import com.taeho.myfavorite.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl {
    private final UserRepository userRepository;

    public UserDetailsImpl getUserDetails(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("해당 유저를 찾을 수 없습니다." + username));
        return new UserDetailsImpl(user);
    }
}
