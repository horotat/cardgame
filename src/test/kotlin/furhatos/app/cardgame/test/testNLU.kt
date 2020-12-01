package furhatos.app.cardgame.test

import furhatos.app.cardgame.flow.discussion.Discussing
import furhatos.app.cardgame.flow.intro.AskName
import furhatos.app.cardgame.flow.intro.SelectDeck
import furhatos.app.cardgame.locales.english.decks.desertSurvivalDeckEnglish
import furhatos.app.cardgame.game.Game
import furhatos.app.cardgame.locales.english.decks.sugarInFoodEnglish
import furhatos.app.cardgame.locales.english.englishLocale
import furhatos.app.cardgame.nlu.HighestCardIntent
import furhatos.app.cardgame.nlu.LowestCardIntent
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.LogisticMultiIntentClassifier
import furhatos.nlu.SimpleIntent
import furhatos.nlu.common.Maybe
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.util.Language


fun main() {
    LogisticMultiIntentClassifier.setAsDefault()
    //LogisticMultiIntentClassifier.cachingEnabled = false
    Game.locale = englishLocale
    Game.selectDeck(sugarInFoodEnglish)
    println(Game.cardSet.cards)
    val classifier = Discussing.getIntentClassifier(Language.ENGLISH_US)
    classifier.classify( "what about the candy bar  ")
    
}