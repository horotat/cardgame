package furhatos.app.cardgame.locales.english.decks

import furhatos.app.cardgame.flow.Nomatch
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

val devDeckEnglish = deck {

    imgFolder = "dev"
    name = "Dev"
    unitLabel = "°C" // Might not be needed, for example if you have YEARS as the unit. Because you don't say "1980 year" or similar, "1980" is enough  // ask: does this work as degree celcius?

    input {
        singular += "animal"

        min_def += options("the coldest climate living") / options("animal", "one", "")
        max_def += options("the") / options("warmest climate living", "hottest climate living") / options("animal", "one", "")

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
        min_def = {random {
            +"the animal living in the coldest climate"
            +"the most low temperature tolerant one"
        }}
        max_def = {random {
            +"the least cold tolerant one"
            +"the animal living in the warmest climate"
        }}
        is_min = {random {
            +"is the animal living in the coldest environment compared to others"
            +"is the coldest living one"
        }}
        is_max = {random {
            +"is the animal living in the warmest climate compared to the others"
            +"is warmest living climate one"
        }}
        is_less_than = {
            +"tolerates colder weather compared to"
        }
        is_more_than = {
            +"lives in warmer place than"
        }
    }

    questions {

        class AskHaveSeenAnimal(): Intent() {
            var card: CardEntity? = null
            override fun getExamples(lang: Language): List<String> {
                return listOf("have you ever seen a @card")
            }
        }

        user<AskHaveSeenAnimal> {
            val seenAnimal = it.card!!.card!!
            robot {
                +attend(seenAnimal.location)
                +{focusStack.prime(seenAnimal)}
                +"No, I don't think I have ever seen ${seenAnimal.indef}."
                +attend(users.current)
                +"Have you?"
            }
            user(Yes()) {
                robot {+"Wow, sounds interesting. Was ${seenAnimal.def} chilling on the ice?"}
                user(Yes(), No(), DontKnow(), Nomatch, proceed = TakeInitiative()) {
                    robot {+"Okay, I see"}
                }
            }
            user(No(), DontKnow()) {
                robot {+"Well, then it might be hard for us to know which temperature does the ${seenAnimal.indef} live in."}
            }
            user(intent("I have seen them on TV", "I think I have seen them on TV", "I have only seen them on film", "I have seen pictures of them")) {
                robot {+"Me too. But that's not the same thing as seeing them in real life, I guess."}
            }
        }

    }

    // Cards. There can be unlimited cards here, but only 5 are selected for each game. A good amount to get variation is minimum 8. Please use the Lion card as a template. It is a good practise to have some cards that are quite extreme, i.e really slow or really fast.

    card {
        id = "lion"
        truth = 25 // Average lion natural habitat temperature
        image = "question-mark.png" // The image of the card
        name = "Lion" // Displayed on card
        def = "the lion" // Definitive form ("bestämd form")
        indef = "a lion"

        // All different ways you can say this card in
        input += "lion"
        input += "the lion"
        argument_low += { +"On Mount Elgon, the lion has been recorded up to an elevation of 3600 meters and close to the snow line on Mount Kenya." }
        argument_low += { +"It seems that lions enjoy chilling under the shade and avoid direct sunlight in their rest." }
        argument_high += { +"African lions live in scattered populations across Sub-Saharan Africa. It doesn't seem to be a very cold habitat." }
        argument_high += { +"The lion prefers grassy plains and savannahs, scrub bordering rivers and open woodlands with bushes." }
        argument_high += { +"It is absent from rainforests and rarely enters closed forests" }
    }

    card {
        id = "hamster"
        truth = 19
        image = "question-mark.png"
        name = "Hamster"
        def = "the hamster"
        indef = "a hamster"
        input += "hamster"
        input += "the hamster"
        argument_low += { +"Hamsters are nocturnal animals and are active during the night" }
        argument_low += { +"Hamsters dig their nests under ground. So they don't see so much sun" }
        argument_high += { +"hamsters get into hibernation when the weather is a little bit cold" }
        argument_high += { +"Hamsters live near the dessert lines" }
    }

    card {
        id = "camel"
        truth = 4
        image = "question-mark.png"
        name = "Camel"
        def = "the camel"
        indef = "a camel"
        input += "camel"
        input += "the camel"
        input += "camel"
        argument_low += { +"Soem camels are found in places with -20 celsius" }
        argument_high += { +"They live in dry desert climates of the Sahara Desert of Northern Africa, the Middle East, Southwestern Asia and in Indian desert areas." }
        argument_high += { +"The camels can live in places with low amount of water" }
    }

    card {
        id = "dolphin"
        truth = 21
        image = "question-mark.png"
        name = "Dolphin"
        def = "the dolphin"
        indef = "a dolphin"
        input += "dolphin"
        input += "the dolphin"
        input += "dolphin"
        argument_low += { +"they live in water and water in general is colder than the temperature on the ground"}
        argument_high += { +"dolphins prefer to stay close to the surface of water, which means closer to the sun"}
        argument_high += { +"dolphins don't live near northern or southern pole waters"}

    }

    card {
        id = "polar_bear"
        truth = -10
        image = "question-mark.png"
        name = "Polar bear"
        def = "the Polar bear"
        indef = "a Polar bear"

        input += "polar bear"
        input += "the polar bear"

        argument_low += { +"Polar bears can weigh 800 kilos, most of which is fat" }
        argument_low += { +"polar bears have black skin over a thick layer of fat that can measure up to 11.4 centimeters" }
        argument_low += { +"Polar bears are fat, especially at the start of winter to preseve the body heat" }
        argument_high += { +"They sleep during the whole winter, so they might not be as tolerant to the cold" }
    }

}