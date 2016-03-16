package cordova.imagepicker.multiple;

import net.yazeed44.imagepicker.util.Picker;
import net.yazeed44.imagepicker.model.ImageEntry;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;

public class ImagePicker extends CordovaPlugin {
	
	private CallbackContext callbackContext;
	private JSONObject params;

    @Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		//Intent intent = new Intent(cordova.getActivity(), MultiImageChooserActivity.class);
		this.callbackContext = callbackContext;
		this.params = args.getJSONObject(0);
		
		if(action.equals("getPictures")) {
			int limit = 0;
			int fabBackgroundColor = Color.BLACK;
			int doneFabIconTintColor = Color.WHITE;
			int imageBackgroundColorWhenChecked = Color.BLACK;
			int albumBackgroundColor = Color.BLACK;
			int albumNameTextColor = Color.WHITE;
			int albumImagesCountTextColor = Color.WHITE;
			
			if(this.params.has("limit")) {
				limit = this.params.getInt("limit");
			}
			if(this.params.has("fabBackgroundColor")) {
				fabBackgroundColor = Color.parseColor(this.params.getString("fabBackgroundColor"));
			}
			if(this.params.has("doneFabIconTintColor")) {
				doneFabIconTintColor = Color.parseColor(this.params.getString("doneFabIconTintColor"));
			}
			if(this.params.has("imageBackgroundColorWhenChecked")) {
				imageBackgroundColorWhenChecked = Color.parseColor(this.params.getString("imageBackgroundColorWhenChecked"));
			}
			if(this.params.has("albumBackgroundColor")) {
				albumBackgroundColor = Color.parseColor(this.params.getString("albumBackgroundColor"));
			}
			if(this.params.has("albumNameTextColor")) {
				albumNameTextColor = Color.parseColor(this.params.getString("albumNameTextColor"));
			}
			if(this.params.has("albumImagesCountTextColor")) {
				albumImagesCountTextColor = Color.parseColor(this.params.getString("albumImagesCountTextColor"));
			}			
			
			if(this.cordova != null) {
				// You can change many settings in builder like limit , Pick mode and colors
				new Picker.Builder(cordova.getActivity(),new MyPickListener())
					// Library settings
					.setPickMode(Picker.PickMode.MULTIPLE_IMAGES)
					.setLimit(limit) // set maximum number of pictures to pick
					.setVideosEnabled(false) // disable videos support
					.disableCaptureImageFromCamera() // disable camera
					
					// Theme colors
					.setFabBackgroundColor(fabBackgroundColor) // floating action button background color
					.setDoneFabIconTintColor(doneFabIconTintColor) // floating action icon color
					.setImageBackgroundColorWhenChecked(imageBackgroundColorWhenChecked) // border around selected image
					.setAlbumBackgroundColor(albumBackgroundColor) // album title bar background color
					.setAlbumNameTextColor(albumNameTextColor) // album title bar text color
					.setAlbumImagesCountTextColor(albumImagesCountTextColor) // album title bar images count number color
					
					.build()
					.startActivity();
			}
			
			return true;
		}
		
		return false;
	}

	/*private void getPictures(int max, CallbackContext callbackContext) {
		//this.callbackContext.success(max);
		
		
	}*/
	
	private class MyPickListener implements Picker.PickListener {

		@Override
		public void onPickedSuccessfully(final ArrayList<ImageEntry> images) {
			ArrayList<String> fileNames = new ArrayList<String>();
			
			for(int i = 0; i < images.size(); i++) {
				fileNames.add(images.get(i).path);
			}
			
			JSONArray res = new JSONArray(fileNames);
			callbackContext.success(res);
		}

		@Override
		public void onCancel() {
			//User canceled the pick activity
			callbackContext.error("Cancelled");
		}
	}
}