package kharkov.kp.gic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kharkov.kp.gic.domain.Userok;

public interface UserokRepository extends JpaRepository<Userok, Integer> {
	
	Userok findByUserName(String username);
}
