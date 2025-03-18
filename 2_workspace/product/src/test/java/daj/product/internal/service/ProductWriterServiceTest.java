package daj.product.internal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import daj.common.depends.user.IUserDepends;
import daj.common.depends.user.UserMDto;
import daj.common.error.ErrorResponse;
import daj.product.visible.port.dto.ProductDto;
import daj.product.visible.port.out.IProductWriterOutputPort;

@ExtendWith(MockitoExtension.class)
public class ProductWriterServiceTest {

  @Mock
  private IProductWriterOutputPort productOutP;

  @Mock
  private IUserDepends userDepends;

  @InjectMocks
  private ProductWriterService userSrv;


  @Test
  void testSave_IfOwnerIsNull_ThrowErrorResponse() {
    final var input = new ProductDto();
    final var owner = new UserMDto(1, null);
    input.setOwner(owner);
    
    when(userDepends.findByIdOrThrow(any())).thenReturn(null);
    final var ex = assertThrows(ErrorResponse.class, () -> userSrv.save(input));
    assertEquals("User not found", ex.getMessage());
    assertEquals(400, ex.getHttpStatus());
  }



  @Test
  void testSave_success() {
    //In/Out
    final var input = new ProductDto();
    input.setOwner(new UserMDto(1, null));
    input.setName("Trompo 1");
    
    final var saved = new ProductDto();
    saved.setId(1);
    saved.setName(input.getName());
    
    //When
    when(userDepends.findByIdOrThrow(any())).thenReturn(input.getOwner());
    when(productOutP.save(any())).thenReturn(saved);

    //Test
    var got = userSrv.save(input);
    
    //assert
    assertEquals(saved.getId(), got.getId());
    assertEquals(input.getName(), got.getName());
  }
  



//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


  @Test
  void testDelete() {

  }

  @Test
  void testUpdate() {

  }
}
