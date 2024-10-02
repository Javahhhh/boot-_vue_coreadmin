# 前后端分离权限管理系统：

基于 SpringBoot、SpringSecurity、JWT、MybatisPlus、Mysql、Vue等主流技术

## 项目预览：

![image-20240511233531992](https://github.com/Javahhhh/boot-_vue_coreadmin/assets/112531492/4cf1b958-129f-476f-8761-9a1ea8ed6518)
![image-20240511233407984](https://github.com/Javahhhh/boot-_vue_coreadmin/assets/112531492/1ea41b2e-4680-4777-8bc6-2de30d6f663e)

![image-20240511233318621](https://github.com/Javahhhh/boot-_vue_coreadmin/assets/112531492/b6e1b42c-60c2-4e41-943d-6cba826718cc)


![image-20240511233243401](https://github.com/Javahhhh/boot-_vue_coreadmin/assets/112531492/ac696dde-6df3-49cc-8e49-00e46fd0997d)




## 特色：

**简洁易用**：基于 [vue-element-admin](https://gitee.com/panjiachen/vue-element-admin) 升级的 Vue3 版本

**数据交互**：同时支持本地 `Mock` 和线上接口，配套 [Java 后端源码](https://gitee.com/youlaiorg/youlai-boot)和[在线接口文档](https://gitee.com/link?target=https%3A%2F%2Fwww.apifox.cn%2Fapidoc%2Fshared-195e783f-4d85-4235-a038-eec696de4ea5)。

**权限管理**：用户、角色、菜单、字典、部门等完善的权限系统功能。

**基础设施**：动态路由、按钮权限、代码规范、Git 提交规范、常用组件封装。



## 前端项目启动

建议前后端分开启动

环境：Node ≥18

```
# 克隆代码
git clone https://github.com/Javahhhh/boot-_vue_coreadmin.git

# 切换目录
cd vue3-element-admin

# 安装 pnpm
npm install pnpm -g

# 安装依赖
pnpm install

# 启动运行
pnpm run dev
```

#### 连接后端接口

修改 `.env.development` 文件中的 `VITE_APP_API_URL` 的值，将其从 [http://vapi.youlai.tech](https://gitee.com/link?target=http%3A%2F%2Fvapi.youlai.tech) 更改为 http://localhost:8989。





## 后端简介

基于 JDK 17、Spring Boot 3、Spring Security 、JWT、Redis、Mybatis-Plus、Knife4j、Vue 3、Element-Plus 构建的前后端分离单体权限管理系统。

- **🚀 开发框架**: 使用 Spring Boot 3 和 Vue 3，以及 Element-Plus 等主流技术栈。
- **🔐 安全认证**: 结合 Spring Security 和 JWT 提供安全、无状态、分布式友好的身份验证和授权机制。
- **🔑 权限管理**: 基于 RBAC 模型，实现细粒度的权限控制，涵盖接口方法和按钮级别。
- **🛠️ 功能模块**: 包括用户管理、角色管理、菜单管理、部门管理、字典管理等多个功能。



## 接口文档

- `knife4j` 接口文档：[http://localhost:8989/doc.html](https://gitee.com/link?target=http%3A%2F%2Flocalhost%3A8989%2Fdoc.html)
- `swagger` 接口文档：[http://localhost:8989/swagger-ui/index.html](https://gitee.com/link?target=http%3A%2F%2Flocalhost%3A8989%2Fswagger-ui%2Findex.html)
- `apifox`  在线接口文档：[https://www.apifox.cn/apidoc](https://gitee.com/link?target=https%3A%2F%2Fwww.apifox.cn%2Fapidoc%2Fshared-195e783f-4d85-4235-a038-eec696de4ea5)

## 🚀 后端启动

1. **数据库初始化**

   执行 [youlai_boot.sql](https://gitee.com/youlaiorg/youlai-boot/blob/master/sql/mysql8/youlai_boot.sql) 脚本完成数据库创建、表结构和基础数据的初始化。

2. **修改配置**

   [application-dev.yml](https://gitee.com/youlaiorg/youlai-boot/blob/master/src/main/resources/application-dev.yml) 修改MySQL、Redis连接配置；

3. **启动项目**

   执行 [SystemApplication.java](https://gitee.com/youlaiorg/youlai-boot/blob/master/src/main/java/com/youlai/system/SystemApplication.java) 的 main 方法完成后端项目启动；

   访问接口文档地址 [http://ip:port/doc.html](https://gitee.com/link?target=http%3A%2F%2Flocalhost%3A8989%2Fdoc.html) 验证项目启动是否成功。



# 参考：

https://gitee.com/youlaiorg/vue3-element-admin











