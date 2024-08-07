package com.adt.hrms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adt.hrms.model.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

	@Query(value = "FROM Employee e WHERE lower(e.firstName) like lower(concat('%', :query,'%')) OR lower(e.lastName) like lower(concat('%', :query,'%'))")
	Page<Employee> SearchByName(@Param("query") String name, Pageable pageable);

	@Query("FROM Employee e WHERE lower(e.email) like lower(concat('%', :query,'%'))")
	Page<Employee> SearchByEmail(@Param("query") String email, Pageable pageable);

	Page<Employee> findAll(Specification<Employee> sp, Pageable pageable);

	Page<Employee> findByIsActiveTrue(Pageable pageable);

	@Query(value = "SELECT * FROM user_schema._employee where employee_id=?1", nativeQuery = true)
	Optional<Employee> findEmployeeById(Integer empId);

	@Query(value = "SELECT * FROM user_schema._employee where adt_id=?1", nativeQuery = true)
	Optional<Employee> findEmpByAdtId(String empAdtId);

}
