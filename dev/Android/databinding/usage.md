
### DataBinding 
>名为数据绑定，他的功能很简单，就是将数据绑定在 UI 页面上，明白这一点很重要，DataBinding 唯一的作用，也是他的使命，就是绑定数据

绑定一词有两种解释，第一是将数据绑定在 UI 元素上；第二是将 UI 上的数据绑定到对应的数据模型中；此外，除了将数据与 UI 绑定在一起，还要支持对数据及 UI 的变动观察，其中一个发生变动就需要同步到另一个上去，也就是同步

    
1.gradle.properties 下添加如下代码
```text
android.injected.testOnly
```
2.module的build.gradle配置

```text
apply plugin: 'kotlin-android'//Java调用Kotlin
//拓展：其他插件,kotlin-android-extensions 是用来替代findViewById的插件
apply plugin: 'kotlin-android-extensions'
//如果你的Kotlin代码里面有使用到注解，么需要加入这个插件（kapt 即 Kotlin annotation processing tool，Kotlin 注解处理工具的缩写）
apply plugin: 'kotlin-kapt'
```

```groovy
android {
    buildFeatures {
        // Determines whether to support Data Binding.
        // Note that the dataBinding.enabled property is now deprecated.
        dataBinding = true
    }
}
```
3.将原来布局文件转换 databinding layout
    
    在原布局文件第一层布局使用 Alt+Enter快捷键或者鼠标放在布局文件第二行出现小灯泡 选择 Convert to data binding layout
    
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
​    <!--
    layout 为跟布局，data 节点中存放数据，下面就是我们常见的布局文件。
    data 中的 variable 标签为变量，类似于我们定义了一个变量，name 为变量名，
    type 为变量全限定类型名，包括包名。布局中通过 @{} 来引用这个变量的值，{} 中可以是任意 Java 表达式，但不推荐使用过多的代码。
    -->
    <data>
        <variable name="title" type="java.lang.String" />
    </data>
​
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{title}" />
    </LinearLayout>
</layout>
```
使用 import 语法来导入类，以及使用 alias 设置别名
```xml
    <data>
        <import type="java.lang.String"/>
        <import type="com.zhangke.demo.jetpack.entity.User"
                alias="ZKUser"/>
        <variable name="title" type="String" />
        <variable name="User" type="ZKUser" />
    </data>
```
我们也可以通过 default 字段设置默认值
```xml
<TextView android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@{title, default=my_default}"/>
```

### 绑定数据
再新建DataBinding 布局文件 activity_main.xml 之后，Gradle 会根据布局创建一个 ActivityMainBinding 类，们需要获取该对象来绑定数据。使用 DataBinding 时，我们不需要再按照之前的 setContentView 的方式来设置布局到 Activity 中，应该通过 DataBindingUtil#setContentView 来设置，该方法会返回对应的 DataBinding 对象，例如我们创建的布局文件为 activity_main，那么生成的就是 ActivityMainBinding

我们可以通过在 data 节点使用 class 关键字更改这种默认的名字：
```xml
<data class=".MainActivityBinding">
    …
</data>
```
前面的 . 号表示使用当前包名，也可以使用全限定包名指定

然后是获取绑定类：
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    binding.lifecycleOwner = this
    binding.title = "Title"//绑定普通数据(非 Observable/LiveData)
}
```
>我们先获取 DataBinding 对象，然后设置 variable 数据，lifecycleOwner 适用于管理生命周期的方法，设置后 Databinding 可以感知到 Activity 的生命周期，保证数据在可见时才会更新，不可见时不会更新数据。

**如果是在 Fragment/ListView/RecyclerView 中，我们可以通过下面的方法获取 DataBinding：**
```kotlin
binding = ActivityMainBinding.inflate(layoutInflater, null, false)
```
### 绑定普通数据
    DataBinding 可以绑定普通数据对象（非 Observable/LiveData)，例如上述例子中绑定了一个 String 类型的数据。绑定普通数据我们只需要按照上述的代码设置即可。
### 绑定可观察数据
    绑定可观察数据意味着当数据变化时 UI 会跟着一起变化，绑定可观察数据有三种方式：objects、fields 和 collections

