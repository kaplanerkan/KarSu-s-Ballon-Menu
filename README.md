# KarSu Menu Button

An animated FAB (Floating Action Button) menu library for Android, written in Kotlin. KarSu Menu Button lets you create expressive, animated popup menus with various button types, animations, and layouts.

## Demo

<video src="https://private-user-images.githubusercontent.com/5566139/547308192-445fab60-1dac-466b-aa91-94742a4fef0b.mp4?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NzA2NjgwNjgsIm5iZiI6MTc3MDY2Nzc2OCwicGF0aCI6Ii81NTY2MTM5LzU0NzMwODE5Mi00NDVmYWI2MC0xZGFjLTQ2NmItYWE5MS05NDc0MmE0ZmVmMGIubXA0P1gtQW16LUFsZ29yaXRobT1BV1M0LUhNQUMtU0hBMjU2JlgtQW16LUNyZWRlbnRpYWw9QUtJQVZDT0RZTFNBNTNQUUs0WkElMkYyMDI2MDIwOSUyRnVzLWVhc3QtMSUyRnMzJTJGYXdzNF9yZXF1ZXN0JlgtQW16LURhdGU9MjAyNjAyMDlUMjAwOTI4WiZYLUFtei1FeHBpcmVzPTMwMCZYLUFtei1TaWduYXR1cmU9ZGM5OWQxOWZhNWE1NDk5MzNmN2QxMmZkOWVmNjFmMTcxMWVmMjBhZTg2NzIxZGJkOTJkYmQzZTYzZWY2YWM4NCZYLUFtei1TaWduZWRIZWFkZXJzPWhvc3QifQ.7FOlz9o4F-FrASoyKy7lWBUSlxttvVfKm35QZI8DVOE" autoplay loop muted playsinline width="300"></video>

## Screenshots

| Main Menu | Simple Circle | Ham Buttons | 3D Animation |
|:---------:|:-------------:|:-----------:|:------------:|
| <img src="screenhots/scrcpy_yVwUHftfjX.png" width="200"/> | <img src="screenhots/scrcpy_aZndqZi0xK.png" width="200"/> | <img src="screenhots/scrcpy_pmsBI6fQvz.png" width="200"/> | <img src="screenhots/scrcpy_Y0v2NX9d80.png" width="200"/> |

| RecyclerView | ListView + Ham | Change Button | RecyclerView + Circle |
|:------------:|:--------------:|:-------------:|:---------------------:|
| <img src="screenhots/scrcpy_8euytzINn4.png" width="200"/> | <img src="screenhots/scrcpy_SKZNEI5Vnf.png" width="200"/> | <img src="screenhots/scrcpy_jyv9JvRmOC.png" width="200"/> | <img src="screenhots/scrcpy_yVwUHftfjX.png" width="200"/> |

## Features

- **4 Button Types**: Simple Circle, Text Inside Circle, Text Outside Circle, Ham (list-style)
- **8 Animation Types**: Line, Parabola (4 variants), Horizontal Throw (2 variants), Random
- **31 Ease Functions**: Sine, Quad, Cubic, Quart, Quint, Expo, Circ, Back, Elastic, Bounce, Linear (each with In/Out/InOut)
- **3D Transform Animations**
- **Flexible Layouts**: 1-9 buttons with multiple placement configurations
- **Button Alignment**: Center, Top, Bottom, Left, Right, and corner alignments
- **Draggable FAB**: Move the menu button anywhere on the screen
- **Share Lines**: Animated connection lines between buttons
- **Custom Positions**: Place buttons at exact coordinates
- **Fade Animations**: Fade-in/fade-out effects
- **Shadow & Ripple Effects**: Material Design style
- **Orientation Support**: Adapts to screen rotation
- **Works Everywhere**: Activity, Fragment, ActionBar, Toolbar, ListView, RecyclerView
- **Multi-Language**: English, Turkish, German
- **Runtime Changes**: Update button text, image, color at runtime

## Requirements

- Android API 30+ (minSdk 30)
- Kotlin
- AndroidX

## Usage

### XML Layout

```xml
<com.karsu.ballonsmenu.KarsuMenuButton
    android:id="@+id/bmb"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:bmb_buttonEnum="simpleCircle"
    app:bmb_piecePlaceEnum="piecePlace_dot_9_1"
    app:bmb_buttonPlaceEnum="buttonPlace_sc_9_1"
    app:bmb_karsuEnum="karsuLine"
    app:bmb_showMoveEaseEnum="outBack"
    app:bmb_showDuration="500"
    app:bmb_draggable="true" />
```

### Kotlin Code

```kotlin
val bmb = findViewById<KarsuMenuButton>(R.id.bmb)

for (i in 0 until bmb.piecePlaceEnum.pieceNumber()) {
    val builder = SimpleCircleButton.Builder()
        .normalImageRes(imageResources[i])
        .normalColorRes(colorResources[i])
        .listener { index -> Log.d("KarSu", "Button $index clicked") }
    bmb.addBuilder(builder)
}
```

### Button Types

| Type | Description |
|------|-------------|
| `SimpleCircleButton` | Icon-only round buttons |
| `TextInsideCircleButton` | Round buttons with text inside |
| `TextOutsideCircleButton` | Round buttons with text below |
| `HamButton` | Full-width buttons with icon, title, and subtitle |

## Demo App

The project includes a demo app with 22 examples covering all features:

- **Buttons**: Simple Circle, Text Inside/Outside, Ham
- **Animations**: Ease functions, 3D transforms, order settings
- **Layout Integration**: ActionBar, Toolbar, ListView, RecyclerView, Fragment
- **Configuration**: Alignment, custom positions, corner radius, change at runtime
- **Features**: Draggable, share lines, fade views, listener, control, orientation

## Project Structure

```
ballonsmenu/          # Library module
  KarsuMenuButton     # Main menu component
  KarsuButton         # Base button class
  AnimationManager    # Animation engine
  BackgroundView      # Dim overlay

app/                  # Demo application
  demo/
    buttons/          # Button type examples
    animation/        # Animation examples
    config/           # Configuration examples
    feature/          # Feature examples
    layout/           # Layout integration examples
```

## Build

```bash
./gradlew assembleDebug
```

## Inspiration & Credits

This project is inspired by [BoomMenu](https://github.com/Nightonke/BoomMenu) by [Nightonke](https://github.com/Nightonke). We are grateful for the original work and the creative ideas it provided. Thank you for the amazing open-source contribution that made this project possible!

## Author

**Erkan Kaplan**

## License

```
Copyright 2025 Erkan Kaplan

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
