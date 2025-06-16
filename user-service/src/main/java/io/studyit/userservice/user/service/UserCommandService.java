package io.studyit.userservice.user.service;

import io.studyit.userservice.user.dto.ChangeNameRequest;
import io.studyit.userservice.user.dto.ChangePasswordRequest;
import io.studyit.userservice.user.dto.UserCreateRequest;
import io.studyit.userservice.user.entity.User;
import io.studyit.userservice.user.repository.RefreshTokenRepository;
import io.studyit.userservice.user.repository.UserRepository;
import io.studyit.userservice.user.security.UserDetailsImpl;
import io.studyit.userservice.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
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

        UserValidator.validateUserInput(request);

        if (userRepository.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 ID 입니다.");
        }

        User user = modelMapper.map(request, User.class);
        user.setEncodedPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }


    @Transactional
    public void changePassword(String userId, ChangePasswordRequest request, UserDetailsImpl userDetails) {
        if (request.getCurrentPassword() == null || request.getCurrentPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("현재 비밀번호는 필수 입력값입니다.");
        }

        if (request.getNewPassword() == null || request.getNewPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("새 비밀번호는 필수 입력값입니다.");
        }

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        if (!userDetails.getUserId().equals(userId)) {
            throw new IllegalArgumentException("본인만 비밀번호를 변경할 수 있습니다.");
        }

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 올바르지 않습니다.");
        }

        user.setEncodedPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void changeName(String userId, ChangeNameRequest request, UserDetailsImpl userDetails) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("변경할 이름은 필수 입력값입니다.");
        }

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        if (!user.getUserId().equals(userDetails.getUserId())) {
            throw new AccessDeniedException("본인만 이름을 변경할 수 있습니다.");
        }

        user.setName(request.getName());
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String userId, UserDetailsImpl userDetails) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        if (!user.getUserId().equals(userDetails.getUserId())) {
            throw new AccessDeniedException("본인만 회원 탈퇴할 수 있습니다.");
        }

        RefreshTokenRepository.deleteByUserId(user.getUserId());

        userRepository.delete(user);
    }


    @Transactional(readOnly = true)
    public User findUserByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));
    }
}