package com.lutu.administrator.model;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AdministratorRepository extends JpaRepository<AdministratorVO, Integer>{

	
    boolean existsByAdminAcc(String adminAcc);
    Optional<AdministratorVO> findByAdminAcc(String adminAcc);
}
