package com.example.ms_product_service.services;

import com.example.ms_product_service.dtos.AddProductToUserDTO;
import com.example.ms_product_service.dtos.ProductDTO;
import com.example.ms_product_service.dtos.UpdateProductAmountDTO;
import com.example.ms_product_service.dtos.UserDTO;
import com.example.ms_product_service.entities.ProductEntity;
import com.example.ms_product_service.exceptions.CloudinaryException;
import com.example.ms_product_service.exceptions.NotFoundException;
import com.example.ms_product_service.repositories.ProductRepository;
import com.example.ms_product_service.utils.ProductConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductConverter productConverter;

    private final CloudinaryService cloudinaryService;

    private final UserService userService;

    public void createProduct(ProductDTO productDTO, MultipartFile image) {
        Map m = cloudinaryService.upload(image);
        if (m == null || m.get("url") == null) {
            throw new CloudinaryException("Error uploading image");
        }

        String imagePath = (String) m.get("url");
        ProductEntity productEntity = productConverter.convertToEntity(productDTO);
        productEntity.setImagePath(imagePath);
        productRepository.save(productEntity);
    }

    public ProductDTO getProduct(Long id) {
        ProductEntity foundProductEntity = productRepository.findById(id).orElseThrow(NotFoundException::new);
        return productConverter.convertToDto(foundProductEntity);
    }

    public List<ProductDTO> getProducts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<ProductEntity> productEntityList;
        if (keyword.isEmpty()) {
            productEntityList = productRepository.findAll(pageable).getContent();
        } else {
            productEntityList = productRepository.findByNameContainingIgnoreCaseOrderByNameAsc(keyword, pageable).getContent();
        }
        return productEntityList.stream().map(productConverter::convertToDto).toList();
    }


    public void deleteProduct(Long id) {
        ProductEntity foundProductEntity = productRepository.findById(id).orElseThrow(NotFoundException::new);
        String publicId = getPublicId(foundProductEntity.getImagePath());
        cloudinaryService.delete(publicId);
        productRepository.deleteById(id);
    }

    private String getPublicId(String imagePath) {
        String fileName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
        return "products/" + fileName.substring(0, fileName.lastIndexOf("."));
    }

    public void updateProduct(Long id, ProductDTO productDTO, MultipartFile image) {
        ProductEntity foundProductEntity = productRepository.findById(id).orElseThrow(NotFoundException::new);
        if (productDTO.getName() != null) {
            foundProductEntity.setName(productDTO.getName());
        }

        if (productDTO.getDescription() != null) {
            foundProductEntity.setDescription(productDTO.getDescription());
        }

        if (!image.isEmpty()) {
            String publicId = getPublicId(foundProductEntity.getImagePath());
            cloudinaryService.delete(publicId);
            Map m = cloudinaryService.upload(image);
            if (m == null || m.get("url") == null) {
                throw new CloudinaryException("Error uploading image");
            }
            String imagePath = (String) m.get("url");
            foundProductEntity.setImagePath(imagePath);

        }
        productRepository.save(foundProductEntity);
    }

    public void addProductToUser(AddProductToUserDTO addProductToUserDTO, String authorizationHeader) {
        ProductEntity productEntity = productRepository.findById(addProductToUserDTO.productId()).orElseThrow(NotFoundException::new);
        UserDTO userDTO = userService.getUserProfile(addProductToUserDTO.userId(), authorizationHeader);
        log.info("userDTO : {}", userDTO.toString());
        if (userDTO.getId() != null) {
            productEntity.setUserId(userDTO.getId());
            productRepository.save(productEntity);
        }

    }

    public void updateProductAmount(UpdateProductAmountDTO updateProductAmountDTO) {
        ProductEntity productEntity = productRepository.findById(updateProductAmountDTO.id()).orElseThrow(NotFoundException::new);
        productRepository.updateAmount(productEntity.getId(), updateProductAmountDTO.amount());
    }
}
