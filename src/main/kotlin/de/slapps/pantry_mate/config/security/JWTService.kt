package de.slapps.pantry_mate.config.security

import de.slapps.pantry_mate.util.PantryMateClock
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.temporal.ChronoUnit
import java.util.Date
import javax.crypto.SecretKey


@Service
class JWTService(
    private val clock: PantryMateClock,
) {

    @Value("\${jwt.secret-key}")
    private val secretKey: String? = null

    @Value("\${jwt.token-expiration-seconds}")
    private val tokenExpirationSeconds: Long = 0

    private val signingKey: SecretKey by lazy {
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }

    private val parser: JwtParser by lazy {
        Jwts.parser().verifyWith(signingKey).build()
    }

    fun generateToken(username: String): String? {
        val issuedAt = Date.from(clock.instant())
        val expiresAt = Date.from(
            clock.instant().plus(tokenExpirationSeconds, ChronoUnit.SECONDS),
        )

        return Jwts.builder()
            .subject(username)
            .issuedAt(issuedAt)
            .expiration(expiresAt)
            .signWith(signingKey)
            .compact()
    }

    fun getUsername(token: String): String {
        return parser.parseSignedClaims(token).payload.subject
    }

    fun validate(userDetails: UserDetails, token: String): Boolean {
        val now = Date.from(clock.instant())
        val payload = parser.parseSignedClaims(token).payload
        val unexpired = payload.expiration.after(now)
        return unexpired && userDetails.username == payload.subject
    }
}