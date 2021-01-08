#  Dart - 类[link](https://juejin.cn/post/6844904202125180941)
![](https://user-gold-cdn.xitu.io/2019/11/26/16ea4e4443d6f545?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)
### Object

- Object 是所有类的父类
- Object 没有父类
- 一个类只能有一个父类
- 如果一个类没有显示用extends去继承一个类，那么默认其继承的是Object.

## 类的概述

普通类

1. 变量
   - 实例变量（创建对象后，使用 对象.变量名 调用）
   - 静态变量（用static修饰，使用类名.变量名 调用）
2. 函数
   - 实例函数（创建对象后，使用 对象.函数名 调用）
   - 静态函数 (用static修饰，使用类名.函数名 调用)
3. 构造函数

```
* 默认构造函数
* 自定义构造函数
* 静态构造函数（使用const修饰的构造函数)
* 重定向构造函数
* 工厂构造函数
复制代码
```

抽象类

1. 变量
   - 实例变量（其子类创建对象后，使用对象.变量名 调用）
   - 静态变量（用static修饰，使用类名.变量名 调用）
2. 函数
   - 实例函数(其子类创建对象后，使用对象.函数名调用）
   - 静态函数（用static修饰，使用类名.函数名调用）
   - 抽象函数（其子类实现该函数，创建对象后，使用对象.函数名调用）
3. 不能实例化（工厂构造函数除外）

### 普通类

- 声明一个普通类

```dart
///声明一个普通类
class A{
  //变量
  int a;
  String b;
  var c = 3;
  //函数
  void fun01(int d){
      print(c+c);
  }
  String fun02(){
    return b;
  }
}

void main() {
  var a = new A();//可省略new
  a.a; //调用实例的属性或函数
  a.fun02();
  //级联操作符..，可以连续调用对象的列属性或函数
  a
    ..a = 1
    ..b = "A"
    ..fun02();
}

```

创建类的实例，使用`new` 或`const`,`new`对应的是普通的构造函数，`const`对应的是用`const`形式构造函数

### 构造函数

- 没有返回值（factory构造方法有返回值)
- 构造函数与类名相同

默认构造函数，如果类中没有显示声明构造函数，那么会默认有个构造函数，默认构造函数是与类同名且无参数无返回值的函数

```dart
//未声明构造函数
class B {
   int a; //变量
   String b; 
}
```

##### 自定义构造函数

```dart
class  D {
  int a;
  String b;
  //如果构造函数中的参数都是给实例变量赋值，可以进行简化为this
  //D(this.a, this.b)//等价于下面
  D(int a, String c){
     this.a = a;  //变量名冲突时，可使用this
     b = c;
  }
}
```

##### 命名构造函数，一种可以为类声明多个构造函数的方式， 注意这里没有重载的概念，不能声明只有参数类型或数量不同的构造函数，使用命名构造函数实现

```dart
class  Class5 {
  int a;
  String b;
  Class5(int a, String b){
      this.a = a;
      this.b = b;
  }
    
  Class5.fun1(this.a);
    
  Class5.fun2(String b){
    this.b = b;
  }
}
```

#### 静态构造函数

- 类的对象不会改变
- 类的变量不会改变，也就是常量
- 使用final修饰变量
- 使用const修饰构造方法
- 创建实例时，使用const而不是new

```dart
class Class6{
  final int a;
  final String b;
  const Class6(this.a, this.b);
  void fun01(){
    print('Maybe6');
  }
}
void main(){
     var d = const Class6(1, "d");
}
```

重定向构造函数，在类的构造函数中，有时我们只是需要调用到另一个构造函数

```dart
class  Class7 {
    int a;
    String b;
    Class7(int a, String b){
        this.a = a;
        this.b = b;
    }
    Class7.fun1(int a){
        this.a = a;
    }
    Class7.fun2(String b):this.fun1(33);
    Class7.fun3(String b):this(123,'abc');
}
```



#### 工厂构造函数

- 使用factory 修饰构造函数
- 构造函数内有返回值，类型是当前类或其子类，此返回值可以上命名函数创建的，也可以是缓存的
- 使用new创建实例

```dart
class Logger {
  final String name;
  bool mute = false; 
  static final Map<String, Logger> _cache =
      <String, Logger>{};

  factory Logger(String name) {
    if (_cache.containsKey(name)) {
      return _cache[name];
    } else {
      final logger = Logger._internal(name);
      _cache[name] = logger;
      return logger;
    }
  }

  Logger._internal(this.name);

  void log(String msg) {
    if (!mute) print(msg);
  }
}

main() {
    var logger = Logger('UI');
    logger.log('Button clicked');
}
```

#### set get 方法

- 使用 set get 修饰属性
- 该属性不是真实存在，而是类似于一个函数，该函数内可以对类的其他属性和方法进行操作
- 读取该属性值时，调用get
- 对该属性赋值时，调用set

```dart
class Class9 {
  int a;
  int b;

  num get persons => a / 2;

  set persons(num n) {
    a = n * 2;
    b = n * 3;
  }
}

main() {
  var class9 = Class9()
    ..a = 1
    ..b = 2;
  print(class9.persons); //0.5
  class9.persons = 3;
  print(class9.a); //6
}

```

### 抽象类

- 使用abstract 修饰类
- 可定义实例方法
- 可定义抽象方法，抽象方法没有函数体
- 抽象类不能实例化(工厂构造函数除外)
- 子类继承抽象类后，必须实现所有抽象方法，除非子类也是抽象类
- 只有抽象类能定义抽象方法

定义抽象类

```dart
abstract class Class10 {
  void fun1(); //定义抽象方法
}

class Class11 extends Class10{
  @override
  void fun1(){  //实现抽象方法
    print('Class11');
  }
}
```

### 静态变量 和静态函数

- 使用static修饰的变量为静态变量
- 使用static修饰的函数为静态函数
- 静态变量和静态函数，使用类名直接调用
- 实例变量和实例函数，使用类的对象调用
- 静态变量和静态静态函数，不能访问实例变量和实例函数
- 静态函数内，不能使用this
- 普通类和抽象类都可以定义静态变量和静态函数

```dart
class Class12 {
    static int a = 3; //静态变量
    int  b  = 4; // 实例变量
    //静态方法
    static void fun1(int c){
        print(c); //44
        // print(b);// 这里会报错，静态方法内不能访问实例变量
    }
    //实例方法
    void fun2(){
      print(b); //4
    } 
 }   

```

### 枚举

- 使用enum声明枚举
- 每个枚举值都有个唯一值
- 枚举不能使用new实例化
- 使用枚举值 枚举.枚举值

```dart
enum Week{
  Monday,
  Tuesday,
  Webnesday,
  Thursday,
  Friday,
  Saturday,
  Sunday
}
```

### 继承

- 使用extends关键字表示继承
- 构造方法不能被继承
- 使用@override重写函数
- 如果继承的是抽象类，要实现所有抽象函数

定义抽象类

```dart
abstract class Parent{
   int a = 1;
   String b = "P";
   void fun1();
   void fun2(int a, int c){
      this.a = a;
      print(c);
   }
 }
```

继承抽象类

```dart
//继承抽象类
class Child extends Parent {
  String b = 'c'; //重写了父类的属性
  @override    //重写父类的抽象函数
  void fun1(){
    print(b); //child b
  }
  @override //重写了父类的函数
  void fun2(int a, int c){
     print(a+c); //3
  }
```



##### 继承普通类

- 子类至少定义一个构造函数调用父类的任一构造函数，使用：super
- 子类的每个构造函数都要继承父类的任一构造函数
- 子类可重写的函数

```dart
class Fruit{
  String a;
  int b;
  Fruit(this.a);//定义构造函数
  Fruit.num(this.a,this.b);//定义命名构造函数
  Fruit.con(c){  //定义命名构造函数
      b = c * 3;
  }
  void fun1(){
    print(a);
  }
  void fun2(){
    print(b);
  }
}

class Banana extends Fruit{
  String a;
  int b;
  int z;
  //至少需要定义一个构造函数调用父类的任一构造函数
  Banana(String a):super(a);
  Banana.con1(this.z,this.a):super.num(a,3);
  Banana.con2():super.con(3){
    z = 6;
  }
  @override 
  void fun2(){
    print(z);
    super.fun2();//调用父类的fun2
  }
}
```

### mixin( 混入)

Dart 的 implements 限制不小，比如你继承一个 dart 中的 `接口`，你必须重写这个接口里面所有的属性和方法，不管这个方法是不是抽象的

```dart

abstract class A {
  int age;
  runA();
  speakA() {}
}
abstract class B {
  runB();
}
class AA implements A, B {
  @override
  int age;
  @override
  runA() {
    return null;
  }
  @override
  runB() {
    return null;
  }
  @override
  speakA() {
    return null;
  }
}
```



- 类在Dart中只能继承一个父类
- mixins可理解为让类实现了继承多个父类的效果，但不是多继承，其目的是为了实现代码重用



这样明显限制太大，很多时候用着并不舒服，所以 Dart 提供一种新的接口类型：`Mixin`,你实现 `Mixin` 类型的接口后，这样你就可以像 java 那样去实现接口了，注意实现接口时要用：`with`

```dart
mixin AAA {
  int a;
  runA();
  speakA() {}
}

mixin BBB {
  runB();
}

class AAABBB with AAA, BBB {
  @override
  runA() {
    return null;
  }
  @override
  runB() {
    return null;
  }
}
```



### With类

- 声明一个with类，跟声明一个普通类一样的方式，区别在于，不声明构造函数，也不使用静态函数或变量，纯粹的函数和属性
- 子类声明时： with后可跟多个类，用，分开

```dart
class With1{//with类 此类不能有构造方法，不然Child2会报错
  String getName()=>'With1'; //三个类都有该方法
  String getAge()=>'With1   10';//该类独有
}
class With2 {
  String getName() => 'With2'; //三个类都有该方法
  String getColor()=> 'With2   red'; //该类独有
  int getNum()=> 6;  //该类和OtherClass 都有
  String getFruit() => 'With2 banana';
}

class  OtherClass {
  String getName()=> 'OtherClass';  //三个类都有该方法
  int getNum() => 3;  //该类和With2都有
  int getDesk()=> 333;  //该类独有
  String getPhone()=> 'OtherClass huawei';  
  String getFruit()=> 'OtherClass apple';
}

class Child2 extends OtherClass with With1,With2{
  //重写父类
String getPhone(){
  return 'Child2 iphone';
}
@override
String getFruit(){
  return 'Child2 orange';
 }
}

class  Child3 extends OtherClass with With2,With1 {

}
main() {
  var child = Child2();
  print(child.getPhone());//Child2 iphone
  print(child.getName());//With2
}
```

- A extends B With C,D{}
- A如果继承B，并拥有了C和D所有的属性和函数，可以用A的实例直接调用C,D的属性和方法
- 如果B有函数fun1,A重写了这个函数，那么以后A的实例调用的fun1,都是A重写后的方法
- 如果B有函数fun1,C、D中也有函数fun1,A重写了这个函数，那么以后A的实力调用的fun1,都是A重写后的方法
- 如果B有函数fun1,C、D中也有函数fun1，A没有重写这个函数，那么以后A的实例调用的fun1,是声明方法时最后的那个类的函数，优先级是从后往前（前提是子类没有重写函数）

### 隐式接口

- 与java不同，dart中没有专门定义接口的方式，dart中类即是接口
- 一个类可以实现多个接口，也就是可以实现多个类，用implements
- 一个类只能继承一个类，用extends;

```dart
class X{
  int x = 12;
  void funX(){
    print('X - x');
  }
}
class Y{
  String y = 'yy';
  void funY(){
    print('Y - y');
  }
}
class Z implement X,Y{
  @override 
  int x = 23;

  @override 
  String y = 'yy12';

  @override 
  void funX(){
    print(Z - x);
  }
  @override 
  void funY(){
    print('Z -y');
  }
}
```

- Z 实现了类 X Y ，那么Z中必须重写X Y 中所有的属性和函数
- 如果X Y 中有同名方法，但是参数个数或返回值不同时，dart会提示使用With，implements已经无法处理
- 如果X Y中有方法，其返回值，名字，参数个数和类型都一样，那么在Z中重写一次就行

#### extends/with/implements

- 三者可同时存在，但声明时有先后顺序
- class A extends B with C implements D{}
- 三者作用不同，extends是继承体系，单继承；mixins是做扩展功能，复用代码，可以理解import了一个工具类，implements是接口实现