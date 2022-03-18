package com.company.optomsavdo.telegramBot.repository;

import com.company.optomsavdo.telegramBot.entity.MenuEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends CrudRepository<MenuEntity,Integer> {
}
