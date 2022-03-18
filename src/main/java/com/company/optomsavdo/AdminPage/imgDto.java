package com.company.optomsavdo.AdminPage;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class imgDto {
private MultipartFile img;
private String name;

}
