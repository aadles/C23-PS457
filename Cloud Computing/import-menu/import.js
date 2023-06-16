const admin = require('firebase-admin');
const serviceAccount = require('./serviceAccount.json');
const data = require('./data.json');

// Inisialisasi Firebase Admin SDK
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: 'https://bangkit-capstone-386710-default-rtdb.firebaseio.com'
});

const firestore = admin.firestore();

// Import data JSON ke Firestore
const importData = async () => {
  try {
    for (const collectionName in data) {
      const documents = data[collectionName];

      for (const documentData of documents) {
        await firestore.collection(collectionName).add(documentData);
        console.log(`Document imported in collection '${collectionName}'`);
      }

      console.log(`All documents imported in collection '${collectionName}'`);
    }

    console.log('Data import completed successfully');
  } catch (error) {
    console.error('Error importing data:', error);
  }
};

importData();
