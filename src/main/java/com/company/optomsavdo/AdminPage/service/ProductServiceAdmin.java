package com.company.optomsavdo.AdminPage.service;

import com.company.optomsavdo.AdminPage.dto.ProductDto;
import com.company.optomsavdo.AdminPage.entitys.MenuEntityAdmin;
import com.company.optomsavdo.AdminPage.entitys.ProductEntityAdmin;
import com.company.optomsavdo.AdminPage.repository.MenuRepositoryAdmin;
import com.company.optomsavdo.AdminPage.repository.ProductRepositoryAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class ProductServiceAdmin {
    @Autowired
    ProductRepositoryAdmin productRepository;
    @Autowired
    MenuRepositoryAdmin menuRepository;
//    @Value("${attach.upload.folder}")
//    private String uploadFolder;

    public void create(ProductDto product) {
        Optional<MenuEntityAdmin> respons = menuRepository.findByM_name(product.getMenu().toLowerCase());
        if (respons.isPresent()) {
            ProductEntityAdmin entity = new ProductEntityAdmin();
            entity.setP_name(product.getP_name().toLowerCase());
            entity.setP_price(product.getP_price());
            entity.setMenu(respons.get());
            entity.setP_caption(product.getP_caption());
            entity.setP_type(product.getP_type().toLowerCase());

            entity.setImg(product.getImg());
            productRepository.save(entity);

        } else {
            MenuEntityAdmin menu = new MenuEntityAdmin();
            menu.setM_name(product.getMenu().toLowerCase());
            menuRepository.save(menu);
            ProductEntityAdmin entity = new ProductEntityAdmin();
            entity.setP_name(product.getP_name().toLowerCase());
            entity.setP_price(product.getP_price());
            entity.setMenu(menu);
            entity.setP_caption(product.getP_caption());
            entity.setP_type(product.getP_type().toLowerCase());
            entity.setImg(product.getImg());
            productRepository.save(entity);
        }
    }

    public void update(String photo, String name) {
        ProductEntityAdmin response = productRepository.getByName(name);
        response.setImg(photo);
        productRepository.save(response);

    }

//    public String saveFile(MultipartFile file, String name) {
//        oldDelete(file, name.toLowerCase());
//        File folder = new File("upload");
//        if (!folder.exists()) {
//            folder.mkdir();
//        }
//
//        try {
//            int lastIndex = file.getOriginalFilename().lastIndexOf(".");
//            String extension = file.getOriginalFilename().substring(lastIndex + 1);
//            byte[] bytes = file.getBytes();
//            Path path = Paths.get("upload/" + name + "." + extension);
//            Files.write(path, bytes);
//            return path.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public void oldDelete(MultipartFile file, String name) {
//        int lastIndex = file.getOriginalFilename().lastIndexOf(".");
//        String extension = file.getOriginalFilename().substring(lastIndex + 1);
//        Path path = Paths.get("upload/" + name + "." + extension);
//        File file1 = path.toFile();
//        file1.delete();
//    }
//    public void delete(String name) {
//        Path path = Paths.get("upload/" + name + ".jpg");
//        File file1 = path.toFile();
//        file1.delete();
//    }


}
