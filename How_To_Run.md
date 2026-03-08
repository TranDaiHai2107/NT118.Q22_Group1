# AI-Powered Healthcare App

Ứng dụng chăm sóc sức khỏe **Android Native** (Java) sử dụng **Firebase** làm backend (Authentication, Cloud Firestore, Cloud Storage).

---

## Mục lục

1. [Tổng quan kiến trúc](#tổng-quan-kiến-trúc)
2. [Cấu trúc dự án](#cấu-trúc-dự-án)
3. [Yêu cầu cài đặt](#yêu-cầu-cài-đặt)
4. [Hướng dẫn cài đặt & chạy](#hướng-dẫn-cài-đặt--chạy)
5. [Firestore Collections](#firestore-collections)
6. [Tài khoản test](#tài-khoản-test)
7. [Màn hình ứng dụng](#màn-hình-ứng-dụng)
8. [Tech Stack](#tech-stack)
9. [Xử lý sự cố thường gặp](#xử-lý-sự-cố-thường-gặp)

---

## Tổng quan kiến trúc

```
┌─────────────────┐         Firebase SDK          ┌──────────────────────────┐
│                 │  ──────────────────────────►  │                          │
│  Android App    │    Firebase Authentication    │   Firebase Cloud         │
│  (Java + XML)   │    Cloud Firestore            │   (Google Server)        │
│                 │    Cloud Storage              │                          │
│                 │  ◄──────────────────────────  │  • Authentication        │
└─────────────────┘     Realtime Sync             │  • Firestore Database    │
   Emulator hoặc                                  │  • Cloud Storage         │
   Điện thoại thật                                └──────────────────────────┘
```

> Không cần chạy backend server riêng. Firebase SDK trong app kết nối trực tiếp đến cloud.

---

## Cấu trúc dự án

```
AI-Powered Healthcare App Design/
│
├── android/                              ← Android Native App
│   ├── build.gradle                      ← Root build (AGP 8.2.2, Google Services plugin)
│   ├── app/build.gradle                  ← App build (compileSdk 34, Java 17, Firebase BOM)
│   ├── app/google-services.json          ← Firebase config (TẢI TỪ FIREBASE CONSOLE)
│   ├── settings.gradle                   ← Project: HealthcareApp
│   ├── gradle.properties                 ← JVM args, AndroidX
│   ├── local.properties                  ← Android SDK path (tự sinh bởi Android Studio)
│   ├── gradlew.bat                       ← Gradle Wrapper (Windows)
│   ├── gradlew                           ← Gradle Wrapper (Linux/Mac)
│   ├── gradle/wrapper/
│   │   ├── gradle-wrapper.jar
│   │   └── gradle-wrapper.properties     ← Gradle 8.5
│   └── app/src/main/
│       ├── AndroidManifest.xml            ← 18 Activities, INTERNET permission
│       ├── java/com/healthcare/app/
│       │   ├── activity/                  ← 18 Activity classes
│       │   │   ├── OnboardingActivity.java        ← Màn hình giới thiệu (LAUNCHER)
│       │   │   ├── LoginActivity.java             ← Đăng nhập (Firebase Auth)
│       │   │   ├── RegisterActivity.java          ← Đăng ký (Firebase Auth)
│       │   │   ├── ForgotPasswordActivity.java    ← Quên mật khẩu (Firebase Auth)
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
│       │   └── model/                     ← 7 Firestore Model classes
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
│           ├── mipmap-*/                  ← App launcher icons
│           └── values/
│               ├── colors.xml             ← Bảng màu pastel
│               ├── themes.xml             ← Material Design 3 theme
│               └── strings.xml
│
├── firebase-seed/                        ← Tool seed data vào Firestore
│   ├── seed-data.html                    ← Mở bằng trình duyệt để seed data (KHÔNG CẦN cài Node.js)
│   ├── seed-firestore.js                 ← Script Node.js (tùy chọn, cần cài Node.js)
│   └── package.json                      ← Dependencies cho script Node.js
│
├── How_To_Run.md                         ← File này
└── README.md
```

---

## Yêu cầu cài đặt

### Phần mềm bắt buộc

| # | Phần mềm | Phiên bản | Mục đích | Link tải |
|---|----------|-----------|----------|----------|
| 1 | **Java JDK** | 17+ | Build Android app | [Adoptium](https://adoptium.net/) |
| 2 | **Android Studio** | Latest | Build + chạy app Android | [Android Studio](https://developer.android.com/studio) |
| 3 | **Trình duyệt web** | Chrome/Edge/Firefox | Seed data vào Firestore | Đã có sẵn |

> **Không cần** cài Docker, Oracle, MySQL, Maven, hoặc bất kỳ backend server nào. Firebase chạy trên cloud.

### Kiểm tra đã cài đặt

```bash
java -version          # Java 17+
```

---

## Hướng dẫn cài đặt & chạy

### Tổng quan các bước

```
Bước 1: Tạo Firebase Project
       ↓
Bước 2: Tải google-services.json
       ↓
Bước 3: Cấu hình Firestore Rules
       ↓
Bước 4: Tạo tài khoản user trong Firebase Auth
       ↓
Bước 5: Seed data vào Firestore
       ↓
Bước 6: Mở Android Studio & chạy app
```

---

### Bước 1: Tạo Firebase Project

1. Truy cập [Firebase Console](https://console.firebase.google.com/)
2. Đăng nhập bằng tài khoản Google
3. Nhấn **"Create a project"** (hoặc "Add project")
4. Đặt tên project, ví dụ: `healthcare-app`
5. Tắt Google Analytics (không bắt buộc) → **Create Project**
6. Đợi tạo xong → nhấn **Continue**

#### 1a. Bật Authentication

7. Trong Firebase Console, menu bên trái chọn **Build → Authentication**
8. Nhấn **Get started**
9. Tab **Sign-in method** → nhấn vào **Email/Password**
10. Bật **Enable** → nhấn **Save**

#### 1b. Tạo Firestore Database

11. Menu bên trái chọn **Build → Firestore Database**
12. Nhấn **Create database**
13. Chọn location gần bạn (ví dụ: `asia-southeast1` cho Việt Nam)
14. Chọn **Start in test mode** → nhấn **Create**

---

### Bước 2: Tải google-services.json

1. Trong Firebase Console, nhấn **⚙ Settings** (bánh răng) → **Project settings**
2. Kéo xuống phần **"Your apps"** → nhấn icon **Android** (</>) để thêm app
3. Nhập **Android package name**: `com.healthcare.app`
4. Nhấn **Register app**
5. Nhấn **Download google-services.json**
6. **Copy file vừa tải vào thư mục** `android/app/` (thay thế file cũ nếu có)
7. Nhấn **Next** → **Next** → **Continue to console**

> **QUAN TRỌNG:** File `google-services.json` phải nằm đúng tại `android/app/google-services.json`

---

### Bước 3: Cấu hình Firestore Rules

1. Trong Firebase Console → **Firestore Database** → tab **Rules**
2. Thay toàn bộ nội dung bằng:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if true;
    }
  }
}
```

3. Nhấn **Publish**

> Lưu ý: Rules này cho phép đọc/ghi tự do, chỉ phù hợp cho development/testing.

---

### Bước 4: Tạo tài khoản user trong Firebase Auth

1. Trong Firebase Console → **Authentication** → tab **Users**
2. Nhấn **Add user**
3. Nhập:
   - **Email**: `sarah.williams@email.com` (hoặc email bất kỳ)
   - **Password**: `password123` (hoặc password bất kỳ, tối thiểu 6 ký tự)
4. Nhấn **Add user**
5. **Ghi lại User UID** hiển thị trong bảng (chuỗi ký tự dài, ví dụ: `VixcTILbf3bqBShZu16nKQRplUf1`)

> UID này sẽ dùng ở Bước 5 để gắn data cho đúng user.

---

### Bước 5: Seed data vào Firestore

#### Cách 1: Dùng file HTML (Khuyến nghị - Không cần cài thêm gì)

1. Mở file `firebase-seed/seed-data.html` bằng **text editor** (Notepad, VS Code...)
2. Tìm dòng chứa `const firebaseConfig = {` và cập nhật thông tin từ Firebase Console:
   - Vào **⚙ Project Settings → General → Your apps** → copy config
   - Thay các giá trị `apiKey`, `authDomain`, `projectId`, `storageBucket`, `messagingSenderId`, `appId`
3. Tìm dòng `const USER_UID = "..."` và thay bằng **UID thật** từ Bước 4
4. Lưu file
5. Mở file `seed-data.html` bằng **trình duyệt** (double-click hoặc kéo vào Chrome)
6. Nhấn nút **"Seed All Data"**
7. Đợi đến khi log hiện: `✅ ALL DATA SEEDED SUCCESSFULLY!`
8. Kiểm tra: vào **Firebase Console → Firestore Database → Data** → phải thấy các collection: `hospitals`, `doctors`, `users`, `appointments`, `medical_records`, `notifications`, `insurance`

#### Cách 2: Dùng Node.js (Tùy chọn - Cần cài Node.js)

1. Cài [Node.js](https://nodejs.org/) (LTS)
2. Trong Firebase Console → **⚙ Settings → Service accounts** → **Generate new private key** → tải file JSON
3. Đổi tên thành `serviceAccountKey.json` và đặt vào thư mục `firebase-seed/`
4. Mở file `firebase-seed/seed-firestore.js`, tìm `REPLACE_WITH_ACTUAL_UID` và thay bằng UID từ Bước 4
5. Mở terminal:

```bash
cd firebase-seed
npm install
node seed-firestore.js
```

---

### Bước 6: Mở Android Studio & chạy app

**6.1. Import project:**

1. Mở **Android Studio**
2. Chọn **File → Open**
3. Trỏ đến thư mục `android/` của project
4. Nhấn **OK**
5. Đợi **Gradle Sync** hoàn tất (lần đầu 2-5 phút để tải dependencies)

**6.2. Tạo Android Emulator (nếu chưa có):**

1. Vào **Tools → Device Manager**
2. Nhấn **Create Virtual Device**
3. Chọn thiết bị: **Pixel 6** → **Next**
4. Chọn system image: **API 34 (Android 14 "UpsideDownCake")**
   - Nếu chưa tải → click **Download** → đợi xong → **Next**
5. Nhấn **Finish**

**6.3. Chạy app:**

1. Trên toolbar, chọn **emulator** vừa tạo ở dropdown thiết bị
2. Đảm bảo module **app** được chọn
3. Nhấn **Run ▶** (nút tam giác xanh) hoặc **Shift + F10**
4. Đợi build + cài lên emulator (lần đầu 1-3 phút)
5. App sẽ tự mở trên emulator

**6.4. Đăng nhập:**

- Dùng **email** và **password** đã tạo ở Bước 4

---

## Firestore Collections

Sau khi seed data, Firestore sẽ có các collection sau:

| Collection | Mô tả | Số documents |
|------------|--------|--------------|
| `hospitals` | Danh sách bệnh viện | 4 |
| `doctors` | Danh sách bác sĩ | 5 |
| `users` | Thông tin user profile | 1 |
| `appointments` | Lịch hẹn khám bệnh | 4 |
| `medical_records` | Hồ sơ y tế | 4 |
| `notifications` | Thông báo | 5 |
| `insurance` | Bảo hiểm y tế | 1 |

### Cấu trúc document mẫu

**hospitals/{id}**
```json
{
  "name": "City Heart Hospital",
  "image": "https://...",
  "rating": 4.8,
  "reviewCount": 450,
  "specialties": "Cardiology,Emergency,Surgery",
  "distance": "1.2 km",
  "priceRange": "$$$",
  "address": "123 Medical Plaza, Downtown",
  "phone": "+1 (555) 123-4567",
  "operatingHours": "24/7"
}
```

**doctors/{id}**
```json
{
  "name": "Dr. Sarah Johnson",
  "specialization": "Cardiologist",
  "hospitalId": "hospital_1",
  "hospitalName": "City Heart Hospital",
  "rating": 4.9,
  "experience": 12,
  "consultationFee": 150,
  "image": "https://...",
  "nextAvailable": "Today, 3:00 PM",
  "availableSlots": "09:00 AM,10:00 AM,11:00 AM,02:00 PM,03:00 PM,04:00 PM"
}
```

**appointments/{auto-id}**
```json
{
  "userId": "<USER_UID>",
  "doctorId": "doctor_1",
  "doctorName": "Dr. Sarah Johnson",
  "doctorSpecialization": "Cardiologist",
  "hospital": "City Heart Hospital",
  "date": "2026-03-08",
  "time": "03:00 PM",
  "status": "upcoming",
  "type": "Specialist Consultation"
}
```

---

## Tài khoản test

| Thông tin | Giá trị |
|-----------|---------|
| **Email** | Email bạn tạo ở Bước 4 (ví dụ: `sarah.williams@email.com`) |
| **Password** | Password bạn tạo ở Bước 4 (ví dụ: `password123`) |

---

## Màn hình ứng dụng

| # | Màn hình | Activity | Mô tả |
|---|----------|----------|-------|
| 1 | Onboarding | `OnboardingActivity` | 3 slide giới thiệu app |
| 2 | Login | `LoginActivity` | Đăng nhập bằng Firebase Auth |
| 3 | Register | `RegisterActivity` | Đăng ký tài khoản mới |
| 4 | Forgot Password | `ForgotPasswordActivity` | Gửi email reset password |
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
| **Android Frontend** | Java 17, Android SDK 34, Material Design 3, ViewBinding, Glide 4, ZXing |
| **Authentication** | Firebase Authentication (Email/Password) |
| **Database** | Cloud Firestore (NoSQL, realtime sync) |
| **Storage** | Firebase Cloud Storage |
| **Build Tool** | Gradle 8.5, Android Gradle Plugin 8.2.2 |

---

## Xử lý sự cố thường gặp

| Vấn đề | Giải pháp |
|--------|-----------|
| Gradle sync lỗi SDK | Android Studio sẽ hiện link **Install missing SDK** → click để cài tự động |
| `google-services.json` not found | Đảm bảo file nằm đúng tại `android/app/google-services.json` |
| App crash khi đăng nhập | Kiểm tra đã bật **Email/Password** trong Firebase Auth → Sign-in method |
| Không thấy data trong app | Kiểm tra đã seed data (Bước 5) và UID trong seed data khớp với user đã tạo |
| `PERMISSION_DENIED` từ Firestore | Kiểm tra Firestore Rules đã đổi thành `allow read, write: if true` (Bước 3) |
| App hiện "No doctors found" | Vào Firebase Console → Firestore → kiểm tra collection `doctors` có data không |
| Build lỗi `JAVA_HOME` | Đảm bảo biến môi trường `JAVA_HOME` trỏ đến JDK 17+ |
| Emulator chạy chậm | Bật **Hardware acceleration** trong BIOS (Intel HAXM hoặc AMD Hypervisor) |
| Seed data lỗi trên trình duyệt | Mở Developer Console (F12) để xem chi tiết lỗi. Kiểm tra lại Firebase config trong file HTML |
