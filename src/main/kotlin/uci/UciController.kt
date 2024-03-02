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
            else -> throw UciCommandException("$command NOT IMPLEMENTED, ignored")
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
        val position = positionCommand.substringAfter("position ").substringBefore(" moves")
        val moveStringList = positionCommand.substringAfter("moves ").split(" ")
        //position startpos moves e2e4 e7e5
        //position fen rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 1 moves ....
        if (position.startsWith("startpos")) {
            board.setStartPos()
        } else if (position.startsWith("fen")) {
            board.initByFen(position.substringAfter("fen "))
        } else {
            throw UciCommandException("Unexpected syntax in position command")
        }
    }

    private fun handleCommandUciNewGame() {
        board.setStartPos()
    }

    private fun handleShow() {
        println (board.toAsciiBoard())
    }
}