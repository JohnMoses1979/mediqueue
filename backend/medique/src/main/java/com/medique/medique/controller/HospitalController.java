// // package com.medique.medique.controller;

// // import com.medique.medique.entity.Doctor;
// // import com.medique.medique.entity.Hospital;
// // import com.medique.medique.service.DoctorService;
// // import com.medique.medique.service.JwtService;
// // import com.medique.medique.dto.HospitalRegisterRequest;
// // import com.medique.medique.service.HospitalService;
// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.http.ResponseEntity;
// // import org.springframework.web.bind.annotation.*;

// // import java.util.*;
// // import java.util.stream.Collectors;

// // @RestController
// // @RequestMapping("/api/hospitals")
// // public class HospitalController {

// //     @Autowired
// //     private HospitalService hospitalService;

// //     @Autowired
// //     private DoctorService doctorService;

// //     @Autowired
// //     private JwtService jwtService;

// //     // ------------------- API Endpoints -------------------

// //     // Get all approved hospitals
// //     @GetMapping("/approved")
// //     public ResponseEntity<List<Map<String, Object>>> getApprovedHospitals() {
// //         List<Hospital> hospitals = hospitalService.getApproved();
// //         List<Map<String, Object>> result = hospitals.stream()
// //                 .map(this::toPublicMap)
// //                 .collect(Collectors.toList());
// //         return ResponseEntity.ok(result);
// //     }


// //     @PostMapping("/register")
// //     public ResponseEntity<Map<String, Object>> registerHospital(@RequestBody HospitalRegisterRequest req) {
// //         try {
// //             Hospital hospital = hospitalService.register(req);

// //             Map<String, Object> response = new HashMap<>();
// //             response.put("success", true);
// //             response.put("message", "Hospital registration submitted successfully. Waiting for admin approval.");
// //             response.put("hospitalId", hospital.getHospitalId());
// //             response.put("status", hospital.getStatus() != null ? hospital.getStatus().name() : "PENDING");

// //             return ResponseEntity.ok(response);
// //         } catch (Exception e) {
// //             e.printStackTrace();

// //             Map<String, Object> error = new HashMap<>();
// //             error.put("success", false);
// //             error.put("message", e.getMessage());

// //             return ResponseEntity.badRequest().body(error);
// //         }
// //     }

// //     @PostMapping("/login")
// //     public ResponseEntity<Map<String, Object>> loginHospital(@RequestBody Map<String, String> body) {
// //         try {
// //             String hospitalId = body.get("hospitalId");
// //             String phone = body.get("phone");
// //             String password = body.get("password");

// //             Hospital hospital = hospitalService.loginStaff(hospitalId, phone, password);
// //             String token = jwtService.generateToken(hospital.getId(), "HOSPITAL");

// //             Map<String, Object> response = new HashMap<>();
// //             response.put("success", true);
// //             response.put("message", "Login successful");
// //             response.put("token", token);
// //             response.put("hospitalId", hospital.getHospitalId());
// //             response.put("name", hospital.getName());
// //             response.put("phone", hospital.getPhone());
// //             response.put("email", hospital.getEmail());
// //             response.put("status", hospital.getStatus() != null ? hospital.getStatus().name() : null);
// //             response.put("role", "HOSPITAL");

// //             return ResponseEntity.ok(response);
// //         } catch (Exception e) {
// //             e.printStackTrace();

// //             Map<String, Object> error = new HashMap<>();
// //             error.put("success", false);
// //             error.put("message", e.getMessage());

// //             return ResponseEntity.badRequest().body(error);
// //         }
// //     }

// //     // Get pending hospitals for admin approval
// //     @GetMapping("/pending")
// //     public ResponseEntity<List<Map<String, Object>>> getPendingHospitals() {
// //         List<Hospital> hospitals = hospitalService.getPending();
// //         List<Map<String, Object>> result = hospitals.stream()
// //                 .map(this::toFullMap)
// //                 .collect(Collectors.toList());
// //         return ResponseEntity.ok(result);
// //     }

