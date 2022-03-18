package com.company.optomsavdo.AdminPage.dto;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDto {
    private MultipartFile img;
    private Integer p_id;
    private String p_name;
    private String p_caption;
    private String p_price;
    private String p_type;
    private String menu;

}
