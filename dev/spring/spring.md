

背景：Spring框架是以简化Java EE应用程序的开发为目标而创建的 

# 第一部分（Spring核心）

Spring容器、 依赖注入（dependency injection， DI） 和面向切面编程（aspect-oriented programming，AOP） ， 也就是Spring框架的核心 



## 第1章 Spring之旅 

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
      <aop:pointcut id="embark"    expression="execution(* *.embarkOnQuest(..))"/>
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

无论是从文件系统还是类路径装在应用上下文，将bean加载到BeanFactory的过程是类似的，区别在于一个是从文件系统路径下查找xml文件，另一个是在所有的类路径下（包含JAR文件）查找xml文件

```java
ApplicationContext context=new FileSystemXmlApplicationContext("C:/knight.xml");
//或者
ApplicationContext context=new ClassPathXmlApplicationContext("knight.xml");
//java配置中加载上下文
ApplicationContext context=new  AnnotationConfigApplicationContext(com.springinaction.knights.config.KnightConfig.class);
```

1.2.2 bean的生命周期 

图展示了bean装载到Spring应用上下文中的一个典型的生命周期过程。 

![](https://github.com/sundyyh/study/blob/master/imgs/spring_bean_lifecycle.jpg)

<kbd>实例化</kbd>-><kbd>扩充属性</kbd>-><kbd>调用BeanNameAware的setBeanName()方法 </kbd>-><kbd>调用BeanFactoryAware的setBeanFactory()方法 </kbd>-><kbd>调用ApplicationContextAware接口的setApplicationContext()方法 </kbd>-><kbd>调用BeanPostProcessor的预初始化方法 </kbd>-><kbd>调用InitializingBean的afterPropertiesSet()方法 </kbd>-><kbd>调用自定的初始化方法</kbd>-><kbd>调用BeanPostProcessor的初始化方法</kbd>-><kbd>此时， bean已经准备就绪， 可以被应用程序使用了， 它们将一直
驻留在应用上下文中， 直到该应用上下文被销毁 </kbd>-><kbd>容器关闭</kbd>-><kbd>调用DisposableBean 的destroy()方法</kbd>-><kbd>调用自定的销毁方法</kbd>

bean在Spring容器中从创建到销毁经历了若干阶段， 每一阶段都可以针对Spring如何管理bean进行个性化定制 

**1． Spring对bean进行实例化；**
**2． Spring将值和bean的引用注入到bean对应的属性中；**
**3． 如果bean实现了BeanNameAware接口， Spring将bean的ID传递给setBean-Name()方法；**
**4． 如果bean实现了BeanFactoryAware接口， Spring将调用setBeanFactory()方法， 将BeanFactory容器实例传入；**
**5． 如果bean实现了ApplicationContextAware接口， Spring将调用setApplicationContext()方法， 将bean所在的应用上下文的引用传入进来；**
**6． 如果bean实现了BeanPostProcessor接口， Spring将调用它们的post-ProcessBeforeInitialization()方法；**
**7． 如果bean实现了InitializingBean接口， Spring将调用它们的after-PropertiesSet()方法。 类似地， 如果bean使用initmethod声明了初始化方法， 该方法也会被调用；**
**8． 如果bean实现了BeanPostProcessor接口， Spring将调用它们的post-ProcessAfterInitialization()方法；**
**9． 此时， bean已经准备就绪， 可以被应用程序使用了， 它们将一直驻留在应用上下文中， 直到该应用上下文被销毁；**
**10． 如果bean实现了DisposableBean接口， Spring将调用它的destroy()接口方法。 同样， 如果bean使用destroy-method声明了销毁方法， 该方法也会被调用。**

##### ![](https://img-blog.csdn.net/2018101716393861?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L05vX0dhbWVfTm9fTGlmZV8=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

**Spring核心容器**
容器是Spring框架最核心的部分， 它管理着Spring应用中bean的创建、配置和管理。 在该模块中， 包括了Spring bean工厂， 它为Spring提供了DI的功能。 基于bean工厂， 我们还会发现有多种Spring应用上下文的实现， 每一种都提供了配置Spring的不同方式。

**Spring的AOP模块**
借助于AOP， 可以将遍布系统的关注点（例如事务和安全） 从它们所应用的对象中解耦出来
**数据访问与集成**    

消除样板式代码 简化模版。Spring提供了ORM模块 ，建立在对DAO的支持之上， 并为多个ORM框架提供了一种构建DAO的简便方式 

Spring没有尝试去创建自己的ORM解决方案， 而是对许多流行的ORM框架进行了集成， 包括Hibernate、 Java Persisternce API、Java Data Object和iBATIS SQL Maps。 Spring的事务管理支持所有的ORM框架以及JDBC 

**Web与远程调用**
MVC模式是一种普遍被接受的构建Web应用的方法，它可以帮助用户将界面逻辑与应用逻辑分离。java从来不缺少MVC框架，Apache的Struts、 JSF、 WebWork和Tapestry都是可选的，最流行的MVC框架 SpringMVC我们将在后续学习到。
远程调用功能集成了RMI/Hessian/Burlap/JAX-WS等等

## 第2章 装配Bean 

在Spring中， 对象无需自己查找或创建与其所关联的其他对象。 相反， 容器负责把需要相互协作的对象引用赋予各个对象 

**创建应用对象之间协作关系的行为通常称为装配（wiring） ， 这也是依赖注入（DI） 的本质** 

### 2.1 Spring配置的可选方案 

Spring容器负责创建应用程序中的bean并通过DI来协调这些对象之间的关系。但是， 作为开发人员， 你需要告诉Spring要创建哪些bean并且如何将其装配在一起。 当描述bean如何进行装配时， Spring具有非常大的灵活性， 它提供了三种主要的装配机制  

1. 在XML中进行显式配置。
2. 在Java中进行显式配置。
3. 隐式的bean发现机制和自动装配。 

Spring的配置风格是可以互相搭配的， 所以你可以选择使用XML装配一些bean， 使用Spring基于Java的配置（JavaConfig） 来装配另一些bean， 而将剩余的bean让Spring去自动发现 

**建议是尽可能地使用自动配置的机制。 显式配置越少越好** 

当你必须要显式配置bean的时候（比如， 有些源码不是由你来维护的， 而当你需要为这些代码配置bean的时候） ， 推荐使用类型安全并且比XML更加强大的JavaConfig。 最后， 只有当你想要使用便利的XML命名空间， 并且在JavaConfig中没有同样的实现时， 才应该使用XML 

### 2.2 自动化装配bean 

Spring从两个角度来实现自动化装配：

- 组件扫描（component scanning） ： Spring会自动发现应用上下文中所创建的bean。
- 自动装配（autowiring） ： Spring自动满足bean之间的依赖。 



2.2.1 创建可被发现的bean 

```java
public interface MediaPlayer {

  void play();

}
package soundsystem;
@Component
public class CDPlayer implements MediaPlayer {
  private CompactDisc cd;

  @Autowired
  public CDPlayer(CompactDisc cd) {
    this.cd = cd;
  }

  public void play() {
    cd.play();
  }

}

package soundsystem;

public interface CompactDisc {
  void play();
}
//注解表明该类会作为组件类， 并告知Spring要为这个类创建bean。 没有必要显式配置SgtPeppers bean， 因为这个类使用了@Component注解， 所以Spring会为你把事情处理妥当
@Component
public class SgtPeppers implements CompactDisc {

  private String title = "Sgt. Pepper's Lonely Hearts Club Band";  
  private String artist = "The Beatles";
  
  public void play() {
    System.out.println("Playing " + title + " by " + artist);
  }
  
}
package soundsystem;
//如果没有其他配置的话， @ComponentScan将扫描与配置类相同的包以及这个包下的所有子包， 查找带有@Component注解的类
@Configuration
@ComponentScan//，组件扫描默认是不启用的,需要显式配置一下Spring，，从而命令它去寻找带有@Component注解的类， 并为其创建bean
public class CDPlayerConfig { 
}
```

XML来启用组件扫描 

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans><!--省略命名空间-->
  <context:component-scan base-package="soundsystem" />
</beans>

```

为了测试组件扫描的功能， 我们创建一个简单的JUnit测试，它会创建Spring上下文， 并判断CompactDisc是不是真的创建出来了 

<a name="CDPlayerTest">CDPlayerTest</a>

```java

@RunWith(SpringJUnit4ClassRunner.class)//使用了Spring的SpringJUnit4ClassRunner， 以便在测试开始的时候自动创建Spring的应用上下文
@ContextConfiguration(classes=CDPlayerConfig.class)//@ContextConfiguration会告诉它需要在CDPlayerConfig中加载配置
public class CDPlayerTest {
//来源于System Rules库（http://stefanbirkner.github.io/systemrules/index.html） 的一个JUnit规则， 该规则能够基于控制台的输出编写断言。 在这里， 我们断言SgtPeppers.play()方法的输出被发送到了控制台上
  @Rule
  public final StandardOutputStreamLog log = new StandardOutputStreamLog();

  @Autowired
  private MediaPlayer player;
  
  @Autowired//将CompactDisc bean注入到测试代码之中
  private CompactDisc cd;
  
  @Test
  public void cdShouldNotBeNull() {
    assertNotNull(cd);
  }

  @Test
  public void play() {
    player.play();
    assertEquals(("Playing Sgt. Pepper's Lonely Hearts Club Band by The Beatles\n", log.getLog());
  }

}
```

2.2.2 为组件扫描的bean命名 

Spring应用上下文中所有的bean都会给定一个ID。 在前面的例子中，尽管我们没有明确地为SgtPeppers bean设置ID， 但Spring会根据类名为其指定一个ID。 具体来讲， 这个bean所给定的ID为sgtPeppers， 也就是将类名的第一个字母变为小写 

```java
@Component("lonelyHeartsClub")//为这个bean设置不同的ID
public class SgtPeppers implements CompactDisc {
    
}

@Named("lonelyHeartsClub")//使用Java依赖注入规范（Java Dependency Injection）中所提供的@Named注解来为bean设置ID
public class SgtPeppers implements CompactDisc {
    
}

```

2.2.3 设置组件扫描的基础包 

```java
@Configuration
@ComponentScan("soundsystem")
@ComponentScan(basePackages="soundsystem")//更加清晰地表明你所设置的是基础包
//@ComponentScan(basePackages={"soundsystem","video"})//更加清晰地表明你所设置的是基础包  !!!!!!!!这种String类型表示方式类型不安全
@ComponentScan(basePackageClasses={CDPlayer.class,DVDPlayer.class})//这些类所在的包将会作为组件扫描的基础包
public class CDPlayerConfig { 
}
```

2.2.4 通过为bean添加注解实现自动装配 

自动装配就是让Spring自动满足bean依赖的一种方法， 在满足依赖的过程中， 会在Spring应用上下文中寻找匹配某个bean需求的其他bean。 为了声明要进行自动装配， 我们可以借助Spring的@Autowired注解 

```java
@Component
public class CDPlayer implements MediaPlayer {
  private CompactDisc cd;

  @Autowired//构造器上添加@Autowired，这表明当Spring创建CDPlayer bean的时候， 会通过这个构造器来进行实例化并且会传入一个可设置给CompactDisc类型的bean
  public CDPlayer(CompactDisc cd) {
    this.cd = cd;
  }

  public void play() {
    cd.play();
  }
  
  //@Autowired//此注解还能用在属性的Setter方法上	依赖是通过带有@Autowired注解的方法进行声明的，也就是setCompactDisc()
  //public void setCompactDisc(CompactDisc cd){
  //	this.cd = cd;      
  //}

}
```

如果没有匹配的bean， 那么在应用上下文创建的时候， Spring会抛出一个异常 （可以将@Autowired的required属性设置为false   **@Autowired(required=false)**   ）

如果有多个bean都能满足依赖关系的话， Spring将会抛出一个异常，表明没有明确指定要选择哪个bean进行自动装配 

@Autowired可以换成@Inject (注解来源于Java依赖注入规范 )

2.2.5 验证自动装配 

<a href="#CDPlayerTest">CDPlayerTest</a>

### 2.3 通过Java代码装配bean 

第三方库中的组件装配到你的应用中, 通过组件扫描和自动装配无法实现 ，通过显式装配 （Java和XML）

JavaConfig是更好的方案，因为它更为强大、 类型安全并且对重构友好 

#### 2.3.1 创建配置类 

```java
@Configuration//创建JavaConfig类的关键在于为其添加@Configuration注解,表明这个类是一个配置类,该类应该包含在Spring应用上下文中如何创建bean的细节
public class CDPlayerConfig { 
}
```

#### 2.3.2 声明简单的bean 

```java
@Bean//@Bean注解会告诉Spring这个方法将会返回一个对象， 该对象要注册为Spring应用上下文中的bean
public CompactDisc SgtPappers(){//默认情况下， bean的ID与带有@Bean注解的方法名是一样的 也可通过  @Bean(name="")  指定
    return new SgtPapers();
}
```

#### 2.3.3 借助JavaConfig实现注入 

```java
@Bean
public CDPlayer cdPlayer(){
    return new CDPlayer(SgtPappers());//赖于CompactDisc,sgtPeppers()方法上添加了@Bean注解，Spring将会拦截所有对它的调用， 并确保直接返回该方法所创建的bean， 而不是每次都对其进行实际的调用 
}

@Bean
public CDPlayer cdPlayer(CompactDisc compactDisc){//当Spring调用cdPlayer()创建CDPlayerbean的时候， 它会自动装配一个CompactDisc到配置方法之中
    return new CDPlayer(compactDisc);
}
```

### 2.4 通过XML装配bean 

#### 2.4.1 创建XML配置规范 

创建一个新的配置规范。在使用JavaConfig的时候， 这意味着要创建一个带有@Configuration注解的类， 而在XML配置中， 这意味着要创建一个XML文件， 并且要以<beans>元素为根 

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="http://www.springframework.org/schema/beans 
 http://www.springframework.org/schema/beans/spring-beans.xsd">
	<bean class="soundsystem.SgtPepers"/>	<!--<bean>元素类似于JavaConfig中的@Bean注解-->
	<bean id="sgtPepers" class="soundsystem.SgtPepers"/>	<!--借助id属性-->
</beans>
```

使用XML时， 需要在配置文件的顶部声明多个XML模式（XSD） 文件， 这些文件定义了配置Spring的XML元素 

因为没有明确给定ID， 所以这个bean将会根据全限定类名来进行命名。 在本例中， bean的ID将会是“soundsystem.SgtPeppers#0”   其中， “#0”是一个计数的形式， 用来区分相同类型的其他bean 

#### 2.4.3 借助构造器注入初始化bean 

构造器注入 (有些事情<constructor-arg>可以做到， 但是使用c-命名空间却无法实现 )

- <constructor-arg>元素 
- 使用Spring 3.0所引入的c-命名空间 

构造器注入bean引用 

按照现在的定义， CDPlayer bean有一个接受CompactDisc类型的构造器 ,上面声明了SgtPeppers bean， 并且SgtPeppers类实现了CompactDisc接口 

```java

  <bean id="compactDisc" class="soundsystem.SgtPeppers" />
  <bean id="cdPlayer" class="soundsystem.CDPlayer">
    <constructor-arg ref="compactDisc" /><!--通过ID引用SgtPeppers-->
  </bean>
</beans>
```

当Spring遇到这个<bean>元素时， 它会创建一个CDPlayer实例。 <constructor-arg>元素会告知Spring要将一个ID为compactDisc的bean引用传递到CDPlayer的构造器中 

可以使用Spring的c-命名空间 (spring3.0引入，它是在XML中更为简洁地描述构造器参数的方式 )，要使用它的话， 必须要在XML的顶部声明其模式 

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:c="http://www.springframework.org/schema/c"
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

	<bean id="compactDisc" class="soundsystem.SgtPeppers" />
		<!--在c-命名空间和模式声明之后--> 
  		<bean id="cdPlayer" class="soundsystem.CDPlayer"
        	c:cd-ref="compactDisc" />
			| |	|		|
			| |	|		|要注入的Bean的ID
			| | |（-ref表示注入bean引用）
			| |
			| |cd代表构造器参数名
        	|c-命名空间
        
  <!--我们使用参数在整个参数列表中的位置信息,参数的名称替换成了“0”， 也就是参数的索引-->
  <!--因为在XML中不允许数字作为属性的第一个字符， 因此必须要添加一个下画线作为前缀-->
  <bean id="cdPlayer" class="soundsystem.CDPlayer" c:_0-ref="compactDisc"/>
  <!--只有一个构造器参数,不用去标示参数-->
  <bean id="cdPlayer" class="soundsystem.CDPlayer" c:_-ref="compactDisc"/>
</beans>
```

属性名以“c:”开头， 也就是命名空间的前缀 。 接下来就是要装配的构造器参数名， 在此之后是“-ref”， 这是一个命名的约定， 它会告诉Spring， 正在装配的是一个bean的引用 ，不是字面量

###### 将字面量注入到构造器中 

```java
public class BlankDisc implements CompactDisc {

  private String title;
  private String artist;

  public BlankDisc(String title, String artist) {
    this.title = title;
    this.artist = artist;
  }

  public void play() {
    System.out.println("Playing " + title + " by " + artist);
  }

}

```

```xml
  <bean id="compactDisc" class="soundsystem.BlankDisc">
    <constructor-arg value="Sgt. Pepper's Lonely Hearts Club Band" /><!--使用<constructor-arg>元素进行构造器参数的注入-->
    <constructor-arg value="The Beatles" /><!--没有使用“ref”属性来引用其他的bean， 而是使用了value属性， 通过该属性表明给定的值要以字面量的形式注入到构造器之中-->
  </bean>
  <!--通过参数索引装配相同的字面量值	(属性名中去掉了“-ref”后缀 )-->
  <bean id="compactDisc" class="soundsystem.BlankDisc"
       c:_0="Sgt. Pepper's Lonely Hearts Club Band" 
       c:_1="The Beatles" />
  <bean id="cdPlayer" class="soundsystem.CDPlayer">
    <constructor-arg ref="compactDisc" />
  </bean>
    <!--构造器参数-->
  <bean id="compactDisc"
     class="soundsystem.properties.BlankDisc"
     c:_title="Sgt. Pepper's Lonely Hearts Club Band"
     c:_artist="The Beatles"/>
```

装配集合 

```java
  public BlankDisc(String title, String artist, List<String> tracks) {//添加tracks集合
    this.title = title;
    this.artist = artist;
    this.tracks = tracks;
  }

```

```xml
<bean id="compactDisc" class="soundsystem.BlankDisc">
	<constructor-arg value="Sgt. Pepper's Lonely Hearts Club Band" />
	<constructor-arg value="The Beatles" />
    <constructor-arg><null/></constructor-arg>	<!--<null/>将null传递给构造器-->
</bean>


 <bean id="compactDisc" class="soundsystem.collections.BlankDisc">
    <constructor-arg value="Sgt. Pepper's Lonely Hearts Club Band" />
    <constructor-arg value="The Beatles" />
    <constructor-arg>
      <list><!--使用<list>元素将其声明为一个列表  也可以set集合-->
		<value>Sgt. Pepper's Lonely Hearts Club Band</value><!--<value>元素用来指定列表中的每个元素-->
		<value>With a Little Help from My Friends</value>
		<value>Lucy in the Sky with Diamonds</value><!--如果集合是对象也可以用ref指定对象-->
		<value>Getting Better</value>
      </list>
    </constructor-arg>
  </bean>
```

#### 2.4.4 设置属性 

 ```JAVA
public class CDPlayer implements MediaPlayer {
  private CompactDisc compactDisc;

  @Autowired
  public void setCompactDisc(CompactDisc compactDisc) {
    this.compactDisc = compactDisc;
  }
  public void play() {
    compactDisc.play();
  }
}
 ```

```XML
<bean id="cdPlayer" class="soundsystem.CDPlayer"/>
<bean id="cdPlayer" class="soundsystem.CDPlayer">
    <!--<property>元素为属性的Setter方法所提供的功能与<constructor-arg>元素为构造器所提供的功能是一样的-->
    <property name="compactDisc" ref="compactDisc"/>
</bean>


<!--Spring提供了更加简洁的p-命名空间，作为<property>元素的替代方案-->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:p="http://www.springframework.org/schema/p"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
	<bean id="cdPlayer"
		class="soundsystem.CDPlayer"
		p:compactDisc-ref="compactDisc" /><!--p为命名空间，compactDisc为属性名，-ref为注入bean引用 后面为注入的bean的ID-->
</beans>
```

将字面量注入到属性中 

```JAVA
public class BlankDisc implements CompactDisc {

  private String title;
  private String artist;
  private List<String> tracks;

  public void setTitle(String title) {
    this.title = title;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public void setTracks(List<String> tracks) {
    this.tracks = tracks;
  }

  public void play() {
    System.out.println("Playing " + title + " by " + artist);
    for (String track : tracks) {
      System.out.println("-Track: " + track);
    }
  }

}
```

```XML
<!--除了使用<property>元素的value属性来设置title和artist， 我们还使用了内嵌的<list>元素来设置tracks属性-->
<bean id="compactDisc" class="soundsystem.properties.BlankDisc">
    <property name="title" value="Sgt. Pepper's Lonely Hearts Club Band" />
    <property name="artist" value="The Beatles" />
    <property name="tracks">
        <list>
            <value>Getting Better</value>
            <value>Fixing a Hole</value>
            <value>She's Leaving Home</value>
        </list>
    </property>
</bean>
<!--使用p-命名空间的属性来完成-->
<bean id="compactDisc" class="soundsystem.properties.BlankDisc" p:title="Sgt. Pepper's Lonely Hearts Club Band" p:artist="The Beatles">
    <property name="tracks">
        <list>
            <value>Getting Better</value>
            <value>Fixing a Hole</value>
            <value>She's Leaving Home</value>
        </list>
    </property>
</bean>

<bean id="cdPlayer"
      class="soundsystem.properties.CDPlayer"
      p:compactDisc-ref="compactDisc" />

<!--没有便利的方式使用p-命名空间来指定一个值（或bean引用）的列表,  可以使用Spring的util-命名空间中的一些功能来简化-->
<bean id="compactDisc"
      class="soundsystem.properties.BlankDisc"
      p:title="Sgt. Pepper's Lonely Hearts Club Band"
      p:artist="The Beatles"
      p:tracks-ref="trackList" />
<!--util-命名空间所提供的功能之一就是<util:list>元素,还有util:constant,map,properties,set,property-path-->
<util:list id="trackList">  
    <value>Getting Better</value>
    <value>Fixing a Hole</value>
    <value>She's Leaving Home</value>
</util:list>
```

#### 2.5 导入和混合配置 

多个java配置可以通过Import导入其他配置

```java
@Configuration//java配置文件一
public class CDConfig {
  
  @Bean
  public CDPlayer cdPlayer(CompactDisc compactDisc) {
    return new CDPlayer(compactDisc);
  }

}

@Configuration//java配置文件二
@Import(CDConfig.class)//使用@Import注解导入CDConfig  或者单独写一个配置类导入所有配置类
public class CDConfig {
  @Bean
  public CompactDisc compactDisc() {
    return new SgtPeppers();
  }
}

@Configuration
@Import(CDPlayerConfig.class)
@ImportResource("classpath:cd-config.xml")//在JavaConfig中引用XML配置
public class SoundSystemConfig {
}

```

2.5.2 在XML配置中引用JavaConfig 

```xml
<bean class="soundsystem.CDConfig"/><!--导入java配置类-->
<bean class="soundsystem.CDPlayer" c:cd-ref="compactDisc" id="cdPlayer"/>
<import resource="cdplayer-config.xml">
```

## 第3章 高级装配 

###   3.1 环境与profile  

开发、测试（ QA -- Quality assurance 质量保证）、生产环境的数据库配置，加密算法不一致

 

```sql
--schema.sql
create table Things (
  id identity,
  name varchar(100)
);
--test-data.sql
insert into Things (name) values ('A')
```

用EmbeddedDatabaseBuilder会搭建一个嵌入式的Hypersonic数据库， 它的模式（schema） 定义在schema.sql中， 测试数据则是通过test-data.sql加载的 

 ```java
@Configuration//在Spring配置类中
@Profile("dev") //类上面
public class DataSourceConfig {
  //开发环境中， 我们可能会使用用EmbeddedDatabaseBuilder搭建一个嵌入式Hypersonic数据库， 并预先加载测试数据 
  @Bean(destroyMethod = "shutdown")
  //@Profile("dev") 
  public DataSource embeddedDataSource() {
      
    return new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .addScript("classpath:schema.sql")
        .addScript("classpath:test-data.sql")
        .build();
  }
    
  @Bean(destroyMethod = "close")
  //@Profile("QA") //配置为Commons DBCP连接池
  public DataSource embeddedDataSource() {
      BasicDataSource dataSource = new BasicDataSource();// 连接池
      dataSource.setUrl("jdbc:h2:tcp://dbserver/~/test");
      dataSource.setDriverClassName("org.h2.Driver");
      dataSource.setUsername("root");
      dataSource.setPassword("1234");
      //可选配置
      dataSource.setMaxActive(10);//连接池最大连接数
      dataSource.setMaxIdle(5);//连接池最大空闲数
      dataSource.setMinIdle(3);//连接池最小空闲数
      dataSource.setInitialSize(10);//初始化连接池时的连接数
  }
    
  @Bean
  //@Profile("prod")//JNDI从容器中获取一个DataSource
  public DataSource jndiDataSource() {
    JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
    jndiObjectFactoryBean.setJndiName("jdbc/myDS");
    jndiObjectFactoryBean.setResourceRef(true);
    jndiObjectFactoryBean.setProxyInterface(javax.sql.DataSource.class);
    return (DataSource) jndiObjectFactoryBean.getObject();
  }

}
 ```
通过JNDI获取DataSource能够让容器决定该如何创建这个DataSource， 甚至包括切换为容器管理的连接池。 即便如此，JNDI管理的DataSource更加适合于生产环境， 对于简单的集成和开发测试环境来说， 这会带来不必要的复杂性  

  3个方法都会生成一个类型为javax.sql.DataSource的bean ，  每个方法都使用了完全不同的策略来生成DataSource bean  

将3个环境使用 单独的配置类（或XML文件） 中配置每个bean ，  然后在构建阶段（可能会使用Maven的profiles） 确定要将哪一个配置编译到可部署的应用中  

####   3.1.1 配置profile bean  

 3.1版本中， Spring引入了bean profile的功能。 要使用profile， 你首先要将所有不同的bean定义整理到一个或多个profile之中， 在将应用部署到每个环境时， 要确保对应的profile处于激活（active） 的状态

 **Spring等到运行时再来确定创建哪个bean和不创建哪个bean**  

 在Spring 3.1中， 只能在类级别上使用@Profile注解，  它会告诉Spring这个配置类中的bean只有在dev profile激活时才会创建。 如果dev profile没有激活的话， 那么带有@Bean注解的方法都会被忽略掉

不过， 从Spring 3.2开始， 你也可以在方法级别上使用@Profile注解，与@Bean注解一同使用。 这样的话， 就能将这两个bean的声明放到同一个配置类之中，  没有指定profile的bean始终都会被创建， 与激活哪个profile没有
关系  

  **在XML中配置profile**    通过<beans>元素的profile属性， 在XML中配置profile bean。  

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 								xmlns:jdbc="http://www.springframework.org/schema/jdbc"
	xmlns:jee="http://www.springframework.org/schema/jee" 								xmlns:p="http://www.springframework.org/schema/p"
 	xsi:schemaLocation="
    http://www.springframework.org/schema/jdbc
    http://www.springframework.org/schema/jdbc/spring-jdbc.xsd
    http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd"
  	profile="dev">
    <jdbc:embedded-database id="dataSource" type="H2">
      <jdbc:script location="classpath:schema.sql" />
      <jdbc:script location="classpath:test-data.sql" />
    </jdbc:embedded-database>
</beans>
```

 你还可以在根<beans>元素中嵌套定义<beans>元素， 而不是为每个环境都创建一个profile XML文件。 这能够将所有的profile bean定义放到同一个XML文件中  

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:jdbc="http://www.springframework.org/schema/jdbc"
	xmlns:jee="http://www.springframework.org/schema/jee" 								xmlns:p="http://www.springframework.org/schema/p"
 	xsi:schemaLocation="
    http://www.springframework.org/schema/jee
    http://www.springframework.org/schema/jee/spring-jee.xsd
    http://www.springframework.org/schema/jdbc
    http://www.springframework.org/schema/jdbc/spring-jdbc.xsd
    http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd">

  <beans profile="dev">
    <jdbc:embedded-database id="dataSource" type="H2">
      <jdbc:script location="classpath:schema.sql" />
      <jdbc:script location="classpath:test-data.sql" />
    </jdbc:embedded-database>
  </beans>
  
  <beans profile="prod">
    <jee:jndi-lookup id="dataSource"
      lazy-init="true"
      jndi-name="jdbc/myDatabase"
      resource-ref="true"
      proxy-interface="javax.sql.DataSource" />
  </beans>
</beans>
```

**xml解释**

1.beans —— xml文件的根节点
2.xmlns ——是XML NameSpace的缩写，因为XML文件的标签名称都是自定义的，自己写的和其他人定义的标签很有可能会重复命名，而功能却不一样，所以需要加上一个namespace来区分这个xml文件和其他的xml文件，类似于java中的package。
3.xmlns:xsi ——是指xml文件遵守xml规范，xsi全名：xml schema instance，是指具体用到的schema资源文件里定义的元素所准守的规范。即http://www.w3.org/2001/XMLSchema-instance这个文件里定义的元素遵守什么标准
4.xsi:schemaLocation——是指本文档里的xml元素所遵守的规范，这些规范都是由官方制定的，可以进你写的网址里面看版本的变动。xsd的网址还可以帮助你判断使用的代码是否合法。



所有的配置文件都会放到部署单元之中（如WAR文件） ， 但是只有profile属性与当前激活profile相匹配的配置文件才会被用到  

####   3.1.2 激活profile  

spring通过 依赖**spring.profiles.active**和**spring.profiles.default**   两个独立的属性  

如果设置了spring.profiles.active属性的话， 那么它的值就会用来确定哪个profile是激活的。 

没有设置spring.profiles.active 属性的话， 那Spring将会查找spring.profiles.default的值。如果spring.profiles.active和spring.profiles.default均没有设置的话， 那就没有激活的profile， 因此只会创建那些没有定义在profile中的bean  

 有多种方式来设置这两个属性：

-  在web.xml中 作为DispatcherServlet的初始化参数；
-  在web.xml中 作为Web应用的上下文参数；
- 作为JNDI条目；
- 作为环境变量；
- 作为JVM的系统属性；
- 在集成测试类上， 使用@ActiveProfiles注解设置。  

 前两者都可以在web.xml文件中设置 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app version="3.0" xmlns="http://java.sun.com/xml/ns/javaee"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
    http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd">

    <display-name>Archetype Created Web Application</display-name>

    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath*:/applicationContext*.xml</param-value>
    </context-param>

    <!-- 在上下文context-param中设置profile.default的默认值 -->
    <context-param>
        <param-name>spring.profiles.default</param-name>
        <param-value>dev</param-value>
    </context-param>

    <!-- 在上下文context-param中设置profile.active的默认值 -->
    <!-- 设置active后default失效，web启动时会加载对应的环境信息 -->
    <context-param>
        <param-name>spring.profiles.active</param-name>
        <param-value>dev</param-value>
    </context-param>

    <servlet>
        <servlet-name>appServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- 在DispatcherServlet参数中设置profile的默认值，active同理 -->
        <init-param>
            <param-name>spring.profiles.default</param-name>
            <param-value>dev</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>appServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

</web-app>
```

**使用profile进行测试**

当运行集成测试时， 通常会希望采用与生产环境（或者是生产环境的部分子集） 相同的配置进行测试。 但是， 如果配置中的bean定义在了profile中， 那么在运行测试时， 我们就需要有一种方式来启用合适的profile。

Spring提供了@ActiveProfiles注解， 我们可以使用它来指定运行测试时要激活哪个profile。 在集成测试时， 通常想要激活的是开发环境的profile。 例如， 下面的测试类片段展现了使用@ActiveProfiles激活dev profile：  

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=DataSourceConfig.class)
//@ContextConfiguration("classpath:datasource-config.xml")
@ActiveProfiles("dev")
public static class DevDataSourceTest {
    @Autowired
    private DataSource dataSource;

    @Test
    public void shouldBeEmbeddedDatasource() {
        assertNotNull(dataSource);
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        List<String> results = jdbc.query("select id, name from Things", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getLong("id") + ":" + rs.getString("name");
            }
        });

        assertEquals(1, results.size());
        assertEquals("1:A", results.get(0));
    }
}
```

###   3.2 条件化的bean

  一个或多个bean只有在应用的类路径下包含特定的库时, 或我们希望某个bean只有当另外某个特定的bean也声明了之后,或只有某个特定的环境变量设置之后， 才会创建某个bean

```java
public class MagicBean {}
//如果给定的条件计算结果为true， 就会创建这个bean， 否则的话， 这个bean会被忽略
public class MagicExistsCondition implements Condition {
  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) { 
      //判断是否存在某个Bean，可通过@Bean("father")设置Bean
      //context.getBeanFactory().containsBean("father")
      //判断是否存在环境变量magic，属性值无所谓，只要存在就可以，可通过System.setProperty("magic", "1");设置该环境变量
      Environment env = context.getEnvironment();
      return env.containsProperty("magic");
  }
}

