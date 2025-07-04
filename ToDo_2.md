# ToDo

Users

- [X] Add roles
  - [X] Adding test to check import.sql and Entity models are working as expected
  - [ ] Only user_admin role can add new roles to users

- [X] Add register
  - [X] Search roles in JWTFilter
  - [X] Check protection by different roles, for example /add-product, /auth/users/all

- [X] use docker Postgres containers for tests

- [X] Pass Hashing to user module so the check happen inside this module

- [ ] Fixes
  - [ ] Show error to user when hit with two slashes ex. '<http://localhost:8080//products>'
  - [ ] Separate in AuthConfig by adapter.domain.
  - [X] Update user picture when is updated by second time.

- [ ] Add module products
  - [X] Add product
  - [X] Get by ID
  - [X] Delete
  - [X] Update
  - [X] Get products by page
  - [X] Product images
    - [X] Upload
    - [X] Show image by id image
    - [X] Get images by product id
    - [X] delete image by id
    - [X] check delete images when product is deleted
  - [X] don't delete, mark as deleted
  - [X] Add owner to product
  - [ ] Convert from ProductDto constructor to builder

- [X] Understand mapping and find out if there is a simpler way.

- [ ] Documentation
  - [X] Include Architecture diagram to repository
  - [ ] Sincronice Diagram with project folder structure
    - [X] Separate Request/Response objects and keep just DTO in user domain module
    - [X] In domain modules make changes to see clear what part is public and what one is private
    - [X] remove dependencies of adapter.user to anything outside of user.port
    - [X] Export diagram from .drawio to .png (and that it looks fine)
    - [X] Sync diagram architecture and code.
    - [X] **IMPORTANT** Add ArchUnit tests, to check that the adapter just
          access to the public part of the domain module.
    - [ ] Check what other diagrams should be useful
      - [ ] Onion diagram
    - [ ] Left clear
    - [ ] Write about how if one whats to save a byte[] in DB we need to give them a special compress
    - [ ] Homologate README.md of Backend with front end
    - [ ] Second try of how to use template of backend
    - [ ] Add document of feature and demonstration
      - [ ] Check of api endpoints document with last version
    - [ ] Add swagger
    - [ ]

- [X] Create template from this project
  - [X] Create essay
    - [X] Explain Architecture
    - [X] Explain how it works
    - [X] Explain what is required
    - [X] Explain how to run the project
    - [X] Start a new project using this template following the steps of 'how to use'

- [ ] Testing
  - [ ] Create e2e tests using @SpringBootTest and test-containers

- [current] Frontend
  - Check ToDo of frontend

- [Bugs]
  - [X] Fix error on images product cascade on product delete
  - [X] Avoid check token when the request have method OPTIONS (for cors)
  - 

curl 'http://localhost:8080/users/21/pictures' \
  --header "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiItMTAwIiwiaWF0IjoxNzQzOTAyMzk4LCJleHAiOjE3NDM5MDk1OTh9.QNwFa3bXIsHOJeiXvudt1N2gbx9fhI-2O-aa-WeLoFc" \
  --data-raw $'------WebKitFormBoundaryM0KAAKnV5Dd2jyBn\r\nContent-Disposition: form-data; name="picture"; filename="cuadro_amarillo.png"\r\nContent-Type: image/png\r\n\r\n\r\n------WebKitFormBoundaryM0KAAKnV5Dd2jyBn--\r\n'


curl -v \
  --header "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiItMTAwIiwiaWF0IjoxNzQzOTAyMzk4LCJleHAiOjE3NDM5MDk1OTh9.QNwFa3bXIsHOJeiXvudt1N2gbx9fhI-2O-aa-WeLoFc" \
  -F picture=@./temporal/small_black.png  \
  'http://localhost:8080/users/1/pictures'












EXIT_STATUS=$?
if [ $EXIT_STATUS -eq 124 ]
    then
        echo 'Process Timed Out!'
    else
        echo 'Process did not timeout. Something else went wrong.'
fi

timeout 5 docker logs tmp_sleep
docker rm tmp_sleep

exit $EXIT_STATUS

