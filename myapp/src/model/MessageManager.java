package model;

import java.util.List;

import transfer.Message;

import dao.DAOFactory;
import dao.MessageDAO;

public class MessageManager {

	private static MessageManager messageManager = null;
	private MessageDAO messagedao; 
	private MessageManager() {
		DAOFactory dao = DAOFactory.getDAOFactory(DAOFactory.DERBY);
		messagedao = dao.getMessageDAO();
	}
	@Override
	public Object clone() throws CloneNotSupportedException{
		 throw new CloneNotSupportedException();
	}
	public static synchronized MessageManager getInstance() {
		if( messageManager != null) {
			return messageManager;
		}
		else {
			return new MessageManager();
		}
	}
	public int delete(List<Integer> ids) {
		return messagedao.delete(ids);
	}
	public boolean insert(Message message) {
		return messagedao.insert(message);
	}
	public List<Message> getMessages(int page) {
		return messagedao.getMessages(page);
	}
	public int getPageSize() {
		return messagedao.getPageSize();
	}
	public void setPageSize(int pageSize) {
		messagedao.setPageSize(pageSize);
	}
	public int getNoOfRecords() {
		return messagedao.getNoOfRecords();
	}
	public void setMaxChars(int maxChars) {
		messagedao.setMaxChars(maxChars);
	}
	public int getMaxChars() {
		return messagedao.getMaxChars();
	}
}
