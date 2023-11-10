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
import org.junit.jupiter.params.provider.Arguments;
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
import java.util.stream.Stream;


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




    private static Stream<Arguments> joinData() throws IOException {
        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setNickname("newuser20");
        joinDTO.setName("New User");
        joinDTO.setId("newuser20");
        joinDTO.setPwd("newpassword");
        joinDTO.setBirthDate("2000-01-03");
        joinDTO.setGender("Male");
        joinDTO.setPhone("1151512114");
        joinDTO.setEmail("newuser@example.com");


        File imageFile = new File("C:\\Users\\User\\Desktop\\dir\\upload\\image.jpg");
        FileInputStream fileInputStream = new FileInputStream(imageFile);
        MultipartFile imageMultipartFile = new MockMultipartFile("file", imageFile.getName(), "image/*", fileInputStream);

        return Stream.of(
                Arguments.of(joinDTO,imageMultipartFile));
    }



    @DisplayName("회원가입 성공 테스트")
    @ParameterizedTest
    @MethodSource("joinData")
    public void testJoin(JoinDTO joinDTO,MultipartFile imageMultipartFile) throws IOException {
        //given

        //when
        User newUser = userService.join(joinDTO,imageMultipartFile);


        // Then
        /*User savedUser = userRepository.findById("newUser").orElse(null);
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals("newUser", savedUser.getId());*/

        // Verify the user profile
        UserProfile userProfile = newUser.getUserProfile();
        Assertions.assertNotNull(userProfile);
        /*Assertions.assertEquals("newuser5-original-image.jpg", userProfile.getUserImageOriginName());*/

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