// //     // Get all hospitals for admin
// //     @GetMapping("/all")
// //     public ResponseEntity<List<Map<String, Object>>> getAllHospitals() {
// //         List<Hospital> hospitals = hospitalService.getAll();
// //         List<Map<String, Object>> result = hospitals.stream()
// //                 .map(this::toFullMap)
// //                 .collect(Collectors.toList());
// //         return ResponseEntity.ok(result);
// //     }

// //     // Approve hospital
// //     @PutMapping("/{hospitalId}/approve")
// //     public ResponseEntity<Map<String, Object>> approveHospital(@PathVariable String hospitalId) {
// //         Hospital hospital = hospitalService.approve(hospitalId);

// //         Map<String, Object> response = new HashMap<>();
// //         response.put("success", true);
// //         response.put("message", "Hospital approved successfully.");
// //         response.put("hospitalId", hospital.getHospitalId());
// //         response.put("status", hospital.getStatus() != null ? hospital.getStatus().name() : "APPROVED");

// //         return ResponseEntity.ok(response);
// //     }

// //     @PostMapping("/{hospitalId}/approve")
// //     public ResponseEntity<Map<String, Object>> approveHospitalPost(@PathVariable String hospitalId) {
// //         return approveHospital(hospitalId);
// //     }

// //     // Reject hospital
// //     @PutMapping("/{hospitalId}/reject")
// //     public ResponseEntity<Map<String, Object>> rejectHospital(@PathVariable String hospitalId) {
// //         Hospital hospital = hospitalService.reject(hospitalId);

// //         Map<String, Object> response = new HashMap<>();
// //         response.put("success", true);
// //         response.put("message", "Hospital rejected successfully.");
// //         response.put("hospitalId", hospital.getHospitalId());
// //         response.put("status", hospital.getStatus() != null ? hospital.getStatus().name() : "REJECTED");

// //         return ResponseEntity.ok(response);
// //     }

// //     @PostMapping("/{hospitalId}/reject")
// //     public ResponseEntity<Map<String, Object>> rejectHospitalPost(@PathVariable String hospitalId) {
// //         return rejectHospital(hospitalId);
// //     }

// //     // Get hospital by ID
// //     @GetMapping("/{id}")
// //     public ResponseEntity<Map<String, Object>> getHospitalById(@PathVariable String id) {
// //         Hospital hospital = hospitalService.getHospitalById(id);
// //         return ResponseEntity.ok(toFullMap(hospital));
// //     }

// //     // ------------------- Helper Methods -------------------

// //     // Convert Hospital entity to JSON-ready map (public fields)
// //     private Map<String, Object> toPublicMap(Hospital h) {
// //         Map<String, Object> m = new HashMap<>();
// //         m.put("hospitalId", h.getHospitalId());
// //         m.put("name", h.getName());
// //         m.put("address", h.getAddress());
// //         m.put("city", h.getCity());
// //         m.put("imageUrl", h.getImageUrl());
// //         m.put("openingTime", h.getOpeningTime());
// //         m.put("closingTime", h.getClosingTime());
// //         m.put("description", h.getDescription());
// //         m.put("type", h.getType());
// //         m.put("status", h.getStatus() != null ? h.getStatus().name() : null);

// //         // Map doctors to JSON-ready list
// //         List<Doctor> doctors = doctorService.getByHospitalId(h.getHospitalId());
// //         List<Map<String,Object>> doctorList = doctors.stream()
// //                 .map(d -> Map.<String, Object>of(
// //                         "id", d.getId(),
// //                         "name", d.getName(),
// //                         "department", d.getDepartment(),
// //                         "qualification", d.getQualification(),
// //                         "fee", d.getFee(),
// //                         "available", d.getAvailable()
// //                 ))
// //                 .collect(Collectors.toList());

// //         m.put("doctorList", doctorList);

