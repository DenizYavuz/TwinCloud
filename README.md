# Twin Cloud
Secure Cloud Sharing Without Explicit Key Management


TwinCloud is a client-side solution providing a secure cloud system to users without compromising the usability of cloud sharing. TwinCloud brings a novel solution to the complex key exchange problem and provides a simple and practical approach to store and share files by hiding all the cryptographic and key-distribution operations from users. Serving as a gateway, TwinCloud stores the encryption keys and encrypted files in separate clouds which ease the secure sharing without a need for trust to either of the cloud service providers with the assumption that they do not collude with each other.


You can find the paper of this research in [here](https://arxiv.org/abs/1606.04705). Paper of this study was presented in the 2nd IEEE Workshop on Security and Privacy in the Cloud (SPC 2016).

## Getting Started

### Prerequisites

You may need to install Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files.
* [Unlimited Strength Jurisdiction Policy Files](http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html)

Mozilla Firefox is needed to debug the code for login and signup.
 
## Running

You can build the project using [Gradle](https://gradle.org/).

```
gradle build
```