# Flight Reservation Manager (Android • Java + SQLite)

An Android app that helps an airline manage flights and passenger reservations. It supports two roles — **Admin** and **Passenger** — with sign‑up/sign‑in, profile data, flight search, reservations, and an admin console for creating and managing flights.

> Built by Engineer Nafè as part of a university lab project; implemented in **Java**, using **SQLite** for persistence, **HttpURLConnection + AsyncTask** for ingesting seed data from a JSON API, **Navigation Drawer** UIs for both roles, **Notifications** for reminders, and a small **tween animation** on the login screen.

## ✨ Highlights
- Local **SQLite** schema with `admin`, `user`, `flight`, `reservations` tables
- **API bootstrap**: downloads flights JSON on first screen and stores in DB
- **Login with “Remember me”** (SharedPreferences)
- **Role‑based navigation drawers** for Admin and Passenger
- **Flight search** (city/date, sort by cost or duration), **reservation flow**, and **summaries**
- **Next‑day flight reminder** notifications for users with upcoming trips
- **Tween animation** of a plane on the login screen

## 🧭 Feature Map (spec ↔ implementation)
- ✅ Networking
- ✅ SQLite DB
- ✅ Auth & Remember Me
- ✅ Notifications (reminders)
- ✅ Animation
- ✅ Admin: create flight
- ✅ Admin: edit/delete flight
- ✅ Admin: view flights by availability
- ✅ Admin: archived flights
- ✅ Admin: view reservations of flight
- ✅ User: search flights (city/date, sort)
- ✅ User: make reservation + summary
- ✅ User: view current/previous reservations
- ⛔ Spec: show 5 closest flights post-login
- ⛔ Spec: round-trip search + return date
- ⛔ Spec: reschedule/cancel reservations

## 🏗️ Architecture & Code Map
```
app/
├─ ConnectionAsyncTask.java
├─ LoginActivity.java
├─ MainActivity.java
├─ FailedLoginActivity.java
├─ HttpManager.java
├─ UserSignupActivity.java
├─ UserNavigationDrawer.java
├─ User.java
├─ SignupActivity.java
├─ DatabaseHelper.java
├─ Flight.java
├─ AdminNavigationDrawer.java
├─ FlightAdapter.java
├─ PlaneJsonParser.java
├─ FlightArchiveFragment.java
├─ ViewReservationsFragment.java
├─ ViewFlightsFragment.java
├─ CreateNewFlightFragment.java
├─ EditFlightFragment.java
├─ HomeFragment.java
├─ HomeViewModel.java
├─ UserViewReservationsFragment.java
├─ UserMakeNewReservationFragment.java
├─ UserSearchFlightsFragment.java
└─ plane_move.xml (tween animation)
```

**Key modules**
- **Network**: `HttpManager` (GET JSON) → `ConnectionAsyncTask` (downloads on app start) → `PlaneJsonParser` (maps to `Flight` model) → `DatabaseHelper.insertFlight(...)`
- **Persistence**: `DatabaseHelper` holds schema & queries (create/update/delete flights, search, reservations, “available vs not yet bookable”, archived by arrival date, user’s current/previous trips, etc.)
- **UI (Activities)**: `LoginActivity` (API ingest + auth + “Remember me” + animation + notifications), `FailedLoginActivity` (retry), `AdminNavigationDrawer`, `UserNavigationDrawer`
- **UI (Fragments)**: Admin — `CreateNewFlightFragment`, `EditFlightFragment`, `ViewFlightsFragment` (available/not yet), `FlightArchiveFragment`, `ViewReservationsFragment` (by flight). Passenger — `UserSearchFlightsFragment`, `UserMakeNewReservationFragment`, `UserViewReservationsFragment`

## 🗄️ Data Model (selected fields)
- **Flight**: `flight_number`, `departure_place`, `destination`, `departure_date_time`, `arrival_date`, `arrival_time`, `duration`, `aircraft_model`, `current_reservations`, `max_seats`, `missed_flight_count`, `booking_open_date`, `price_economy`, `price_business`, `price_extra_baggage`, `is_recurrent`
- **User (passenger)**: adds `passport_number`, `passport_issue_date`, `passport_issue_place`, `passport_expiration_date`, `food_preference`, `date_of_birth`, `nationality`
- **Reservation**: `user_email`, `flight_number`, `class` (economy/business), `extra_bags`, `reservation_date`

## 🔐 Validation
- Email format, phone number length, first/last name length (3–20), password complexity (8–15, 1 digit, 1 lower, 1 upper, 1 special), and “confirm password” checks in both Admin and Passenger sign‑up.

## 🔔 Notifications
- On successful **user** login, the app queries flights departing the **next day** and sends a system notification per flight.

## 🖼️ Animation
- Simple XML **tween translate** animation moves a plane diagonally on the login screen (`res/anim/plane_move.xml`).

## 🚀 Getting Started
1. **Open in Android Studio** (prefer latest stable). Let Gradle sync.
2. **Set the seed API URL** (if you want to use your own JSON): in `LoginActivity.java`, update the URL passed to `new ConnectionAsyncTask(...).execute("<your-endpoint>")`.
- Default Mock API endpoint(s):
  - `https://mocki.io/v1/02979e2f-71d1-4632-82cb-bcea8e9ddde2`
  - `https://mocki.io/v1/285bea45-5062-46e7-8e16-2de255a94d53`
  - `https://mocki.io/v1/465b854e-2c25-4e3e-8822-f03b431a60fa`
3. **Run on an emulator or device**. On the first screen the app will fetch flights and seed the DB. If the fetch fails, you’ll be sent to the **Retry** screen.
4. **Sign up** as Admin or Passenger, then **log in**. “Remember me” stores your email.

## 🧪 How to demo quickly
- **Admin** → Create a flight → Edit it → Toggle availability via `booking_open_date` (visible in “Available/Not yet” views) → View archived flights (past arrivals) → View reservations for a flight.
- **Passenger** → Search by departure/arrival city + date, sort by “Lowest Cost” or “Shortest Duration” → Select a flight number → Make a reservation (choose class, extra bags, optional food preference) → See cost summary → View **Current** vs **Previous** reservations.

## 🧩 Known Gaps / Next Steps
- Dashboard card for **“Top 5 upcoming flights”** after login (per spec).
- **Round‑trip + return date** search UI.
- **Reschedule / Cancel** reservation flows.
- Use **Date/Time pickers** everywhere and **dropdowns** for city/model consistently.
- Replace `AsyncTask + HttpURLConnection` with **WorkManager + Retrofit** and coroutines/Flows (or RxJava) for modern architecture.
- UI polish + tests.

## 🧪 Test JSON shape (example)
```jsonc
[
  {
    "flight_number": "AB123",
    "departure_place": "Amman",
    "destination": "Riyadh",
    "departure_date": "2024-08-10",
    "departure_time": "14:30",
    "arrival_date": "2024-08-10",
    "arrival_time": "17:10",
    "duration": "2h 40m",
    "aircraft_model": "AIRBUS A320 JET",
    "current_reservations": 12,
    "max_seats": 160,
    "missed_flight_count": 0,
    "booking_open_date": "2024-07-01 00:00",
    "price_economy": 200.0,
    "price_business": 540.0,
    "price_extra_baggage": 30.0,
    "is_recurrent": "weekly"
  }
]
```

---

### 🧑‍💻 Tech Stack
Android (Java), SQLite, HttpURLConnection/AsyncTask, AndroidX Navigation, Notifications, SharedPreferences.

### 📄 License
Academic project; feel free to adapt.
