package com.example.mypratice_bluetooth_requestpermission_1

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mypratice_bluetooth_requestpermission_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var TAG = "MyTag" + MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestPermission(permissionArray())

        setContentView(binding.root)
    }
        //進行版本檢查後回傳相應的權限陣列
        fun permissionArray(): Array<String>{
            //藍牙需要的基本權限
            var permissions = arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN
            )
            //如果API等級大於等於26，就將權限附加上去
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                permissions += arrayOf(
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_ADVERTISE
                )
            }
            return permissions
        }


    //請求權限，需傳入一個包含了權限的陣列
    fun requestPermission(permissionArray: Array<String>){
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissions ->
            permissionArray.forEach { permission ->
                val status = when(permissions[permission]){
                    true -> {"success"}
                    false -> {"fail"}
                    else -> {"unknow"}
                }
                Log.d(TAG, "requestPermission(): $permission: $status")
            }
        }.launch(permissionArray)
    }
}