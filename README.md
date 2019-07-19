# react-native-bridgeutils

## Getting started

`$ npm install react-native-bridgeutils --save`

### Mostly automatic installation

`$ react-native link react-native-bridgeutils`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.indigoviolet.react.RNBridgeutilsPackage;` to the imports at the top of the file
  - Add `new RNBridgeutilsPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-bridgeutils'
  	project(':react-native-bridgeutils').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-bridgeutils/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-bridgeutils')
  	```


## Usage
```javascript
import RNBridgeutils from 'react-native-bridgeutils';

// TODO: What to do with the module?
RNBridgeutils;
```
  