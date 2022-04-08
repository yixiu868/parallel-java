# LockSupport

## 与wait和notify区别

park和unpark其实实现了wait和notify的功能，不过还是有一些差别

* 1、park不需要获取某个对象的锁；

* 2、因为中断的时候park不会抛出InterruptedException异常，所以需要在park之后自行判断中断状态，然后做额外的处理；

  参考代码

  com.ww.lock.locksupport.LockSupportDemo

## 与stop与resume区别

相对于线程的stop和resume，park和unpark的先后顺序并不是那么严格，stop和resume如果顺序反了，会出现死锁现象，而park和unpark不会。

这是因为park和unpark会对每个线程维持一个许可（boolean值）

* unpark调用时，如果当前线程还未进入park，则许可证为true；
* park调用时，判断许可是否为true，如果是true，则继续往下执行；如果是false，则等待，直到许可为true；