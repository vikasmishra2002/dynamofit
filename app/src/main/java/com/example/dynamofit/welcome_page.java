package com.example.dynamofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;

public class welcome_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        TextView tagline = findViewById(R.id.Tag);
        if (tagline != null) {
            setTextViewColor(tagline, getResources().getColor(R.color.register_button_11),
                    getResources().getColor(R.color.register_button_12));
        }

    }

    public void register(View view) {
        startActivity(new Intent(welcome_page.this, registrationActivity.class));
    }

    private void setTextViewColor(TextView textView, int... color) {
        TextPaint paint = textView.getPaint();
        float width = paint.measureText(textView.getText().toString());

        Shader shader = new LinearGradient(0, 0, width, textView.getTextSize(), color, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(shader);
        textView.setTextColor(color[0]);
    }
}
