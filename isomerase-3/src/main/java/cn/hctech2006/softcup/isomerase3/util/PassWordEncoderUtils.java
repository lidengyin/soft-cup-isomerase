package cn.hctech2006.softcup.isomerase3.util;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PassWordEncoderUtils {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public static String BCryptPassword(String password){
        return encoder.encode(password);
    }
    public static boolean matches(CharSequence rawPassword, String encodedPassword){
        return encoder.matches(rawPassword,encodedPassword);
    }
}
