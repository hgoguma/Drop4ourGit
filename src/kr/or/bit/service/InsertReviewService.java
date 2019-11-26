package kr.or.bit.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.ReviewDao;
import kr.or.bit.dto.Review;

public class InsertReviewService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		
		// 데이터 받기: reviewNum, userId, reviewContent, reviewDate, reaId
		int reviewNum = Integer.parseInt(request.getParameter("reviewNum"));
		String userId = request.getParameter("userId");
		String reviewContent = request.getParameter("reviewContent");
		Date reviewDate = null;
		try {
			reviewDate = (Date) new SimpleDateFormat().parse(request.getParameter("reviewDate"));
		} catch (ParseException e1) {
			System.out.println("InsertReviewService reviewDate 예외발생");
			System.out.println(e1.getMessage());
		}
		String reaId = request.getParameter("reaId");
		System.out.println("InsertReviewService userId : " + userId);
		System.out.println("InsertReviewService reaId : " + reaId);
		
		// 객체에 데이터 저장
		Review review = new Review();
		review.setReviewNum(reviewNum);
		review.setUserId(userId);
		review.setReviewContent(reviewContent);
		review.setReviewDate(reviewDate);
		review.setReaId(reaId);
		
		// 데이터 저장 확인
		System.out.println("Review 객체 데이터 저장 확인: " + review.toString());
		
		try {
			// DB 저장
			ReviewDao rvdao = new ReviewDao();
			int result = rvdao.insertReview(review);
			System.out.println("result: " + result);
			if (result > 0) {
				forward.setPath("InsertReview");
			} else {
				forward.setPath("Main.jsp");
			}
		} catch (Exception e) {
			System.out.println("InsertReviewService 예외발생");
			System.out.println(e.getMessage());
		}
		System.out.println(forward);
		return forward;
	}

}
