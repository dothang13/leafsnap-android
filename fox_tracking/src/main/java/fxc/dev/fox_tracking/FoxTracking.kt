package fxc.dev.fox_tracking

import fxc.dev.common.Fox

/** Returns the TrackingManager instance of the default Fox. */
val Fox.tracking: TrackingManager
    get() = TrackingManager.getInstance()
