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
                +"Oh no... Your friend just got sick and you do not recognize any of the bottles in their medicin cabinet. Rank them from most to least useful based on the words on their etiquette."
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
                val cure = focusStack[0]
                robot {
                    include(cure.definition.randomAvoidRepeat()!!)
                }
                userFollowUpDefinition()
            }
        }

        user<AskWhatIs> {
            val cure = it.card!!.card!!
            robot {
                +{ focusStack.prime(cure) }
                include(cure.definition.randomAvoidRepeat()!!)
            }
            userFollowUpDefinition()
        }

        fun RobotTurn.userFollowUpExample() {
            user(intent("another example", "can you tell me another sentence?", "give me another instance", "tell me another example", "I want more examples", "try another example", "could you tell me another one?")) {
                val cure = focusStack[0]
                robot {
                    include(cure.example.randomAvoidRepeat()!!)
                }
                userFollowUpExample()
            }
        }

        user<AskExample> {
            val cure = it.card!!.card!!
            robot {
                +{ focusStack.prime(cure) }
                include(cure.example.randomAvoidRepeat()!!)
            }
            userFollowUpExample()
        }

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

        argument_low += { +"“Noxious” is a word that would usually describe poison or something else harmful, so it would probably be dangerous to give your friend the pills from this bottle." }
        argument_low += { +"If someone is not well, giving them something noxious would do the opposite of curing them, since that means the pills are poisonous." }

        argument_high += { +" Homeopathic remedies work by giving people a very small amount of a dangerous, noxious substance, so it seems like this bottle could be useful." }
        argument_high += { +" You need a noxious substance that will poison and kill whatever is infecting your friend, so this bottle seems like a good choice." }

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

        argument_low += { +"Usually things that smell or taste disgusting are poisonous, so I don’t think you should give your friend medicine that’s “noisome”." }
        argument_low += { +"A bottle that’s marked “noisome” sounds like something disgusting and unpleasant that will probably just make your friend feel worse." }

        argument_high += { +"It’s common knowledge that medicine usually tastes and smells awful, so something marked “noisome” is probably an effective medicine." }
        argument_high += { +" It seems likely that a very unpleasant, disagreeable drug that’s marked “noisome” is a powerful medicine." }

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

        argument_low += { +"A medication being “luscious” just tells you it tastes good, but for all we know it could be completely useless." }
        argument_low += { +" I’m not sure a word like “luscious”, which is usually used to describe something delicious or pleasing to the senses, is something you’d find on a bottle of medication." }

        argument_high += { +"A medicine that is luscious would be very pleasant to consume, so it would be easy to get your sick friend to take it." }
        argument_high += { +"Taking a luscious medicine that tastes delicious would probably make your sick friend feel much better." }

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

        argument_low += { +"I don’t think there’s much point in inoculation if your friend is already sick, since that prevents diseases rather than curing them." }
        argument_low += { +" Inoculation seems more appropriate for protecting someone who isn’t sick yet from than for treating someone who is already ill." }

        argument_high += { +"With some diseases, giving a patient an inoculation can make them better even if they’ve already started experiencing symptoms, so this bottle seems useful." }
        argument_high += { +"A drug that provides inoculation against diseases seems like it could be very helpful for a sick person." }

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

        argument_low += { +"I’m not sure how useful it would be to give your friend something that palliates their symptoms, since that won’t actually address the cause of their illness." }
        argument_low += { +"To palliate just means to make a person’s symptoms less painful, not to actually cure them, so using the pills in this bottle may not be the best idea." }

        argument_high += { +"The bottle that says “palliate” would probably have something in it that would make a sick person feel better." }
        argument_high += { +"A drug that palliates would have a positive effect on your sick friend’s symptoms." }

        example += { +"The drugs palliate pain but have no effect on inflammation." }
        example += { +" They tried to palliate the hardship of their lives." }
    }

    card {
        id = "panacea"
        truth = 5
        image = "question-mark.png"
        name = "Panacea"
        def = "the panacea"
        indef = "a panacea"
        gramclass = "noun"

        input += "panacea"
        input += "the panacea"

        definition += { +"A panacea is something that will solve all the problems of a particular situation." }

        argument_low += { +"I would be a little suspicious of something that was supposed to be a panacea, since I don’t think there’s such a thing as a universal cure for everything." }
        argument_low += { +"A one-size-fits-all solution to every problem hardly seems believable, so I would be wary about using a bottle labeled “panacea”" }

        argument_high += { +"Since a panacea is a universal cure, a bottle with this word on it would clearly be the most useful for curing a sick person." }
        argument_high += { +"A panacea is something that will solve any problem, which means it would definitely be useful for helping your sick friend." }

        example += { +"Many people believe chicken soup is a panacea for any respiratory illness. " }
        example += { +"Unfortunately there is no panacea that will make cancer instantly vanish from your body." }
    }

}