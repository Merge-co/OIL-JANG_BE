package com.mergeco.oiljang.user;

import com.mergeco.oiljang.auth.model.dto.JoinDTO;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.entity.UserProfile;
import com.mergeco.oiljang.user.model.dto.UpdateUserDTO;
import com.mergeco.oiljang.user.model.dto.UserDTO;
import com.mergeco.oiljang.user.model.service.UserService;
import com.mergeco.oiljang.user.repository.UserProfileRepository;
import com.mergeco.oiljang.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.stream.Stream;

@SpringBootTest
@Slf4j
public class UserTests {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserService userService;



    private static Stream<Arguments> joinData() throws IOException {
        JoinDTO joinDTO = new JoinDTO();
        joinDTO.setNickname("테스형4");
        joinDTO.setName("테스형4");
        joinDTO.setId("test04");
        joinDTO.setPwd("test04");
        joinDTO.setBirthDate("2000-01-03");
        joinDTO.setGender("남");
        joinDTO.setPhone("1254151342");
        joinDTO.setEmail("test3@naver.com");


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

    @DisplayName("아이디 중복 확인 테스트")
    @Test
    public void testduplicateId() {

        //given
        String id1 = "user01"; //중복일 경우
        String id2 = "user1011110"; //중복이 아닐 경우

        //when
        boolean result1 = userService.checkUserIdExist(id1); //중복일 경우
        boolean result2 = userService.checkUserIdExist(id2); //중복이 아닐 경우

        //then
        Assertions.assertFalse(result1);
        Assertions.assertTrue(result2);

    }

    @DisplayName("닉네임 중복 확인 테스트")
    @Test
    public void testduplicateNickname() {

        //given
        String nickname1 = "모짜르트"; //중복일 경우
        String nickname2 = "테스트01"; //중복이 아닐 경우

        //when
        boolean result1 = userService.checkUserNicknameExist(nickname1); //중복일 경우
        System.out.println(result1);
        boolean result2 = userService.checkUserNicknameExist(nickname2); //중복이 아닐 경우
        System.out.println(result2);

        //then
        Assertions.assertFalse(result1);
        Assertions.assertTrue(result2);

    }


    @DisplayName("나의 회원 정보 조회 테스트")
    @Test
    public void testMyInfo(){

        //give
        int userCode = 1;

        //when
        UserDTO userDTO = userService.getUserInfo(userCode);

        //then
        Assertions.assertNotNull(userDTO);
        Assertions.assertEquals(userCode, userDTO.getUserCode());

        Assertions.assertNotNull(userDTO.getUserImageOriginName());
        Assertions.assertNotNull(userDTO.getUserImageName());
        Assertions.assertNotNull(userDTO.getUserImageOriginAddr());
        Assertions.assertNotNull(userDTO.getUserImageThumbAddr());
    }

    @DisplayName("나의 회원 정보 수정 성공 테스트(닉네임 및 프로필 이미지 변경)")
    @Test
    public void testUpdateMyInfo() throws IOException{

        //given
        int userCode = 7;

        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setNickname("수정 테스트 완료");

        String imagePath = "C:\\Users\\User\\Desktop\\dir\\upload\\lion.jpg";
        File imageFile = new File(imagePath);

        FileInputStream fileInputStream = new FileInputStream(imageFile);
        MultipartFile profileImage = new MockMultipartFile("file", imageFile.getName(),"image/*",fileInputStream);


        //when
        UserDTO updatedUserDTO = userService.updateUser(userCode, updateUserDTO,profileImage);

        //then
        Assertions.assertNotNull(updatedUserDTO);
        Assertions.assertEquals(userCode, updatedUserDTO.getUserCode());
        Assertions.assertEquals(updateUserDTO.getNickname(), updatedUserDTO.getNickname());

        Assertions.assertNotNull(updatedUserDTO.getUserImageOriginName());
        Assertions.assertNotNull(updatedUserDTO.getUserImageName());
        Assertions.assertNotNull(updatedUserDTO.getUserImageOriginAddr());
        Assertions.assertNotNull(updatedUserDTO.getUserImageThumbAddr());

    }

    @DisplayName("나의 회원 정보 수정 성공 테스트(비밀번호 변경)")
    @Test
    public void testUpdateUserPassword() throws IOException {

        //given
        int userCode = 7;

        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setNewPassword("test01");
        updateUserDTO.setNewPasswordConfirm("test01");

        MultipartFile profileImage = null;

        //when
        UserDTO updatedUserDTO = userService.updateUser(userCode, updateUserDTO,profileImage);

        //then
        Assertions.assertNotNull(updatedUserDTO);
        Assertions.assertEquals(userCode, updatedUserDTO.getUserCode());

    }

    @DisplayName("회원 탈퇴 상태값으로 변경 테스트")
    @Test
public void testWithdrawUser(){

        //given
        int userCode = 13;

        //when
        userService.withdrawUser(userCode);

        //then
        UserDTO withdrawnUser = UserDTO.fromEntity(userRepository.findById(userCode).orElse(null));
        Assertions.assertNotNull(withdrawnUser);
        Assertions.assertEquals("Y", withdrawnUser.getWithdrawStatus().toUpperCase());
    }



}
