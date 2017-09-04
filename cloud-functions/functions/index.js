const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);

exports.orderCreated = functions.database.ref('/orders/{orderID}')
    .onWrite(event => {
       const val =  event.data.val();
       const id = event.params;
        console.log(val);
        console.log(id);

        const topic = 'orders';

        var payload = {
            data: {
                orderID : id.toString()
                //send meals in object
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


exports.acceptOrder = functions.database.ref('/orders/{orderID}')
    .onUpdate((event) => {
    const wasOrderAccepted = event.data.previous.child('isOrderAccepted');
    const isOrderAccepted = event.data.current.child('isOrderAccepted');

    if(!wasOrderAccepted && isOrderAccepted){
        var payload = {
            data: {
                orderStatus: 'OK'
            }
        }

        admin.messaging().sendToDevice(event.data.current.child('senderToken'), payload)
            .then(response => {
                console.log('Successfully sent message: ', response);
            })
            .catch(error => {
                console.log('Error sending messaging: ', error);
            });
    }
    });
