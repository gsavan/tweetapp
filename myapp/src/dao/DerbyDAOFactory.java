package dao;

public class DerbyDAOFactory extends DAOFactory {

	MessageDAO messageDAO;
	@Override
	public MessageDAO getMessageDAO() {
		messageDAO = DerbyMessageDAO.getInstance();
		return messageDAO;
	}

}
