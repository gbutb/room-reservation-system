// RRSTermsAndConditionsController.java
package ge.rrs.modules.termsAndConditions;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RRSTermsAndConditionsController {
    @GetMapping("/terms-and-conditions")
    public String termsAndConditions() {
        return "/termsAndConditions/termsAndConditions";
    }
}