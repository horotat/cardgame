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

val colleagueDeckEnglish = deck {  // todo: change the name pf the Deck to ***DeckEnglish, then add it to the listof decks

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
        id = "truculent"
        truth = 0
        image = "question-mark.png"
        name = "Truculent"
        def = "being truculent"
        indef = "a truculent colleague"

        gramclass = "adjective"

        input += "truculent"
        input += "being truculent"

        definition += { +"Being truculent means tending to argue or become angry; slightly aggressive" }

        argument_low += { +"It would be very hard to work on a task under pressure with someone who easily flies off the handle. " }
        argument_low += { +"Having to work under pressure with someone who is angry and argumentative seems like a recipe for disaster." }

        argument_high += { +"Someone who gets angry easily might have the necessary passion to accomplish a difficult task under pressure." }
        argument_high += { +"If someone is willing to argue with you, then they won’t keep quiet if they notice you making a dangerous mistake." }

        example += { +"He was truculent and difficult to deal with." }
        example += { +"Why are you in such a truculent mood that you want to argue with everyone today?" }
    }

    card {
        id = "callow"
        truth = 1
        image = "question-mark.png"
        name = "Callow"
        def = "being callow"
        indef = "a callow colleague"
        gramclass = "adjective"

        input += "callow"
        input += "being callow"

        definition += { +"Somebody who is callow is young and without experience." }

        argument_low += { +"The last thing you want when doing a very complex task is someone who has no idea what they’re doing." }
        argument_low += { +" Someone who is young and lacks experience is very likely to make dangerous mistakes. " }

        argument_high += { +"An inexperienced person might be open to ways of doing things that someone more set in their ways wouldn’t think of." }
        argument_high += { +"If someone is very young and naïve, they may be flexible in their approach to problem solving. " }

        example += { +"Mark was just a callow youth of 16 when he arrived in Paris" }
        example += { +"The callow basketball player missed an incredibly easy shot." }
    }

    card {
        id = "indolent"
        truth = 2
        image = "question-mark.png"
        name = "Indolent"
        def = "being indolent"
        indef = "an indolent colleague"
        gramclass = "adjective"

        input += "indolent"
        input += "being indolent"

        definition += { +"Indolent means not wanting to work." }

        argument_low += { +"If you need to do a difficult task under pressure you definitely don’t want to be stuck with someone who is unwilling to work. " }
        argument_low += { +"The last thing you want to worry about in a high-pressure scenario is trying to get other people to pull their weight. " }

        argument_high += { +"A person who is lazy would probably be willing to stay out of your way and let you find the best solution to a problem. " }

        example += { +"they were indolent and addicted to a life of pleasure" }
        example += { +"Because I enjoy being indolent, there is nothing I find more pleasant than relaxing in bed." }
    }

    card {
        id = "utilitarian"
        truth = 3
        image = "question-mark.png"
        name = "Utilitarian"
        def = "being utilitarian"
        indef = "an utilitarian colleague"
        gramclass = "adjective"

        input += "utilitarian"
        input += "being utilitarian"

        definition += { +"Something that is utilitarian is useful and practical rather than attractive" }

        argument_low += { +"Someone who is utilitarian might be convinced that the ends justify the means." }
        argument_low += { +"A utilitarian person would probably approach problems in a way that isn’t particularly elegant." }

        argument_high += { +" A utilitarian person would be able to focus on coming up with a practical solution to a problem" }
        argument_high += { +"It would be useful to have help from someone who focuses on practical details instead of worrying about appearances." }

        example += { +"The farm is a rather utilitarian building set just beyond another group of trees." }
        example += { +"This site has shed some of its more fancy handles since we last reviewed it, becoming more utilitarian." }
    }

    card {
        id = "shrewd"
        truth = 4
        image = "question-mark.png"
        name = "Shrewd"
        def = "being shrewd"
        indef = "a shrewd colleague"
        gramclass = "adjective"

        input += "shrewd"
        input += "being shrewd"

        definition += { +"Being shrewd means showing good judgement and likely to be right" }

        argument_low += { +"Someone who is very shrewd might be convinced they’re right even if they aren’t" }
        argument_low += { +"If you have to work on a difficult task with a shrewd person, they might rely on their good judgment rather than being careful and methodical. " }

        argument_high += { +"Having good judgment seems like a very important requirement for working under pressure." }
        argument_high += { +"A shrewd person would be good at solving problems in a tense situation." }

        example += { +"He was shrewd enough not to take the job when there was the possibility of getting a better one a few months later." }
        example += { +"It was a shrewd move to buy your house just before property prices started to rise." }
    }

    card {
        id = "punctilious"
        truth = 5
        image = "question-mark.png"
        name = "Punctilious"
        def = "being punctilious"
        indef = "a punctilious colleague"
        gramclass = "adjective"

        input += "punctilious"
        input += "being punctilious"

        definition += { +"Someone punctilious is very careful to behave correctly or to perform your duties exactly as you should" }

        argument_low += { +"If someone is too concerned with behaving correctly, maybe they would have trouble coming up with creative solutions." }
        argument_low += { +"A person who is very careful and methodical might not be able to work quickly enough under pressure." }

        argument_high += { +"Excellent attention to detail is very important for completing a difficult task under a lot of stress. " }
        argument_high += { +"If you need to work on a very challenging task with someone, you would want them to be very careful to do everything correctly. " }

        example += { +"he was punctilious in providing every amenity for his guests" }
        example += { +"He was always punctilious in his manners." }
    }

}