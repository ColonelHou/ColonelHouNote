类能有任意多的辅助构造器
辅助构造器的名称为this，在类中定义
辅助构造器必须以一个主构造器或其他已定义的辅助构造器调用开始
class HELLOWORLD{
private var value1=“”
private var value2=“”
def this(m:String){
this() //调用主构造器
this.value1=m}
def this(m:String,n:String){
this(m) //调用已定义的辅助构造器
this.value2=n}}