Scala允许任何语法结构中镶嵌任何语法结构，因此能在类中定义类
class HELLOWORLD{
class HI{....}
}
对于同一个外部类，不同实例下的内部类是不同的
形如val three = new HELLOWORLD与val four = new HELLOWORLD
three.HI与four.HI是两个不同的类
内部类中可以调用外部类的成员，利用外部类.this或指针实现
class HELLOWORLD{ pointto =>
var value2= ““
class HI{ val value3=HELLOWORLD.this.value2
var value4=pointto.value2}}