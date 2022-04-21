package com.yusufyildiz.chatappwithfirebase.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yusufyildiz.chatappwithfirebase.Chat
import com.yusufyildiz.chatappwithfirebase.R
import com.yusufyildiz.chatappwithfirebase.adapter.ChatRecyclerAdapter
import com.yusufyildiz.chatappwithfirebase.databinding.FragmentChatBinding


class ChatFragment : Fragment() {

    private lateinit var binding : FragmentChatBinding
    private lateinit var firestore : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var adapter : ChatRecyclerAdapter
    private var chats = arrayListOf<Chat>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firestore = Firebase.firestore
        auth = Firebase.auth

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ChatRecyclerAdapter()
        binding.chatRecycler.adapter = adapter
        binding.chatRecycler.layoutManager = LinearLayoutManager(context)

        binding.sendButtonImage.setOnClickListener {

            auth.currentUser?.let { user->

                val userEmail = user.email
                val chatText = binding.chatText.text
                val date = FieldValue.serverTimestamp()

                val dataMap = HashMap<String,Any>()
                dataMap.put("text",chatText.toString())
                dataMap.put("user",userEmail!!)
                dataMap.put("date",date)

                firestore.collection("Chats").add(dataMap).addOnSuccessListener {

                    binding.chatText.setText("")

                }.addOnFailureListener{ e->
                    Toast.makeText(context,e.localizedMessage,Toast.LENGTH_LONG).show()
                    binding.chatText.setText("")
                }
            }

        }

        firestore.collection("Chats").orderBy("date",Query.Direction.ASCENDING).addSnapshotListener { value, error ->

            if(error != null)
            {
                Toast.makeText(context,error.localizedMessage,Toast.LENGTH_LONG).show()
            }
            else
            {
                if(value != null)
                {
                    if(value.isEmpty)
                    {
                        Toast.makeText(context,"Mesajınız Yok",Toast.LENGTH_LONG).show()
                    }
                    else
                    {
                        val documents = value.documents
                        chats.clear()

                        for(document in documents)
                        {
                            val text = document.get("text") as String // any cast to string
                            val user = document.get("user") as String
                            val chat = Chat(user,text)
                            chats.add(chat)
                            adapter.chats = chats

                        }


                    }
                    adapter.notifyDataSetChanged()
                }
            }

        }




    }

}