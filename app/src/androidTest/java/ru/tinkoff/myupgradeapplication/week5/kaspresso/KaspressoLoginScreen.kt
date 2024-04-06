package ru.tinkoff.myupgradeapplication.week5.kaspresso

import com.google.android.material.snackbar.SnackbarContentLayout
import com.google.android.material.textview.MaterialTextView
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import ru.tinkoff.myupgradeapplication.R
import ru.tinkoff.myupgradeapplication.week5.matchers.TextViewHintColorMatcher

class KaspressoLoginScreen : BaseScreen() {

    val editTextlogin = KEditText { withId(R.id.edittext_login) }
    val editTextpassword = KEditText { withId(R.id.edittext_password) }
    val buttonSubmit = KButton { withId(R.id.button_submit) }
    val buttonPrevious = KButton { withId(R.id.button_second) }
    val textOnSnackBar = KTextView {
        isInstanceOf(MaterialTextView::class.java)
        withParent { isInstanceOf(SnackbarContentLayout::class.java) }
    }

    fun checkLoginFieldHintColor(color: Int){
        editTextlogin.matches { TextViewHintColorMatcher(color) }
    }

    companion object {
        inline operator fun invoke(crossinline block: KaspressoLoginScreen.() -> Unit) =
            KaspressoLoginScreen().block()
    }
}