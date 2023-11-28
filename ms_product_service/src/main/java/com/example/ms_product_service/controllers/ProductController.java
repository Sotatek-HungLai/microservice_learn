package com.example.ms_product_service.controllers;


import com.example.ms_product_service.dtos.AddProductToUserDTO;
import com.example.ms_product_service.dtos.ProductDTO;
import com.example.ms_product_service.dtos.UpdateProductAmountDTO;
import com.example.ms_product_service.services.ProductService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping("/user-product")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void addProductToUser(@RequestBody AddProductToUserDTO addProductToUserDTO, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("authorizationHeader : " + authorizationHeader);
        productService.addProductToUser(addProductToUserDTO,authorizationHeader);
    }

    @PostMapping("/product")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestParam("image") MultipartFile image, @ModelAttribute ProductDTO productDTO) {
        productService.createProduct(productDTO, image);
    }

    @GetMapping("/product/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(productService.getProduct(id));
    }

    @GetMapping("/product")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ProductDTO>> getProducts(@RequestParam(value = "searchBy", required = false, defaultValue = "") String keyword,
                                                        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                        @RequestParam(value = "size", required = false, defaultValue = "10") int size
                                                        ) {
        return ResponseEntity.ok().body(productService.getProducts(keyword, page, size));
    }

    @DeleteMapping("/product/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
    }

    @PatchMapping("/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateProduct(@PathVariable("id") Long id, @RequestParam("image") MultipartFile image, @ModelAttribute ProductDTO productDTO) {
        productService.updateProduct(id, productDTO, image);
    }

    @GetMapping("/hello")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> hello() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok().body(email);
    }

    @PutMapping("/product/amount")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @ResponseStatus(HttpStatus.OK)
    public void updateAmountProduct(@RequestBody UpdateProductAmountDTO updateProductAmountDTO) {
        productService.updateProductAmount(updateProductAmountDTO);
    }
}
