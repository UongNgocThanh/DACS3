package com.example.dacs3_uongngocthanh_22iteb071.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dacs3_uongngocthanh_22iteb071.model.UserData
import com.example.dacs3_uongngocthanh_22iteb071.databinding.ActivityRegisterPageBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener


class RegisterPage : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterPageBinding
    private lateinit var firebaseDatabase : FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("user")
        binding.signUpBtn.setOnClickListener {
            val signupUsername = binding.usernameSignup.text.toString()
            val signupPassword = binding.passwordSignup.text.toString()

            if (signupUsername.isNotEmpty()&&signupPassword.isNotEmpty()){
                signupUser(signupUsername,signupPassword)
            }else{
                Toast.makeText(this@RegisterPage,"All field are mandatory",Toast.LENGTH_SHORT).show()

            }
        }
        binding.SigninRedirect.setOnClickListener{
            startActivity(Intent(this@RegisterPage, LoginPage::class.java))
            finish()
        }
    }

    private fun signupUser(username:String,password:String){
        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()){
                    val id = databaseReference.push().key
                    val userData = UserData(id,username,password)
                    databaseReference.child(id!!).setValue(userData)
                    Toast.makeText(this@RegisterPage,"Sign Up Successful",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@RegisterPage, LoginPage::class.java))
                    finish()
                }else{
                    Toast.makeText(this@RegisterPage,"User already exists",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@RegisterPage,"Database Error: ${databaseError.message}",Toast.LENGTH_SHORT).show()

            }
        })
    }
}