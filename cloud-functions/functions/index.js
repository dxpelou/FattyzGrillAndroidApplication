const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);

exports.orderCreated = functions.database.ref('/orders/{orderID}')
    .onCreate(event => {
       const val =  event.data.val();
       const order = event.params;
        console.log('val: ',val);
        console.log('order: ',order);

        const topic = 'orders';

        var payload = {
                "data": {
                    "type": "new_order",
                    "orderID": order.orderID
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
    const order = event.params;


    if(!wasOrderAccepted && isOrderAccepted){
        var payload = {
            data: {
                "type": "order_accepted",
                "orderID" : order.orderId
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