#### 对单个变量的绑定：fields
对于一些数据类，如果我们不想继承 BaseObservable 或者只需要其中几个字段支持可观察，那么可以使用这种方式来创建可观察数据：
```kotlin
class BindFieldUser {
    var age = ObservableInt()
    var name = ObservableField<String>()
}
```
对于基本类型和 Parcelable 我们可以直接使用对应的包装类：

- ObservableBoolean
- ObservableByte
- ObservableChar
- ObservableShort
- ObservableInt
- ObservableLong
- ObservableFloat
- ObservableDouble
- ObservableParcelable

引用类型使用带有泛型参数的 ObservableField 类来创建：
```kotlin
var name = ObservableField<String>()
```


#### 对集合的绑定：collections

>我们同样可以在布局中绑定集合中的某个元素，当集合中的数据发生变化后会同步更新到 UI。

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout>
    <data>
        <import type="androidx.databinding.ObservableMap"/>
        <import type="androidx.databinding.ObservableList"/>
        <variable name="map" type="ObservableMap&lt;String, Object>"/>
        <variable name="list" type="ObservableList&lt;Object>"/>
    </data>
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@{String.valueOf(map.count)}" />
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@{String.valueOf(list[0])}" />
</layout>
```
>绑定数据到 UI 中：
```kotlin
val map = ObservableArrayMap<String, Any>().apply { put("count", 0) }
binding.map = map
val list = ObservableArrayList<Any>().apply { add(0) }
binding.list = list
```

#### 绑定对象：objects

>这种是最常用的一种方式，需要绑定的数据实体类继承 BaseObservable：

```kotlin
class Person : BaseObservable() {
    @get:Bindable
    var country: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.country)
        }
    
    @get:Bindable
    var sex: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.sex)
        }
}
```

首先要在需要支持可观察的数据上添加 @get:Bindable 注解，然后重写 set 方法，在其中调用 notifyPropertyChanged 方法表示更新该数据，BR 是自动生成的，包名跟当前包名一致，会根据 Bindable 注解的变量生成对应的值；也可以调用 notifyChange() 方法更新所有数据。

####双向绑定
上述的单向绑定是指数据变化后更新 UI，而双向绑定是指其中任意一个变化后都会同步更新到另一个。

>双向绑定使用 @={} 表达式来实现：

```xml
    <data>
        <variable
            name="input"
            type="androidx.databinding.ObservableField&lt;String>" />
    </data>
    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="@{input}"/>
    <EditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="@={input}"/>
```
**TextView跟随EditText变化而变化**，此时还需在activity初始化input
#### 事件绑定

>事件绑定其实跟数据绑定一样，本质上就是将监听器对象绑定到 UI 元素上：

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:app="http://schemas.android.com/apk/res-auto">
    <data class="">
    ...
        <variable
            name="handler"
            type="com.simple.MainActivity.EventHandler" />
    </data>
...
    <Button
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       android:layout_marginTop="10dp"
       android:onClick="@{handler::onToastBtnClick}"
       android:text="ToastClick"/>
</layout>
```
然后我们写好监听事件，绑定到 binding 中即可：

```kotlin
class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.handler = EventHandler()
    }

    inner class EventHandler {
        fun onToastBtnClick(v: View) {
            Toast.makeText(this@MainActivity, "Click", Toast.LENGTH_SHORT).show()
        }
    }
}
```

自定义参数绑定：BindingAdapter

![](https://github.com/sundyyh/study/blob/master/imgs/support_binding_view.webp)

除了上述的参数外，我们也可以使用 BindingAdapter 创建自定义参数。

例如我们需要使用 Glide 加载网络图片，可以先创建一个使用了 BindingAdapter 注解的函数，注解中的字段为参数名，函数的第一个参数必须为目标 View 或者其子类，因此使用 Kotlin 时我们可以定义为扩展函数，这样使用很方便。
```kotlin
@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String) =
    Glide.with(this.context).load(url).into(this)
```

然后我们就可以在布局代码中直接使用该参数加载图片：

```xml
<ImageView
    android:layout_width="100dp"
    android:layout_height="100dp"
    android:layout_marginTop="10dp"
    imageUrl="@{imageUrl}"
    android:layout_gravity="center_horizontal"/>
```

//性别
val sex = MutableLiveData<Int>()
val sexStr = sex.map {//该函数对数据进行自发的转换
    when(it){
        1 -> "男"
        2 -> "女"
        else -> ""
    }
}
