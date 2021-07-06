package furhatos.app.cardgame.locales.english.decks

import furhatos.app.cardgame.flow.Nomatch
import furhatos.app.cardgame.flow.RobotTurn
import furhatos.app.cardgame.flow.discussion.TakeInitiative
import furhatos.app.cardgame.flow.user
import furhatos.app.cardgame.focusStack
import furhatos.app.cardgame.game.deck
import furhatos.app.cardgame.locales.english.englishLocale
import furhatos.app.cardgame.nlu.CardEntity
import furhatos.app.cardgame.options
import furhatos.gestures.Gestures
import furhatos.nlu.Intent
import furhatos.nlu.common.DontKnow
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.nlu.intent
import furhatos.util.Language

val jobsDeckEnglish = deck {

    imgFolder = "writing"
    name = "Writing Review"
    unitLabel = " °importance"

    input {
        singular += "writing"

        min_def += options("the coldest climate living") / options("animal", "one", "")  // t
        max_def += options("the") / options("warmest climate living", "hottest climate living") / options(
            "animal",
            "one",
            ""
        )

        is_min_def += options("may be", "might be", "could be", "is") / min_def  // ask: what are these (/) for?
        is_max_def += options("may be", "might be", "could be", "is") / max_def

        is_less_than += "lives in colder weather than"
        is_less_than += "is not as warm tolerant as"
        is_less_than += "is not as hot living as"
        is_less_than += "likes less temperature more than"
        is_less_than += "wants a chillier temperature compared to"
        is_less_than += "lives in sauna in the eyes of the"

        is_more_than += "lives in warmer weather than"
        is_more_than += "is not as cold tolerant as"
        is_more_than += "likes warmth more than"
        is_more_than += "doesn't like cold as much as"
        is_more_than += "would freeze where lives the"
    }

    output {
        purpose = {
            random {
                +"Suddenly you find yourself in a kingdom you do not recognize. The leader of the country is deciding whether they like you or not. Rank how you should act based on how appropriate it would be."
            }
        }
        singular = {
            +"animal"
        }
        min_def = {
            random {
                +"the least appropriate"
                +"the most inappropriate"
            }
        }
        max_def = {
            random {
                +"the most appropriate"
                +"the least inappropriate"
            }
        }
        is_min = {
            random {
                +"is the least appropriate"
                +"is the most inappropriate"
            }
        }
        is_max = {
            random {
                +"is the most appropriate"
                +"is the least inappropriate"
            }
        }
        is_less_than = {
            +"tolerates colder weather compared to"
        }
        is_more_than = {
            +"lives in warmer place than"
        }
    }

    questions {

        class AskHaveSeenAnimal() : Intent() {
            var card: CardEntity? = null
            override fun getExamples(lang: Language): List<String> {
                return listOf("have you ever seen a @card")
            }
        }

        // User asking information about what a particular card is?
        class AskWhatIs() : Intent() {
            var card: CardEntity? = null
            override fun getExamples(lang: Language): List<String> {
                return listOf("what is @card")  // ask: does it matter to put the question mark or not?
            }
        }

        user<AskHaveSeenAnimal> {
            val seenAnimal = it.card!!.card!!
            robot {
                +attend(seenAnimal.location)
                +{ focusStack.prime(seenAnimal) }
                +"No, I don't think I have ever seen ${seenAnimal.indef}."
                +attend(users.current)
                +"Have you?"
            }
            user(Yes()) {
                robot { +"Wow, sounds interesting. Was ${seenAnimal.def} chilling on the ice?" }
                user(Yes(), No(), DontKnow(), Nomatch, proceed = TakeInitiative()) {
                    robot { +"Okay, I see" }
                }
            }
            user(No(), DontKnow()) {
                robot { +"Well, then it might be hard for us to know which temperature does the ${seenAnimal.indef} live in." }
            }
            user(
                intent(
                    "I have seen them on TV",
                    "I think I have seen them on TV",
                    "I have only seen them on film",
                    "I have seen pictures of them"
                )
            ) {
                robot { +"Me too. But that's not the same thing as seeing them in real life, I guess." }
            }
        }


        fun RobotTurn.userFollowUp() {  // ask: what is RobotTurn?
            user(intent("can you elaborate", "I need more details", "give me more information")) {
                val animal = focusStack[0]
                robot {
                    include(animal.explanation.randomAvoidRepeat()!!)
                }
                userFollowUp()
            }
        }

        user<AskWhatIs> {
            val animal = it.card!!.card!!
            robot {
                +{ focusStack.prime(animal) }  // ask: what does focus stack do?
                include(animal.explanation.randomAvoidRepeat()!!)
            }
            userFollowUp()
        }

    }

    // template: you can copy and paste the one below for all of them. It's a clean version without the comments.

    card {
        id = "lion"
        truth = 0
        image = "question-mark.png"
        name = "Lion"
        def = "the lion"
        indef = "a lion"
        gramclass = ""

        input += "lion"
        input += "the lion"

        definition += { +"" }

        example += { +"" }

        argument_low += { +"" }

        argument_high += { +"" }

        example += { +"" }
    }

    // Start the deck here:
    card {
        id = "vituperate"
        truth = 0
        image = "question-mark.png"
        name = "Vituperate"
        def = "to vituperate"
        indef = "vituperate"

        gramclass = "verb"

        input += "vituperate"
        input += "to vituperate"

        definition += { +"To vituperate is to blame or insult (someone) in strong or violent language." }

        argument_low += { +"" }

        argument_high += { +"" }

        example += { +"let us not revile and vituperate those who are our brethren" }
        example += { +"It is considered almost universally acceptable to abuse and vituperate the agency." }
    }

    card {
        id = "disparage"
        truth = 1
        image = "question-mark.png"
        name = "Disparage"
        def = "to disparage"
        indef = "disparage"
        gramclass = "verb"

        input += "to disparage"
        input += "disparage"

        definition += { +"Disparage means to suggest that somebody/something is not important or valuable" }

        argument_low += { +"" }

        argument_high += { +"" }

        example += { +"I don't mean to disparage your achievements" }
        example += { +" he never missed an opportunity to disparage his competitors" }
    }

    card {
        id = "eclectic"
        truth = 2
        image = "question-mark.png"
        name = "Eclectic"
        def = "being eclectic"
        indef = "an eclectic person"
        gramclass = "adjective"

        input += "eclectic"
        input += "being eclectic"

        definition += { +"Something eclectic consists of different types, methods and styles" }

        argument_low += { +"" }

        argument_high += { +"" }

        example += { +"My friends are an eclectic group of individuals who can rarely agree on a single topic." }
        example += { +"In Gerald’s library, you will find an eclectic mix of books" }

    }

    card {
        id = "contrite"
        truth = 3
        image = "question-mark.png"
        name = "Contrite"
        def = "being contrite"
        indef = "a contrite person"
        gramclass = "adjective"

        input += "contrite"
        input += "being contrite"

        definition += { +"Being contrite means being very sorry for something bad that you have done" }

        argument_low += { +"" }

        argument_high += { +"" }

        example += { +"He looked so contrite that she believed he was sorry." }
        example += { +"Though she claimed to be contrite, the woman was truly shameless." }
    }

    card {
        id = "tout"
        truth = 4
        image = "question-mark.png"
        name = "Tout"
        def = "to tout"
        indef = "tout"
        gramclass = "verb"

        input += "tout"
        input += "to tout"

        definition += { +"Tout means to try to persuade people that somebody/something is important or valuable by praising them/it" }

        argument_low += { +"" }

        argument_high += { +"" }

        example += { +"All doctors tout the benefits of eating nutritiously and exercising regularly." }
        example += { +"The company is running advertisements touting the drug's effectiveness." }
    }

    card {
        id = "extol"
        truth = 5
        image = "question-mark.png"
        name = "Extol"
        def = "to extol"
        indef = "extol"
        gramclass = "verb"

        input += "extol"
        input += "to extol"

        definition += { +"To extol is to praise somebody or something very much" }

        argument_low += { +"" }

        argument_high += { +"" }

        example += { +"She is forever extolling the virtues of her children." }
        example += { +"She was extolled as a genius." }
    }

    card {
        id = "adulate"
        truth = 6
        image = "question-mark.png"
        name = "Adulate"
        def = "to adulate"
        indef = "adulate"
        gramclass = "verb"

        input += "adulate"
        input += "to adulate"

        definition += { +"To adulate is to admire or praise someone very much, especially when this is more than" }

        argument_low += { +"" }

        argument_high += { +"" }

        example += { +"he was adulated in the press" }
        example += { +"This country's glorious and wonderful system has been copied and adulated throughout the world." }
    }


}