http://www.cnblogs.com/wangfupeng1988/

函数是一种对象

简述【执行上下文】
console.log(a)
ReferenceError: a is not defined
console.log(a); var a;
undefined
console.log(a); var a=13;
undefined
第一句报错，a未定义，很正常。第二句、第三句输出都是undefined，说明浏览器在执行console.log(a)时，
已经知道了a是undefined，但却不知道a是10（第三句中）
每条语句执行前先做准备工作:包括对变量的声明，而不是赋值。变量赋值是在赋值语句执行的时候进行的。

console.log(f1); function f1(){}
undefined
console.log(f2); var f2 = function ()();
console.log(f2); var f2 = function (){};
undefined 
--------------------------------------------------------------------------------------
在“准备工作”中完成了哪些工作：
    变量、函数表达式——变量声明，默认赋值为undefined；
    this——赋值；
    函数声明——赋值；

这三种数据的准备情况我们称之为“执行上下文”或者“执行上下文环境”。