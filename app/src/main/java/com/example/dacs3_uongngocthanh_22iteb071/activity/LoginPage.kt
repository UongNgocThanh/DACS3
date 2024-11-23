package com.example.dacs3_uongngocthanh_22iteb071.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dacs3_uongngocthanh_22iteb071.model.UserData
import com.example.dacs3_uongngocthanh_22iteb071.databinding.ActivityLoginPageBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginPage : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPageBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("user")

        binding.signInBtn.setOnClickListener {
            val loginUsername = binding.usernameLogin.text.toString()
            val loginPassword = binding.passwordLogin.text.toString()

            if (loginUsername.isNotEmpty() && loginPassword.isNotEmpty()) {
                loginUser(loginUsername, loginPassword)
            } else {
                Toast.makeText(this@LoginPage, "All fields are mandatory", Toast.LENGTH_SHORT).show()
            }
        }

        binding.SignupRedirect.setOnClickListener {
            startActivity(Intent(this@LoginPage, RegisterPage::class.java))
            finish()
        }
    }

    private fun loginUser(username: String, password: String) {
        // Check for admin credentials
        if (username == "thanhadmin" && password == "123") {
            Toast.makeText(this@LoginPage, "Admin Login Successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,AdminActivity::class.java)
            startActivity(intent)
//            finish()
            return
        }

        // Regular user login logic
        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (userSnapshot in dataSnapshot.children) {
                        val userData = userSnapshot.getValue(UserData::class.java)
                        if (userData != null && userData.password == password) {
                            Toast.makeText(this@LoginPage, "Login Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginPage, MainActivity::class.java))
                            finish()
                            return
                        }
                    }
                }
                Toast.makeText(this@LoginPage, "Login Failed", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@LoginPage, "Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