// //         // Process departments
// //         List<String> deptFromDoctors = doctors.stream()
// //                 .map(Doctor::getDepartment)
// //                 .filter(d -> d != null && !d.isBlank())
// //                 .distinct()
// //                 .collect(Collectors.toList());

// //         if (deptFromDoctors.isEmpty() && h.getDepartments() != null && !h.getDepartments().isBlank()) {
// //             String raw = h.getDepartments().trim();
// //             if (raw.startsWith("[")) {
// //                 raw = raw.replaceAll("[\\[\\]\"]", "");
// //             }
// //             for (String d : raw.split(",")) {
// //                 String trimmed = d.trim();
// //                 if (!trimmed.isEmpty()) deptFromDoctors.add(trimmed);
// //             }
// //         }
// //         m.put("departments", deptFromDoctors);

// //         return m;
// //     }

// //     // Convert Hospital entity to full JSON-ready map (all fields)
// //     private Map<String, Object> toFullMap(Hospital h) {
// //         Map<String,Object> m = toPublicMap(h); // base fields
// //         m.put("email", h.getEmail());
// //         m.put("phone", h.getPhone());
// //         m.put("type", h.getType());
// //         m.put("imageUrl", h.getImageUrl());
// //         return m;
// //     }
// // }
// package com.medique.medique.controller;

// import com.medique.medique.entity.Doctor;
// import com.medique.medique.entity.Hospital;
// import com.medique.medique.repository.HospitalRepository;
// import com.medique.medique.service.DoctorService;
// import com.medique.medique.service.JwtService;
// import com.medique.medique.dto.HospitalRegisterRequest;
// import com.medique.medique.service.HospitalService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.*;
// import java.util.stream.Collectors;

// @RestController
// @RequestMapping("/api/hospitals")
// public class HospitalController {

//     @Autowired
//     private HospitalService hospitalService;

//     @Autowired
//     private HospitalRepository hospitalRepo;

//     @Autowired
//     private DoctorService doctorService;

//     @Autowired
//     private JwtService jwtService;

//     // ── GET MY HOSPITAL DETAILS (Staff — uses JWT to identify hospital) ────────
//     // Called by StaffProfileScreen via fetchMyHospitalDetails()
//     // Frontend hits: GET /api/hospitals/details  (with Authorization: Bearer <token>)
//     @GetMapping("/details")
//     public ResponseEntity<Map<String, Object>> getMyHospitalDetails(
//             @RequestHeader("Authorization") String authHeader) {
//         try {
//             String token = authHeader.replace("Bearer ", "").trim();
//             Long internalId = jwtService.extractUserId(token);

//             Hospital hospital = hospitalRepo.findById(internalId)
//                     .orElseThrow(() -> new RuntimeException("Hospital not found"));

//             return ResponseEntity.ok(toFullMap(hospital));
//         } catch (Exception e) {
//             Map<String, Object> error = new HashMap<>();
//             error.put("success", false);
//             error.put("message", e.getMessage());
//             return ResponseEntity.badRequest().body(error);
//         }
//     }

//     // ── UPDATE MY HOSPITAL PROFILE (Staff — uses JWT to identify hospital) ─────
//     // Called by StaffProfileScreen via updateHospitalProfile()
//     // Frontend hits: PUT /api/hospitals/profile  (with Authorization: Bearer <token>)
//     @PutMapping("/profile")
//     public ResponseEntity<Map<String, Object>> updateMyProfile(
//             @RequestHeader("Authorization") String authHeader,
//             @RequestBody Map<String, Object> updates) {
//         try {
//             String token = authHeader.replace("Bearer ", "").trim();
//             Long internalId = jwtService.extractUserId(token);

//             Hospital hospital = hospitalRepo.findById(internalId)
//                     .orElseThrow(() -> new RuntimeException("Hospital not found"));

//             Hospital updated = hospitalService.updateHospitalProfile(
//                     hospital.getHospitalId(), updates);

