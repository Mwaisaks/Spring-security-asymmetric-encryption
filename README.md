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

## Understanding `instanceof` Keyword

**`instanceof`** is a Java operator that checks if an object is an instance of a specific class or interface.

```java
// Syntax: object instanceof ClassName
authentication instanceof AnonymousAuthenticationToken

// Returns true if 'authentication' is an AnonymousAuthenticationToken, false otherwise
```

## getPrincipal() vs getSubject()

**`getPrincipal()`** (Spring Security):
- Returns the **user object** (UserDetails implementation)
- In your case: returns the actual `User` entity
- Contains full user information (ID, email, roles, etc.)

**`getSubject()`** (JWT):
- Returns the **subject claim** from JWT token (usually username/email)
- Just a String value
- Limited information

```java
// From JWT token
String username = jwt.getSubject(); // Returns "john@example.com"

// From Spring Security Authentication  
User user = (User) authentication.getPrincipal(); // Returns full User object
String userId = user.getId(); // Can access ID, roles, etc.
```

## What is AuditorAware?

**AuditorAware** is Spring Data JPA's mechanism for **automatic auditing** - it tracks who created/modified entities.

### The Generic Type `<String>`

```java
AuditorAware<String> // String = type of your user identifier
AuditorAware<Long>   // If user IDs are Long
AuditorAware<UUID>   // If user IDs are UUID
```

The type represents what you use to identify users in your audit fields.

### How It Works

**1. Entity with Audit Fields:**
```java
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @Id
    private String id;
    
    private String title;
    private String content;
    
    @CreatedBy
    private String createdBy;    // Automatically filled by AuditorAware
    
    @LastModifiedBy  
    private String lastModifiedBy; // Automatically filled by AuditorAware
    
    @CreatedDate
    private LocalDateTime createdDate;
    
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
```

**2. Automatic Population:**
```java
// When you save an entity:
Post post = new Post();
post.setTitle("My Post");
postRepository.save(post);

// Spring automatically calls your AuditorAware.getCurrentAuditor()
// and sets createdBy = "user123" (or whatever your method returns)
```

## What Happens Without AuditorAware?

### Alternative 1: Manual Auditing
```java
@Service
public class PostService {
    
    public void createPost(Post post, Authentication auth) {
        User user = (User) auth.getPrincipal();
        
        // Manual auditing - you handle it yourself
        post.setCreatedBy(user.getId());
        post.setCreatedDate(LocalDateTime.now());
        
        postRepository.save(post);
    }
}
```

### Alternative 2: Base Entity with Manual Tracking
```java
@MappedSuperclass
public abstract class AuditableEntity {
    @CreatedBy
    private String createdBy;
    
    @LastModifiedBy
    private String lastModifiedBy;
    
    // You manually set these in services
    public void setAuditInfo(String userId) {
        if (this.createdBy == null) {
            this.createdBy = userId;
        }
        this.lastModifiedBy = userId;
    }
}
```

### Alternative 3: No Auditing
```java
// Just don't track who created/modified entities
// Simpler but less traceable
```

## Complete AuditorAware Example

**Your Implementation (Excellent!):**
```java
@Component // Don't forget this annotation!
public class ApplicationAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if user is properly authenticated
        if (authentication == null ||
            !authentication.isAuthenticated() ||
            authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        User user = (User) authentication.getPrincipal();
        return Optional.ofNullable(user.getId());
    }
}
```

**Enhanced Version with Error Handling:**
```java
@Component
@Slf4j
public class ApplicationAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
                return Optional.empty();
            }

            if (authentication.getPrincipal() instanceof User user) {
                return Optional.ofNullable(user.getId());
            }
            
            log.warn("Unexpected principal type: {}", authentication.getPrincipal().getClass());
            return Optional.empty();
            
        } catch (Exception e) {
            log.error("Error getting current auditor", e);
            return Optional.empty();
        }
    }
}
```

## Benefits of AuditorAware

