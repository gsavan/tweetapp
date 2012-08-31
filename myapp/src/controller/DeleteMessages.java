package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MessageManager;

import dao.DAOFactory;
import dao.MessageDAO;

/**
 * Servlet implementation class DeleteMessages
 */
public class DeleteMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteMessages() {
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
		String[] ints = request.getParameterValues("deletes");
		if( ints != null) {
 		List<Integer> list = new ArrayList<Integer>();
		for( int i=0; i < ints.length; ++i) {
			list.add(Integer.parseInt(ints[i]));
		}
		messageManager.delete(list);
		}
		RequestDispatcher view = request.getRequestDispatcher("message.do");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
