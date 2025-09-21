Think of JWT with asymmetric encryption like a tamper-proof ID card system for your web applications.

**What it is in simple terms:**
- **JWT (JSON Web Token)** is like a digital ID card that contains user information (like "John is logged in and has admin rights")
- **Asymmetric encryption** uses two keys: a private key (like your personal seal) and a public key (like a stamp everyone can verify)
- When combined, your server creates JWTs using the private key, and anyone with the public key can verify they're authentic - but they can't create fake ones

**Why you need to know this as a backend dev:**
This is essentially modern authentication and authorization. Instead of storing user sessions on your server, you give users a JWT token after they log in. Every request they make includes this token, proving who they are and what they're allowed to do.

**Where you'd use it:**
- **Microservices** - Each service can verify tokens independently without hitting a central auth database
- **APIs** - Perfect for REST APIs where you want stateless authentication
- **Single Sign-On (SSO)** - One login works across multiple applications
- **Mobile apps** - Tokens work great for mobile clients
- **Distributed systems** - When you have multiple servers/services that need to verify users

**Where you wouldn't use it:**
- **Simple monolithic apps** - Traditional sessions might be simpler
- **High-security scenarios requiring instant revocation** - JWTs can't be "cancelled" until they expire
- **When tokens would be huge** - If you need to store lots of user data in the token
- **Short-lived applications** - Might be overkill for simple projects

The key benefit is scalability and statelessness - your servers don't need to remember who's logged in because the token itself contains and proves that information.

Interpretation of the docker-compose.yml file
How to use keys.properties file here

When you use entity listeners you have to enable JPA auditing;
- Use @EnableJpaAuditing in the main class
- add a configuration class 

Tell me about the userDetails interface

where/how should I store my private and public keys?

responsible for generating access token and refresh token - JWTService class

Using keys can lead to performance issues? since evrytime you have to load the key

It's recommended to always have a short access token and a longer refresh token...why?

