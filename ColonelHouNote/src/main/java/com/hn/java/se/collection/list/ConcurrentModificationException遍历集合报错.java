package com.hn.java.se.collection.list;


/**
 * ArrayList的remove方法只是修改了modCount的值，并没有修改expectedModCount，
 * 导致modCount和expectedModCount的值的不一致性，当next()时则抛出
 * ConcurrentModificationException异常  因此使用Iterator遍历集合时，不要改动被迭代的对象，
 * 可以使用 Iterator 本身的方法 remove() 来删除对象，Iterator.remove() 
 * 方法会在删除当前迭代对象的同时维护modCount和expectedModCount值的一致性。
 * @author 123
 *
 */
public class ConcurrentModificationException遍历集合报错
{

}
