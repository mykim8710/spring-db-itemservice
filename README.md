# Spring - DB Study project

## Project Spec
- 프로젝트 선택
    - Project: Gradle Project
    - Spring Boot: 2.7.3
    - Language: Java
    - Packaging: Jar
    - Java: 11
- Project Metadata
    - Group: com.example
    - Artifact: item-service
    - Name: spring-db-itemservice
    - Package name: com.example.itemservice
- Dependencies: **Spring Web**, **Thymeleaf**, **Lombok**
- DB : H2 database

## Package Design
- start
```
└── src
    ├── main
    │   ├── java
    │   │   └── com.example.itemservice
    │   │               ├── config
    │   │               │     └── MemoryConfig(C) 
    │   │               ├── domain
    │   │               │     ├── Item(C)           
    │   │               │     └── ItemSearchCondition(C) 
    │   │               ├── exception
    │   │               │     └── NotFoundException(C) 
    │   │               ├── repository
    │   │               │     ├── ItemRepository(I) 
    │   │               │     └── MemoryItemRepository(C)
    │   │               ├── service
    │   │               │     ├── ItemService(I)           
    │   │               │     └── ItemServiceImplV1(C) 
    │   │               ├──  web
    │   │               │      ├── controller
    │   │               │      │     ├── HomeController(C)
    │   │               │      │     └── ItemController(C)
    │   │               │      └──  dto
    │   │               │            ├──  request
    │   │               │            │      ├── RequestItemInsertDto(C)
    │   │               │            │      ├── RequestItemUpdateDto(C)
    │   │               │            │      └── RequestItemSelectDto(C)
    │   │               │            └──  response
    │   │               │                   └── ResponseItemSelectDto(C)
    │   │               ├── TestDataInit(C)
    │   │               └── ItemServiceApplication(C)
    │   └── resource
    │       ├── static
    │       │     ├──  css 
    │       │     │      └── bootstrap.min.css
    │       │     └── index.html
    │       ├── template
    │       │     ├──  css 
    │       │     │      └── bootstrap.min.css
    │       │     ├── item.html    
    │       │     ├── items.html   
    │       │     ├── addForm.html 
    │       │     └── editForm.html
    │       └── application.properties
    ├── test
    │   ├── java
    │   │   └── com.example.itemservice
    │   │                       └── repository
    │   │                                └── ItemRepositoryTest(C)
```

```
[DB Table]
drop table if exists item CASCADE;
create table item (
    id        bigint generated by default as identity,
    item_name varchar(10),
    price     integer,
    quantity  integer,
    primary key (id)
);
```

## Study 내용
1. JdbcTemplate
- JdbcTemplate을 사용해서 메모리에 저장하던 데이터를 데이터베이스에 저장

