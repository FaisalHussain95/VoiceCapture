package fr.product.slf.calls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import android.util.Log;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * Created by faisa on 08/12/2015.
 */
public class CallHelper {
    /*
     * Init service
     */
    public byte[] buffer;
    public static DatagramSocket socket;
    private int port=50005;

    AudioRecord recorder;

    private int sampleRate = 44100 ; // 44100 for music
    private int channelConfig = AudioFormat.CHANNEL_IN_MONO;
    private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    int minBufSize = 3840;//AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat);
    private boolean status = false;


    /**
     * Listener to detect incoming calls.
     */
    private class CallStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    // called when someone is ringing to this phone

                    Toast.makeText(ctx,
                            ctx.getString(R.string.inGoingCall_msg)+incomingNumber,
                            Toast.LENGTH_SHORT).show();
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    // When call is running
                    status = true;
                    startStreaming();
                    Toast.makeText(ctx,
                            ctx.getString(R.string.inHookCall_msg)+incomingNumber,
                            Toast.LENGTH_SHORT).show();
                    break;

                case TelephonyManager.CALL_STATE_IDLE:
                    // When call end
                    if (status) {
                        status = false;
                        recorder.release();

                        Toast.makeText(ctx,
                                ctx.getString(R.string.endCall_msg),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    /**
     * Broadcast receiver to detect the outgoing calls.
     */
    public class OutgoingReceiver extends BroadcastReceiver {
        public OutgoingReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            //status = true;
            //startStreaming();
            Toast.makeText(ctx,
                    ctx.getString(R.string.outGoingCall_msg)+number,
                    Toast.LENGTH_SHORT).show();
        }

    }

    private Context ctx;
    private TelephonyManager tm;
    private CallStateListener callStateListener;

    private OutgoingReceiver outgoingReceiver;

    public CallHelper(Context ctx) {
        this.ctx = ctx;

        callStateListener = new CallStateListener();
        outgoingReceiver = new OutgoingReceiver();
    }

    /**
     * Start calls detection.
     */
    public void start() {
        tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        ctx.registerReceiver(outgoingReceiver, intentFilter);
    }

    /**
     * Stop calls detection.
     */
    public void stop() {
        tm.listen(callStateListener, PhoneStateListener.LISTEN_NONE);
        ctx.unregisterReceiver(outgoingReceiver);
    }
    public void startStreaming() {

        Log.d("VS", "SLF");
        Thread streamThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    socket = new DatagramSocket();
                    Log.d("VS", "Socket Created");

                    buffer = new byte[minBufSize];
                    Log.d("VS","Buffer created of size " + minBufSize);
                    DatagramPacket packet;

                    final InetAddress destination = InetAddress.getByName("192.168.1.24");
                    Log.d("VS", "Address retrieved");


                    recorder = new AudioRecord(MediaRecorder.AudioSource.VOICE_DOWNLINK,sampleRate,channelConfig,audioFormat,minBufSize*10);
                    Log.d("VS", "Recorder initialized");

                    recorder.startRecording();


                    while(status) {

                        //reading data from Call into buffer
                        minBufSize = recorder.read(buffer, 0, buffer.length);

                        //putting buffer in the packet
                        packet = new DatagramPacket (buffer,buffer.length,destination,port);

                        socket.send(packet);
                        System.out.println("MinBufferSize: " + minBufSize);

                    }



                } catch(UnknownHostException e) {
                    Log.e("VS", "UnknownHostException");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("VS", "IOException");
                }
            }

        });
        streamThread.start();
    }

}
