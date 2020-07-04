# Stepper-Library (0.5.2)

## Screenshots
![Image Result](https://media.giphy.com/media/f6micojJP1dpgwnB3E/giphy.gif)
## How Add Library To Your Project
 in `build.gradle` on Project Level you should add 

``` kotlin
allprojects { 
     repositories { 
        maven { url 'https://jitpack.io' }
      }
 }   
```

 After Thet add Library in `build.gradle` in App Level
 in `dependencies` Tag
 
 ``` kotlin
 implementation 'com.github.kareemradwan:Stepper-Library:0.5.2'
 ```


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
  - [X] The Developer Can Custmise Color of Step. 
  - [X] Save State of View When Device Rotate.
  - [X] Custmaize Color for `unChecked` and `Checked` status
  - [X] Add Number Stepper View
  - [ ] Add Animation for `CheckBox` When be Selected


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
     Order(1, "Step1"),
     Order(2, "Step2"),
     Order(3, "Step3"),
     Order(4, "Step4")
 )
 
 val adapter = OrderAdapter(this, mList)
 steeper.setAdapter(adapter)
 
 // Go To Next Step
 btnNextStep.setOnClickListener { steeper.nextStep() }
```

## Note
you can show the example in this repo.

## About Developer:
Name: Kareem E Radwan
Email: kareem.e.radwan@gmail.com

