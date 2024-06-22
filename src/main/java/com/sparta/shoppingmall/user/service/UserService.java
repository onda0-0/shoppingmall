package com.sparta.shoppingmall.user.service;


import java.time.LocalDateTime;
import java.util.Optional;
import com.sparta.shoppingmall.jwt.JwtProvider;
import com.sparta.shoppingmall.jwt.RefreshTokenService;
import com.sparta.shoppingmall.security.UserDetailsImpl;
import com.sparta.shoppingmall.user.dto.*;
import com.sparta.shoppingmall.user.entity.User;
import com.sparta.shoppingmall.user.entity.UserStatus;
import com.sparta.shoppingmall.user.entity.UserType;
import com.sparta.shoppingmall.user.exception.UserException;
import com.sparta.shoppingmall.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    /**
     * 1. 회원 가입
     * @param requestDTO 회원 가입 요청 데이터
     * @return SignupResponseDTO 회원 가입 결과
     */
    @Transactional
    public SignupResponseDTO createUser(SignupRequestDTO requestDTO) {
        //아이디 유효성 검사
        validateUsername(requestDTO.getUsername());


        //비밀번호 암호화
        String password = passwordEncoder.encode(requestDTO.getPassword());
        User user = User.builder()
                .username(requestDTO.getUsername())
                .password(password)
                .email(requestDTO.getEmail())
                .address(requestDTO.getAddress())
                .userStatus(UserStatus.JOIN)
                .userType(UserType.USER)
                .statusChangedAt(LocalDateTime.now())
                .build();

        User saveUser = userRepository.save(user);

        return new SignupResponseDTO(saveUser);
    }


    /**
     * 2. 회원 탈퇴
     * @param requestDTO 비밀번호 확인 요청 데이터
     * @param user 로그인한 사용자의 세부 정보
     * @return 탈퇴된 회원의 ID
     */
    @Transactional
    public String withdrawUser(WithdrawRequestDTO requestDTO, User user) {
        //회원 상태 확인
        checkUserStatus(user.getUserStatus());

        //비밀번호 일치 확인
        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new UserException("비밀번호가 일치하지 않습니다.");
        }

        //회원 상태 변경
        user.getUserStatus();
        userRepository.save(user);

        return user.getUsername();
    }

    /**
     * 4. 로그아웃
     * @param user 로그인한 사용자의 세부 정보
     * @param accessToken access token
     * @param refreshToken refresh token
     */
    @Transactional
    public void logout(User user, String accessToken, String refreshToken) {

        if(user==null){
            throw new UserException("로그인되어 있는 유저가 아닙니다.");
        }

        if(user.getUserStatus().equals(UserStatus.WITHDRAW)){
            throw new UserException("탈퇴한 회원입니다.");
        }

        User existingUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new UserException("해당 유저가 존재하지 않습니다."));

        existingUser.refreshTokenReset("");
        userRepository.save(existingUser);

        jwtProvider.invalidateToken(accessToken);
        jwtProvider.invalidateToken(refreshToken);
    }


    /**
     * 5. 회원 조회 (유저 아이디)
     * @param username 조회할 회원의 username
     * @return EditProfileResponseDTO 회원 조회 결과
     */
    public EditProfileResponseDTO inquiryUser(String userId) {
        User user = userRepository.findByUsername(userId).orElseThrow(() -> new UserException("해당 유저를 찾을 수 없습니다."));
        return new EditProfileResponseDTO(user);
    }


    /**
     * 7. 회원 프로필 수정
     * @param requestDTO 프로필 수정 요청 데이터
     * @param user 로그인한 사용자의 세부 정보
     * @return UserResponseDTO 회원 프로필 수정 결과
     */
    @Transactional // 변경할 필드만 수정하고 바꾸지 않은 필드는 기존 데이터를 유지하는 메서드
    public UserResponseDTO editProfile(EditProfileRequestDTO requestDTO, User user) {
        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new UserException("비밀번호가 일치하지 않습니다.");
        }

        String editUsername = requestDTO.getUsername() != null ? requestDTO.getUsername() : user.getUsername();
        String editEmail = requestDTO.getEmail() != null ? requestDTO.getEmail() : user.getEmail();
        String editAddress = requestDTO.getAddress() != null ? requestDTO.getAddress() : user.getAddress();

        user.editProfile(editUsername, editEmail,editAddress );
        userRepository.save(user);
        return new UserResponseDTO(user);
    }

    /**
     * 8. 비밀번호 변경
     * @param requestDTO 비밀번호 변경 요청 데이터
     * @param userDetails 로그인한 사용자의 세부 정보
     * @return UserResponseDTO 비밀번호 변경 결과
     */
    @Transactional
    public UserResponseDTO editPassword(EditPasswordRequestDTO requestDTO, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new UserException("현재 비밀번호가 일치하지 않습니다.");
        }

        if (passwordEncoder.matches(requestDTO.getNewPassword(), user.getPassword())) {
            throw new UserException("새로운 비밀번호와 기존 비밀번호가 동일합니다.");
        }

        // 새로운 비밀번호가 최근 비밀번호들과 일치하는지 확인 - > 유효성 검사
        if (passwordEncoder.matches(requestDTO.getNewPassword(), user.getRecentPassword())
                || passwordEncoder.matches(requestDTO.getNewPassword(), user.getRecentPassword2())
                || passwordEncoder.matches(requestDTO.getNewPassword(), user.getRecentPassword3())) {
            throw new UserException("새로운 비밀번호는 최근 사용한 비밀번호와 다르게 설정해야 합니다.");
        }

        // 최근3개 비밀번호 저장
        user.setRecentPassword3(user.getRecentPassword2());
        user.setRecentPassword2(user.getRecentPassword());
        user.setRecentPassword(user.getPassword());

        String editPassword = passwordEncoder.encode(requestDTO.getNewPassword());
        user.changePassword(editPassword);
        userRepository.save(user);

        return new UserResponseDTO(user);
    }

    /**
     * 회원 상태 확인
     * @param userStatus 회원 상태
     */
    private void checkUserStatus(UserStatus userStatus) {
        if (userStatus.equals(UserStatus.WITHDRAW)) {
            throw new UserException("이미 탈퇴한 회원입니다.");
        }
    }

    /**
     * username 유효성 검사
     * @param username 중복확인
     */
    private void validateUsername(String username) {
        Optional<User> findUser = userRepository.findByUsername(username);
        if (findUser.isPresent()) {
            throw new UserException("중복된 username 입니다.");
        }
    }

}
