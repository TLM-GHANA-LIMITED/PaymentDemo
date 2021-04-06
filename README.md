# PaymentDemo
Demo expressPay for Android using both Java and Kotlin.


## ***PREVIEW***

| HOME PAGE | PAYMENT PAGE |
| ------------- | ------------- |
| ![Main Page](screenshots/2.png)| ![Main Page](screenshots/1.png)|



Requirements
==============
  - Android Studio 4.0 and above
  - kotlin_version = 1.4.32
  -  BuildToolsVersion : 30.0.3
  -  CompileSdkVersion : 30
  -  MinSdkVersion : 16
  - TargetSdkVersion : 30
  -  Gradle Version : 4.1.3


FILL IN YOUR OWN DETAILS
====================
```
    public static String ENDPOINT = "https://sandbox.expresspaygh.com/api/sdk/php/server.php";
    public static String SUBMIT = "submit";
    public static String ORDER_ID = "";
    public static String CURRENCY = "GHS";
    public static String AMOUNT = "";
    public static String ORDER_DESC = "";
    public static String USER_NAME = "";
    public static String FIRST_NAME = "";
    public static String LAST_NAME = "";
    public static String EMAIL = "";
    public static String PHONE = "";
    public static String ACCOUNT_NUMBER = "";


```

   DEPENDENCIES USED.
  =================

```

    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'androidx.appcompat:appcompat:1.2.0'
    implementation 'org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version'
    implementation 'androidx.core:core-ktx:1.3.2'
    implementation 'com.google.android.material:material:1.3.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'

    //expressPay SDK
    implementation (name: 'expressPayLibrary-1.42', ext:'aar')

    //Networking
    implementation 'com.android.volley:volley:1.2.0'

    //Lottie
    implementation 'com.airbnb.android:lottie:3.4.1'

    //Testing
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.2'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'


```