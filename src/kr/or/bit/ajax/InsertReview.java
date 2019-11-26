package kr.or.bit.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.dao.ReviewDao;
import kr.or.bit.dto.Review;
import net.sf.json.JSONArray;

@WebServlet("/InsertReview")
public class InsertReview extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InsertReview() {
        super();
        // TODO Auto-generated constructor stub
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	response.setContentType("text/html;charset=UTF-8");
    	
    	PrintWriter out = response.getWriter();
    	String reaId = request.getParameter("reaId");
    	String userId = request.getParameter("userId");
    	Review review = null;
    	ReviewDao rvdao = null;
    	int result = 0;
    	
    	try {
    		review = new Review();
    		rvdao = new ReviewDao();
    		reaId = review.getReaId();
    		userId = review.getUserId();
    		result = rvdao.insertReview(review);
    		System.out.println("InsertReview ajax reaId: " + reaId);
    		System.out.println("InsertReview ajax userId: " + userId);
    		System.out.println("InsertReview ajax result : " + result);
    		
    		if (result > 0) {
    			out.print(result);
    			System.out.println("InsertReview ajax 성공");
    		}
    		System.out.println("InsertReview ajax 실패");
    	} catch (Exception e) {
    		System.out.println("InsertReview ajax 예외발생");
			System.out.println(e.getMessage());
		}
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

}
