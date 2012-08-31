package dao;

public abstract class DAOFactory {
	public static final int DERBY = 1;
public abstract MessageDAO getMessageDAO();
private static DerbyDAOFactory derbydaofactory = new DerbyDAOFactory();
public static DAOFactory getDAOFactory(int whichFactory) {
	  
	    switch (whichFactory) {
	      case DERBY: 
	          return derbydaofactory;
	      default: 
	          return null;
	    }
}
}
