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

val writingDeckEnglish = deck {  // todo: change the name pf the Deck to ***DeckEnglish, then add it to the listof decks

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

    // read the comments for knowing what these are and what do they do.

    card {
        id = "lion"  // a simple name here without space, the best is to use the actual word with small letters
        truth = 0 // put the importance degree here
        image = "writing.jpg" // It's the same image for all in each deck
        name = "Lion" // Displayed on card, first letter capital
        def = "the lion" // Definitive form ("bestämd form"), [ if it is a verb or an adjective, if they have a noun form, try to use that, for example beautiful --> the beauty
        indef = "a lion" // If indefinite form does not apply you can use the word itself again.
        gramclass = ""  // the grammatical class of the word, noun, verb, adjective, adverb
        // All different ways you can say this card in
        input += "lion"  // again the word
        input += "the lion" // if it is a noun change this line but if not just delete the line.

        //of the lines below you can add as much of them as you want. So if you have 2 definitions, just copy paste the line below. Works the same for all the others.
        definition += { +"" }

        argument_low += { +"" }

        argument_high += { +"" }

        example += { +"" }
    }

    // template: you can copy and paste the one below for all of them. It's a clean version without the comments.

    card {
        id = "lion"
        truth = 0
        image = "writing.jpg"
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
        id = "hackneyed"
        truth = 0
        image = "writing.jpg"
        name = "Hackneyed"
        def = "being hackneyed"
        indef = "a hackneyed writing"  // this is an example, I am not sure how it will turn out. When we finish the first deck we will decide how to tread adjectives and adverbs and verbs

        gramclass = "adjective"

        input += "hackneyed"
        input += "being hackneyed"

        definition += { +"Hackneyed means something being used too often and therefore it is boring." }

        argument_low += { +"This is a very negative thing to say about someone’s writing, since it implies that it’s boring. " }
        argument_low += { +"I think it would be very rude to say that someone’s writing overuses repetitive tropes. " }

        argument_high += { +"Lots of authors try to write things similar to what others have written, so this seems like a good compliment. " }
        argument_high += { +" I think it’s a positive quality for writing to reuse ideas that have been used many times before. " }

        example += { +"The artist should be careful to avoid hackneyed subjects." }
        example += { +"The plot of the film is just a hackneyed boy-meets-girl scenario." }
    }


    card {
        id = "trite"
        truth = 1
        image = "writing.jpg"
        name = "Trite"
        def = "being trite"
        indef = "a trite writing"
        gramclass = "adjective"
        input += "trite"
        input += "being trite"

        definition += { +"Being trite means being boring because it has been expressed so many times before, it is unoriginal" }

        argument_low += { +"Calling someone’s writing unoriginal seems pretty insulting." }
        argument_low += { +"A word that means boring and not original does not seem like much of a compliment." }

        argument_high += { +"It seems like a good thing to be told that your work is nothing new." }
        argument_high += { +"I think that an author might really appreciate being told that their writing has ideas that have been expressed a lot of times." }

        example += { +"this point may now seem obvious and trite" }
        example += { +"She seemed bored and asked trite questions" }
    }


    card {
        id = "derivative"
        truth = 2
        image = "writing.jpg"
        name = "Derivative"
        def = "being derivative"
        indef = "a derivative writing"
        gramclass = "adjective"
        input += "derivative"
        input += "beign derivative"

        definition += { +"Something derivative is imitative of the work of another artist, writer, etc., and usually disapproved of for that reason." }

        argument_low += { +"Accusing an author of copying others’ work doesn’t seem like much of a compliment." }
        argument_low += { +"Do you really think an author would like to be told that they’re just imitating the work of others?" }

        argument_high += { +"They do say that imitation is the sincerest form of flattery, so it seems like being derivative would be a good thing." }
        argument_high += { +"I think an author would like to hear that their work seems very similar to that of others." }

        example += { +"His work was so derivative that it lacked any originality." }
        example += { +"She didn’t contribute anything new; her work was completely derivative." }
    }


    card {
        id = "prosaic"
        truth = 3
        image = "writing.jpg"
        name = "Prosaic"
        def = "being prosaic"
        indef = "a prosaic writing"
        gramclass = "adjective"
        input += "prosaic"
        input += "being prosaic"

        definition += { +"Prosaic means ordinary and not showing any imagination." }

        argument_low += { +"Do you really think anyone would appreciate being told their work is “ordinary”?" }
        argument_low += { +"Saying that an author’s work doesn’t show imagination seems fairly rude." }

        argument_high += { +" I think an author would like to hear that their work is ordinary." }
        argument_high += { +"A lack of imagination seems like something most authors would strive for." }

        example += { +"the masses were too preoccupied by prosaic day-to-day concerns" }
        example += { +"prosaic language can't convey the experience" }
    }


    card {
        id = "perspicuous"
        truth = 4
        image = "writing.jpg"
        name = "Perspicuous"
        def = "being perspicuous"
        indef = "a perspicuous writing"
        gramclass = "adjective"
        input += "perspicuous"
        input += "being perspicuous"

        definition += { +"Something perspicuous is transparently clear and easily understandable." }

        argument_low += { +"This isn’t a negative thing to say but it’s not the most positive you could come up with." }
        argument_low += { +"This seems like a basic requirement of a piece of writing rather than a compliment." }

        argument_high += { +"Writing in a way that’s clear and easy to understand seems like something a writer would strive for." }
        argument_high += { +"If no one can understand what you’ve written, it doesn’t have much value, does it?" }

        example += { +"These essential points should be plainly expressed, in a style familiar and perspicuous to all." }
        example += { +"it provides simpler and more perspicuous explanations than its rivals" }
    }


    card {
        id = "adroit"
        truth = 5
        image = "writing.jpg"
        name = "Adroit"
        def = "being adroit"
        indef = "an adroit writing"
        gramclass = "adjective"
        input += "adroit"
        input += "being adroit"

        definition += { +"Being adroit means being clever and showing skill." }

        argument_low += { +"Is this really the most flattering thing you could say about a person’s writing?" }
        argument_low += { +"Being skillful is nice, but it might not be the most flattering compliment you could give." }

        argument_high += { +"Most authors would appreciate being told that their writing is clever and skillful." }
        argument_high += { +"It seems like being clever and skillful is something most writers would strive for." }

        example += { +"he was adroit at tax avoidance" }
        example += { +"She became adroit at dealing with difficult questions." }
    }


    card {
        id = "peerless"
        truth = 6
        image = "writing.jpg"
        name = "Peerless"
        def = "being peerless"
        indef = "a peerless writing"
        gramclass = "adjective"
        input += "peerless"
        input += "being peerless"

        definition += { +"Peerless means better than all others of its kind." }

        argument_low += { +"It might seem like you’re being insincere if you say something excessively positive." }
        argument_low += { +"This seems like a fairly generic complement." }
        argument_low += { +"I think this would make the author think you’re sucking up to them." }

        argument_high += { +"“Peerless” means better than anything of its kind, so it’s basically the most positive thing you could say." }
        argument_high += { +"Does it get any better than being best?" }
        argument_high += { +"This is an absolutely glowing complement." }

        example += { +"a peerless performance" }
        example += { +"a peerless cartoonist" }
    }

}