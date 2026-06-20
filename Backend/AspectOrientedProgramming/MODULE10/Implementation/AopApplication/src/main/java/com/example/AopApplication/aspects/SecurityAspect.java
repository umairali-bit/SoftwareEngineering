package com.example.AopApplication.aspects;



import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

@Aspect
@Component
public class SecurityAspect {

    @Around(
            "execution(* com.example.AopApplication.services..*(..)) && " +
                    "@annotation(com.example.AopApplication.aspects.RequiresAdmin)"
    )
    public Object checkAccess(ProceedingJoinPoint joinPoint) throws Throwable {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new SecurityException(
                    "Only admins can access this resource"
            );
        }

        return joinPoint.proceed();
    }
}
