背景：Spring框架是以简化Java EE应用程序的开发为目标而创建的 

# 第一部分（Spring核心）

Spring容器、 依赖注入（dependency injection， DI） 和面向切面编程（aspect-oriented programming，AOP） ， 也就是Spring框架的核心 



#### 第1章 Spring之旅 

1.1 简化java开发

​     简单的JavaBean实现之前只有EJB才能完成的事情

​     Spring用bean或者JavaBean来表示应用组件，但并不意味着Spring组件必须要遵循JavaBean规范。 一个Spring组件可以是任何形式的POJO

​     4种关键策略降低Java开发的复杂性 
​         A：基于POJO的轻量级和最小侵入性编程；
​         B：通过依赖注入和面向接口实现松耦合；
​         C：基于切面和惯例进行声明式编程；
​         D：通过切面和模板减少样板式代码。







 1.1.2 依赖注入

```java
public class DamselRescuingKnight implements Knight {
//任何一个有实际意义的应用都会由两个或者更多的类组成， 这些类相互之间进行协作来完成特定的业务逻辑,
  private RescueDamselQuest quest;
//传统的做法， 每个对象负责管理与自己相互协作的对象（ 即它所依赖的对象） 的引用， 这将会导致高度耦合和难以测试的代码
  public DamselRescuingKnight() {
    this.quest = new RescueDamselQuest();//与RescueDamselQuest紧密耦合
  }

  public void embarkOnQuest() {
    quest.embark();
  }

}
```

紧密耦合的代码难以测试、 难以复用、 难以理解

```java
public class BraveKnight implements Knight {
     private Quest quest; //所有探险任务都必须实现的一个接口 ,不和任何特定Quest实现发生耦合 

    public BraveKnight(Quest quest) {//BraveKnight没有自行创建探险任务， 而是在构造的时候把探险任务作为构造器参数传入。 这是依赖注入的         方式之一 构造注入（constructor injection） 
         this.quest = quest;
     }
     public void embarkOnQuest() {
         quest.embark();
     }
}
```



对象的依赖关系将由系统中负责协调各对象的第三方组件在创建对象的时候进行设定。 对象无需自行创建或管理它们的依赖关系
DI所带来的最大收益——松耦合。 如果一个对象只通过接口（而不是具体实现或初始化过程） 来表明依赖关系， 那么这种依赖就能够在对象本身毫不知情情况下， 用不同的具体实现进行替换



```java
public class BraveKnightTest {
//对依赖进行替换的一个最常用方法就是在测试的时候使用mock实现 
 @Test
 public void knightShouldEmbarkOnQuest() {
 	Quest mockQuest = mock(Quest.class);//使用mock框架Mockito去创建一个Quest接口的mock实现 
 	BraveKnight knight = new BraveKnight(mockQuest);
 	knight.embarkOnQuest();
 	verify(mockQuest, times(1)).embark();
 }

}
```



创建应用组件之间协作的行为通常称为装配（wiring） 。 Spring有多种装配bean的方式， 采用XML是很常见的一种装配方式

**基于XML的配置**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="http://www.springframework.org/schema/beans 
 http://www.springframework.org/schema/beans/spring-beans.xsd">

 <bean id="knight" class="sia.knights.BraveKnight">	<!--创建Quest Bean-->
 <constructor-arg ref="quest" />
 </bean>

 <bean id="quest" class="sia.knights.SlayDragonQuest">	<!--创建SlayDragonQuest-->
 <constructor-arg value="#{T(System).out}" />
 </bean>

</beans>
```

BraveKnight和SlayDragonQuest被声明为Spring中的bean 

BraveKnight 来讲，构造时传入了对SlayDragonQuest bean的引用， 将其作为构造器参数。  

SlayDragonQuest bean的声明使用了Spring表达式语言（Spring Expression Language） ， 将System.out（这是一个PrintStream） 传入到了SlayDragonQuest的构造器中 

**Spring还支持使用Java来描述配置 (java配置)**

```java
@Configuration
public class KnightConfig {

  @Bean
  public Knight knight() {
    return new BraveKnight(quest());
  }
  
  @Bean
  public Quest quest() {
    return new SlayDragonQuest(System.out);
  }

}
```

如何工作

Spring通过应用上下文（Application Context） 装载bean的定义并把它们组装起来。 Spring应用上下文全权负责对象的创建和组装。 Spring自带了多种应用上下文的实现， 它们之间主要的区别仅仅在于如何加载配置 

​	基于knights.xml文件创建了Spring应用上下文 

```java

public class KnightMain {

