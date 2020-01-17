import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.BadPaddingException;
import java.security.MessageDigest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
// BEGIN SOLUTION
// please import only standard libraries and make sure that your code compiles and runs without unhandled exceptions
// END SOLUTION
 
public class HW2 {    
  static void P1() throws Exception {
    byte[] cipherBMP = Files.readAllBytes(Paths.get("cipher1.bmp"));
    
    // BEGIN SOLUTION
    byte[] key = new byte[] { 7, 7, 7, 7, 
                             7, 7, 7, 7, 
                             7, 7, 7, 7, 
                             7, 7, 7, 7 };
    byte[] ivkey = new byte[] { 0, 0, 0, 0, 
                              0, 0, 0, 0, 
                              0, 0, 0, 0, 
                              0, 0, 0, 0 };
    SecretKeySpec sp = new SecretKeySpec(key, "AES");
    IvParameterSpec iv = new IvParameterSpec(ivkey);
    Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
    cipher.init(Cipher.DECRYPT_MODE,sp,iv);
    byte[] plainBMP = cipher.doFinal(cipherBMP); 
    // END SOLUTION
    
    Files.write(Paths.get("plain1.bmp"), plainBMP);
  }

  static void P2() throws Exception {
    // BEGIN SOLUTION
    Scanner scanner = new Scanner(new File("messages.txt"));
    MessageDigest mess = MessageDigest.getInstance("MD5");
    byte[] messD = new byte[0];
    int count = 0;
    while (scanner.hasNextLine()) {
      String message = scanner.nextLine();
      mess.update(message.getBytes("UTF-8"));
      messD = mess.digest();
      StringBuffer stringBuffer = new StringBuffer();
      System.out.println(count + " " + Arrays.toString(messD));
      /*for (byte bytes : messD) {
        stringBuffer.append(String.format("%02x", bytes & 0xff));
      }
      System.out.println("digestedMD5(hex):" + stringBuffer.toString());*/
      //System.out.println(count +" "+message);
      count++;
    }

    scanner.close();
    // END SOLUTION
  }

  static void P3() throws Exception {
    byte[] cipherBMP = Files.readAllBytes(Paths.get("cipher3.bmp"));
    
    // BEGIN SOLUTION
    byte[] key = new byte[] { 7, 7, 7, 7,
            7, 7, 7, 7,
            7, 7, 7, 7,
            7, 7, 7, 7 };
    SecretKeySpec sp = new SecretKeySpec(key, "AES");
    Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
    cipher.init(Cipher.DECRYPT_MODE,sp);
    byte[] modifiedBMP = cipher.doFinal(cipherBMP);
    //byte[] modifiedBMP = cipherBMP;
    // END SOLUTION
    
    Files.write(Paths.get("cipher3_modified.bmp"), modifiedBMP);
  }

  static void P4() throws Exception {
    byte[] cipherPNG = Files.readAllBytes(Paths.get("cipher4.png"));
    
    // BEGIN SOLUTION
    byte[] key = new byte[] {   0,   0,    0,   0, 
                                0,   0,    0,   0,
                                0,   0,    0,   0,
                                0,   0,    0,   0 }; 
    byte[] plainPNG = cipherPNG;    
    // END SOLUTION
    
    Files.write(Paths.get("plain4.png"), plainPNG);
  }

  public static void main(String [] args) {
    try {  
      P1();
      P2();
      P3();
      //P4();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