//             return ResponseEntity.ok(toFullMap(updated));
//         } catch (Exception e) {
//             Map<String, Object> error = new HashMap<>();
//             error.put("success", false);
//             error.put("message", e.getMessage());
//             return ResponseEntity.badRequest().body(error);
//         }
//     }

//     // ── GET ALL APPROVED HOSPITALS (Public — patient app browse) ──────────────
//     @GetMapping("/approved")
//     public ResponseEntity<List<Map<String, Object>>> getApprovedHospitals() {
//         List<Hospital> hospitals = hospitalService.getApproved();
//         List<Map<String, Object>> result = hospitals.stream()
//                 .map(this::toPublicMap)
//                 .collect(Collectors.toList());
//         return ResponseEntity.ok(result);
//     }

//     // ── REGISTER HOSPITAL ─────────────────────────────────────────────────────
//     @PostMapping("/register")
//     public ResponseEntity<Map<String, Object>> registerHospital(
//             @RequestBody HospitalRegisterRequest req) {
//         try {
//             Hospital hospital = hospitalService.register(req);

//             Map<String, Object> response = new HashMap<>();
//             response.put("success", true);
//             response.put("message",
//                     "Hospital registration submitted successfully. Waiting for admin approval.");
//             response.put("hospitalId", hospital.getHospitalId());
//             response.put("status",
//                     hospital.getStatus() != null ? hospital.getStatus().name() : "PENDING");

//             return ResponseEntity.ok(response);
//         } catch (Exception e) {
//             e.printStackTrace();
//             Map<String, Object> error = new HashMap<>();
//             error.put("success", false);
//             error.put("message", e.getMessage());
//             return ResponseEntity.badRequest().body(error);
//         }
//     }

//     // ── STAFF LOGIN ───────────────────────────────────────────────────────────
//     @PostMapping("/login")
//     public ResponseEntity<Map<String, Object>> loginHospital(
//             @RequestBody Map<String, String> body) {
//         try {
//             String hospitalId = body.get("hospitalId");
//             String phone      = body.get("phone");
//             String password   = body.get("password");

//             Hospital hospital = hospitalService.loginStaff(hospitalId, phone, password);
//             String token = jwtService.generateToken(hospital.getId(), "HOSPITAL");

//             Map<String, Object> response = new HashMap<>();
//             response.put("success", true);
//             response.put("message", "Login successful");
//             response.put("token", token);
//             response.put("hospitalId", hospital.getHospitalId());
//             response.put("name", hospital.getName());
//             response.put("phone", hospital.getPhone());
//             response.put("email", hospital.getEmail());
//             response.put("address", hospital.getAddress());
//             response.put("city", hospital.getCity());
//             response.put("type", hospital.getType());
//             response.put("imageUrl", hospital.getImageUrl());
//             response.put("openingTime", hospital.getOpeningTime());
//             response.put("closingTime", hospital.getClosingTime());
//             response.put("status",
//                     hospital.getStatus() != null ? hospital.getStatus().name() : null);
//             response.put("role", "HOSPITAL");

//             return ResponseEntity.ok(response);
//         } catch (Exception e) {
//             e.printStackTrace();
//             Map<String, Object> error = new HashMap<>();
//             error.put("success", false);
//             error.put("message", e.getMessage());
//             return ResponseEntity.badRequest().body(error);
//         }
//     }

//     // ── ADMIN — PENDING HOSPITALS ─────────────────────────────────────────────
//     @GetMapping("/pending")
//     public ResponseEntity<List<Map<String, Object>>> getPendingHospitals() {
//         List<Hospital> hospitals = hospitalService.getPending();
//         List<Map<String, Object>> result = hospitals.stream()
//                 .map(this::toFullMap)
//                 .collect(Collectors.toList());
//         return ResponseEntity.ok(result);
//     }