**✅ Automatic:** No need to manually set audit fields  
**✅ Consistent:** Same auditing logic across all entities  
**✅ DRY:** Write once, use everywhere  
**✅ Reliable:** Can't forget to set audit info  
**✅ Thread-safe:** Uses SecurityContextHolder properly

**Example Usage:**
```java
// All of these automatically get audit info:
postRepository.save(new Post(...));           // createdBy set automatically
userRepository.save(existingUser);            // lastModifiedBy set automatically  
commentRepository.saveAll(comments);          // All get proper audit info
```
## Java Annotations
Java annotations are a mechanism for adding metadata information to our source code (Program).

Annotations provide an alternative to the use of XML descriptors. 

Also, we are able to attach them to packages, classes, interfaces, methods, and fields, annotations by themselves have no effect on the execution of a source code (Program). 

Create Custom Annotation

We are going to create three custom annotations with the goal of serializing an object into a JSON string. that is -

    Class Level Annotation
    Field Level Annotation
    Method Level Annotation

***
## Why Validation on Request Classes Only?

**Request classes (DTOs) are the perfect place for validation** because they represent the **input boundary** of your application. Here's why:

### 1. **Single Responsibility**
```java
// Request DTO: Handles input validation and data transfer
@Getter @Setter
public class RegistrationRequest {
    @NotBlank @Email
    private String email;
}

// Entity: Handles business logic and persistence  
@Entity
public class User {
    private String email; // No validation annotations needed here
}
```

### 2. **Separation of Concerns**
```java
// Controller validates input
@PostMapping("/register")
public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest request) {
    // Request is already validated here
    authService.register(request); // Service focuses on business logic
}
```

### 3. **Different Validation Rules**
```java
// Registration: Requires strong password
public class RegistrationRequest {
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W).*$")
    private String password;
}

// Password Change: Different rules
public class ChangePasswordRequest {
    @Size(min = 6) // Maybe less strict for changes
    private String newPassword;
}

// User Entity: No password validation (business logic handles it)
@Entity
public class User {
    private String password; // Clean entity
}
```

## Comprehensive Guide to Custom Annotations

### 1. **Basic Structure**

Your custom annotation has all the required parts:

```java
@Target({ElementType.FIELD, ElementType.PARAMETER})  // Where it can be used
@Retention(RetentionPolicy.RUNTIME)                  // When it's processed  
@Constraint(validatedBy = EmailDomainValidator.class) // Who validates it
public @interface NonDisposableEmail {
    
    // Required by Bean Validation specification
    String message() default "Disposable email addresses are not allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

### 2. **Annotation Elements Explained**

#### **@Target** - Where can this annotation be used?
```java
@Target({ElementType.FIELD})           // Only on fields
@Target({ElementType.METHOD})          // Only on methods  
@Target({ElementType.PARAMETER})       // Only on parameters
@Target({ElementType.TYPE})            // Only on classes
@Target({ElementType.FIELD, ElementType.METHOD}) // Multiple targets
```

#### **@Retention** - When is annotation available?
```java
@Retention(RetentionPolicy.SOURCE)     // Compile-time only (like @Override)
@Retention(RetentionPolicy.CLASS)      // In .class files but not runtime
@Retention(RetentionPolicy.RUNTIME)    // Available at runtime (for validation)
```

#### **Required Methods (Bean Validation Spec):**
```java
String message() default "Default error message";    // Error message
Class<?>[] groups() default {};                      // Validation groups
Class<? extends Payload>[] payload() default {};    // Metadata payload
```

### 3. **Advanced Custom Annotations**

#### **Class-Level Validation** (Password Confirmation)
```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
public @interface PasswordMatch {
    String message() default "Passwords do not match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    String password();        // Field name for password
    String confirmPassword(); // Field name for confirmation
}

