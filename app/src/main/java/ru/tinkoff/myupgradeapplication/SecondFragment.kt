package ru.tinkoff.myupgradeapplication

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.tinkoff.core.testing.demo.network.WikiApiService
import ru.tinkoff.myupgradeapplication.databinding.FragmentSecondBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    lateinit var wikiApiServe : WikiApiService
    var disposable: Disposable? = null
    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        wikiApiServe = WikiApiService.create(getWikiFromPref())
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.edittextLogin.setOnFocusChangeListener { view: View, hasFocus: Boolean ->
            if (hasFocus)
                binding.edittextLogin.setHintTextColor(resources.getColor(R.color.default_color_hint))
        }
        binding.edittextPassword.setOnFocusChangeListener { view: View, hasFocus: Boolean ->
            if (hasFocus)
                binding.edittextPassword.setHintTextColor(resources.getColor(R.color.default_color_hint))
        }
        binding.buttonWikiSearch.setOnClickListener {
            if (binding.etWikiRequest.text.isEmpty()) {
                Toast.makeText(activity,"Login field must be filled", Toast.LENGTH_SHORT).show()
            }
            else
                getWikiFromApi()
        }

        binding.buttonSubmit.setOnClickListener {

            view.let { activity?.hideKeyboard(it) }

            val ET_login_value = binding.edittextLogin.text
            val ET_password_value = binding.edittextPassword.text

            var snackMessage : String = "You enter login = $ET_login_value password = $ET_password_value"

            if (ET_login_value.isEmpty()) {
                snackMessage = "Login field must be filled!"
                binding.edittextLogin.setHintTextColor(resources.getColor(R.color.error_hint))
            } else
                binding.edittextLogin.setHintTextColor(resources.getColor(R.color.default_color_hint))


            if (ET_password_value.isEmpty()){
                snackMessage = "Password field must be filled!"
                binding.edittextPassword.setHintTextColor(resources.getColor(R.color.error_hint))
            } else
                binding.edittextPassword.setHintTextColor(resources.getColor(R.color.default_color_hint))


            if (ET_login_value.isEmpty() && ET_password_value.isEmpty())
                snackMessage = "Both of fields must be filled!"

            Snackbar.make(view, snackMessage, Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.fab)
                .setAction("Action", null).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun getWikiFromPref() : String{
        val mPrefs = activity?.getSharedPreferences("demo_url", AppCompatActivity.MODE_PRIVATE)
        var data :String? = null
        mPrefs?.let {
            val prefsEditor = mPrefs.edit()
            data = mPrefs.getString("wiki_url", null)
            prefsEditor.commit()
        }

        return data?:"https://en.wikipedia.org/w/"
    }


    private fun getWikiFromApi() {
        disposable = wikiApiServe.hitCountCheck("query", "json", "search", binding.etWikiRequest.text.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
//                    activity.let {
//                        AlertDialog.Builder(activity as Context)
//                    }.setMessage("Твой логин упоминается в  ${result.query.searchinfo.totalhits} статьях!")
//                        .setTitle("Результаты поиска")
//                        .create()
//                        .show()
                    if (result.query.search.isNotEmpty())
                    {
                        binding.twWikiResult.text = Html.fromHtml (result.query.search[0].snippet, Html.FROM_HTML_MODE_COMPACT )
                    } else
                        binding.twWikiResult.text = getString(R.string.no_result)
                },
                { error ->

                    Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
                }
            )
    }
}