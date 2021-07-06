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

val funeralDeckEnglish = deck {

    imgFolder = "writing"
    name = "Writing Review"
    unitLabel = " °importance"

    input {
        singular += "writing"

        min_def += options("the coldest climate living") / options("animal", "one", "")
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
                +"You are going to a funeral. How should you act? Rank from most appropriate to most inappropriate."
            }
        }
        singular = {
            +"animal"
        }
        min_def = {
            random {
                +"the animal living in the coldest climate"
                +"the most low temperature tolerant one"
            }
        }
        max_def = {
            random {
                +"the least cold tolerant one"
                +"the animal living in the warmest climate"
            }
        }
        is_min = {
            random {
                +"is the animal living in the coldest environment compared to others"
                +"is the coldest living one"
            }
        }
        is_max = {
            random {
                +"is the animal living in the warmest climate compared to the others"
                +"is warmest living climate one"
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
        id = "exultant"
        truth = 0
        image = "question-mark.png"
        name = "Exultant"
        def = "being exultant"
        indef = "an exultant person"

        gramclass = "adjective"

        input += "exultant"
        input += "being exultant"

        definition += { +"Being exultant means feeling or showing that you are very proud or happy, especially because of something exciting that has happened" }

        argument_low += { +"" }

        argument_high += { +"" }

        example += { +"he waved to the exultant crowds" }
        example += { +"They were exultant over their victory." }
    }

    card {
        id = "ebullient"
        truth = 1
        image = "question-mark.png"
        name = "Ebullient"
        def = "being ebullient"
        indef = "an ebullient person"
        gramclass = "adjective"

        input += "ebullient"
        input += "being ebullient"

        definition += { +"Someone ebullient is full of confidence, energy and good humour" }

        argument_low += { +"" }

        argument_high += { +"" }

        example += { +"She sounded as ebullient and happy as ever." }
        example += { +"Charlie’s ebullient attitude made him a favorite in the office." }
    }

    card {
        id = "cogent"
        truth = 2
        image = "question-mark.png"
        name = "Cogent"
        def = "being cogent"
        indef = "a cogent person"
        gramclass = "adjective"

        input += "cogent"
        input += "being cogent"

        definition += { +"Cogent means persuasive and well expressed." }

        argument_low += { +"" }

        argument_high += { +"" }

        example += { +"Your article provides cogent reading." }
        example += { +"But it has no cogent, lucid scheme to engage us" }
    }

    card {
        id = "amicable"
        truth = 3
        image = "question-mark.png"
        name = "Amicable"
        def = "being amicable"
        indef = "an amicable person"
        gramclass = "adjective"

        input += "amicable"
        input += "being amicable"

        definition += { +"Something that is amicable is done or achieved in a polite or friendly way and without arguing" }

        argument_low += { +"" }

        argument_high += { +"" }

        example += { +"In spite of their disagreement they parted on amicable terms" }
        example += { +" I can tell you that the meeting was professional, efficient and amicable." }
    }

    card {
        id = "lugubrious"
        truth = 4
        image = "question-mark.png"
        name = "Lugubrious"
        def = "being lugubrious"
        indef = "a lugubrious person"
        gramclass = "adjective"

        input += "lugubrious"
        input += "being lugubrious"

        definition += { +"Being lugubrious means being sad and serious." }

        argument_low += { +"" }

        argument_high += { +"" }

        example += { +"his face looked even more lugubrious than usual" }
        example += { +"Just because I’m a bit down today doesn’t mean I’m in a lugubrious mood!" }
    }

    card {
        id = "disconsolate"
        truth = 5
        image = "question-mark.png"
        name = "Disconsolate"
        def = "being disconsolate"
        indef = "a disconsolate person"
        gramclass = "adjective"

        input += "disconsolate"
        input += "being disconsolate"

        definition += { +"Somebody who is disconsolate is very unhappy and disappointed." }

        argument_low += { +"" }

        argument_high += { +"" }

        example += { +"she left Fritz looking disconsolate" }
        example += { +"The disconsolate players left for home without a trophy." }
    }

}