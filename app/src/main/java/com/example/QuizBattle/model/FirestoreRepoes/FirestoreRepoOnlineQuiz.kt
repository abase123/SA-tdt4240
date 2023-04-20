package com.example.QuizBattle.model.FirestoreRepoes

import com.example.QuizBattle.model.OnlineModel.OnlineMatch
import com.example.QuizBattle.model.PlayerModel.Player
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseRepository {
    private val db = FirebaseFirestore.getInstance()
    private val onlineQuizzesCollection = db.collection("onlineQuizzes")

    suspend fun joinOrCreateGame(player: Player): OnlineMatch? {
        val existingGame = findAvailableGame()

        return if (existingGame != null) {
            joinGame(existingGame, player)
        } else {
            createNewGame(player)
        }
    }

    private suspend fun findAvailableGame(): DocumentSnapshot? {
        val querySnapshot = onlineQuizzesCollection
            .whereEqualTo("playerTwo", null)
            .limit(1)
            .get()
            .await()

        return if (querySnapshot.isEmpty) {
            null
        } else {
            querySnapshot.documents.first()
        }
    }

    private suspend fun joinGame(document: DocumentSnapshot?, player: Player): OnlineMatch? {
        if (document != null) {
            updatePlayerTwo(document.id, player)
        }

        if (document != null) {
            return document.toObject(OnlineMatch::class.java)?.apply {
                this.playerTwo = player
            }
        }
        return null
    }

    private suspend fun createNewGame(player: Player): OnlineMatch {
        val newGame = OnlineMatch().apply {
            this.playerOne = player
            this.gameStatus = "Waiting for opponent"
        }
        val document = onlineQuizzesCollection.add(newGame).await()
        return newGame.apply { this.id = document.id }
    }

    private suspend fun updatePlayerTwo(documentId: String, player: Player) {
        onlineQuizzesCollection.document(documentId)
            .update("playerTwo", player)
            .await()
    }
}