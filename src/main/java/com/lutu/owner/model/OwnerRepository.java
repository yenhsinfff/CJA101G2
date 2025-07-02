package com.lutu.owner.model;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lutu.owner.model.OwnerVO;

@Repository
public interface OwnerRepository extends JpaRepository<OwnerVO, Integer> {
	
    Optional<OwnerVO> findByOwnerAcc(String ownerAcc);
    
    Optional<OwnerVO> findByVerificationToken(String verificationToken);
    
    boolean existsByOwnerEmail(String ownerEmail);
    Optional<OwnerVO> findByOwnerEmail(String ownerEmail);
}
	

