package com.company.optomsavdo;

import com.company.optomsavdo.telegramBot.entity.MenuEntity;
import com.company.optomsavdo.telegramBot.entity.ProductEntity;
import com.company.optomsavdo.telegramBot.repository.MenuRepository;
import com.company.optomsavdo.telegramBot.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class OptomSavdoApplicationTests {
@Autowired
    MenuRepository menuRepository;
@Autowired
    ProductRepository productRepository;
    @Test
    void contextLoads() {
        ProductEntity p = new ProductEntity();
        Optional<MenuEntity> menu = menuRepository.findById(1);
        p.setMenu(menu.get());
        p.setP_name("qizil olcha ");
        p.setP_caption("tabiiy");
        p.setP_price("10.000");
        p.setP_type("olcha");
        productRepository.save(p);

    }

}
