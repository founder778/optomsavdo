package com.company.optomsavdo.AdminPage.controller;

import com.company.optomsavdo.AdminPage.dto.ProductDto;
import com.company.optomsavdo.AdminPage.entitys.MenuEntityAdmin;
import com.company.optomsavdo.AdminPage.entitys.ProductEntityAdmin;
import com.company.optomsavdo.AdminPage.repository.MenuRepositoryAdmin;
import com.company.optomsavdo.AdminPage.repository.ProductRepositoryAdmin;
import com.company.optomsavdo.AdminPage.service.ProductServiceAdmin;
import com.company.optomsavdo.telegramBot.entity.OrderEntity;
import com.company.optomsavdo.telegramBot.entity.ProductEntity;
import com.company.optomsavdo.telegramBot.entity.UserEntity;
import com.company.optomsavdo.telegramBot.repository.OrderRepository;
import com.company.optomsavdo.telegramBot.repository.ProductRepository;
import com.company.optomsavdo.telegramBot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class ProductControllerAdmin {
    @Autowired
    ProductServiceAdmin productService;
    @Autowired
    MenuRepositoryAdmin menuRepositoryAdmin;
    @Autowired
    ProductRepositoryAdmin productRepositoryAdmin;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;

    @PostMapping("/upload")
    public String fileUpload(@RequestParam("photo") String photoToken,
                             @RequestParam("name") String name,
                             @RequestParam("menu") String menu,
                             @RequestParam("type") String type,
                             @RequestParam("price") String price,
                             @RequestParam("caption") String caption) {
        ProductDto product = new ProductDto();
        product.setImg(photoToken);
        product.setP_name(name);
        product.setMenu(menu);
        product.setP_type(type);
        product.setP_price(price);
        product.setP_caption(caption);
        productService.create(product);

        return "redirect:/admin/home";
    }

    @PostMapping("/save")
    public String save(ProductEntity products) {
        ProductEntity product = productRepository.getId(products.getP_id());
        product.setP_caption(products.getP_caption());
        product.setP_price(products.getP_price());
        product.setP_name(products.getP_name());
        productRepository.save(product);
        return "redirect:/admin/products";
    }

    @PostMapping("/img")
    public String ImgUpdate(@RequestParam("photo") String photo,
                            @RequestParam("name") String name) {

        productService.update(photo, name.toLowerCase());
        return "redirect:/admin/home";
    }

    @GetMapping("")
    public String home() {
        return "Auth";

    }
    @PostMapping("/check")
    public String check(@RequestParam("login") String login,
                        @RequestParam("parol") Integer parol, RedirectAttributes mas){
        if(login.equalsIgnoreCase("AdminOptom")){
            return "redirect:/admin/home";
        }
        else if(login.equalsIgnoreCase("AdminOptom2")){
            return "redirect:/admin/home2";
        }
        mas.addFlashAttribute("messages","not found");

        return "Auth";

    }

    @GetMapping("/home")
    public String newCreate(Model model1, Model model2) {
        List<MenuEntityAdmin> menu = menuRepositoryAdmin.getAll();
        model1.addAttribute("menus", menu);
        List<String> type = productRepositoryAdmin.getAll();
        model2.addAttribute("types", type);


        return "imgUpload";
    }

    @GetMapping("/products")
    public String products(Model model1) {
        List<ProductEntityAdmin> products = productRepositoryAdmin.AllProducts();
        model1.addAttribute("pro", products);
        return "Products";
    }

    @GetMapping("/edit/{name}")
    public String proUpdate(@PathVariable("name") String name, Model model1) {
        ProductEntity product = productRepository.getProduct(name);
        model1.addAttribute("pro", product);
        return "UpdateProduct";
    }

    @GetMapping("/delete/{name}")
    public String delete(@PathVariable("name") String name) {
        productRepositoryAdmin.ByNameDelete(name);
        return "redirect:/admin/products";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        Optional<ProductEntityAdmin> product = productRepositoryAdmin.findById(id);
        model.addAttribute("product", product);
        return "redirect:/admin/home";
    }

    @GetMapping("/imgUpdate")
    public String updateImg(Model model) {
        List<ProductEntityAdmin> product = productRepositoryAdmin.AllProducts();
        model.addAttribute("product", product);
        return "imgUpdate";
    }

    @GetMapping("/orderByDay")
    public String orderByDate(Model model) {
        List<String> users = userRepository.getAll();
        model.addAttribute("users", users);
        return "AgentOfOrder";
    }

    @GetMapping("/getorder/{name}")
    public String getOrder(@PathVariable("name") String name, Model model) {
        Integer userId = userRepository.getuserbyid(name);
        List<OrderEntity> order = orderRepository.getOrder(userId, String.valueOf(LocalDate.now()));
        model.addAttribute("orders", order);
        return "OrderA";
    }

    @GetMapping("/ordersAll")
    public String getuser(Model model) {
        List<String> users = userRepository.getAll();
        model.addAttribute("users", users);
        return "AgentsOrder";
    }

    @GetMapping("/orders/{name}")
    public String getOrderByUser(@PathVariable("name") String name, Model model, Model model2) {
        List<Integer> count = new LinkedList<>();
        List<String> names = new LinkedList<>();
        Integer userId = userRepository.getuserbyid(name);
        List<Integer> order = orderRepository.getOrderAll(userId);
        for (Integer o : order) {
            Integer num = orderRepository.getAllBydatePro(userId, o);
            count.add(num);
            String num2 = productRepository.getProductId(o);
            names.add(num2);
        }


        model.addAttribute("counts", count);
        model2.addAttribute("names", names);
        return "Order";
    }


    @GetMapping("/Allusers")
    public String Allusers(Model model) {
        List<UserEntity> allUsers = userRepository.getAllUsers();
        model.addAttribute("users", allUsers);
        return "AllUsers";
    }

    @GetMapping("/status/{id}/{status}")
    public String updateStatus(@PathVariable("id") Integer id,
                               @PathVariable("status") String status) {
        if (status.equals("ACTIVE")) {
            userRepository.update("BLOCK", id);
        } else {
            userRepository.update("ACTIVE", id);
        }

        return "redirect:/admin/Allusers";
    }

    @GetMapping("/editUser/{id}")
    public String updateUser(@PathVariable("id") Integer id,
                             Model model) {
        Optional<UserEntity> user = userRepository.findById(id);
        model.addAttribute("user", user.get());
        return "User";

    }

    @PostMapping("/saveUser")
    public String saveUser(UserEntity user) {
        Optional<UserEntity> respons = userRepository.findById(user.getA_id());
        respons.get().setA_login(user.getA_login());
        respons.get().setA_password(user.getA_password());
        userRepository.save(respons.get());
        return "redirect:/admin/Allusers";

    }



//    =============================admin 2================

    @GetMapping("/home2")
    public String newCreate2(Model model1, Model model2) {
        List<MenuEntityAdmin> menu = menuRepositoryAdmin.getAll();
        model1.addAttribute("menus", menu);
        List<String> type = productRepositoryAdmin.getAll();
        model2.addAttribute("types", type);
        return "Agent2";
    }

    @GetMapping("/products2")
    public String products2(Model model1) {
        List<ProductEntityAdmin> products = productRepositoryAdmin.AllProducts();
        model1.addAttribute("pro", products);
        return "Products2";
    }

    @PostMapping("/img2")
    public String ImgUpdate2(@RequestParam("photo") String photo,
                            @RequestParam("name") String name) {

        productService.update(photo, name.toLowerCase());
        return "redirect:/admin/home2";
    }

    @GetMapping("/orderByDay2")
    public String orderByDate2(Model model) {
        List<String> users = userRepository.getAll();
        model.addAttribute("users", users);
        return "ByDay";
    }

    @PostMapping("/upload2")
    public String fileUpload2(@RequestParam("photo") String photo,
                             @RequestParam("name") String name,
                             @RequestParam("menu") String menu,
                             @RequestParam("type") String type,
                             @RequestParam("price") String price,
                             @RequestParam("caption") String caption) {
        ProductDto product = new ProductDto();
        product.setImg(photo);
        product.setP_name(name);
        product.setMenu(menu);
        product.setP_type(type);
        product.setP_price(price);
        product.setP_caption(caption);
        productService.create(product);

        return "redirect:/admin/home2";
    }

    @GetMapping("/delete2/{name}")
    public String delete2(@PathVariable("name") String name) {
        productRepositoryAdmin.ByNameDelete(name);
        return "redirect:/admin/products2";
    }

    @GetMapping("/edit2/{name}")
    public String proUpdate2(@PathVariable("name") String name, Model model1) {
        ProductEntity product = productRepository.getProduct(name);
        model1.addAttribute("pro", product);
        return "UpdateProduct2";
    }
    @PostMapping("/save2")
    public String save2(ProductEntity products) {
        ProductEntity product = productRepository.getId(products.getP_id());
        product.setP_caption(products.getP_caption());
        product.setP_price(products.getP_price());
        product.setP_name(products.getP_name());
        productRepository.save(product);
        return "redirect:/admin/products2";
    }
    @GetMapping("/getorder2/{name}")
    public String getOrder2(@PathVariable("name") String name, Model model) {
        Integer userId = userRepository.getuserbyid(name);
        List<OrderEntity> order = orderRepository.getOrder(userId, String.valueOf(LocalDate.now()));
        model.addAttribute("orders", order);
        return "OrderA2";
    }

    @GetMapping("/imgUpdate2")
    public String updateImg2(Model model) {
        List<ProductEntityAdmin> product = productRepositoryAdmin.AllProducts();
        model.addAttribute("product", product);
        return "imgUpdate2";
    }

}
