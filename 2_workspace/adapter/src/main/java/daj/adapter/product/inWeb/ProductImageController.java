package daj.adapter.product.inWeb;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import daj.adapter.product.inWeb.reqAndResp.ProductActionResponse;
import daj.common.error.ErrorResponse;
import daj.common.utils.ImageUtility;
import daj.product.visible.config.IProductConstants;
import daj.product.visible.port.dto.ProductDto;
import daj.product.visible.port.dto.ProductImageDto;
import daj.product.visible.port.in.IProductImageInputPort;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductImageController {

  private final IProductImageInputPort imageIP;

  @PostMapping(IProductConstants.POINT_PRODUCTS_IMAGE)
  @ResponseStatus(HttpStatus.CREATED)
  public ProductActionResponse uploadImage(@PathVariable("id") Integer id, @RequestParam("image") MultipartFile file) throws IOException {
    final var imageFile = ImageUtility.compressImage(file.getBytes());
    
    var imageName = "defaultName";
    if(file.getOriginalFilename() != null) {
      imageName = file.getOriginalFilename();
    }
    
    final var imageEntity = new ProductImageDto();
    imageEntity.setName(imageName);
    imageEntity.setType(file.getContentType());
    imageEntity.setImage(imageFile);
    
    final var product = new ProductDto();
    product.setId(id);
    imageEntity.setProduct(product);

    final ProductImageDto imageSaved = imageIP.saveImage(imageEntity);

    final var response = new ProductActionResponse(imageSaved.getId(), "product image saved");

    return response;
  }





  @GetMapping(IProductConstants.POINT_PRODUCTS_IMAGE_ID)
  public ResponseEntity<byte[]> getImage(@PathVariable Integer id) throws IOException {

    final ProductImageDto image = imageIP.findImageById(id);

    if(image == null) {
      throw new ErrorResponse(IProductConstants.ERROR_IMAGE_NOT_FOUND, 404, "not_found");
    }

    return ResponseEntity
      .ok()
      .contentType(MediaType.valueOf(image.getType()))
      .body(ImageUtility.decompressImage(image.getImage()));
  }




  @DeleteMapping(IProductConstants.POINT_PRODUCTS_IMAGE_ID)
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ProductActionResponse delete(@PathVariable Integer id) {
    final ProductImageDto deleted = imageIP.delete(id);

    if(deleted == null) {
      throw new ErrorResponse(IProductConstants.ERROR_IMAGE_NOT_FOUND, 404, "not_found");
    }

    final var response = new ProductActionResponse(deleted.getId(), "product image deleted");
    return response;
  }



}
