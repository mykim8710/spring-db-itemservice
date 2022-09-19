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

## 
