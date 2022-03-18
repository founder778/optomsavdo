package com.company.optomsavdo.AdminPage.repository;

import com.company.optomsavdo.AdminPage.entitys.MenuEntityAdmin;
import com.company.optomsavdo.AdminPage.entitys.ProductEntityAdmin;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepositoryAdmin extends CrudRepository<ProductEntityAdmin, Integer> {
    //    @Query("select p from ProductEntityAdmin p where p.menu=?1")
//    Optional<ProductEntityAdmin> findByName(String name);
    @Query("select distinct t.p_type from ProductEntityAdmin t")
    List<String> getAll();

    @Query("select t from ProductEntityAdmin t")
    List<ProductEntityAdmin> AllProducts();

    @Modifying
    @Transactional
    @Query("  delete  from ProductEntityAdmin  a  where a.p_name=?1 ")
    void ByNameDelete(String name);



}
