package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.service.ExpirationDateCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ExpirationDateCheckerService expirationDateCheckerService;

    @GetMapping("/check-expiration-dates")
    public ResponseEntity<Void> checkExpirationDates() {
        expirationDateCheckerService.checkExpirationDates();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
