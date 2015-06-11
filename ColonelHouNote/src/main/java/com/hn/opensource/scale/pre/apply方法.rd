需要构造有参数需求的伴生对象时，可定义并使用apply方法
class HELLOWORLD(var m:String,n:Char){...}
object HELLOWORLD{
def apply(n：Char)=new HELLOWORLD（“”，n）
}
val hi=HELLOWORLD（‘j’） 