//     // ── ADMIN — ALL HOSPITALS ─────────────────────────────────────────────────
//     @GetMapping("/all")
//     public ResponseEntity<List<Map<String, Object>>> getAllHospitals() {
//         List<Hospital> hospitals = hospitalService.getAll();
//         List<Map<String, Object>> result = hospitals.stream()
//                 .map(this::toFullMap)
//                 .collect(Collectors.toList());
//         return ResponseEntity.ok(result);
//     }

//     // ── ADMIN — APPROVE ───────────────────────────────────────────────────────
//     @PutMapping("/{hospitalId}/approve")
//     public ResponseEntity<Map<String, Object>> approveHospital(
//             @PathVariable String hospitalId) {
//         Hospital hospital = hospitalService.approve(hospitalId);

//         Map<String, Object> response = new HashMap<>();
//         response.put("success", true);
//         response.put("message", "Hospital approved successfully.");
//         response.put("hospitalId", hospital.getHospitalId());
//         response.put("status",
//                 hospital.getStatus() != null ? hospital.getStatus().name() : "APPROVED");

//         return ResponseEntity.ok(response);
//     }

//     @PostMapping("/{hospitalId}/approve")
//     public ResponseEntity<Map<String, Object>> approveHospitalPost(
//             @PathVariable String hospitalId) {
//         return approveHospital(hospitalId);
//     }

//     // ── ADMIN — REJECT ────────────────────────────────────────────────────────
//     @PutMapping("/{hospitalId}/reject")
//     public ResponseEntity<Map<String, Object>> rejectHospital(
//             @PathVariable String hospitalId) {
//         Hospital hospital = hospitalService.reject(hospitalId);

//         Map<String, Object> response = new HashMap<>();
//         response.put("success", true);
//         response.put("message", "Hospital rejected successfully.");
//         response.put("hospitalId", hospital.getHospitalId());
//         response.put("status",
//                 hospital.getStatus() != null ? hospital.getStatus().name() : "REJECTED");

//         return ResponseEntity.ok(response);
//     }

//     @PostMapping("/{hospitalId}/reject")
//     public ResponseEntity<Map<String, Object>> rejectHospitalPost(
//             @PathVariable String hospitalId) {
//         return rejectHospital(hospitalId);
//     }

//     // ── GET HOSPITAL BY ID (public) ───────────────────────────────────────────
//     @GetMapping("/{id}")
//     public ResponseEntity<Map<String, Object>> getHospitalById(
//             @PathVariable String id) {
//         Hospital hospital = hospitalService.getHospitalById(id);
//         return ResponseEntity.ok(toFullMap(hospital));
//     }

//     // ─────────────────────────────────────────────────────────────────────────
//     // Helper: public-facing map (used for patient browse)
//     // ─────────────────────────────────────────────────────────────────────────
//     private Map<String, Object> toPublicMap(Hospital h) {
//         Map<String, Object> m = new HashMap<>();
//         m.put("hospitalId",   h.getHospitalId());
//         m.put("name",         h.getName());
//         m.put("address",      h.getAddress());
//         m.put("city",         h.getCity());
//         m.put("imageUrl",     h.getImageUrl());
//         m.put("openingTime",  h.getOpeningTime());
//         m.put("closingTime",  h.getClosingTime());
//         m.put("description",  h.getDescription());
//         m.put("type",         h.getType());
//         m.put("status",
//                 h.getStatus() != null ? h.getStatus().name() : null);

//         // Doctors list
//         List<Doctor> doctors = doctorService.getByHospitalId(h.getHospitalId());
//         List<Map<String, Object>> doctorList = doctors.stream()
//                 .map(d -> Map.<String, Object>of(
//                         "id",            d.getId(),
//                         "name",          d.getName(),
//                         "department",    d.getDepartment(),
//                         "qualification", d.getQualification(),
//                         "fee",           d.getFee(),
//                         "available",     d.getAvailable()
//                 ))
//                 .collect(Collectors.toList());
//         m.put("doctorList", doctorList);

