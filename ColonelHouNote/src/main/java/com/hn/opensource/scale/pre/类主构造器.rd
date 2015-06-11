每个类都有主构造器，且与类定义交织在一起
主构造器的参数直接放置在类名之后
class HELLOWORLD（val value1：String，var value2：String）{...}
主构造器的参数被编译成字段，并在构造对象时初始化传入
一个类若没有显式定义主构造器自动拥有一个无参主构造器
若类中有直接执行的语句(非定义的方法、函数)，每次构造对象时皆会执行一次，不论什么样的构造器类型
如：class HELLOWORLD（val value1：String，var value2：String）{
println(“HELLOWORLD IS CREATED”)
val value3=value1+value2
}
val two = new HELLOWORLD（“WELCOME”，“HOME”）

主构造器的参数一般有四种：
value：String
生成对象私有字段，对象中没有方法使用value，则没有该字段
private val/var value：String
私有字段，私有的getter/setter方法
val/var value：String
私有字段，公有的getter/setter方法
@BeanProperty val/var value：String
私有字段，共有的Scala和JavaBean的getter/setter方法
class HELLOWORD private（主构造器）{ 类成员}
主构造器私有，只能通过辅助构造器构造对象