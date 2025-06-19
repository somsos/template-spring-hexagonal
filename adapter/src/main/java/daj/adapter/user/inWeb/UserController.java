package daj.adapter.user.inWeb;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import daj.adapter.user.inWeb.reqAndResp.UserAddRequest;
import daj.adapter.user.inWeb.reqAndResp.UserAddResponse;
import daj.adapter.user.inWeb.reqAndResp.UserPictureUploadResponse;
import daj.adapter.user.inWeb.reqAndResp.UserResponse;
import daj.adapter.user.inWeb.reqAndResp.UserUpdateRequest;
import daj.adapter.user.utils.IUserMapper;
import daj.common.error.ErrorResponse;
import daj.common.types.AppPage;
import daj.common.types.PageAndFilterRequestDto;
import daj.common.utils.ImageUtility;
import daj.user.visible.port.dto.UserDto;
import daj.user.visible.port.dto.UserPictureDto;
import daj.user.visible.port.in.IUserCrudInputPort;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@AllArgsConstructor
public class UserController {

  final static public String POINT_USERS = "/users";

  final static public String POINT_USER_ID = POINT_USERS + "/{id}";

  final static public String POINT_USER_PICTURE = POINT_USER_ID + "/pictures";

  final static public String POINT_BY_PAGE = POINT_USERS + "/page";

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
    final var pageResponse = new AppPage<UserResponse>(contentMapped, pageFound.getTotalElements(), pageFound.getPageNumber(), 5);
    return pageResponse;
  }

  @GetMapping(POINT_USER_ID)
  public UserResponse getUserById(@PathVariable Integer id) {
    final UserDto found = userCrudIP.getUserById(id);
    final UserResponse response = mapper.dtoToUserResponse(found);
    return response;
  }


  @PostMapping(POINT_USERS)
  @ResponseStatus(HttpStatus.CREATED)
  public UserAddResponse save(@RequestBody UserAddRequest entity) {
    final var casted = mapper.requestSaveToDto(entity);
    final UserDto saved = userCrudIP.save(casted);
    final UserAddResponse response = mapper.dtoToAddedResponse(saved);
    return response;
  }

  @PutMapping(POINT_USER_ID)
  @ResponseStatus(HttpStatus.ACCEPTED)
  public UserResponse update(@RequestBody UserUpdateRequest updateRequest, @PathVariable Integer id) {
    final UserDto casted = mapper.updateRequestToDto(updateRequest);
    final UserDto updated = userCrudIP.update(id, casted);
    final UserResponse response = mapper.dtoToUserResponse(updated);
    return response;
  }

  @DeleteMapping(POINT_USER_ID)
  @ResponseStatus(HttpStatus.ACCEPTED)
  public UserResponse delete(@PathVariable Integer id) {
    final UserDto deleted = userCrudIP.deleteById(id);
    final UserResponse response = mapper.dtoToUserResponse(deleted);
    return response;
  }

  @PostMapping(POINT_USER_PICTURE)
  @ResponseStatus(HttpStatus.CREATED)
  public UserPictureUploadResponse uploadImage(@PathVariable Integer id, @RequestParam MultipartFile picture) throws java.io.IOException {
    final var requestDto = new UserPictureDto();
    requestDto.setIdUser(id);

    final var imageFile = ImageUtility.compressImage(picture.getBytes());
    requestDto.setFile(imageFile);
    requestDto.setType(picture.getContentType());

    final UserPictureDto saved = userCrudIP.saveImage(requestDto);
    
    final var response = this.mapper.userPictureDtoToUserPictureUploadResponse(saved);
    return response;
  }


  @GetMapping(UserController.POINT_USER_PICTURE)
  public ResponseEntity<byte[]> seeImage(@PathVariable Integer id) throws IOException {

    final UserPictureDto userPicture = userCrudIP.findImageByUserId(id);

    if(userPicture == null) {
      throw new ErrorResponse("User picture not found", 404, "not_found");
    }

    return ResponseEntity
      .ok()
      .contentType(MediaType.valueOf("image/png"))
      .body(ImageUtility.decompressImage(userPicture.getFile()));
  }
  


  
}
