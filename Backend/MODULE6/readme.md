# 🔐 Spring Security 

This project demonstrates a **modern Spring Security setup** using:

- JWT (Access + Refresh Tokens)
- OAuth2 (Google Login)
- Roles & Permissions (Authorities)
- Request Matchers vs Method Security
- Global Exception Handling

The goal of this README is to explain **how security starts, how requests flow, and where each security concept fits**, in simple words.

---

## 1️⃣ How Spring Security Starts

When the application boots:

1. Spring scans configuration classes
2. Sees `@EnableWebSecurity`
3. Sees `@EnableMethodSecurity`
4. Builds the **Security Filter Chain**
5. Registers **method security interceptors (AOP)**

⚠️ Without `@EnableMethodSecurity`, annotations like  
`@PreAuthorize`, `@Secured`, `@RolesAllowed` **do nothing**.

---

## 2️⃣ Big Picture Architecture

Client (Browser / Postman)
|
v
Spring Security Filter Chain
|
v
Controller
|
v
Service / Business Logic

yaml
Copy code

Security happens in **two places**:

- **At the door** → Request Matchers
- **Inside the room** → Method Security

---

## 3️⃣ Authentication vs Authorization

### Authentication → WHO are you?
- JWT validation
- OAuth2 login (Google)
- Happens in **filters**

### Authorization → WHAT can you do?
- Roles
- Permissions (Authorities)
- Happens in **request matchers + method annotations**

---

## 4️⃣ JWT Authentication Flow

### Login
1. User logs in (password or OAuth2)
2. Backend issues:
   - **Access Token** (short-lived)
   - **Refresh Token** (long-lived)

### Request Flow
Client
→ Authorization: Bearer <access-token>
→ JwtAuthFilter
→ SecurityContext populated
→ Controller / Service

yaml
Copy code

- Access Token → used on every request
- Refresh Token → used only to get a new access token
- Refresh tokens are stored server-side and can be revoked

---

## 5️⃣ OAuth2 Login Flow (Google)

OAuth2 is **delegated authentication**.

Flow:
1. Client hits `/oauth2/authorization/google`
2. Redirects to Google
3. User logs in on Google
4. Google sends authorization code
5. Backend exchanges code for tokens
6. Spring creates authenticated user
7. (Optional) Backend issues its own JWT

Important:
- Google tokens ≠ API tokens
- OAuth2 proves identity
- JWT secures your APIs

---

## 6️⃣ Roles vs Authorities (CRITICAL)

Spring Security checks **authorities only**.

### Authority
A permission string:
POST_CREATE
POST_DELETE
USER_UPDATE
ROLE_ADMIN

pgsql
Copy code

### Role
A role is just an authority with `ROLE_` prefix.

ADMIN → ROLE_ADMIN
USER → ROLE_USER

css
Copy code

So:
```
hasRole("ADMIN")
Actually means:

hasAuthority("ROLE_ADMIN")
Spring never checks roles directly.
```

7️⃣ Request Matchers vs Method Security
🚪 Request Matchers (URL-level)
Defined in SecurityFilterChain.
```
.requestMatchers("/posts/**").authenticated()
```
They decide:

Can this request reach the controller at all?

Fail here → request blocked immediately (401 / 403)

🧠 Method Security (Business-level)
Defined on methods.
```
@PreAuthorize("hasAuthority('POST_DELETE')")
```
They decide:

Can THIS method execute?

Fail here → AccessDeniedException (403)

Why You Use Both
```
// SecurityConfig
.requestMatchers("/posts/**").authenticated()

// Controller / Service
@PreAuthorize("@postSecurity.isOwnerOfPost(#postId)")
Request matcher = coarse protection

Method security = fine-grained logic (ownership, permissions)
```

8️⃣ Method Security Annotations
Security annotations are locks on methods.

Annotation	When it runs	Use case
@PreAuthorize	Before	Most common
@PostAuthorize	After	Depends on return
@Secured	Before	Role-only
@RolesAllowed	Before	JSR-250

They only work if:
```
@EnableMethodSecurity
```
is present.

9️⃣ What Happens on Every Request (End-to-End)
Request enters app

JWT filter runs

Token validated

User loaded

Authentication created

Stored in SecurityContext

Request matchers checked

Controller reached

Method security evaluated

Decision:

✅ Allowed → method runs

❌ Denied → 401 / 403

You never write this logic — Spring handles it.

🔟 User Session Management (JWT-based)
Even with JWT, sessions are managed server-side using refresh tokens.

Flow:

On login:

Generate access token

Generate refresh token

Store session (userId, refreshToken, lastUsedAt)

On refresh:

Validate refresh token

Issue new access token

Rotate refresh token

On logout:

Revoke refresh token

Delete session

This gives:

Stateless APIs

Secure logout

Multi-device support

1️⃣1️⃣ Exception Handling (Important)
What @RestControllerAdvice handles
Business exceptions

ResourceNotFoundException

Validation errors

What it does NOT reliably handle
Authentication failures

Authorization failures in filters

Those are handled by:

AuthenticationEntryPoint → 401

AccessDeniedHandler → 403

1️⃣2️⃣ Common Mistakes (Avoid These)
❌ Forgetting @EnableMethodSecurity
❌ Expecting controller exception handlers to catch security errors
❌ Not setting author when creating entities
❌ Confusing roles with authorities
❌ Using OAuth tokens to secure APIs

🧠 Final Mental Model
Spring Security works in layers:

Filters authenticate the user

Authorities describe permissions

Request matchers guard URLs

Method annotations guard business logic

OAuth2 proves identity
JWT secures APIs

✅ Recommended Pattern
Use request matchers for coarse rules

Use method security for business logic

Use JWT for APIs

Use OAuth2 for login

Keep exceptions centralized

📌 Author
This project is designed to learn Spring Security deeply, not just “make it work”.
