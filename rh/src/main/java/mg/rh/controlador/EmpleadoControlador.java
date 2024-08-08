package mg.rh.controlador;

import mg.rh.excepción.RecursoNoEncontradoExcepcion;
import mg.rh.modelo.Empleado;
import mg.rh.servicio.EmpleadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("rh-app")
@CrossOrigin(value = "http://localhost:3000")
public class EmpleadoControlador {

    private static final Logger logger = LoggerFactory.getLogger(EmpleadoControlador.class);

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping("/empleados")
    public List<Empleado> obtenerEmpleados(){
        var empleados = empleadoService.listarEmpleado();
        empleados.forEach((empleado -> logger.info(empleado.toString())));
        return empleados;
    }

    @PostMapping("/empleados")
    public Empleado agregarEmpleado(@RequestBody Empleado empleado){
        logger.info("Empleado a agregar: " + empleado);
        return empleadoService.guardarEmpleado(empleado);

    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado>
    obtenerEmpleadoId(@PathVariable Integer id){
        Empleado empleado = empleadoService.buscarEmpleadoPId(id);
        if(empleado == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontró el empleado con la id: " + id);
        }
        return ResponseEntity.ok(empleado);
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<Empleado>
    actualizarEmpleado(@PathVariable Integer id,@RequestBody Empleado empleadoRecibido){
        Empleado empleado = empleadoService.buscarEmpleadoPId(id);
        if(empleado==null){
            throw new RecursoNoEncontradoExcepcion("No se encontro el empleado con la id: " +empleado);
        }
        empleado.setNombre(empleadoRecibido.getNombre());
        empleado.setSueldo(empleadoRecibido.getSueldo());
        empleado.setDepartamento(empleadoRecibido.getDepartamento());
        empleadoService.guardarEmpleado(empleado);
        return ResponseEntity.ok(empleado);

    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Map<String, Boolean>>
    eliminarEmpleado(@PathVariable Integer id){
        Empleado empleado = empleadoService.buscarEmpleadoPId(id);
        if(empleado==null){
            throw new RecursoNoEncontradoExcepcion("No se pudo encontrar el id:"+empleado);
        }
        empleadoService.eliminarEmpleado(empleado);
        Map<String,Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);

    }

}

