package com.example.spring.repository;

import com.example.spring.domain.Employee;
import com.example.spring.domain.enumation.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    @Query("select e from Employee e where e.firstName like %:name%")
    List<Employee> findByFirstName(String name);
//    @Query("SELECT e FROM Employee e WHERE e.firstName like %:name% AND " +
//            "e.lastName like %:lastName% and " +
//            "e.position = :position and " +
//            "e.yearOfBirth like %:yearOfBirth% and " +
//            "e.company = :company")
//    List<Employee> findByFirstNameAndLastName(String firstName,
//                                              String lastName,
//                                              Position position,
//                                              String yearOfBirth,
//                                              Department company);

    @Query("select e from Employee e where e.lastName like %:lastName%")
    List<Employee> findByLastName(String lastName);
    @Query("select e from Employee e where e.position = :position")
    List<Employee> findByPosition(Position position);
    @Query("select e from Employee e where e.yearOfBirth like %:yearOfBirth%")
    List<Employee> findByYearOfBirth(String yearOfBirth);
//    @Query("select e from Employee e where e.department = :department")
//    List<Employee> findByDepartment(String company);
}
