package gs.springportfolio.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@JsonPropertyOrder({
        "success",
        "challenge_ts",
        "hostname",
        "error-codes"
})
@Getter @Setter
public class RecaptchaResponse {

    @JsonProperty("success")
    private boolean success;
    @JsonProperty("challenge_ts")
    private String challengeTs;
    @JsonProperty("host-name")
    private String hostName;
    @JsonProperty("error-codes")
    private ErrorCode[] errorCodes;

    public enum ErrorCode{
        MISSING_SECRET("The secret parameter is missing."),
        INVALID_SECRET("The secret parameter is invalid or malformed."),
        MISSING_RESPONSE("The response parameter is missing."),
        INVALID_RESPONSE("The response parameter is invalid or malformed."),
        BAD_REQUEST("The request is invalid or malformed."),
        TIMEOUT_OR_DUPLICATE("The response is no longer valid: either is too old or has been used previously.");

        private static final Map<String, ErrorCode> errorCodeMap = new HashMap<>();

        static {
            errorCodeMap.put("missing-input-secret", MISSING_SECRET);
            errorCodeMap.put("invalid-input-secret", INVALID_SECRET);
            errorCodeMap.put("missing-input-response", MISSING_RESPONSE);
            errorCodeMap.put("invalid-input-response", INVALID_RESPONSE);
            errorCodeMap.put("bad-request", BAD_REQUEST);
            errorCodeMap.put("timeout-or-duplicate", TIMEOUT_OR_DUPLICATE);
        }

        private final String errorDescription;

        ErrorCode(String errorDescription) {
            this.errorDescription = errorDescription;
        }

        @JsonCreator
        public static ErrorCode forValues(String value){
            return errorCodeMap.get(value);
        }

        public String getErrorDescription() {
            return errorDescription;
        }
    }


}
