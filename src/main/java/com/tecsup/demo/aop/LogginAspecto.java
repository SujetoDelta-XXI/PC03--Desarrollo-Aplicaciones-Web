package com.tecsup.demo.aop;

import com.tecsup.demo.domain.entities.Auditoria;
import com.tecsup.demo.domain.entities.Curso;
import com.tecsup.demo.domain.entities.Alumno;
import com.tecsup.demo.domain.persistence.AuditoriaDao;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

@Component
@Aspect
public class LogginAspecto {

    private Long tx;

    @Autowired
    private AuditoriaDao auditoriaDao;

    @Around("execution(* com.tecsup.demo.services.*ServiceImpl.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        Long currTime = System.currentTimeMillis();
        tx = currTime;

        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String metodo = "tx[" + tx + "] - " + joinPoint.getSignature().getName();

        if (joinPoint.getArgs().length > 0)
            logger.info(metodo + "() INPUT:" + Arrays.toString(joinPoint.getArgs()));

        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            logger.error(e.getMessage());
            throw e;
        }

        logger.info(metodo + "(): tiempo transcurrido " + (System.currentTimeMillis() - currTime) + " ms.");
        return result;
    }

    @After("execution(* com.tecsup.demo.controllers.*Controller.guardar*(..)) ||" +
            "execution(* com.tecsup.demo.controllers.*Controller.editar*(..)) ||" +
            "execution(* com.tecsup.demo.controllers.*Controller.eliminar*(..))")
    public void auditoria(JoinPoint joinPoint) throws Throwable {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String metodo = joinPoint.getSignature().getName();
        Object[] parametros = joinPoint.getArgs();

        String tabla = null;
        Object id = null;

        if (metodo.startsWith("guardar")) {
            Object entidad = parametros[0];
            if (entidad instanceof Curso curso) {
                tabla = "cursos";
                id = curso.getId();
            } else if (entidad instanceof Alumno alumno) {
                tabla = "alumno";
                id = alumno.getCodigo();
            }
        } else if (metodo.startsWith("editar") || metodo.startsWith("eliminar")) {
            id = parametros[0];
            String clase = joinPoint.getTarget().getClass().getSimpleName().toLowerCase();
            if (clase.contains("curso")) {
                tabla = "cursos";
            } else if (clase.contains("alumno")) {
                tabla = "alumno";
            }
        }

        if (tabla != null && id != null) {
            logger.info("tx[" + tx + "] - " + metodo + "(): registrando auditoria...");
            auditoriaDao.save(new Auditoria(tabla, convertToString(id), new Date(), "usuario", metodo));
        } else {
            logger.warn("tx[" + tx + "] - " + metodo + "(): no se pudo registrar auditoria por datos nulos.");
        }
    }

    // ✅ Método modificado para devolver String
    private String convertToString(Object id) {
        return id != null ? id.toString() : "null";
    }
}
