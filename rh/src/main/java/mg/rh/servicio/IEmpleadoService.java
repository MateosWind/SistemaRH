package mg.rh.servicio;

import mg.rh.modelo.Empleado;

import java.util.List;

public interface IEmpleadoService {

    public List<Empleado> listarEmpleado();

    public Empleado buscarEmpleadoPId(Integer idEmpleado);

    public void eliminarEmpleado(Empleado empleado);

    public  Empleado guardarEmpleado (Empleado empleado);

}
