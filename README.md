# cordova-imagepicker
Cordova plugin for picking images from phone
(This plugin was inspired by https://github.com/wymsee/cordova-imagePicker but uses a different Android library)

## Installing the plugin

The plugin conforms to the Cordova plugin specification, it can be installed
using the Cordova command line interface.

    cordova plugin add https://github.com/SoSocio/cordova-imagepicker

## How to use the plugin

The plugin creates the object `window.plugins.imagePicker` with the method `getPictures(options, success, fail)`

Example - Get at most 10 images:
```javascript
window.imagePicker.getPictures({
		limit: 10
	},
	function(results) {
		for (var i = 0; i < results.length; i++) {
			console.log('Image URI: ' + results[i]);
		}
	},
	function (error) {
		console.log('Error: ' + error);
	}
);
```

The plugin returns an array with the paths to the images

## Libraries used
#### MultiImagePicker

For Android this plugin uses MultiImagePicker which is licensed under the MIT License

https://github.com/yazeed44/MultiImagePicker

#### ELCImagePicker

For iOS this plugin uses the ELCImagePickerController, with slight modifications for the iOS image picker. ELCImagePicker uses the MIT License which can be found in the file LICENSE.

https://github.com/B-Sides/ELCImagePickerController

## License
GNU GENERAL PUBLIC LICENSE
See license file