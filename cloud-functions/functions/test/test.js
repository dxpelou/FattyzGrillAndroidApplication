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


describe('', () =>{
    describe('',() => {
        it('send notification', () => {
            stubEvent = {
                data : new functions.database.DeltaSnapshot(null, null, 'old', 'new')
            }
            //myFunctions.orderCreated(stubEvent);
        });
    });
});




after(() => {
    configStub.restore();
    adminInitStub.restore();
});
