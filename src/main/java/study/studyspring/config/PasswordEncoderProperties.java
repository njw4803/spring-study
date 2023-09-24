package study.studyspring.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 비밀번호 암호화 방식 설정
 * 필요한 암호화 방식으로 커스텀마이징
*/
public class PasswordEncoderProperties {
    public static final String ID = "bcrypt";
    public static final PasswordEncoder ENCODERS = new BCryptPasswordEncoder();

    /*
    public static final String ID = "ldap";
    public static final PasswordEncoder ENCODERS = new org.springframework.security.crypto.password.LdapShaPasswordEncoder();
    */

    /*
    public static final String ID = "MD4";
    public static final PasswordEncoder ENCODERS = new org.springframework.security.crypto.password.Md4PasswordEncoder();
    */

    /*
    public static final String ID = "MD5";
    public static final PasswordEncoder ENCODERS = new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5");
    */

    /*
    public static final String ID = "noop";
    public static final PasswordEncoder ENCODERS = org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    */

    /*
    public static final String ID = "pbkdf2";
    public static final PasswordEncoder ENCODERS = new Pbkdf2PasswordEncoder();
    */

    /*
    public static final String ID = "scrypt";
    public static final PasswordEncoder ENCODERS = new SCryptPasswordEncoder();
    */

    /*
    public static final String ID = "SHA-1";
    public static final PasswordEncoder ENCODERS = new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-1");
    */

    /*
    public static final String ID = "SHA-256";
    public static final PasswordEncoder ENCODERS = new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256");
    */

    /*
    public static final String ID = "sha256";
    public static final PasswordEncoder ENCODERS = new org.springframework.security.crypto.password.StandardPasswordEncoder();
    */

    /*
    String ID = "argon2";
    PasswordEncoder ENCODERS = new Argon2PasswordEncoder();
    */
}
