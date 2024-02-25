package org.example.uci

import org.example.config.Settings
import org.example.model.Board

class UciController {
    private var continueCommunicate = true
    private val board = Board()

    fun startProcessing() {
        println("Start processing")
        while(continueCommunicate) {
            print("> ")
            val input = readln()
            processInput(input)
        }
        println("Stop Processing")
    }

    private fun processInput(command: String) {
        when(command.substringBefore(" ")) {
            "uci" -> handleCommandUci()
            "isready" -> handleCommandIsReady()
            "ucinewgame" -> handleCommandUciNewGame()
            "position" -> handleCommandPosition(command)
            "go"  -> println(command.uppercase())
            "stop"  -> println(command.uppercase())
            "ponderhit"  -> println(command.uppercase())
            "quit"  -> handleCommandQuit()
            "show" -> handleShow()
            else -> println("$command NOT IMPLEMENTED, ignored")
        }
    }

    private fun handleCommandUci() {
        println("id name ${Settings.name} ${Settings.version}")
        println("id author ${Settings.author}")
        println("uciok")
    }

    private fun handleCommandQuit() {
        continueCommunicate = false
    }

    private fun handleCommandIsReady() {
        println("readyok")
    }

    private fun handleCommandPosition(positionCommand: String) {
        val position = positionCommand.substringAfter("position ").substringBefore(" ")
        val moveStringList = positionCommand.substringAfter("position ").substringAfter(" ").split(" ")
        //position startpos moves e2e4 e7e5
        if (position == "startpos") {
            board.init()
        } else {
            board.init()
        }
    }

    private fun handleCommandUciNewGame() {
        board.init()
    }

    private fun handleShow() {
        println (board.toAscciiBoard())
    }
}