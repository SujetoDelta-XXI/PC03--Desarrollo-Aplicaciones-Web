package com.tecsup.demo.services;

import com.tecsup.demo.domain.entities.Alumno;
import com.tecsup.demo.domain.entities.Auditoria;
import com.tecsup.demo.domain.persistence.AlumnoDao;
import com.tecsup.demo.domain.persistence.AuditoriaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    @Autowired
    private AlumnoDao dao;

    @Autowired
    private AuditoriaDao auditoriaDao;

    @Override
    @Transactional
    public void grabar(Alumno alumno) {
        dao.save(alumno);
        auditoriaDao.save(new Auditoria("Alumno", alumno.getCodigo(), new Date(), "admin", "GRABAR"));
    }

    @Override
    @Transactional
    public void eliminar(String codigo) {
        dao.deleteById(codigo);
        auditoriaDao.save(new Auditoria("Alumno", codigo, new Date(), "admin", "ELIMINAR"));
    }

    @Override
    @Transactional(readOnly = true)
    public Alumno buscar(String codigo) {
        return dao.findById(codigo).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alumno> listar() {
        return (List<Alumno>) dao.findAll();
    }
}
