package ru.tinkoff.myupgradeapplication.week5.kakao.screens

import androidx.appcompat.widget.AppCompatTextView
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.SnackbarContentLayout
import com.google.android.material.textview.MaterialTextView
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KSnackbar
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matchers
import ru.tinkoff.myupgradeapplication.R
import ru.tinkoff.myupgradeapplication.week5.matchers.TextViewHintColorMatcher

class KakaoLoginScreen : Screen<KakaoLoginScreen>() {
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

}