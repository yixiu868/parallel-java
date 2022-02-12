package com.ww.juc.cow;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description: CopyOnWriteArrayList基本操作
 * @author xiaohua
 * @date 2021年9月24日 上午10:42:25
 */
public class CopyOnWriteArrayListDemo {

	public static void main(String[] args) {
		
		CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
		
		// add() 操作
		// iterator() 迭代器
		boolean addBoolean = list.add(1);
		System.out.println("是否添加成功：" + addBoolean);
		Iterator<Integer> iterator = list.iterator();
		while (iterator.hasNext()) {
			System.out.println("遍历：" + iterator.next());
		}
		
		// add(index, e) 指定位置添加元素
		list.add(0, 2);
		// indexOf(o) 获取元素索引
		System.out.println("2在list位置:" + list.indexOf(2));
		// lastIndexOf(E) 获取元素最后索引
		list.add(0, 1);
		System.out.println("获取最后一个1索引位置:" + list.lastIndexOf(1));
		// remove() 移出元素
		System.out.println("移出0位置元素之前");
		for (Integer i : list) {
			System.out.print(i + " ");
		}
		System.out.println();
		list.remove(0);
		System.out.println("移出0位置元素之后");
		for (Integer i : list) {
			System.out.print(i + " ");
		}
		// set(index, e) 指定位置替换位置上元素
		list.set(1, 3);
		System.out.println("\n调用set之后");
		for (Integer i : list) {
			System.out.print(i + " ");
		}
	}
}