// Validator
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {
    
    private String passwordField;
    private String confirmPasswordField;
    
    @Override
    public void initialize(PasswordMatch annotation) {
        this.passwordField = annotation.password();
        this.confirmPasswordField = annotation.confirmPassword();
    }
    
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        try {
            Object password = getFieldValue(obj, passwordField);
            Object confirmPassword = getFieldValue(obj, confirmPasswordField);
            
            return password != null && password.equals(confirmPassword);
        } catch (Exception e) {
            return false;
        }
    }
    
    private Object getFieldValue(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }
}

// Usage
@PasswordMatch(password = "password", confirmPassword = "confirmPassword")
public class RegistrationRequest {
    private String password;
    private String confirmPassword;
}
```

#### **Parameterized Annotations**
```java
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinAgeValidator.class)
public @interface MinAge {
    String message() default "Must be at least {value} years old";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    int value(); // Parameter for minimum age
}

// Validator
public class MinAgeValidator implements ConstraintValidator<MinAge, LocalDate> {
    
    private int minAge;
    
    @Override
    public void initialize(MinAge annotation) {
        this.minAge = annotation.value();
    }
    
    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        if (birthDate == null) return true;
        
        return Period.between(birthDate, LocalDate.now()).getYears() >= minAge;
    }
}

// Usage
public class UserRegistrationRequest {
    @MinAge(18)
    private LocalDate birthDate;
}
```

### 4. **Composite Annotations**
```java
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@NotBlank(message = "Email is required")
@Email(message = "Invalid email format")
@NonDisposableEmail(message = "Disposable emails not allowed")
public @interface ValidBusinessEmail {
    String message() default "Invalid business email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

// Usage - one annotation, multiple validations
public class ContactRequest {
    @ValidBusinessEmail
    private String email; // Automatically applies all three validations
}
```

### 5. **Conditional Validation**
```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConditionalValidator.class)
public @interface ConditionalValidation {
    String message() default "Conditional validation failed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    String condition();     // Field that determines if validation applies
    String conditionalField(); // Field to validate conditionally
}

// Validator
public class ConditionalValidator implements ConstraintValidator<ConditionalValidation, Object> {
    
    private String conditionField;
    private String conditionalField;
    
    @Override
    public void initialize(ConditionalValidation annotation) {
        this.conditionField = annotation.condition();
        this.conditionalField = annotation.conditionalField();
    }
    
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        try {
            Object conditionValue = getFieldValue(obj, conditionField);
            Object conditionalValue = getFieldValue(obj, conditionalField);
            
            // Only validate if condition is true
            if (Boolean.TRUE.equals(conditionValue)) {
                return conditionalValue != null && !conditionalValue.toString().trim().isEmpty();
            }
            
            return true; // Validation passes if condition is false
        } catch (Exception e) {
            return false;
        }
    }
}

// Usage
@ConditionalValidation(condition = "requiresApproval", conditionalField = "managerEmail")
public class LeaveRequest {
    private boolean requiresApproval;
    private String managerEmail; // Only required if requiresApproval is true
}
```

### 6. **Multiple Validators for One Annotation**
```java
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {
    PhoneNumberValidator.class,    // For String
    PhoneObjectValidator.class     // For custom Phone object
})
public @interface ValidPhoneNumber {
    String message() default "Invalid phone number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

### 7. **Issues in Your Validator**

Your `EmailDomainValidator` has a potential bug:

```java
// Current (problematic):
final String domain = email.substring(atIndex, dotIndex);

// Better (handles emails without dots or multiple dots):
public boolean isValid(String email, ConstraintValidatorContext context) {
    if (email == null || !email.contains("@")) {
        return true; // Let @Email handle format validation
    }
    
    String domain = email.substring(email.indexOf("@") + 1).toLowerCase();
    
    // Extract root domain (handle subdomains)
    String[] parts = domain.split("\\.");
    if (parts.length >= 2) {
        domain = parts[parts.length - 2] + "." + parts[parts.length - 1];
    }
    
    return !blocked.contains(domain);
}
```
