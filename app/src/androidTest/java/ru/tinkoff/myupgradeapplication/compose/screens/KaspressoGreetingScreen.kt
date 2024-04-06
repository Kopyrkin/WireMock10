package ru.tinkoff.myupgradeapplication.compose.screens

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode
import ru.tinkoff.myupgradeapplication.compose.GreetingTestTags

/**
 * Наследуемся от ComposeScreen (библиотека kakao) и передаем semanticsProvider
 */
class KaspressoGreetingScreen(semanticsProvider: SemanticsNodeInteractionsProvider) :
    ComposeScreen<KaspressoGreetingScreen>(
        semanticsProvider,
        // Опционально можно указать builder для родительской composable-функции экрана
        viewBuilderAction = { hasTestTag(GreetingTestTags.SCREEN) }
    ) {

    /**
     * Все элементы экрана являются узлами (node) ui-дерева, и поддерживают иерархию
     * Объявим все элементы как child по отношению к родительской composable-функции
     */
    val okButton: KNode = child {
        hasText("OK")
    }

    val greetingText: KNode = child {
        hasTestTag(GreetingTestTags.GREETING_TEXT_TAG)
    }

    val textInput: KNode = child {
        hasTestTag(GreetingTestTags.NAME_TEXT_INPUT)
    }

    /**
     * Лейбл поля ввода является дочерней функцией для родительской composable поля ввода,
     * такое отношение можно указать как textInput.child {}
     */
    val textInputLabel: KNode = textInput.child {
        /**
         * Без этой строчки элемент лейбла поля ввода не будет найден, т.к. его semantics будет
         * объединена в родительский textInput компонент
         */
        useUnmergedTree = true
        hasTestTag(GreetingTestTags.NAME_TEXT_INPUT_LABEL)
    }
}