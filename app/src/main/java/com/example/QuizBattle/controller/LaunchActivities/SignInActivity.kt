package com.example.QuizBattle.controller.LaunchActivities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.QuizBattle.model.FirestoreRepoes.FirestoreRepoUser
import com.example.QuizBattle.R
import com.example.QuizBattle.controller.GameActivity
import com.example.QuizBattle.model.PlayerModel.Player
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

/**

SignInActivity is an activity class responsible for handling user authentication using Google Sign-In.
It works with the FirebaseAuth and GoogleSignInClient to manage the sign-in process and add new users to Firestore.
The signIn function initiates the Google Sign-In process.
The firebaseAuthWithGoogle function authenticates the user with Firebase using the Google Sign-In token.
The addUserToFireStore function adds a new user to Firestore if they do not already exist.

 */
class SignInActivity: AppCompatActivity(){
    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var signINButton: SignInButton
    private val firebaseRepoUser: FirestoreRepoUser = FirestoreRepoUser()
    companion object{
        private const val RC_SIGN_IN=120
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        signINButton=findViewById(R.id.google_sign_in_button)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient=GoogleSignIn.getClient(this,gso)
        mAuth = FirebaseAuth.getInstance()
        signINButton.setOnClickListener {
            signIn()
        }
    }
    private fun signIn(){
        val signInIntent=googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("SingInActivity", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            //Firebase (account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("SingInActivity", "Google sign in failed", e)
            }
        }

    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("signInActivity", "signInWithCredential:success")
                    val user= mAuth.currentUser
                    if (user != null){
                       lifecycleScope.launch{
                           addUserToFireStore(user.uid)
                       }
                    }
                    Intent(this, GameActivity::class.java).also { startActivity(it) }
                    finish()
                }

                else {
                    // If sign in fails, display a message to the user.
                    Log.w("signInActivity", "signInWithCredential:failure", task.exception)
                    finish()
                }
            }
    }
    private suspend fun addUserToFireStore(uid: String) {
        val userSnapshot = firebaseRepoUser.getUser(uid)
        if (!userSnapshot.exists()) {
            val user = mAuth.currentUser
            val newPlayer = Player(
                userUid = uid,
                displayName = user?.displayName ?: "",
                userEmail = user?.email ?: "",
                allTimeScore = 0,
                dailyQuizTaken = false,
                numQuizzesTaken=0
            )
            firebaseRepoUser.addUser(newPlayer, uid)
        }
    }

}
