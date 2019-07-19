import com.gzhc365.utils.JDBCUtilsDruid;
import com.gzhc365.utils.MysqlUtil;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 上午 9:46 2019/7/19 0019
 * @Modified by:
 */
public class GetPrimaryKeyTest {

    public static void main(String[] args) {

        QueryRunner qr = new QueryRunner(JDBCUtilsDruid.getDataSource());
        String sql = "insert into primarykey(name,score) values(?,?);";
        Object[] params = {97,98};
        try {
            qr.update(sql,params);
            long primarykey = MysqlUtil.getInsertPrimaryKey("primarykey");
            System.out.println(primarykey);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}
