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
    .onWrite((event) => {
    const wasOrderAccepted = event.data.previous.child('orderAccepted').val();
    const isOrderAccepted = event.data.current.child('orderAccepted').val();
    const order = event.params;

    console.log(wasOrderAccepted);
    console.log(isOrderAccepted);


    if(!wasOrderAccepted && isOrderAccepted){
        var payload = {
            data: {
                "type": "order_accepted",
                "orderID" : order.orderID
            }
        }

        console.log(payload);
        const token = event.data.current.child('senderNotificationToken').val();


        console.log('user token: ', token);

        admin.messaging().sendToDevice(token, payload)
            .then(response => {
                console.log('Successfully sent message: ', response);
            })
            .catch(error => {
                console.log('Error sending messaging: ', error);
            });
    }
    });
