import * as functions from "firebase-functions";
import * as admin from "firebase-admin";

const serviceAccount = require("./serviceAccountKey.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

export const sendNotificationOnNewData = functions.firestore
  .document("daily_quizzes/{docId}")
  .onCreate((snapshot, context) => {
    const payload = {
      notification: {
        title: "New data Quiz is here",
        body: "you should take it now",
        channelId: "DailyQuiz",
      },
    };

    return admin.messaging().sendToTopic("new_quiz", payload)
      .then((response) => {
        console.log("Notification sent successfully:", response);
      })
      .catch((error) => {
        console.log("Error sending notification:", error);
      });
  });
