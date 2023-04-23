package es.uca.iiss.aspectj;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoginAspect {
    @Before("execution(* Bank.createUser())")
    public void before(JoinPoint joinPoint){
        System.out.println("Before creating user..");
    }
    
    @After("execution(* Bank.makeTransaction()) || execution(* Bank.takeMoneyOut())")
    public void after(JoinPoint joinPoint){
        System.out.println("After making transaction or taking money out..");
    }
}
