package com.example.baeldungtest.login.repository;
import com.example.baeldungtest.login.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("select o from roles o where o.role=:role")
    Role findByRole(String role);

}
