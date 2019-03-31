package scheduler

import tornadofx.runAsync

// TODO: ovo bi trebalo da bude neki komputaciono-zahtevni zadatak preko kojeg se generiše raspored, i koji
// potencijalno daje uvid u napredak generisanja kroz updateProgress.
fun generateTimetablesTask() = runAsync {
    for (i in 0..10L) {
        Thread.sleep(1000)
        updateProgress(i, 10)
    }
}