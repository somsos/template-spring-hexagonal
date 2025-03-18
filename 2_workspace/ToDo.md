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
    - [X] **IMPORTANT** Add ArchUnit tests, to check that the adapter just access to the public part of the domain module
    - [ ] Check what other diagrams should be useful
      - [ ] Onion diagram
    - [ ] Left clear

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
