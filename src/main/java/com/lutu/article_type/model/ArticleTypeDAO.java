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
	String passwd = "Lightcavalry2";

	private static final String INSERT_STMT = "INSERT INTO article_type (ac_type_kind, ac_type_text) VALUES (?, ?)";
	private static final String GET_ALL_STMT = "SELECT ac_type_id,ac_type_kind,ac_type_text FROM article_type order by ac_type_id";
	private static final String GET_ONE_STMT = "SELECT ac_type_id,ac_type_kind,ac_type_text FROM article_type where ac_type_id = ?";
	private static final String DELETE = "DELETE FROM article_type where ac_type_id = ?";
	private static final String UPDATE = "UPDATE article_type set  ac_type_kind=?, ac_type_text=? where ac_type_id = ?";

	@Override
	public void insert(ArticleTypeVO ArticleTypeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

//			pstmt.setInt(1, ArticleTypeVO.getAcTypeId()); // 這要看你的主鍵是否為手動給值
			pstmt.setString(1, ArticleTypeVO.getAcTypeKind());
			pstmt.setString(2, ArticleTypeVO.getAcTypeText());

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
	public ArticleTypeVO getOneArticleType(Integer acTypeId) {

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
				articleTypeVO.setAcTypeId(rs.getInt("ac_type_id"));
				articleTypeVO.setAcTypeKind(rs.getString("ac_type_kind"));
				articleTypeVO.setAcTypeText(rs.getString("ac_type_text"));
			
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
				articleTypeVO.setAcTypeId(rs.getInt("ac_type_id"));
				articleTypeVO.setAcTypeKind(rs.getString("ac_type_kind"));
				articleTypeVO.setAcTypeText(rs.getString("ac_type_text"));
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
	
	
	
}