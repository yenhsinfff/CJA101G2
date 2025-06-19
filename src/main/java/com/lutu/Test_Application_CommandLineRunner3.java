package com.lutu;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.lutu.product_type.model.ProdTypeVO;
import com.lutu.shopProd.model.ShopProdRepository;
import com.lutu.shopProd.model.ShopProdVO;

@Transactional
@SpringBootApplication
public class Test_Application_CommandLineRunner3 implements CommandLineRunner {

	@Autowired
	ShopProdRepository repository ;
	
//	@Autowired
//	ShopProdTypeRepository repositoryType; 
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public static void main(String[] args) {
        SpringApplication.run(Test_Application_CommandLineRunner3.class);
    }

    @Override
    public void run(String...args) throws Exception {

    	//● 新增
//		DeptVO deptVO = new DeptVO(); // 部門POJO
//		deptVO.setDeptno(1);
    	ProdTypeVO prodTypeVO = new ProdTypeVO();
    	prodTypeVO.setProdTypeId(2);
    	
//		EmpVO empVO1 = new EmpVO();
//		empVO1.setEname("吳永志1");
//		empVO1.setJob("MANAGER");
//		empVO1.setHiredate(java.sql.Date.valueOf("2005-01-01"));
//		empVO1.setSal(new Double(50000));
//		empVO1.setComm(new Double(500));
//		empVO1.setDeptVO(deptVO);
//		repository.save(empVO1);

		//● 修改
    	ShopProdVO p = new ShopProdVO();
		p.setProdId(3);
		p.setProdName("吳永志2");
		p.setProdIntro("");
//		p.setProdReleaseDate("");
		p.setProdDiscount(BigDecimal.valueOf(0.1));
		p.setProdDiscountStart(Timestamp.valueOf("2025-06-15 12:00:00"));
		p.setProdDiscountEnd(Timestamp.valueOf("2025-06-17 16:30:00"));
		p.setProdTypeVO(prodTypeVO);
		
		p.setProdStatus(Byte.valueOf((byte)0));
		p.setProdColorOrNot(Byte.valueOf((byte)0));
		repository.save(p);
		System.out.println("修改成功");
		System.out.println("--------------------------------");
//		EmpVO empVO2 = new EmpVO();
//		empVO2.setEmpno(7001);
//		empVO2.setEname("吳永志2");
//		empVO2.setJob("MANAGER2");
//		empVO2.setHiredate(java.sql.Date.valueOf("2002-01-01"));
//		empVO2.setSal(new Double(20000));
//		empVO2.setComm(new Double(200));
//		empVO2.setDeptVO(deptVO);
//		repository.save(empVO2);
		
		
		
		
		//● 刪除   //●●● --> EmployeeRepository.java 內自訂的刪除方法
//		repository.deleteByEmpno(7014);
		
		//● 刪除   //XXX --> Repository內建的刪除方法目前無法使用，因為有@ManyToOne
		//System.out.println("--------------------------------");
		//repository.deleteById(7001);      
		//System.out.println("--------------------------------");

    	//● 查詢-findByPrimaryKey (多方emp2.hbm.xml必須設為lazy="false")(優!)
//    	Optional<ShopProdVO> optional = repository.findById(3);
//    	Optional<ShopProdVO> optional = repository.selectProdById(1);
//    	ShopProdVO p = optional.get();
//    	System.out.print(p.getProdId() + ",");
//		System.out.print(p.getProdName() + ",");
//		System.out.print(p.getProdIntro() + ",");
//		System.out.print(p.getProdReleaseDate() + ",");
//		System.out.print(p.getProdDiscount() + ",");
//		System.out.print(p.getProdDiscountStart() + ",");
//		System.out.print(p.getProdDiscountEnd() + ",");
//		System.out.print(p.getProdCommentCount() + ",");
//		System.out.print(p.getProdStatus() + ",");
//		System.out.print(p.getProdColorOrNot() + ",");
//		// 注意以下三行的寫法 (優!)
//		System.out.print(p.getProdTypeVO().getProdTypeId() + ",");
//		System.out.print(p.getProdTypeVO().getProdTypeName() + ",");
//		System.out.println("\n---------------------");
      
    	
		//● 查詢-getAll (多方emp2.hbm.xml必須設為lazy="false")(優!)
//    	List<ShopProdVO> list = repository.selectAllProducts(); //自訂方法
//    	List<ShopProdVO> list = repository.findAll(); //內建方法
//		for (ShopProdVO p : list) {
//			System.out.print(p.getProdId() + ",");
//			System.out.print(p.getProdName() + ",");
//			System.out.print(p.getProdIntro() + ",");
//			System.out.print(p.getProdReleaseDate() + ",");
//			System.out.print(p.getProdDiscount() + ",");
//			System.out.print(p.getProdDiscountStart() + ",");
//			System.out.print(p.getProdDiscountEnd() + ",");
//			System.out.print(p.getProdCommentCount() + ",");
//			System.out.print(p.getProdTypeVO().getProdTypeName() + ",");
//			System.out.print(p.getProdStatus() + ",");
//			System.out.print(p.getProdColorOrNot() + ",");
//			System.out.println();
//		}


		//● 複合查詢-getAll(map) 配合 req.getParameterMap()方法 回傳 java.util.Map<java.lang.String,java.lang.String[]> 之測試
//		Map<String, String[]> map = new TreeMap<String, String[]>();
//		map.put("empno", new String[] { "7001" });
//		map.put("ename", new String[] { "KING" });
//		map.put("job", new String[] { "PRESIDENT" });
//		map.put("hiredate", new String[] { "1981-11-17" });
//		map.put("sal", new String[] { "5000.5" });
//		map.put("comm", new String[] { "0.0" });
//		map.put("deptno", new String[] { "1" });
//		
//		List<EmpVO> list2 = HibernateUtil_CompositeQuery_Emp3.getAllC(map,sessionFactory.openSession());
//		for (EmpVO aEmp : list2) {
//			System.out.print(aEmp.getEmpno() + ",");
//			System.out.print(aEmp.getEname() + ",");
//			System.out.print(aEmp.getJob() + ",");
//			System.out.print(aEmp.getHiredate() + ",");
//			System.out.print(aEmp.getSal() + ",");
//			System.out.print(aEmp.getComm() + ",");
//			// 注意以下三行的寫法 (優!)
//			System.out.print(aEmp.getDeptVO().getDeptno() + ",");
//			System.out.print(aEmp.getDeptVO().getDname() + ",");
//			System.out.print(aEmp.getDeptVO().getLoc());
//			System.out.println();
//		}
		

		//● (自訂)條件查詢
//		DeptVO dept = new DeptVO();
//		dept.setDeptno(2);
//		List<EmpVO> list3 = repository.findByOthers(7001,"%K%",java.sql.Date.valueOf("1981-11-17"));
//		if(!list3.isEmpty()) {
//			for (EmpVO aEmp : list3) {
//				System.out.print(aEmp.getEmpno() + ",");
//				System.out.print(aEmp.getEname() + ",");
//				System.out.print(aEmp.getJob() + ",");
//				System.out.print(aEmp.getHiredate() + ",");
//				System.out.print(aEmp.getSal() + ",");
//				System.out.print(aEmp.getComm() + ",");
//				// 注意以下三行的寫法 (優!)
//				System.out.print(aEmp.getDeptVO().getDeptno() + ",");
//				System.out.print(aEmp.getDeptVO().getDname() + ",");
//				System.out.print(aEmp.getDeptVO().getLoc());
//				System.out.println();
//			}
//		} else System.out.print("查無資料\n");
//		System.out.println("--------------------------------");

    }
}
