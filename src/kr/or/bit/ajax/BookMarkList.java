package kr.or.bit.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.dao.BookMarkDao;
import kr.or.bit.dto.BookMark;
import net.sf.json.JSONArray;


@WebServlet("/BookMarkList")
public class BookMarkList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public BookMarkList() {
        super();
    }
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  request.setCharacterEncoding("UTF-8");
		  response.setContentType("text/html;charset=UTF-8"); //클라언트에게 전달한 페이지의 정보 구성
		  PrintWriter out = response.getWriter();
	  	  String userId = request.getParameter("userId");
	  	  BookMarkDao dao = null;
		try {
			dao = new BookMarkDao();
		  List<BookMark> bmList = dao.getBookMark(userId);
		  System.out.println(bmList);
	  	  JSONArray jsonlist = JSONArray.fromObject(bmList);
	  	  out.print(jsonlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
