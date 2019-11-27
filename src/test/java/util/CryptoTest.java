package test.util;

import org.junit.Test;
import org.junit.Assert;

import br.com.ucs.subreddits.manipulation.util;

public class CryptoTest {

    public AES obj;

    @Before
    public void initialize() {
        obj.setkey("PERNAMBUCO");
    }

    @Test
    public void testEncryption(){

        Assert.assertEquals(obj.decrypt(obj.encrypt("admin")),obj.encrypt("admin"));

    }

    @Test
    public void testDecryption(){


        Assert.assertEquals("admin",obj.decrypt(obj.encrypt("admin")));

    }
}