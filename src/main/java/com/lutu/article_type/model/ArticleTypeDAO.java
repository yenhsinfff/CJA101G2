package com.lutu.article_type.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ArticleTypeDAO implements ArticleTypeDAO_interface {

	// JDBC
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/lutudb?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "880510";

	private static final String INSERT_STMT = "INSERT INTO ArticleType (AC_TYPE_KIND, AC_TYPE_TEXT) VALUES (?, ?)";
	private static final String GET_ALL_STMT = "SELECT acTypeId,acTypeKind,acTypeText FROM ArticleType order by acTypeId";
	private static final String GET_ONE_STMT = "SELECT acTypeId,acTypeKind,acTypeText FROM ArticleType where acTypeId = ?";
	private static final String DELETE = "DELETE FROM ArticleType where acTypeId = ?";
	private static final String UPDATE = "UPDATE ArticleType set  AC_TYPE_KIND=?, AC_TYPE_TEXT=? where acTypeId = ?";

	@Override
	public void insert(ArticleTypeVO ArticleTypeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, ArticleTypeVO.getAcTypeId()); // 這要看你的主鍵是否為手動給值
			pstmt.setString(2, ArticleTypeVO.getAcTypeKind());
			pstmt.setString(3, ArticleTypeVO.getAcTypeText());

			pstmt.executeUpdate();

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
	public void update(ArticleTypeVO articleTypeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, articleTypeVO.getAcTypeKind());
			pstmt.setString(2, articleTypeVO.getAcTypeText());
			pstmt.setInt(3, articleTypeVO.getAcTypeId());

			pstmt.executeUpdate();

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
	public void delete(Integer acTypeId) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, acTypeId);

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public ArticleTypeVO findByPrimaryKey(Integer acTypeId) {

		ArticleTypeVO articleTypeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, acTypeId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				articleTypeVO = new ArticleTypeVO();
				articleTypeVO.setAcTypeId(rs.getInt("acTypeId"));
				articleTypeVO.setAcTypeKind(rs.getString("acTypeKind"));
				articleTypeVO.setAcTypeText(rs.getString("acTypeText"));
			
			} // Handle any driver errors
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
		return articleTypeVO;
	}

	@Override
	public List<ArticleTypeVO> getAll() {

		List<ArticleTypeVO> list = new java.util.ArrayList<>();
		ArticleTypeVO articleTypeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				articleTypeVO = new ArticleTypeVO();
				articleTypeVO.setAcTypeId(rs.getInt("acTypeId"));
				articleTypeVO.setAcTypeKind(rs.getString("acTypeKind"));
				articleTypeVO.setAcTypeText(rs.getString("acTypeText"));
				list.add(articleTypeVO);

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
	
	
	//============================================================
	
	public static void main(String[] args) {
		
		ArticleTypeDAO dao = new ArticleTypeDAO();
		
		// 新增類別----------------------------------------------
		ArticleTypeVO articleTypeVO1 = new ArticleTypeVO();
		
		articleTypeVO1.setAcTypeKind("露營知識");
		articleTypeVO1.setAcTypeText("露營小知識");

		dao.insert(articleTypeVO1);
		
		//更新資料----------------------------------------------
		ArticleTypeVO articleTypeVO2 = new ArticleTypeVO();
		
		articleTypeVO1.setAcTypeId(30001);
		articleTypeVO1.setAcTypeKind("露營知識");
		articleTypeVO1.setAcTypeText("露營小知識");
		dao.update(articleTypeVO2);
		
		// 刪除----------------------------------------------
		
		dao.delete(30001);
		
		
		// 查詢單筆類別----------------------------------------------
		
		ArticleTypeVO articleTypeVO3 = dao.findByPrimaryKey(7001);
		
		System.out.print(articleTypeVO3.getAcTypeId() + ",");
		System.out.print(articleTypeVO3.getAcTypeKind() + ",");
		System.out.print(articleTypeVO3.getAcTypeText() + ",");

		System.out.println("---------------------");
		
		
		// 查詢所有類別----------------------------------------------
		
		List<ArticleTypeVO> list = dao.getAll();
		for (ArticleTypeVO aEmp : list) {
			System.out.print(aEmp.getAcTypeId() + ",");
			System.out.print(aEmp.getAcTypeKind() + ",");
			System.out.print(aEmp.getAcTypeText() + ",");
			
			System.out.println();
		}
		
		
		
		
		}

}