# Java多线程wait、notify/notifyAll
## wait()
参考链接

[Java多线程wait、notify详解](https://www.cnblogs.com/moongeek/p/7631447.html)

* 1、使当前线程阻塞，前提是必须先获得锁，一般配合synchronized关键字使用，一般在synchronized同步代码块里使用wait()、notify/notifyAll()方法。
* 2、由于wait()、notify/notifyAll()在synchronized代码块执行，说明当前线程一定获取到了锁。
  * 当前线程执行wait()方法时候，会释放当前的锁，然后让出CPU，进入等待状态。
  * 只有当notify/notifyAll()被执行时候，才会唤醒一个或多个正处于等待状态的线程，然后继续往下执行，知道执行完synchronized代码块的代码或是中途遇到wait()，再次释放锁。
* 3、notify和wait顺序不能错，如果A线程先执行notify方法，B线程再执行wait方法，那么B线程是无法被唤醒的。
* 4、notify和notifyAll的区别
  notify方法只唤醒一个等待（对象的）线程并使该线程开始执行，所以如果有多个线程等待一个对象，这个方法只会唤醒其中一个线程，选择哪个线程取决于操作系统对多线程管理的实现。notifyAll会唤醒所有等待（对象的）线程，尽管哪一个线程将会第一个处理取决于操作系统的实现。如果当前情况下多个线程需要被唤醒，推荐使用notifyAll方法。
* 5、在多线程中要测试某个条件的变化，使用if还是while？
  要注意：notify唤醒沉睡的线程后，线程会接着上次的执行位置，继续向下执行。所以在进行条件判断时候，可以先把wait()语句忽略不计进行考虑其执行步骤。显然，要确保程序一定要执行，并且保证程序直到满足一定条件再执行，要使用while进行等待，直到满足条件才继续往下执行。
  ```java
  public class K {

      // 状态锁
      private Object lock;

      // 条件变量
      private int now, need;

      public void produce(int num) {
          // 同步
          synchronized(lock) {
              // 如果当前不满足条件，无法跳出while循环往下执行
              while (now < need) {
                  try {
                      lock.wait();
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
                  System.out.println("被唤醒");
              }

              // 满足条件
              // 执行具体业务逻辑
          }
      }
  }
  ```