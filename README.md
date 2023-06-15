# Cloud Computing
Creating RestAPI for foods menu API in mobile application and deploying to Google Cloud Platform using Cloud Function and using Firebase for database. We use NodeJS 

## RESTFul API

### List Package
This section there is a list of all menus without filter using JSON.

#### Base URL
> https://us-central1-bangkit-capstone-386710.cloudfunctions.net/app

#### Path
> /read/menu

#### Method
> `GET`

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
          "Nama Makanan": doc.data()["Nama Makanan"],
          "Bahan-Bahan": doc.data()["Bahan-Bahan"],
          "Langkah Pembuatan": doc.data()["Langkah Pembuatan"],
          "Jumlah Kalori per porsi (kkal)": doc.data()["Jumlah Kalori per porsi (kkal)"],
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
});
```
#### Data
```
{
  "menu": [
    {
      "Menu": "Tahu Bulat",
      "Bahan-Bahan": [
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
      "Langkah Pembuatan": [
        "- Hancurkan tahu menggunakan sendok hingga halus.",
        "- Wortel dipotong halus menggunakan pemarut.",
        "- Masukkan telur, daun bawang, seledri, bawang putih halus, garam, gula, penyedap rasa ayam, garam, dan bawang goreng.",
        "- Aduk rata dan koreksi rasa.",
        "- Lalu adonan tahu dibuat membulat. Ukuran disesuaikan dengan kecukupan adonan.",
        "- Panaskan wajan dengan api kecil saja, lalu setelah panas, goreng tahu.",
        "- Jika sudah berubah warna menjadi coklat keemasan, balik tahu agar tidak pecah, dan goreng sisi lainnya.",
        "- Angkat dan siap disajikan."
      ],
      "Jumlah Kalori per porsi (kkal)": 880,
      "imageURL": "https://firebasestorage.googleapis.com/v0/b/bangkit-capstone-386710.appspot.com/o/Tahu%20Bulat.JPG?alt=media&token=94d26c0c-829b-452c-ab3f-967cfeb891a7&_gl=1*xibuyn*_ga*NzYzODA1MDc2LjE2ODYzNzI5NTk.*_ga_CW55HF8NVT*MTY4NjY0NDc3Ni4xMS4xLjE2ODY2NDUyMzUuMC4wLjA."
    }
```

### List Package
This section there is a list menu that filtered by ID using JSON.

#### Base URL
> https://us-central1-bangkit-capstone-386710.cloudfunctions.net/app

#### Path
> /read/menu/:id

#### Method
> `GET`

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
});
```

#### Data
[will be edit]

### List Package
This section We need users data for our application that is height, weight, and age using JSON.

#### Base URL
> https://us-central1-bangkit-capstone-386710.cloudfunctions.net/app

#### Path
> /create/user

#### Method
> `POST`

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
})
```
#### Data
[will be edit]

### List Package
This section We need users data for our application that is height, weight, and age that filtered by ID using JSON.

#### Base URL
> https://us-central1-bangkit-capstone-386710.cloudfunctions.net/app

#### Path
> /read/user/:id

#### Method
> `POST`

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
})
```

#### Data
[will be edit]
