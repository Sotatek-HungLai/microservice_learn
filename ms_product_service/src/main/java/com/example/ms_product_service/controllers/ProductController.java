package com.example.ms_product_service.controllers;


import com.example.ms_product_service.dtos.AddProductToUserDTO;
import com.example.ms_product_service.dtos.GetProductsResponseDTO;
import com.example.ms_product_service.dtos.ProductDTO;
import com.example.ms_product_service.services.ProductService;
import com.example.ms_product_service.services.ProductUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor

public class ProductController {

    private final ProductService productService;

    private final ProductUserService productUserService;
    @PostMapping("/user-product")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(operationId = "addProductToUser")
    public void addProductToUser(@RequestBody AddProductToUserDTO addProductToUserDTO, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("authorizationHeader : " + authorizationHeader);
        productUserService.addProductToUser(addProductToUserDTO,authorizationHeader);
    }

    @PostMapping("/product")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "createProduct")
    public void createProduct(@RequestParam("image") MultipartFile image, @ModelAttribute ProductDTO productDTO) {
        productService.createProduct(productDTO, image);
    }

    @GetMapping("/product/{id}")
    @PreAuthorize("permitAll()")
    @Operation(operationId = "getProduct")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(productService.getProduct(id));
    }

    @GetMapping("/product")
    @PreAuthorize("permitAll()")
    @Operation(operationId = "getProducts")
    public ResponseEntity<GetProductsResponseDTO> getProducts(@RequestParam(value = "searchBy", required = false, defaultValue = "") String keyword,
                                                              @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                              @RequestParam(value = "size", required = false, defaultValue = "10") int size
                                                        ) {
        return ResponseEntity.ok().body(productService.getProducts(keyword, page, size));
    }

    @DeleteMapping("/product/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(operationId = "deleteProduct")
    public void deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
    }

    @PatchMapping("/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(operationId = "updateProduct")
    public void updateProduct(@PathVariable("id") Long id, @RequestParam("image") MultipartFile image, @ModelAttribute ProductDTO productDTO) {
        productService.updateProduct(id, productDTO, image);
    }
}
