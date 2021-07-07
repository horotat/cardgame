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

    imgFolder = "colleague"
    name = "Best Colleague"
    unitLabel = " °importance"

    input {
        singular += "colleagues"

        min_def += options("the best quality a colleague can have") / options("animal", "one", "")
        max_def += options("the") / options("worst behaviour", "most undesired quality") / options("in a colleague")

        is_min_def += options("may be", "might be", "could be", "is") / min_def
        is_max_def += options("may be", "might be", "could be", "is") / max_def

        is_less_than += "is a worse quality than"
        is_less_than += "is not as good as"
        is_less_than += "is a lower personality trait"
        is_less_than += "is a worse than"
        is_less_than += "is less likable in a person than"

        is_more_than += "is a better quality in a person than"
        is_more_than += "is better than"
        is_more_than += "is a more likable personality than"
    }

    output {
        purpose = {
            random {
                +"You are starting a new project with new collegues tomorrow. Rank from most to least the qualities you hope that your collegues have."
            }
        }
        singular = {
            +"personality trait"
        }
        min_def = {
            random {
                +"the worst personality trait"
                +"the loswest charachter quality in a colleague"
            }
        }
        max_def = {
            random {
                +"the most likable charachter quality of a colleague"
                +"the best personality trait of a colleague"
            }
        }
        is_min = {
            random {
                +"is the worst personality trait a colleague can have among the others"
                +"is the least likable character trait of a colleague"
            }
        }
        is_max = {
            random {
                +"is the best value in a colleague"
                +"is the best way a colleague can be among all others"
            }
        }
        is_less_than = {
            +"is not as important as"
            +"is less determining than"
        }
        is_more_than = {
            +"makes a better person than"
            +"is a more more important personality trait"
            +"makes a person be a better colleague compared to"
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
                val trait = focusStack[0]
                robot {
                    include(trait.definition.randomAvoidRepeat()!!)
                }
                userFollowUpDefinition()
            }
        }

        user<AskWhatIs> {
            val trait = it.card!!.card!!
            robot {
                +{ focusStack.prime(trait) }
                include(trait.definition.randomAvoidRepeat()!!)
            }
            userFollowUpDefinition()
        }

        fun RobotTurn.userFollowUpExample() {
            user(intent("another example", "can you tell me another sentence?", "give me another instance", "tell me another example", "I want more examples", "try another example", "could you tell me another one?")) {
                val trait = focusStack[0]
                robot {
                    include(trait.example.randomAvoidRepeat()!!)
                }
                userFollowUpExample()
            }
        }

        user<AskExample> {
            val trait = it.card!!.card!!
            robot {
                +{ focusStack.prime(trait) }
                include(trait.example.randomAvoidRepeat()!!)
            }
            userFollowUpExample()
        }

    }

    // Start the deck here:
    card {
        id = "truculent"
        truth = 0
        image = "colleague.jpg"
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
        image = "colleague.jpg"
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
        image = "colleague.jpg"
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
        image = "colleague.jpg"
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
        image = "colleague.jpg"
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
        image = "colleague.jpg"
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