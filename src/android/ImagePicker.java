package cordova.imagepicker.multiple;

import net.yazeed44.imagepicker.util.Picker;
import net.yazeed44.imagepicker.model.ImageEntry;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Color;
import android.media.ExifInterface;

public static final int REQUEST_CODE = 0;
public static final String READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;

public class ImagePicker extends CordovaPlugin {
	
	private CallbackContext callbackContext;
	private JSONObject params;

    @Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		//Intent intent = new Intent(cordova.getActivity(), MultiImageChooserActivity.class);
		this.callbackContext = callbackContext;
		this.params = args.getJSONObject(0);
		
		if(action.equals("getPictures")) {
			if(cordova.hasPermission(READ_STORAGE)) {
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
						.setPickMode(limit > 1 ? Picker.PickMode.MULTIPLE_IMAGES : Picker.PickMode.SINGLE_IMAGE)
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
				else {
					getPermission(REQUEST_CODE);
				}
			}
			
			return true;
		}
		
		return false;
	}

	protected void getPermission(int requestCode) {
		cordova.requestPermission(this, requestCode, READ_STORAGE);
	}

	public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) throws JSONException {
		for(int r:grantResults) {
			if(r == PackageManager.PERMISSION_DENIED) {
				callbackContext.error("Permissions");

				return;
			}
		}
    }
	
	private class MyPickListener implements Picker.PickListener {

		@Override
		public void onPickedSuccessfully(final ArrayList<ImageEntry> images) {
			JSONArray imageArray = new JSONArray();
			
			for(int i = 0; i < images.size(); i++) {
				String filePath = images.get(i).path;
				int orientation = 1;

				try {
					orientation = getExifOrientation(filePath);
				} catch(IOException e) {

				}

				JSONObject imageData = new JSONObject();
				JSONObject exifData = new JSONObject();

				try {
					imageData.put("path", filePath);
				} catch(JSONException e) {

				}

				try {
					exifData.put("orientation", orientation);
				} catch(JSONException e) {

				}

				try {
					imageData.put("exif", exifData);
				} catch(JSONException e) {

				}


				imageArray.put(imageData);
			}
			
			callbackContext.success(imageArray);
		}

		@Override
		public void onCancel() {
			//User canceled the pick activity
			callbackContext.error("Cancelled");
		}

		private int getExifOrientation(String src) throws IOException {
			int orientation = 1;

			ExifInterface exif = new ExifInterface(src);
			String orientationString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
			try {
				orientation = Integer.parseInt(orientationString);
			}
			catch(NumberFormatException e){}

			return orientation;
		}
	}
}
