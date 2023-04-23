package es.uca.iiss.aspectj;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoginAspect {
    @Before ("execution(* Bank.makeTransaction()) || execution(* Bank.takeMoneyOut())")
    public void before2(JoinPoint joinPoint){
        System.out.println("The login is required");
    }

    @After ("execution(* Bank.showUsers())")
    public void after(JoinPoint joinPoint){
        System.out.println("The database is empty");
    }    
}
