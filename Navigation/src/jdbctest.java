import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static java.lang.Class.forName;

/**
 * ClassName: jdbctest
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author 南极星
 * @Create 2024/6/13 14:01
 * Version 1.0
 */
public class jdbctest {
    public static void main(String[] args) throws Exception {
        String url="jdbc:mysql:///dbtest2";
        String username="root";
        String password="abc123";
        Connection coon=DriverManager.getConnection(url,username,password);
        Statement statement=coon.createStatement();
        String sql="insert into emp values(null,'哈哈哈')";
        statement.executeUpdate(sql);
        statement.close();
        coon.close();

    }

}
