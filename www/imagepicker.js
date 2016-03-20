/* global cordova, window */
/**
 * An Image Picker plugin for Cordova
 * 
 */

var ImagePicker = function() {

};

ImagePicker.prototype.getPictures = function(options, success, fail) {
	var params = {
		limit: options.limit ? options.limit : 20
	};
	
	if(options.fabBackgroundColor) {
		params.fabBackgroundColor = options.fabBackgroundColor;
	}
	if(options.doneFabIconTintColor) {
		params.doneFabIconTintColor = options.doneFabIconTintColor;
	}
	if(options.imageBackgroundColorWhenChecked) {
		params.imageBackgroundColorWhenChecked = options.imageBackgroundColorWhenChecked;
	}
	if(options.albumBackgroundColor) {
		params.albumBackgroundColor = options.albumBackgroundColor;
	}
	if(options.albumNameTextColor) {
		params.albumNameTextColor = options.albumNameTextColor;
	}
	if(options.albumImagesCountTextColor) {
		params.albumImagesCountTextColor = options.albumImagesCountTextColor;
	}

	return cordova.exec(success, fail, "ImagePicker", "getPictures", [params]);
};

ImagePicker.install = function() {
	if(!window.plugins) {
		window.plugins = {};
	}

	window.plugins.imagePicker = new ImagePicker();
	return window.plugins.imagePicker;
};

cordova.addConstructor(ImagePicker.install);
