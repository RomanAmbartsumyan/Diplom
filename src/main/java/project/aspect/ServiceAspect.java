package project.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ServiceAspect {
    @AfterThrowing(pointcut = "execution(* (@org.springframework.stereotype.Service *).*(..))", throwing = "ex")
    public void logRuntimeException(RuntimeException ex){
        log.error("Runtime error: " +  ex.toString());
    }
}