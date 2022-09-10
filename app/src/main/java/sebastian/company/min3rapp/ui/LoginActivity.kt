package sebastian.company.min3rapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.databinding.ActivityLoginBinding
import sebastian.company.min3rapp.domain.model.User

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            goToMainActivity()
        }else{
            showLogin()
        }
    }


    private fun showLogin(){
        binding.getStartedButton.setOnClickListener {
            signUp()
        }

        binding.tAndCMessage.visibility = View.VISIBLE
        binding.getStartedButton.visibility = View.VISIBLE
    }

    private fun goToMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun signUp(){
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    createUser()
                }else{
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun createUser(){
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(auth.currentUser!!.uid).set(
            User(auth.currentUser!!.uid, dateJoined = Timestamp.now())
        ).addOnSuccessListener {
            goToMainActivity()
        }.addOnFailureListener {
            Toast.makeText(baseContext, "Authentication error", Toast.LENGTH_SHORT).show()
        }

    }
}