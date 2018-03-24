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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class UserPassword extends AppCompatActivity implements TextToSpeech.OnInitListener  {
    TextView userPin,textInput;
    EditText pin;
    private final  int CODE_SPEECH_OUTPUT2=143;
    private ConstraintLayout constraintLayout2;
    private TextToSpeech tts2;
    Button bt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_password);
        userPin = (TextView) findViewById(R.id.p1);
        pin = (EditText) findViewById(R.id.p2);
        bt = (Button) findViewById(R.id.bt5);

        constraintLayout2 = (ConstraintLayout) findViewById(R.id.userPw);
        tts2 = new TextToSpeech(this, this);

        tts2 = new TextToSpeech(this, this);
        constraintLayout2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                btnToOpenMic();
                return false;
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin2 = pin.getText().toString();
                int finalpin = Integer.parseInt(pin2);
                if(finalpin == 1234)
                {
                    Intent intent = new Intent(UserPassword.this,User2.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(UserPassword.this,
                            "Sorry Please Try again", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void btnToOpenMic() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speek now......");
        try {
            startActivityForResult(intent, CODE_SPEECH_OUTPUT2);
        } catch (ActivityNotFoundException e) {


        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case CODE_SPEECH_OUTPUT2:{
                if(resultCode == RESULT_OK && null!= data){
                    ArrayList<String> voiceInText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textInput.setText(voiceInText.get(0));
                }
                break;
            }

        }

        String check = textInput.getText().toString();
        int num = Integer.parseInt(check);
        if (num==1234)
        {
            Intent intent3 = new Intent(UserPassword.this,User2.class);
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
    private void reject()
    {

        String mistake = "Oops , Sorry Please try again Please enter your pin number";
        tts2.speak(mistake,TextToSpeech.QUEUE_FLUSH,null);
    }
    private void initialCall()
    {
        String msg = "Please Enter your Pin Number" ;
        tts2.speak(msg,TextToSpeech.QUEUE_FLUSH,null);

    }
}
