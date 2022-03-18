package com.company.optomsavdo.AdminPage.repository;

import com.company.optomsavdo.AdminPage.entitys.MenuEntityAdmin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface MenuRepositoryAdmin extends CrudRepository<MenuEntityAdmin,Integer> {
    @Query("select m from MenuEntityAdmin m where  m.m_name=?1")
    Optional<MenuEntityAdmin> findByM_name(String name);

    @Query("select m from MenuEntityAdmin m")
    List<MenuEntityAdmin>getAll();
}
