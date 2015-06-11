package com.hn.java.六大法则.迪米特法则.v1;

/**
 * 
 * @author Colonel.Hou 问题：从逻辑上讲总公司应该与他的分公司耦合就可以了，与分公司的员工并没有任何联系，
 *         这样设计显示是增加了不必要的耦合
 * 
 *         解决方案：为分公司增加打印方法， 总公司进行调用打印就可以了，从而避免与分公司员工发生耦合
 * 
 * 
 */
public class Client
{
	public static void main(String[] args)
	{
		CompanyManager e = new CompanyManager();
		e.printAllEmployee(new SubCompanyManager());
	}
}
