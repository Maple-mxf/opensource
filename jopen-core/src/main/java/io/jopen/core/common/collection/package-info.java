/**
 * TODO HashSet如何检查重复元素
 * <p>
 * 当你把对象加入HashSet时，HashSet会先计算对象的hashcode值来判断对象加入的位置，同时也会与其他加入的对象的hashcode值作比较，
 * 如果没有相符的hashcode，HashSet会假设对象没有重复出现。但是如果发现有相同hashcode值的对象，这时会调用equals（）方法来检查
 * hashcode相等的对象是否真的相同。如果两者相同，HashSet就不会让加入操作成功。（摘自我的Java启蒙书《Head fist java》第二版）
 * <p>
 * TODO  Set底层数据结构
 * HashSet（无序，唯一）:哈希表或者叫散列集(hash table)
 * LinkedHashSet：链表和哈希表组成 。 由链表保证元素的排序 ， 由哈希表证元素的唯一性
 * TreeSet（有序，唯一）：红黑树(自平衡的排序二叉树。)
 *
 * @author maxuefeng
 */
package io.jopen.core.common.collection;