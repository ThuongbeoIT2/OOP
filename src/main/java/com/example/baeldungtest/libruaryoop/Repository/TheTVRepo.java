package com.example.baeldungtest.libruaryoop.Repository;

import com.example.baeldungtest.libruaryoop.Model.TheTV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheTVRepo extends JpaRepository<TheTV,Long> {
    @Query("select u from thetv u where u.checkTTV=true order by u.soThe asc ")
    List<TheTV> findAllCheckTrue();

    @Query("select u from thetv u where u.checkTTV=false order by u.soThe asc ")
    List<TheTV> findAllCheckFalse();
    @Query("SELECT u FROM thetv u WHERE u.docGia.tenDocGia = :tenDocGia")
    TheTV findByName(@Param("tenDocGia") String tenDocGia);

    @Query("SELECT u from thetv u where u.soThe= :soThe")
    TheTV findUserById(@Param("soThe") Long soThe);

}
