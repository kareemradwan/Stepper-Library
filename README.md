# Stepper-Library

## How Library Work

##### We Need Create Step Class for Example `Order` and `OrderAdapter` 
##### The Class `Order` Must Be Implements Interface `IStep` and Implementaion Require Method `isChecked()`
##### The Class `OrderAdapter` must be inherited class `StepAdapter` with Generic Type `Order`
##### The Class `OrderAdapter` Require List of Any Class Implements Interface `IStep` in Our Example `Order`

`
StepAdapter<Order>(list)
`

##### In `OrderAdapter` We Should Override `onCreateView` Method 
##### The Method `onCreateView` take a Model of `Order` and Sould be Return `View`

##### After Adapter Ready You Can Assign Adapter For `SteeperView` 

##### You can Register The Activity as Controller to Notifiy When `SteeoerView` Finish ( in Last Step )



## What is Next:
##### - The Developer Can Custmise Color of Step
##### - Add Animation for `CheckBox` When be Selected


## About Developer:
##### Name: Kareem E Radwan
##### Email: kareem.e.radwan@gmail.com

