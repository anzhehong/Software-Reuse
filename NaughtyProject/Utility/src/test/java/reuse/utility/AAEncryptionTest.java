package reuse.utility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.Key;
import java.util.ArrayList;

import static reuse.utility.AAEncryption.*;

/**
 * Created by fowafolo
 * Date: 16/5/4
 * Time: 16:37
 */
public class AAEncryptionTest {

    private AAEncryption aaEncryption;
    private String testkey = "test";

    @Before
    public void setUp() throws Exception {
        aaEncryption = new AAEncryption();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test() throws Exception {

        byte[] key = aaEncryption.initSecretKey();
        System.out.println("key："+ showByteArray(key));
        Key k = aaEncryption.toKey(key);
        System.out.println("k:" + k);
        String data ="AES数据";
        System.out.println("加密前数据: string:"+data);
        System.out.println("加密前数据: byte[]:"+showByteArray(data.getBytes()));
        System.out.println();
        byte[] encryptData = encrypt(data.getBytes(), k);
        System.out.println(encryptData);
        System.out.println("加密后数据: byte[]:"+showByteArray(encryptData));
        System.out.println("加密后数据: hexStr:"+Hex.encodeHexStr(encryptData));
        System.out.println();
        byte[] decryptData = decrypt(encryptData, k);
        System.out.println("解密后数据: byte[]:"+showByteArray(decryptData));
        System.out.println("解密后数据: string:"+new String(decryptData));

        /**
         * 本次常用方法1
         */
        String originStr = "我是一个测试string";
        ArrayList<Object> result = AAEncryption.DefaultEncryptString(originStr);
        String code = (String) result.get(0);
        Key codeKey = (Key) result.get(1);
        System.out.println("加密后String:" + code);
        System.out.println("解密后String:"+ AAEncryption.DefaultDecodeStringByStrAndKey(code, codeKey));

        /**
         * 本次常用方法2
         */
        Key tempKey = AAEncryption.getRandomKey();
        String oldStr = "试一下新的方法";
        String encriptedStr= AAEncryption.DefaultEncryptStringWithKey(oldStr, tempKey);
        System.out.println("-----");
        System.out.println(encriptedStr);
        System.out.println("-----");
        System.out.println(AAEncryption.DefaultDecodeStringByStrAndKey(encriptedStr, tempKey));

        String oldStr2 = "试一下新的方法2";
        String encriptedStr2= AAEncryption.DefaultEncryptStringWithKey(oldStr2, tempKey);
        System.out.println("-----");
        System.out.println(encriptedStr2);
        System.out.println("-----");
        System.out.println(AAEncryption.DefaultDecodeStringByStrAndKey(encriptedStr2, tempKey));

        System.out.println("Key  "+tempKey);
    }
}