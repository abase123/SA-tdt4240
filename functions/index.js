const functions = require("firebase-functions");
const admin  = require("firebase-admin")

admin.initializeApp()
exports.sendNotificationOnNewData = functions.firestore
    .document('daily_quizzes/{documentId}')
    .onCreate(async(snap,context) => {
        const payload ={
            notification:{
                title:"Daily Quiz is available",
                body: " Check out the quiz as fast as possible."
            },
        };
        const tokensSnapshot = await admin.firestore().collection('user-tokens').get();
        const tokens = tokensSnapshot.docs.map((doc) => doc.data().token);

        if (tokens.length > 0) {
            const response = await admin.messaging().sendToDevice(tokens, payload);
            console.log('Notification sent successfully:', response);
        } else {
            console.log('No tokens found. Notification not sent.');
        }
    })
// // Create and deploy your first functions
// // https://firebase.google.com/docs/functions/get-started
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//   functions.logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });
