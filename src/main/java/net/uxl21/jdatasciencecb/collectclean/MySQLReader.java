package net.uxl21.jdatasciencecb.collectclean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.MysqlDataSource;

import net.uxl21.jdatasciencecb.JDSRunnable;

public class MySQLReader extends JDSRunnable {
	
	private String user;
	
	private String pswd;
	
	private String url;
	
	

	public MySQLReader(String[] args) {
		super(args);
		
		this.user = this.configSet.getString("mysqlUser");
		this.pswd = this.configSet.getString("mysqlPswd");
		this.url  = this.configSet.getString("mysqlJdbcUrl");
	}

	@Override
	public void run() {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser(this.user);
		dataSource.setPassword(this.pswd);
		dataSource.setUrl(this.url);
		
		this.logger.debug(String.format("Connect to %s as %s", this.url, this.user));
		
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT * FROM DATA_SCIENCE.BOOKS");
			
			while(rs.next()) {
				this.logger.info(
					String.format(
						"[%d] %s - %s (%sn)", 
						rs.getInt("ID"), rs.getString("BOOK_NAME"), rs.getString("AUTHOR_NAME"), rs.getDate("DATE_CREATED")
					)
				);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				rs.close();
				stmt.close();
				connection.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
