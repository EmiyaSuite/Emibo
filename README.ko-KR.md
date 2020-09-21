# Emibo
Emibo 공개 소스 코드.

Emibo는 Kotlin으로 작성하여 Ktor를 이용하였으며, HTML DSL로 HTML을 렌더링하고, CSS DSL로 CSS 파일을 제공합니다.

## 필요한 것
 - Java와 Kotlin 언어의 이해도
 - JDK 11 혹은 최신버전 (11 미만은 테스트하지 않음)

## 환경
 - Gradle 6.6.1
 - Ktor 1.3.2
 - Dokka 1.4.0
 - Kotlin 1.3.70
### 개발 환경
 - Amazon Corretto 11 / AdoptOpenJDK 14 (OpenJ9)
 - Fedora 32
 - AdoptOpenJDK 14 (OpenJ9)
 - macOS 11
### 서비스 환경 (https://dodo.ij.rs/emibo) 
 - AdoptOpenJDK 11 (OpenJ9)
 - Red Hat Enterprise Linux 8.2
## 설치
처음 설치하는 경우 'run' 이라는 gradle 작업을 실행하면 됩니다.
```bash
$ ./gradlew run
```
## 문서
gradle 작업 dokkaHtml 을 실행하면 문서 파일이 생성됩니다.
```bash
$ ./gradlew dokkaHtml
```
문서 파일은 build/dokka에 위치하게 됩니다.

## 참고
덤프된 바이너리 파일, 카드 이미지 등은 포함되어 있지 않습니다. 소유하고 있는 NFC 카드를 덤프하여 리소스 폴더 안에 채워넣으면 됩니다.

