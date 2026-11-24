# Oxygen Customizer QS Style Template

A template for Quick Settings style packs for [Oxygen Customizer](https://github.com/DHD2280/Oxygen-Customizer).

A pack is a normal APK: no overlays, no Magisk module, no reboot. Install it, then pick a
style in **Oxygen Customizer, Modifications, QS style packs**. The tiles change as soon as
Quick Settings is drawn again, without restarting SystemUI.

> QS style packs need the support proposed in
> [DHD2280/Oxygen-Customizer#254](https://github.com/DHD2280/Oxygen-Customizer/pull/254),
> and the tile edge needs OxygenOS 17 or newer.

The template contains one style, `ExampleStyle`: a plain hairline on the inactive tiles.
Rename it, change it, or add more styles next to it. For a full pack, see
[oc-qs-style-pack](https://github.com/Bouteillepleine/oc-qs-style-pack), which contains
seven styles.

Before you publish your pack, change `namespace` and `applicationId` in
`app/build.gradle.kts`, and the `app_name` and `pack_author` strings.

## Why a QS pack carries values, not resources

A notification style pack replaces SystemUI resources. That does not work for Quick
Settings on OxygenOS 17, because the tile backgrounds are built in code rather than
inflated from a drawable, so there is no resource to replace. A QS pack describes the
tile edge with values instead, and Oxygen Customizer strokes it after the ROM has drawn
the tile, following the same path the ROM used. The edge therefore tracks whatever tile
shape the user picked under Quick Settings, and the fill, the blur and the icon stay the
ROM's own.

## How a pack works

### 1. Declare each style

Every style is an `activity` with the `it.dhd.oxygencustomizer.qsstyle` action. The label
is the style name shown in the app. The activity is never started, so it needs no class.

```xml
<activity
    android:name="MyStyle"
    android:exported="true"
    android:label="@string/my_style">
    <intent-filter>
        <action android:name="it.dhd.oxygencustomizer.qsstyle" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
</activity>
```

One APK can hold any number of styles. To show the author name, add an application
meta-data entry:

```xml
<meta-data
    android:name="packauthor"
    android:value="Your name" />
```

### 2. Describe the style

Each style needs a `string-array` with the same name as its activity. Every item is
`key=value`:

```xml
<string-array name="MyStyle" translatable="false">
    <item>border.width=1dp</item>
    <item>border.color.dark=#59FFFFFF</item>
    <item>border.color.light=#1A000000</item>
</string-array>
```

| Key | Type | Default | Meaning |
| --- | --- | --- | --- |
| `border.width` | dp | `1dp` | Stroke width. `0` disables the edge. |
| `border.color.dark` | `#AARRGGBB` | `#59FFFFFF` | Edge color in dark mode. |
| `border.color.light` | `#AARRGGBB` | `#1A000000` | Edge color in light mode. |
| `border.color.dark.bottom` | `#AARRGGBB` | unset | Second stop of a top to bottom gradient in dark mode. Unset draws a flat edge. |
| `border.color.light.bottom` | `#AARRGGBB` | unset | The same in light mode. |
| `border.inset` | dp | `0dp` | Pulls the edge towards the centre of the tile, so it reads as a ring. |
| `border.active` | bool | `false` | Whether active toggles also get an edge. iOS leaves them borderless. |
| `preview` | drawable name | unset | A preview image for the list. Without one the list draws three tiles from the values themselves, so most packs need no artwork. |

Unknown keys are ignored and a malformed value falls back to its default, so a pack
written against a newer Oxygen Customizer still loads on an older one.

### 3. Build it

```bash
./gradlew :app:assembleDebug
```

The APK lands in `app/build/outputs/apk/debug/`. There is no code in a pack, only
resources, so nothing needs signing beyond the debug key to try it on your own device.
Sign a release build with your own key before publishing it.
