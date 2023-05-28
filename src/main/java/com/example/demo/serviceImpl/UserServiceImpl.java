package com.example.demo.serviceImpl;

import com.example.demo.domain.User;
import com.example.demo.dto.token.AccessTokenDTO;
import com.example.demo.exceptions.ErrorCode;
import com.example.demo.exceptions.UserException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    public AccessTokenDTO login(User user) throws Exception {
        Optional<User> userData = userRepository.findByEmail(user.getEmail());
        if(userData.isEmpty()) {
            throw new UserException(ErrorCode.User_Invalid_Request);
        }
        User userFromDB = userData.get();

        // 비밀번호 비교
        if(!encoder.matches(user.getPassword(), userFromDB.getPassword())) {
            throw new UserException(ErrorCode.User_Invalid_Request);
        }

        return new AccessTokenDTO(jwtUtil.getAccessToken(userFromDB.getId()));
    }

    @Transactional(readOnly = true)
    public User getInfo(Long id) throws Exception {
        return findMember(id);
    }

    @Transactional(readOnly = true)
    public User findMember(Long memberId) { return userRepository.findOne(memberId);}

    public void updateAnsweringStatus(Long user_id, Boolean isAnswering) throws Exception {
        User user = getInfo(user_id);
        user.setAnswering(isAnswering);
    }
}
