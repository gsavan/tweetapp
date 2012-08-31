package dao;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.derby.jdbc.EmbeddedDataSource40;

import transfer.Message;


public class DerbyMessageDAO implements MessageDAO {

	DataSource ds;
	private static DerbyMessageDAO derbydao = null;
	private String dbname = "tweetapp";
	private boolean create = true;
	private Connection conn = null;
	private String tablename = "message";
	private int pageSize;
	private int maxChars;
	
	@Override
	public int getMaxChars() {
		return this.maxChars;
	}

	@Override
	public void setMaxChars(int maxChars) {
		this.maxChars = maxChars;
	}

	public static synchronized DerbyMessageDAO getInstance() {
		if ( derbydao != null ) {
			return derbydao;
		}
		else {
			return new DerbyMessageDAO();
		}
	}
	
	public static synchronized DerbyMessageDAO getInstance(String dbname, boolean create) {
		if( derbydao != null )  {
			return derbydao;
		}
		else {
			return new DerbyMessageDAO(dbname, create);
		}
	}
	public Object clone() throws CloneNotSupportedException{
		 throw new CloneNotSupportedException();
	}
	private DerbyMessageDAO() {
		this("tweetapp", true);
	}
	
	private DerbyMessageDAO(String dbname, boolean create) {
		String createtable = "create table "+tablename
				+"(id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
				+" uname varchar(40) DEFAULT 'ANNONYMOUS', message varchar(150), "
				+" timestamp TIMESTAMP)";
		this.dbname = dbname;
		this.create = create;
		this.pageSize = 10;
		ds = makeDataSource(dbname, create);
		
		Statement stmt = null;
		try{
		conn = ds.getConnection();
		stmt = conn.createStatement();
		//stmt.execute("drop table message");
		DatabaseMetaData dbm = conn.getMetaData();
		ResultSet tables = dbm.getTables(null, null, tablename.toUpperCase() , null);
		
		
		if( tables.next()) {
			System.out.println("table already exists");
		}else {
			stmt.execute(createtable);
			System.out.println("table now created");
			//stmt.execute("drop table message");
		}
		
		
		}catch(Exception ex) { 
			ex.printStackTrace();
		}finally {
			try {
				if( stmt != null ) {
					stmt.close();
					stmt = null;
				}	
			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		System.out.println("derby message dao creaed."+conn );
	}
	
	private DataSource makeDataSource (String dbname, boolean create)
	        
	 { 
		EmbeddedDataSource40 ds = null;
		try{
	        ds = new EmbeddedDataSource40();
	        ds.setDatabaseName(dbname);

	        if (create)
	                ds.setCreateDatabase("create");

		}catch(Exception ex) {
				ex.printStackTrace();
		}
	   
	        return ds;
	 }
	@Override
	public boolean insert(Message message) {
		Statement stmt = null;
		
		String insertinto = null;
		if(message.getUname() != null )
			insertinto = "insert into "+ tablename 
				+" (uname, message, timestamp)"
				+" VALUES ('"+message.getUname()+"','"+message.getMessage()+"','"+new Timestamp(System.currentTimeMillis())+"')";
		else
			insertinto = "insert into "+ tablename 
			+" (message, timestamp)"
			+" VALUES ('"+message.getMessage()+"','"+new Timestamp(System.currentTimeMillis())+"')";
	
		try {
			stmt = conn.createStatement();
			if(stmt.executeUpdate(insertinto) != 0)
				return true;
		}catch(Exception ex) {
			ex.printStackTrace();			
		}finally {
			try {
			if( stmt != null) {
				stmt.close();
				stmt = null;
			}
			}catch(SQLException sqlex) {
				sqlex.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean delete(Message message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int delete(List<Integer> ids) {
		String ints = ids.toString().replace('[', '(').replace(']', ')');
		String sql = "delete from "+tablename
				+" where id in "+ints;
		int count = 0;
		try{
			Statement stmt = conn.createStatement();
			count = stmt.executeUpdate(sql);
			stmt.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}
	@Override
	public boolean setPageSize(int count) {
		this.pageSize = count;
		return true;
	}
	@Override
	public int getPageSize() {
		return pageSize;
	}
	@Override
	public List<Message> getMessages(int page) {
		page = page - 1;
		return getMessages(page*getPageSize(), getPageSize());
	}

	@Override
	public List<Message> getMessages(int from, int count) {
			
		List<Message> list = new ArrayList<Message>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from "+tablename
					+" order by id desc"
					+" offset "+from+" ROWS"
					+" fetch next "+count+" ROWS ONLY");
			while( rs.next() ) {
				Message message = new Message();
				message.setId(rs.getInt("id"));
				message.setUname(rs.getString("uname"));
				
				message.setMessage(rs.getString("message"));
				message.setTimestamp(rs.getTimestamp("timestamp"));
				list.add(message);
				/*System.out.println(" "+ rs.getInt("id")
						+", "+rs.getString("uname")
						+", "+rs.getString("message")
						+", "+rs.getTimestamp("timestamp"));*/
			}
			rs.close();
			stmt.close();
		}catch(Exception ex) {
				ex.printStackTrace();
			}
		return list;
	}
	@Override
	public int getNoOfRecords() {
		int count = 0 ;
		try {
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery("SELECT COUNT(*) FROM "+tablename);
		  while (res.next()){
		  count = res.getInt(1);
		  }
		  res.close();
		  stmt.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}
}
