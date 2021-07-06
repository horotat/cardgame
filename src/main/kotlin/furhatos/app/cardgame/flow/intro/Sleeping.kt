package furhatos.app.cardgame.flow.intro

import furhatos.app.cardgame.*
import furhatos.app.cardgame.flow.Parent
import furhatos.app.cardgame.flow.updateUsersOnGUI
import furhatos.autobehavior.setDefaultMicroexpression
import furhatos.event.EventSystem
import furhatos.event.senses.SenseSkillGUIConnected
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.records.Location

val Sleeping: State = state(Parent) {

    onEntry {
        send(ActionClearTable())
        send(ActionShowButton(output.button_tap_to_play))
        updateUsersOnGUI()
        furhat.stopSpeaking()
        furhat.attendNobody()
        furhat.attend(Location(0, 0, 1))
        furhat.setDefaultMicroexpression(blinking = false, eyeMovements = false, facialMovements = false)
        furhat.gesture(Gestures.CloseEyes, priority = 1)
    }

    onEvent(SenseGameReset) {
    }

    onEvent<SenseSkillGUIConnected> {
    }

    onEvent(SenseButtonClick) {
        if (users.count > 0) {
            send(ActionHideButton())
            furhat.setDefaultMicroexpression()
            furhat.gesture(Gestures.OpenEyes, priority = 1, async = false)
            // todo: start the log
            dialogLogger.startSession()
            goto(Greeting)
        }
    }

}