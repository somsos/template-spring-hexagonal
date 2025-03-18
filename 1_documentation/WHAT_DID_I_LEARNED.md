# WHAT DID I LEARNED

- [WHAT DID I LEARNED](#what-did-i-learned)
  - [Mapping](#mapping)
    - [Why I choose Two ways mapping](#why-i-choose-two-ways-mapping)
    - [Pitfalls for mapping objects](#pitfalls-for-mapping-objects)
  - [Errors](#errors)
    - [org.springframework.data.jpa.repository.Query and @Entity](#orgspringframeworkdatajparepositoryquery-and-entity)
  - [Considerations to start a project](#considerations-to-start-a-project)
    - [Don't use Dates, use Timestamps](#dont-use-dates-use-timestamps)

## Mapping

Whe have several ways to manage our objects to transport and work with our data,
in the book *Get Your Hands Dirty on Clean Architecture* talks about 4;
1'No Mapping', 2'Two ways', 3'One way' and 4'Full' each one with their good
and bad parts. In this project I tried the 3 first ways and here are some observations.

The way it seemed me more equilibrated is **Two ways** so I'm going to use this
one for the template project that I'm building, and explain more in this section.

**No Mapping**, Uses only one DTO and/or Model, it makes that one change in a
feature can affect other features, but is easy to understand and implement at
the start, but I predict is difficult to maintain.

**Two ways**, Uses several objects in the adapter one for each input use case,
for example, ProductSaveRequest, ProductUpdateRequest and both map to ProductModel,
is a little more complex to implement but if you make a change in one part it
does not affect other features.

**One way (I don't try it again)** In my case tried to use interfaces, trying to
reflect the intention of the group of data, for example, ProductUserInput (name, price, etc),
ProductDBRecord(id, createdAt, deletedAt), PublicInfo(id, name price).
at first worked well but then it was very confusing, because the mapping it does
not occurs as I thought, sometimes the casting between interfaces worked and others
not, so sometimes i used mapping and others casting what lead me to a disorder.

**Full** I really didn't try this one because is to complex for a simple project,
and for what i see I can evolve from two ways mapping to full mapping, so I keep
this by the time the project get more complex.

### Why I choose Two ways mapping

- It's simple enough to start a simple project
- can evolve to full mapping that is for more complex projects
- The complexity is absorbed by the adapter and keep it simple for the domain
- The idea is simple likely to all the team understand it.

The is that for the adapter we can have several object, commonly one for use case
but all these ones should map to the DTO of the domain, that is the public object
of our domain to speak with the outside world, so as I already know the public
part of our domain module should be the more simple posible, so when we are trying
to understand the whole is easier for us.

[Diagram of two ways]

### Pitfalls for mapping objects

Caution It could be tempting to inherent from the DTO, but this is not a good
idea, because **ProductSaveRequest is not a ProductDTO**, the first one is to get the
input with the intention of create a new product, so we do not need data as id,
createdAt, images, etc, and with the DTO our intention is to pass information from one
module to another, and with a more simple communication between modules better,
so better use a few DTOs.

Remember that the only responsibility of our domain is the business logic, so
**the adapter has the mapping responsibility**. And in a clean architecture the
domain doesn't depend of outside layers.

**Save boilerplate** using libraries in my case `mapstruct` is easy to use and
I try to don't make it do complex things as map lists inside od the object to map,
for this the library give us the option to implement it ourselves.

## Errors

### org.springframework.data.jpa.repository.Query and @Entity

I had an error

```java

org.hibernate.query.sqm.UnknownEntityException: 
//Could not resolve root entity 'UserEntity' JPA and Spring Boot

//In this line
@Query("select p.id from ProductEntity p where p.id = ?1")

// trying to use this entity
@Entity(name = "products")      // <- here is the error
@Table()                        // <- here is the error
public class ProductEntity { ... }

```

this because it is different to use name in @Entity or @Table.

when is used it in @Entity as above change the name to look in the HQL so in the
case of above should be `@Query("... products ...")`.

when is used it in @Table change the name in the DB motor.

Solution: be careful where we use the parameter name.

```java
@Entity() // <- remove name, so keeps the same name of the class on the HQL
@Table(name = "products") 
public class ProductEntity { ... }

-----

@Query("select p.id from ProductEntity p where p.id = ?1")
```

## Considerations to start a project

### Don't use Dates, use Timestamps

When you add a Date to save in a database, in my case the field created_at, i had
the problem od when I started to order them by creating_date, they didn't change
the order as I wanted because they had been created the same day.

And the recommended way is to save the timestamp that includes