@Configuration
public class MagicConfig {
  @Bean
  @Conditional(MagicExistsCondition.class)//条件创建bean,Conditional指定条件
  public MagicBean magicBean() {
    return new MagicBean();
  }
}
```

设置给@Conditional的类可以是任意实现了Condition接口的类型。  只需提供matches()方法的实现即可,如果matches()方法返回true， 那么就会创建带有@Conditional注解的bean

```java
public interface Condition {
    boolean matches(ConditionContext var1, AnnotatedTypeMetadata var2);
}
public interface ConditionContext {
	BeanDefinitionRegistry getRegistry();//检查bean定义
	ConfigurableListableBeanFactory getBeanFactory();//检查bean是否存在，甚至探查bean的属性
	Environment getEnvironment();//检查环境变量是否存在以及它的值是什么
	ResourceLoader getResourceLoader();//
	ClassLoader getClassLoader();//
}
```

ConditionContext，我们可以做到如下几点：

- 借助getRegistry()返回的BeanDefinitionRegistry检查bean定义
- 借助getBeanFactory()返回的ConfigurableListableBeanFactory检查bean是否存在，甚至探查bean的属性
- 借助getEnvironment()返回的Environment检查环境变量是否存在以及它的值是什么
- 读取并探查getResourceLoader()返回的ResourceLoader所加载的资源
- 借助getClassLoader()返回的ClassLoader加载并检查类是否存在

AnnotatedTypeMetadata则能够让我们检查带有@Bean注解的方法上还有什么其他的注解。  借助其他的那些方法， 我们能够检查@Bean注解的方法上其他注解的属性

```java

