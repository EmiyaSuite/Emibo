# Emibo 
Public version of Emibo source codes.

Emibo based on Kotlin. using [Ktor](https://ktor.io), render HTML using HTML DSL, provide CSS using CSS DSL.

## Requirements
 - Knowledge to understand Java, Kotlin programming languages.
 - JDK 11 or newer (below 11 not tested)
 
## Environments
 - Gradle 6.6.1
 - Ktor 1.3.2
 - Dokka 1.4.0
 - Kotlin 1.3.70
 
### Development
 - Amazon Corretto 11 / AdoptOpenJDK 14 (OpenJ9)
 - Fedora 32
 - AdoptOpenJDK 14 (OpenJ9)
 - macOS 11
### Production (https://dodo.ij.rs/emibo)
 - AdoptOpenJDK 11 (OpenJ9)
 - Red Hat Enterprise Linux 8.2
 
## Installation
For first install, just run gradle job 'run'.
```bash
$ ./gradlew run
```

## Documentation
Generate documentation file using gradle job 'dokkaHtml'. 
```bash
$ ./gradlew dokkaHtml
```
Documentation files will be located in build/dokka

## Notes
Dumped bin files, card images are not included. You need to dump your NFC card, fill it into the resource folder.

