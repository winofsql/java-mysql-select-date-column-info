// コメント
import java.sql.*;
import java.text.*;

public class Main {
    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    private static  int maxRows = 20;
    private static String query_string = "select * from 社員マスタ";
    
	// *****************************************************
	// エントリポイント
	// *****************************************************
	public static void main(String[] args) {

		try {
		    // MySQL Connector/J 接続
		    conn = (Connection) DriverManager.getConnection(
			"jdbc:mysql://localhost/lightbox?user=root&password=&characterEncoding=UTF-8"
		    );

		    // ステートメント
		    stmt = (Statement) conn.createStatement();
		    // SQL 実行
		    rs = stmt.executeQuery(query_string);

		    // select の結果の列情報の取得
		    ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();

		    // 列数
		    int columnCount = rsmd.getColumnCount();

		    // 列名
		    for( int i = 1; i <= columnCount; i++) {
			System.out.println(rsmd.getColumnName(i));
		    }

		    // 日付フォーマット
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		    int countRow = 0;
		    while( rs.next() && countRow < maxRows ) {

			countRow++;

			// 全ての列に対するデータの出力
			for( int i = 1; i <= columnCount; i++) {

			    if ( rsmd.getColumnTypeName(i).equals("DATETIME") ) {
				System.out.println(sdf.format(rs.getDate(i)));
			    }
			    else {
			       System.out.println(rs.getString(i));
			    }

			}
		    }

		    rs.close();
		    stmt.close();
		    conn.close();

		}
		catch (SQLException ex) {
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

}
