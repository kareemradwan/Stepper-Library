# Stepper-Library V0.2

## Screenshots
![Image Result](https://scontent.fgza6-1.fna.fbcdn.net/v/t1.0-9/106503813_693000678211700_2733733955416928403_n.jpg?_nc_cat=102&_nc_sid=07e735&_nc_ohc=4NiB_Ur8XbAAX9iQAH0&_nc_ht=scontent.fgza6-1.fna&oh=9c30aefb155035a8e040691afc145f9c&oe=5F22F65B)
## How Add Library To Your Project
 in `build.gradle` on Project Level you should add 

``` kotlin
allprojects { 
     repositories { 
        maven { url 'https://jitpack.io' }
      }
 }   
```
 After Thet add `implementation 'com.github.kareemradwan:Stepper-Library:0.2'` in `build.gradle` in App Level
 in `dependencies` Tag


## How Library Work
We Need Create Step Class for Example `Order` and `OrderAdapter` 
The Class `Order` Must Be Implements Interface `IStep` and Implementaion Require Method `isChecked()`
The Class `OrderAdapter` must be inherited class `StepAdapter` with Generic Type `Order`
The Class `OrderAdapter` Require List of Any Class Implements Interface `IStep` in Our Example `Order`

`
StepAdapter<Order>(list)
`

In `OrderAdapter` We Should Override `onCreateView` Method 
The Method `onCreateView` take a Model of `Order` and Sould be Return `View`
After Adapter Ready You Can Assign Adapter For `SteeperView` 
You can Register The Activity as Controller to Notifiy When `SteeoerView` Finish ( in Last Step )



## What is Next:
  - The Developer Can Custmise Color of Step.  (DONE)
 - Add Animation for `CheckBox` When be Selected


## Sample Code 
In Xml 
```xml
  <com.kradwan.stepeer.view.SteeperView
        android:id="@+id/steeper"
        app:checked_color="@color/colorPrimaryDark"
        app:unchecked_color="@color/colorPrimary"
        android:padding="20dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
        
```
In Kotlin
```kotlin
val mList = listOf(
     Order(1, "Step1", true),
     Order(2, "Step2"),
     Order(3, "Step3"),
     Order(4, "Step4")
 )
 
 val adapter = OrderAdapter(this, mList)
 steeper.setAdapter(adapter)
 
 // Go To Next Step
 btnNextStep.setOnClickListener { steeper.nextStep() }
```


## About Developer:
Name: Kareem E Radwan
Email: kareem.e.radwan@gmail.com

