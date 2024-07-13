package literAllura.vargas.api.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}

