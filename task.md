```markdown
# 🧪 TEST PROJECT – THE NOTES APP

We would like to invite you to try out some technologies that may be new to you and that most of our teams are using, so we have designed this small “assignment” where you touch in on a few of them.  
We imagine that you can work with it for 2 days from the day of receiving this test.  
Thank you and good luck.

---

## 📘 Application Specification

This is a **Spring Boot & MongoDB** application allowing the user to store everyday notes.

---

## 🧩 Functional Requirements

- User should be able to create notes that have **Title**, **Created Date**, **Text**, and **Tags** that can be empty.  
- The only allowed tags are:  
  - `BUSINESS`  
  - `PERSONAL`  
  - `IMPORTANT`
- User should always be able to **update** and **delete** notes.
- The app should allow obtaining **stats per each note**, calculating the number of unique words used in the note’s text, sorted descending.  
  For instance, given a text:  
```

"note is just a note"

````
it should return a Map as follows:
```json
{ "note": 2, "is": 1, "just": 1, "a": 1 }
````

* The app should **not allow** creating notes without a **title** or **text**.
* The app should allow **listing notes** showing only their *Title* and *Created Date*.
  Getting the note text should be done via a **separate request**.
* While listing notes, the user should be able to **filter by Tags**.
* Notes should always be **sorted with the newest first**.
* Listing notes should support **pagination**, as the user might have many notes.

---

## ⚙️ Nonfunctional Requirements

* **Do NOT create UI.** Provide a **RESTful API** that fulfills the given requirements.
* Use **MongoDB** as the database to store user notes.
* Use **Spring Boot** as the backend framework.
* Use **Docker & Docker Compose** to build the application.
* Provide the **steps to run the application** as a separate note attached along with the code.
* Provide **test cases** for the above-mentioned conditions and APIs.

---

```
```
