package daj.adapter.user.inWeb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import daj.adapter.user.inWeb.reqAndResp.UserResponse;
import daj.adapter.user.utils.IUserMapper;
import daj.common.types.AppPage;
import daj.common.types.PageAndFilterRequestDto;
import daj.user.visible.port.dto.UserDto;
import daj.user.visible.port.in.IUserCrudInputPort;
import jakarta.servlet.ServletRequest;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {

  final static public String POINT_BY_PAGE = "/users/page";

  final private IUserCrudInputPort userCrudIP;

  final private IUserMapper mapper;


  /*
   * Params(page:int, itemsPerPage:int, sortBy:DBProp, sortDirection:asc|desc, ...filters:[:DBProp])
   */
  @GetMapping(POINT_BY_PAGE)
  public AppPage<UserResponse> findByPage(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int itemsPerPage,
    @RequestParam(defaultValue = "id") String sortBy,
    @RequestParam(defaultValue = "ASC") String sortDirection, // ASC or DESC
    @RequestParam(defaultValue = "") String query // overall search
  ) {
    final var pageAndFilterRequest = new PageAndFilterRequestDto(page, itemsPerPage, sortBy, sortDirection, query);
    final AppPage<UserDto> pageFound = userCrudIP.findByPage(pageAndFilterRequest);

    //create response
    final var contentMapped = mapper.listDtoToResponse(pageFound.getContent());
    //final var pageResponse = new PageImpl<ProductDetailsResponse>(contentMapped, pageFound.getPageable(), pageFound.getSize());
    final var pageResponse = new AppPage<UserResponse>(contentMapped, pageFound.getTotalElements(), pageFound.getPageNumber(), 5);
    return pageResponse;
  }

}
