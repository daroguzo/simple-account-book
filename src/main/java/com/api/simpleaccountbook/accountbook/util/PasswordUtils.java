package com.api.simpleaccountbook.accountbook.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCrypt;

@UtilityClass
public class PasswordUtils {

    // 비밀번호 암호화 후 리턴

    // 입력된 비밀번호와 암호화된 비밀번호 비교
    public static boolean isEqualPassword(String password, String encryptedPassword) {
        return BCrypt.checkpw(password, encryptedPassword);
    }
}