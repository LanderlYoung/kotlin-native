/*
 * Copyright 2010-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

package org.jetbrains.kotlin.backend.konan.optimizations

import org.jetbrains.kotlin.backend.konan.Context

internal class DeadCodeElimination(private val context: Context) {
    internal fun run() {
        if (context.shouldDeadCodeElimination()) {
            TODO("need impl")
        }
    }
}


