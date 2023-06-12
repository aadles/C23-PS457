/* eslint-disable max-len */
const functions = require("firebase-functions");
const admin = require("firebase-admin");

const serviceAccount = require("./serviceAccount.json");
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://bangkit-capstone-386710-default-rtdb.firebaseio.com",
});

const express = require("express");
const app = express();
const db = admin.firestore();

const cors = require("cors");
app.use( cors( {origin: true}));

// API Url
// https://us-central1-bangkit-capstone-386710.cloudfunctions.net/app


// POST Height, Weight, Age
app.post("/create/user", (req, res) => {
  (async () => {
    try {
      const newUserRef = await db.collection("user").doc();
      const newUser = {
        height: req.body.height,
        weight: req.body.weight,
        age: req.body.age,
      };
      await newUserRef.set(newUser);
      return res.status(200).send();
    } catch (error) {
      console.log(error);
      return res.status(500).send(error);
    }
  })();
});

// GET Height, Weight, Age by ID
app.get("/read/user/:id", (req, res) => {
  (async () => {
    try {
      const document = db.collection("user").doc(req.params.id);
      const user = await document.get();
      const response = user.data();

      return res.status(200).send(response);
    } catch (error) {
      console.log(error);
      return res.status(500).send(error);
    }
  })();
});

// GET All Menu
app.get("/read/menu", (req, res) => {
  (async () => {
    try {
      const query = db.collection("menu");
      const response = [];

      const querySnapshot = await query.get();
      querySnapshot.forEach((doc) => {
        const selectedItem = {
          "id": doc.id,
          "Nama Makanan": doc.data()["Nama Makanan"],
          "Bahan-Bahan": doc.data()["Bahan-Bahan"],
          "Langkah Pembuatan": doc.data()["Langkah Pembuatan"],
          "Jumlah Kalori per porsi (kkal)": doc.data()["Jumlah Kalori per porsi (kkal)"],
        };
        response.push(selectedItem);
      });

      return res.status(200).send(response);
    } catch (error) {
      console.log(error);
      return res.status(500).send(error);
    }
  })();
});


// GET Menu by ID
app.get("/read/menu/:id", (req, res) => {
  (async () => {
    try {
      const document = db.collection("menu").doc(req.params.id);
      const menu = await document.get();
      const response = menu.data();

      return res.status(200).send(response);
    } catch (error) {
      console.log(error);
      return res.status(500).send(error);
    }
  })();
});

exports.app = functions.https.onRequest(app);
