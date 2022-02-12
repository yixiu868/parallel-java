package com.ww.juc.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Description: LongAddr与AtomicLong性能对比
 * @author xiaohua
 * @date 2021年9月25日 下午4:46:52
 */
public class LongAdderVSAtomicLongTest {

	// 执行结果，LongAddr还是快的多
//	开始执行...
//	线程数1每个线程执行次数1000000
//	LongAddr耗时33ms
//	AtomicLong耗时10ms
//	线程数10每个线程执行次数1000000
//	LongAddr耗时72ms
//	AtomicLong耗时207ms
//	线程数20每个线程执行次数1000000
//	LongAddr耗时71ms
//	AtomicLong耗时416ms
//	线程数40每个线程执行次数1000000
//	LongAddr耗时136ms
//	AtomicLong耗时902ms
//	线程数80每个线程执行次数1000000
//	LongAddr耗时268ms
//	AtomicLong耗时1842ms
//	执行完成.
	public static void main(String[] args) throws InterruptedException {
		System.out.println("开始执行...");
		testAtomicLongVSLongAddr(1, 1000000);
		testAtomicLongVSLongAddr(10, 1000000);
		testAtomicLongVSLongAddr(20, 1000000);
		testAtomicLongVSLongAddr(40, 1000000);
		testAtomicLongVSLongAddr(80, 1000000);
		System.out.println("执行完成.");
	}
	
	/**
	 * @Title: testAtomicLongVSLongAddr
	 * @Description: 执行比较
	 * @param threadCount
	 * @param times
	 * @return void
	 */
	static void testAtomicLongVSLongAddr(final int threadCount, final int times) {
		try {
			System.out.println("线程数" + threadCount + "每个线程执行次数" + times);
			long start = System.currentTimeMillis();
			testLongAddr(threadCount, times);
			System.out.println("LongAddr耗时" + (System.currentTimeMillis() - start) + "ms");
			
			long start2 = System.currentTimeMillis();
			testAtomicLong(threadCount, times);
			System.out.println("AtomicLong耗时" + (System.currentTimeMillis() - start2) + "ms");
		} catch (Exception e) {
		}
	}
	
	/**
	 * @Title: testAtomicLong
	 * @Description: AtomicLong多线程竞争增加
	 * @param threadCount
	 * @param times
	 * @throws InterruptedException
	 * @return void 
	 */
	static void testAtomicLong(final int threadCount, final int times) throws InterruptedException {
		AtomicLong atomicLong = new AtomicLong();
		List<Thread> list = new ArrayList<>();
		
		for (int i = 0; i < threadCount; i++) {
			list.add(new Thread(() -> {
				for (int j = 0; j < times; j++) {
					atomicLong.incrementAndGet();
				}
			}));
		}
		
		for (Thread thread : list) {
			thread.start();
		}
		
		for (Thread thread : list) {
			thread.join();
		}
	}
	
	/**
	 * @Title: testLongAddr
	 * @Description: LongAddr多线程竞争
	 * @param threadCount
	 * @param times
	 * @throws InterruptedException
	 * @return void
	 */
	static void testLongAddr(final int threadCount, final int times) throws InterruptedException {
		LongAdder longAdder = new LongAdder();
		List<Thread> list = new ArrayList<>();
		
		for (int i = 0; i < threadCount; i++) {
			list.add(new Thread(() -> {
				for (int j = 0; j < times; j++) {
					longAdder.add(1);
				}
			}));
		}
		
		for (Thread thread : list) {
			thread.start();
		}
		
		for (Thread thread : list) {
			thread.join();
		}
	}
}
