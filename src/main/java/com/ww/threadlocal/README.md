# ThreadLocal

[ThreadLocal](https://mp.weixin.qq.com/s?__biz=MzU4NzA3MTc5Mg==&mid=2247484118&idx=1&sn=9526a1dc0d42926dd9bcccfc55e6abc2&scene=21#wechat_redirect)

## 常见面试问题

* 1）工作中有用过ThreadLocal吗

  用过，用在对时间进行格式化SimpleDateFormat，达到在格式化时间时，线程安全的目的。

* 2）Spring中ThreadLocal使用场景

  Spring事务

  Spring事务中ThreadLocal使用，key为DataSource，value为Connection（为了应对多数据源的情况，所以是一个Map）

  用ThreadLocal保证了同一个线程获取一个Connection对象，从而保证一次事务所有操作都需要在同一个数据库连接上。

* 3）ThreadLocal内存泄漏

  ![d4f54e734efe8dfb79f59bc62a5ad0ba_640_wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1](img\d4f54e734efe8dfb79f59bc62a5ad0ba_640_wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1.webp)

