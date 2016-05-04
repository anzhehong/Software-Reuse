package reuse.utility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.Key;

import static reuse.utility.AAEncryption.decrypt;
import static reuse.utility.AAEncryption.encrypt;
import static reuse.utility.AAEncryption.showByteArray;

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
        byte[] key = aaEncryption.initSecretKey(testkey);
        System.out.println("key："+ showByteArray(key));
        Key k = aaEncryption.toKey(key);

        String data ="DES数据";
        System.out.println("加密前数据: string:"+data);
        System.out.println("加密前数据: byte[]:"+ showByteArray(data.getBytes()));
        System.out.println();

        byte[] encryptData = encrypt(data.getBytes(), k);
        System.out.println("加密后数据: byte[]:"+showByteArray(encryptData));
        System.out.println("加密后数据: hexStr:"+Hex.encodeHexStr(encryptData));
        System.out.println();

        byte[] decryptData = decrypt(encryptData, k);
        System.out.println("解密后数据: byte[]:"+showByteArray(decryptData));
        System.out.println("解密后数据: string:"+new String(decryptData));
    }
}