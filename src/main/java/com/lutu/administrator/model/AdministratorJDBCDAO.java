package com.lutu.administrator.model;

import java.util.*;
import java.sql.*;

public class AdministratorJDBCDAO implements AdministratorDAO_interface{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/campdb?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "880510";

	private static final String INSERT_STMT = "INSERT INTO administrator(admin_acc,admin_pwd,admin_pwd_hash,admin_status,admin_name) VALUES (?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT admin_id,admin_acc,admin_pwd,admin_pwd_hash,admin_status,admin_name FROM administrator order by admin_id";
	private static final String GET_ONE_STMT = "SELECT admin_id,admin_acc,admin_pwd,admin_pwd_hash,admin_status,admin_name FROM administrator where admin_id = ?";
	private static final String DELETE = "DELETE FROM administrator where admin_id = ?";
	private static final String UPDATE = "UPDATE administrator set admin_acc=?, admin_pwd=?, admin_pwd_hash=?, admin_status=?, admin_name=? where admin_id = ?";

	@Override
	public void insert(AdministratorVO administratorVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, administratorVO.getAdminAcc());
			pstmt.setString(2, administratorVO.getAdminPwd());
			pstmt.setString(3, administratorVO.getAdminPwdHash());
			pstmt.setByte(4, administratorVO.getAdminStatus());
			pstmt.setString(5, administratorVO.getAdminName());

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
	public void update(AdministratorVO administratorVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, administratorVO.getAdminAcc());
			pstmt.setString(2, administratorVO.getAdminPwd());
			pstmt.setString(3, administratorVO.getAdminPwdHash());
			pstmt.setByte(4, administratorVO.getAdminStatus());
			pstmt.setString(5, administratorVO.getAdminName());

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
	public void delete(Integer admin_id) {
	
		Connection con = null;
		PreparedStatement pstmt = null;
	
		try {
	
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
	
			pstmt.setInt(1, admin_id);
	
			pstmt.executeUpdate();
	
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public AdministratorVO findByPrimaryKey(Integer admin_id) {

		AdministratorVO administratorVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1, admin_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				administratorVO = new AdministratorVO();
                administratorVO.setAdminId(rs.getInt("admin_id"));
                administratorVO.setAdminAcc(rs.getString("admin_acc"));
                administratorVO.setAdminPwd(rs.getString("admin_pwd"));
                administratorVO.setAdminPwdHash(rs.getString("admin_pwd_hash"));
                administratorVO.setAdminStatus(rs.getByte("admin_status"));
                administratorVO.setAdminName(rs.getString("admin_name"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
		return administratorVO;
	}
	
    @Override
    public List<AdministratorVO> getAll() {
        List<AdministratorVO> list = new ArrayList<AdministratorVO>();
        
        AdministratorVO administratorVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
        	
            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                administratorVO = new AdministratorVO();
                administratorVO.setAdminId(rs.getInt("admin_id"));
                administratorVO.setAdminAcc(rs.getString("admin_acc"));
                administratorVO.setAdminPwd(rs.getString("admin_pwd"));
                administratorVO.setAdminPwdHash(rs.getString("admin_pwd_hash"));
                administratorVO.setAdminStatus(rs.getByte("admin_status"));
                administratorVO.setAdminName(rs.getString("admin_name"));
                list.add(administratorVO);
            }
            
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException("A database error occurred. " + se.getMessage());
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

	public static void main(String[] args) {

		AdministratorJDBCDAO dao = new AdministratorJDBCDAO();
		
        // 測試
//        AdministratorVO newAdmin = new AdministratorVO();
//        newAdmin.setAdminAcc("newadmin@example.com");
//        newAdmin.setAdminPwd("newpwd");
//        newAdmin.setAdminPwdHash("hashedpwd");
//        newAdmin.setAdminStatus((byte) 1);
//        newAdmin.setAdminName("新管理員");
//        dao.insert(newAdmin);

		//修改
//        AdministratorVO updateAdmin = new AdministratorVO();
//        updateAdmin.setAdminId(30000001);
//        updateAdmin.setAdminAcc("updatedadmin@example.com");
//        updateAdmin.setAdminPwd("updatedpwd");
//        updateAdmin.setAdminPwdHash("updatedhash");
//        updateAdmin.setAdminStatus((byte) 0);
//        updateAdmin.setAdminName("更新後的管理員");
//        dao.update(updateAdmin);
        
		// 查詢
		List<AdministratorVO> list = dao.getAll();
		for (AdministratorVO aAdmin : list) {
			System.out.print(aAdmin.getAdminId() + ",");
			System.out.print(aAdmin.getAdminAcc() + ",");
			System.out.print(aAdmin.getAdminPwd() + ",");
			System.out.print(aAdmin.getAdminPwdHash() + ",");
			System.out.print(aAdmin.getAdminStatus() + ",");
			System.out.print(aAdmin.getAdminName() + ",");
			System.out.println();
		}
	}
	
	
}
