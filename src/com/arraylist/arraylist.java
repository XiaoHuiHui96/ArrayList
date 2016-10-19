package com.arraylist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class arraylist<E> {

	/**
	 *  存放集合的元素 
	 *  
	 */
	private transient Object[] elementData;
	/** 元素的大小 */
	private int size;
	
	/**
	 * 根据指定大小初始化
	 * @param initialCapacity
	 */
	public arraylist(int initialCapacity){
		super();
		if(initialCapacity<=0){
			//抛异常
			throw new IllegalArgumentException("初始化参数不能小于0");
		}else{
			//初始化数组
			this.elementData=new Object[initialCapacity];
		}
	}
	/**
	 * 默认初始化
	 */
	public arraylist(){
		this(10);
	}
	/**
	 * 根据一个集合类初始化
	 * @param c 一个必须继承了Collection接口的类
	 */
	public arraylist(Collection<? extends E> c){
		//初始化
		elementData=c.toArray();
		size=elementData.length;
		//如果不是任意类型的数组就转换Objec类型
		if (elementData.getClass() != Object[].class){
			elementData=Arrays.copyOf(elementData,size, Object[].class);
		}
	}
	
	/**
	 * 扩容集合
	 * @param minCapacity
	 */
	public void ensureCapacity(int minCapacity){
		/** 当前数组的大小  */
		int oldCapacity = elementData.length;  
	    if (minCapacity > oldCapacity) {
	    	/**
	    	 * oldData 虽然没有被使用，但是这是关于内存管理的原因和Arrays.copyOf()方法不是线程安全
	    	 * oldData在if的生命周期内引用elementData这个变量，所以不会被GC回收掉
	    	 * 当Arrays.copyOf()方法在把elementData复制到newCapacity时，就可以防止新的内存或是其他线程分配内存是elementData内存被侵占修改
	    	 * 当结束是离开if，oldData周期就结束被回收
	    	 */
	        Object oldData[] = elementData;  
	        int newCapacity = (oldCapacity * 3)/2 + 1;  //增加50%+1
	            if (newCapacity < minCapacity)  
	                newCapacity = minCapacity;  
	      //使用Arrays.copyOf把集合的元素复制并生成一个新的数组
	      elementData = Arrays.copyOf(elementData, newCapacity);  
	    }  
	}
	
	/**
	 * 检查索引是否出界
	 * @param index
	 */
	private void RangeCheck(int index){
		if(index > size || index < 0){
			throw new IndexOutOfBoundsException("下标超出,Index: " + index + ", Size: " +size);
		}
	}
	
	/**
	 * 添加元素
	 * 将指定的元素添加到集合的末尾
	 * @param e 添加的元素
	 * @return
	 */
	public boolean add(E e){
		ensureCapacity(size+1);
		elementData[size]=e;
		size++;
		return true;
	}
	
	/**
	 * 添加元素
	 * 将元素添加到指定的位置
	 * @param index 指定的索引下标
	 * @param element 元素
	 * @return
	 */
	public boolean add(int index, E element){
		RangeCheck(index);
		ensureCapacity(size+1);
		// 将 elementData中从Index位置开始、长度为size-index的元素，  
		// 拷贝到从下标为index+1位置开始的新的elementData数组中。  
		// 即将当前位于该位置的元素以及所有后续元素右移一个位置。
		System.arraycopy(elementData, index, elementData, index+1, size-index);
		elementData[index]=element;
		size++;//元素加一
		return true;
	}
	
	/**
	 * 添加全部元素
	 *  按照指定collection的迭代器所返回的元素顺序，将该collection中的所有元素添加到此列表的尾部。  
	 * @param c
	 * @return
	 */
	public boolean addAll(Collection < ? extends E>c){
		Object[] newElement=c.toArray();
		int elementLength=newElement.length;
		ensureCapacity(size+elementLength);
		//从newElement 0的下标开始，elementLength个元素，elementData size的下标 
		System.arraycopy(newElement, 0, elementData, size, elementLength);
		size+=elementLength;
		return elementLength!=0;
	}
	
	/**
	 * 指定位置，添加全部元素
	 * @param index 插入位置的下标
	 * @param c 插入的元素集合
	 * @return
	 */
	public boolean addAll(int index, Collection<? extends E> c){
		if(index > size || index < 0){
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " +size);
		}
		Object[] newElement=c.toArray();
		int elementLength=newElement.length;
		ensureCapacity(size+elementLength);
		int numMoved=size-index;
		//判断插入的位置是否在数组中间
		if(numMoved>0){
			//把index插入位置的后面的所有元素往后移
			//elementData index下标开始的numMoved个元素插入到elementData 的index+elementLength位置
			System.arraycopy(elementData, index, elementData, index+elementLength, numMoved);
		}
		//把newElement里从0开始的elementLength个元素添加到elementData index开始的位置
		System.arraycopy(newElement, 0, elementData, index, elementLength); 
		size += elementLength; 
		return elementLength != 0;
	}
	
	/**
	 * 指定下标赋值
	 * @param index
	 * @param element
	 * @return
	 */
	public E set(int index,E element){
		RangeCheck(index);
		E oldElement=(E)elementData[index];
		elementData[index]=element;
		return oldElement;
	}
	
	/**
	 * 根据下标取值
	 * @param index
	 * @return
	 */
	public E get(int index){
		RangeCheck(index);
		return (E)elementData[index];
	}
	
	/**
	 * 根据下标移除元素
	 * @param index 
	 */
	public E remove(int index){
		RangeCheck(index);
		E oldElement=(E)elementData[index];
		/** 移除的下标后面的元素数量  */
		int numMoved=size-index-1;
		//如果在数组范围内就进行移动
		if(numMoved>0)
			System.arraycopy(elementData, index+1, elementData, index, numMoved);
		//移除
		elementData[--size]=null;
		return oldElement;
	}
	
	/**
	 * 根据元素移除
	 * @param obj
	 * @return
	 */
	public boolean remove(Object obj){
		//Arraylist允许存放null,所以也要进行判断处理
		if(obj==null){
			for(int index=0;index<size;index++){
				if(elementData[index]==null){
					 remove(index);
					 return true;
				}
			}
		}else{
			for(int index=0;index<size;index++){
				if(obj.equals(elementData[index])){
					 remove(index);
					 return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 根据下标移除指定范围内的元素
	 * @param fromIndex 开始
	 * @param toIndex 结束
	 */
	protected void removeRange(int fromIndex, int toIndex){
		RangeCheck(fromIndex);
		RangeCheck(toIndex);
		//要移动的元素数
		int numMoved = size - toIndex; 
		//把toIndex后面的元素移动到fromIndex
		System.arraycopy(elementData, toIndex, elementData, fromIndex, numMoved);
		//要移除的元素数量
		int newSize=size-(toIndex-fromIndex);
		while(size!=newSize){
			elementData[--size]=null;
		} 
	}
	
	/**
	 * 把数组容量调整到实际的容量
	 */
	public void trimToSize(){
		int leng=elementData.length;
		if(size<leng){
			Object[] old=elementData;
			elementData=Arrays.copyOf(elementData, size);
		}
	}
	/**
	 * 把集合元素转换成数组
	 * @return
	 */
	public Object[] toArray(){
		return Arrays.copyOf(elementData, size);
	}
	
	public <T>T[] toArray(T[] a){
		if(a.length<size){
			return (T[]) Arrays.copyOf(elementData,size, a.getClass());
		}
		//把集合元素复制到a数组中
		System.arraycopy(elementData, 0, a, 0, size);
		 if (a.length > size){
			 for(int index=size;index<a.length;index++){
				 a[index] = null;
			 }
		 }
	      return a;  
	}
}
