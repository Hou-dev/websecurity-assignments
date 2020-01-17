import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.HashMap;
import java.lang.String;
// BEGIN SOLUTION
// please use only standard libraries
// END SOLUTION

public class HW1 {
    @SuppressWarnings("serial")

    /* Problem 1 */

    static final int alphaIndex = (int) 'A';

    static void Problem1() {
        String cipherText = "Z ENIZNMN GWBG KA. RO ZD B LNLENA RQ B DNHANG RATBOZYBGZRO HBIINK U.W.B.O.G.R.L., PWRDN TRBI ZD GRGBI PRAIK KRLZOBGZRO. GWNZA UIBO ZD GR BHXJZAN B DJUNAPNBURO BOK GR WRIK GWN PRAIK ABODRL. Z QNBA GWBG PN KR ORG WBMN LJHW GZLN JOGZI GWNV DJHHNNK. Z WBMN ZOGNAHNUGNK GPR NOHAVUGNK LNDDBTND (HZUWNA_B.GSG BOK HZUWNA_E.GSG) GWBG PNAN DNOG EV KA. RO GR WZD HRODUZABGRA, LA. EIRPQZNIK. Z LBOBTNK GR KZDHRMNA GWN UIBZOGNSG RQ GWN QZADG LNDDBTN:\n\"LA. EIRPQZNIK, LV BDDRHZBGN PZII KNIZMNA GWN UBVLNOG ONSG QAZKBV BG ORRO. Z NSUNHG VRJ GR KNIZMNA GWN UIBOD QRA GWN DJUNAPNBURO ZO NSHWBOTN. VRJ DWRJIK NOHAVUG GWNL PZGW ENOKNAHZUWNA GR UANMNOG BOVRON QARL DGNBIZOT GWNL. Z PZII DUNHZQV GWN IRHBGZRO QRA GWN NSHWBOTN ZO LV ONSG LNDDBTN. KR ORG KBAN GR QBZI LN.\"\nZ ENIZNMN GWBG GWN DNHROK LNDDBTN PBD NOHAVUGNK JDZOT GWN DBLN FNV, EJG GWN NOHAVUGZRO IRRFD UNAQNHG, BOK Z PBD ORG BEIN GR EANBF ZG.\nUINBDN DNOK ANZOQRAHNLNOGD ZLLNKZBGNIV! Z GAZNK GR BHG HBJGZRJDIV, EJG Z WBMN B QNNIZOT GWBG KA. RO'D WNOHWLNO BAN ROGR LN. Z KRO'G FORP WRP IROT Z WBMN ENQRAN GWNV KZDHRMNA LV ZKNOGZGV BOK LV DNHANG WZKZOT";
        // BEGIN SOLUTION
        System.out.print("PROBLEM #1");
        System.out.println();
        String cipherTextno = cipherText.replaceAll("[.,:!_'()\"\"]", "");
        HashMap<Character, Integer> wordMapper = new HashMap<Character, Integer>();
        for (int i = 0; i < cipherTextno.length(); i++) {
            if (cipherTextno.charAt(i) != ' ') {
                char letter = cipherTextno.charAt(i);
                Integer count = wordMapper.get(letter);
                if (count != null) {
                    wordMapper.put(letter, count + 1);
                } else {
                    wordMapper.put(letter, 1);
                }
            }
        }
        System.out.print(wordMapper);
        System.out.println();
        for (char cipherChar : cipherText.toCharArray())
            if (Character.isLetter(cipherChar)) { // only letters are encrypted, punctuation marks and whitespace are not
                // following line converts letters to numbers between 0 and 25
                int cipher = (int) cipherChar - alphaIndex;
                int plain = Math.floorMod(cipher, 26); // decrypt
                int k1 = ((13-6)*19) %26;
                int k2 = (13-4)*k1 %26;
                //cipher = Math.floorMod(plain * k1 + k2, 26);
                int nplaintext = Math.floorMod(9*(cipher - k2),26);
                char nplainchar = (char) (nplaintext + alphaIndex);
                // following line coverts numbers between 0 and 25 to letters
                char plainChar = (char) (plain + alphaIndex);
                System.out.print(nplainchar);
            } else
                System.out.print(cipherChar);
        // END SOLUTION
        System.out.println();

    }

    /* Problem 2 */

    static void Problem2() throws IOException {
        byte[] cipherTextA = Files.readAllBytes(Paths.get("cipher_a.txt"));
        byte[] cipherTextB = Files.readAllBytes(Paths.get("cipher_b.txt"));
        // BEGIN SOLUTION
        System.out.println();
        System.out.print("PROBLEM #2");
        System.out.println();
        String plainTextAstring = "MR. BLOWFIELD, MY ASSOCIATE WILL DELIVER THE PAYMENT NEXT FRIDAY AT NOON. I EXPECT YOU TO DELIVER THE PLANS FOR THE SUPERWEAPON IN EXCHANGE. YOU SHOULD ENCRYPT THEM WITH BENDERCIPHER TO PREVENT ANYONE FROM STEALING THEM. I WILL SPECIFY THE LOCATION FOR THE EXCHANGE IN MY NEXT MESSAGE. DO NOT DARE TO FAIL ME."; // complete using Problem 1
        byte[] plainTextA = plainTextAstring.getBytes();
        byte[] plainTextB = new byte[cipherTextB.length];
        for(int i =0; i<cipherTextB.length; i++){
            byte xor = (byte)(plainTextA[i]^cipherTextA[i]);
            byte xor2 = (byte)(cipherTextB[i]^xor);
            char c = (char)(xor2 & 0xFF);
            System.out.print(c);
        }
        // END SOLUTION
        System.out.println(new String(plainTextB));
    }

    /* Problem 3 */

    public static byte[] benderDecrypt(int key, byte[] cipherText) {
        byte[] p = new byte[cipherText.length];
        byte r = (byte) key;
        for (int i = 0; i < p.length; i++) {
            r = (byte) (r * key);
            p[i] = (byte) (cipherText[i] ^ r);
        }
        return p;
    }

    public static boolean isEnglishText(byte[] bytes) {
        String punctuations = ".,'-\"";
        for (char chr : new String(bytes).toCharArray())
            if (!(Character.isLetterOrDigit(chr) || Character.isWhitespace(chr) || punctuations.contains(String.valueOf(chr))))
                return false;
        return true;
    }

    static void Problem3() throws IOException {
        byte[] cipherText3 = Files.readAllBytes(Paths.get("cipher3.txt"));
        System.out.println();
        System.out.println("PROBLEM #3");
        // BEGIN SOLUTION
        int key = 129;
        byte[] plainText3 = benderDecrypt(key, cipherText3);
        //this is the function used to find the key from the hints given in question two.
        /*do{
            byte[] plainText3 = benderDecrypt(key, cipherText3);
            String plainText3String = new String(plainText3);
            System.out.println("Key Number:" + key);
            System.out.println(plainText3String);
            key++;
        }while(key<256);*/

        // END SOLUTION
        String plainText3String = new String(plainText3);
        System.out.println(plainText3String);
    }

    public static void main(String[] args) throws IOException {
        Problem1();
        Problem2();
        Problem3();
    }
}
