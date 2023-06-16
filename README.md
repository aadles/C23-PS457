# Cloud Computing Path
Created a RESTful API using the Express framework and NodeJS to handle food menu data and users data. The API is deployed using Cloud Functions, while Firebase's Firestore and Storage services are utilized for the database functionality.

## List Destination 

| Method | Description |
|------|------|
| `GET` |   All Menu  |
| `GET` |   Menu by Id   |
| `GET` |   Data Users by Id   |
| `POST` |   Data Users   |

In this section will exlpain about list of all menu using Express framework and NodeJS.

### Base URL
> https://us-central1-bangkit-capstone-386710.cloudfunctions.net/app

### Path
/read/menu

### Method
`GET`

### Code
```
app.get("/read/menu", (req, res) => {
  (async () => {
    try {
      const query = db.collection("menu");
      const response = [];

      const querySnapshot = await query.get();
      querySnapshot.forEach((doc) => {
        const selectedItem = {
          "id": doc.id,
          "menu": doc.data()["menu"],
          "bahan": doc.data()["bahan"],
          "langkahPembuatan": doc.data()["langkahPembuatan"],
          "kalori": doc.data()["kalori"],
          "imageURL": doc.data()["imageURL"],
        };
        response.push(selectedItem);
      });

      return res.status(200).send(response);
    } catch (error) {
      console.log(error);
      return res.status(500).send(error);
    }
  })();
})
```
## List Destination 
In this section will exlpain about list menu by Id using Express framework and NodeJS.

### Path
/read/user/:id

### Method
`GET`

### Code
```
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
})
```
## Data Menu
```
"menu": [
    {
      "menu": "Tahu Bulat",
      "bahan": [
        "11 buah tahu putih dan sudah direbus",
        "1 buah wortel",
        "2 tangkai daun bawang",
        "2 tangkai seledri",
        "1 buah telur",
        "1/2 sdm bawang putih giling halus",
        "1 sdt garam",
        "secukupnya gula",
        "secukupnya penyedap rasa ayam",
        "2 sdm bawang goreng"
      ],
      "langkahPembuatan": [
        "- Hancurkan tahu menggunakan sendok hingga halus.",
        "- Wortel dipotong halus menggunakan pemarut.",
        "- Masukkan telur, daun bawang, seledri, bawang putih halus, garam, gula, penyedap rasa ayam, garam, dan bawang goreng.",
        "- Aduk rata dan koreksi rasa.",
        "- Lalu adonan tahu dibuat membulat. Ukuran disesuaikan dengan kecukupan adonan.",
        "- Panaskan wajan dengan api kecil saja, lalu setelah panas, goreng tahu.",
        "- Jika sudah berubah warna menjadi coklat keemasan, balik tahu agar tidak pecah, dan goreng sisi lainnya.",
        "- Angkat dan siap disajikan."
      ],
      "kalori": 175,
      "imageURL": "https://firebasestorage.googleapis.com/v0/b/bangkit-capstone-386710.appspot.com/o/Tahu%20Bulat.JPG?alt=media&token=94d26c0c-829b-452c-ab3f-967cfeb891a7&_gl=1*xibuyn*_ga*NzYzODA1MDc2LjE2ODYzNzI5NTk.*_ga_CW55HF8NVT*MTY4NjY0NDc3Ni4xMS4xLjE2ODY2NDUyMzUuMC4wLjA."
    }
```

## List Destination 
In this section will exlpain about post users data using Express framework and NodeJS.

### Path
/create/user

### Method
`POST`

### Code
```
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
```

## List Destination 
In this section will exlpain about list users data by Id using Express framework and NodeJS.

### Path
/read/user/:id

### Method
`GET`

### Code
```
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
```