  public static void main(String[] args) throws Exception {
    //因为knights.xml中的bean是使用XML文件进行配置的， 所以选择ClassPathXmlApplicationContext[1]作为应用上下文相对是比较合适的。该类加载位于应用程序类路径下的一个或多个XML配置文件
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/knight.xml");//加载spring上下文
    Knight knight = context.getBean(Knight.class);//获取bean
    knight.embarkOnQuest();//使用bean
    context.close();
  }

}
```

1.1.3 应用切面
DI能够让相互协作的软件组件保持松散耦合， 而面向切面编程（aspect-oriented programming， AOP） 允许你把遍布应用各处的功能分离出来形成可重用的组件 

面向切面编程往往被定义为促使软件系统实现关注点的分离一项技术 

系统由许多不同的组件组成， 每一个组件各负责一块特定功能。除了实现自身核心的功能之外， 这些组件还经常承担着额外的职责。诸如日志、 事务管理和安全这样的系统服务经常融入到自身具有核心业务逻辑的组件中去， 这些系统服务通常被称为横切关注点， 因为它们会跨越系统的多个组件 

如果将这些关注点分散到多个组件中去， 你的代码将会带来双重的复杂性。

- 实现系统关注点功能的代码将会重复出现在多个组件中。 这意味着如果你要改变这些关注点的逻辑， 必须修改各个模块中的相关实现。 即使你把这些关注点抽象为一个独立的模块， 其他模块只是调用它的方法， 但方法的调用还是会重复出现在各个模块中。

- 组件会因为那些与自身核心业务无关的代码而变得混乱。 一个向地址簿增加地址条目的方法应该只关注如何添加地址， 而不应该关注它是不是安全的或者是否需要支持事务 

![](https://github.com/sundyyh/study/blob/master/imgs/aop_system_relation.jpg)

左边的业务对象与系统级服务结合得过于紧密。 每个对象不但要知道它需要记日志、 进行安全控制和参与事务， 还要亲自执行这些服务 



AOP能够使这些服务模块化， 并以声明的方式将它们应用到它们需要影响的组件中去。 所造成的结果就是这些组件会具有更高的内聚性并且会更加关注自身的业务， 完全不需要了解涉及系统服务所带来复杂性。 总之， AOP能够确保POJO的简单性 

我们可以把切面想象为覆盖在很多组件之上的一个外壳。 应用是由那些实现各自业务功能的模块组成的。 借助AOP， 可以使用各种功能层去包裹核心业务层。 这些层以声明的方式灵活地应用到系统中， 你的核心应用甚至根本不知道它们的存在。 这是一个非常强大的理念， 可以将安全、 事务和日志关注点与核心业务逻辑相分离 

![](https://github.com/sundyyh/study/blob/master/imgs/aop_system_relation_after.jpg)

​	不使用AOP

```java
public class Minstrel {
//在骑士执行每一个探险任务之前， singBeforeQuest()方法会被调用；在骑士完成探险任务之后， singAfterQuest()方法会被调用。 
  private PrintStream stream;
  
  public Minstrel(PrintStream stream) {
    this.stream = stream;
  }
//在这两种情况下， Minstrel都会通过一个PrintStream类来歌颂骑士的事迹， 这个类是通过构造器注入进来的
  public void singBeforeQuest() {
    stream.println("Fa la la, the knight is so brave!");
  }

  public void singAfterQuest() {
    stream.println("Tee hee hee, the brave knight " +
    		"did embark on a quest!");
  }

}

//将BraveKnight和Minstrel组合起来
public class BraveKnight implements Knight {
     private Quest quest;
     private Minstrel minstrel;
    //因为骑士需要知道吟游诗人， 所以就必须把吟游诗人注入到BarveKnight类中。 这不仅使BraveKnight的代码复杂化了，而且还让我疑惑是否还需要一个不需要吟游诗人的骑士呢？ 如果Minstrel为null会发生什么呢？ 我是否应该引入一个空值校验逻辑来覆盖该场景
    public BraveKnight(Quest quest,Minstrel minstrel) {
         this.quest = quest;
         this.minstrel = minstrel;
     }
     public void embarkOnQuest() {
//管理吟游诗人不是骑士职责范围内的工作，吟游诗人应该做他份内的事， 根本不需要骑士命令他这么做。 毕竟， 用诗歌记载骑士的探险事迹， 这是吟游诗人的职责。 为什么骑士还需要提醒吟游诗人去做他份内的事情呢
         minstrel.singBeforeQuest();
         quest.embark();
         minstrel.singAfterQuest();
     }
}//但利用AOP， 你可以声明吟游诗人必须歌颂骑士的探险事迹， 而骑士本身并不用直接访问Minstrel的方法
```

要将Minstrel抽象为一个切面， 你所需要做的事情就是在一个Spring配置文件中声明它。 更新后的knights.xml文件， Minstrel被声明为一个切面 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:aop="http://www.springframework.org/schema/aop"
  xsi:schemaLocation="http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

  <bean id="knight" class="sia.knights.BraveKnight">
    <constructor-arg ref="quest" />
  </bean>

  <bean id="quest" class="sia.knights.SlayDragonQuest">
    <constructor-arg value="#{T(System).out}" />
  </bean>
<!--把Minstrel声明为一个bean,在<aop:aspect>元素中引用该bean-->
  <bean id="minstrel" class="sia.knights.Minstrel">
    <constructor-arg value="#{T(System).out}" />
  </bean>

  <bean id="fakePrintStream" class="sia.knights.FakePrintStream" />
<!--使用了Spring的aop配置命名空间把Minstrel bean声明为一个切面-->
  <aop:config>
    <aop:aspect ref="minstrel">
      <!--切入点，并配置expression属性来选择所应用的通知。 表达式的语法采用的是AspectJ的切点表达式语言-->
      <aop:pointcut id="embark"
          expression="execution(* *.embarkOnQuest(..))"/>
      <!--声明（使用<aop:before>） 在embarkOnQuest()方法执行前调用Minstrel的singBeforeQuest()方法。 这种方式被称为前置通知（before advice）-->
      <aop:before pointcut-ref="embark"  method="singBeforeQuest"/>
	  <!--after advice:pointcut-ref属性都引用了名字为embank的切入点-->
      <aop:after pointcut-ref="embark"  method="singAfterQuest"/>
    </aop:aspect>
  </aop:config>

</beans>
```

