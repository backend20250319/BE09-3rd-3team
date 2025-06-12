package io.studyit.userservice.user.service;

import io.studyit.userservice.user.dto.UserCreateRequest;
import io.studyit.userservice.user.entity.User;
import io.studyit.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(UserCreateRequest request) {

        if(userRepository.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 ID 입니다.");
        }

       User user = modelMapper.map(request, User.class);
       user.setEncodedPassword(passwordEncoder.encode(request.getPassword()));
       userRepository.save(user);
    }
}
