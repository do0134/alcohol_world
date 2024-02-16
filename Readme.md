## 목차

- [목차](#목차)
  - [1. 프로젝트 개요](#1-프로젝트-개요)
  - [2. 실행 명령어](#2-실행-명령어)
  - [3. 시스템 아키텍처](#3-시스템-아키텍처)
  - [4. ERD](#4-erd)
  - [5. API 명세서](#5-api-명세서)


### 1. 프로젝트 개요
- 뉴스피드 기반 SNS와 예약 구매 기능을 구현한 프로젝트

### 2. 실행 명령어

```
docker-compose up -d
```

### 3. 시스템 아키텍처
![alt text](/readme_image/image.png)

### 4. ERD

**User**
![alt text](/readme_image/user_erd.png)

**activity**
![alt text](/readme_image/activity_erd.png)


**Order, Item**
![alt text](/readme_image/image-1.png)

### 5. API 명세서

**종합**
[Total](https://www.notion.so/c22dd4071756475393da255f8e87c640?pvs=21)

**UserService**
[UserService ](https://www.notion.so/0deac2cd0ac34b258fc64a8cae810de2?pvs=21)
**ActivityService**
[ActivityService](https://www.notion.so/384f01d2d60540a4ac5ae428765c022f?pvs=21)
**NewsfeedService**
[NewsfeedService](https://www.notion.so/4bd827fee7204dc4a25b80882874d20b?pvs=21)
**ItemService**
[ItemService(:8084)](https://www.notion.so/4be898da330043e2a61a21386571505c?pvs=21)

**OrderService**
[OrderService(:8085)](https://www.notion.so/b49b52a62b904ebc91a22a3ccd4b6cb2?pvs=21)



