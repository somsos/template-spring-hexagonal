package daj.adapter.product.outDB;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import daj.adapter.product.outDB.entity.ProductEntity;
import daj.adapter.product.outDB.entity.ProductImageEntity;
import daj.adapter.product.outDB.repository.ImageProductRepository;
import daj.adapter.product.outDB.repository.ProductRepository;
import daj.adapter.product.utils.ProductImageMapper;
import daj.adapter.product.utils.ProductMapper;
import daj.common.error.ErrorResponse;
import daj.common.types.AppPage;
import daj.product.visible.config.IProductConstants;
import daj.product.visible.port.dto.ProductDto;
import daj.product.visible.port.dto.ProductImageDto;
import daj.product.visible.port.out.IProductReaderOutputPort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductReaderDbAdapter implements IProductReaderOutputPort {

  private final ProductRepository repo;

  private final ProductMapper mapper;

  private final ProductImageMapper imageMapper;

  private final ImageProductRepository imageRepo;

  
  public ProductEntity findByIdOrThrow(Integer id) {
    var found = this.repo.findById(id).orElse(null);
    if(found == null) {
      throw new ErrorResponse(IProductConstants.NOT_FOUND, 404, "not_found");
    }
    return found;
  }

  @Override
  public ProductDto findDetailsById(Integer id) {
    final var found = this.findByIdOrThrow(id);
    final var images = imageRepo.findByProductId(found.getId());
    ProductImageEntity.orderImagesNewerFirst(images);

    final ProductDto output = mapper.entityToModel(found);
    List<ProductImageDto> imagesOut = images.stream().map(e -> imageMapper.entityToModel(e)).toList();
    output.setImages(imagesOut);
    return output;
  }

  @Override
  public AppPage<ProductDto> findByPage(final int page, final int itemsPerPage) {
    final var pageFound = repo.findAllByOrderByIdAsc(PageRequest.of(page, itemsPerPage));
    ProductImageEntity.orderProductsImages(pageFound.getContent());
    final List<ProductDto> contentMapped = mapper.listEntitiesToModels(pageFound.getContent());
    contentMapped.forEach(pm -> {
      final var pe = pageFound.getContent().stream().filter(e -> e.getId() == pm.getId()).findFirst().orElse(null);
      if(pe != null) {
        List<ProductImageDto> imagesOut = pe.getImages().stream().map(e -> imageMapper.entityToModel(e)).toList();
        pm.setImages(imagesOut);  
      }
    });
    
    final var total = getTotalProducts();
    final var pageMapped = new AppPage<ProductDto>(contentMapped, total, page, itemsPerPage);
    return pageMapped;
  }

  private int getTotalProducts() {
    return this.repo.findAll().size();
  }

}
