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

val cureDeckEnglish = deck {  // todo: change the name pf the Deck to ***DeckEnglish, then add it to the listof decks

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
                +"Well. In this game we are going to sort these animals by"
                +"So. For this game we will sort these animals by"
                +"Okey, In this game we shall arrange these animals by"
            }
            random {
                +"the temperature of their habitat"
                +"their favorite habitat temperature"
                +"their temperature tolerance"
                +"which temperature they live in"
                +"which climate they live in"
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
        id = "noxious"
        truth = 0
        image = "question-mark.png"
        name = "Noxious"
        def = "being noxious"
        indef = "a noxious substance"

        gramclass = "adjective"

        input += "noxious"
        input += "being noxious"

        definition += { +"Noxious means poisonous or harmful." }

        argument_low += { +"" }

        argument_high += { +"" }

        example += { +"You can avoid getting sick so often by frequently washing your hands to eliminate noxious bacteria and viruses." }
        example += { +"They died from inhaling noxious fumes." }
    }

    card {
        id = "noisome"
        truth = 1
        image = "question-mark.png"
        name = "Noisome"
        def = "being noisome"
        indef = "a noisome substance"
        gramclass = "adjective"

        input += "noisome"
        input += "being noisome"

        definition += { +"Something noisome is very disagreeable or unpleasant." }

        argument_low += { +"" }

        argument_high += { +"" }

        example += { +"As soon as I opened the refrigerator, the noisome scent of bad meat hit my nose. " }
        example += { +"The air is infected with noisome gases." }
    }

    card {
        id = "luscious"
        truth = 2
        image = "question-mark.png"
        name = "Luscious"
        def = "being luscious"
        indef = "a luscious substance"
        gramclass = "adjective"

        input += "luscious"
        input += "being luscious"

        definition += { +"" }

        example += { +"" }

        argument_low += { +"" }

        argument_high += { +"" }

        example += { +"" }
    }

    card {
        id = "inoculation"
        truth = 3
        image = "question-mark.png"
        name = "Inoculation"
        def = "the inoculation"
        indef = "an inoculation "
        gramclass = "noun"

        input += "inoculation"
        input += "the inoculation"

        definition += { +"inoculation protects a person or an animal from catching a particular disease by injecting them with a mild form of the disease" }

        argument_low += { +"" }

        argument_high += { +"" }

        example += { +"The disease can now be prevented by inoculation." }
        example += { +"After inoculation with the 2nd vaccine the animals' temperature will go up." }
    }

    card {
        id = "palliate"
        truth = 4
        image = "question-mark.png"
        name = "Palliate"
        def = "to palliate"
        indef = "palliate"
        gramclass = "verb"

        input += "palliate"
        input += "to palliate"

        definition += { +"Palliating makes a disease or an illness less painful or unpleasant without curing it." }

        argument_low += { +"" }

        argument_high += { +"" }

        example += { +"The drugs palliate pain but have no effect on inflammation." }
        example += { +" They tried to palliate the hardship of their lives." }
    }

    card {
        id = "panacea"
        truth = 0
        image = "question-mark.png"
        name = "Panacea"
        def = "the panacea"
        indef = "a panacea"
        gramclass = "noun"

        input += "panacea"
        input += "the panacea"

        definition += { +"A panacea is something that will solve all the problems of a particular situation." }

        argument_low += { +"" }

        argument_high += { +"" }

        example += { +"Many people believe chicken soup is a panacea for any respiratory illness. " }
        example += { +"Unfortunately there is no panacea that will make cancer instantly vanish from your body." }
    }

}