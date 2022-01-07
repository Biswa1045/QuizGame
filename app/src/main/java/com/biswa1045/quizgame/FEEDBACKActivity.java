package com.biswa1045.quizgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FEEDBACKActivity extends AppCompatActivity {
EditText sub,feedback;
Button btsend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_e_e_d_b_a_c_k);
        Toolbar toolbar = findViewById(R.id.toolbar_feedback);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Feedback");
        sub=findViewById(R.id.feedback_sub);
        feedback=findViewById(R.id.feedback_feedback);
        btsend=findViewById(R.id.feedback_btn);
       btsend.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String to="biswajitapp1045@gmail.com";
               String subject=sub.getText().toString();
               String message=feedback.getText().toString();


               Intent email = new Intent(Intent.ACTION_SEND);
               email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
               email.putExtra(Intent.EXTRA_SUBJECT, subject);
               email.putExtra(Intent.EXTRA_TEXT, message);

               //need this to prompts email client only
               email.setType("message/rfc822");

               startActivity(Intent.createChooser(email, "Choose an Email client :"));
           }
       });
    }
}