public interface AnnotatedTypeMetadata {
    //借助isAnnotated()方法， 我们能够判断带有@Bean注解的方法是不是还有其他特定的注解
	boolean isAnnotated(String annotationName);
	Map<String, Object> getAnnotationAttributes(String annotationName);
	Map<String, Object> getAnnotationAttributes(String annotationName, boolean classValuesAsString);
	MultiValueMap<String, Object> getAllAnnotationAttributes(String annotationName);
	MultiValueMap<String, Object> getAllAnnotationAttributes(String annotationName, boolean classValuesAsString);

}
```

  从Spring 4开始， @Profile注解进行了重构， 使其基于@Conditional和Condition实现  

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(ProfileCondition.class)
public @interface Profile {
	String[] value();
}
```

###   3.3 处理自动装配的歧义性

如果不仅有一个bean能够匹配结果的话， 这种歧义性会阻碍Spring自动装配属性、 构造器参数或方法参数

 **当Spring 自动装配setDessert()中的Dessert参数时候，它并没有唯一的可选值，所以Spring无法自动装配** 

```java
@Autowired
public void setDessert(Dessert dessert){//NoUniqueBeanDefinitionException
this.dessert = dessert;
}
@Component//在组件扫描的时候，能够发现它们并将其创建为Spring应用上下文里面的bean
public class Cake implements Dessert {}
@Component
@Primary//当遇到歧义性的时候，Spring将会使用首选的bean
public class IceCream implements Dessert{}

@Bean
@Primary//Java配置类中
public Dessert cake(){
    return new Cake();
}
```

