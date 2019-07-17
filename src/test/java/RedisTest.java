import com.gzhc365.utils.RedisUtil;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 19:29 2019/7/17 0017
 * @Modified by:
 */
public class RedisTest {

    public static void main(String[] args) {
        //备份Redis中的list
//        String test = RedisUtil.backupList("test");
//        System.out.println(test);

        //备份Redis中的set
//        String testSet = RedisUtil.backupSet("testSet");
//        System.out.println(testSet);

        //去重Redis中的list
        String test_bakup_1 = RedisUtil.listRemoveDupcate("test");
        System.out.println(test_bakup_1);

    }

}
