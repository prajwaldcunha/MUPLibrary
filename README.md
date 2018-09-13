# MUPLibrary

##### This library supports uploading of multiple images using OKHTTP3. 
## Features
* Customizable Image Picker.
* Share images from gallery.
* Upload status on Notification bar.
* Progress Dialog while uploading.
* Specify compression rate for photos before uploading and local storage of compressed photos.
* Check for necessary permissions.
* Set Folder name for local storage of compressed photos.

## Sample Project

For more information how to use the library in Java checkout [Sample App](https://github.com/prajwaldcunha/MUPLibrary/tree/master/app) in repository.


## Add this to your project's `build.gradle`

```groovy
allprojects {
    repositories {
        maven { url "https://jitpack.io" }        
        maven { url "http://dl.bintray.com/lukaville/maven" }
    }
}
```

And add this to your module's `build.gradle` 

```groovy
dependencies {
	implementation 'com.github.prajwal:MUPLibrary:x.y.z'
}
```

change `x.y.z` to version in the [release page](https://github.com/prajwaldcunha/MUPLibrary/releases)


## Usage



### For uploading multiple images by passing an ArrayList of URI of images.

#### Request for permissions:
```java
private final int PERMISSION_ALL = 1;

final String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

        if (MUP.doesNotHavePermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            return;
        }
	
```

### For using image picker 
```java
	MUPPick.init()
		.pickImages(Activity ctx,String toolbarFolderTitleName,String toolbarImageTitleName,int maxNoImages,int theme)
```


#### Get the intent that starts this activity, in this case: gallery intent
```java
Intent data = getIntent();

if (data != null && data.getExtras() != null) {

            Object something = data.getExtras().get("android.intent.extra.STREAM");
            if (something instanceof Uri) {
                ArrayList<Uri> images = new ArrayList<>();
                Uri i = (Uri) something;
                images.add(i);

                Options options=new Options();                  // To use additional features
                options.enableNotification(true);		// To show notifications
                options.setFolderName("/folderName");		// Set FolderName to store compressed images before uploading
                options.setCompressionRate(50);			// Specify the compression rate for images. Eg: 50
		
                options.setNotificationOptions(R.drawable.baseline_notification_important_black_18,R.color.colorPrimary);
				// Set the drawable for notification and the color for notification bar.


		
                 MUPPick.init()							
                        .setProgressTitle(this, "Uploading Test")		// Set text to show on Progress Dialog
                        .upload(this, "http://example.com/", images, new ResponseListener() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("MUP", "Response: " + response);
                            }

                            @Override
                            public void onError(String error) {

                            }
                        },options);

            } else {
                @SuppressWarnings("unchecked")
                ArrayList<Uri> images = (ArrayList<Uri>) something;

                Options options=new Options();                 		// To use additional features
                options.enableNotification(true);			// To show notifications
                options.setFolderName("/folderName");			// Set FolderName to store compressed images before uploading
                options.setCompressionRate(50);				// Specify the compression rate for images. Eg: 50
		
                options.setNotificationOptions(R.drawable.baseline_notification_important_black_18,R.color.colorPrimary);
				// Set the drawable for notification and the color for notification bar.



                MUPPick.init()
                        .setProgressTitle(this, "Uploading")		// Set text to show on Progress Dialog
                        .upload(this, "http://example.com/", images,  new ResponseListener() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("MUP", "Response: " + response);
                            }

                            @Override
                            public void onError(String error) {

                            }
                        },options);
            }
```

## License
MIT License(https://github.com/prajwaldcunha/MUPLibrary/blob/master/LICENSE)