**解决方案1**：表示首选的bean(  在Spring中， 可以通过@Primary来表达最喜欢的方案 ),  @Primary能够与@Component组合用在组件扫描的bean上， 也可以与@Bean组合用在Java配置的bean声明中

```xml
<bean id="cake" class="Cake" primary="true"></bean><!--xml方式-->
```

  显然， 如果不止一个bean被设置成了首选bean， 那实际上也就是没有首选bean

**解决方案2**： 限定自动装配的bean

  @Qualifier注解是使用限定符的主要方式， 它可以与@Autowired和@Inject协同使用

```java
@Autowired
@Qualifier("iceCream") //确保IceCream注入到setDessert()之中,参数就是想要注入的bean的ID
public void setDessert(Dessert dessert){	
this.dessert = dessert;
}
```

所有使用@Component注解声明的类都会创建为bean， 并且bean的ID为首字母变为小写的类名。 因此， @Qualifier("iceCream")指向的是组件扫描时所创建的bean， 并且这个bean是IceCream类的实例

bean设置自己的限定符， 而不是依赖于将bean ID作为限定符

```java
@Component
@Qualifier("cold")//不和类名耦合，可任意重构类名
public class iceCream implements Dessert{
}
```

**解决方案3:自定义的限定符注解** ，它本身就要使用@Qualifier注解来标注,最后所做到的就是我们不需要再使用@Qualifier(“cold”),而是使用自定义的@Cold注解

```java
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD,ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface Cold{}//限定符注解
@Component
@Cold
public class IceCream implements Dessert{}
//注入点
@Autowired
@Cold
@Cold2//可以同时使用多个限定符,自定义的注解也更为类型安全
public void setDessert(Dessert dessert){
	this.dessert = dessert;
}
```

### 3.4 bean的作用域

Spring应用上下文中所有bean都是作为以单例（singleton） 的形式创建的。 也就是说， 不管给定的一个bean被注入到其他bean多少次， 每次所注入的都是同一个实例  

spring定义了多种作用域，可以基于这些作用域创建bean，包括：

- **单例（Singleton）**：在整个应用（IOC容器）中，只创建bean的一个实例。**单例为spring默认作用域**
- 原型（Prototype）：每次注入或者通过Spring应用上下文获取的时候，都会创建一个新的bean实例。
- 会话（Session）：在Web应用中，为每个会话创建一个bean实例
- 请求（Rquest）：在Web应用中，为每个请求创建一个bean实例

易变的类型选择其他的作用域， 要使用@Scope注解， 它可以与@Component或@Bean一起使用  

```java
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)//使用常量更安全
//@Scope("prototype")//容易拼写出错
public class NotePad {}

@Bean
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public NotePad notePad(){
    return new NotePad();
}
//xml方式
<bean id="ServiceImpl" class="cn.service.ServiceImpl" scope="prototype">
```

#### 3.4.1 使用会话和请求作用域

购物车bean来说，会话(Session) 作用域是最为合适

```java
@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION,proxyMode=ScopedProxyMode.INTERFACES)
public ShippingCart  cart {...}
```

这里将value的值设置为WebApplicationContext中的SCOPE_SESSION常量（他的值是session）。这样就会告诉Spring每个会话创建一个Cart 

