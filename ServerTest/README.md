# How to use this Listener Test App

**Pre-requisites**
------------------
*For windows Only - Should also work on linux*

 - JDK installed
 - Windows path to JDK configured
 - The IP address of your PC should be entered in the CallHeader.java file located in the Android App folder Main activity
*Please use Android Studio to avoid problems during process*

**Running the listener**
-----------------------
*For windows Only - Should also work on linux*

 1. Launch the windows terminal <kbd>windows</kbd> + <kbd>X</kbd>
 2. Goto the ServerTest folder of the cloned repo:  `cd PATHTO\ServerTest\`
 3. Run `javac ReceiveAudio.java`
 4. And `java -cp . Server`
 5. Your Listener should be running
 ![Sample CMD](http://i.imgsafe.org/1055f73.png "Sample CMD")
 
 6. Now you can run the Android App Service and start calling and testing.
 The call audio should be streamed to your PC audio system.

>Thanks to @chuckliddell0 on StackOverflow
>http://stackoverflow.com/questions/15349987/stream-live-android-audio-to-server%3E
