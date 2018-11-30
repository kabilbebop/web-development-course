package org.weightcars.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class WebConfigurerTestController {

    @GetMapping("/api/test-cors")
    public void testCorsOnApiPath() {
    }

    @GetMapping("/test/test-cors")
    public void testCorsOnOtherPath() {
    }
}
