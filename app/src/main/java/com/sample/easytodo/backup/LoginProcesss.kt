package com.sample.easytodo.backup

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.services.drive.DriveScopes
import com.sample.easytodo.feature_todo.presentation.util.print

const val CONST_SIGN_IN = 1000

fun isUserSignedIn(context: Context): Boolean {

    val account = GoogleSignIn.getLastSignedInAccount(context)
    if (account != null){
        "Signed in user -  ${account.email}".print()
    }
    return account != null

}
fun Activity.GoogleSignInInApp() {

    if (!isUserSignedIn(this)) {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .requestScopes(Scope(DriveScopes.DRIVE))
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, CONST_SIGN_IN)
    }

}

fun handleSignData(data: Intent?) {
    // The Task returned from this call is always completed, no need to attach
    // a listener.
    GoogleSignIn.getSignedInAccountFromIntent(data)
        .addOnCompleteListener {
            "isSuccessful ${it.isSuccessful}".print()
            if (it.isSuccessful){
                // user successfully logged-in
                "account ${it.result?.account}".print()
                "displayName ${it.result?.displayName}".print()
                "Email ${it.result?.email}".print()
            } else {
                // authentication failed
                "exception ${it.exception}".print()
            }
        }

}