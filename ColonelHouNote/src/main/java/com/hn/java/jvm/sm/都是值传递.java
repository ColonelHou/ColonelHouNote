package com.hn.java.jvm.sm;


/**
 * http://my.oschina.net/sunchp/blog/369707?p=2#comments
 *
 */
public class 都是值传递 {

	/**
	 * 所谓值传递，就是将实际参数值的副本（复制品）传入方法内，而参数本身不会受到任何影响
	 * @param st
	 */
	public static void swap(Student st){
		st.setAge(29);
		st.setName("jim");
	}
	
	public static void main(String[] args) {
		Student st = new Student("tom", 27);
		//Student [name=tom, age=27]
		System.out.println(st.toString());
	}
}

class Student {
	private String name;
	private int age;
	public Student(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + "]";
	}
}