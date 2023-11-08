package com.mergeco.oiljang.user;

import com.mergeco.oiljang.auth.model.dto.JoinDTO;
import com.mergeco.oiljang.auth.model.dto.LoginDTO;
import com.mergeco.oiljang.common.UserRole;
import com.mergeco.oiljang.user.entity.EnrollType;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.entity.UserProfile;
import com.mergeco.oiljang.user.model.dto.UserProfileDTO;
import com.mergeco.oiljang.user.model.service.UserService;
import com.mergeco.oiljang.user.repository.UserProfileRepository;
import com.mergeco.oiljang.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
/*import org.junit.jupiter.params.provider.Arguments;*/
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
/*
import org.springframework.dao.DataIntegrityViolationException;
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;


/*
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
*/

@SpringBootTest
public class UserTests {

    private final Logger LOGGER = LoggerFactory.getLogger(UserTests.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserService userService;


  /*  @AfterEach
    public void afterEach(){
        userRepository.clearStore();
    }*/



    @DisplayName("회원가입 성공 테스트")
    @Test
    public void testJoin() throws IOException {
        //given
        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setNickname("newuser1");
        joinDTO.setName("New User");
        joinDTO.setId("newuser1");
        joinDTO.setPwd("newpassword");
        joinDTO.setBirthDate("2000-01-03");
        joinDTO.setGender("Male");
        joinDTO.setPhone("11111421414");
        joinDTO.setEmail("newuser@example.com");
        joinDTO.setProfileImageUrl("newuser_image.jpg");

        UserProfileDTO profileDTO = new UserProfileDTO();
        profileDTO.setUserImageOriginName("newuser_image.jpg");

        File imageFile = new File("C:\\Users\\User\\Desktop\\dir\\upload\\image.jpg");
        FileInputStream fileInputStream = new FileInputStream(imageFile);
        MultipartFile imageMultipartFile = new MockMultipartFile("file", imageFile.getName(), "image/jpeg", fileInputStream);

        //when
        User newUser = userService.join(joinDTO, profileDTO, imageMultipartFile);


        // Then
        User savedUser = userRepository.findById("newuser").orElse(null);
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals("newuser", savedUser.getId());

        // Verify the user profile
        UserProfile userProfile = savedUser.getUserProfile();
        Assertions.assertNotNull(userProfile);
        Assertions.assertEquals("newuser_image.jpg", userProfile.getUserImageOriginName());

    }


    @DisplayName("중복확인 테스트")
    @Test
    public void testduplicate() {

        //given
        String id = "duplicateId";
        String nickname = "uniqueNickname";

        //when

        //then


    }




    @DisplayName("JWT 로그인 테스트")
    @Test
    public void testLogin() throws IOException {
        //given
        String id = "newuser";
        String pwd = "newpassword";










    }



}
