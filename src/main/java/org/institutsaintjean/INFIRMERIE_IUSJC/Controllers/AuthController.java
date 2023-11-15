




package org.institutsaintjean.INFIRMERIE_IUSJC.Controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.institutsaintjean.INFIRMERIE_IUSJC.Configurations.Security.UserAuthenticationProvider;
import org.institutsaintjean.INFIRMERIE_IUSJC.Dto.CredentialsDto;
import org.institutsaintjean.INFIRMERIE_IUSJC.Dto.InfirmiereDto;
import org.institutsaintjean.INFIRMERIE_IUSJC.Dto.SignUpDto;
import org.institutsaintjean.INFIRMERIE_IUSJC.Metiers.Infirmiere_Metier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
//é@RequestMapping("/Infirmerie_IUSJC/Authentification")
@RequiredArgsConstructor
@RestController
public class AuthController {
    //@Autowired
    private final Infirmiere_Metier infirmiere_metier;

    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<InfirmiereDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        InfirmiereDto userDto = infirmiere_metier.login(credentialsDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<InfirmiereDto> register(@RequestBody @Valid SignUpDto user) {
        InfirmiereDto createdUser = infirmiere_metier.register(user);
        createdUser.setToken(userAuthenticationProvider.createToken(createdUser));
        return ResponseEntity.created(URI.create("/users/" + createdUser.getIdInfirmiere())).body(createdUser);
    }

    @PutMapping("/Infirmerie-IUSJC/Modifier/Indentifiant/{infirmiereId}")
    public ResponseEntity<InfirmiereDto> updateLoginAndPassword(
            @PathVariable Long infirmiereId,
            @RequestParam(required = false) String newLogin,
            @RequestParam(required = false) String newPassword
    ) {
        InfirmiereDto updatedInfirmiere = infirmiere_metier.updateLoginAndPassword(infirmiereId, newLogin, newPassword);
        return ResponseEntity.ok(updatedInfirmiere);
    }

}



