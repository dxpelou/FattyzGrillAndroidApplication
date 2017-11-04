# Architecture

Application uses a Model-View-Presenter (MVP) architecture without using any architectural frameworks.

The app consists of three UI screens:
 - Meals - Where a list of meals is displayed
 - Checkout - shows a list of checked out meals
 - Notification - displays any notifications


In this version of the app, each screen is implemented using the following classes and interfaces:
- A [contract](https://github.com/dxpelou/FattyzGrillAndroidApplication/tree/master/app/src/main/java/com/louanimashaun/fattyzgrill/contract) class which defines communication between the view and the presenter.
- A [Fragment](https://github.com/dxpelou/FattyzGrillAndroidApplication/tree/master/app/src/main/java/com/louanimashaun/fattyzgrill/view) which implements the view interface.
- A [presenter](https://github.com/dxpelou/FattyzGrillAndroidApplication/tree/master/app/src/main/java/com/louanimashaun/fattyzgrill/presenter) which implements the presenter interface in the corresponding contract.

A presenter typically hosts business logic associated with a particular feature, and the corresponding view handles the Android UI work. The view contains almost no logic; it converts the presenter's commands to UI actions, and listens for user actions, which are then passed to the presenter.

A single Activity is used to create all fragments and corresponding presenters. The use of both activities and fragments allows for a better separation of concerns which complements this implementation of MVP.

A Repository pattern is used to encapsulate local and remote data access. You simply specify the data you want and the repository will fetch it for you. An abstract repository is defined which contains the logic needed for all repositories. It also inherently makes offline use simple. Code can be found [here](https://github.com/dxpelou/FattyzGrillAndroidApplication/tree/master/app/src/main/java/com/louanimashaun/fattyzgrill/data).

Dagger Android is used so dagger can handle dependency injection for Activities and Fragments which is normally handled by the OS. Code can be found [here](https://github.com/dxpelou/FattyzGrillAndroidApplication/tree/master/app/src/main/java/com/louanimashaun/fattyzgrill/di).

# Testing

Unit tests for presenters, repositories are found [here](https://github.com/dxpelou/FattyzGrillAndroidApplication/tree/master/app/src/test/java/com/louanimashaun/fattyzgrill).

Espresso UI tests are found [here](https://github.com/dxpelou/FattyzGrillAndroidApplication/tree/master/app/src/androidTest/java/com/louanimashaun/fattyzgrill).

# Local Data Storage
Realm is used to handle local data storage. Code can be found [here](https://github.com/dxpelou/FattyzGrillAndroidApplication/tree/master/app/src/main/java/com/louanimashaun/fattyzgrill/data/source/local).

# Backend
The back is handled through Firebase data which stores and syncs data across all devices in real time. The code that handles firebase database access is found [here](https://github.com/dxpelou/FattyzGrillAndroidApplication/tree/master/app/src/main/java/com/louanimashaun/fattyzgrill/data/source/remote).

# Push Notifications
Push notifications are used to send orders to admins and notify users that there order has been accepted. Firebase Cloud Messaging handles downstream notifications. Code can be found [here](https://github.com/dxpelou/FattyzGrillAndroidApplication/tree/master/app/src/main/java/com/louanimashaun/fattyzgrill/data/source/local).


# Screen Shots
![menu](https://github.com/dxpelou/FattyzGrillAndroidApplication/blob/travis-ci/screenshots/menu.png)
![checkout](https://github.com/dxpelou/FattyzGrillAndroidApplication/blob/travis-ci/screenshots/orders.png)
![notifications](https://github.com/dxpelou/FattyzGrillAndroidApplication/blob/travis-ci/screenshots/notifications.png)
