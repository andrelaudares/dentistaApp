package com.example.dentistaapp

import android.content.ContentValues
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.dentistaapp.databinding.ActivityFormCadastroBinding
import com.example.dentistaapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

private  lateinit var binding: ActivityMainBinding
private lateinit var auth: FirebaseAuth
private fun updateUI(user: FirebaseUser?) {}

class FormCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCriarCad.setOnClickListener { view ->
            val email = binding.etEmailCad.text.toString()
            val senha = binding.etSenhaCad.text.toString()
            val endereco1 = binding.etEndereco1Cad.text.toString()
            val endereco2 = binding.etEndereco2Cad.text.toString()
            val endereco3 = binding.etEndereco3Cad.text.toString()
            val nome = binding.etNomeCad.text.toString()
            val cpf = binding.etCpfCad.text.toString()
            val telefone = binding.etTelefoneCad.text.toString()
            val curriculo = binding.etCurriculoCad.text.toString()

            if (email.isEmpty() || senha.isEmpty() || endereco1.isEmpty() || nome.isEmpty() || cpf.isEmpty() || curriculo.isEmpty()) {
                val snackbar =
                    Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()


            } else {
                binding.btnCriarCad.setOnClickListener {

                    val usuarioMap = hashMapOf(

                        "senha" to senha,
                        "nome" to nome,
                        "cpf" to cpf,
                        "telefone" to telefone,
                        "endereço1" to endereco1,
                        "endereço2" to endereco2,
                        "endereço3" to endereco3,
                        "curriculo " to curriculo

                    )
                    db.collection("Usuários").document(email)
                        .set(usuarioMap).addOnCompleteListener {
                            Log.d("db", "sucesso ao salvar o usuário!")

                        }.addOnFailureListener {
                        }

                }
                auth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this) { task ->

                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(ContentValues.TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                            updateUI(null)
                        }
                    }
            }


        }
    }
}