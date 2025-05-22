package com.camp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CampDAO implements CampDAO_interface {

	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	// private static DataSource ds = null;
	// static {
	// try {
	// Context ctx = new InitialContext();
	// ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB2");
	// } catch (NamingException e) {
	// e.printStackTrace();
	// }
	// }

	// JDBC
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/lutudb?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "password";

	private static final String INSERT_STMT = "INSERT INTO CAMP (OWNER_ID,CAMP_NAME,CAMP_CONTENT,CAMP_CITY,CAMP_DIST,CAMP_ADDR,CAMP_RELEASE_STATUS,CAMP_PIC1,CAMP_PIC2,CAMP_PIC3,CAMP_PIC4,CAMP_COMMENT_NUMBER_COUNT,CAMP_COMMENT_SUM_SCORE,CAMP_REG_DATE) VALUES (?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_STMT = "UPDATE CAMP SET OWNER_ID=?,CAMP_NAME=?,CAMP_CONTENT=?,CAMP_CITY=?,CAMP_DIST=?,CAMP_ADDR=?,CAMP_RELEASE_STATUS=?,CAMP_PIC1=?,CAMP_PIC2=?,CAMP_PIC3=?,CAMP_PIC4=?,CAMP_COMMENT_NUMBER_COUNT=?,CAMP_COMMENT_SUM_SCORE=?,CAMP_REG_DATE=? WHERE CAMP_ID = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM CAMP";
	// private static final String INSERT_STMT = "SELECT
	// OWNER_ID,CAMP_NAME,CAMP_CONTENT,CAMP_CITY,CAMP_DIST,CAMP_ADDR,CAMP_RELEASE_STATUS,CAMP_PIC1,CAMP_PIC2,CAMP_PIC3,CAMP_PIC4,CAMP_COMMENT_NUMBER_COUNT,CAMP_COMMENT_SUM_SCORE,CAMP_REG_DATE
	// FROM emp2 where empno = ?";
	private static final String DELETE = "DELETE FROM camp where CAMP_ID = ?";
	// private static final String UPDATE = "UPDATE emp2 set ename=?, job=?,
	// hiredate=?, sal=?, comm=?, deptno=? where empno = ?";

	@Override
	public void insert(CampVO campVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, campVO.getOwnerId());
			pstmt.setString(2, campVO.getCampName());
			pstmt.setString(3, campVO.getCampContent());
			pstmt.setString(4, campVO.getCampCity());
			pstmt.setString(5, campVO.getCampDist());
			pstmt.setString(6, campVO.getCampAddr());
			pstmt.setInt(7, campVO.getCampReleaseStatus());
			pstmt.setBytes(8, campVO.getCampPic1());
			pstmt.setBytes(9, campVO.getCampPic2());
			pstmt.setBytes(10, campVO.getCampPic3());
			pstmt.setBytes(11, campVO.getCampPic4());
			pstmt.setInt(12, campVO.getCampCommentNumberCount());
			pstmt.setInt(13, campVO.getCampCommentSumScore());
			pstmt.setDate(14, campVO.getCampRegDate());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update(CampVO campVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setInt(1, campVO.getOwnerId());
			pstmt.setString(2, campVO.getCampName());
			pstmt.setString(3, campVO.getCampContent());
			pstmt.setString(4, campVO.getCampCity());
			pstmt.setString(5, campVO.getCampDist());
			pstmt.setString(6, campVO.getCampAddr());
			pstmt.setInt(7, campVO.getCampReleaseStatus());
			pstmt.setBytes(8, campVO.getCampPic1());
			pstmt.setBytes(9, campVO.getCampPic2());
			pstmt.setBytes(10, campVO.getCampPic3());
			pstmt.setBytes(11, campVO.getCampPic4());
			pstmt.setInt(12, campVO.getCampCommentNumberCount());
			pstmt.setInt(13, campVO.getCampCommentSumScore());
			pstmt.setDate(14, campVO.getCampRegDate());
			pstmt.setInt(1, campVO.getCampId());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(int campId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, campId);
			

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	// @Override
	// public CampVO findByPrimaryKey(Integer campno) {
	// // TODO Auto-generated method stub
	// return null;
	// }

	@Override
	public List<CampVO> getAll() {
		List<CampVO> list = new ArrayList<CampVO>();
		CampVO campVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				campVO = new CampVO();
				campVO.setCampId(rs.getInt("CAMP_ID"));
				campVO.setOwnerId(rs.getInt("OWNER_ID"));
				campVO.setCampName(rs.getString("CAMP_NAME"));
				campVO.setCampCommentNumberCount(rs.getInt("CAMP_COMMENT_NUMBER_COUNT"));
				campVO.setCampCommentSumScore(rs.getInt("CAMP_COMMENT_SUM_SCORE"));
				campVO.setCampRegDate(rs.getDate("CAMP_REG_DATE"));
				campVO.setCampPic1(rs.getBytes("CAMP_PIC1"));
				campVO.setCampPic2(rs.getBytes("CAMP_PIC2"));
				campVO.setCampPic3(rs.getBytes("CAMP_PIC3"));
				campVO.setCampPic4(rs.getBytes("CAMP_PIC4"));
				campVO.setCampContent(rs.getString("CAMP_CONTENT"));
				campVO.setCampCity(rs.getString("CAMP_CITY"));
				campVO.setCampDist(rs.getString("CAMP_DIST"));
				campVO.setCampAddr(rs.getString("CAMP_ADDR"));
				campVO.setCampReleaseStatus(rs.getByte("CAMP_RELEASE_STATUS"));
				list.add(campVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	// @Override
	// public List<CampVO> getAll() {
	// List<CampVO> list = new ArrayList<CampVO>();
	// CampVO campVO = null;

	// Connection con = null;
	// PreparedStatement pstmt = null;
	// ResultSet rs = null;

	// try {

	// // con = ds.getConnection();
	// Class.forName(driver);
	// con = DriverManager.getConnection(url, userid, passwd);
	// pstmt = con.prepareStatement(GET_ALL_STMT);
	// rs = pstmt.executeQuery();

	// while (rs.next()) {
	// // empVO 也稱為 Domain objects
	// campVO = new CampVO();
	// campVO.setCampId(rs.getInt("CAMP_ID"));
	// campVO.setOwnerId(rs.getInt("OWNER_ID"));
	// campVO.setCampName(rs.getString("CAMP_NAME"));
	// campVO.setCampCommentNumberCount(rs.getInt("CAMP_COMMENT_NUMBER_COUNT"));
	// campVO.setCampCommentSumScore(rs.getInt("CAMP_COMMENT_SUM_SCORE"));
	// campVO.setCampRegDate(rs.getDate("CAMP_REG_DATE"));
	// campVO.setCampPic1(rs.getBytes("CAMP_PIC1"));
	// campVO.setCampPic2(rs.getBytes("CAMP_PIC2"));
	// campVO.setCampPic3(rs.getBytes("CAMP_PIC3"));
	// campVO.setCampPic4(rs.getBytes("CAMP_PIC4"));
	// campVO.setCampContent(rs.getString("CAMP_CONTENT"));
	// campVO.setCampCity(rs.getString("CAMP_CITY"));
	// campVO.setCampDist(rs.getString("CAMP_DIST"));
	// campVO.setCampAddr(rs.getString("CAMP_ADDR"));
	// campVO.setCampReleaseStatus(rs.getByte("CAMP_RELEASE_STATUS"));
	// list.add(campVO); // Store the row in the list
	// }

	// // Handle any driver errors
	// } catch (SQLException se) {
	// throw new RuntimeException("A database error occured. "
	// + se.getMessage());
	// // Clean up JDBC resources
	// } finally {
	// if (rs != null) {
	// try {
	// rs.close();
	// } catch (SQLException se) {
	// se.printStackTrace(System.err);
	// }
	// }
	// if (pstmt != null) {
	// try {
	// pstmt.close();
	// } catch (SQLException se) {
	// se.printStackTrace(System.err);
	// }
	// }
	// if (con != null) {
	// try {
	// con.close();
	// } catch (Exception e) {
	// e.printStackTrace(System.err);
	// }
	// }
	// }
	// return list;
	// }

}