//         // Departments (prefer live doctor data, fall back to stored string)
//         List<String> deptFromDoctors = doctors.stream()
//                 .map(Doctor::getDepartment)
//                 .filter(d -> d != null && !d.isBlank())
//                 .distinct()
//                 .collect(Collectors.toList());

//         if (deptFromDoctors.isEmpty()
//                 && h.getDepartments() != null
//                 && !h.getDepartments().isBlank()) {
//             String raw = h.getDepartments().trim();
//             if (raw.startsWith("[")) raw = raw.replaceAll("[\\[\\]\"]", "");
//             for (String d : raw.split(",")) {
//                 String t = d.trim();
//                 if (!t.isEmpty()) deptFromDoctors.add(t);
//             }
//         }
//         m.put("departments", deptFromDoctors);

//         return m;
//     }

//     // ─────────────────────────────────────────────────────────────────────────
//     // Helper: full map — includes all private/staff fields
//     // ─────────────────────────────────────────────────────────────────────────
//     private Map<String, Object> toFullMap(Hospital h) {
//         Map<String, Object> m = toPublicMap(h);

//         // Contact
//         m.put("email",   h.getEmail());
//         m.put("phone",   h.getPhone());
//         m.put("type",    h.getType());
//         m.put("address", h.getAddress());
//         m.put("city",    h.getCity());
//         m.put("imageUrl", h.getImageUrl());

//         // Timing
//         m.put("openingTime", h.getOpeningTime());
//         m.put("closingTime", h.getClosingTime());

//         // Licensing
//         m.put("licenseNumber",      h.getLicenseNumber());
//         m.put("registrationNumber", h.getRegistrationNumber());
//         m.put("ownerName",          h.getOwnerName());

//         // Payment / Bank
//         m.put("upiId",             h.getUpiId());
//         m.put("bankAccountName",   h.getBankAccountName());
//         m.put("bankAccountNumber", h.getBankAccountNumber());
//         m.put("bankIfsc",          h.getBankIfsc());
//         m.put("bankName",          h.getBankName());

//         return m;
//     }
// }
package com.medique.medique.controller;
 
import com.medique.medique.dto.HospitalLoginRequest;
import com.medique.medique.dto.HospitalRegisterRequest;
import com.medique.medique.dto.SendOtpRequest;
import com.medique.medique.dto.ResetPasswordRequest;
import com.medique.medique.entity.Doctor;
import com.medique.medique.entity.Hospital;
import com.medique.medique.repository.DoctorRepository;
import com.medique.medique.security.JwtUtil;
import com.medique.medique.service.HospitalService;
import com.medique.medique.service.TwilioOTPService;
 
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
 
@RestController
@RequestMapping("/api/hospitals")
@CrossOrigin(origins = "*")
public class HospitalController {
 
    private final HospitalService hospitalService;
    private final JwtUtil jwtUtil;
    private final DoctorRepository doctorRepository;
    private final TwilioOTPService twilioOTPService;
 
    public HospitalController(HospitalService hospitalService, JwtUtil jwtUtil, DoctorRepository doctorRepository, TwilioOTPService twilioOTPService) {
        this.hospitalService = hospitalService;
        this.jwtUtil = jwtUtil;
        this.doctorRepository = doctorRepository;
        this.twilioOTPService = twilioOTPService;
    }
 
    // ── HOSPITAL FORGOT PASSWORD FLOW ──────────────────────────────────────────
 
