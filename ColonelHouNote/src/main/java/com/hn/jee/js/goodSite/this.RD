在函数中this到底取何值，是在函数真正被调用执行的时候确定的，函数定义的时候确定不了

，分四种情况:
情况1：构造函数
    直接调用是window对象, new出来的对象的话就是当前对象
    
情况2：函数作为对象的一个属性
    如果函数作为对象的一个属性时，并且作为对象的一个属性被调用时，函数中的this指向该对象。
    var obj = {x : 10, fn : function(){console.log(this); console.log(this.x);}}
	obj.fn()
	Object {x: 10, fn: function} VM320:2
	10 
    果fn函数被赋值到了另一个变量中，并没有作为obj的一个属性被调用，那么this的值就是window，this.x为undefined。
    var obj = {x : 10, fn : function(){console.log(this); console.log(this.x);}}
    var fn1 = obj.fn;
	fn1()
	Window {top: Window, window: Window, location: Location, external: Object, chrome: Object…} VM320:2
情况3：函数用call或者apply调用
    var obj = {x : 10}; var fn = function(){console.log(this); console.log(this.x); }
	fn.call(obj)
	Object {x: 10} VM491:2
	10 
情况4：全局 & 调用普通函数
        