package com.hn.java.模式.装饰器模式.大话;

/**
 * 
 * @author Colonel.Hou
 * 
 * 装饰对象的共同父类
 *
 */
import java.util.List;

public abstract class OptionalCalculator extends Calculator
{
	protected Calculator calculator;

	public void setCalculator(Calculator calculator)
	{
		this.calculator = calculator;
	}

	public abstract double getResult(List<Double> list);
}
