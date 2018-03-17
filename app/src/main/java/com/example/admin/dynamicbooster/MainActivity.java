package com.example.admin.dynamicbooster;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private TextView showVoiceText;
    private final int CODE_SPEECH_OUTPUT = 143;
    private ConstraintLayout constraintLayout;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintLayout = (ConstraintLayout) findViewById(R.id.activity_main);
        tts = new TextToSpeech(this, this);
        showVoiceText = (TextView)findViewById(R.id.showV);

        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                btnToOpenMic();
                return false;
            }
        });


    }

    private void btnToOpenMic() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speek now......");
        try {
            startActivityForResult(intent, CODE_SPEECH_OUTPUT);
        } catch (ActivityNotFoundException e) {


        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case CODE_SPEECH_OUTPUT:{
                if(resultCode == RESULT_OK && null!= data){
                    ArrayList<String> voiceInText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    showVoiceText.setText(voiceInText.get(0));
                }
                break;
            }

        }
        String check = showVoiceText.getText().toString();
        if (check.equals("1"))
        {
            Toast.makeText(getApplicationContext(), "Successfully logged in",
                    Toast.LENGTH_SHORT).show();
            speakOut();
        }
        else if (check.equals("2"))
        {


        }
        else{
            Toast.makeText(getApplicationContext(), "Sorry",
                    Toast.LENGTH_SHORT).show();
            reject();

        }
    }


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                // btnSpeak.setEnabled(true);
                initialCall();


            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }
    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
    // text to speech correct pw
    private void speakOut() {

        String text = "Welcome Its a pleasure to help you!! ";

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
    //text to speech wrong pw
    private void reject()
    {

        String mistake = "Oops , Sorry Please try again...Are u  User or Admin ? Please Enter 1 for User or 2 for Admin";
        tts.speak(mistake,TextToSpeech.QUEUE_FLUSH,null);
    }
    private void initialCall()
    {
        String msg = "Hello, Are u  User or Admin ? Please Enter 1 for User or 2 for Admin" ;
        tts.speak(msg,TextToSpeech.QUEUE_FLUSH,null);

    }


}


