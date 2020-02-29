package de.app.subh.utilities;

import org.apache.commons.codec.digest.DigestUtils;
import java.util.UUID;

public class EnryptionHelper {

    public static String generateSalt(){
        return UUID.randomUUID().toString().substring(0, 7);
    }

    public static String hashPwAndSalt(String pw, String salt){
       return DigestUtils.sha512Hex(pw + salt);
    }
}
