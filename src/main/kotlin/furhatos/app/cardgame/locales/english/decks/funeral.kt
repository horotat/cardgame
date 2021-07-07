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

        class AskWhatIs() : Intent() {
            var card: CardEntity? = null
            override fun getExamples(lang: Language): List<String> {
                return listOf("what is @card?", "define @card", "what does @card mean?", "what is the definition of @card")
            }
        }

        class AskExample(): Intent() {
            var card: CardEntity? = null
            override fun getExamples(lang: Language): List<String> {
                return listOf("can you use @card in a sentence?", "can you use @card in context?", "How do you use @card in a sentence?", "give an example of @card", "how to use @card in a sentence?", "give me an example of @card" )
            }
        }

        fun RobotTurn.userFollowUpDefinition() {
            user(intent("tell me another definition", "define more", "give me another definition")) {
                val funeral = focusStack[0]
                robot {
                    include(funeral.definition.randomAvoidRepeat()!!)
                }
                userFollowUpDefinition()
            }
        }

        user<AskWhatIs> {
            val funeral = it.card!!.card!!
            robot {
                +{ focusStack.prime(funeral) }
                include(funeral.definition.randomAvoidRepeat()!!)
            }
            userFollowUpDefinition()
        }

        fun RobotTurn.userFollowUpExample() {
            user(intent("another example", "can you tell me another sentence?", "give me another instance", "tell me another example", "I want more examples", "try another example", "could you tell me another one?")) {
                val funeral = focusStack[0]
                robot {
                    include(funeral.example.randomAvoidRepeat()!!)
                }
                userFollowUpExample()
            }
        }

        user<AskExample> {
            val funeral = it.card!!.card!!
            robot {
                +{ focusStack.prime(funeral) }
                include(funeral.example.randomAvoidRepeat()!!)
            }
            userFollowUpExample()
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