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

    imgFolder = "funeral"
    name = "Sorry for your loss!"
    unitLabel = " ┬░appropriateness"

    input {
        singular += "behaviour"

        min_def += options("the most inappropriate") / options("behaviour", "one", "")
        max_def += options("the") / options("most appropriate behaviour", "best way of behaving") / options(
            "in a funeral",
            ""
        )

        is_min_def += options("may be", "might be", "could be", "is") / min_def
        is_max_def += options("may be", "might be", "could be", "is") / max_def

        is_less_than += "is less appropriate than"
        is_less_than += "is not as sympathetic as"
        is_less_than += "is not as warm as"
        is_less_than += "is not so appropriate compared to"

        is_more_than += "sounds more appropriate than"
        is_more_than += "fits better to the situation compared to"
        is_more_than += "is a better behaviour than"
    }

    output {
        purpose = {
            random {
                +"You are going to a funeral. How should you act? Rank from the most inappropriate to the most appropriate."
            }
        }
        singular = {
            +"manner"
        }
        min_def = {
            random {
                +"the most unsympathetic one"
                +"the least appropriate attitude for a funeral"
            }
        }
        max_def = {
            random {
                +"the most appropriate one"
                +"the best way to show your respect"
                +"the most empathetic"
            }
        }
        is_min = {
            random {
                +"is the least appropriate way of behaving in a funeral"
                +"is the least respectful among all."
            }
        }
        is_max = {
            random {
                +"is the best way to show respect in a funeral"
                +"is the most sympathetic"
                +"is the most empathetic"
            }
        }
        is_less_than = {
            +"is less appropriate than"
        }
        is_more_than = {
            +"is more appropriate than"
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
                return listOf("can you use @card in a sentence?", "can you use @card in context?", "How do you use @card in a sentence?", "give an example of @card", "how to use @card in a sentence?", "give me an example of @card", "could you give me an example of the @card", "can you give me an example of the word @card" )
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

    // Start the deck here:
    card {
        id = "exultant"
        truth = 0
        image = "funeral.jpg"
        name = "Exultant"
        def = "being exultant"
        indef = "an exultant person"

        gramclass = "adjective"

        input += "exultant"
        input += "being exultant"

        definition += { +"Being exultant means feeling or showing that you are very proud or happy, especially because of something exciting that has happened" }


        argument_low += { +"The last thing you should do at a funeral is to act exultant: seeming proud and happy will make it look like youÔÇÖre glad your friend is dead." }
        argument_low += { +"Appearing to be happy about your friendÔÇÖs death by acting exultant at the funeral is inappropriate and will probably make you seem like the prime suspect." }

        argument_high += { +"Only a fool would murder someone and then act exultent at their funeral, so seeming proud and happy would be appropriate." }
        argument_high += { +"The detective is probably expecting the murderer to pretend to be sad, so acting exultent and seeming as if something exciting just happened will throw them off." }

        example += { +"he waved to the exultant crowds" }
        example += { +"They were exultant over their victory." }
    }

    card {
        id = "excited"
        truth = 1
        image = "funeral.jpg"
        name = "Excited"
        def = "being excited"
        indef = "an excited person"
        gramclass = "adjective"

        input += "excited"
        input += "being excited"

        definition += { +"Being excited means feeling or showing happiness and enthusiasm" }

        argument_low += { +"Being excited at a funeral could be offensive towards the people grieving. " }

        argument_high += { +"If you are excited while everyone else is sad, you are sure to stand out." }

        example += { +"I'm really excited at the prospect of working abroad." }
        example += { +"The children were excited about opening their presents." }
    }


    card {
        id = "ebullient"
        truth = 2
        image = "funeral.jpg"
        name = "Ebullient"
        def = "being ebullient"
        indef = "an ebullient person"
        gramclass = "adjective"

        input += "ebullient"
        input += "being ebullient"

        definition += { +"Someone ebullient is full of confidence, energy and good humour" }

        argument_low += { +"Seeming ebullient and cheerful when your friend has just died may seem insensitive and might make the detective suspicious of you." }
        argument_low += { +"Someone who has just lost their friend would seem like a psychopath if they acted happy and ebullient." }

        argument_high += { +"Many people would prefer that their friends and loved ones are ebullient and in good spirits, even if theyÔÇÖve just passed away" }
        argument_high += { +" If you project confidence and good spirits and come across as ebullient, it will seem unlikely that you could have done something terrible like murder a friend." }

        example += { +"She sounded as ebullient and happy as ever." }
        example += { +"CharlieÔÇÖs ebullient attitude made him a favorite in the office." }
    }




/*
    card {
        id = "glad"
        truth = 3
        image = "funeral.jpg"
        name = "Glad"
        def = "being glad"
        indef = "a glad person"
        gramclass = "adjective"

        input += "glad"
        input += "being glad"

        definition += { +"Someone who is glad is pleased or happy" }

        argument_low += { +"It might seem inappropriate to be glad at your friends funeral." }

        argument_high += { +"If everyone else is sad, they might need someone to cheer them up." }

        example += { +"She was glad when the meeting was over." }
        example += { +"He was glad he'd come." }
    }
*/

/*
    card {
        id = "cogent"
        truth = 4
        image = "funeral.jpg"
        name = "Cogent"
        def = "being cogent"
        indef = "a cogent person"
        gramclass = "adjective"

        input += "cogent"
        input += "being cogent"

        definition += { +"Cogent means persuasive and well expressed." }

        argument_low += { +"If the biggest impression you give is that youÔÇÖre articulate and express yourself logically, you might seem like a coldhearted serial killer, so acting cogent is probably not a good idea." }
        argument_low += { +"You should probably act sad at a friendÔÇÖs funeral, rather than focusing on expressing yourself clearly and persuasively." }

        argument_high += { +"A person who is cogent would come across as convincing and logical, which would help you prove that you arenÔÇÖt guilty of killing your friend." }
        argument_high += { +"Being cogent does not seem like a quality a crazed killer would have, since murderers are usually not persuasive and logical." }

        example += { +"Your article provides cogent reading." }
        example += { +"But it has no cogent, lucid scheme to engage us" }
    }

 */


/*
    card {
        id = "amicable"
        truth = 5
        image = "funeral.jpg"
        name = "Amicable"
        def = "being amicable"
        indef = "an amicable person"
        gramclass = "adjective"

        input += "amicable"
        input += "being amicable"

        definition += { +"Something that is amicable is done or achieved in a polite or friendly way and without arguing" }

        argument_low += { +" If you walk around the funeral acting amicable and trying to behave in a friendly manner towards everyone, you might not seem sad enough." }
        argument_low += { +"A funeral is hardly a place for socializing and being polite and amicable, so this seems like inappropriate behavior." }

        argument_high += { +"Being amicable, or friendly and without conflict, is always appropriate behaviour, so this seems like the right way to act" }
        argument_high += { +" If you come across as amicable and the detective thinks that youÔÇÖre friendly and polite, she might be less likely to see you as a suspect." }

        example += { +"In spite of their disagreement they parted on amicable terms" }
        example += { +" I can tell you that the meeting was professional, efficient and amicable." }
    }
*/


    card {
        id = "unhappy"
        truth = 6
        image = "funeral.jpg"
        name = "Unhappy"
        def = "being unhappy"
        indef = "an unhappy person"
        gramclass = "adjective"

        input += "unhappy"
        input += "being unhappy"

        definition += { +"Being unhappy means not being happy or being sad. " }

        argument_low += { +"You could also be unhappy for something minor, so it might not seem like you are sad enough. " }

        argument_high += { +"A funeral is not a happy gathering, so it could be appropriate to be unhappy." }

        example += { +"This story has an unhappy ending" }
        example += { +"I didn't realize but he was deeply unhappy at that time." }
    }

/*
    card {
        id = "lugubrious"
        truth = 7
        image = "funeral.jpg"
        name = "Lugubrious"
        def = "being lugubrious"
        indef = "a lugubrious person"
        gramclass = "adjective"

        input += "lugubrious"
        input += "being lugubrious"

        definition += { +"Being lugubrious means being sad and serious." }

        argument_low += { +"If you are lugubrious, you might seem exaggeratedly sad, which may come across as fake." }
        argument_low += { +"Although mourners at a funeral should definitely seem sad, being lugubrious and acting excessively gloomy might not be very convincing." }

        argument_high += { +"Anyone attending a friendÔÇÖs funeral would be expected to be lugubrious, since the death of a friend ought to make you sad" }
        argument_high += { +"It is a social expectation that people attending a funeral are lugubrious and act mournful." }

        example += { +"his face looked even more lugubrious than usual" }
        example += { +"Just because IÔÇÖm a bit down today doesnÔÇÖt mean IÔÇÖm in a lugubrious mood!" }
    }
    
 */





    card {
        id = "disconsolate"
        truth = 8
        image = "funeral.jpg"
        name = "Disconsolate"
        def = "being disconsolate"
        indef = "a disconsolate person"
        gramclass = "adjective"

        input += "disconsolate"
        input += "being disconsolate"

        definition += { +"Somebody who is disconsolate is very unhappy and disappointed." }

        argument_low += { +"If youÔÇÖre so upset that you canÔÇÖt be consoled, it might seem like youÔÇÖre putting on an act to fake being disconsolate." }
        argument_low += { +"Appearing disconsolate and acting extremely upset if you havenÔÇÖt been on good terms with your friend might be hard to do convincingly." }

        argument_high += { +"Anyone who cared a lot about their friend would be disconsolate at their funeral, so this seems like the most appropriate behaviour to display." }
        argument_high += { +"If you had just lost a very close friend, you would be so grief stricken that you would be impossible to console, so being disconsolate seems like appropriate behavior. " }

        example += { +"she left Fritz looking disconsolate" }
        example += { +"The disconsolate players left for home without a trophy." }
    }

}