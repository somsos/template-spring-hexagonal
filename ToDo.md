# ToDo

- [ToDo](#todo)
  - [Current Task](#current-task)
  - [General](#general)
  - [Users](#users)
  - [Products](#products)
  - [Documentation](#documentation)
  - [Convert to template](#convert-to-template)
  - [Automated testings](#automated-testings)

## Current Task

## General

- [ ] Correct pagination when filter is applied (it gets it bad because return the page date as if there where no filter applied)
- [ ] Add relative search on dates

## Users

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


## Products

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


## Documentation

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

## Convert to template

- [X] Create essay
  - [X] Explain Architecture
  - [X] Explain how it works
  - [X] Explain what is required
  - [X] Explain how to run the project
  - [X] Start a new project using this template following the steps of 'how to use'

## Automated testings
  
- [ ] Create e2e tests using @SpringBootTest and test-containers
