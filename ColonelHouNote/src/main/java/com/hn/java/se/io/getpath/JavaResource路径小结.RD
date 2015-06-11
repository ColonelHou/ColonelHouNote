首先一点很重要，Java中不存在标准的相对路径，各种相对路径取资源的方式都是基于某种规则转化为绝对路劲

然后一点也很重要，绝对不要直接使用绝对路径，否则死得很难看

基于以上两点，总结Resource路径问题无非归结为一点：找基点，也就是在某种环境下（web、j2ee或jar包等）通过合适的方式找到一个稳定的基点，然后通过这个基点找到你要的resource

Java中的基点有哪些呢？大致总结一下有以下几种：

1）classpath

如果你要找的资源在classpath下，那么通过classpath这个基点是比较合适的，而取得这个基点方式主要是通过ClassLoader来，具体方法就是 ClassLoader.getResource(String name)，而取得ClassLoader的方式很多，比如：

Thread.currentThread().getContextClassLoader()
clazz.getClassLoader()
ClassLoader. getSystemClassLoader()
ClassLoader找resource的实现原理就是先递归在parent classLoader中从所在classpath里加载resource（最终如何加载JDK未开源），如果所有层级的classLoader都未找到，则调用findResource方法来找，而这个方法是暴露给自制classLoader来现实的，因此给了在classpath之外加载resource的机会。

2） 当前用户目录

就是相对于System.getProperty("user.dir" )返回的路径， 对于一般项目，这是项目的根路径。对于JavaEE服务器，这可能是服务器的某个路径。这个并没有统一的规范！ 然而， 默认情况下，java.io 包中的类总是根据当前用户目录来分析相对路径名，如new File("xxx")，就是在 System.getProperty("user.dir" )路径下找xxx文件。因此，通过这种方式来定位文件可能会出现移植问题。

3） Web应用程序的根目录

在Web应用程序中，我们一般通过ServletContext.getRealPath("/" )方法得到Web应用程序的根目录的绝对路径。


掌握了上面几个基点，就能很轻松得定位你要找的resource，只不过要清晰地认识到不要只图一时快活，而不管将来移植的死活，要确保能任何环境下（j2se or web，windows or linux）不出问题。