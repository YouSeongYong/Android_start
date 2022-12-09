package com.example.fullstackapplication.board

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.fullstackapplication.R
import com.example.fullstackapplication.utils.FBAuth
import com.example.fullstackapplication.utils.FBdatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class BoardWriteActivity : AppCompatActivity() {

    lateinit var imgLoad : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_write)
        //background trans parents

        // id 값 다 찾아오기
         imgLoad= findViewById<ImageView>(R.id.imgLoad)
        val etTitle= findViewById<EditText>(R.id.etContent)
        val etContent= findViewById<EditText>(R.id.etTitle)
        val imgWrite=findViewById<ImageView>(R.id.imgWrite)

        imgLoad.setOnClickListener {
            val intent= Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)

            launcher.launch(intent)
        }


        //모든 값 (title content time ...
        imgWrite.setOnClickListener {
            val title= etTitle.text.toString()
            val content= etContent.text.toString()

            //board
            // -key ( 게시물의 고유한 uid : push())
            //   -boardVO(title, content, 사용자 uid, time)

//            FBdatabase.getBoardRef().push().setValue(BoardVO("1","1","1","1"))
            //Auth
            val uid= FBAuth.getUid()
            val time= FBAuth.getTime()
            // 실제 데이터 넣기
            // setValue가 되기전에 미리 BoardVO가 저장될 key 값을(uid_)을 만들자   push() Url 값을 가지고 있음
            var key=  FBdatabase.getBoardRef().push().key.toString()
            FBdatabase.getBoardRef().child(key).setValue(BoardVO(title,content,uid,time))
            imgUpload(key)
            finish()// 이전페이지로 돌아가기
        }

    }

    val launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
       if(it.resultCode== RESULT_OK ){
           imgLoad.setImageURI(it.data?.data)
       }


    }
    fun imgUpload(key: String){

        val storage= Firebase.storage
        val storageRef =storage.reference
        val mountainsRef= storageRef.child("$key.png")
        // Get the data from an ImageView as bytes
        imgLoad.isDrawingCacheEnabled = true
        imgLoad.buildDrawingCache()
        val bitmap = (imgLoad.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }
}