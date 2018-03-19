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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private TextView showVoiceText;
    private final int CODE_SPEECH_OUTPUT = 143;
    private ConstraintLayout constraintLayout;
    private TextToSpeech tts;
    Button admin,user,register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintLayout = (ConstraintLayout) findViewById(R.id.activity_main);
        tts = new TextToSpeech(this, this);
        showVoiceText = (TextView)findViewById(R.id.showV);
        admin =(Button)findViewById(R.id.admin);
        user=(Button)findViewById(R.id.user);
        register=(Button)findViewById(R.id.register);

        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                btnToOpenMic();
                return false;
            }
        });
admin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       Intent intent1 = new Intent(MainActivity.this,AdminActivity.class);
       startActivity(intent1);
    }
});
user.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent intent2 =new Intent(MainActivity.this,user.class);
        startActivity(intent2);

    }
});
register.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
Intent intent3 = new Intent(MainActivity.this,Register.class);
startActivity(intent3);
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
        if (check.equals("user"))
        {
Intent intent1 = new Intent(MainActivity.this,user.class);
startActivity(intent1);
        }
        else if (check.equals("admin"))
        {
Intent intent2 = new Intent(MainActivity.this,AdminActivity.class);
startActivity(intent2);
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
        tts.setSpeechRate(0.9f);
        String mistake = "Oops , Sorry Please try again...Are u  User or Admin ? Please say user if u r User or Admin for Admin";
        tts.speak(mistake,TextToSpeech.QUEUE_FLUSH,null);
    }
    private void initialCall()
    {
        String msg = "Hello, Are u  User or Admin ? Please say user if u r User or Admin for Admin" ;
        tts.setSpeechRate(0.9f);
        tts.speak(msg,TextToSpeech.QUEUE_FLUSH,null);

    }


}


