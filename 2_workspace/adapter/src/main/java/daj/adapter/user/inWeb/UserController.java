package daj.adapter.user.inWeb;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import daj.adapter.user.inWeb.reqAndResp.UserResponse;
import daj.adapter.user.utils.IUserMapper;
import daj.common.types.AppPage;
import daj.user.visible.port.dto.UserDto;
import daj.user.visible.port.in.IUserCrudInputPort;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {

  final static public String POINT_BY_PAGE = "/users/page";
  
  final private IUserCrudInputPort userCrudIP;

  final private IUserMapper mapper;


  @GetMapping(POINT_BY_PAGE)
  public AppPage<UserResponse> findByPage(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int itemsPerPage,
    @RequestParam(defaultValue = "id") String sortBy,
    @RequestParam(defaultValue = "ASC") String sortDirection // ASC or DESC
  ) {
    final var pageRequest = this._buildPageRequest(page, itemsPerPage, sortBy, sortDirection);
    final AppPage<UserDto> pageFound = userCrudIP.findByPage(pageRequest);

    //create response
    final var contentMapped = mapper.listDtoToResponse(pageFound.getContent());
    //final var pageResponse = new PageImpl<ProductDetailsResponse>(contentMapped, pageFound.getPageable(), pageFound.getSize());
    final var pageResponse = new AppPage<UserResponse>(contentMapped, pageFound.getTotalElements(), pageFound.getPageNumber(), itemsPerPage);
    return pageResponse;
  }


  private PageRequest _buildPageRequest(int page, int itemsPerPage, String sortBy, String sortDirection) {
    final var direction = Sort.Direction.fromString(sortDirection);
    final var order = Sort.by(direction, sortBy);
    final var pageRequest = PageRequest.of(page, itemsPerPage, order);
    return pageRequest;
  }

}
