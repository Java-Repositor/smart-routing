# 📦 Smart Delivery Routing System

An intelligent, scalable, and efficient routing system built in Java + Spring Boot that computes the **shortest delivery route** between any two Indian pincodes using real-world geographical data.

---

## 🚀 Features

- 🌐 **India-wide pincode support** (11K+ locations)
- 📍 Calculates shortest path between two pincodes using real geospatial coordinates
- ⚡ Blazing fast route computation with **KD-Tree optimization**
- 🧠 Apply classic **Dijkstra’s algorithm** for accurate shortest paths
- ♻ Support for both **brute-force** and **KD-Tree**-based graph building
- ↻ Switchable algorithm mode via REST API
- 📦 Lightweight, file-based bootstrapping (CSV source)
- 🫮 Real-world distance calculation using **Haversine formula**

---

## 🧠 Algorithms & Data Structures Used

| Feature              | Approach                                                      |
| -------------------- | ------------------------------------------------------------- |
| Graph Construction   | Nearest-neighbor connection                                   |
| KD-Tree              | Used to optimize spatial KNN lookup                           |
| Dijkstra’s Algorithm | Used to calculate the shortest path                           |
| Haversine Formula    | Calculates the distance between two coordinates on a sphere   |
| Strategy Pattern     | Used to switch between Brute-force and KD-Tree graph builders |

---

## 📱 API Usage

### `GET /api/v1/find/best-route/{from}/{to}?mode={mode}`

| Parameter | Type   | Description                      |
| --------- | ------ | -------------------------------- |
| `from`    | String | Source pincode                    |
| `to`      | String | Destination pincode               |
| `mode`    | String | Mode: `brute` or `test` (KDTree)   |

#### ✅ Example Request:

```
http://localhost:8080/api/v1/find/best-route/600041/226014?mode=test
```

### ⟺ Modes:

| Mode    | Behavior                              |
| ------- | ------------------------------------- |
| `brute` | Uses brute-force distance comparisons |
| `test`  | Uses optimized KD-Tree based lookup   |

---

## 📊 Sample API Response

```json
{
    "path": [
        {
            "circleName": "Tamildu Circle",
            "regionName": "Cheni City Region",
            "divisionName": "Cheni City South Division",
            "officeName": "Valmiki gar S.O",
            "pincode": "600041",
            "officeType": "PO",
            "delivery": "Non Delivery",
            "district": "CHENI",
            "stateName": "TAMIL DU",
            "latitude": "12.993084",
            "longitude": "80.163043",
            "parsedLat": 12.993084,
            "parsedLon": 80.163043
        },
        {
            "circleName": "Tamildu Circle",
            "regionName": "Southern Region Madurai",
            "divisionName": "Virudhugar Division",
            "officeName": "Khansapuram BO",
            "pincode": "626133",
            "officeType": "BO",
            "delivery": "Delivery",
            "district": "VIRUDHUGAR",
            "stateName": "TAMIL DU",
            "latitude": "9.62",
            "longitude": "77.6",
            "parsedLat": 9.62,
            "parsedLon": 77.6
        }
    ],
    "totalDistance": 477.5747679884589
}
```

---

## 📂 Folder Structure (simplified)

```
src/
├── main/
│   ├── java/
│   │   └── com.santhosh.routing/
│   │       ├── api/
│   │       ├── model/
│   │       ├── service/
│   │       ├── graph/
│   │       └── util/
│   └── resources/
│       └── pincode-data.csv
```

---

## 📚 Learning Highlights

This project helped implement and reinforce:

- ✅ Spring Boot REST API design
- ✅ Java generics and strategy pattern
- ✅ CSV parsing and data modeling
- ✅ Real-world use of Dijkstra’s shortest path algorithm
- ✅ KD-Tree for 2D spatial optimization
- ✅ Handling 11,000+ real records efficiently
- ✅ Lat/Lon parsing, distance, and graph traversal logic

---

## 💡 Future Improvements

- 🗺 Map visualization of routes (Google Maps / Leaflet.js)
- 🫨 Caching frequently used paths
- 🤥 Multi-threaded graph preloading
- 🧵 Logging and analytics for requests
---

## 👌 Credits

- KD-Tree Java Library: [`com.harium.kdtree`](https://github.com/harium/kdtree)
- Base data: Indian Pincode Database (public dataset)
---
