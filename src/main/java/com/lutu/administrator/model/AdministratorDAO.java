package com.lutu.administrator.model;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.DataSource;



public class AdministratorDAO implements AdministratorDAO_interface {

    // 資料庫連線池
    private static DataSource ds = null;
    static {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB2");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    // SQL 指令
    private static final String INSERT_STMT =
        "INSERT INTO administrator (admin_acc, admin_pwd, admin_pwd_hash, admin_status, admin_name) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_ALL_STMT =
        "SELECT admin_id, admin_acc, admin_pwd, admin_pwd_hash, admin_status, admin_name FROM administrator ORDER BY admin_id";
    private static final String GET_ONE_STMT =
        "SELECT admin_id, admin_acc, admin_pwd, admin_pwd_hash, admin_status, admin_name FROM administrator WHERE admin_id = ?";
    private static final String DELETE =
        "DELETE FROM administrator WHERE admin_id = ?";
    private static final String UPDATE =
        "UPDATE administrator SET admin_acc=?, admin_pwd=?, admin_pwd_hash=?, admin_status=?, admin_name=? WHERE admin_id = ?";

    @Override
    public void insert(AdministratorVO administratorVO) {
    	
    	Connection con = null;
    	PreparedStatement pstmt = null;
    	
        try {
            con = ds.getConnection();
            pstmt = con.prepareStatement(INSERT_STMT);
         
            pstmt.setString(1, administratorVO.getAdminAcc());
            pstmt.setString(2, administratorVO.getAdminPwd());
            pstmt.setString(3, administratorVO.getAdminPwdHash());
            pstmt.setByte(4, administratorVO.getAdminStatus());
            pstmt.setString(5, administratorVO.getAdminName());
            
            pstmt.executeUpdate();
            
        } catch (SQLException se) {
            throw new RuntimeException("資料庫錯誤：" + se.getMessage());
        }
        finally {
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
            con = ds.getConnection();
            pstmt = con.prepareStatement(UPDATE);
        
            pstmt.setString(1, administratorVO.getAdminAcc());
            pstmt.setString(2, administratorVO.getAdminPwd());
            pstmt.setString(3, administratorVO.getAdminPwdHash());
            pstmt.setByte(4, administratorVO.getAdminStatus());
            pstmt.setString(5, administratorVO.getAdminName());
            pstmt.setInt(6, administratorVO.getAdminId());
            
            pstmt.executeUpdate();
            
        } catch (SQLException se) {
            throw new RuntimeException("資料庫錯誤：" + se.getMessage());
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
    		
            con = ds.getConnection();
            pstmt = con.prepareStatement(DELETE);
        
            pstmt.setInt(1, admin_id);
            
            pstmt.executeUpdate();
            
        } catch (SQLException se) {
            throw new RuntimeException("資料庫錯誤：" + se.getMessage());
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
        	
            con = ds.getConnection();
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
            
            
        } catch (SQLException se) {
            throw new RuntimeException("資料庫錯誤：" + se.getMessage());
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
            con = ds.getConnection();
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
            
            
        } catch (SQLException se) {
            throw new RuntimeException("資料庫錯誤：" + se.getMessage());
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
