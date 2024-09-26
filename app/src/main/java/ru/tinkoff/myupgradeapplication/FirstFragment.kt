package ru.tinkoff.myupgradeapplication

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import ru.tinkoff.myupgradeapplication.databinding.FragmentFirstBinding
import ru.tinkoff.myupgradeapplication.network.RandomUserService

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    lateinit var randomUserApiService : RandomUserService

    var disposable: Disposable? = null
    lateinit var dialog : AlertDialog

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        randomUserApiService = RandomUserService.create(getPersonFromPref())
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        dialog = view.context.let {
            AlertDialog.Builder(it)
        }.setPositiveButton("OK") {
                dialog, which -> dialog.dismiss()
        }.create()


        binding.changeButton.setOnClickListener {
            val text1 = getResources().getString(R.string.first_text);
            val text2 = getResources().getString(R.string.second_text);
            binding.textviewFirst.text =
                if (binding.textviewFirst.text.contains(text1))
                    text2
                else
                    text1
        }
        binding.showPerson.setOnClickListener {
            //dialog.show()
            getRandPersons()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getPersonFromPref() : String{
        val mPrefs = activity?.getSharedPreferences("demo_url", AppCompatActivity.MODE_PRIVATE)
        var data :String? = null
        mPrefs?.let {
            val prefsEditor = mPrefs.edit()
            data = mPrefs.getString("url", null)
            prefsEditor.commit()
        }

        return data?:"https://randomuser.me/"
    }



    private fun getRandPersons() {
        disposable = randomUserApiService.getRandPersons()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {
                result ->
                if (!result.results.isEmpty())
                {
                    val person = result.results.first()

                    with(binding.personLayout){
                        Picasso.get().load(person.picture.medium).into(personAvatar)
                        personName.text = "${person.name.title} ${person.name.first} ${person.name.last}"
                        personEmail.text = person.email
                        personPhone.text = person.phone
                        personAddress.text = "${person.location.city} ${person.location.street.name}, ${person.location.country}"
                    }
                    binding.personLayout.root.visibility = View.VISIBLE
                }
                else
                    binding.personLayout.root.visibility = View.GONE

            },
                { error ->
                    if (error is HttpException)
                    {
                        with(dialog) {
                            setMessage(error.localizedMessage)
                            setTitle("Something wrong")
                            show()
                        }
                    }
                    binding.personLayout.root.visibility = View.GONE }
            )
    }
}