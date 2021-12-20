# ruisshop-backend

前后端分离项目-后端(分布式项目)



![分布式项目结构图](https://github.com/oruilio/ruisshop-backend/blob/master/image-1.png)

共9个子项目：

- 基础环境/服务模块(3): base-serivice, repository-service,  common-service,
- 核心业务模块(3): product-service, order-service, account-service
- 辅助业务模块(3): sms-service, mq-service, gatewayservice

## 概述

- 开发环境
  - JDK1.8  
  - IDEA  
  - DataGrip

- 开发框架
  - Spring Boot 
  - Spring Cloud Alibaba
- 数据库
  - MySQL 
  - Redis
- 核心技术工具
  - MyBatis Plus 
  - Nacos
  - Rocket MQ 
  - Gateway & JWT
- 其他技术
  - Maven 
  - Swagger 
  - EasyExcel
- 子项目功能概述
  - base-serivice：不负责业务逻辑，仅用于提供基础环境
  - repository-service: 提供MySQL相关服务/配置及环境
  - common-service：提供通用服务和环境配置
  - product-service：产品相关接口实现模块
  - order-service：订单相关接口实现模块
  -  account-service：账户相关接口实现模块
  - sms-service：通过调用第三方接口实现短信验证功能模块
  - mq-service：提供RocketMQ依赖，实现向前端推送消息
  - gatewayservice：用于网关的实现模块
