package com.mergeco.oiljang.user;

import com.mergeco.oiljang.auth.model.dto.JoinDTO;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.entity.UserProfile;
import com.mergeco.oiljang.user.model.service.UserService;
import com.mergeco.oiljang.user.repository.UserProfileRepository;
import com.mergeco.oiljang.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
/*import org.junit.jupiter.params.provider.Arguments;*/
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
/*
import org.springframework.dao.DataIntegrityViolationException;
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.stream.Stream;


/*
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
*/

@SpringBootTest
@Slf4j
public class UserTests {


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

        UserProfile userProfile = newUser.getUserProfile();
        Assertions.assertNotNull(userProfile);

    }


    @DisplayName("중복확인 테스트")
    @Test
    public void testduplicateId() {

        //given
        String id1 = "newuser20"; //중복일 경우
        String id2 = "user1011110"; //중복이 아닐 경우

        //when
        boolean result1 = userService.checkUserIdExist(id1); //중복일 경우
        System.out.println(result1);
        boolean result2 = userService.checkUserIdExist(id2); //중복이 아닐 경우
        System.out.println(result2);

        //then
        Assertions.assertFalse(result1);
        Assertions.assertTrue(result2);

    }




    @DisplayName("JWT 로그인 테스트")
    @Test
    public void testLogin() throws IOException {
        //given
        String id = "newuser";
        String pwd = "newpassword";




    }



}
