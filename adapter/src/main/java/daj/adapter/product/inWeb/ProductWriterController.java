package daj.adapter.product.inWeb;

import org.springframework.web.bind.annotation.RestController;

import daj.adapter.product.inWeb.reqAndResp.ProductActionResponse;
import daj.adapter.product.inWeb.reqAndResp.ProductSaveRequest;
import daj.adapter.product.inWeb.reqAndResp.ProductUpdateRequest;
import daj.adapter.product.utils.ProductMapper;
import daj.common.depends.user.UserMDto;
import daj.product.visible.config.IProductConstants;
import daj.product.visible.port.dto.ProductDto;
import daj.product.visible.port.in.IProductWriteInputPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequiredArgsConstructor
public class ProductWriterController {

  private final IProductWriteInputPort writerIP;

  private final ProductMapper mapper;
  
  @PostMapping("/products")
  @ResponseStatus(HttpStatus.CREATED)
  public ProductActionResponse save(@Valid @RequestBody ProductSaveRequest input, Authentication auth) {
    final ProductDto mapped = mapper.saveRequestToEntity(input);
    final var authUser =  (UserMDto)auth.getPrincipal();
    mapped.setOwner(authUser);

    final var saved = writerIP.save(mapped);
    final var response = new ProductActionResponse(saved.getId(), "product saved");
    return response;
  }

  @DeleteMapping(IProductConstants.POINT_PRODUCTS_ID)
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ProductActionResponse delete(@PathVariable Integer id) {
    final ProductDto deleted = writerIP.delete(id);

    final var response = new ProductActionResponse(deleted.getId(), "product deleted");
    return response;
  }

  @PutMapping(IProductConstants.POINT_PRODUCTS_ID)
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ProductDto update(@PathVariable("id") Integer id, @Valid @RequestBody ProductUpdateRequest input) {
    final ProductDto mapped = mapper.updateRequestToEntity(input);
    final ProductDto newInfo = writerIP.update(id, mapped);
    return newInfo;
  }

  

}