Minstrel是一个普通POJO，Spring的上下文中， Minstrel实际上已经变成一个切面 了，Minstrel可以被应用到BraveKnight中，而BraveKnight不需要显式地调用它。 实际上， BraveKnight完全不知道Minstrel的存在 

1.1.4 使用模板消除样板式代码 

为了实现通用的和简单的任务， 你不得不一遍遍地重复编写这样的代码 ，板式代码的一个常见范例是使用JDBC访问数据库查询数据 

举个例子，如果你曾经使用过jdbc，相信你写过如下类似的代码

```java
public Employee getEmployeeById(long id){
	Connection conn=null;
	PreparedStatement stmt=null;
	ResultSet rs=null;
	try{
		conn=dataSource.getConnection();
		...
	}catch(SQLException e){...}
	finally{
	if(rs!=null){try{...}catch(...){...}}
	...
	}
}
```

正如你所看到，少量的查询代码淹没在一堆JDBC样板式代码中，建立链接关闭链接。JDBC不是产生样板式代码的唯一场景，JMS/JNDI/REST服务等等都会产生大量的重复代码。
Spring旨在通过模版封装来消除样板式代码，举个例子，使用Spring的JdbcTemplate重写getEmployeeById()方法仅仅关注获取员工数据的核心逻辑，而不需要迎合JDBC API的需求

```java
public Employee getEmployeeById(long id){
	return jdbcTemplate.queryForObject{
		"select id,firstname,lastname,salary form emplyee where id=?",
		new RowMapper<Employee>(){
			public Employee mapRow(ResultSet rs,int rowNom)throws SQLExcepion{
				Employee employee=new Employee();
				employee.setId(rs.getLong("id"));
				employee.setFirstName(rs.getString("firstname"));
				...//各种set
				return employee;
			}
		},
		id);//指定查询参数
}
```

新版本的getEmployeeById()简单多了，而且仅仅关注于从数据库中查询员工。模版的queryForObject()方法需要一个SQL查询语句，一个RowMapper对象（把数据映射为一个域对象），零个或多个查询参数 

##### 1.2 容纳你的Bean 

在基于Spring的应用中， 你的应用对象生存于Spring容器（container）中。 Spring容器负责创建对象， 装配它们， 配置它们并管理它们的整个生命周期， 从生存到死亡（在这里， 可能就是new到finalize()） 

容器是Spring框架的核心。 Spring容器使用DI管理构成应用的组件，它会创建相互协作的组件之间的关联。 毫无疑问， 这些对象更简单干净， 更易于理解， 更易于重用并且更易于进行单元测试 

Spring容器并不是只有一个。 Spring自带了多个容器实现， 可以归为两种不同的类型。 bean工厂（由org.springframework. beans.factory.BeanFactory接口定义） 是最简单的容器， 提供基本的DI支持。 **应用上下文**（由org.springframework.context.ApplicationContext接口定义） 基于BeanFactory构建， 并提供应用框架级别的服务， 例如从属性文件解析文本信息以及发布应用事件给感兴趣的事件监听者。虽然我们可以在bean工厂和应用上下文之间任选一种， 但bean工厂对大多数应用来说往往太低级了， 因此， 应用上下文要比bean工厂更受欢迎。 我们会把精力集中在应用上下文的使用上， 不再浪费时间讨论bean工厂 

Spring自带了多种类型的应用上下文。 下面罗列的几个是你最有可能遇到的。

- AnnotationConfigApplicationContext： 从一个或多个基于Java的配置类中加载Spring应用上下文。
- AnnotationConfigWebApplicationContext： 从一个或多个基于Java的配置类中加载Spring Web应用上下文。
- ClassPathXmlApplicationContext： 从类路径下的一个或多个XML配置文件中加载上下文定义， 把应用上下文的定义文件作为类资源。
- FileSystemXmlapplicationcontext： 从文件系统下的一个或多个XML配置文件中加载上下文定义。
- XmlWebApplicationContext： 从Web应用下的一个或多个XML配置文件中加载上下文定义 

```mermaid
graph LR
A[方形] -->B(圆角)
    B --> C{条件a}
    C -->|a=1| D[结果1]
    C -->|a=2| E[结果2]
    F[横向流程图]
​```
```