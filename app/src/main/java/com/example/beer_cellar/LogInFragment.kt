package com.example.beer_cellar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.beer_cellar.databinding.FragmentLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LogInFragment : Fragment() {

    private var _binding: FragmentLogInBinding? = null

    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding.buttonLogin.setOnClickListener {
            val email = binding.edittextUsername.text.trim().toString()
            if (email.isEmpty()) {
                binding.edittextUsername.error = "No email"
                return@setOnClickListener
            }
            val password = binding.edittextPassword.text.trim().toString()
            if (password.isEmpty()) {
                binding.edittextPassword.error = "No password"
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithEmail:success")
                        val user = auth.currentUser
                        // updateUI(user)
                        findNavController().navigate(R.id.action_LogInFragment_to_MyBeersFragment)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithEmail:failure", task.exception)
                        binding.textviewMessageLogin.text =
                            "Authentication failed" + task.exception?.message
                        // updateUI(null)
                    }
                }
        }

        binding.buttonRegister.setOnClickListener {
            val email = binding.edittextCreateUsername.text.trim().toString()
            if (email.isEmpty()) {
                binding.edittextCreateUsername.error = "No email"
                return@setOnClickListener
            }
            val password = binding.edittextCreatePassword.text.trim().toString()
            if (password.isEmpty()) {
                binding.edittextCreatePassword.error = "No password"
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("APPLE", "createUserWithEmail:success")
                        val user = auth.currentUser
                        findNavController().navigate(R.id.action_LogInFragment_to_MyBeersFragment)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("APPLE", "createUserWithEmail:failure", task.exception)
                        binding.textviewMessageLogin.text =
                            "Registration failed " + task.exception?.message
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}