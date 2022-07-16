package com.example.dialogfragment

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.dialogfragment.databinding.ActivityMainBinding
import com.example.dialogfragment.databinding.CustomDialogBinding
import com.example.dialogfragment.databinding.EdtBinding
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var arr_item by Delegates.notNull<Int>()
    val arr = listOf("one", "two", "three").toTypedArray()
    val arr_bool = listOf(true, false, true).toBooleanArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.btFirstDialog.setOnClickListener { showDialog() }
        binding.button2.setOnClickListener { showDialogMultiChoice() }
        binding.button3.setOnClickListener { showThirdDialog() }
        binding.btfc.setOnClickListener { showDi() }

        arr_item = savedInstanceState?.getInt(KEY_ARR) ?: 1
        updateUI()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_ARR, arr_item)
    }

    private fun showDialog(){

        val listener = DialogInterface.OnClickListener { d, which ->
            when(which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    val index = (d as AlertDialog).listView.checkedItemPosition
                    arr_item = index
                    updateUI()
                }
                DialogInterface.BUTTON_NEGATIVE -> showMessage("You've just tapped on No")
            }
        }

        val dialog = AlertDialog.Builder(this)
            .setIcon(R.drawable.ic_launcher_background)
            .setTitle("Are you sure?")
            .setSingleChoiceItems(arr,arr_item, null)
            .setPositiveButton("Yes", listener)
            .setNegativeButton("No", listener)
            .create()

        dialog.show()
    }

    private fun showThirdDialog(){
        var string = ""
        val dialogBinding = EdtBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(this)
            .setIcon(R.drawable.ic_launcher_background)
            .setTitle("Are you sure?")
            .setView(dialogBinding.root)
            .setPositiveButton("Yes") { _, _ ->
                Toast.makeText(this, dialogBinding.rdt.text, Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("No", null)
            .create()

        dialog.show()
    }

    private fun showDi(){
        val dibinding = CustomDialogBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(this)
            .setView(dibinding.root)
            .create()

        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

    }

    private fun showDialogMultiChoice(){
        val dialog = AlertDialog.Builder(this)
            .setIcon(R.drawable.ic_launcher_background)
            .setTitle("Are you sure?")
            .setMultiChoiceItems(arr,arr_bool) { _, which, isChecked ->
                arr_bool[which] = isChecked
            }
            .create()

        dialog.show()
    }

    private fun updateUI() {
        binding.textView.text = arr[arr_item]
    }

    private fun showMessage(text: String){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val KEY_ARR = "KEY_ARR"
    }
}