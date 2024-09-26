package ru.tinkoff.myupgradeapplication.week5.kakao.screens

import androidx.appcompat.widget.AppCompatTextView
import androidx.test.espresso.matcher.ViewMatchers
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.SnackbarContentLayout
import com.google.android.material.textview.MaterialTextView
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KSnackbar
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import ru.tinkoff.myupgradeapplication.R

class KakaoFirstScreen: Screen<KakaoFirstScreen>(){
    val nextButton = KButton { withId(R.id.button_first) }
    val fab = KButton { withId(R.id.fab)}

    val textOnSnackBar = KTextView {
        isInstanceOf(MaterialTextView::class.java)
        withParent { isInstanceOf(SnackbarContentLayout::class.java) }
    }

    val screenTitle = KTextView {
        isInstanceOf(AppCompatTextView::class.java)
        withParent { isInstanceOf(MaterialToolbar::class.java) }
    }

}