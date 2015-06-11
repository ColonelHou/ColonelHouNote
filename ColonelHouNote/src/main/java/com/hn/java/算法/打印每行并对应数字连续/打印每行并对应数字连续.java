package com.hn.java.算法.打印每行并对应数字连续;


/*
 * @Title      : 
 * @Package    : default
 * @Description: 找规律打印数组
 * @author     : 
 * @date       : 2015-07-31 
 *    0
 *   1 2
 *  3 4 5
 * 6 7 8 9
 */
public class 打印每行并对应数字连续 {

	/**
	 * @Description 计算当前行的首数字值, [使用递归来计算]
	 * @param currLine 当前行号  正上方值 + 当前行-1
	 * @return 当前行的第一个数值
	 */
	public static int computCurrLineFirstVal(int currLine ){
		if(currLine == 1){
			return 0;
		}else{
			return computCurrLineFirstVal(currLine - 1) + currLine - 1;
		}
	}
	
	public static void main(String[] args) {
		
		//打印多少行
		int lineNum = 6;
		
		//迭代所有行
		for (int currentLine = 1; currentLine <= lineNum; currentLine++) {
			
			for (int i = 0; i < lineNum - currentLine; i++) {
				System.out.print(" ");
			}
			
			//每行的值
			for (int everyLineNum = 0; everyLineNum < currentLine; everyLineNum++) {
				int val = computCurrLineFirstVal(currentLine) + everyLineNum;
				if(String.valueOf(val).length() < 2){
					System.out.print(val + "  ");
				} else {
					System.out.print(val + " ");
				}
			}
			System.out.println();
		}
	}
}
