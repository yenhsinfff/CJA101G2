package com.lutu.campsitetype.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CampsiteTypeDAO implements CampsiteTypeDAO_interface {


	static {
		try {
			Class.forName(Util.DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = 
		"INSERT INTO campsite_type (camp_id,campsite_name,campsite_people,campsite_num,campsite_price,campsite_pic1,campsite_pic2,campsite_pic3,campsite_pic4) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT campsite_type_id, camp_id,campsite_name,campsite_people,campsite_num,campsite_price,campsite_pic1,campsite_pic2,campsite_pic3,campsite_pic4 FROM campsite_type order by campsite_type_id";
	private static final String GET_ONE_STMT = 
		"SELECT campsite_Type_Id, camp_Id,campsite_Name,campsite_People,campsite_Num,campsite_Price,campsite_Pic1,campsite_Pic2,campsite_Pic3,campsite_Pic4 FROM campsite_type where campsite_Type_Id = ?";
	private static final String DELETE = 
		"DELETE FROM campsite_type where campsite_Type_Id = ?";
	private static final String UPDATE = 
		"UPDATE campsite_type set camp_id=?, campsite_name=?, campsite_people=?, campsite_num=?, campsite_price=?, campsite_pic1=?, campsite_pic2=?, campsite_pic3=?, campsite_pic4=? where campsite_type_id = ?";

	@Override
	public void insert(CampsiteTypeVO campsiteTypeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, campsiteTypeVO.getCampId());
			pstmt.setString(2, campsiteTypeVO.getCampsiteName());
			pstmt.setInt(3, campsiteTypeVO.getCampsitePeople());
			pstmt.setByte(4, campsiteTypeVO.getCampsiteNum());
			pstmt.setInt(5, campsiteTypeVO.getCampsitePrice());
			pstmt.setBytes(6, campsiteTypeVO.getCampsitePic1());
			pstmt.setBytes(7, campsiteTypeVO.getCampsitePic2());
			pstmt.setBytes(8, campsiteTypeVO.getCampsitePic3());
			pstmt.setBytes(9, campsiteTypeVO.getCampsitePic4());


			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void update(CampsiteTypeVO campsiteTypeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, campsiteTypeVO.getCampId());
			pstmt.setString(2, campsiteTypeVO.getCampsiteName());
			pstmt.setInt(3, campsiteTypeVO.getCampsitePeople());
			pstmt.setByte(4, campsiteTypeVO.getCampsiteNum());
			pstmt.setInt(5, campsiteTypeVO.getCampsitePrice());
			pstmt.setBytes(6, campsiteTypeVO.getCampsitePic1());
			pstmt.setBytes(7, campsiteTypeVO.getCampsitePic2());
			pstmt.setBytes(8, campsiteTypeVO.getCampsitePic3());
			pstmt.setBytes(9, campsiteTypeVO.getCampsitePic4());
			pstmt.setInt(10, campsiteTypeVO.getCampsiteTypeId());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void delete(Integer campsiteTypeId) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, campsiteTypeId);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public CampsiteTypeVO findByPK(Integer campsiteTypeId) {

		CampsiteTypeVO campsiteTypeVO= null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1, campsiteTypeId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				campsiteTypeVO = new CampsiteTypeVO();
				campsiteTypeVO.setCampsiteTypeId(rs.getInt("campsite_Type_Id"));
				campsiteTypeVO.setCampId(rs.getInt("camp_Id"));
				campsiteTypeVO.setCampsiteName(rs.getString("campsite_Name"));
				campsiteTypeVO.setCampsitePeople(rs.getInt("campsite_People"));
				campsiteTypeVO.setCampsiteNum(rs.getByte("campsite_Num"));
				campsiteTypeVO.setCampsitePrice(rs.getInt("campsite_Price"));
				campsiteTypeVO.setCampsitePic1(rs.getBytes("campsite_Pic1"));
				campsiteTypeVO.setCampsitePic2(rs.getBytes("campsite_Pic2"));
				campsiteTypeVO.setCampsitePic3(rs.getBytes("campsite_Pic3"));
				campsiteTypeVO.setCampsitePic4(rs.getBytes("campsite_Pic4"));
				
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return campsiteTypeVO;
	}

	@Override
	public List<CampsiteTypeVO> getAll() {
		List<CampsiteTypeVO> list = new ArrayList<CampsiteTypeVO>();
		CampsiteTypeVO campsiteTypeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				campsiteTypeVO = new CampsiteTypeVO();
				campsiteTypeVO.setCampsiteTypeId(rs.getInt("campsite_Type_Id"));
				campsiteTypeVO.setCampId(rs.getInt("camp_Id"));
				campsiteTypeVO.setCampsiteName(rs.getString("campsite_Name"));
				campsiteTypeVO.setCampsitePeople(rs.getInt("campsite_People"));
				campsiteTypeVO.setCampsiteNum(rs.getByte("campsite_Num"));
				campsiteTypeVO.setCampsitePrice(rs.getInt("campsite_Price"));
				campsiteTypeVO.setCampsitePic1(rs.getBytes("campsite_Pic1"));
				campsiteTypeVO.setCampsitePic2(rs.getBytes("campsite_Pic2"));
				campsiteTypeVO.setCampsitePic3(rs.getBytes("campsite_Pic3"));
				campsiteTypeVO.setCampsitePic4(rs.getBytes("campsite_Pic4"));
				list.add(campsiteTypeVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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