package com.company.optomsavdo.telegramBot.repository;

import com.company.optomsavdo.telegramBot.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity,Integer> {
    @Query("select u from UserEntity u where u.a_login =?1 and u.a_password=?2")
    UserEntity getUser(String login,String pas);
    @Query("select u from UserEntity u where u.a_password=?1")
    UserEntity getUserByPas(String pas);

    @Query("select u.a_name from UserEntity u ")
    List<String> getAll();
    @Query("select u.a_id from UserEntity u where u.a_name=?1")
    Integer getuserbyid(String name);


    @Query("select u from UserEntity u where u.status = 'ACTIVE' and u.status='BLOCKED'")
    List<UserEntity> getAllUsers();


    @Modifying
    @Transactional
    @Query("update UserEntity o set o.status =?1 where o.a_id=?2")
    void update(String status,Integer userId);


    @Query("select u from UserEntity u where u.a_id=?1 and u.status = 'ACTIVE'")
    UserEntity get(Integer userid);



}
