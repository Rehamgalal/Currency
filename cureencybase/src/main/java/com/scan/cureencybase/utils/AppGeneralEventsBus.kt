package com.scan.cureencybase.utils

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class AppGeneralEventsBus {

    //Channel for one-time-consume events
    private val invalidateUserChannel = Channel<AppEvent>()
    val eventsFlow = invalidateUserChannel.receiveAsFlow()

    fun post(event: AppEvent) {
        invalidateUserChannel.trySend(event)
    }


    sealed class AppEvent {
        object InvalidateUser : AppEvent()
    }

}
