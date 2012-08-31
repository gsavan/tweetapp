package dao;
import java.util.List;

import transfer.Message;


public interface MessageDAO {

public boolean insert(Message message);
public boolean delete(Message message);
public int delete(List<Integer> ids);
public boolean setPageSize(int count);
public int getPageSize(); 
public int getNoOfRecords();
public int getMaxChars();
public void setMaxChars(int maxChars);
List<Message> getMessages(int page);
List<Message> getMessages(int from, int to);
}
