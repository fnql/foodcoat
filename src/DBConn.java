import java.sql.Connection;
import java.sql.DriverManager;

public class DBConn {
	public static Connection dbConnection() {
		Connection conn;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookex?serverTimezone = UTC", "root", "1234");
			System.out.println("DB 연결 완료");
			return conn;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("DB 연결 에러~~~~~");
			return null;
		}
	}

}