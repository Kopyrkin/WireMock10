package ru.tinkoff.myupgradeapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import ru.tinkoff.myupgradeapplication.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        val dialog = view.context.let {
            AlertDialog.Builder(it)
        }.setMessage("Теперь ты автоматизатор").setTitle("Важное сообщение").create()


        binding.changeButton.setOnClickListener {
            val text1 = getResources().getString(R.string.first_text);
            val text2 = getResources().getString(R.string.second_text);
            binding.textviewFirst.text =
                if (binding.textviewFirst.text.contains(text1))
                    text2
                else
                    text1
        }
        binding.dialogButton.setOnClickListener {
            dialog.show()
        }

        binding.composeButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_ComposeGreetingFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}