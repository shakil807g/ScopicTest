const functions = require("firebase-functions");

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//

exports.makeUppercase = functions.firestore.document("users/{userId}/{Notes}/{documentID}").onCreate((snap, context) => {
    // Grab the current value of what was written to Firestore.
    const newValue = snap.data();
    const oritxt = newValue.text;
    // Access the parameter `{documentId}` with `context.params`
    functions.logger.log("Uppercasing", context.params.documentId, oritxt);
    
    const uppercase = `${oritxt[0].toUpperCase()}${oritxt.slice(1)}`;
    
    functions.logger.log("After Uppercasing", context.params.documentId, uppercase);
    
    const trimSpace = uppercase.replace(/^\s+|\s+$/g, "");
    
    functions.logger.log("After trim Spaces", context.params.documentId, trimSpace);
    // You must return a Promise when performing asynchronous tasks inside a Functions such as
    // writing to Firestore.
    // Setting an 'uppercase' field in Firestore document returns a Promise.
    return snap.ref.set({trimSpace}, {merge: true});
});