    @GetMapping("/check-phone")
    public ResponseEntity<?> checkPhone(@RequestParam String phone) {
        try {
            String formattedPhone = phone.trim();
            if (!formattedPhone.startsWith("+")) {
                formattedPhone = "+91" + formattedPhone;
            }
            boolean registered = hospitalService.isPhoneRegistered(formattedPhone);
            // Always return 200 OK — let the frontend read the "registered" boolean
            return ResponseEntity.ok(Map.of("registered", registered));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
 
    @PostMapping("/otp/send")
    public ResponseEntity<?> sendOtp(@RequestBody SendOtpRequest req) {
        try {
            String phone = req.getPhone().trim();
            if (!phone.startsWith("+")) {
                phone = "+91" + phone;
            }
 
            boolean registered = hospitalService.isPhoneRegistered(phone);
            if (!registered) {
                return ResponseEntity.status(404)
                        .body(Map.of("message", "This phone number is not registered."));
            }
 
            boolean sent = twilioOTPService.sendOTP(phone);
            if (sent) {
                return ResponseEntity.ok(Map.of("message", "OTP sent successfully."));
            } else {
                return ResponseEntity.badRequest().body(Map.of("message", "Unable to send OTP."));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
 
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest req) {
        try {
            String phone = req.getPhone().trim();
            if (!phone.startsWith("+")) {
                phone = "+91" + phone;
            }
 
            boolean isValid = twilioOTPService.verifyOTP(phone, req.getCode().trim());
            if (!isValid) {
                return ResponseEntity.badRequest().body(Map.of("message", "Invalid or expired OTP code."));
            }
 
            hospitalService.resetPassword(phone, req.getNewPassword());
            return ResponseEntity.ok(Map.of("message", "Password reset successful."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
 
    // ──────────────────────────────────────────────────────────────────────────
 
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody HospitalRegisterRequest req) {
        try {
            Hospital hospital = hospitalService.register(req);
            return ResponseEntity.ok(Map.of(
                    "message",    "Registration submitted successfully. Await admin approval.",
                    "hospitalId", hospital.getHospitalId(),
                    "status",     hospital.getStatus().name(),
                    "name",       hospital.getName()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
 
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody HospitalLoginRequest req) {
        try {
            Hospital h = hospitalService.loginStaff(
                    req.getHospitalId(),
                    req.getPhone(),
                    req.getPassword()
            );
 
            String token = jwtUtil.generateHospitalToken(h.getHospitalId(), "HOSPITAL");
 
            Map<String, Object> res = new HashMap<>();
            res.put("token",       token);
            res.put("hospitalId",  h.getHospitalId());
            res.put("name",        h.getName());
            res.put("email",       h.getEmail());
            res.put("phone",       h.getPhone());
            res.put("address",     h.getAddress());
            res.put("city",        h.getCity());
            res.put("type",        h.getType());
            res.put("imageUrl",    h.getImageUrl());
            res.put("openingTime", h.getOpeningTime());
            res.put("closingTime", h.getClosingTime());
            res.put("status",      h.getStatus().name());
 
            // ── Include bank details in login response ──
            res.put("upiId",             h.getUpiId());
            res.put("bankAccountName",   h.getBankAccountName());
            res.put("bankAccountNumber", h.getBankAccountNumber());
            res.put("bankIfsc",          h.getBankIfsc());
            res.put("bankName",          h.getBankName());
            // ────────────────────────────────────────────
 
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
 
    @GetMapping("/details")
    public ResponseEntity<?> getHospitalDetails(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Missing or invalid Authorization header"));
            }
 
            String token      = authHeader.substring(7);
            String hospitalId = jwtUtil.extractSubject(token);
            Hospital hospital = hospitalService.getHospitalById(hospitalId);
 
            return ResponseEntity.ok(toFullMap(hospital));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
        }
    }
 
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            HttpServletRequest request,
            @RequestBody Map<String, Object> updates) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Unauthorized"));
            }
 
            String token      = authHeader.substring(7);
            String hospitalId = jwtUtil.extractSubject(token);
 
            Hospital updated = hospitalService.updateHospitalProfile(hospitalId, updates);
            return ResponseEntity.ok(toFullMap(updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
 
    @GetMapping("/{hospitalId}")
    public ResponseEntity<?> getHospitalById(@PathVariable String hospitalId) {
        try {
            Hospital hospital = hospitalService.getHospitalById(hospitalId);
            return ResponseEntity.ok(toFullMap(hospital));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }
 
    @GetMapping("/approved")
    public ResponseEntity<?> getApprovedHospitals() {
        List<Map<String, Object>> result = hospitalService.getApproved()
                .stream()
                .map(this::toFullMap)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
 
    @GetMapping("/pending")
    public ResponseEntity<?> getPendingHospitals() {
        return ResponseEntity.ok(hospitalService.getPending()
                .stream()
                .map(this::toFullMap)
                .collect(Collectors.toList()));
    }
 
    @GetMapping("/all")
    public ResponseEntity<?> getAllHospitals() {
        return ResponseEntity.ok(hospitalService.getAll()
                .stream()
                .map(this::toFullMap)
                .collect(Collectors.toList()));
    }
 
    @PutMapping("/{hospitalId}/approve")
    public ResponseEntity<?> approveHospital(@PathVariable String hospitalId) {
        try {
            Hospital hospital = hospitalService.approve(hospitalId);
            return ResponseEntity.ok(Map.of(
                    "message", "Hospital approved successfully",
                    "status",  hospital.getStatus().name()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
 
    @PutMapping("/{hospitalId}/reject")
    public ResponseEntity<?> rejectHospital(@PathVariable String hospitalId) {
        try {
            Hospital hospital = hospitalService.reject(hospitalId);
            return ResponseEntity.ok(Map.of(
                    "message", "Hospital rejected",
                    "status",  hospital.getStatus().name()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
 
    // ── Private helpers ───────────────────────────────────────────────────────
 
    private Map<String, Object> toPublicMap(Hospital h) {
        Map<String, Object> m = new HashMap<>();
        m.put("hospitalId",   h.getHospitalId());
        m.put("name",         h.getName());
        m.put("address",      h.getAddress());
        m.put("city",         h.getCity());
        m.put("imageUrl",     h.getImageUrl());
        m.put("openingTime",  h.getOpeningTime());
        m.put("closingTime",  h.getClosingTime());
        m.put("description",  h.getDescription());
        m.put("type",         h.getType());
        m.put("status",       h.getStatus() != null ? h.getStatus().name() : null);
 
        List<Doctor> doctors = doctorRepository.findByHospitalId(h.getHospitalId());
        m.put("doctorList", doctors);
 
        List<String> deptFromDoctors = doctors.stream()
                .map(Doctor::getDepartment)
                .filter(d -> d != null && !d.isBlank())
                .distinct()
                .collect(Collectors.toList());
 
        if (deptFromDoctors.isEmpty() && h.getDepartments() != null && !h.getDepartments().isBlank()) {
            String raw = h.getDepartments().trim();
            if (raw.startsWith("[")) {
                raw = raw.replaceAll("[\\[\\]\"]", "");
            }
            for (String d : raw.split(",")) {
                String trimmed = d.trim();
                if (!trimmed.isEmpty()) deptFromDoctors.add(trimmed);
            }
        }
        m.put("departments", deptFromDoctors);
        return m;
    }
 
    private Map<String, Object> toFullMap(Hospital h) {
        Map<String, Object> m = toPublicMap(h);
        m.put("ownerName",          h.getOwnerName());
        m.put("email",              h.getEmail());
        m.put("phone",              h.getPhone());
        m.put("numberOfDoctors",    h.getNumberOfDoctors());
        m.put("licenseNumber",      h.getLicenseNumber());
        m.put("registrationNumber", h.getRegistrationNumber());
        m.put("createdAt",          h.getCreatedAt() != null ? h.getCreatedAt().toString() : null);
        m.put("documentUrls",       h.getDocumentUrls());
 
        // ── Bank / Payment details ──
        m.put("upiId",             h.getUpiId());
        m.put("bankAccountName",   h.getBankAccountName());
        m.put("bankAccountNumber", h.getBankAccountNumber());
        m.put("bankIfsc",          h.getBankIfsc());
        m.put("bankName",          h.getBankName());
        // ────────────────────────────
 
        return m;
    }
}