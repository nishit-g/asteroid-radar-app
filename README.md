# Asteroid Radar App

## What does this app do ?

This app fetches asteroid list and picture of the day from NASA's API and displays them.

## Features

- App is completely in **Kotlin**
- App uses **DataBinding** and **ViewBinding**
- App Architecture - **MVVM** _(LiveData, Repository)_
- App uses **SQLite Database** to store the information
- **Caching** is used to store and show the data
- **WorkManager** is used to _fetch and destroy data from database periodically_
- App has different layout for **Landscape** and **Portrait**
- App is available in **Hindi** and **English**

## Libraries Used

- **Retrofit** - For interacting with API
- **Picasso** - For displaying the image
- **SafeArgs** - For type safety in kotlin

# App Demo

# Screenshots

### English Version

![Landscape Main Activity](/screenshots/app_eng_portrait_main.jpg)
![Portrait Main Activity](/screenshots/app_eng_portrait_detail.jpg)
![](/screenshots/app_eng_detail_dialog.jpg)
![](/screenshots/app_eng_landscape_main.jpg)
![](/screenshots/app_eng_landscape_detail.jpg)

### Hindi Version

![Landscape Main Hindi Fragment](/screenshots/app_hindi_portrait_main.jpg)
![Portrait Main Hindi Fragment](/screenshots/app_hindi_portrait_detail.jpg)
![](/screenshots/app_hindi_landscape_main.jpg)
![](/screenshots/app_hindi_landscape_detail.jpg)
