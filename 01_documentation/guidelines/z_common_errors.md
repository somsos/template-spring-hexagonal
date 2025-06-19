# Errors

## Don't use Interfaces as dtos in Controllers or repositories

Spring use default classes (created with no-args-constructor) for its internals,
so if we use interfaces and can'r create the Request/Response/Entity will throw
this error.

```r
Could not resolve parameter [1] in public daj.product.port.in.dto.ProductModel daj.adapter.product.inWeb.ProductWriterController.update(java.lang.Integer,daj.product.port.in.dto.ProductModel): Type definition error: [simple type, class daj.product.port.in.dto.ProductModel]
```

## Don't use manual mapping, use mapstruct

As it is being use two ways mapping there is many objects like Requests, Responses
Entities, Models, etc. and all these are mapped from and to Dtos to communicate
between layers, in order to respect the boundaries of our architecture. So making a change
requiere to make several manual and repetible work, that is susceptible to mistakes,
and this *mapstruct* library could be a little confusing sometimes, but the time
invested to understand how to make a complex mapping is much less that pass it to
manual.

## Spring security endpoint auth conf doesnt load

For some reason the configuration AuthConfig do not load, and had this way

```java
@WebMvcTest({ AuthConfig.class, AuthController.class, UserUtilBeans.class })
```

Then i changed it to

```java
@WebMvcTest({ AuthController.class, UserUtilBeans.class })
@ContextConfiguration(classes = {AuthConfig.class})
```

and already worked.

