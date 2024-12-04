
# Steps to run the frontend application
```
npm install
npm start
```

# Login
When the user enters the username and password, the application will make a call to the backend to validate the credentials.

Example API Request:
```
curl --location 'http://localhost:8080/api/auth/login' \
--header 'Content-Type: application/json' \
--data '{
    "username": "Sunil",
    "password": "Sunil123"
}'
```

Example API response: 
```
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTdW5pbCIsImlhdCI6MTczMzMzMTg3NiwiZXhwIjoxNzMzNDE4Mjc2fQ.3AYhHr9zmjaTIVKmxSZ5UHC6Qx1uCbCAJMnRK4yF5uo", // This is the JWT token
    "user": {
        "id": 8,
        "username": "Sunil",
        "name": "Sunil",
        "email": "sunil@setu.co"
    }
}
```
Backend return JWT token if username and password are correct.

## JWT Token Generation (if credentials are valid):
- Creates JWT token containing:
    - Subject (sub): Username
    - Issued At (iat): Current timestamp
    - Expiration (exp): Usually set to 24 hours from issuance
    - Signs token with a secret key
- Both the secret key and expiration time are stored in the `application.yml` file in the backend.

## JWT Token Structure
The JWT token consists of three parts separated by dots:
1. Header: Contains algorithm type (HS256)
```
{
    "alg": "HS256"
}
```
2. Payload: Contains claims
```
{
    "sub": "Sunil",
    "iat": 1733331876,
    "exp": 1733418276
}
```
3. Signature: Created by:
Taking encoded header and payload
Signing them with secret key using HS256 algorithm


# Frontend Token Storage
- The JWT token is stored in the browser's local storage under the key "authToken".
- This token can be sent with all the subsequent requests to the backend[not yet implemented in this example]
- On Logout , this key `authToken` will be removed from local storage.
