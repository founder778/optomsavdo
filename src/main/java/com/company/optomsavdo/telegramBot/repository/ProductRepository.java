package com.company.optomsavdo.telegramBot.repository;

import com.company.optomsavdo.telegramBot.entity.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<ProductEntity,Integer> {
    @Query("select distinct p.p_type from ProductEntity p where p.menu.m_name=?1")
    List<String> getByMenuPro(String menu);

    @Query("select distinct p from ProductEntity p where p.p_type=?1")
    List<ProductEntity> getByTypePro(String menu);

    @Query("select  p from ProductEntity p where p.p_type=?1")
    List<ProductEntity> getByTypeName(String menu);

    @Query("select  p from ProductEntity p where p.p_name=?1")
    ProductEntity getProduct(String menu);

    @Query("select  p from ProductEntity p where p.p_id=?1")
    ProductEntity getId(Integer id);

    @Query("select  p.p_name from ProductEntity p where p.p_id=?1")
    String getProductId(Integer id);
}
