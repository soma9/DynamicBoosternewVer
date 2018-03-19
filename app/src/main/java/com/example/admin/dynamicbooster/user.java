package com.example.admin.dynamicbooster;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class user extends Activity implements TextToSpeech.OnInitListener {
TextView input1;
EditText input;
    private final  int CODE_SPEECH_OUTPUT2=143;
    private ConstraintLayout constraintLayout2;
    private TextToSpeech tts2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
  input1 = (TextView) findViewById(R.id.input1);
  input = (EditText)findViewById(R.id.input);
  constraintLayout2 = (ConstraintLayout)findViewById(R.id.con2);
tts2 =new TextToSpeech(this,this);
constraintLayout2.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        btnToOpenMic();
        return false;
    }
});

    }
    private void btnToOpenMic()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speek now......");
        try{
            startActivityForResult(intent,CODE_SPEECH_OUTPUT2);
        }
        catch (ActivityNotFoundException e)
        {


        }

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case CODE_SPEECH_OUTPUT2:{
                if(resultCode == RESULT_OK && null!= data){
                    ArrayList<String> voiceInText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    input1.setText(voiceInText.get(0));
                }
                break;
            }

        }
        String check = input1.getText().toString();
        double num = Integer.parseInt(check);
        if (num==1234567890)
        {
           Intent intent3 = new Intent(user.this,User2.class);
           startActivity(intent3);
        }
        else{
            Toast.makeText(getApplicationContext(), "Sorry",
                    Toast.LENGTH_SHORT).show();
            reject();

        }
    }

    @Override
    protected void onDestroy() {
        if (tts2 != null) {
            tts2.stop();
            tts2.shutdown();
        }
        super.onDestroy();
    }
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts2.setLanguage(Locale.US);

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


    //text to speech wrong pw
    private void reject()
    {

        String mistake = "Oops , Sorry Please try again Please enter your phone number";
        tts2.speak(mistake,TextToSpeech.QUEUE_FLUSH,null);
    }
    private void initialCall()
    {
        String msg = "Please Enter your Phone Number" ;
        tts2.speak(msg,TextToSpeech.QUEUE_FLUSH,null);

    }
}