要注意的是，@Scope中使用了proxyMode属性，被设置成了ScopedProxyMode.INTERFACES。这个属性是用于解决将会话或请求作用域的bean注入到单例bean中所遇到的问题。

假设我们将ShippingCart bean注入到单例StoreService bean的setter方法中：

```java
@Component
public class StoreService {
    private ShippingCart shippingCart;
	@Autowired
    public void setShoppingCart(ShippingCart shoppingCart) {
        this.shippingCart = shoppingCart;
    }
}
```

因为StoreService 是个单例bean，会在Spring应用上下文加载的时候创建。当它创建的时候，Spring会试图将ShippingCart bean注入到setShoppingCart()方法中。但是ShippingCart bean是会话作用域，此时并不存在。直到用户进入系统创建会话后才会出现ShippingCart实例。

另外，系统中会有多个ShippingCart 实例，每个用户一个。我们并不希望注入固定的ShippingCart实例，而是希望当StoreService 处理购物车时，它所使用的是当前会话的ShippingCart实例。

Spring并不会将实际的ShippingCart bean注入到StoreService，Spring会注入一个ShippingCart bean的代理。这个代理会暴露与ShippingCart相同的方法，所以StoreService会认为它就是一个购物车。但是，当StoreService调用ShippingCart的方法时，代理会对其进行懒解析并将调用委任给会话作用域内真正的ShippingCart bean。

在上面的配置中，proxyMode属性，被设置成了ScopedProxyMode.INTERFACES，这表明这个代理要实现ShippingCart接口，并将调用委托给实现bean。
但如果ShippingCart是一个具体的类而不是接口的话，Spring就没法创建基于接口的代理了。此时，它必须使用CGLib来生成基于类的代理。所以，如果bean类型是具体类的话我们必须要将proxyMode属性，设置成ScopedProxyMode.TARGET_CLASS，以此来表明要以生成目标类扩展的方式创建代理。
请求作用域的bean应该也以作用域代理的方式进行注入。

#### 3.4.2 在XML中声明作用域代理

 如果你需要使用**xml**来声明会话或请求作用域的bean，那么就需要使用``元素来指定代理模式 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans ...>
  <bean id="cart" class="com.lixiang.bean.ShoppingCart" scope="session"/>
  <aop:scoped-proxy /><!--@Scope注解的proxyMode属性功能相同-->
</beans>
```

<aop:scoped-proxy />是与@Scope注解的proxyMode属性相同的xml元素。它会告诉Spring为bean创建一个作用域代理。默认情况下，它会使用CGLib创建目标类的代理，如果要生成基于接口的代理可以将proxy-target-class属性设置成false,如下(必须在XML配置中声明Spring的aop命名空间)

```xml
<bean id="cart" class="com.lixiang.bean.ShoppingCart" scope="session"/>
<aop:scoped-proxy proxy-target-class="false"/>
```

###   3.5 运行时值注入  

Spring提供了2种方式在运行时注入值：

1. 属性占位符(Property placeholder)
2. Spring表达式语言(SpEL)

####   3.5.1 注入外部的值

处理外部值的最简单方式就是声明属性源并通过Spring的Environment来检索属性,一般情况下，我们会将一些值放到配置文件中，等程序运行时再把值注入到一些字段上。

假如，我们有一个app.properties配置文件，内容如下：

```properties
disc.title=wangyunfei
disc.artist=spring boot
author.age=30
```



  ```java
@Configuration
@ComponentScan
@PropertySource("classpath:demo/el/app.properties")//申明属性源，引用类路径中为app.properties的文件
public class ExpressiveConfig {
    @Autowired
    private Environment env;

    public void outputResource() {
		env.getProperty("disc.title");//检索属性值
		env.getProperty("disc.artist");
        
    }
}
public interface PropertyResolver {//其他方法
    String getProperty(String key);
    <T> T getProperty(String key, Class<T> targetType);
    <T> T getProperty(String key, Class<T> targetType, T defaultValue);
    String getProperty(String key, String defaultValue);
    boolean containsProperty(String key);
    String getRequiredProperty(String key) throws IllegalStateException;
}
public interface Environment extends PropertyResolver {
    //检查哪些profile处于激活状态
	String[] getActiveProfiles();//返回激活profile名称的数组
	String[] getDefaultProfiles();
    //如果environment支持给定profile的话， 就返回true
	boolean acceptsProfiles(String... profiles);
}
  ```

这个属性文件会加载到Spring的Environment中， 稍后可以从这里检索属性  

**解析属性占位符**

用environment能解决很多问题，属性占位符也能很好地解决上述问题。
Spring中，占位符的形式为“${…}”,可以在XML中使用占位符值将其插入到Spring bean中  

```xml
<bean id="sgtPeppers" class="com.example.chr1s.BlankDisc"
      c:_title="${disc.title}"
      c:_artist="${disc.artist}" />
```

 也可以在Java配置文件中使用属性占位符，通过@Value注解来实现 

```java
public BlankDisc(//依赖于组件扫描和自动装配来创建和初始化应用组件
    @Value("${disc.title}") String title,
    @Value("${disc.artist}") String artist) {
    this.title = title;
    this.artist = artist;
}
```

 为了使用占位符，必须配置一个PropertyPlaceholderConfigurer bean或**PropertySourcesPlaceholderConfigurer** bean。推荐使用后者，因为它能够基于Spring Environment及其属性源来解析占位符 , 以按照如下的方法配置一个相应的bean： 

 ```java
@Bean
public static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){
    return new PropertySourcesPlaceholderConfigurer();
}
 ```

 使用XML配置的话， Spring context命名空间中的 &lt;context:propertyplaceholder&gt;  元素将会为你生成PropertySourcesPlaceholderConfigurer bean：  

```xml
<beans>
    <context:property-placeholder 	location="classpath:jdbc.properties,classpath:system.properties"	
                                  file-encoding="UTF-8"/>
</beans>
```

解析外部属性能够将值的处理推迟到运行时， 但是它的关注点在于根据名称解析来自于Spring Environment和属性源的属性。 而Spring表达式语言提供了一种更通用的方式在运行时计算所要注入的值  

#### 3.5.2 使用Spring表达式语言进行装配

Spring 3引入了Spring表达式语言（Spring Expression Language，SpEL） ， 它能够以一种强大和简洁的方式将值装配到bean属性和构造器参数中， 在这个过程中所使用的表达式会在运行时计算得到值。 使用SpEL， 你可以实现超乎想象的装配效果， 这是使用其他的装配技术难以做到的（甚至是不可能的）  

SpEL具有以下特性：

- 使用bean的ID来引用bean
- 调用方法和访问对象的属性
- 对值进行算术、关系和逻辑运算 
- 正则表达式匹配
- 集合操作

SpEL能够用在依赖注入以外的其他地方。 例如， Spring Security支持使用SpEL表达式定义安全限制规则。 另外， 如果你在Spring MVC应用中使用Thymeleaf模板作为视图的话， 那么这些模板可以使用SpEL表达式引用模型数据

**SpEL表达式放到“#{ ... }”之中** 

```java
public class TestSpringEL {
    //@Value注解等同于XML配置中的<property/>标签,SpringEL同样支持在XML<property/>中编写
    //最简单的SpEL表达式,除去“#{…}”标记之后，剩下的就是SpEL表达式体了，也就是一个数字常量注入简单值,输出num为5,表达式的计算结果就是数字5
    @Value("#{5}")
    private Integer num;
    // 注入ID为testConstant的Bean
    @Value("#{testConstant}")
    private TestConstant Constant;
    // 注入ID为testConstant Bean中的STR常量/变量
    @Value("#{testConstant.STR}")
    //T()表达式会将java.lang.System视为Java中对应的类型， 因此可以调用其static修饰的currentTimeMillis()方法
    @Value("#{T(System).currentTimeMillis()}")
    private String str;
    @Value("#{systemProperties['disc.title']}")//通过systemProperties对象引用系统属性
    private String str;

}

public BlankDisc(//通过组件扫描创建bean在注入属性和构造器参数时， 可以使用@Value注解的SpEL表达式
    @Value("#{systemProperties['disc.title']}") String title,
    @Value("#{systemProperties['disc.artist']}") String artist) {
    this.title = title;
    this.artist = artist;
}
```

在XML配置中， 你可以将SpEL表达式传入&lt;property&gt;或&lt;constructor-arg&gt;的value属性中， 或者将其作为p-命名空间或c-命名空间条目的值  

```xml
<bean id="sgtPepers" class="soundsystem.BlankDisc"
      c:_title="#{systemProperties['disc.title']}"
      c:_artist="#{systemProperties['disc.artist']}" />
```

表示字面值

```java
"#{3.14159}"//浮点值
"#{9.38E4}"//科学记数
"#{'Hello'}"//计算String类型的字面值
"#{false}"
"#{sgtPepers}"//SpEL通过ID引用其他的bean
"#{sgtPepers.artist}"
"#{sgtPepers.toString().toUpperCase()}"//调用对象方法或方法的方法
"#{artistSelector.selectArtist()?.toUpperCase()}"//使用类型安全的运算符
```

  “?.”运算符能够在访问它右边的内容之前， 确保它所对应的元素不是null。 如果selectArtist()的返回值是null的话， 那么SpEL将不会调用toUpperCase()方法。 表达式的返回值会是null  

**在表达式中使用类型**
```java
T(java.lang.Math)//T()运算符的结果会是一个Class对象
T(java.lang.Math).PI
```
SpEL中访问类作用域的方法和常量的话， 要依赖T()这个关键的运算符，T()运算符的结果会是一个Class对象

**代码示例**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
 
    <bean id="car" class="com.spring.spel.Car">
        <!--直接value="cat"也可以-->
        <property name="brand" value="#{'cat'}"></property>
        <!-- 或者
        <property name="brand" value='#{"cat"}'></property>
        -->
        <!--直接value="32000.78"也可以-->
        <property name="price" value="#{3.78}"></property>
        <!--调用静态方法的属性-->
        <property name="perimeter" value="#{T(java.lang.Math).PI * 1.8f}"></property> 
    </bean>
 
    <bean id="person" class="com.spring.spel.Person">
        <!--调用对象的方法或属性，判断表达式#{true}/#{false}，marriage为Boolean型-->
        <property name="marriage" value="#{car.price > 400000 and age > 30}"></property>
        <!--引用bean，之前为：<property name="car" ref="car"></property>-->
        <property name="car" value="#{car}"></property>
        <property name="socialStatus" value="#{car.price > 30000 ? '金领' : '白领'}"></property>
        <property name="address" value="#{address.province + '省' + address.city + '市' + address.area + '区'}"/>
    </bean>
</beans>
```

