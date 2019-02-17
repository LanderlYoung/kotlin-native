/*
 * Copyright 2010-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 */

package org.jetbrains.kotlin.backend.konan.optimizations

import org.jetbrains.kotlin.backend.konan.Context
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.konan.KonanException
import org.jetbrains.kotlin.konan.target.CompilerOutputKind

internal typealias DeadCodeEliminationResult = Any

internal fun eliminateDeadCode(
        context: Context,
        irModule: IrModuleFragment
) {
    if (context.shouldEliminateDeadCode()) {
        context.deadCodeEliminationResult = DeadCodeElimination(context, irModule).eliminate()
    }
}

private class DeadCodeElimination(
        private val context: Context,
        private val irModule: IrModuleFragment) {

    fun eliminate() {
        markKept()
        eliminateUnmarked()
    }

    private fun markKept() {
        markProduceDefaultKeeps()

        context.config.shrinkKeptLibraries.forEach { lib ->
            val library = context.llvm.librariesToLink.firstOrNull { it.libraryName == lib }
            if (library == null) {
                throw KonanException("can't find library with name $library to keep")
            }
            markModulePublicSymbols(TODO("get ir module of library"))
        }
    }

    private fun eliminateUnmarked() {
    }

    private fun markProduceDefaultKeeps() {
        when (context.config.produce) {
            CompilerOutputKind.PROGRAM -> TODO("keep main function")
            CompilerOutputKind.STATIC,
            CompilerOutputKind.DYNAMIC,
            CompilerOutputKind.FRAMEWORK,
            CompilerOutputKind.LIBRARY ->
                // mark this module
                markModulePublicSymbols(context.irModule!!)
        }
    }

    private fun markModulePublicSymbols(moduleFragment: IrModuleFragment, includeInternals: Boolean = false) {
    }

}


