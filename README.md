# SecuredApp: Android API Security Demonstration

## Overview

This project is a secure Android application which demonstrates a multi-layered security strategy 
to prevent unauthorized API access from tampered, repackaged, or otherwise compromised application 
instances.

##  How It Works

The app proves it's legitimate by sending three things with every request:

1. App fingerprint - The app sends its own signing certificate as a Base64-encoded string. The 
   server knows what the real signing certificate looks like and rejects any fakes.
2. Timestamp - Shows the request is fresh, not a replay of an old one
3. Secret signature - The app uses a secret key (hidden in the NDK) to create a unique HMAC 
   signature for each request. The backend knows the same secret handshake and can verify its authenticity.

If any of these are wrong or missing, the server blocks the request.

##  Architecture

The Android application is built following modern Clean Architecture principles to ensure a clear 
separation of concerns, testability, and scalability.

MVVM: The UI is driven by a ViewModel which manages state and logic.
Hilt: Handles dependency injection, wiring all the components together.

##  Security Features Implemented

1. HMAC Request Signing: All API calls are signed to prove app integrity and authenticity.

2. NDK Secret Obfuscation: The HMAC secret key is stored in native C++ code. secret has split and 
obfuscated before storing and Reconstruct when returning.

3. Startup Security Check: On app launch, the EnvironmentSecurityManager checks for:
   - Rooted Device (using the RootBeer library)
   - Developer Mode being enabled
   - Emulator environment

If any of these checks fail, the app displays a non-cancellable dialog and forces the user to exit.

##  Backend Server
   - Simple Node.js server with Express
   - Validates App fingerprint, Timestamp and Secret signature before processing requests and reject 
     if any check fails.

- NOTE: This is a demo server become idle after 15 minutes of inactivity on free tier. So the first 
request after a while may timeout. Just try again. 

##  What's Next
   - User login/authentication with JWT tokens
   - Google Play Integrity API for even stronger protection

**GitHub Repository:** [SecuredAppBackend](https://github.com/shan-lak/SecuredAppBackend)
**GitHub Repository:** [SecuredApp](https://github.com/shan-lak/SecuredApp)