- 整数：#{8}
- 小数：#{8.8}
- 科学计数法：#{1e4}
- String：使用单或双引号作为字符串的定界符
- Boolean：#{true}
- 引用对象：#{car}
- 引用对象属性：#{car.brand}
- 调用对象方法：#{car.toString()},还可以链式操作
- 调用静态方法静态属性、静态方法：#{T(java.lang.Math).PI}，#{T(java.lang.Math).random()}使用T()调用类作用域的
- 方法和常量
- 支持算术运算符：+，-，*，/，%，^(加号还可以用作字符串连接)
- 比较运算符：/gt , ==/eq , >=/ge , <=/le
- 逻辑运算符：and , or , not , |
- 三目运算符：? :
- 正则表达式：#{admin.email matches ‘[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}’}

**SpEL运算符**(可用于SpEL表达式的值上)

| 运算符类型 | 运算符                     |
| ---------- | -------------------------- |
| 算术运算   | +、-、*、/、%、^           |
| 比较运算   | 符号形式：<、>、==、<=、>= ; 文本形式：lt、gt、eq、le、ge |
| 逻辑运算 | and、or、not、 |
| 条件运算 | ?: (ternary)、?: (Elvis) |
| 正则表达式 | matches |
| 计算集合 | []、.?[]、.^[]、.$[]、.![] |

 ```java
#{2*T(java.lang.Math).PI * circle.radius}               //圆周长计算
#{T(java.lang.Math).PI * circle.radius^2}               //圆面积计算
#{disc.title + 'by' + disc.artist}                      // + 是连接符
#{counter.total == 100}  #{counter.total eq 100}        //判断是否一致，返回true和false
#{counter.total > 100 ? "Winner" : "Loser"}             //三元表达式 
#{disc.title ?: 'Rattle'}                   //Elvis，如果是null的话结果则为Rattle
#{admin.email matches '[a-zA-Z0-9._%+-]+@[a-zA-Z0-9._-]+\\.com'}  //正则表达式
#{a>10 ? true : false}
#{a?:’b’}
//计算集合
#{jukebox.songs[4].title}
#{jukebox.songs[T(java.lang.Math).random*jukebox.songs.size()].title}//随机
#{'This is a test'[3]}          //获取的字符就是 's'
#{jukebox.songs.?[artist eq 'Aerosmith']}               //匹配全部(过滤数据)
#{jukebox.songs.^[artist eq 'Aerosmith']}               //匹配第一个
#{jukebox.songs.$[artist eq 'Aerosmith']}               //匹配最后一个
#{jukebox.songs.![title]}//将title属性投影到一个新的String类型的集合
 ```

“[]”运算符用来从集合或数组中按照索引获取元素， 实际上， 它还可以从String中获取一个字符  

 SpEL还提供了查询运算符（.?[]），它会用来对集合进行过滤，得到集合的一个子集。比如我们现在想要从jukebox中artist属性为Aerosmith的所有歌曲 

 SpEL还提供了投影运算符（.![]），它会从集合的每个成员中选择特定的属性放到另外一个集合中 

这个运算符一样可以和其他的运算符一起使用。比如我们可以使用如下的表达式获取Aerosmith所有歌曲的名称列表：

```java
#{jukebox.songs.?[aitist eq 'Aerosmith'].![title]}
```

## 第4章 面向切面的Spring

散布于应用中多处的功能被称为横切关注点（crosscutting concern）, 这些横切关注点从概念上是与应用的业
务逻辑相分离的（但是往往会直接嵌入到应用的业务逻辑之中） 。 把这些横切关注点与业务逻辑相分离正是面向切面编程（AOP） 所要解决的问题  

### 4.1 什么是面向切面编程

面能帮助我们模块化横切关注点。 简而言之， 横切关注点可以被描述为影响应用多处的功能(  例如， 安全就是一个横切关注点， 应用中的许多方法都会涉及到安全规则。)

A，B,C服务都涉及通用功能如安全、事务、日志  ，如果要重用通用功能的话， 最常见的面向对象技术是继承（ inheritance） 或委托（ delegation）  

AOP（Aspect Oriented Programming），即面向切面编程，可以说是OOP（Object Oriented Programming，面向对象编程）的补充和完善。OOP引入封装、继承、多态等概念来建立一种对象层次结构，用于模拟公共行为的一个集合。不过OOP允许开发者定义纵向的关系，但并不适合定义横向的关系，例如日志功能。日志代码往往横向地散布在所有对象层次中，而与它对应的对象的核心功能毫无关系对于其他类型的代码，如安全性、异常处理和透明的持续性也都是如此，这种散布在各处的无关的代码被称为横切（cross cutting），在OOP设计中，它导致了大量代码的重复，而不利于各个模块的重用。

AOP技术恰恰相反，它利用一种称为"横切"的技术，剖解开封装的对象内部，并将那些影响了多个类的公共行为封装到一个可重用模块，并将其命名为"Aspect"，即切面。所谓"切面"，简单说就是那些与业务无关，却为业务模块所共同调用的逻辑或责任封装起来，便于减少系统的重复代码，降低模块之间的耦合度，并有利于未来的可操作性和可维护性。

使用"横切"技术，AOP把软件系统分为两个部分：核心关注点和横切关注点。业务处理的主要流程是核心关注点，与之关系不大的部分是横切关注点。横切关注点的一个特点是，他们经常发生在核心关注点的多处，而各处基本相似，比如权限认证、日志、事物。AOP的作用在于分离系统中的各种关注点，将核心关注点和横切关注点分离开来

#### 4.1.1 定义AOP术语

AOP核心概念

1、横切关注点：对哪些方法进行拦截，拦截后怎么处理，这些关注点称之为横切关注点

2、切面（aspect）：类是对物体特征的抽象，切面就是对横切关注点的抽象

3、连接点（joinpoint）：被拦截到的点，因为Spring只支持方法类型的连接点，所以在Spring中连接点指的就是被拦截到的方法，实际上连接点还可以是字段或者构造器

被拦截到的点，因为Spring只支持方法类型的连接点，所以在Spring中连接点指的就是被拦截到的方法，实际上连接点还可以是字段或者构造器

4、切入点（pointcut）：对连接点进行拦截的定义

5、通知（advice）：所谓通知指的就是指拦截到连接点之后要执行的代码

Spring切面可以应用5种类型的通知：

- 前置通知（Before） ： 在目标方法被调用之前调用通知功能
- 后置通知（After） ： 在目标方法完成之后调用通知， 此时不会关心方法的输出是什么
- 返回通知（After-returning） ： 在目标方法成功执行之后调用通知
- 异常通知（After-throwing） ： 在目标方法抛出异常后调用通知
- 环绕通知（Around） ： 通知包裹了被通知的方法， 在被通知的方法调用之前和调用之后执行自定义的行为  

6、目标对象：代理的目标对象

7、织入（weave）：将切面应用到目标对象并导致代理对象创建的过程（编译期，类加载期，运行期）

在目标对象的生命周期里有多个点可以进行织入  

- 编译期： 切面在目标类编译时被织入。 这种方式需要特殊的编译器。 AspectJ的织入编译器就是以这种方式织入切面的。
- 类加载期： 切面在目标类加载到JVM时被织入。 这种方式需要特殊的类加载器（ClassLoader） ， 它可以在目标类被引入应用之前增强该目标类的字节码。 AspectJ 5的加载时织入（load-timeweaving， LTW） 就支持以这种方式织入切面。
- 运行期： 切面在应用运行的某个时刻被织入。 一般情况下， 在织入切面时， AOP容器会为目标对象动态地创建一个代理对象。Spring AOP就是以这种方式织入切面的。  

8、引入（introduction）：在不修改代码的前提下，引入可以在运行期为类动态地添加一些方法或字段

#### 4.1.2 Spring对AOP的支持

 不同AOP框架在连接点模型上可能有强弱之分。 有些允许在字段修饰符级别应用通知， 而另一些只支持与方法
调用相关的连接点。 它们织入切面的方式和时机也有所不同。 但是无论如何， 创建切点来定义切面所织入的连接点是AOP框架的基本功能，Spring对AOP的支持在很多方面借鉴了AspectJ项目。  

Spring提供了4种类型的AOP支持：

- 基于代理的经典Spring AOP
- 纯POJO切面
- @AspectJ注解驱动的切面
- 注入式AspectJ切面（适用于Spring各版本）。 

前三种都是Spring AOP实现的变体， Spring AOP构建在动态代理基础之上， 因此， Spring对AOP的支持局限于方法拦截  

Spring引入了简单的声明式AOP和基于注解的AOP，更简洁和干净的面向切面编程方式之后， Spring经典的AOP看起来就显得非常笨重和过于复杂  

Spring的aop命名空间，可以将纯POJO转换为切面(声明式地将对象转换为切面)。 实际上， 这些POJO只是提供了满足切点条件时所要调用的方法 ,  这种技术需要XML配置

注解驱动的AOP这种AOP风格的好处在于能够不使用XML来完成功能  

如果你的AOP需求超过了简单的方法调用（如构造器或属性拦截），那么你需要考虑使用AspectJ来实现切面。 在这种情况下， 上文所示的第四种类型能够帮助你将值注入到AspectJ驱动的切面中。  

**Spring通知是用标准的Java类编写**

**Spring在运行时通知对象**( 通过在代理类中包裹切面， Spring在运行期把切面织入到Spring管理的bean中)  

