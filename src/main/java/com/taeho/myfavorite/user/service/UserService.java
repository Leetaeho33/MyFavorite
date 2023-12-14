package com.taeho.myfavorite.user.service;

import com.taeho.myfavorite.user.dto.CheckUsernameRequestDTO;
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

        // Repository에 save
        if(checkDuplication(username)){
            throw new IllegalArgumentException("중복된 회원이 있습니다.");
        }
        if(password.contains(username)){
            throw new IllegalArgumentException("비밀번호에는 아이디가 포함되면 안됩니다.");
        }
        userRepository.save(user);
    }


    public void checkUsername(CheckUsernameRequestDTO checkUsernameRequestDTO) {
        String username = checkUsernameRequestDTO.getUsername();
        if(checkDuplication(username)) {
            throw new IllegalArgumentException("중복된 회원이 있습니다.");
        }
    }
    public User findUser(Long userid){
        User user = userRepository.findById(userid).
                orElseThrow(()-> new IllegalArgumentException("해당 유저는 없습니다."));
        return user;
    }

    public boolean checkDuplication(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent();
    }
}
