package com.taeho.myfavorite.user.service;

import com.taeho.myfavorite.user.dto.CheckUsernameRequestDTO;
import com.taeho.myfavorite.user.dto.LoginRequestDTO;
import com.taeho.myfavorite.user.dto.SignupRequestDTO;
import com.taeho.myfavorite.user.entity.User;
import com.taeho.myfavorite.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;


    public void signup(SignupRequestDTO signupRequesetDTO) {
        // DTO -> Entity
        String username = signupRequesetDTO.getUsername();
        String password = signupRequesetDTO.getPassword();
        String nickname= signupRequesetDTO.getNickname();
        User user = User.builder().username(username).password(password).nickname(nickname).build();

        checkDuplication(username);
        // Repository에 save
        if(password.contains(username)){
            throw new IllegalArgumentException("비밀번호에는 아이디가 포함되면 안됩니다.");
        }
        userRepository.save(user);
    }

    public void checkUsername(CheckUsernameRequestDTO checkUsernameRequestDTO) {
        String username = checkUsernameRequestDTO.getUsername();
        checkDuplication(username);
    }

    public void login(LoginRequestDTO loginRequestDTO) {
        String username = loginRequestDTO.getUsername();
        String password = loginRequestDTO.getPassword();
        findUser(username);

    }
    public User findUser(String username){
        return userRepository.findByUsername(username).
                orElseThrow(()-> new IllegalArgumentException("해당 유저는 없습니다."));
    }

    public void checkDuplication(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()) throw new IllegalArgumentException("중복된 회원이 있습니다.");
    }
}
