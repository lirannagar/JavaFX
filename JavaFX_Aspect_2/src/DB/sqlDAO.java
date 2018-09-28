package DB;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.lang.reflect.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import Listeners.DBactions;
import Objects.DestructedMissile;
import Objects.Launcher;
import Objects.LauncherDestructor;
import Objects.Missile;
import Objects.MissileDestructor;

public class sqlDAO implements DBactions {
	public final static String DB_URL = "jdbc:mysql://localhost/javafx_part2";
	public final static String DB_URL_NEW = "jdbc:mysql://localhost/javafx_proj2";
	public final static String SQL_INSERT_QUERY = "INSERT INTO `JavaFX_Proj2`.";
	public final static String SQL_DELETE_QUERY = "DELETE FROM `JavaFX_Proj2`.";
	private static sqlDAO theSqlDAO; //Singleton design pattern
	private Statement statement;
	private Connection connection;
	
	private sqlDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(DB_URL_NEW, "root", "");
			System.out.println("Database connection established");
			statement = connection.createStatement();
			
			this.deleteMissileLaunchers();
			this.deleteMissileDestructors();
			this.deleteLaunchEvents();
			this.deleteInterceptions();
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
	}
	
	public void createTable(Launcher launcher) {
		try {
			String s = "CREATE TABLE Missile Launchers (ID int NOT NULL AUTO_INCREMENT, PRIMARY KEY(id))";
			statement.executeUpdate(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/** Function that creates launchers table in SQL DB **/
	private String createLaunchersTable(Launcher launcher) {
	String name = "Missile_Launchers"; 	
	   try {
	   System.out.println("Creating table in given database..."); 
	   
		String sql = "CREATE TABLE IF NOT EXISTS " + name +
                " (id VARCHAR(10) not NULL, " +
                " isHidden TINYINT(1), " + 
                " isActive TINYINT(1), " +  
                " PRIMARY KEY ( id ))";
		statement = connection.createStatement();
		statement.executeUpdate(sql);
	   } catch (SQLException se) {
		   se.printStackTrace();
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
	   return name;
	}
	/** Function that creates missile destructors table in SQL DB **/
	private String createMissileDestructorsTable(MissileDestructor md) {
		String name = "Missile_Destructors";
		try {
			   System.out.println("Creating table in given database..."); 
			   
				String sql = "CREATE TABLE IF NOT EXISTS " + name +
		                " (id VARCHAR(10) not NULL, " +
		                " isRunning TINYINT(1), " +   
		                " PRIMARY KEY ( id ))";
				statement = connection.createStatement();
				statement.executeUpdate(sql);
			   } catch (SQLException se) {
				   se.printStackTrace();
			   } catch (Exception e) {
				   e.printStackTrace();
			   }
			   return name;
	}
	/** Function that creates launcher destructors in SQL DB **/
	private String createLauncherDestructorsTable(LauncherDestructor ld) {
		String name = "Launcher_Destructors";
		try {
			   System.out.println("Creating table in given database..."); 
			   
				String sql = "CREATE TABLE IF NOT EXISTS " + name +
		                " (id VARCHAR(10) not NULL, " +
						" Type VARCHAR(5),		" +
		                " isRunning TINYINT(1), " +   
		                " PRIMARY KEY ( id ))";
				statement = connection.createStatement();
				statement.executeUpdate(sql);
			   } catch (SQLException se) {
				   se.printStackTrace();
			   } catch (Exception e) {
				   e.printStackTrace();
			   }
			   return name;
	}
	/** Function that creates launch events table in SQL DB **/
	private String createLaunchEventsTable() {
		String name = "Launch_Events";
		try {
			  // System.out.println("Creating table in given database..."); 
			   
				String sql = "CREATE TABLE IF NOT EXISTS " + name +
		                " (Launcher_id VARCHAR(10) not NULL, " +
						"  Missile_id VARCHAR(10) not NULL,  " +
		                "  Destination VARCHAR(20), " +
		                "  Launch_time INTEGER, " +
		                "  Fly_time INTEGER, " +
		                "  Damage INTEGER, " +
		                "  Hit TINYINT(1), " +
		                " PRIMARY KEY ( Missile_id ))";
				statement = connection.createStatement();
				statement.executeUpdate(sql);
			   } catch (SQLException se) {
				   se.printStackTrace();
			   } catch (Exception e) {
				   e.printStackTrace();
			   }
			   return name;
	}
	/** Function that creates interceptions table in SQL DB **/
	private String createInterceptionsTable() {
		String name = "Interceptions";
		try {
			   System.out.println("Creating Interceptions-table in given database..."); 
			   
				String sql = "CREATE TABLE IF NOT EXISTS " + name +
		                " (Missile_Destructor_id VARCHAR(10) not NULL, " +
						"  Missile_id VARCHAR(10) not NULL, " +
		                "  Time INTEGER,  " +
		                " PRIMARY KEY ( Missile_Destructor_id ))";
				statement = connection.createStatement();
				statement.executeUpdate(sql);
			   } catch (SQLException se) {
				   se.printStackTrace();
			   } catch (Exception e) {
				   e.printStackTrace();
			   }
			   return name;
	}
	
	public void closeConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(DB_URL, "root", "");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public void saveLauncherDetails(Launcher launcher) {
		// TODO Insert a new created missile launcher into DB
		String tableName = createLaunchersTable(launcher);
		try {
			String s = SQL_INSERT_QUERY + tableName + " VALUES('" + launcher.getLId() + "', " + launcher.isHidden() + " , " + launcher.isActive() + ")";
				statement.executeUpdate(s);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	

	@Override
	public void saveMissileDestructorDetails(MissileDestructor missileDestructor) {
		// TODO Auto-generated method stub
		String tableName = createMissileDestructorsTable(missileDestructor);
		try {
			String s = SQL_INSERT_QUERY + tableName + " VALUES('" + missileDestructor.getId() + "', " + missileDestructor.isRunning() + ")";
			statement.executeUpdate(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void saveLauncherDestructorDetails(LauncherDestructor ld) {
		// TODO Auto-generated method stub
		String tableName = createLauncherDestructorsTable(ld);
		try {
			String s = SQL_INSERT_QUERY + tableName + " VALUES('" + ld.getId() + "', '" + ld.getType() + "', " + ld.isRunning() + ")";
			statement.executeUpdate(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveLaunchEvents(Missile m) {
		// TODO Auto-generated method stub
		String tableName = createLaunchEventsTable();
		try {
			System.out.println(m.getDestination());
			String s = SQL_INSERT_QUERY + tableName + " VALUES('" + m.getLauncherId() + "', '" + m.getMId() + "', '" + m.getDestination() + "', " + m.getLaunchTime() + ", " + m.getFlyTime() + ", " + m.getDamage() + ", TRUE)";
			statement.executeUpdate(s);
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		
	}

	/*@Override
	public void saveInterceptions(MissileDestructor md, DestructedMissile m) {
		// TODO Auto-generated method stub
		String tableName = createInterceptionsTable();
		try {
			String s = SQL_INSERT_QUERY + tableName + " VALUE('" + md.getId() + "', '" + m.getTargetId() + "')" ;
			statement.executeUpdate(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/
	
	
	/*public void saveInterceptions(String mdID, Missile m) {
		// TODO Auto-generated method stub
		String tableName = createInterceptionsTable();
		try {
			String s = SQL_INSERT_QUERY + tableName + " VALUE('" + mdID + "', '" + m.getMId() + "')" ;
			statement.executeUpdate(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/
	@Override
	public void saveInterceptions(String mdID, DestructedMissile dm) {
		String tableName = createInterceptionsTable();
		try {
			String s = SQL_INSERT_QUERY + tableName + " VALUE('" + mdID + "', '" + dm.getTargetId() + "', " + dm.getDestructAfterLaunch() + ")" ;
			statement.executeUpdate(s);		
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateLaunchEvent(Missile m) {
		try {
			ResultSet rs = statement.executeQuery("UPDATE `JavaFX_Proj2`.`Launch_Events` SET `Hit` = FALSE WHERE `Missile ID` == m.getMid()");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateLauncher(Missile m) {
		try {
			ResultSet rs = statement.executeQuery("UPDATE `JavaFX_Proj2`.`Launch_Events` SET Launcher_id = '" + m.getLauncherId() + "'");
			System.out.println("DEBUG: " + rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean findMissileDestructor(String MDId) {
		try {
		String s = "SELECT * FROM `JavaFX_Proj2`.`Missile_Destructors` WHERE id = '" + MDId + "'";
		ResultSet rs = statement.executeQuery(s);
		if (rs != null)
			return true;
		return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private void deleteMissileLaunchers() {
		try {
			String s = SQL_DELETE_QUERY + "`Missile_Launchers` ";
			statement.executeUpdate(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void deleteLaunchEvents() {
		try {
			String s = SQL_DELETE_QUERY + "`Launch_Events` ";
			statement.executeUpdate(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void deleteMissileDestructors() {
		try {
			String s = SQL_DELETE_QUERY + "`Missile_Destructors` ";
			statement.executeUpdate(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void deleteInterceptions() {
		try {
			String s = SQL_DELETE_QUERY + "`Interceptions` ";
			statement.executeUpdate(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static sqlDAO getInstance() {
		if (theSqlDAO == null) {
			theSqlDAO = new sqlDAO();
		}
		return theSqlDAO;
	}
}
