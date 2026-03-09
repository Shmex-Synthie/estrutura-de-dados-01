import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArranjoOrdenadoTest {

    @Test
    void testeInsercaoCrescente() {
        ArranjoOrdenado arr = new ArranjoOrdenado(10, true);

        arr.inserir(5);
        arr.inserir(2);
        arr.inserir(8);

        assertEquals(2, arr.get(0));
        assertEquals(5, arr.get(1));
        assertEquals(8, arr.get(2));
    }

    @Test
    void testeInsercaoDecrescente() {
        ArranjoOrdenado arr = new ArranjoOrdenado(10, false);

        arr.inserir(5);
        arr.inserir(2);
        arr.inserir(8);

        assertEquals(8, arr.get(0));
        assertEquals(5, arr.get(1));
        assertEquals(2, arr.get(2));
    }

    @Test
    void testeBuscar() {
        ArranjoOrdenado arr = new ArranjoOrdenado(10, true);

        arr.inserir(3);
        arr.inserir(7);
        arr.inserir(1);

        assertEquals(1, arr.buscar(3));
        assertEquals(-1, arr.buscar(99));
    }

    @Test
    void testeDeletar() {
        ArranjoOrdenado arr = new ArranjoOrdenado(10, true);

        arr.inserir(1);
        arr.inserir(2);
        arr.inserir(3);

        arr.deletar(2);

        assertEquals(2, arr.getTamanho());
        assertEquals(1, arr.get(0));
        assertEquals(3, arr.get(1));
    }

    @Test
    void testeReordenar() {
        ArranjoOrdenado arr = new ArranjoOrdenado(10, true);

        arr.inserir(1);
        arr.inserir(3);
        arr.inserir(2);

        arr.setCrescente(false);

        assertEquals(3, arr.get(0));
        assertEquals(2, arr.get(1));
        assertEquals(1, arr.get(2));
    }
}