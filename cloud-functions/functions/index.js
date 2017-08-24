const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);

exports.orderCreated = functions.database.ref('/orders/{orderID}')
    .onWrite(event => {
       const val =  event.data.val();
        console.log(val);

        const topic = 'orders';

        var payload = {
            data: {
                message: 'hi'
            }
        }

        admin.messaging().sendToTopic(topic, payload)
            .then(response => {
                console.log("Successfully sent message:", response);
            })
            .catch(error => {
                console.log("Error sending message:", error);
            });
    });
