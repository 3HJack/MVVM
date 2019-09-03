# MVVM

基于Google MVVM架构和Jetpack组件而设计的一套多Tab、Feed流页面解决方案，整套代码精心设计，下面只是简单
描述，这套方案的优越性还需要开发者自己去阅读源码以及实践。

## MVVM的特点

- [x] 引入[scwang90/SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)实现下拉刷新，并解决了HeadView，FootView的问题
- [x] 采用官方 CoordinatorLayout 实现HeadView
- [x] 采用官方 DataSource,PagedList,PagedAdapter分页加载框架，支持加载到倒数第X个item时自动加载下一页，天生支持DiffUtil，高性能
- [x] 对Google 的 Jetpack组件进行了封装，规范了用法，降低了开发者的使用门槛
- [x] 整套方案，设计优雅，代码的可维护性，可扩展性，可读性都很强，可以帮助开发者设计出优雅且健壮的APP

## 在工程中引用

Via Gradle: 在项目根目录的build.gradle的如下位置添加 <br><br>
maven { url "https://dl.bintray.com/onepiece/maven" }

```
allprojects {
    repositories {
        google()
        jcenter()
        maven { url "https://dl.bintray.com/onepiece/maven" }
    }
}
```

For Pre-AndroidX
```
implementation 'com.hhh.onepiece:mvvm:0.0.1'
```
For AndroidX:
```
implementation 'com.hhh.onepiece:mvvm-x:0.0.1'
```

## 实现原理
- 受Google MVVM设计思想启发而设计，引入了Jetpack、RxJava等诸多优秀的组件


## 其他
- 欢迎提Issue与作者交流
- 欢迎提Pull request，帮助 fix bug，增加新的feature，让MVVM变得更强大、更好用
