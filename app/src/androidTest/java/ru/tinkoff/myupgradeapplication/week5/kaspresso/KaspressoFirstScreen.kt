package ru.tinkoff.myupgradeapplication.week5.kaspresso

import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.SnackbarContentLayout
import com.google.android.material.textview.MaterialTextView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import ru.tinkoff.myupgradeapplication.R

class KaspressoFirstScreen : BaseScreen() {
    val nextButton = KButton { withId(R.id.button_first) }
    val fab = KButton { withId(R.id.fab)}
    val composeButton = KButton { withId(R.id.compose_button) }

    val textOnSnackBar = KTextView {
        isInstanceOf(MaterialTextView::class.java)
        withParent { isInstanceOf(SnackbarContentLayout::class.java) }
    }

    val screenTitle = KTextView {
        isInstanceOf(AppCompatTextView::class.java)
        withParent { isInstanceOf(MaterialToolbar::class.java) }
    }


    companion object {
        inline operator fun invoke(crossinline block: KaspressoFirstScreen.() -> Unit) =
            KaspressoFirstScreen().block()
    }
}