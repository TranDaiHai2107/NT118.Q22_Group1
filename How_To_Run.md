# AI-Powered Healthcare App

Ứng dụng chăm sóc sức khỏe full-stack gồm **Android Native App** (Java) và **Spring Boot REST API** (Java), sử dụng **Oracle Database**.

---

## Mục lục

1. [Tổng quan kiến trúc](#tổng-quan-kiến-trúc)
2. [Cấu trúc dự án](#cấu-trúc-dự-án)
3. [Yêu cầu cài đặt](#yêu-cầu-cài-đặt)
4. [Hướng dẫn cài đặt & chạy](#hướng-dẫn-cài-đặt--chạy)
5. [API Endpoints](#api-endpoints)
6. [Tài khoản mặc định](#tài-khoản-mặc-định)
7. [Màn hình ứng dụng](#màn-hình-ứng-dụng)

---

## Tổng quan kiến trúc

```
┌─────────────────┐       HTTP/REST        ┌──────────────────┐        JDBC         ┌─────────────────┐
│                 │  ──────────────────►   │                  │  ─────────────────► │                 │
│  Android App    │    JSON (Retrofit)     │  Spring Boot API │    JPA/Hibernate    │  Oracle DB XE   │
│  (Java + XML)   │  ◄──────────────────   │  (Port 8080)     │  ◄───────────────── │  (Port 1521)    │
│                 │                        │                  │                     │                 │
└─────────────────┘                        └──────────────────┘                     └─────────────────┘
     Emulator:                                  localhost                              Docker Container
  10.0.2.2:8080                                                                     Service: XEPDB1
```

---

## Cấu trúc dự án

```
AI-Powered Healthcare App Design/
│
├── backend/                              ← Spring Boot REST API
│   ├── pom.xml                           ← Maven dependencies (Spring Boot 3.2, Oracle JDBC)
│   ├── mvnw.cmd                          ← Maven Wrapper (Windows)
│   ├── setup-oracle.sql                  ← Script tạo user Oracle
│   ├── .mvn/wrapper/
│   │   └── maven-wrapper.properties
│   └── src/main/
│       ├── java/com/healthcare/api/
│       │   ├── HealthcareApplication.java          ← Main entry point
│       │   ├── config/
│       │   │   └── CorsConfig.java                 ← CORS cho Android
│       │   ├── controller/                          ← 9 REST Controllers
│       │   │   ├── AuthController.java             ←   POST /auth/login, /auth/register
│       │   │   ├── DoctorController.java           ←   GET /doctors, /doctors/{id}
│       │   │   ├── HospitalController.java         ←   GET /hospitals, /hospitals/{id}
│       │   │   ├── AppointmentController.java      ←   GET/POST /appointments
│       │   │   ├── MedicalRecordController.java    ←   GET /medical-records
│       │   │   ├── NotificationController.java     ←   GET/PUT /notifications
│       │   │   ├── InsuranceController.java        ←   GET /insurance
│       │   │   ├── PaymentController.java          ←   POST /payments
│       │   │   └── ProfileController.java          ←   GET/PUT /profile
│       │   ├── dto/                                 ← 5 Data Transfer Objects
│       │   │   ├── ApiResponse.java
│       │   │   ├── LoginRequest.java
│       │   │   ├── RegisterRequest.java
│       │   │   ├── BookingRequest.java
│       │   │   └── PaymentRequest.java
│       │   ├── model/                               ← 7 JPA Entity classes
│       │   │   ├── User.java
│       │   │   ├── Doctor.java
│       │   │   ├── Hospital.java
│       │   │   ├── Appointment.java
│       │   │   ├── MedicalRecord.java
│       │   │   ├── Notification.java
│       │   │   └── Insurance.java
│       │   ├── repository/                          ← 7 JPA Repository interfaces
│       │   └── service/                             ← 7 Service classes
│       └── resources/
│           ├── application.properties               ← Oracle DB config, port 8080
│           └── data.sql                             ← Seed data (Oracle MERGE INTO syntax)
│
├── android/                              ← Android Native App
│   ├── build.gradle                      ← Root build (AGP 8.2.2)
│   ├── app/build.gradle                  ← App build (compileSdk 34, Java 17)
│   ├── settings.gradle                   ← Project: HealthcareApp
│   ├── gradle.properties                 ← JVM args, AndroidX
│   ├── local.properties                  ← Android SDK path (tự sinh)
│   ├── gradlew.bat                       ← Gradle Wrapper (Windows)
│   ├── gradlew                           ← Gradle Wrapper (Linux/Mac)
│   ├── gradle/wrapper/
│   │   ├── gradle-wrapper.jar
│   │   └── gradle-wrapper.properties     ← Gradle 8.5
│   └── app/src/main/
│       ├── AndroidManifest.xml            ← 18 Activities, permissions
│       ├── java/com/healthcare/app/
│       │   ├── activity/                  ← 18 Activity classes
│       │   │   ├── OnboardingActivity.java        ← Màn hình giới thiệu (LAUNCHER)
│       │   │   ├── LoginActivity.java             ← Đăng nhập
│       │   │   ├── RegisterActivity.java          ← Đăng ký
│       │   │   ├── ForgotPasswordActivity.java    ← Quên mật khẩu
│       │   │   ├── ResetPasswordActivity.java     ← Đặt lại mật khẩu
│       │   │   ├── HomeActivity.java              ← Trang chủ
│       │   │   ├── SearchActivity.java            ← Tìm kiếm bác sĩ/bệnh viện
│       │   │   ├── DoctorDetailActivity.java      ← Chi tiết bác sĩ
│       │   │   ├── HospitalDetailActivity.java    ← Chi tiết bệnh viện
│       │   │   ├── BookingActivity.java           ← Đặt lịch khám
│       │   │   ├── PaymentActivity.java           ← Thanh toán
│       │   │   ├── ConfirmationActivity.java      ← Xác nhận đặt lịch
│       │   │   ├── AppointmentsActivity.java      ← Danh sách lịch hẹn
│       │   │   ├── CheckInActivity.java           ← Check-in + QR Code
│       │   │   ├── MedicalRecordsActivity.java    ← Hồ sơ y tế
│       │   │   ├── NotificationsActivity.java     ← Thông báo
│       │   │   ├── InsuranceActivity.java         ← Bảo hiểm
│       │   │   └── ProfileActivity.java           ← Hồ sơ cá nhân
│       │   ├── adapter/                   ← 5 RecyclerView Adapters
│       │   │   ├── DoctorAdapter.java
│       │   │   ├── HospitalAdapter.java
│       │   │   ├── AppointmentAdapter.java
│       │   │   ├── RecordAdapter.java
│       │   │   └── NotificationAdapter.java
│       │   ├── api/                       ← Retrofit HTTP Client
│       │   │   ├── ApiClient.java         ← BASE_URL = http://10.0.2.2:8080/api/
│       │   │   └── ApiService.java        ← Định nghĩa tất cả API calls
│       │   └── model/                     ← 8 POJO Model classes
│       │       ├── ApiResponse.java
│       │       ├── User.java
│       │       ├── Doctor.java
│       │       ├── Hospital.java
│       │       ├── Appointment.java
│       │       ├── MedicalRecord.java
│       │       ├── Notification.java
│       │       └── Insurance.java
│       └── res/
│           ├── layout/                    ← 24 XML layouts (18 activity + 6 item)
│           ├── drawable/                  ← 33 drawable XMLs (gradients, shapes, icons)
│           ├── menu/
│           │   └── bottom_nav_menu.xml    ← Bottom Navigation (5 tabs)
│           ├── mipmap-*/                  ← App launcher icons (adaptive)
│           └── values/
│               ├── colors.xml             ← Bảng màu pastel
│               ├── themes.xml             ← Material Design 3 theme
│               └── strings.xml            ← Tất cả strings
│
└── README.md                              ← File này
```

---

## Yêu cầu cài đặt

### Phần mềm bắt buộc

| # | Phần mềm | Phiên bản | Mục đích | Link tải |
|---|----------|-----------|----------|----------|
| 1 | **Java JDK** | 17+ | Chạy backend + build Android | [Adoptium](https://adoptium.net/) |
| 2 | **Docker Desktop** | Latest | Chạy Oracle Database | [Docker](https://www.docker.com/products/docker-desktop/) |
| 3 | **Maven** | 3.9+ | Build backend Spring Boot | [Maven](https://maven.apache.org/download.cgi) |
| 4 | **Android Studio** | Latest | Build + chạy app Android | [Android Studio](https://developer.android.com/studio) |

### Kiểm tra đã cài đặt

```bash
java -version          # Java 17+
mvn -version           # Maven 3.9+  (hoặc dùng mvnw.cmd trong backend/)
docker --version       # Docker Desktop
```

---

## Hướng dẫn cài đặt & chạy

### Thứ tự thực hiện

```
Bước 1: Oracle DB  →  Bước 2: Backend API  →  Bước 3: Android App
   (Docker)              (Spring Boot)           (Android Studio)
```

---

### Bước 1: Khởi động Oracle Database (Docker)

**1.1. Pull và chạy Oracle XE container:**

```bash
docker run -d --name oracle-xe \
  -p 1521:1521 \
  -e ORACLE_PASSWORD=Oracle123 \
  container-registry.oracle.com/database/express:21.3.0-xe
```

> Lần đầu chạy mất khoảng 5-10 phút để khởi tạo database.

**1.2. Kiểm tra Oracle đã sẵn sàng:**

```bash
docker logs oracle-xe --tail 20
```

Đợi đến khi thấy: `DATABASE IS READY TO USE!`

**1.3. Tạo user `healthcare` trong Oracle:**

```bash
docker exec -i oracle-xe sqlplus system/Oracle123@//localhost:1521/XEPDB1 < backend/setup-oracle.sql
```

Hoặc chạy thủ công:

```bash
docker exec -it oracle-xe sqlplus system/Oracle123@//localhost:1521/XEPDB1
```

Rồi paste nội dung file `backend/setup-oracle.sql`:

```sql
ALTER SESSION SET "_ORACLE_SCRIPT"=true;

CREATE USER healthcare IDENTIFIED BY healthcare123
DEFAULT TABLESPACE USERS
TEMPORARY TABLESPACE TEMP
QUOTA UNLIMITED ON USERS;

GRANT CONNECT, RESOURCE TO healthcare;
GRANT CREATE SESSION TO healthcare;
GRANT CREATE TABLE TO healthcare;
GRANT CREATE SEQUENCE TO healthcare;
GRANT CREATE VIEW TO healthcare;

EXIT;
```

**1.4. Xác nhận kết nối:**

```bash
docker exec -it oracle-xe sqlplus healthcare/healthcare123@//localhost:1521/XEPDB1
```

Nếu vào được SQL> prompt → thành công.

---

### Bước 2: Chạy Backend Spring Boot

**2.1. Mở terminal, di chuyển vào thư mục backend:**

```bash
cd "c:\Users\ASUS\Downloads\AI-Powered Healthcare App Design\backend"
```

**2.2. Chạy Spring Boot:**

Nếu đã cài Maven global:
```bash
mvn spring-boot:run
```

Hoặc dùng Maven Wrapper (không cần cài Maven):
```bash
mvnw.cmd spring-boot:run
```

> Lần đầu chạy Maven sẽ tải dependencies (~50MB), mất khoảng 2-3 phút.

**2.3. Xác nhận backend chạy thành công:**

Khi thấy log:
```
Started HealthcareApplication in X seconds
```

Mở trình duyệt, truy cập:
```
http://localhost:8080/api/doctors
```

Nếu thấy JSON trả về danh sách bác sĩ → Backend OK.

**2.4. Cấu hình Oracle (nếu cần thay đổi):**

File: `backend/src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/XEPDB1
spring.datasource.username=healthcare
spring.datasource.password=healthcare123
```

> **Lưu ý:** Giữ backend terminal mở, KHÔNG tắt khi chạy app Android.

---

### Bước 3: Chạy Android App

**3.1. Mở Android Studio**

**3.2. Import project:**

- Chọn **File → Open**
- Trỏ đến thư mục:
  ```
  c:\Users\ASUS\Downloads\AI-Powered Healthcare App Design\android
  ```
- Click **OK**
- Đợi **Gradle Sync** hoàn tất (lần đầu 2-5 phút, tải dependencies)

**3.3. Tạo Android Emulator (nếu chưa có):**

1. Vào **Tools → Device Manager**
2. Click **Create Virtual Device**
3. Chọn thiết bị: **Pixel 6** → **Next**
4. Chọn system image: **API 34 (Android 14 "UpsideDownCake")**
   - Nếu chưa tải → click **Download** → đợi xong → **Next**
5. **Finish**

**3.4. Chạy app:**

1. Trên toolbar, chọn **emulator** vừa tạo ở dropdown thiết bị
2. Đảm bảo module **app** được chọn
3. Nhấn **Run ▶** (nút tam giác xanh) hoặc **Shift + F10**
4. Đợi build + cài lên emulator (lần đầu 1-3 phút)
5. App sẽ tự mở trên emulator

**3.5. Cấu hình kết nối (nếu cần):**

File: `android/app/src/main/java/com/healthcare/app/api/ApiClient.java`

```java
// Emulator → dùng 10.0.2.2 (đã cấu hình sẵn)
private static final String BASE_URL = "http://10.0.2.2:8080/api/";

// Điện thoại thật → đổi sang IP máy tính (ví dụ)
// private static final String BASE_URL = "http://192.168.1.100:8080/api/";
```

---

## API Endpoints

| Method | Endpoint | Mô tả |
|--------|----------|-------|
| `POST` | `/api/auth/login` | Đăng nhập |
| `POST` | `/api/auth/register` | Đăng ký tài khoản |
| `GET` | `/api/doctors` | Danh sách bác sĩ |
| `GET` | `/api/doctors/{id}` | Chi tiết bác sĩ |
| `GET` | `/api/doctors/search?query=` | Tìm kiếm bác sĩ |
| `GET` | `/api/hospitals` | Danh sách bệnh viện |
| `GET` | `/api/hospitals/{id}` | Chi tiết bệnh viện |
| `GET` | `/api/hospitals/search?query=` | Tìm kiếm bệnh viện |
| `GET` | `/api/appointments/user/{userId}` | Lịch hẹn của user |
| `POST` | `/api/appointments` | Tạo lịch hẹn mới |
| `PUT` | `/api/appointments/{id}/cancel` | Hủy lịch hẹn |
| `PUT` | `/api/appointments/{id}/checkin` | Check-in lịch hẹn |
| `GET` | `/api/medical-records/user/{userId}` | Hồ sơ y tế |
| `GET` | `/api/notifications/user/{userId}` | Thông báo |
| `PUT` | `/api/notifications/{id}/read` | Đánh dấu đã đọc |
| `GET` | `/api/insurance/user/{userId}` | Thông tin bảo hiểm |
| `GET` | `/api/profile/{userId}` | Hồ sơ cá nhân |
| `PUT` | `/api/profile/{userId}` | Cập nhật hồ sơ |
| `POST` | `/api/payments/process` | Xử lý thanh toán |

---

## Tài khoản mặc định

| Thông tin | Giá trị |
|-----------|---------|
| **Email** | `sarah.williams@email.com` |
| **Password** | `password123` |
| **Patient ID** | `PT-123456` |

---

## Màn hình ứng dụng

| # | Màn hình | Activity | Mô tả |
|---|----------|----------|-------|
| 1 | Onboarding | `OnboardingActivity` | 3 slide giới thiệu app |
| 2 | Login | `LoginActivity` | Đăng nhập email/password |
| 3 | Register | `RegisterActivity` | Đăng ký tài khoản mới |
| 4 | Forgot Password | `ForgotPasswordActivity` | Nhập email khôi phục |
| 5 | Reset Password | `ResetPasswordActivity` | Đặt mật khẩu mới |
| 6 | Home | `HomeActivity` | Trang chủ: quick actions, bác sĩ, bệnh viện |
| 7 | Search | `SearchActivity` | Tìm kiếm bác sĩ & bệnh viện |
| 8 | Doctor Detail | `DoctorDetailActivity` | Thông tin chi tiết bác sĩ |
| 9 | Hospital Detail | `HospitalDetailActivity` | Thông tin chi tiết bệnh viện |
| 10 | Booking | `BookingActivity` | Đặt lịch khám (chọn ngày, giờ, dịch vụ) |
| 11 | Payment | `PaymentActivity` | Chọn phương thức thanh toán |
| 12 | Confirmation | `ConfirmationActivity` | Xác nhận đặt lịch thành công |
| 13 | Appointments | `AppointmentsActivity` | Danh sách lịch hẹn (upcoming/completed/cancelled) |
| 14 | Check-In | `CheckInActivity` | Check-in bằng QR Code |
| 15 | Medical Records | `MedicalRecordsActivity` | Hồ sơ y tế (đơn thuốc, xét nghiệm, ghi chú) |
| 16 | Notifications | `NotificationsActivity` | Thông báo nhắc nhở |
| 17 | Insurance | `InsuranceActivity` | Thông tin bảo hiểm y tế |
| 18 | Profile | `ProfileActivity` | Hồ sơ cá nhân, đăng xuất |

---

## Tech Stack

| Layer | Công nghệ |
|-------|-----------|
| **Android Frontend** | Java 17, Android SDK 34, Material Design 3, ViewBinding, Retrofit 2, Glide 4, ZXing |
| **Backend API** | Java 17, Spring Boot 3.2.3, Spring Data JPA, Hibernate 6 |
| **Database** | Oracle Database XE 21c (Docker) |
| **Build Tools** | Gradle 8.5 (Android), Maven 3.9+ (Backend) |

---

## Xử lý sự cố thường gặp

| Vấn đề | Giải pháp |
|--------|-----------|
| Gradle sync lỗi SDK | Android Studio sẽ hiện link **Install missing SDK** → click để cài tự động |
| App không kết nối được backend | Đảm bảo backend đang chạy trên port 8080 trước khi mở app |
| `10.0.2.2` không hoạt động | Chỉ dùng cho **Android Emulator**. Điện thoại thật → đổi sang IP LAN |
| Oracle connection refused | Kiểm tra Docker container đang chạy: `docker ps` |
| `mvn` not recognized | Dùng `mvnw.cmd` thay vì `mvn`, hoặc cài Maven global |
| Backend lỗi `ORA-01017` | Kiểm tra user/password Oracle: `healthcare` / `healthcare123` |
