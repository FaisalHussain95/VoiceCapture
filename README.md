# VoiceCapture 
This App is an Addon for the RogerVoice App
Actually the RogerVoice is using an intern VoIP server service to make their app working.

> Our objective is to make an android application that could stream voicecall audio to the RogerVoice App.

# Android Studio & SDK
We used Android Studio to create this app.
> This app have **INTERNET** permission but it was added just for **testing purpose** and should be removed since the app have **to be an offline Service**.

The ServerTest folder is a Java Listener App capable of listening streamed audio from our app, just for testing.

# Try It !
http://developer.android.com/sdk/index.html

# Bugs & ToDo
**ToDo**
 - Outgoing Call: detect when call is reached
 - Fix Warning issue: `W/Resources: Converting to string`
 - Improve memory usage

**Known Bugs**
 - We can hear the mic sound but we just selected the DownLink sound
http://developer.android.com/reference/android/media/MediaRecorder.AudioSource.html

#Links
http://developer.android.com/reference/android/media/MediaRecorder.html
http://developer.android.com/guide/topics/ui/themes.html
http://developer.android.com/reference/android/app/Service.html
http://stackoverflow.com/questions/15349987/stream-live-android-audio-to-server%3E
