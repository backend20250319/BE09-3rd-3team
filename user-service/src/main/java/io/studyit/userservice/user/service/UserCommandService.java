package io.studyit.userservice.user.service;

import io.studyit.userservice.user.dto.ChangeNameRequest;
import io.studyit.userservice.user.dto.ChangePasswordRequest;
import io.studyit.userservice.user.dto.UserCreateRequest;
import io.studyit.userservice.user.entity.User;
import io.studyit.userservice.user.repository.UserRepository;
import io.studyit.userservice.user.security.UserDetailsImpl;
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

    public void changePassword(Long id, ChangePasswordRequest request, UserDetailsImpl userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 올바르지 않습니다.");
        }

        // 새 비밀번호 암호화 후 저장
        user.setEncodedPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public void changeName(Long id, ChangeNameRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        user.setName(request.getName());
        userRepository.save(user);
    }
}
