import java.security.MessageDigest;
import java.util.UUID;

public class GeneratePassword {
    private static final String SALT_FIXED = "expressway_salt_";
    private static final String CHARSET = "UTF-8";

    public static String encrypt(String password) {
        try {
            String salt = SALT_FIXED + UUID.randomUUID().toString().substring(0, 8);
            byte[] inputBytes = (password + salt).getBytes(CHARSET);
            String encryptPwd = md5DigestAsHex(inputBytes);
            return encryptPwd + ":" + salt;
        } catch (Exception e) {
            throw new RuntimeException("密码加密失败", e);
        }
    }

    public static String md5DigestAsHex(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(data);
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("MD5加密失败", e);
        }
    }

    public static void main(String[] args) {
        String password = "123456";
        System.out.println("明文密码: " + password);
        System.out.println("\n加密后的密码（用于数据库存储）:");
        System.out.println("================================");
        
        String encrypted = encrypt(password);
        System.out.println(encrypted);
        System.out.println("================================");
        System.out.println("\nSQL 更新语句：");
        System.out.println("UPDATE user SET password = '" + encrypted + "' WHERE username = 'admin';");
    }
}
