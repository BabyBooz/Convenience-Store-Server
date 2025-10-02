package demo.shop.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import demo.shop.DTO.Request.AuthenticationRequest;
import demo.shop.DTO.Request.IntrospectRequest;
import demo.shop.DTO.Request.LogoutRequest;
import demo.shop.DTO.Request.RefreshTokenRequest;
import demo.shop.DTO.Response.AuthenticationResponse;
import demo.shop.DTO.Response.IntrospectResponse;
import demo.shop.DTO.Response.LogoutResponse;
import demo.shop.Entity.LogoutToken;
import demo.shop.Entity.User;
import demo.shop.Exception.AppException;
import demo.shop.Exception.EnumCode;
import demo.shop.Repository.LogoutRepository;
import demo.shop.Repository.UserRepository;
import jakarta.transaction.Transactional;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
  UserRepository userRepository;
  LogoutRepository logoutRepository;

  @NonFinal
  @Value("${jwt.SIGNER_KEY}")
  protected String SIGNER_KEY;

  //! Kiem tra tinh hop le cua token
  public IntrospectResponse introspectResponse(IntrospectRequest request)
      throws JOSEException, ParseException {
    var token = request.getToken();
    var signedJWT = verifyToken(token);
    boolean verified = signedJWT != null;
    var jti = signedJWT.getJWTClaimsSet().getJWTID();
    var logout = logoutRepository.existsById(jti);
    if(logout){
      throw new AppException(EnumCode.LOGOUT);
    }
    return new IntrospectResponse(verified);
  }


  private SignedJWT verifyToken(String token) throws ParseException, JOSEException {
    // kiem tra signerkey khi tao ra token va verify token co giong nhau khong
    JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

    // phan tich token thanh 3 phan header, payload, signature
    SignedJWT signedJWT = SignedJWT.parse(token);

    // lay ra thoi gian het han
    Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

    // so sanh thoi gian het han voi thoi gian hien tai
    // kiem tra token co hop le hay khong

    var user = signedJWT.getJWTClaimsSet().getSubject();
    if(!userRepository.existsByUsername(user)){
      throw new AppException(EnumCode.USER_NOT_EXISTED);
    }

    if(!signedJWT.verify(verifier)){
      throw new AppException(EnumCode.UNAUTHENTICATED);
    }
    if (!( new Date().before(expirationTime))) {
      throw new AppException(EnumCode.OutOfToken);
    }

    return signedJWT;
  }

  //! Authenticate user and generate JWT token
  public AuthenticationResponse authenticationUser(AuthenticationRequest request) {
    User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(EnumCode.USER_NOT_EXISTED));
    PasswordEncoder encoder = new BCryptPasswordEncoder(10);
    boolean authenticated = encoder.matches(request.getPassword(), user.getPassword());
    if (!authenticated) {
      throw new AppException(EnumCode.INVALID_AUTHENTICATION);
    }
    return AuthenticationResponse.builder()
        .authenticated(true)
        .token(generateToken(user))
        .build();
  }

  public LogoutResponse logoutToken(LogoutRequest request) throws ParseException, JOSEException {
    var signedJWT = verifyToken(request.getToken());

    var jti = signedJWT.getJWTClaimsSet().getJWTID();
    var exp = signedJWT.getJWTClaimsSet().getExpirationTime();
    LogoutToken object = LogoutToken.builder().token(jti).expiryDate(exp).build();
    logoutRepository.save(object);
    return LogoutResponse.builder().valid("Logout").build();
  }

  private String generateToken(User user){
    //JWT Gom 3 phan: Header, Payload, Signature
    //Header: chua thong tin ve kieu token, thuat toan ma hoa

    JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

    //Payload: chua thong tin ve user, thoi gian het han
    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
        .subject(user.getUsername())
        .issuer("linhnghiem")
        .expirationTime(new Date(
            Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
        ))
        .claim("scope", buildScope(user))
        .jwtID(java.util.UUID.randomUUID().toString())
        .build();

    Payload payload = new Payload(claimsSet.toJSONObject());

    JWSObject jwsObject = new JWSObject(header, payload);

    //Signature: duoc tao ra tu Header va Payload, dung de xac thuc , ket hop then signerkey

    try{
      jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
      return jwsObject.serialize();
    }catch (JOSEException e) {
      throw new RuntimeException("Error generating token", e);
    }
  }

  private String buildScope(User user){
    return String.join(" ", user.getRoles());
  }

  @Transactional
  public AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException {
    var signedJWT = verifyToken(request.getToken());

    var id = signedJWT.getJWTClaimsSet().getJWTID();
    var date = signedJWT.getJWTClaimsSet().getExpirationTime();
    logoutRepository.save(LogoutToken.builder().token(id).expiryDate(date).build());

    var logoutToken = logoutRepository.existsById(id);
    if(logoutToken){
      throw new AppException(EnumCode.LOGOUT);
    }

    var name = signedJWT.getJWTClaimsSet().getSubject();
    User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(EnumCode.USER_NOT_EXISTED));

    var tokenRefresh = generateToken(user);
    return AuthenticationResponse.builder()
        .authenticated(true)
        .token(tokenRefresh)
        .build();
  }
}
