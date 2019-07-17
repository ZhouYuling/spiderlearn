import com.gzhc365.pre_learn._1_HttpURLConnection;

import java.io.IOException;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 上午 10:53 2019/7/17 0017
 * @Modified by:
 */
public class _1_HttpURLConnectionTest {

    public static void main(String[] args) {

        _1_HttpURLConnection httpURLConnectionTest = new _1_HttpURLConnection();
        try {
            httpURLConnectionTest.getHtmlByGet();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
