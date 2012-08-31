package controller;

import java.io.IOException;

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
 * Servlet implementation class InsertMessageController
 */
public class InsertMessageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertMessageController() {
        super();
        // TODO Auto-generated constructor stub
    }
    private MessageManager messageManager = null;
    public void init(){
		messageManager = (MessageManager)getServletContext().getAttribute("messageManager");
		if( messageManager == null ) {
			messageManager = MessageManager.getInstance();
		}
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname, text;
		Message message = new Message();
		if((uname = request.getParameter("uname")) != null) {
			if( uname.length() == 0 )
				message.setUname(null);
			else if( uname.length() <= 40)
				message.setUname(uname);
		}
		//System.out.println("From insert" + messageManager.getMaxChars());
		if( (text = request.getParameter("message")) != null) {
			if( text.length() <= messageManager.getMaxChars())
				message.setMessage(text);
		}
		
		messageManager.insert(message);
		RequestDispatcher view = request.getRequestDispatcher("message.do");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
