package com.mergeco.oiljang.user;

import com.mergeco.oiljang.auth.model.dto.JoinDTO;
import com.mergeco.oiljang.auth.model.dto.LoginDTO;
import com.mergeco.oiljang.common.UserRole;
import com.mergeco.oiljang.user.entity.EnrollType;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.entity.UserProfile;
import com.mergeco.oiljang.user.model.dto.UserProfileDTO;
import com.mergeco.oiljang.user.model.service.UserService;
import com.mergeco.oiljang.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
/*import org.junit.jupiter.params.provider.Arguments;*/
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
/*
import org.springframework.dao.DataIntegrityViolationException;
*/

import java.time.LocalDateTime;
/*
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
*/

@SpringBootTest
public class UserTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

   @BeforeEach
   private void insertUser() throws Exception {

       UserProfile profile1 = UserProfile
               .builder()
               .userImageOriginName("profile_image1.jpg")
               .userImageName("image1.jpg")
               .userImageOriginAddr("original1.jpg")
               .userImageThumbAddr("thumbnail1.jpg")
               .build();

       User user1 = User.builder()
               .nickname("유저01")
               .name("유저01")
               .id("user01")
               .pwd("user01")
               .birthDate("2000-01-01")
               .gender("여")
               .phone("010-1234-1234")
               .email("user1@example.com")
               .enrollType(EnrollType.NORMAL)
               .token("token1")
               .role(UserRole.USER)
               .verifyStatus("Y")
               .enrollDate(LocalDateTime.now())
               .userProfile(profile1)
               .profileImageUrl("profile_image1.jpg")
               .withdrawStatus("N")
               .build();

            userRepository.save(user1);

       UserProfile profile2 = UserProfile.builder()
               .userImageOriginName("profile_image2.jpg")
               .userImageName("image2.jpg")
               .userImageOriginAddr("original2.jpg")
               .userImageThumbAddr("thumbnail2.jpg")
               .build();

       User user2 = User.builder()
               .nickname("nickname2")
               .name("Name2")
               .id("user2")
               .pwd("password2")
               .birthDate("2000-01-02")
               .gender("Female")
               .phone("9876543210")
               .email("user2@example.com")
               .enrollType(EnrollType.NORMAL)
               .token("token2")
               .role(UserRole.USER)
               .verifyStatus("Y")
               .withdrawStatus("N")
               .enrollDate(LocalDateTime.now())
               .userProfile(profile2)
               .profileImageUrl("profile_image2.jpg")
               .withdrawStatus("N")
               .build();

       userRepository.save(user2);


   }

  /*  @AfterEach
    public void afterEach(){
        userRepository.clearStore();
    }*/

    /*private static Stream<Arguments> createUser(){
        return Stream.of(
                Arguments.of("유저14","유저14","user14","user14","1999-02-02","여",EnrollType.NORMAL,UserRole.USER,"010-4214-6344","ddd111@naver.com","Y")
        );
    }*/

  /*  private static Stream<Arguments> getSameUser(){
        return Stream.of(
                Arguments.of("유저3","유저3","user13","user03","1999-02-02","여",EnrollType.NORMAL,UserRole.USER,"010-1323-1521","ddd111@naver.com","Y"),
                Arguments.of("유저13","유저3","user03","user03","1999-02-02","여",EnrollType.NORMAL,UserRole.USER,"010-1141-4211","ddd111@naver.com","Y"),
                Arguments.of("유저23","유저3","user23","user03","1999-02-02","여",EnrollType.NORMAL,UserRole.USER,"010-1234-1234","ddd111@naver.com","Y")
        );
    }*/


    @DisplayName("회원가입 성공 테스트")
    @Test
    public void testJoin() throws Exception {
        //given
        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setNickname("newuser");
        joinDTO.setName("New User");
        joinDTO.setId("newuser");
        joinDTO.setPwd("newpassword");
        joinDTO.setBirthDate("2000-01-03");
        joinDTO.setGender("Male");
        joinDTO.setPhone("1111111111");
        joinDTO.setEmail("newuser@example.com");
        joinDTO.setProfileImageUrl("newuser_image.jpg");

        UserProfileDTO profileDTO = new UserProfileDTO();
        profileDTO.setUserImageOriginName("newuser_image.jpg");

        //when
        User newUser = userService.join(joinDTO, profileDTO, null);

        //then
        Assertions.assertNotNull(newUser);
        User savedUser = userRepository.findById("newuser").orElse(null);
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals("newuser", savedUser.getId());

    }

  /*  @DisplayName("중복확인 테스트")
    @ParameterizedTest
    @MethodSource("getSameUser")
    public void testduplicate(   String nickname, String name, String id, String pwd, String birthDate, String gender, EnrollType enrollType, UserRole role, String phone, String email, String verifyStatus)
    {
        //given
        JoinDTO userInfo = new JoinDTO(
                nickname
                ,name
                ,id
                ,pwd
                ,birthDate
                ,gender
                ,enrollType
                ,role
                ,phone
                ,email
                ,verifyStatus
        );

        //when

        //then
        Assertions.assertNotNull(userInfo);
        Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> userService.join(userInfo)
        );

    }*/


    @DisplayName("로그인 성공 테스트")
    @ParameterizedTest
    @MethodSource("getSameUser")
    public void testLogin(String id, String pwd)
    {
        //given
        LoginDTO userInfo = new LoginDTO(
                id
                ,pwd
        );

        //when

        //then
        Assertions.assertNotNull(userInfo);

    }
}
