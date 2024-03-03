package com.example.dynamofit;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registrationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText fullNameEditText, emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        TextView appName = findViewById(R.id.app_name);
        setTextViewColor(appName, getResources().getColor(R.color.register_button_11),
                getResources().getColor(R.color.register_button_12));

        fullNameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void setTextViewColor(TextView textView, int... color) {
        TextPaint paint = textView.getPaint();
        float width = paint.measureText(textView.getText().toString());

        Shader shader = new LinearGradient(0, 0, width, textView.getTextSize(), color, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(shader);
        textView.setTextColor(color[0]);
    }

    private void registerUser() {
        String fullName = fullNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validate email format
        if (!isValidEmail(email)) {
            showToast("Invalid email format");
            return;
        }

        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            showToast("All fields must be filled");
            return;
        }

        if (password.length() < 6 || password.length() > 12) {
            showToast("Password must be between 6 and 12 characters");
            return;
        }

        // Check if OTP is selected
        EditText otpEditText = findViewById(R.id.otp);
        String otp = otpEditText.getText().toString().trim();
        if (TextUtils.isEmpty(otp)) {
            showToast("Please enter OTP");
            return;
        }

        // Continue with Firebase registration
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            showToast("Registration successful");
                            // You can perform additional actions here, such as saving user data to Firebase Database
                        } else {
                            showToast("Registration failed: " + task.getException().getMessage());
                        }
                    }
                });
    }

    private boolean isValidEmail(String email) {
        // Simple email format validation
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