代理类封装了目标类， 并拦截被通知方法的调用，再把调用转发给真正的目标bean。当代理拦截到方法调用时，在调用目标bean方法之前， 会执行切面逻辑  

直到应用需要被代理的bean时， Spring才创建代理对象。 如果使用的是ApplicationContext的话， 在ApplicationContext从BeanFactory中加载所有bean的时候， Spring才会创建被代理的对象。 因为Spring运行时才创建代理对象， 所以我们不需要特殊的编译器来织入Spring AOP的切面  

**Spring只支持方法级别的连接点**(可以利用Aspect来补充Spring AOP的功能)

因为Spring基于动态代理， 所以Spring只支持方法连接点。 这与一些其他的AOP框架是不同的， 例如AspectJ和JBoss， 除了方法切点， 它们还提供了字段和构造器接入点。 Spring缺少对字段连接点的支持， 无法让我们创建细粒度的通知， 例如拦截对象字段的修改。 而且它不支持构造器连接点， 我们就无法在bean创建时应用通知  

### 4.2 通过切点来选择连接点

切点用于准确定位应该在什么地方应用切面的通知。 通知和切点是切面的最基本元素,  **在Spring AOP中， 要使用AspectJ的切点表达式语言来定义切点**

Spring仅支持AspectJ切点指示器（pointcut designator） 的一个子集 , Spring是基于代理的， 而某些切点表达式是与基于代理的AOP无关的  

**Spring借助AspectJ的切点表达式语言来定义Spring切面**

| AspectJ指示器 | 描 述                                                        |
| ------------- | ------------------------------------------------------------ |
| arg()         | 限制连接点匹配参数为指定类型的执行方法                       |
| @args()       | 限制连接点匹配参数由指定注解标注的执行方法                   |
| execution()   | 用于匹配是连接点的执行方法                                   |
| this()        | 限制连接点匹配AOP代理的bean引用为指定类型的类                |
| target        | 限制连接点匹配目标对象为指定类型的类                         |
| @target()     | 限制连接点匹配特定的执行对象， 这些对象对应的类要具有指定类型的注解 |
| within()      | 限制连接点匹配指定的类型                                     |
| @within()     | 限制连接点匹配指定注解所标注的类型（当使用Spring AOP时， 方法定义在由指定的注解所标注的类里） |
| @annotation   | 限定匹配带有指定注解的连接点                                 |

在Spring中尝试使用AspectJ其他指示器时， 将会抛出IllegalArgumentException异常

 只有execution指示器是实际执行匹配的， 而其他的指示器都是用来限制匹配的。 这说明execution指示器是我们在编写切点定义时最主要使用的指示器。 在此基础上， 我们使用其他指示器来限制所匹配的切点  

#### 4.2.1 编写切点

```java
public interface Arithmetic {
    public double add(double a,double b);
}

//execution(* demo.Arithmetic.add(..))
//切点表达式， 这个表达式能够设置当add()方法执行时触发通知的调用,第一个*表示不关心方法返回值的类型
//方法参数列表，使用两个点号(..) 表明切点要选择任意的add()方法， 无论该方法的入参是什么
//execution(* demo.Arithmetic.add(..)) && within(demo.*) 用within()指示器来限制匹配配置的切点仅匹配demo包,使用了“&&”操作符把execution()和within()指示器连接在一起形成与（and） 关系（切点必须匹配所有的指示器）

```

####   4.2.2 在切点中选择bean  

```java
execution(* demo.Arithmetic.add(..)) and bean('woodsstock') //使用bean的ID来标识bean  ,add()方法时应用通知， 但限定bean的ID为woodstock
//execution(* demo.Arithmetic.add(..)) and !bean('woodsstock') 切面的通知会被编织到所有ID不为woodstock的bean中
```

###  4.3 使用注解创建切面 

使用注解来创建切面是AspectJ 5所引入的关键特性.

 Performance接口， 它是切面中切点的目标对象  

####   4.3.1 定义切面  

```java
@Aspect//@Aspect:表示该类为切面类
public class LogAspect {
    //execution():表示需要被通知的类中的add方法，add方法可以用通配符表示，那就表示该类中所有的方法都要被通知
  @Before(value="execution(* demo.Arithmetic.add(double,double))")
    //JoinPoint:是程序执行中的一个精确执行点，例如类中的一个方法。它是一个抽象的概念，在实现AOP时，并不需要去定义一个join point
  public void before(JoinPoint joinpoint) {
        //获得被通知程序类的参数，返回类型是一个数组
      Object[] args = joinpoint.getArgs();
        //获取被通知程序类的方法名，返回类型是字符串
      String name = joinpoint.getSignature().getName();
      System.out.println("------>the method "+name+" begin with"+Arrays.asList(args));
  }

  //@After:后置通知，表示在类的方法执行后、类中的return方法执行前执行
  @After(value="execution(* demo.Arithmetic.add(double,double))")
  public void after() {
      System.out.println("------>the method *** end result");
  }
}
```

LogAspect类中的方法都使用注解来定义切面的具体行为,  AspectJ提供了五个注解来定义通知

| 注解           | 通知 |
| ---------------- | ---------------------------------------- |
| @After  | 通知方法会在目标方法返回或抛出异常后调用           |
| @AfterReturning  | 通知方法会在目标方法返回后调用           |
| @AfterThrowing | 通知方法会在目标方法抛出异常后调用       |
| @Around         | 通知方法会将目标方法封装起来             |
| @Before          | 通知方法会在目标方法调用之前执行         |

```java
@Aspect//通过AspectJ注解实现切面编程,@Aspect:表示该类为切面类
public class LogAspect {
    @Pointcut("execution(* demo.Arithmetic.add(..))")//定义名称的切点，统一的定义，以方便我们的重用和维护
	public void performance(){}
    
    @Before(value="performance()")
    public void before(JoinPoint joinpoint) {}
}

@Configuration
@EnableAspectJAutoProxy//配置类的类级别上通过使用注解启用AspectJ自动代理功能
@ComponentScan
public class LogConfig{
    @Bean
    public LogAspect log(){
        return new LogAspect();
    }
}
```

 ```xml
<!--xml方式来装配bean，用Spring aop命名空间中的<aop:aspectj-autoproxy>元素-->
<beans>
    <context:component-scan base-package="concert">
	<aop:aspectj-autoproxy/><!--启用AspectJ自动代理功能-->
    <bean class="demo.LogAspect"/>
</beans>
 ```

不管使用JavaConfig还是XML， AspectJ自动代理都会为使用@Aspect注解的bean创建一个代理  ， 这个代理会围绕着所有该切面的切点所匹配的bean。 在这种情况下， 将会为 bean创建一个代理， LogAspect类中的通知方法将会在add()调用前后执行  

####   4.3.2 创建环绕通知  

最为强大的通知类型。 它能够让你所编写的逻辑将被通知的目标方法完全包装起来。 实际上就像在一个通知方法中同时编写前置通知和后置通知  

```java
@Component
public class Audience {
    /**目标方法执行之前调用*/
    public void takeSeats(){
        System.out.println("The audience is taking their seats.");
    }
    /**目标方法执行之前调用*/
    public void turnOffCellPhones(){
        System.out.println("The audience is turning off their cellphones.");
    }
    /** 目标方法执行完后调用*/
    public void applaud(){
        System.out.println("CLAP CLAP CLAP CLAP CLAP");
    }
    /**目标方法发生异常时调用*/
    public void demandRefund(){
        System.out.println("Boo! We want our money back!");
    }

    public void watchPerformance(ProceedingJoinPoint joinPoint){
        try{
            takeSeats(); //表演之前
            turnOffCellPhones(); //表演之前
            long start = System.currentTimeMillis();
            System.out.println("The performance start ......");//节目开始
            joinPoint.proceed(); //执行被通知的方法
            System.out.println("The performance end ......");//节目结束
            long end = System.currentTimeMillis(); //表演之后
            applaud();//表演之后
            System.out.println("The performance took milliseconds："+ (end - start) );//表演时长
        }catch (Throwable t){
            demandRefund(); //表演失败之后
        }
    }
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd  http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    <!--前置通知和后置通知是在一个方法中实现，所以不需要保存变量值，自然是线程安全的-->
    <context:component-scan base-package="com.spring.example.aspectAround"/>
    <!--通过component-scan自动扫描，@Component注解将Magician注册到spring容器-->
    <aop:config>
            <!--audience :切面  watchPerformance：切面方法   performance：切点-->
            <aop:aspect ref="audience">
                <aop:pointcut id="performance" expression="execution(* com.spring.example.aspectAround.Performer.perform(..))"/>
                <aop:around pointcut-ref="performance" method="watchPerformance" />
            </aop:aspect>
    </aop:config>
</beans>
```

#### 4.3.3 处理通知中的参数

```java
@Aspect//创建切面
public class TrackCounter {
//在该切点表达式中使用了args(trackNumber)限定符。表示传递给playTrack()方法的int类型参数也会传递到通知中去。参数名trackNumber也与切点方法签名中的参数相匹配。
    @Pointcut("execution(* com.wqh.aop.CompactDisc.playTrack(int))&&args(trackNumber)")
    public void trackPlayder(int trackNumber){}

    @Before("trackPlayder(trackNumber)")
    public void countTrack(int trackNumber) {
        System.out.println("前置通知:targetNumber=" + trackNumber);
    }
}
//连接点类
@Service
public class CompactDisc {
    public void playTrack(int trackNumber){
        System.out.println("trackNumber =" + trackNumber);
    }
}
@Test
public void testT(){
    ApplicationContext applicationContext =
        new ClassPathXmlApplicationContext(new String[]{"classpath:/spring/applicationContext.xml"});
    CompactDisc compactDisc = (CompactDisc) applicationContext.getBean("compactDisc");
    compactDisc.playTrack(12);
}
```

```xml
 <!--启用AspectJ的自动代理-->
<aop:aspectj-autoproxy/>
<!--声明bean-->
<bean class="com.wqh.aop.TrackCounter"/>
<!--自动扫描包下的类-->
<context:component-scan base-package="com.wqh.aop"/>
```

