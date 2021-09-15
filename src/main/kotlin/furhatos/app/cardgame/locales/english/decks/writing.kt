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

val writingDeckEnglish = deck {

    imgFolder = "writing"
    name = "Writing Review"
    unitLabel = " °importance"

    input {
        singular += "writing"

        min_def += options("the least original") / options("writing", "adjective", "")
        max_def += options("the") / options("most original", "highest quality") / options(
            "adjective",
            "style of writing",
            ""
        )

        is_min_def += options("I think ", "I guess") / options("may be", "might be", "could be", "is") / min_def
        is_max_def += options("I think ", "I guess") / options("may be", "might be", "could be", "is") / max_def

        is_less_than += "is less original than"
        is_less_than += "is not at original as"
        is_less_than += "sounds less original than"

        is_more_than += "sounds more original than"
        is_more_than += "is more original than"
        is_more_than += "is more unique than"
    }

    output {
        purpose = {
            +"You have just read a piece of writing that you find interesting. "
        }
        singular = {
            +"writing"
        }
        min_def = {
            random {
                +"the adjective representing the least originality"
                +"the adjective showing a writing fully unoriginal"
            }
        }
        max_def = {
            random {
                +"the most original"
                +"the most unique"
            }
        }
        is_min = {
            random {
                +"is the most original style of writing"
                +"is the adjective showing the most originality in a work"
            }
        }
        is_max = {
            random {
                +"represents the most unique way of writing"
                +"is the adjective showing the most originality in a work"
            }
        }
        is_less_than = {
            +"is less original compared to"
        }
        is_more_than = {
            +"is more original compared to"
        }
    }

    questions {

        class AskHaveHeardAdj() : Intent() {
            var card: CardEntity? = null
            override fun getExamples(lang: Language): List<String> {
                return listOf("have you ever heard of this adjective, @card")
            }
        }

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

        user<AskHaveHeardAdj> {
            val heardAdj = it.card!!.card!!
            robot {
                +attend(heardAdj.location)
                +{ focusStack.prime(heardAdj) }
                +"No, I don't think I have ever heard ${heardAdj.indef}."
                +attend(users.current)
                +"Have you?"
            }
            user(Yes()) {
                robot { +"Wow, sounds interesting. Was ${heardAdj.def} in a book?" }
                user(Yes(), No(), DontKnow(), Nomatch, proceed = TakeInitiative()) {
                    robot { +"Okay, I see" }
                }
            }
            user(No(), DontKnow()) {
                robot { +"Well, then it might be hard for us to know where ${heardAdj.indef} should be in our list." }
            }
            user(
                intent(
                    "I have read it",
                    "I have seen it in a book",
                    "I think I have."
                )
            ) {
                robot { +"Me too. But that's not the same thing as knowing and using it in a context." }
            }
        }


        fun RobotTurn.userFollowUpDefinition() {
            user(intent("tell me another definition", "define more", "give me another definition")) {
                val writing = focusStack[0]
                robot {
                    include(writing.definition.randomAvoidRepeat()!!)
                }
                userFollowUpDefinition()
            }
        }

        user<AskWhatIs> {
            val writing = it.card!!.card!!
            robot {
                +{ focusStack.prime(writing) }
                include(writing.definition.randomAvoidRepeat()!!)
            }
            userFollowUpDefinition()
        }

        fun RobotTurn.userFollowUpExample() {
            user(intent("another example", "can you tell me another sentence?", "give me another instance", "tell me another example", "I want more examples", "try another example", "could you tell me another one?")) {
                val writing = focusStack[0]
                robot {
                    include(writing.example.randomAvoidRepeat()!!)
                }
                userFollowUpExample()
            }
        }

        user<AskExample> {
            val writing = it.card!!.card!!
            robot {
                +{ focusStack.prime(writing) }
                include(writing.example.randomAvoidRepeat()!!)
            }
            userFollowUpExample()
        }

    }

    // Start the deck here:
    card {
        id = "hackneyed"
        truth = 0
        image = "writing.jpg"
        name = "Hackneyed"
        def = "being hackneyed"
        indef = "a hackneyed substance"

        gramclass = "adjective"

        input += "hackneyed"
        input += "being hackneyed"

        definition += { +"Hackneyed means something being used too often and therefore boring." }

        argument_low += { +"This is a very negative thing to say about someone’s writing, since it implies that it’s boring. " }
        argument_low += { +"I think it would be very rude to say that someone’s writing overuses repetitive tropes " }

        argument_high += { +"Lots of authors try to write things similar to what others have written, so this seems like a good compliment. " }
        argument_high += { +" I think it’s a positive quality for writing to reuse ideas that have been used many times before. " }

        example += { +"The artist should be careful to avoid hackneyed subjects" }
        example += { +"The plot of the film is just a hackneyed boy-meets-girl scenario." }
    }


   /* card {
        id = "trite"
        truth = 1
        image = "writing.jpg"
        name = "Trite"
        def = "being trite"
        indef = "a trite writing"
        gramclass = "adjective"
        input += "trite"
        input += "being trite"

        definition += { +"Being trite means being boring because it has been expressed so many times before; not original." }

        argument_low += { +"Calling someone’s writing unoriginal seems pretty insulting." }
        argument_low += { +"A word that means boring and not original does not seem like much of a compliment." }

        argument_high += { +"It seems like a good thing to be told that your work is nothing new." }
        argument_high += { +"I think that an author might really appreciate being told that their writing has ideas that have been expressed a lot of times." }

        example += { +"this point may now seem obvious and trite" }
        example += { +"She seemed bored and asked trite questions" }
    }

    */
/*
    card {
        id = "unexciting"
        truth = 2
        image = "writing.jpg"
        name = "Unexciting"
        def = "being unexciting"
        indef = "an unexciting writing"
        gramclass = "adjective"

        input += "unexciting"
        input += "being unexciting"

        definition += { +"Something unexciting is not interesting, it is boring" }

        argument_low += { +"Saying that a text is unexciting implies that it is not so funny to read. " }

        argument_high += { +"If a text is unexiting nothing unexpected happens in it, which would appear to be good. " }

        example += { +"He is an earnest, unexciting politician." }
        example += { +"Some people might find the life we live here unexciting" }
    }

*/

    card {
        id = "derivative"
        truth = 3
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
        truth = 4
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


/*
   card {
        id = "acceptable"
        truth = 5
        image = "writing.jpg"
        name = "Acceptable"
        def = "being acceptable"
        indef = "an acceptable piece of writing"
        gramclass = "adjective"

        input += "acceptable"
        input += "being acceptable"

        definition += { +"Acceptable means agreed or approved of by most people in a society" }

        argument_low += { +"Saying that the text is acceptable does not sound like praise. " }

        argument_high += { +"Something acceptable is not bad. " }

        example += { +"Children must learn socially acceptable behaviour." }
        example += { +"It is perfectly acceptable for you as an employee to say no." }
    }

*/

/*
    card {
        id = "perspicuous"
        truth = 6
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
*/




    card {
        id = "interesting"
        truth = 7
        image = "writing.jpg"
        name = "Interesting"
        def = "being interesting"
        indef = "an interesting writing"
        gramclass = "adjective"

        input += "interesting"
        input += "being interesting"

        definition += { +"Something interesting attracts your attention because it is special, exciting or unusual" }

        argument_low += { +"Many texts are interesting, it does not make the text stand out." }

        argument_high += { +"If  a text is interesting, it is fun to read and people want to read it." }

        example += { +"She sounds like a really interesting person." }
        example += { +"The article raises several interesting questions." }
    }

    card {
        id = "adroit"
        truth = 8
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






/*
    card {
        id = "peerless"
        truth = 9
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
*/


}