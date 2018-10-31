# 7.4 Encapsulation(封装)
   封装（ Encapsulation ）是面向对象的三大特征之一（另外两个特征是继承和多态） ， 指的
   是将对象的状态信息隐藏在对象内部，不允许外部程序直接访问对象内部信息，而是通过该类
   所提供的方法来实现对内部信息的操作和访问。
   
   封装是面向对象编程语言对客观世界的模拟，在客观世界里，对象的状态信息都被隐藏在对象内部，外界无法直接操作和修改。对一个类或对象实现良好的封装，可以实现以下目的。
   
    ·隐藏类的实现细节。
    ·让使用者只能通过事先预定的方法来访问数据，从而可以在该方法里加入控制逻辑，限制对属性的不合理访问。
    ·可进行数据检查，从而有利于保证对象信息的完整性。
    ·便于修改，提高代码的可维护性。
    
   为了实现良好的封装，需要从两个方面考虑。
   
    ·将对象的属性和实现细节隐藏起来，不允许外部直接访问。
    ·把方法暴露出来，让方法来控制对这些属性进行安全的访问和操作。
   因此，封装实际上有两个方面的含义：把该隐藏的隐藏起来，把该暴露的暴露出来。封装需要通过使用包和访问权限修饰符（也称为可见性修饰符， Visibility Modifier ）来实现。
   
## 7.4.1 包和导包
 一旦在 Kotlin 源程序中使用了这条 package 语句 ， 就意味着该源程序中定义的所有类、函数都属于这个包。位于包中的每个类的完整类名都应该是包名和类名的组合，如果其他人需要使用该包中的类，也应该使用包名加类名的组合。
  
  Hello.kt
 
```kotlin
package lee
    
fun test() {
    println("简单的 test()函数")
}
class Foo(name: String) {
    var name = name
}
fun main(args: Array<String>) {
    println("带包的主函数")
    lee.test()
}
```
    
上面程序中粗体字代码表明把 test函数和Foo类都放在 lee 包空间下。把上面的源文件保存在任意位置，编译这个源文件会多出一个名为 lee 的文件夹，该文件夹中则有 Foo.class和 HelloKt.class 文件  
程序中test()函数的完整函数名是 lee.test(),Foo 类的完整类名是 lee.Foo 

如果希望使用 Java 来调用上面的 test()函数，由于 Kotlin 实际上还会为上面程序生成一个 HelloKt 类，而 test()函数将以 HelloKt 类的类方法而存在，因此程序应该通过lee.HelloKt.test()的形式来调用 test() 函数


    源程序的包名大致按如下规则命名:公司域名倒写.项目名.模块名.组件类型
    
```kotlin
import java.util.*
import java.sql.Date as SDate//Kotlin的import 语句支持 as 关键字，这样就可以为导入类指定别名，从而解决了导入两个不同包中的同名类的问题

fun main(args: Array<String>) {
    //使用 java . util . Date
    var d = Date()
    //使用java.sql . Date
    var d2 = SDate(System.currentTimeMillis())
    println(d)
    println(d2)
}
```
## 7.4.2 Kotlin 的默认导入
Kotlin 默认会导入如下包。

    kotlin.*
    kotlin.annotation.*
    kotlin.collections.*
    kotlin.comparisons.*(自Kotlin1.1起)
    kotlin.io.*
    kotlin.ranges.*
    kotlin.sequences.*
    kotlin.text.*


对于JVM平台 ， 还会自动导入如下两个包
    
    java.lang.*
    kotlin.jvm.*
   
 ## 7.4.3 使用访问控制符
 >private:与Java 的 private 类似，private成员只能在该类的内部或文件的内部被访问。
 
 > internal:internal成员可以在该类的内部或文件的内部或者同一个模块内被访 问 。
 
 > protected:protected 成员可以在该类 的内 部或文件的内 部或者其子类 中被访问。
 
 > public: public 成员可以在任意地方被访问 。
 
 ```kotlin
open class Outer {
    private val a = 1
    protected open val b = 2
    internal val c = 3
    val d = 4 //默认是 public 访问权限

    protected class Nested {
        public val e: Int = 5
    }
}

class Subclass : Outer() {
    // a 不可访问
    // b 、 c 、 d 可访问
    // Nested 和 e 可访问
    override val b = 5 //被重写的 b 依然是 protected 访问权限
}
class Other(o: Outer) {
   var a =o;
   // o.a, o.b 不可访问
    // o.c 与 Other 类在同一个模块中，可以被访问
    // o.d 可访问
    // Outer.Nested 不可访问， Nested: :e 也不可访问
}
```