#### 4.3.4 通过注解引入新功能

切面可以为Spring bean添加新方法,当引入接口的方法被调用时，代理会把此调用委 托给实现了新接口的某个其他对象。实际上，一个bean的实现被拆分 到了多个类中

```java
public interface Encoreable {
    void performEncore();
}
public class DefaultEncoreable implements Encoreable {
    @Override
    public void performEncore() {
        System.out.println("川剧变脸");
    }
}
//创建一个用于注入新方法的切面EncoreableIntroducer.java
@Aspect
@Component
public class EncoreableIntroducer {
    @DeclareParents(value="concert.Performance+",defaultImpl = DefaultEncoreable.class)
    public static Encoreable encoreable;
}
@Configuration
@EnableAspectJAutoProxy // 启用AspectJ自动代理
@ComponentScan
public class ConcertConfig {//采用java装配bean
}
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = concert.ConcertConfig.class)
public class ConcertTest {
    @Autowired
    public Performance p;
    @Autowired
    public Encoreable en;
    @Test
    public void test() {
        p.perform();
        System.out.println("-----------------------------");
        System.out.println("自己创建对象调用");
        en.performEncore();
        System.out.println("-----------------------------");
        System.out.println("通过Performance对象调用“新方法”");
        Encoreable e = (Encoreable) p;
        e.performEncore();
    }
}
```

EncoreableIntroducer是一个切面，同时声明它是一个bean。但是，它与我们 之前所创建的切面不同，它并没有提供前置、后置或环绕通知，而是 通过@DeclareParents注解，将Encoreable接口引入 到Performance bean中。 **@DeclareParents注解由三部分组成**：

- value属性指定了哪种类型的bean要引入该接口。在本例中，也 就是所有实现Performance的类型。（标记符后面的加号表示 是Performance的所有子类型，而不是Performance本 身。）
- defaultImpl属性指定了为引入功能提供实现的类。在这里， 我们指定的是DefaultEncoreable提供实现。
- @DeclareParents注解所标注的静态属性指明了要引入了接 口。在这里，我们所引入的是Encoreable接口。 

和其他切面一样，我们需要在Spring应用中将EncoreableIntroducer声明为一个bean。然后Spring的自动代理机制会获取到它的声明，当Spring发现一个bean使用@AspectJ注解时，Spring就会创建一个代理，单后将调用委托给被代理的bean或被引入的实现，这取决于调用的方法属于被代理的bean还是属于被引入的接口。

gradle.properties

```properties
springVersion=4.0.7.RELEASE
aspectJVersion=1.7.2
```

build.gradle

```groovy
apply plugin: 'java'
apply plugin: 'eclipse'
apply plugin: 'idea'
jar {
    baseName = 'conditional'
    version =  '0.0.1-SNAPSHOT'
}
repositories {
    mavenLocal()
    mavenCentral()
}
dependencies {
    compile("org.springframework:spring-context:${springVersion}")
    compile("org.aspectj:aspectjweaver:${aspectJVersion}")
    testCompile("org.springframework:spring-test:${springVersion}")
}
task wrapper(type: Wrapper) {
    gradleVersion = '1.11'
}
```

### 4.4 在XML中声明切面

```xml
<aop:config><!-- AOP定义开始,顶层的AOP配置元素。大多数的<aop:*>元素必须包含在<aop:config>元素内-->
    <aop:pointcut/>				<!-- 定义切入点 -->
    <aop:advisor/>				<!-- 定义AOP通知器 -->
    <aop:aspect>				<!-- 定义切面开始 -->
        <aop:pointcut/>			<!-- 定义切入点 -->
        <aop:before/>			<!-- 前置通知 -->
        <aop:after-returning/>	        <!-- 后置返回通知 -->
        <aop:after-throwing/>           <!-- 后置异常通知 -->
        <aop:after/>			<!-- 后置通知（不管通知的方法是否执行成功） -->
        <aop:around/>			<!-- 环绕通知 -->
        <aop:declare-parents/>          <!-- 引入通知: 以透明的方式为被通知的对象引入额外的接口 -->
    </aop:aspect>				<!-- 定义切面结束 -->
</aop:config>					<!-- AOP定义结束 -->
<aop:aspectj-autoproxy>		<!--启用@AspectJ注解驱动的切面-->
```

&lt;aop:aspectj-autoproxy&gt;元素， 它能够自动代理AspectJ注解的通知类。 aop命名空间的其他元素能够让我们直接在Spring配置中声明切面， 而不需要使用注解

上面Audience和普通Java类没有任何的区别，需要在Xml中稍作配置，他就可以成为一个切面

#### 4.4.1 声明前置和后置通知  

```xml
<aop:config>
    <aop:aspect ref="audience">
        <aop:before pointcut="execution(** com.spring.aop.service.Perfomance.perform(..)" 
        method="silenceCellPhone"/>
        <aop:before pointcut="execution(** com.spring.aop.service.Perfomance.perform(..)" 
        method="takeSeats"/>
        <aop:after-returning pointcut="execution(** com.spring.aop.service.Perfomance.perform(..)"
        method="applause"/>
        <aop:after-throwing pointcut="execution(** com.spring.aop.service.Perfomance.perform(..)"
        method="demandRefund"/>
    </aop:aspect>
</aop:config>
```

在基于AspectJ注解的通知中， 当发现这种类型的重复时， 我们使用@Pointcut注解消除了这些重复的内容。 而在基于XML的切面声明中， 我们需要使用&lt;aop:pointcut&gt;元素。 

```xml
<aop:config>
    <aop:pointcut id="performance" 
        expression="execution(** com.spring.aop.service.Perfomance.perform(..)" /><!--定义切点-->
    <aop:aspect ref="audience">
        <aop:before pointcut-ref="performance" method="silenceCellPhone"/><!--引用-->
    </aop:aspect>
</aop:config>
```

&lt;aop:pointcut&gt;元素所定义的切点可以被同一个&lt;aop:aspect&gt;元素之内的所有通知元素引用。 如果想让定义的切点能够在多个切面使用， 我们可以把&lt;aop:pointcut&gt;元素放在&lt;aop:config&gt;元素的范围内

#### 4.4.2 声明环绕通知

```java
/*** 环绕通知*/
public void watchPerformance(ProceedingJoinPoint jp) {
    try {
        System.out.println("Silencing cell phones");
        System.out.println("Taking seats");
        jp.proceed();
        System.out.println("CLAP CLAP CLAP!!!");
    } catch (Throwable e) {
        System.out.println("Demanding a refund");
    }
}
```

```xml
<aop:config>
    <aop:aspect ref="audience">
        <!-- 也可以放在<aop:aspect>外，<sop:config>内 ，可以供多个切面使用-->
    <aop:around pointcut-ref="performance" method="watchPerformance"/>
    </aop:aspect>
</aop:config>
```

#### 4.4.3 为通知传递参数

```java
/**
 * <dl>
 * <dd>Description:统计每首歌曲播放次数的AOP</dd>
 * <dd>Company: 掌控者</dd>
 * <dd>@date：2019年12月03日 下午17:26:24</dd>
 * <dd>@author：sundy</dd>
 * </dl>
 */
public class TrackCounter {
        
    private Map<Integer,Integer> trackCounts = new HashMap<Integer,Integer>();
    /**
     * 将统计播放次数的方法声明为前置通知
     * @param trackNumber 歌曲id
     */
    public void countTrack(int trackNumber){
        int currentCount = getPlayCount(trackNumber);
        trackCounts.put(trackNumber, currentCount+1);
    }

    public int getPlayCount(int trackNumber) {
        return trackCounts.containsKey(trackNumber)?trackCounts.get(trackNumber):0;
    }    
}
```

```xml
<bean id="trackCounter" class="com.spring.aop.service.aop.TrackCounter" />  

<bean id="cd" class="com.spring.aop.service.impl.BlankDisc">
    <property name="title" value="Sgt. Pepper's Lonely Hearts Club Band"/>
    <property name="artist" value="The Beatles"/>
    <property name="tracks">
        <list>
            <value>Sgn. Pepper's Lonelu Hears Club Band</value>
            <value>Wiith a Litter Help from My Friends</value>
            <value>Lucy in the Sky with Diamonds</value>
            <value>Getting Better</value>
            <value>Fixing a Hole</value>
        </list>
    </property>
</bean>
<aop:config>
    
    <aop:aspect ref="trackCounter">
        <aop:pointcut id="trackPlayed" 
        expression="execution(* com.spring.aop.service.CompactDisc.playTrack(int)) and args(trackNumber)"/>
        <!--唯一的差别在于这里使用and关键字而不是“&&”（因为在XML中， “&”符号会被解析为实体的开始）-->
       <aop:after pointcut-ref="trackPlayed" method="countTrack"/>
    </aop:aspect>
</aop:config>
```

#### 4.4.4 通过切面引入新的功能

```xml
<aop:config>
    <!--default-impl="com.spring.aop.service.impl.DefaultEncoreable":通过类名指定添加功能的实现  -->
    <!-- delegate-ref="encoreableDelegate"：通过bean ID指定添加功能的实现 -->
    <aop:aspect>
        <aop:declare-parents 
        types-matching="com.spring.aop.service.Perforance+" 
        implement-interface="com.spring.aop.service.Encoreable"
        default-impl="com.spring.aop.service.impl.DefaultEncoreable"
        />
    </aop:aspect>
</aop:config>
```

```xml
<aop:config>
    <aop:aspect>
        <aop:declare-parents 
        types-matching="com.spring.aop.service.Perforance+" 
        implement-interface="com.spring.aop.service.Encoreable"
        delegate-ref="encoreableDelegate"
        />
    </aop:aspect>
</aop:config>
<bean id="encoreableDelegate" class="com.spring.aop.service.impl.DefauleEncoreable"/>
```

- types-matching:指定添加方法的接口
- mplement-interface:指定添加的新功能的接口
- default-impl/delegate-ref:指定添加新功能接口的实现类，其中default-impl直接表示委托，delegate-ref指定一个bean，个人觉得后者较灵活。

### 4.5 注入AspectJ切面