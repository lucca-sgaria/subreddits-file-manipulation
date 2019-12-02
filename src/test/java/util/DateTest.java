import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {

    @Test
    public void dateConvert()
    {
        java.util.Date date = new Date("Sat Dec 01 00:00:00 GMT 2012");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String format = formatter.format(date);

        Assert.assertEquals("2012-11-30", format);
    }
}
