当一个单例对象存在同名类的时候，称为伴生对象
class HELLOWORLD{...}
objectHELLOWORLD{...}
类和其伴生对象可以互相访问私有属性，但必须存在同一个源文件中
类的伴生对象可以被访问，但并不在作用域中，如;
class HELLOWORLD{...}
object HELLOWORLD{ def NOW{...} }
HELLOWORLD 类必须通过HELLOWORLD.NOW调用伴生对象中的NOW方法，而不能直接用NOW来调用