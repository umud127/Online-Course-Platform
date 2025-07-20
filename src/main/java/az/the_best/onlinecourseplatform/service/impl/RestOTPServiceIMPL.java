package az.the_best.onlinecourseplatform.service.impl;

import az.the_best.onlinecourseplatform.dto.DTOEmailRequest;
import az.the_best.onlinecourseplatform.service.IRestOTPService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class RestOTPServiceIMPL implements IRestOTPService {

    private final Map<String, OTPData> otpStorage = new HashMap<>();
    private final Random random = new Random();

    public String generateOTP(DTOEmailRequest dtoEmailRequest) {
        String otp = String.format("%06d", random.nextInt(999999));
        otpStorage.put(dtoEmailRequest.getEmail(), new OTPData(otp, LocalDateTime.now().plusMinutes(1)));
        return otp;
    }

    public boolean verifyOTP(String email, String otpInput) {
        OTPData data = otpStorage.get(email);

        if (data == null) return false;
        if (LocalDateTime.now().isAfter(data.expiry)) return false;

        return data.otp.equals(otpInput);
    }

    private static class OTPData {
        String otp;
        LocalDateTime expiry;

        OTPData(String otp, LocalDateTime expiry) {
            this.otp = otp;
            this.expiry = expiry;
        }
    }
}
