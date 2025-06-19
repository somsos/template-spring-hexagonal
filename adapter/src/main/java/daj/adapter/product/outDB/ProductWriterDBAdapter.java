package daj.adapter.product.outDB;

import org.springframework.stereotype.Component;

import daj.adapter.product.outDB.entity.ProductEntity;
import daj.adapter.product.outDB.repository.ProductRepository;
import daj.adapter.product.utils.ProductMapper;
import daj.common.error.ErrorResponse;
import daj.product.visible.config.IProductConstants;
import daj.product.visible.port.dto.ProductDto;
import daj.product.visible.port.out.IProductWriterOutputPort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductWriterDBAdapter implements IProductWriterOutputPort {

  private final ProductRepository repo;

  private final ProductMapper mapper;

  private final ProductReaderDbAdapter reader;

  @Override
  public ProductDto save(ProductDto input) {
    final ProductEntity casted = mapper.modelToEntity(input);
    final var saved = this.repo.save(casted);
    final var output = mapper.entityToModel(saved);
    return output;
  }

  @Override
  public ProductDto delete(Integer toDel) {
    final var found = repo.findIfExistsById(toDel);
    if(found == null) {
      throw new ErrorResponse(IProductConstants.NOT_FOUND, 404, "not_found");
    }
    this.repo.deleteById(found);
    final var output = new ProductDto(1, null, null, null, null, null, null, null);
    return output;
  }

  @Override
  public ProductDto update(Integer id, ProductDto newInfo) {
    final var inDB = reader.findByIdOrThrow(id);
    final var merged = inDB.overwrite(newInfo);

    final ProductEntity updated = this.repo.save(merged);
    final ProductDto output = mapper.entityToModel(updated);
    return output;
  }

}
