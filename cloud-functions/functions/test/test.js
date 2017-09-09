const sinon = require('sinon');
let myFunctions, configStub, adminInitStub, functions, admin;



// myFunctions.orderCreated(stubEvent);

before(() => {
    admin =  require('firebase-admin');
    adminInitStub = sinon.stub(admin, 'initializeApp');

    functions = require('firebase-functions');

    configStub = sinon.stub(functions, 'config').returns({
        firebase: {
            databaseURL: 'https://not-a-project.firebaseio.com',
            storageBucket: 'not-a-project.appspot.com',
        }
    });

    myFunctions = require('../index');
});


describe('order created', () =>{
        it('push notification is sent to topic', () => {

            oldData = null;

            newData = { loaded: true,
                managed: false,
                mealIds: [ 'meal3' ],
                orderAccepted: false,
                totalPrice: 1.5,
                valid: true }


            stubEvent = {
                data : new functions.database.DeltaSnapshot(null, null, oldData, newData)
            }



            myFunctions.orderCreated(stubEvent);

            sinon.assert(adminInitStub.messaging.sendToTopic(null, null).calledOnce)
        });
});




after(() => {
    configStub.restore();
    adminInitStub.restore();
});
