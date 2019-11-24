package kr.or.bit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.jsp.jstl.sql.Result;
import javax.sql.DataSource;

import kr.or.bit.dto.REAUser;
import kr.or.bit.dto.Sale;
import kr.or.bit.dto.SaleImage;
import kr.or.bit.utils.DB_Close;

public class SaleDao {

	DataSource ds = null;

	public SaleDao() throws NamingException {
		Context context = new InitialContext();
		ds = (DataSource) context.lookup("java:comp/env/jdbc/oracle");
	}

	public int insertSale(Sale sale) { // 매물 넣기
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		int resultRow = 0;
		try {
			conn = ds.getConnection();
			
			//매물 테이블에 객체 넣기
//			String sql = "SELECT aptnum INTO salecopy FROM sale";
//			pstmt=conn.prepareStatement(sql);
//			rs=pstmt.executeQuery();
//			String maxNum=rs.getString("maxNum");
//			System.out.println("maxNum: 맥스넘~ " + maxNum);
			
			
			
			String sql_insert_sale = "insert into sale(aptNum, aptSize, type, addr, aptName, aptDong, aptHo, price, direction, etc, isContract,reaId)" + 
								" values(seq_aptNum.nextval, ?,(select type from type where type=?),?,?,?,?,?,?,?,?,(select reaid from reauser where reaid=?))";
			pstmt = conn.prepareStatement(sql_insert_sale);
			pstmt.setString(1, sale.getAptSize());
			pstmt.setString(2, sale.getType());
			pstmt.setString(3, sale.getAddr());
			pstmt.setString(4, sale.getAptName());
			pstmt.setString(5, sale.getAptDong());
			pstmt.setString(6, sale.getAptHo());
			pstmt.setInt(7, sale.getPrice());
			pstmt.setString(8, sale.getDirection());
			pstmt.setString(9, sale.getEtc());
			pstmt.setString(10, sale.getIsContract());
			pstmt.setString(11, sale.getId());
			resultRow = pstmt.executeUpdate();
			
			
			if(resultRow>0) {
				System.out.println("매물 정보 DB 등록 성공");
			}
		}catch(Exception e) {
			System.out.println("e.getMessage(): " + e.getMessage());
		}finally {
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultRow;
	}
	
//	public int insertImg(SaleImage saleImage) { // 매물 이미지 넣기
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		int resultRow = 0;
//		try {
//			conn = ds.getConnection();
//			conn.setAutoCommit(false); //트랜잭션 처리
//			
//			//매물 테이블에 객체 넣기
//			
//			//1. 매물 테이블에서 매물 번호 가져오기 
//			String sql_max_aptNum = "select max(aptnum) as maxNum from sale";
//			pstmt = conn.prepareStatement(sql_max_aptNum);
//			
//			
//			rs = pstmt.executeQuery();
//			if(rs.next()) {
//				String aptMax=rs.getString("maxNum");
//				System.out.println(aptMax);
//			} else {
//				System.out.println("rs가 없어욤...");
//			}
//			
//			System.out.println("resultRow: " + resultRow);
//			if(resultRow>1) {
//				conn.commit();
//			}
//		}catch(Exception e) {
//			System.out.println(e.getMessage());
//		}
//		return resultRow;
//	}

	public Sale getSaleDataByAptNum(String aptNum) { // 매물 한개 읽기 (매물 번호로)
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Sale sale = null;
		try {
			conn = ds.getConnection();
			String sql_select_aptInfo = "select aptnum, aptsize, type, addr, aptname, aptdong, aptho, price, direction, etc, iscontract, reaid from sale"
					+" where aptnum=?";
			pstmt = conn.prepareStatement(sql_select_aptInfo);
			pstmt.setString(1, aptNum);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				sale = new Sale();
				sale.setAptNum(rs.getString("aptNum"));
				sale.setAptSize(rs.getString("aptSize"));
				sale.setType(rs.getString("type"));
				sale.setAddr(rs.getString("addr"));
				sale.setAptName(rs.getString("aptName"));
				sale.setAptDong(rs.getString("aptDong"));
				sale.setAptHo(rs.getString("aptHo"));
				sale.setPrice(Integer.parseInt(rs.getString("price")));
				sale.setDirection(rs.getString("direction"));
				sale.setEtc(rs.getString("etc"));
				sale.setIsContract(rs.getString("isContract"));
				sale.setId(rs.getString("reaId"));

			}
			

		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return sale;
	}
	
	public String getAptNumByAptInfo(String aptName, String aptDong, String aptHo) { // 매물 번호 읽기 (아파트 이름, 동, 호수로)
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String aptNum = "";
		try {
			conn = ds.getConnection();
			String sql_select_aptInfo = "select aptnum from sale where aptname=? and aptdong=? and aptho=?";
			pstmt = conn.prepareStatement(sql_select_aptInfo);
			pstmt.setString(1, aptName);
			pstmt.setString(2, aptDong);
			pstmt.setString(3, aptHo);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				aptNum = rs.getString("aptNum");
			}
			System.out.println(aptNum);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return aptNum;
	}

	public List<Sale> getSaleList(String id) { // 매물 리스트 출력(공인중개사 아이디로)
		return null;
	}

	public int updateSale(Sale sale) { // 매물 수정
		return 0;
	}

	public int deleteSale(String aptNum) { // 매물 삭제(매물 번호로)
		return 0;
	}
	
	public ArrayList<Sale> selectAtpList(String addr) { // 주소로 아파트 이름, 아파트 동, 아파트 가격 조회(매물 보는 첫 페이지)
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Sale> saleList = null;
		String sql_select_aptList = "select aptname, aptdong, price, aptNum, aptSize from sale where addr=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql_select_aptList);
			pstmt.setString(1, addr);
			rs = pstmt.executeQuery();
			saleList = new ArrayList<Sale>();

			while (rs.next()) {
				Sale sale = new Sale();
				sale.setAptName(rs.getString("aptName")); // 아파트 이름
				sale.setAptDong(rs.getString("aptDong")); // 아파트 동
				sale.setPrice(rs.getInt("price")); //가격
				sale.setAptNum(rs.getString("aptNum")); //매물번호
				sale.setAptSize(rs.getString("aptSize")); //아파트 사이즈

				saleList.add(sale);
			}
		System.out.println(saleList);	
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return saleList;

	}
	
	
	public Sale getSaleDetail(String aptNum) { // 아파트 매물 번호로 매물 정보 가져오기 (SaleDetail 페이지)
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Sale sale = null;
		String sql_get_saleDetail = "select aptName,aptSize,Type,aptDong,price,Direction,etc, reaId from sale where aptNum=?";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql_get_saleDetail);
			pstmt.setString(1, aptNum);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				sale = new Sale();
				sale.setAptName(rs.getString("aptName")); //아파트 이름
				sale.setAptSize(rs.getString("aptSize")); //아파트 면적
				sale.setType(rs.getString("type")); //매물 유형
				sale.setAptDong(rs.getString("aptDong")); // 아파트 동
				sale.setPrice(rs.getInt("price")); //매매가격
				sale.setDirection(rs.getString("direction")); //아파트 향
				sale.setEtc(rs.getString("etc")); //특징
				sale.setId(rs.getString("reaId")); //공인중개사 아이디
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB_Close.close(rs);
			DB_Close.close(pstmt);
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sale;
	}

}
