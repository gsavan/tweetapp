package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MessageManager;

import transfer.Message;

import dao.DAOFactory;
import dao.MessageDAO;

/**
 * Servlet implementation class MessageServlet
 */
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MessageServlet() {
        	super();
	}
	private MessageManager messageManager = null;
	private int pagesize;
	@Override
	public void init(){
		messageManager= MessageManager.getInstance();
		getServletContext().setAttribute("messageManager", messageManager);
		pagesize = 20;
		messageManager.setPageSize(pagesize);
		messageManager.setMaxChars(150);
		//System.out.println("From Message"+messageManager.getMaxChars());
	}
	public void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		/*for(int i=0;i<30;++i){
			Message message = new Message();
			message.setMessage(i+"SomeTextMessage");
			messagedao.insert(message);
		}*/
		int page = 1;
		int recordsPerPage = messageManager.getPageSize();
		if(request.getParameter("page") != null)
			page = Integer.parseInt(request.getParameter("page"));
		if(request.getParameter("pageSize") != null) {
		int pageSize;
			try {
				pageSize = Integer.parseInt(request.getParameter("pageSize"));
				if( pageSize >= 1)
					messageManager.setPageSize(pageSize);
			}catch(NumberFormatException nfex) {
				//No action	
			}
		}
		List<Message> list = messageManager.getMessages(page);
		int noOfRecords = messageManager.getNoOfRecords();
		int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		int maxChars = messageManager.getMaxChars();
		request.setAttribute("messageList", list);
		request.setAttribute("noOfPages", noOfPages);
		request.setAttribute("currentPage", page);
		request.setAttribute("pageSize", messageManager.getPageSize());
		request.setAttribute("maxChars", maxChars);
		RequestDispatcher view = request.getRequestDispatcher("displayMessages.jsp");
		view.forward(request, response);
	}
}