# 🔐 JWT Authentication with Spring Security (Simple Guide)

This project shows **how authentication works in a real Spring Boot application** using **JWT**, **Spring Security**, **database-backed sessions**, and **request logging**.

It is written to be **easy to understand**, even if you are new to backend development.

---

## 🧠 Big Idea (In Simple Words)

Think of your application like a **secure building**:

* 🧾 **JWT Token** → an ID card
* 🧍 **User** → a person
* 🛂 **JwtAuthFilter** → security guard checking ID cards
* 🎥 **LoggingFilter** → CCTV camera recording everything
* 🗂 **SessionEntity** → register of active ID cards

Only people with a **valid, active ID card** are allowed inside.

---

## 🚀 What Happens When a User Logs In

1. User sends **email + password** to `/auth/login`
2. Spring Security checks credentials
3. If correct:

   * A **JWT token** is created
   * A **SessionEntity row** is saved in the database
   * Old sessions for that user are removed
4. Token is returned to the client:

   * In response body
   * As an **HttpOnly cookie**

📌 Only **one active session per user** is allowed.

---

## 🔑 What Is a JWT Token (Very Simple)

A **JWT (JSON Web Token)** is a signed string that proves:

* Who the user is
* When the token expires
* What role the user has

It looks like this:

```
HEADER.PAYLOAD.SIGNATURE
```

If anyone changes the token → **signature breaks** → token becomes invalid.

---

## 🔄 What Happens on Every Request

When the client calls a protected API:

```
GET /api/books
Authorization: Bearer <JWT_TOKEN>
```

The request goes through this flow:

1. **LoggingFilter** logs the request
2. **JwtAuthFilter** checks the token
3. **SessionService** checks the database
4. **JwtService** validates the token
5. **UserService** loads the user
6. User is marked as authenticated
7. Controller runs
8. Response is logged

---

## 🎥 LoggingFilter (CCTV Camera)

What it does:

* Logs HTTP method (GET, POST, etc.)
* Logs URL and query params
* Logs request body
* Logs response body
* Logs time taken

What it **does NOT** do:

* It does NOT authenticate
* It does NOT block requests

It runs **before everything else**, so even failed requests are logged.

---

## 🛂 JwtAuthFilter (Security Guard)

This filter decides **who is allowed in**.

It checks:

1. Is there an `Authorization: Bearer` header?
2. Does the token exist in the database?
3. Is the session revoked?
4. Is the token expired?
5. Does the user still exist?

If everything is valid:

* The user is placed into `SecurityContextHolder`
* Controllers can now access the authenticated user

If not:

* Request is rejected with `401 Unauthorized`

---

## 🗂 SessionEntity (Why We Need It)

JWT alone **cannot be revoked**.

So we store sessions in the database.

Each session stores:

* User
* JWT token
* Created time
* Expiration time
* Revoked flag

This allows:

* Real logout
* Token revocation
* Single active login per user
* Immediate blocking of stolen tokens

---

## 🚪 Logout (Real Logout)

When user logs out:

1. Token is extracted from header or cookie
2. Session is marked as `revoked = true`
3. Cookie is deleted

Even if the JWT is still valid:

❌ It will NOT work anymore

---

## ⚠️ Error Handling

All errors are handled in one place using:

* `@ControllerAdvice`
* `@ExceptionHandler`

Examples:

* Expired token → `401 Unauthorized`
* Invalid token → `401 Unauthorized`
* User not found → `404 Not Found`

Responses are always clean JSON.

---

## 🔐 Authentication vs Authorization

Very important difference:

* **Authentication** → Who are you?
* **Authorization** → What are you allowed to do?

Authentication happens in **filters**.
Authorization happens using:

* Roles
* `@PreAuthorize`
* Endpoint rules

---

## 🧩 Why This Design Is Good

✔ Secure
✔ Scalable
✔ Easy to debug
✔ Real logout support
✔ Industry-standard

This is how **real backend systems** are built.

---

## 🏁 Final Summary

* JWT proves identity
* SessionEntity gives control
* Filters enforce security
* LoggingFilter gives visibility
* Spring Security ties it all together

If you understand this flow, you understand **modern backend authentication**.

✅ You are no longer a beginner.
