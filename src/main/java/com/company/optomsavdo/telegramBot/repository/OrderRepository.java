package com.company.optomsavdo.telegramBot.repository;

import com.company.optomsavdo.telegramBot.entity.OrderEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Integer> {
    @Query("select distinct o.product.p_id  from OrderEntity o where o.user.a_id = ?1 and o.status = 'BLOCK'")
    List<Integer> getAll(Integer userId);

    @Query("select distinct o.product.p_id  from OrderEntity o where o.user.a_id = ?1 and o.status = 'ACTIVE' and o.date=?2")
    List<Integer> getAllAdmin(Integer userId,String date);

    @Query("select sum(o.o_number) from OrderEntity o where o.user.a_id = ?1 and o.status = 'BLOCK' and o.product.p_id=?2")
    Integer getAllPro(Integer userId, Integer pId);
    @Query("select sum(o.o_number) from OrderEntity o where o.user.a_id = ?1 and o.status = 'ACTIVE' and o.product.p_id=?2 and o.date=?3")
    Integer getAllProAdmin(Integer userId, Integer pId,String date);

    @Modifying
    @Transactional
    @Query("update OrderEntity o set o.status = 'ACTIVE' where o.user.a_id=?1 and o.status ='BLOCK'")
    void update(Integer userId);


    @Query("select distinct o.date from OrderEntity o where o.user.a_id = ?1")
    List<String> getByDateOrder(Integer userId);


    @Query("select o from OrderEntity o where o.user.a_id = ?2 and o.date=?1")
    List<OrderEntity> getOrders(String date,Integer userId);




//    ==================istoriya======================


    @Query("select distinct o.product.p_id  from OrderEntity o where o.user.a_id = ?2 and o.date=?1")
    List<Integer> getAllByDate(String date,Integer userId);


    @Query("select sum(o.o_number) from OrderEntity o where o.user.a_id = ?1 and o.date=?3 and o.product.p_id=?2")
    Integer getAllBydatePro(Integer userId, Integer pId,String date);

    @Query("select o from OrderEntity o where o.user.a_id=?1 and o.date=?2")
    List<OrderEntity> getOrder(Integer id,String date );

    @Query("select distinct o.product.p_id from OrderEntity o where o.user.a_id=?1")
    List<Integer> getOrderAll(Integer id);

    @Query("select sum(o.o_number) from OrderEntity o where o.user.a_id = ?1  and o.product.p_id=?2")
    Integer getAllBydatePro(Integer userId, Integer pId);

    @Query("select distinct o.product.p_name  from OrderEntity o where o.user.a_id = ?2 and o.date=?1")
    List<String> getAllByDate(Integer userId);


}
