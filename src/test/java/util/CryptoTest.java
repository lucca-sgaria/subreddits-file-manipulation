package test.java.util;

import br.com.ucs.subreddits.manipulation.util.AES;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.Date;


public class CryptoTest {


    @Test
    public void testEncryption(){
        AES obj = new AES();
        String str = "senha123";
        String criptografado = obj.encrypt(str, "chave_cripto");
        String decrypt = obj.decrypt(criptografado, "chave_cripto");

        Assert.assertEquals(str,decrypt);
    }

    @Test
    public void testJson(){
        Gson gson = new Gson();
        String json = gson.toJson(null, Date.class);
        Assert.assertEquals("null",json);
    }

}