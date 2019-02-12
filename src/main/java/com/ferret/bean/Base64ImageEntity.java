package com.ferret.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Administrator;
 * @since 2018/1/15;
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Base64ImageEntity {
    private String imageData;
    private String imageType; // .jpeg,.png
}
